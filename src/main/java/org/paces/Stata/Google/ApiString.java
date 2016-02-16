package org.paces.Stata.Google;

import java.util.StringJoiner;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class ApiString {

	public static String makeApiString() {
		StringJoiner apiString = new StringJoiner("&");

		return apiString.toString();
	}
}
