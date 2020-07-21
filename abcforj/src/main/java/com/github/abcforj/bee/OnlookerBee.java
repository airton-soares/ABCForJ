package com.github.abcforj.bee;

public class OnlookerBee
{
    private double[] currentPosition;
    private double[] bestPosition;

    public OnlookerBee(int dimension)
    {
        this.currentPosition = new double[dimension];
        this.bestPosition = new double[dimension];
    }

    public OnlookerBee() {}

    public double[] getCurrentPosition()
    {
        return currentPosition;
    }

    public void setCurrentPosition(double[] currentPosition)
    {
        this.currentPosition = currentPosition;
    }

    public double[] getBestPosition()
    {
        return bestPosition;
    }

    public void setBestPosition(double[] bestPosition)
    {
        this.bestPosition = bestPosition;
    }
}
