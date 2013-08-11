import java.awt.Color;
import java.text.DecimalFormat;
import edu.rit.numeric.AggregateXYSeries;
import edu.rit.numeric.ListSeries;
import edu.rit.numeric.Series;
import edu.rit.numeric.Statistics;
import edu.rit.numeric.plot.Plot;
import edu.rit.sim.Simulation;
import edu.rit.util.Random;

public class GridComputingSim {
	private static double procTime;
	private static double requestLowerBound;
	private static double requestUpperBound;
	private static double requestRate;
	private static int numberOfRequests;
	private static long seed;
	private static ListSeries requestSeries;
	private static ListSeries meanRespTimeOne;
	private static ListSeries meanRespTimeTwo;
	private static Random prng;
	private static Simulation sim;
	private static Server server_A1;
	private static Server server_A2;
	private static Server server_B1;
	private static Server server_B2;
	private static Server server_B3;
	private static Server server_B4;
	private static Generator generator;
	private static Series.Stats stats;
	private static double responseMean;
	private static Series trespSeriesOne;
	private static Series trespSeriesTwo;

	/**
	 * Main program to simulate the 2 Grid Computing Systems A and B.
	 */
	public static void main(String args[]) {
		// Parses the command line arguments
		if (args.length != 6)
			usage();
		procTime = Double.parseDouble(args[0]);
		requestLowerBound = Double.parseDouble(args[1]);
		requestUpperBound = Double.parseDouble(args[2]);
		requestRate = Double.parseDouble(args[3]);
		numberOfRequests = Integer.parseInt(args[4]);
		seed = Long.parseLong(args[5]);
		// Sets up the pseudo random number generator
		prng = Random.getInstance(seed);
		// Sets up the series for plotting data
		requestSeries = new ListSeries();
		meanRespTimeOne = new ListSeries();
		meanRespTimeTwo = new ListSeries();
		// Print labels for the console output
		System.out.println("");
		System.out.println("\ttReq\tMean-A\tMean-B\tt-val\tp-val");
		// Repeat for treq = requestLowerBound to requestUpperBound by
		// requestRate.
		double treq;
		for (int i = 0; (treq = requestLowerBound + i * requestRate) <= requestUpperBound; ++i) {
			System.out.printf("\t%.3f", treq);
			requestSeries.add(treq);
			// Simulate Grid System A with 2 Servers
			sim = new Simulation();
			server_A1 = new Server("A1", sim, procTime, prng);
			server_A2 = new Server("A2", sim, procTime, prng);
			generator = new TwoServers(sim, treq, numberOfRequests, prng,
					server_A1, server_A2);
			sim.run();
			stats = generator.responseTimeStats();
			responseMean = stats.mean;
			System.out.printf("\t%.3f", responseMean);
			meanRespTimeOne.add(responseMean);
			trespSeriesOne = generator.responseTimeSeries();
			// Simulate Grid System B with 4 Servers
			sim = new Simulation();
			server_B1 = new Server("B1", sim, procTime, prng);
			server_B2 = new Server("B2", sim, procTime, prng);
			server_B3 = new Server("B3", sim, procTime, prng);
			server_B4 = new Server("B4", sim, procTime, prng);
			generator = new FourServers(sim, treq, numberOfRequests, prng,
					server_B1, server_B2, server_B3, server_B4);
			sim.run();
			stats = generator.responseTimeStats();
			responseMean = stats.mean;
			System.out.printf("\t%.3f", responseMean);
			meanRespTimeTwo.add(responseMean);
			trespSeriesTwo = generator.responseTimeSeries();
			// Do an unequal-variance t-test to see if the Grid System A's mean
			// response time is the same as the Grid System B's mean response
			// time.
			double[] ttest = Statistics.tTestUnequalVariance(trespSeriesOne,
					trespSeriesTwo);
			System.out.printf("\t%.3f\t%.3f%n", ttest[0], ttest[1]);
		}
		// Plot mean response time versus mean interarrival time.
		new Plot()
				.rightMargin(36)
				.xAxisTitle("Mean interarrival time")
				.xAxisTickFormat(new DecimalFormat("0.0"))
				.yAxisTitle("Mean response time")
				.yAxisTickFormat(new DecimalFormat("0.0"))
				.seriesDots(null)
				.seriesColor(Color.GREEN)
				.xySeries(new AggregateXYSeries(requestSeries, meanRespTimeOne))
				.seriesColor(Color.RED)
				.xySeries(new AggregateXYSeries(requestSeries, meanRespTimeTwo))
				.labelPosition(Plot.RIGHT)
				.labelOffset(6)
				.labelColor(Color.BLUE)
				.label("Grid System A",
						requestSeries.x(requestSeries.length() - 1),
						meanRespTimeOne.x(meanRespTimeOne.length() - 1))
				.labelColor(Color.RED)
				.label("Grid System B",
						requestSeries.x(requestSeries.length() - 1),
						meanRespTimeTwo.x(meanRespTimeTwo.length() - 1))
				.getFrame().setVisible(true);
	}

	/**
	 * Prints a usage message if the number os arguments are not equal to 6 and
	 * exits.
	 */
	public static void usage() {
		System.err
				.println("Usage: java GridComputingSim <procTime> <requestLowerBound> <requestUpperBound> <requestRate> <numberOfRequests> <seed>");
		System.err.println("<procTime> = Mean request processing time");
		System.err
				.println("<requestLowerBound> = Mean request interarrival time lower bound");
		System.err
				.println("<requestUpperBound> = Mean request interarrival time upper bound");
		System.err
				.println("<requestRate> = Mean request interarrival time rate");
		System.err.println("<numberOfRequests> = Number of requests");
		System.err.println("<seed> = Random seed");
		System.exit(1);
	}
}