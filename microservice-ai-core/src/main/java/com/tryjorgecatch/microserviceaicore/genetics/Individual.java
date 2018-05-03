package com.tryjorgecatch.microserviceaicore.genetics;

import java.util.List;

public class Individual implements Comparable<Individual>{
	
	private List<Double> genes;
	private Double fitness;

	public List<Double> getGenes() {
		return genes;
	}

	public void setGenes(List<Double> genes) {
		this.genes = genes;
	}

	public Double getFitness() {
		return fitness;
	}

	public void setFitness(Double fitness) {
		this.fitness = fitness;
	}

	@Override
	public int compareTo(Individual o) {
		if(this.fitness < o.getFitness())
			return -1;
		else
			return 1;
	}

}
