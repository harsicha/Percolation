import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;
//        private Stopwatch stopwatch;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
//        stopwatch = new Stopwatch();
        double[] thresholds = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                percolation.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
            }
            double threshold = 1.0 * percolation.numberOfOpenSites() / (n * n);
        //    System.out.println(this.threshold);
            thresholds[i] = threshold;
        }
        this.mean = StdStats.mean(thresholds);
        this.stddev = StdStats.stddev(thresholds);
        this.confidenceLo = this.mean - (CONFIDENCE_95 * this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean + (CONFIDENCE_95 * this.stddev / Math.sqrt(trials));
//        System.out.println(stopwatch.elapsedTime());
    }
    public double mean() {
        return this.mean;
    }
    public double stddev() {
        return this.stddev;
    }
    public double confidenceLo() {
        return this.confidenceLo;
    }
    public double confidenceHi() {
        return this.confidenceHi;
    }
    public static void main(String[] args) {
        PercolationStats percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + percolationStats.mean());
        System.out.println("stddev                  = " + percolationStats.stddev());
        System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
