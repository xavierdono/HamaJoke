package com.xav.hamajoke.domain;

public class ObjectItem {
	
	private String fullname;
	private String name;
	
	public ObjectItem(String fullname, String name) {
		this.fullname = fullname;
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public String getName() {
		return name;
	}
}