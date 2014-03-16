package models.cons;

import models.File;

/**
 * @author ender
 */
public class MemFile {

	private File file;
	private String tempurl;

	public MemFile(File file, String tempurl) {
		this.file = file;
		this.tempurl = tempurl;
	}

	public File getFile() {
		return file;
	}

	public String getTempurl() {
		return tempurl;
	}

}
