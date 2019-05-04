package x.primis.zk.curator.lock;

import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import java.util.logging.*;

public class DistributedClient {

	private String id;
	private InterProcessMutex lock;
	private DistributedResource resource;
	private Logger logger = Logger.getLogger(DistributedClient.class.getName());

	public DistributedClient(String id, CuratorFramework client, String lockPath, DistributedResource resource) {
		this.id = id;
		this.resource = resource;
		lock = new InterProcessMutex(client, lockPath);
	}

	public void execute(long maxWait) throws Exception {
		if (this.lock.acquire(maxWait, TimeUnit.MILLISECONDS)) {
			try {
				// do some work inside of the critical section here
				logger.info("Lock acquired by client " + this.id + " waiting for 2 second.");
				resource.use();
			} finally {
				logger.info("Lock released by client " + this.id);
				lock.release();
			}
		}
	}

}
