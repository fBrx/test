package com.photorize.server.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.photorize.server.entities.Image;
import com.photorize.server.persistence.IImageStore;
import com.photorize.server.persistence.ITagStore;

@Component
@Path("/tags")
public class TagResource {

	private static final Logger LOG = LoggerFactory.getLogger(TagResource.class);
	
	@Inject
	private ITagStore tags;
	
	@Inject
	private IImageStore images;
	
	public TagResource() {
		LOG.debug("creating new tag resource");
	}
	
	@PostConstruct
	public void initialized() {
		LOG.debug("tag resource initialized");
		LOG.info("currently hosting " + tags.size() + " images");
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getTagCount() {
		int tagCount = tags.size();
		LOG.debug("currently hosting " + tagCount + " tags");
		
		JSONObject result = new JSONObject();
		try {
			result.put("count", tagCount);
		} catch (JSONException ex) {
			//if this happens something screwed up bad...
			LOG.error("cannot create json object", ex);
		}
		
		return result;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getAllTags() {
		Map<String, Integer> allTags = tags.getAll();
		JSONArray result = new JSONArray();
		for(String key : allTags.keySet()) {
			Integer count = allTags.get(key);
			JSONObject entry = new JSONObject();
			try {
				entry.put("name", key);
				entry.put("count", count);
			} catch (JSONException ex) {
				//if this happens something screwed up bad...
				LOG.error("cannot create json object", ex);
			}
			
			result.put(entry);
		}
		return result;
	}
	
	@GET
	@Path("/{pattern}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getTags(@PathParam("pattern") String pattern) {
		Map<String, Integer> allTags = tags.getAll();
		JSONArray result = new JSONArray();
		for(String key : allTags.keySet()) {
			if(!key.startsWith(pattern))
				continue;
			
			Integer count = allTags.get(key);
			JSONObject entry = new JSONObject();
			try {
				entry.put("tag", key);
				entry.put("count", count);
			} catch (JSONException ex) {
				//if this happens something screwed up bad...
				LOG.error("cannot create json object", ex);
			}
			
			result.put(entry);
		}
		return result;
	}
	
	@GET
	@Path("/{tag}/images")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Image> getImagesByTag(@PathParam("tag") String tag){
		Integer tagCount = tags.getTag(tag);
		if(tagCount == null || tagCount < 1)
			return new HashSet<Image>();
		
		Set<Image> result = new HashSet<Image>();
		for(Image img : images.getAll()) {
			if(img.getTags().contains(tag))
				result.add(img);
		}
		
		return result;
	}
}
