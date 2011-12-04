package com.photorize.server.service;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.photorize.server.entities.Image;
import com.photorize.server.persistence.IImageStore;

@Component
@Path("/images")
public class ImageResource{
	
	@Inject
	private IImageStore images;

	private static final Logger LOG = LoggerFactory.getLogger(ImageResource.class);
	
	public ImageResource() {
		LOG.debug("creating new image resource");
	}
	
	@PostConstruct
	public void initialized() {
		LOG.debug("image resource initialized");
		LOG.info("currently hosting " + images.size() + " images");
	}
	
	@GET
	@Path("/count")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject getImageCount() {
		int imgCount = images.size();
		LOG.debug("currently hosting " + imgCount + " images");
		
		JSONObject result = new JSONObject();
		try {
			result.put("count", imgCount);
		} catch (JSONException ex) {
			//if this happens something screwed up bad...
			LOG.error("cannot create json object", ex);
		}
		
		return result;
	}
	
	@GET
	@Path("/{hash}")
	@Produces(MediaType.APPLICATION_JSON)
	public Image getImage(@PathParam("hash") String hash) {
		LOG.debug("requested image: " + hash);

		Image img = images.get(hash);
		LOG.debug("found image: " + img);
		return img;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Image> getAllImages(){
		return images.getAll();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void storeImage(Image newImage) {
		images.put(newImage);
	}
	
}
