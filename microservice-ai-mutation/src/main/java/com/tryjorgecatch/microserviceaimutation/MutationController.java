package com.tryjorgecatch.microserviceaimutation;

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
public class MutationController {

	@RequestMapping(value = "/MService", method = RequestMethod.POST)
	public ResponseEntity<String> crossGenes(@RequestBody String params) {

		Gson gson = new Gson();

		Map<String, Object> paramMap = gson.fromJson(params, Map.class);

		List<Double> ind = (List<Double>) paramMap.get("ind");
		List<Double> initRanges = (List<Double>) paramMap.get("initRanges");
		List<Double> endRanges = (List<Double>) paramMap.get("endRanges");
		Double mutationChance = (Double) paramMap.get("mutationChance");

		if (ind != null && initRanges != null && endRanges != null) {

			Random rGen = new Random();

			Double coin;

			for (int i = 0; i < ind.size(); i++) {
				coin = rGen.nextDouble();

				if (coin < mutationChance) {
					MutableDouble gen = new MutableDouble(ind.get(i));
					mutaGen(gen, initRanges.get(i), endRanges.get(i));
					ind.set(i, gen.doubleValue());
				}
			}

			Map<String, Object> response = new HashMap<>();

			response.put("ind", ind);

			return new ResponseEntity<String>(gson.toJson(response, Map.class), HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.EXPECTATION_FAILED);
	}

	private void mutaGen(MutableDouble gen, Double min, Double max) {
		Random rGen = new Random();
		
		gen.setValue(min + (max - min) * rGen.nextDouble());
	}
	  
	
}
