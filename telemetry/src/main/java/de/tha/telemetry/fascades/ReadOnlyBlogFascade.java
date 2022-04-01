package de.tha.telemetry.fascades;

import java.util.List;

import de.tha.telemetry.anotations.Intercept;
import de.tha.telemetry.model.BlogEntry;
import de.tha.telemetry.model.BlogEntryMetaData;
import de.tha.telemetry.persistence.BlogEntryMetaDataRepository;
import de.tha.telemetry.persistence.BlogEntryRepository;

public class ReadOnlyBlogFascade {

	private BlogEntryRepository blogEntryRepos;
	private BlogEntryMetaDataRepository metaDataRepos;

	public ReadOnlyBlogFascade(BlogEntryMetaDataRepository metaDataRepos, BlogEntryRepository blogEntryRepos) {
		this.metaDataRepos = metaDataRepos;
		this.blogEntryRepos = blogEntryRepos;
	}

	@Intercept
	public List<BlogEntry> getAllBlogEntries() {
		return blogEntryRepos.readAll();
	}

	public BlogEntry getBlogEntryById(int id) {
		return blogEntryRepos.findById(id);
	}

	public BlogEntryMetaData getBlogEntryMetaDataById(int id) {
		return metaDataRepos.findById(id);
	}

	public List<BlogEntryMetaData> readAllBlogMetaDataEntries() {
		return metaDataRepos.readAll();
	}

}
