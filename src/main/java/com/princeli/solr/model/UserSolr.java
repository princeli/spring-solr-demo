package com.princeli.solr.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;


@SolrDocument(solrCoreName = "user")
public class UserSolr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Field
	private String id;

	@Field
	private Date birth;

	@Field
	private Boolean sex;

	@Field
	private String name;
	
	@Field
	private Integer salary;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "UserSolr [id=" + id + ", birth=" + birth + ", sex=" + sex
				+ ", name=" + name + ", salary=" + salary + "]";
	}
	
	
}
