package com.tryjorgecatch.microserviceaicross;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@SuppressWarnings("unchecked")
@RestController
public class CrossController {

	  @RequestMapping(value = "/XService", method = RequestMethod.POST)
	  public ResponseEntity<String> crossGenes(@RequestBody String params) {

		  Gson gson = new Gson();
		  
		  Map<String, Object> paramMap = gson.fromJson(params, Map.class); 
		  
		  List<Double> indOne  = (List<Double>) paramMap.get("indOne");
		  List<Double> indTwo  = (List<Double>) paramMap.get("indTwo");
		  Double crossChance = (Double) paramMap.get("crossChance");  
		  
		  if(indOne != null && indTwo != null && crossChance != null) {
			  
			  Random rGen = new Random();
			  
			  if(indOne.size() == indTwo.size()) {
				  Double coin;
				  for(int i = 0; i < indOne.size(); i++) {	
						coin = rGen.nextDouble();
						
						if(coin > crossChance) 
							swapGenes(indOne.get(i), indTwo.get(i));			
				  }
			  }
			  
			  Map<String, Object> response = new HashMap<>();
			  
			  response.put("indOne", indOne);
			  response.put("indTwo", indTwo); 
			  
			  return new ResponseEntity<String>(gson.toJson(response, Map.class), HttpStatus.OK);
		  }
		  else
			  return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
	  }
	  
	  private void swapGenes(Double genOne, Double genTwo) {
		  Double t = genOne;
		  genOne = genTwo.doubleValue();
		  genTwo = t.doubleValue();
	  }
	
}
