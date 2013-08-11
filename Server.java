import java.util.LinkedList;
import edu.rit.numeric.ExponentialPrng;
import edu.rit.sim.Event;
import edu.rit.sim.Simulation;
import edu.rit.util.Random;

public class Server {
	private String serverName;
	private Simulation sim;
	private ExponentialPrng tprocPrng;
	public boolean idle;

	/**
	 * Construct a new Grid Computing Server. The server's request processing
	 * time is exponentially distributed with the given mean.
	 * 
	 * @param serverName
	 *            Server name
	 * @param sim
	 *            Simulation
	 * @param tproc
	 *            Mean request processing time
	 * @param prng
	 *            Pseudo random number generator
	 */
	public Server(String serverName, Simulation sim, double tproc, Random prng) {
		this.idle = true;
		this.serverName = serverName;
		this.sim = sim;
		this.tprocPrng = new ExponentialPrng(prng, 1.0 / tproc);
	}

	/**
	 * Starts processing the first request in this server's queue. Sets the
	 * status of server to idle when it starts processing and to false when it *
	 * finishes.
	 * 
	 * @param Server
	 *            queue.
	 */
	public void startProcessing(final LinkedList<Request> serverQueue) {
		this.idle = false;
		final Request request = serverQueue.removeFirst();
		sim.doAfter(tprocPrng.next(), new Event() {
			public void perform() {
				idle = request.finishProcessing();
			}
		});
	}

	/**
	 * Returns the status of the server.
	 * 
	 * @return Status of server
	 */
	public boolean isIdle() {
		return idle;
	}
}