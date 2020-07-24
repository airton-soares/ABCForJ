package com.github.abcforj.beehive;

import java.util.ArrayList;
import java.util.List;

import com.github.abcforj.bee.OnlookerBee;
import com.github.abcforj.bee.ScoutBee;
import com.github.abcforj.function.Function;

public class Beehive {
    private List<OnlookerBee> onlookerBees;
    private List<ScoutBee> scoutBees;
    private int dimension;

    public Beehive(int beehiveSize, float scoutBeesProportion, int dimension) {
        int numberOfScoutBees = Math.round(beehiveSize * scoutBeesProportion);
        int numberOfOnlookerBees = beehiveSize - numberOfScoutBees;

        this.onlookerBees = new ArrayList<OnlookerBee>(numberOfOnlookerBees);
        this.scoutBees = new ArrayList<ScoutBee>(numberOfScoutBees);

        for(int i = 0; i < numberOfOnlookerBees; i++) {
            this.onlookerBees.add(new OnlookerBee());
        }

        for(int i = 0; i < numberOfScoutBees; i++) {
            this.scoutBees.add(new ScoutBee());
        }

        this.dimension = dimension;
    }

    public void initializeBeehive(double bottomDomainLimit, double topDomainLimit) {
        for (int i = 0; i < this.scoutBees.size(); i++) {
            this.scoutBees.set(i, new ScoutBee(bottomDomainLimit, topDomainLimit, this.dimension));
        }

        for (int i = 0; i < this.onlookerBees.size(); i++) {
            this.onlookerBees.set(i, new OnlookerBee(this.dimension));
        }
    }

    public void allocateOnlookerBees(Function function) {
        double fitnessSum = 0;
        int onlookerBeesInitialSize = this.onlookerBees.size();

        for (int i = 0; i < this.scoutBees.size(); i++) {
            if (this.scoutBees.get(i) != null) {
                double currentFitness = function.fitness(this.scoutBees.get(i).getCurrentPosition());

                if (currentFitness < 0) {
                    currentFitness = 1 + Math.abs(currentFitness);
                }
                else {
                    currentFitness = 1 / (1 + currentFitness);
                }

                fitnessSum += currentFitness;
            }
        }

        for (int i = 0; i < this.scoutBees.size(); i++) {
            if (this.scoutBees.get(i) != null) {
                double currentFitness = function.fitness(this.scoutBees.get(i).getCurrentPosition());

                if (currentFitness < 0) {
                    currentFitness = 1 + Math.abs(currentFitness);
                }
                else {
                    currentFitness = 1 / (1 + currentFitness);
                }

                double allocationProbability = currentFitness / fitnessSum;
                int numberOfAllocatedOnlookerBees = (int) Math.round(onlookerBeesInitialSize * allocationProbability);

                if(this.onlookerBees.size() < numberOfAllocatedOnlookerBees) {
                    numberOfAllocatedOnlookerBees = this.onlookerBees.size();
                }

                List<OnlookerBee> allocatedOnlookerBees = this.onlookerBees.subList(0, numberOfAllocatedOnlookerBees);
                this.scoutBees.get(i).addOnlookerBees(allocatedOnlookerBees);
                this.onlookerBees = this.onlookerBees.subList(numberOfAllocatedOnlookerBees, this.onlookerBees.size());
            }
        }
    }

    public List<OnlookerBee> getOnlookerBees() {
        return onlookerBees;
    }

    public void setOnlookerBees(List<OnlookerBee> onlookerBees) {
        this.onlookerBees = onlookerBees;
    }

    public List<ScoutBee> getScoutBees() {
        return scoutBees;
    }

    public void setScoutBees(List<ScoutBee> scoutBees) {
        this.scoutBees = scoutBees;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
}
