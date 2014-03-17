package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import models.cons.Role;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * @author ender
 */
@Entity(name = "users")
public class User extends BaseModel {

	private String login;
	private String name;
	private String pass;
	private Role role;
	@ManyToMany(cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "user_files", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "file_id") })
	private List<File> files = new ArrayList<File>();

	public User() {
	}

	public User(String login, String name, String pass, Role role) {
		this.login = login;
		this.name = name;
		this.pass = pass;
		this.role = role;
	}

	public void addFile(File file) {
		files.add(file);
	}

	public boolean fileExist(File file) {
		for (File f : files) {
			if (f.getUid().equals(file.getUid())) {
				return true;
			}
		}
		return false;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", name=" + name + ", role=" + role + "]";
	}

}
