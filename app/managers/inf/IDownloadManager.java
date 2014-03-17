package managers.inf;

import models.File;
import models.User;

public interface IDownloadManager {

	String generateUrl(String uid);

	File getFile(String tempurl, User user);

}
