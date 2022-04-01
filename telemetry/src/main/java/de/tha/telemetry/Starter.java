package de.tha.telemetry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import de.tha.telemetry.anotations.Intercept;
import de.tha.telemetry.anotations.InterceptorHandler;
import de.tha.telemetry.fascades.ReadOnlyBlogFascade;
import de.tha.telemetry.fascades.ReadOnlyBlogFascadeImpl;
import de.tha.telemetry.model.BlogEntry;
import de.tha.telemetry.model.BlogEntryMetaData;
import de.tha.telemetry.persistence.BlogEntryMetaDataRepository;
import de.tha.telemetry.persistence.BlogEntryRepository;

public class Starter {

	private static final class ReadOnlyBlogFascadeInvocationHandler implements InvocationHandler {

		private ReadOnlyBlogFascade instance;

		public ReadOnlyBlogFascadeInvocationHandler(ReadOnlyBlogFascade instance) {
			this.instance = instance;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			Intercept annotation = null;

			if (method.isAnnotationPresent(Intercept.class)) {
				System.out.println("Annotation da sie ist!");
				annotation = method.getAnnotation(Intercept.class);
			}
			// precall
			if (annotation != null) {
				Class<? extends InterceptorHandler> handler = annotation.handler();

				Constructor<? extends InterceptorHandler> defaultConstructor = handler
						.getConstructor(new Class<?>[] {});

				InterceptorHandler newInstance = defaultConstructor.newInstance();

				newInstance.precall(method.getName(), new Date(), args);

			}

			Object invoke = method.invoke(instance, args);
			// postcall
			if (annotation != null) {
				Class<? extends InterceptorHandler> handler = annotation.handler();

				Constructor<? extends InterceptorHandler> defaultConstructor = handler
						.getConstructor(new Class<?>[] {});

				InterceptorHandler newInstance = defaultConstructor.newInstance();

				newInstance.postcall(method.getName(), new Date(), invoke);

			}
			return invoke;
		}
	}

	public static void main(String[] args) {
		BlogEntryRepository blogEntryRepository = new BlogEntryRepository();
		BlogEntryMetaDataRepository metaRepository = new BlogEntryMetaDataRepository();

		createData(blogEntryRepository, metaRepository);

		ReadOnlyBlogFascade fascadeInstance = getBlogFascadeInstance(blogEntryRepository, metaRepository);

		List<BlogEntry> allBlogEntries = fascadeInstance.getAllBlogEntries();

		BlogEntry entry = fascadeInstance.getBlogEntryById(22);

	}

	private static ReadOnlyBlogFascade getBlogFascadeInstance(BlogEntryRepository blogEntryRepository,
			BlogEntryMetaDataRepository metaRepository) {
		ReadOnlyBlogFascade readOnlyBlogFascade = new ReadOnlyBlogFascadeImpl(metaRepository, blogEntryRepository);

		ClassLoader classLoader = Starter.class.getClassLoader();

		return (ReadOnlyBlogFascade) Proxy.newProxyInstance(classLoader, new Class<?>[] { ReadOnlyBlogFascade.class },
				new ReadOnlyBlogFascadeInvocationHandler(readOnlyBlogFascade));
	}

	private static void createData(BlogEntryRepository blogEntryRepository,
			BlogEntryMetaDataRepository metaRepository) {
		BlogEntryMetaData createBlogEntryMetaData = createBlogEntryMetaData("Heinz Meyer", "Kaffe", "Tee", "Koffein");
		createBlogEntryMetaData = metaRepository.save(createBlogEntryMetaData);

		BlogEntry blogEntry = createBlogEntry("Dies ist ein Blog über Kaffee");
		blogEntry = blogEntryRepository.save(blogEntry);

		blogEntry.setMetaData(createBlogEntryMetaData);

		createBlogEntryMetaData.setBlogEntryId(blogEntry.getId());

		metaRepository.update(createBlogEntryMetaData);
		blogEntryRepository.update(blogEntry);
	}

	private static BlogEntry createBlogEntry(String content) {
		BlogEntry blogEntry = new BlogEntry();
		blogEntry.setContent(content);
		return blogEntry;
	}

	private static BlogEntryMetaData createBlogEntryMetaData(String author, String... keys) {
		BlogEntryMetaData blogEntryMetaData = new BlogEntryMetaData();
		blogEntryMetaData.setAuthor(author);
		blogEntryMetaData.setCreationDate(new Date());
		blogEntryMetaData.setKeyWords(Arrays.asList(keys));
		return blogEntryMetaData;
	}

}
