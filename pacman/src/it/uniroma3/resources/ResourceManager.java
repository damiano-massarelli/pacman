package it.uniroma3.resources;

import java.io.InputStream;

public class ResourceManager {
	private static ResourceManager instance = null;
	
	private ResourceManager() {}
	
	public static ResourceManager getInstance() {
		if (instance == null)
			instance = new ResourceManager();
		return instance;
	}
	
	public InputStream getResourceAsStream(String path) {
		return getClass().getResourceAsStream(path);
	}
	
}
