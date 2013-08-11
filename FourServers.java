import java.util.LinkedList;
import edu.rit.sim.Event;
import edu.rit.sim.Simulation;
import edu.rit.util.Random;

public class FourServers extends Generator {
	private Server server_B1;
	private Server server_B2;
	private Server server_B3;
	private Server server_B4;
	private LinkedList<Request> gridQueueB;

	/**
	 * Create a new four-server request generator.
	 * 
	 * @param sim
	 *            Simulation.
	 * @param treq
	 *            Request mean interarrival time.
	 * @param nreq
	 *            Number of requests.
	 * @param prng
	 *            Pseudo random number generator.
	 * @param server_B1
	 *            Server.
	 * @param server_B2
	 *            Server.
	 * @param server_B3
	 *            Server.
	 * @param server_B4
	 *            Server.
	 */
	public FourServers(Simulation sim, double treq, int nreq, Random prng,
			Server server_B1, Server server_B2, Server server_B3,
			Server server_B4) {
		super(sim, treq, nreq, prng);
		this.server_B1 = server_B1;
		this.server_B2 = server_B2;
		this.server_B3 = server_B3;
		this.server_B4 = server_B4;
		gridQueueB = new LinkedList<Request>();
		generateRequest();
	}

	/**
	 * Generate the next request.
	 */
	protected void generateRequest() {
		Request req = new Request(sim, respTimeSeries);
		gridQueueB.add(req);
		if (server_B1.isIdle()) {
			if (gridQueueB.size() > 0) {
				server_B1.startProcessing(gridQueueB);
			}
		}
		if (server_B2.isIdle()) {
			if (gridQueueB.size() > 0) {
				server_B2.startProcessing(gridQueueB);
			}
		}
		if (server_B3.isIdle()) {
			if (gridQueueB.size() > 0) {
				server_B3.startProcessing(gridQueueB);
			}
		}
		if (server_B4.isIdle()) {
			if (gridQueueB.size() > 0) {
				server_B4.startProcessing(gridQueueB);
			}
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