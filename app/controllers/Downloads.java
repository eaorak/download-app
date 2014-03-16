package controllers;

import play.mvc.Before;

/**
 * @author ender
 */
public class Downloads extends CRUD {
	@Before
	protected static void check() {
		BaseController.authorize();
	}
}
