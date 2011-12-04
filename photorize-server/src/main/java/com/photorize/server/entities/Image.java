package com.photorize.server.entities;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Image {

	public Image() {}
	
	private String hash;
	private String title;
	private List<String> tags;
	private Integer rating;
	
	public String getHash() {
		return this.hash;
	}
	
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<String> getTags() {
		return this.tags;
	}
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public Integer getRating() {
		return this.rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
