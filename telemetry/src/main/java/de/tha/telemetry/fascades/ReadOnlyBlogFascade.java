package de.tha.telemetry.fascades;

import java.util.List;

import de.tha.telemetry.anotations.Intercept;
import de.tha.telemetry.model.BlogEntry;
import de.tha.telemetry.model.BlogEntryMetaData;

public interface ReadOnlyBlogFascade {

	@Intercept(handler = SysoutInterceptionHandler.class)
	List<BlogEntry> getAllBlogEntries();

	@Intercept(handler = SysoutInterceptionHandler.class)
	BlogEntry getBlogEntryById(int id);

	BlogEntryMetaData getBlogEntryMetaDataById(int id);

	List<BlogEntryMetaData> readAllBlogMetaDataEntries();

}