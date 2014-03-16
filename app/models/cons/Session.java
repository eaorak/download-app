package models.cons;

import models.User;

/**
 * @author ender
 */
public class Session {

	private User user;
	private String sessionId;
	private String duration;

	public Session(User user, String sessionId, String duration) {
		this.user = user;
		this.sessionId = sessionId;
		this.duration = duration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Session [user=" + user + ", sessionId=" + sessionId + ", duration=" + duration + "]";
	}
}
