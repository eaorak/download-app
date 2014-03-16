package controllers;

import play.mvc.Before;

/**
 * @author ender
 */
public class Files extends CRUD {
	@Before
	protected static void check() {
		BaseController.authorize();
	}
}
