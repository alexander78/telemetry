package de.tha.telemetry;

import java.util.Arrays;
import java.util.Date;

import de.tha.telemetry.fascades.ReadOnlyBlogFascade;
import de.tha.telemetry.model.BlogEntry;
import de.tha.telemetry.model.BlogEntryMetaData;
import de.tha.telemetry.persistence.BlogEntryMetaDataRepository;
import de.tha.telemetry.persistence.BlogEntryRepository;

public class Starter {

	public static void main(String[] args) {
		BlogEntryRepository blogEntryRepository = new BlogEntryRepository();
		BlogEntryMetaDataRepository metaRepository = new BlogEntryMetaDataRepository();

		createData(blogEntryRepository, metaRepository);
		
		ReadOnlyBlogFascade readOnlyBlogFascade = new ReadOnlyBlogFascade(metaRepository, blogEntryRepository);
		
		System.err.println(readOnlyBlogFascade.getAllBlogEntries());
		
		

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
