package models.cons;

/**
 * @author ender
 */
public enum Role {

	ADMIN("-"), //
	USER("/admin");

	private String restricted;

	private Role(String restricted) {
		this.restricted = restricted;
	}

	public boolean allowed(String url) {
		if (url.startsWith(restricted)) {
			return false;
		}
		return true;
	}

}
