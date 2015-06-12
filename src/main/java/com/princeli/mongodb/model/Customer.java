package com.princeli.mongodb.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// 注意这里的注解哦，简单看看mongodb的文档就知道这个是文档集合
@Document(collection = "customer")
public class Customer {

	@Id
	private String id;

	private String firstName;
	private String lastName;

	private String name;

	private long age;

	private String sex;

	public Customer() {
	}

	public Customer(String id, String firstName, String lastName, String name,
			long age, String sex) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.name = name;
		this.age = age;
		this.sex = sex;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", name=" + name + ", age=" + age
				+ ", sex=" + sex + "]";
	}

}