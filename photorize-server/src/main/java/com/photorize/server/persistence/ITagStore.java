package com.photorize.server.persistence;

import java.util.Map;

public interface ITagStore {

	public int size();
	public Map<String, Integer> getAll();
	public void addTag(String name);
	public void removeTag(String name);
	public Integer getTag(String name);
	public Map<String, Integer> getTags(String name);
	
	
}
