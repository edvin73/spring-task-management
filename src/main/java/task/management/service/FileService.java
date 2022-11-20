package task.management.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;


import task.management.model.TaskFile;

@Service
public class FileService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Value("${files.path}")
	private String filesPath;
	
		
	public Resource download(String filename) {
	
		logger.debug("filename {}", filename);
		logger.debug("filesPath {} ",  filesPath);
		
		try {
			Path file = Paths.get(filesPath).resolve(filename);		
			Resource resource = new UrlResource(file.toUri());
			
			if(resource.exists() || resource.isReadable()) {				
				return resource;				
			} else {
				throw new RuntimeException("Could not read the file!");
			}
			
			
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}		
	}
	
	
	

}
