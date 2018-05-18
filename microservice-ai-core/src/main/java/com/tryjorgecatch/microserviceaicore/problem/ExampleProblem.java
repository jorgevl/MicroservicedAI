package com.tryjorgecatch.microserviceaicore.problem;

import java.util.ArrayList;
import java.util.List;

import com.tryjorgecatch.microserviceaicore.genetics.Individual;

public class ExampleProblem extends Problem {

	public ExampleProblem() {
		
		this.setNumVars(2);
		
		this.setPerfection(-186.7309);
		
		this.setInitRanges(new ArrayList<Double>());
		this.setEndRanges(new ArrayList<Double>());
		
		this.getInitRanges().add(-10.0);
		this.getEndRanges().add(10.0);
		
		this.getInitRanges().add(-10.0);
		this.getEndRanges().add(10.0);
		
	}
	
	@Override
	public Double fitnessFunction(List<Individual> population) {
		
		Double totalFitness = 0.0;
		
		for(Individual individual : population) {
			Double fitness = evaluate(individual);
			individual.setFitness(fitness);
			totalFitness += fitness;
		}
		
		return totalFitness / population.size();
		
	}

	@Override
	public Double fitnessFunction(Individual individual) {
		
		return evaluate(individual);
		
	}

	private Double evaluate (Individual individual) {
		
		Double xValue = individual.getGenes().get(0);
		Double yValue = individual.getGenes().get(1);
		
		Double sum1 = 0.0;
		Double sum2 = 0.0;
		
		for(int i = 1; i <= 5; i++){
			sum1 += i*Math.cos((i+1)*xValue + i);
			sum2 += i*Math.cos((i+1)*yValue + i);
		}
		
		return Math.abs(getPerfection() - (sum1*sum2));
	}

	@Override
	public Individual getBestIndividual(List<Individual> population) {
		
		Individual bestIndividual = population.get(0);
		
		for(Individual individual : population) {
			if(individual.getFitness() < bestIndividual.getFitness())
				bestIndividual = individual;
		}
		
		return bestIndividual;
	}
	
}
