package controllers;

import play.mvc.Before;

/**
 * @author ender
 */
public class Users extends CRUD {
	@Before
	protected static void check() {
		BaseController.authorize();
	}
}
