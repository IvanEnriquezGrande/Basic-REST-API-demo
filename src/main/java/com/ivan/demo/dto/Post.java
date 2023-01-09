package com.ivan.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

//The entity annotation is used to create a table of this class in the h2 console
@Entity
public class Post {
	//Id annotation indicates this value is going to be a primary key on the data base
	@Id
	@GeneratedValue
	private int id;
	private String description;
	//ManyToOne annotation indicates the relationship with this value
	@ManyToOne(fetch = FetchType.EAGER)
	@JsonIgnore
	private User user;
	
	
	public Post(int id, String description, User user) {
		super();
		this.id = id;
		this.description = description;
		this.user = user;
	}
	
	
	public Post() {
		super();
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Post [id=");
		builder.append(id);
		builder.append(", description=");
		builder.append(description);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}
}
