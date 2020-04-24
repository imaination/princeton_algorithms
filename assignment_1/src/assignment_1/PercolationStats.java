package assignment_1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] counts;
    private final int numTrials;
    private final static double CONFIDENCE_95 = 1.96;
    
    // perform independent trials on an n-by-n grid
    
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("illegal input");
        }
        counts = new double[trials];
        numTrials = trials;
        
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int j = StdRandom.uniform(1, n+1);
                int k = StdRandom.uniform(1, n+1);
                p.open(j, k);
            }
            counts[i] = (double) p.numberOfOpenSites()/(n*n);
        }
    }

    // sample mean of percolation threshold
    
    public double mean() {
        return StdStats.mean(counts);
    }

    // sample standard deviation of percolation threshold
    
    public double stddev() {
        return StdStats.stddev(counts);
    }

    // low endpoint of 95% confidence interval
    
    public double confidenceLo() {
        double miu = mean();
        double stddev = stddev();
        double ciLow;
        ciLow = miu - (CONFIDENCE_95 * stddev)/Math.sqrt(numTrials);
        return ciLow;
    }

    // high endpoint of 95% confidence interval
    
    public double confidenceHi() {
        double miu = mean();
        double stddev = stddev();
        double ciHigh;
        ciHigh = miu + (CONFIDENCE_95 * stddev)/Math.sqrt(numTrials);
        return ciHigh;
    }

    // test client (see below)
    
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, t);
        System.out.print("mean     = " + ps.mean());
        System.out.print("\n");
        System.out.print("stddev   = " + ps.stddev());
        System.out.print("\n");
        System.out.printf("95%% ci = [%f , %f]\n", ps.confidenceLo(), ps.confidenceHi());
    }

}
