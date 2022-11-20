package task.management.config;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	Logger logger = LoggerFactory.getLogger(getClass());
 
	@Bean
	public ModelMapper modelMapper() {
		
		logger.debug("Model Mapper Launched...");
			
	    return new ModelMapper();
	}
}
