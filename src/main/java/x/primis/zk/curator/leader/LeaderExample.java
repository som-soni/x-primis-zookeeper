package x.primis.zk.curator.leader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class LeaderExample {

	private static final int CLIENTS = 5;
	private static final String CONNECTION_STRING = "localhost:2181";

	private static final String PATH = "/xprimis/examples/leaders";

	public static void main(String[] args) throws IOException {
		for (int i = 1; i <= CLIENTS; i++) {
			CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECTION_STRING,
					new ExponentialBackoffRetry(1000, 3));
			try {
				client.start();
				LeaderClient example = new LeaderClient(Integer.toString(i), client, PATH);
				example.start();

			} catch (Exception e) {
				e.printStackTrace();
				// log or do something
			}

		}

		System.out.println("Press enter/return to quit\n");
		new BufferedReader(new InputStreamReader(System.in)).readLine();

	}

}
