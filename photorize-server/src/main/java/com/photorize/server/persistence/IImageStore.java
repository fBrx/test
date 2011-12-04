package com.photorize.server.persistence;

import java.util.Set;

import com.photorize.server.entities.Image;

public interface IImageStore {

	public int size();
	public String put(Image newImage);
	public Image get(String hash);
	public Set<Image> getAll();
}
