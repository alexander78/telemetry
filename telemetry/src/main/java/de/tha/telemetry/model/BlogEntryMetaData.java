package de.tha.telemetry.model;

import java.util.Date;
import java.util.List;

public class BlogEntryMetaData implements Entity {
	
	private int id;
	
	private String author;
	
	private List<String> keyWords;
	
	private Date creationDate;
	
	private int blogEntryId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getBlogEntryId() {
		return blogEntryId;
	}

	public void setBlogEntryId(int blogEntryId) {
		this.blogEntryId = blogEntryId;
	}
	
}
