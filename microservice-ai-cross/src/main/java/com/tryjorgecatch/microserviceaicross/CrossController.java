package com.tryjorgecatch.microserviceaicross;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrossController {

	  @GetMapping("/XService")
	  public ResponseEntity<List<String>> cross() {
	    		  
		  List<String> individual = new ArrayList<>();
		  
		  individual.add("testValue");
		  
		  return new ResponseEntity<List<String>>(individual, HttpStatus.OK);
	  }
	
}
