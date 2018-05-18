package com.tryjorgecatch.microserviceaicore.genetics;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
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

	public Individual clone() {
		Individual ret = new Individual();
		ret.setFitness(fitness.doubleValue());
		List<Double> clonedGenes = new ArrayList<>(genes.size());
		for(Double gen : genes)
			clonedGenes.add(gen.doubleValue());
		ret.setGenes(clonedGenes);
		
		return ret;
	}
	
}
