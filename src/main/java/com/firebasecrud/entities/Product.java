package com.firebasecrud.entities;

import org.springframework.stereotype.Component;

import com.google.cloud.Timestamp;

@Component
public class Product {
	
	private String id;
	private String name;
	private String description;
	private Timestamp timestampCreate = null;
	private Timestamp timestampDelete = null;
	private Timestamp timestampUpdate = null;
	
	public Product() {
		super();
	}
	
	public Product(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Timestamp getTimestampCreate() {
//		return timestampCreate;
//	}

	public void setTimestampCreate(Timestamp timestampCreate) {
		this.timestampCreate = timestampCreate;
	}

//	public Timestamp getTimestampDelete() {
//		return timestampDelete;
//	}

	public void setTimestampDelete(Timestamp timestampDelete) {
		this.timestampDelete = timestampDelete;
	}

//	public Timestamp getTimestampUpdate() {
//		return timestampUpdate;
//	}

	public void setTimestampUpdate(Timestamp timestampUpdate) {
		this.timestampUpdate = timestampUpdate;
	}
	
	

}
