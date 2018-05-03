package com.tryjorgecatch.microserviceaicore.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.tryjorgecatch.microserviceaicore.genetics.Individual;
import com.tryjorgecatch.microserviceaicore.problem.ExampleProblem;
import com.tryjorgecatch.microserviceaicore.problem.Problem;

public class EvolutiveAlgorithm {
	private static final Integer popSize = 100;
	private static final Integer iterations = 100;
	private static final Double crossChance = 0.6;
	private static final Double mutationChance = 0.05;
	private static final Problem problem = new ExampleProblem();

	public void start() {
		List<Individual> population = new ArrayList<>(popSize);

		generateStartingPop(population);
		iterate(population);
		showResults(population);
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
		Random rGen = new Random();
		
		for(Individual individual : population) {
			if(rGen.nextDouble() < mutationChance) {
				mutateIndividual(individual);
			}
		}
	}

	private void mutateIndividual(Individual individual) {
		// TODO: Call the microservice here, replace the individual with the response
	}

	private void crossPopulation(List<Individual> population) {
		Random rGen = new Random();
		
		List<Individual> newPopulation = new ArrayList<Individual>(popSize);
		while(newPopulation.size() < popSize) {
			Individual crossIndOne = selectIndividualToCross(population);
			Individual crossIndTwo = selectIndividualToCross(population);
			
			if(rGen.nextDouble() < crossChance) {
				// TODO: Call the microservice here, add the response to the new population
			}
			else {
				newPopulation.add(crossIndOne);
				
				if(newPopulation.size() < popSize)
					newPopulation.add(crossIndTwo);
			}

		}
		
		population = newPopulation;
	}

	private Individual selectIndividualToCross(List<Individual> population) {
		
		Random rGen = new Random();
		
		Integer halfPop;
		
		if(popSize % 2 == 0)
			halfPop = popSize / 2;
		else
			halfPop = popSize / 2 + 1;
		
		Collections.sort(population);
		
		Double coin = rGen.nextDouble();
		
		//Best 50 per cent fitnesses have 75 per cent chance to be picked
		Integer selectedIndex;
		if(coin < 0.75)
			selectedIndex = halfPop + rGen.nextInt(popSize - halfPop);
		else 
			selectedIndex = rGen.nextInt(halfPop);
				
		return population.get(selectedIndex);
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
