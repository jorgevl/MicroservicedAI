package com.tryjorgecatch.microserviceaicore.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tryjorgecatch.microserviceaicore.genetics.Individual;
import com.tryjorgecatch.microserviceaicore.problem.ExampleProblem;
import com.tryjorgecatch.microserviceaicore.problem.Problem;

public class EvolutiveAlgorithm {
	private Integer popSize;
	private Integer eliteSize;
	private Integer iterations;
	private Double crossChance;
	private Double mutationChance;
	private Problem problem;
	
	public void start() {
		config();
		
		List<Individual> population = new ArrayList<>(popSize);

		generateStartingPop(population);
		iterate(population);
		showResults(population);
	}

	private void config() {
		popSize = 100;
		eliteSize = 5;
		iterations = 100;
		crossChance = 0.6;
		mutationChance = 0.3;
		problem = new ExampleProblem();
	}

	private void showResults(List<Individual> population) {
		Individual bestIndiv = problem.getBestIndividual(population);
		
		System.out.println("Best fitness found (distance to perfection): " + bestIndiv.getFitness());
	}

	private void iterate(List<Individual> population) {
		for(int i = 0; i < iterations; i++) {
			evaluatePopulation(population);
			if(i != iterations -1) {
				crossPopulation(population);
				mutatePopulation(population);
			}
		}
	}

	private void mutatePopulation(List<Individual> population) {
		for(int i = eliteSize; i < population.size(); i++)
			mutateIndividual(population.get(i));
	}

	@SuppressWarnings("unchecked")
	private void mutateIndividual(Individual individual) {
		Map<String, Object> request = new HashMap<>();
	    
	    request.put("ind", individual.getGenes());
	    request.put("mutationChance", mutationChance);
	    request.put("initRanges", problem.getInitRanges());
	    request.put("endRanges", problem.getEndRanges());
	    
	    Gson gson = new Gson();

	    RestTemplate restTemplate = new RestTemplate();

	    String jsonResponse = restTemplate.postForObject("http://localhost:8082/MService", gson.toJson(request), String.class);
	    
	    Map<String, Object> response = gson.fromJson(jsonResponse, Map.class);

	    List<Double> modifiedInd = (List<Double>) response.get("ind");
	   
	    individual.setGenes(modifiedInd);
	}

	private void crossPopulation(List<Individual> population) {
		Random rGen = new Random();
		
		Collections.sort(population);
		
		List<Individual> newPopulation = new ArrayList<Individual>(popSize);
		
		saveElite(population, newPopulation);
		
		while(newPopulation.size() < popSize) {
			Individual crossIndOne = selectIndividualToCross(population);
			Individual crossIndTwo = selectIndividualToCross(population);
			
			if(rGen.nextDouble() < crossChance) {
				crossIndividuals(crossIndOne, crossIndTwo);
			}
			else {
				newPopulation.add(crossIndOne);
				
				if(newPopulation.size() < popSize)
					newPopulation.add(crossIndTwo);
			}

		}
		
		population = newPopulation;
	}

	private void saveElite(List<Individual> population, List<Individual> newPopulation) {
		for(int i = 0; i < eliteSize; i++)
			newPopulation.add(population.get(i).clone());
	}

	@SuppressWarnings("unchecked")
	private void crossIndividuals(Individual crossIndOne, Individual crossIndTwo) {
		Map<String, Object> request = new HashMap<>();
	    
	    request.put("indOne", crossIndOne.getGenes());
	    request.put("indTwo", crossIndTwo.getGenes());
	    
	    Gson gson = new Gson();

	    RestTemplate restTemplate = new RestTemplate();

	    String jsonResponse = restTemplate.postForObject("http://localhost:8081/XService", gson.toJson(request), String.class);
	    
	    Map<String, Object> response = gson.fromJson(jsonResponse, Map.class);

	    List<Double> modifiedIndOne = (List<Double>) response.get("indOne");
		List<Double> modifiedIndTwo = (List<Double>) response.get("indTwo");
	   
	    crossIndOne.setGenes(modifiedIndOne);
	    crossIndTwo.setGenes(modifiedIndTwo);
	}

	private Individual selectIndividualToCross(List<Individual> population) {
		
		Random rGen = new Random();
		
		Integer halfPop;
		
		if(popSize % 2 == 0)
			halfPop = popSize / 2;
		else
			halfPop = popSize / 2 + 1;

		Double coin = rGen.nextDouble();
		
		//Best 50 per cent fitnesses have 75 per cent chance to be picked
		Integer selectedIndex;
		if(coin < 0.75)
			selectedIndex = rGen.nextInt(halfPop);
		else 
			selectedIndex = halfPop + rGen.nextInt(popSize - halfPop);
			
				
		return population.get(selectedIndex).clone();
	}

	private void evaluatePopulation(List<Individual> population) {
		problem.fitnessFunction(population);
		
	}

	private void generateStartingPop(List<Individual> population) {
		Random rGen = new Random();

		for (int j = 0; j < popSize; j++) {
			Individual newInd = new Individual();
			List<Double> newGenes = new ArrayList<>(problem.getNumVars());

			for (int i = 0; i < problem.getNumVars(); i++) {
				Double init = problem.getInitRanges().get(i);
				Double end = problem.getEndRanges().get(i);

				newGenes.add(init + (end - init) * rGen.nextDouble());
			}

			newInd.setGenes(newGenes);
			population.add(newInd);
		}

	}
}
