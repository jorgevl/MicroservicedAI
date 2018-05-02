package com.tryjorgecatch.microserviceaicore;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@SpringBootApplication
public class MicroserviceAiCoreApplication {

	public static void main(String[] args) {

	    Map<String, Object> request = new HashMap<>();
	    
	    List<Double> indiv = new ArrayList<>();
	    
	    indiv.add(1.0);
	    indiv.add(1.5);
	    indiv.add(2.0);
	    
	    request.put("testIndividual", indiv);

	    Gson gson = new Gson();

	    RestTemplate restTemplate = new RestTemplate();

	    String jsonResponse = restTemplate.postForObject("http://localhost:8081/XService", gson.toJson(request), String.class);
 
	    Map<String, Object> response = gson.fromJson(jsonResponse, Map.class);

	    List<Double> modifiedIndividual = (List<Double>) response.get("testIndividual");
	    
	    for(Double gen : modifiedIndividual)
	    	System.out.println(gen);
	    
	}
}
