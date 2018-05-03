package com.tryjorgecatch.microserviceaicore.problem;

import java.util.List;

import com.tryjorgecatch.microserviceaicore.genetics.Individual;

public abstract class Problem {
	
	private Integer numVars;
	private List<Double> initRanges, endRanges;
	private Double perfection;
	
	public abstract Double fitnessFunction(List<Individual> population);
	
	public abstract Individual getBestIndividual(List<Individual> population);
	
	public abstract Double fitnessFunction(Individual individual);

	public List<Double> getInitRanges() {
		return initRanges;
	}

	public void setInitRanges(List<Double> initRanges) {
		this.initRanges = initRanges;
	}

	public List<Double> getEndRanges() {
		return endRanges;
	}

	public void setEndRanges(List<Double> endRanges) {
		this.endRanges = endRanges;
	}

	public Integer getNumVars() {
		return numVars;
	}

	public void setNumVars(Integer numVars) {
		this.numVars = numVars;
	}

	public Double getPerfection() {
		return perfection;
	}

	public void setPerfection(Double perfection) {
		this.perfection = perfection;
	}	
	
	
	
}
