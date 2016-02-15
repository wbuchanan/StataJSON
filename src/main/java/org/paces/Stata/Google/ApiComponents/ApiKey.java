package org.paces.Stata.Google.ApiComponents;

/**
 * @author Billy Buchanan
 */
public class ApiKey {

	private static String key;

	ApiKey(final String keyval) {
		if (!keyval.isEmpty()) key = "key=" + keyval;
		else key = "";
	}

	protected String getKey() {
		return this.key;
	}

}
