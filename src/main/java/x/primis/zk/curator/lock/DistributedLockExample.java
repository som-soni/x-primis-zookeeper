package x.primis.zk.curator.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class DistributedLockExample {

	private static final int CLIENTS = 5;
	private static final int REP = 10;

	private static final String CONNECTION_STRING = "localhost:2181";

	private static final String PATH = "/xprimis/examples/locks";

	public static void main(String[] args) throws Exception {

		final DistributedResource distributedResource = new DistributedResource();
		ExecutorService service = Executors.newFixedThreadPool(CLIENTS);

		for (int i = 1; i <= CLIENTS; i++) {
			final int index = i;

			Runnable task = () -> {
				CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECTION_STRING,
						new ExponentialBackoffRetry(1000, 3));

				try {
					client.start();
					DistributedClient example = new DistributedClient(Integer.toString(index), client, PATH,
							distributedResource);
					for (int j = 0; j < REP; ++j) {
						example.execute(5000);
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (Exception e) {
					e.printStackTrace();
					// log or do something
				}
			};
			service.submit(task);
		}
		service.shutdown();
		service.awaitTermination(10, TimeUnit.MINUTES);
	}
}
