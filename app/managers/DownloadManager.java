package managers;

import java.util.concurrent.TimeUnit;

import managers.inf.IDownloadManager;
import managers.inf.IPersistenceManager;
import models.File;
import models.User;
import play.Logger;
import utils.Util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * @author ender
 */
public class DownloadManager extends BaseManager implements IDownloadManager, RemovalListener<String, File> {

	private final int EXPIRE = 10; // Minutes
	private Cache<String, File> files;
	private IPersistenceManager pem;

	public DownloadManager() {
	}

	@Override
	public void initialize() {
		pem = M.get(PersistenceManager.class);
		files = CacheBuilder.newBuilder() //
				.concurrencyLevel(4)//
				.maximumSize(10000)//
				.removalListener(this)//
				.expireAfterWrite(EXPIRE, TimeUnit.SECONDS)// TODO Change !!
				.build();
	}

	public String generateUrl(String uid) {
		File file = pem.findFirst(File.class, "uid=?", uid);
		if (file == null) {
			throw new RuntimeException("Invalid file id.");
		}
		String tempurl = Util.uuid();
		files.put(tempurl, file);
		return tempurl;
	}

	public File getFile(String tempurl, User user) {
		File file = files.getIfPresent(tempurl);
		if (user.fileExist(file)) {
			return file;
		}
		throw new RuntimeException("User is not authorized for file.");
	}

	@Override
	public void onRemoval(RemovalNotification<String, File> removal) {
		File file = removal.getValue();
		String key = removal.getKey();
		Logger.info("[DownloadManager.onRemoval] File [%s] with url [%s] removed from cache.", file, key);
	}

}
