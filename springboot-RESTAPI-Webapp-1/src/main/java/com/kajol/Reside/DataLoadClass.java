package com.kajol.Reside;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kajol.dao.ServicePrice;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
@Component
public class DataLoadClass  implements CommandLineRunner {
	
	 private final RedisTemplate<String, ServicePrice> redisTemplate;
	 
	
		 @Autowired
	    public DataLoadClass(RedisTemplate<String, ServicePrice> redisTemplate) {
	        this.redisTemplate = redisTemplate;
	    }
	    
	    @Override
	    public void run(String... args) throws IOException {
	    	int i=0;
	    	HashOperations<String, String,String> ops=redisTemplate.opsForHash();
	            // Read JSON data from the file
	            List<ServicePrice> items = readDataFromJsonFile();
                System.out.print("data loaded");
                System.out.print(items);
	            // Store data in Redis cache
//	            for (ServicePrice item : items) {
//	                ops.put("price_key4", item.getName(), item.getPrice());
//	                i++;
//	            }
//	            Map<String,ServicePrice> m=ops.entries("price_key3");
//	    	     s=m.values();
//	    		System.out.println("value of"+s);

	          
	          
	    }
	    
	    private List<ServicePrice> readDataFromJsonFile() throws IOException {
	        ClassPathResource resource = new ClassPathResource("data.json");
	        ObjectMapper objectMapper = new ObjectMapper();
	        return objectMapper.readValue(resource.getInputStream(), new TypeReference<>() {});
	    }


}
