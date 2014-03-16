package utils;

import java.util.UUID;

/**
 * @author ender
 */
public class Util {

	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
