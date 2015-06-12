package com.princeli.solr.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

@SolrDocument(solrCoreName = "document")
public class DocumentSolr implements Serializable {

	private static final long serialVersionUID = 1L;

	@Field
	private String id;

	@Field
	private Date date_added;

	@Field
	private String title;

	@Field
	private String content;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate_added() {
		return date_added;
	}

	public void setDate_added(Date date_added) {
		this.date_added = date_added;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "DocumentSolr [id=" + id + ", date_added=" + date_added
				+ ", title=" + title + ", content=" + content + "]";
	}

	

}
