package com.photorize.server.persistence;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.stereotype.Component;

import com.photorize.server.entities.Image;

@Component
public class SimpleImageStore implements IImageStore{

	private static final Map<String, Image> imgMap = new HashMap<String, Image>();
	
	@Inject
	private ITagStore tags;
	
	public SimpleImageStore() {
		Image i = new Image();
		i.setHash("1");
		i.setRating(2);
		i.setTitle("cuba 001");
		i.setTags(Arrays.asList("cuba", "beach", "flo"));
		imgMap.put(i.getHash(), i);
		
		i = new Image();
		i.setHash("2");
		i.setRating(4);
		i.setTitle("cuba 002");
		i.setTags(Arrays.asList("cuba", "beach", "colleen"));
		imgMap.put(i.getHash(), i);
		
		i = new Image();
		i.setHash("3");
		i.setRating(3);
		i.setTitle("cuba 003");
		i.setTags(Arrays.asList("cuba"));
		imgMap.put(i.getHash(), i);
	}
	
	@Override
	public int size() {
		return imgMap.size();
	}

	@Override
	public String put(Image newImage) {
		imgMap.put(newImage.getHash(), newImage);
		for(String tag : newImage.getTags())
			tags.addTag(tag);
		
		return newImage.getHash();
	}

	@Override
	public Image get(String hash) {
		return imgMap.get(hash);
	}

	@Override
	public Set<Image> getAll() {
		Set<Image> result = new HashSet<Image>();
		result.addAll(imgMap.values());
		
		return result;
	}

}
