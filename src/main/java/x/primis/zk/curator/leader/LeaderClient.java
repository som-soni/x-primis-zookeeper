package x.primis.zk.curator.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;

public class LeaderClient extends LeaderSelectorListenerAdapter {
	private final String id;
	private static final int SLEEP = 2000;
	private final LeaderSelector leaderSelector;

	public LeaderClient(String id, CuratorFramework client, String path) {
		this.id = id;
		this.leaderSelector = new LeaderSelector(client, path, this);
		this.leaderSelector.autoRequeue();
	}

	public void start() {
		this.leaderSelector.start();
	}

	@Override
	public void takeLeadership(CuratorFramework client) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Client " + this.id + " is now the leader. Sleeping for " + SLEEP + " milliseconds");
		try {
			Thread.sleep(SLEEP);
		} finally {
			System.out.println("Client " + this.id + " is now relinquishing leadership.");
		}
	}

}
