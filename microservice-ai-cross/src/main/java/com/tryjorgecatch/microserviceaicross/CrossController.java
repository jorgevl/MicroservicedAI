package com.tryjorgecatch.microserviceaicross;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class CrossController {

	  @RequestMapping(value = "/XService", method = RequestMethod.POST)
	  public ResponseEntity<String> crossGenes(@RequestBody String params) {

		  Gson gson = new Gson();
		  
		  Map<String, Object> paramMap = gson.fromJson(params, Map.class); 
		  
		  List<Double> testIndividual  = (List<Double>) paramMap.get("testIndividual");
		  
		  for(Double gen : testIndividual) {
			  gen*=2;
		  }
		  
		  Map<String, Object> response = new HashMap<>();
		  
		  response.put("testIndividual", testIndividual);
		  
		  return new ResponseEntity<String>(gson.toJson(response, Map.class), HttpStatus.OK);
	  }
	
}
