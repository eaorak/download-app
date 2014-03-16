package controllers;

import java.net.URL;

import managers.DownloadManager;
import managers.M;
import managers.PersistenceManager;
import models.Download;
import models.File;
import models.User;

/**
 * @author ender
 */
public class DownloadController extends BaseController {

	private static PersistenceManager pem = M.get(PersistenceManager.class);
	private static DownloadManager dom = M.get(DownloadManager.class);

	public static void generate(String id) throws Exception {
		String tempurl = dom.generateUrl(id);
		// Redirects to download with temporary link
		download(tempurl);
	}

	public static void download(String url) throws Exception {
		File file = dom.getFile(url, session().getUser());
		if (file == null) {
			error("Invalid download URL.");
		}
		URL realUrl = new URL(file.getUrl());
		response.contentType = file.getType();
		User user = session().getUser();
		Download download = new Download(file, user, url);
		pem.save(download);
		renderBinary(realUrl.openStream());
	}

}
