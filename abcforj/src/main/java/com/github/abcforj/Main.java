package com.github.abcforj;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

import com.github.abcforj.function.*;

public class Main
{
    /**
     * @param args
     *            [0] -> Tamanho do enxame.
     * @param args
     *            [1] -> Proporção de abelhas exploradoras (0 - 1)
     * @param args
     *            [2] -> Número de ciclos de exploração.
     * @param args
     *            [3] -> Número de ciclos de refinamento premitidos sem
     *            melhoras.
     * @param args
     *            [4] -> Limite inferior da função.
     * @param args
     *            [5] -> Limite superior da função.
     * @param args
     *            [6] - Dimensão do espaço de busca
     */
    public static void main(String[] args)
    {
	if (args.length == 7)
	{
	    int beehiveSize = Integer.parseInt(args[0]);
	    float scoutBeesProportion = Float.parseFloat(args[1]);
	    int numberOfExplorationCycles = Integer.parseInt(args[2]);
	    int limitOfExploitationCycles = Integer.parseInt(args[3]);
	    double bottomDomainLimit = Double.parseDouble(args[4]);
	    double topDomainLimit = Double.parseDouble(args[5]);
	    int dimension = Integer.parseInt(args[6]);

	    ABC abc = new ABC(beehiveSize, scoutBeesProportion, numberOfExplorationCycles, limitOfExploitationCycles,
		    new RotatedRastriginFunction(bottomDomainLimit, topDomainLimit), dimension);
	    abc.doFoodSearch();
	    
	    double[] results = abc.getBestSolutions();
	    double[] iteractions = new double[results.length];

	    for (int i = 0; i < results.length; i++)
	    {
		iteractions[i] = i + 1;
	    }

	    Plot2DPanel plot = new Plot2DPanel();
	    plot.addLinePlot("Optimization result", iteractions, results);
	    JFrame frame = new JFrame("Optimization report");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
	    frame.setContentPane(plot);
	    frame.pack();
	    frame.setVisible(true);
	}
	else
	{
	    System.err.println("INVALID NUMBER OF ARGUMENTS!");
	}
    }
}
