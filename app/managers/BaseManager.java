package managers;

/**
 * @author ender
 */
public class BaseManager {

	protected volatile boolean initialized;

	public void initialize() {
	}

	public synchronized final void doInitialize() {
		if (initialized) {
			return;
		}
		initialize();
		initialized = true;
	}

	/**
	 * 0-10, Most-Least Important, respectively.
	 */
	public int priority() {
		return 5;
	}

}
