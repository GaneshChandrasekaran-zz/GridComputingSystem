import java.util.LinkedList;
import edu.rit.sim.Event;
import edu.rit.sim.Simulation;
import edu.rit.util.Random;

public class TwoServers extends Generator {
	private Server server_A1;
	private Server server_A2;
	private LinkedList<Request> gridQueueA;

	/**
	 * Create a new two-server request generator.
	 * 
	 * @param sim
	 *            Simulation.
	 * @param treq
	 *            Request mean interarrival time.
	 * @param nreq
	 *            Number of requests.
	 * @param prng
	 *            Pseudo random number generator.
	 * @param server_A1
	 *            Server.
	 * @param server_A2
	 *            Server.
	 */
	public TwoServers(Simulation sim, double treq, int nreq, Random prng,
			Server server_A1, Server server_A2) {
		super(sim, treq, nreq, prng);
		this.server_A1 = server_A1;
		this.server_A2 = server_A2;
		gridQueueA = new LinkedList<Request>();
		generateRequest();
	}

	/**
	 * Generate the next request.
	 */
	protected void generateRequest() {
		Request req = new Request(sim, respTimeSeries);
		gridQueueA.add(req);
		if (server_A1.isIdle()) {
			if (gridQueueA.size() > 0)
				server_A1.startProcessing(gridQueueA);
		}
		if (server_A2.isIdle()) {
			if (gridQueueA.size() > 0)
				server_A2.startProcessing(gridQueueA);
		}
		++n;
		if (n < nreq) {
			sim.doAfter(treqPrng.next(), new Event() {
				public void perform() {
					generateRequest();
				}
			});
		}
	}
}