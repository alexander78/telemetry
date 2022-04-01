package de.tha.telemetry.model;

public class BlogEntry implements Entity{

	private int id;

	private String content;

	private BlogEntryMetaData metaData;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BlogEntryMetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(BlogEntryMetaData metaData) {
		this.metaData = metaData;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Entry------------");
		builder.append("\n");
		builder.append("ID: ");
		builder.append(id);
		builder.append("\n");
		builder.append("CONTENT: ");
		builder.append(content);
		builder.append("\n");
		builder.append("Entry------------");
		return builder.toString();
	}

}
