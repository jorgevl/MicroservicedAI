package com.tryjorgecatch.microserviceaicross;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.mutable.MutableDouble;
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

		List<Double> indOne = (List<Double>) paramMap.get("indOne");
		List<Double> indTwo = (List<Double>) paramMap.get("indTwo");

		if (indOne != null && indTwo != null) {

			Random rGen = new Random();

			if (indOne.size() == indTwo.size()) {
				Integer crossPoint;
				crossPoint = rGen.nextInt(indOne.size());

				for (int i = crossPoint; i < indOne.size(); i++) {
					MutableDouble genOne = new MutableDouble(indOne.get(i));
					MutableDouble genTwo = new MutableDouble(indTwo.get(i));
					
					swapGenes(genOne, genTwo);
				
					indOne.set(i, genOne.getValue());
					indTwo.set(i, genTwo.getValue());
				}
			}

			Map<String, Object> response = new HashMap<>();

			response.put("indOne", indOne);
			response.put("indTwo", indTwo);

			return new ResponseEntity<String>(gson.toJson(response, Map.class), HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
	}
	  
	  private void swapGenes(MutableDouble genOne, MutableDouble genTwo) {
		  Double t = genOne.getValue();
		  genOne.setValue(genTwo.getValue());
		  genTwo.setValue(t);
		  
	  }
	
}
