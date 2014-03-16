package managers;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ender
 */
public class ThreadManager extends BaseManager {

	private final int POOL_SIZE = 10;
	private ExecutorService pool = Executors.newFixedThreadPool(POOL_SIZE);

	public ThreadManager() {
	}

	@Override
	public int priority() {
		return 0;
	}

	public void execute(Runnable r) {
		pool.execute(r);
	}

	public <V> Future<V> submit(Callable<V> task) {
		return pool.submit(task);
	}

}
