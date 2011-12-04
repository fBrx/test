package com.photorize.server.persistence;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SimpleTagStore implements ITagStore{

	private static final Map<String, Integer> tags = new HashMap<String, Integer>();
	
	public SimpleTagStore() {
		tags.put("cuba", 3);
		tags.put("beach", 2);
		tags.put("flo", 1);
		tags.put("colleen", 1);
	}
	
	@Override
	public int size() {
		return tags.size();
	}

	@Override
	public Map<String, Integer> getAll() {
		return tags;
	}

	@Override
	public void addTag(String name) {
		Integer count = tags.get(name);
		if(count == null)
			tags.put(name, 1);
		else
			tags.put(name, count + 1);
	}

	@Override
	public void removeTag(String name) {
		Integer count = tags.get(name);
		if(count == null || count == 1)
			tags.remove(name);
		else
			tags.put(name, count - 1);
	}

	@Override
	public Integer getTag(String name) {
		return tags.get(name);
	}

	@Override
	public Map<String, Integer> getTags(String name) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		for(String key : tags.keySet()) {
			if(!key.startsWith(name))
				continue;
			
			Integer count = tags.get(key);
			result.put(key, count);
			
		}
			
		return result;
	}
	

}
