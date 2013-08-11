import edu.rit.numeric.ListSeries;
import edu.rit.sim.Simulation;

public class Request {
	private static int requestIdCounter = 0;
	private int requestId;
	private Simulation sim;
	private double startTime;
	private double finishTime;
	private ListSeries responseTimeSeries;

	/**
	 * Construct a new request of Class Request. The request's start time is set
	 * to the current simulation time. The request's response time will be
	 * recorded in the given series of type ListSeries.
	 * 
	 * @param sim
	 *            Simulation.
	 * @param series
	 *            Response time series.
	 */
	public Request(Simulation sim, ListSeries series) {
		this.requestId = ++requestIdCounter;
		this.sim = sim;
		this.startTime = sim.time();
		this.responseTimeSeries = series;
	}

	/**
	 * Marks the current request as finished. The request's finish time is set
	 * to the current simulation time. The request's response time is recorded
	 * in the response time series.
	 * 
	 * @return Status of the server. True if idle else false.
	 */
	public boolean finishProcessing() {
		this.finishTime = sim.time();
		if (this.responseTimeSeries != null)
			responseTimeSeries.add(responseTime());
		return true;
	}

	/**
	 * Returns the current request's response time.
	 ** 
	 * @return Response time.
	 */
	public double responseTime() {
		return (this.finishTime - this.startTime);
	}
}