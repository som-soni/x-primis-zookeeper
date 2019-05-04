package x.primis.zk.curator.lock;

import java.util.concurrent.locks.ReentrantLock;

public class DistributedResource {

	ReentrantLock lock = new ReentrantLock();

	public void use() throws InterruptedException {
		if (!lock.tryLock()) {
			throw new InterruptedException(
					"Resource already in use by another client. Example not working as expected");
		} else {
			try {
				Thread.sleep(2000);
			} finally {
				lock.unlock();
			}

		}

	}
}
