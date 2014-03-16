package models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * @author ender
 */
@Entity(name = "downloads")
public class Download extends BaseModel {

	@OneToOne
	private File file;
	@OneToOne
	private User user;
	private long time;
	private String tempurl;

	public Download() {
		time = System.currentTimeMillis();
	}

	public Download(File file, User user, String tempurl) {
		this();
		this.file = file;
		this.user = user;
		this.tempurl = tempurl;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTempurl() {
		return tempurl;
	}

	public void setTempurl(String tempurl) {
		this.tempurl = tempurl;
	}

}
