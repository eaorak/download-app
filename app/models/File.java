package models;

import javax.persistence.Entity;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Table;

import utils.Util;

/**
 * @author ender
 */
@Entity(name = "files")
@Table(appliesTo = "files", indexes = { @Index(name = "file_uid", columnNames = { "uid" }) })
public class File extends BaseModel {

	private String name;
	private String url;
	private String type;
	private String uid;

	public File() {
		uid = Util.uuid();
	}

	public File(String name, String url, String type) {
		this();
		this.name = name;
		this.url = url;
		this.type = type;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "File [name=" + name + "]";
	}

}
