package com.github.abcforj;

import java.util.ArrayList;
import java.util.List;

import com.github.abcforj.bee.ScoutBee;
import com.github.abcforj.beehive.Beehive;
import com.github.abcforj.function.Function;

public class ABC
{
    private Beehive beehive;
    private int numberOfExplorationCycles;
    private int numberOfExploitationCycles;
    private Function function;
    private List<Double> bestSolutions;

    public ABC(int beehiveSize, float scoutBeesProportion, int numberOfExplorationCycles,
	    int numberOfExploitationCycles, Function function, int dimension)
    {
	this.beehive = new Beehive(beehiveSize, scoutBeesProportion, dimension);
	this.numberOfExplorationCycles = numberOfExplorationCycles;
	this.numberOfExploitationCycles = numberOfExploitationCycles;
	this.function = function;
	this.bestSolutions = new ArrayList<Double>();
    }

    public void doFoodSearch()
    {
	int remainingExplorationCycles = this.numberOfExplorationCycles;
	
	while(remainingExplorationCycles > 0)
	{
	    this.beehive.initializeBeehive(this.function.getBottomDomainLimit(), this.function.getTopDomainLimit());
	    this.beehive.allocateOnlookerBees(this.function);
	    
	    for(ScoutBee scoutBee: this.beehive.getScoutBees())
	    {
		scoutBee.updateAllocatedOnlookerBeesPositions(this.function);
		//TODO Descobrir como/se será preciso paralelizar
	    }
	    
	    remainingExplorationCycles--;
	}
    }

    public Beehive getBeehive()
    {
	return beehive;
    }

    public void setBeehive(Beehive beehive)
    {
	this.beehive = beehive;
    }

    public int getNumberOfExplorationCycles()
    {
	return numberOfExplorationCycles;
    }

    public void setNumberOfExplorationCycles(int numberOfExplorationCycles)
    {
	this.numberOfExplorationCycles = numberOfExplorationCycles;
    }

    public int getNumberOfExploitationCycles()
    {
	return numberOfExploitationCycles;
    }

    public void setNumberOfExploitationCycles(int numberOfExploitationCycles)
    {
	this.numberOfExploitationCycles = numberOfExploitationCycles;
    }

    public Function getFunction()
    {
	return function;
    }

    public void setFunction(Function function)
    {
	this.function = function;
    }

    public List<Double> getBestSolutions()
    {
	return bestSolutions;
    }

    public void setBestSolutions(List<Double> bestSolutions)
    {
	this.bestSolutions = bestSolutions;
    }
}
