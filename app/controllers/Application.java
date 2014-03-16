package controllers;

import java.util.List;

import models.File;
import models.User;
import models.cons.Role;
import models.cons.Session;

/**
 * @author ender
 */
public class Application extends BaseController {

	public static void index() {
		Session session = session();
		if (session.getUser().getRole() == Role.ADMIN) {
			redirect("/admin");
		}
		List<File> files = session.getUser().getFiles();
		User user = session.getUser();
		render(files, user);
	}
}