package org.paces.Stata.Google.ApiComponents;

import java.util.*;

/**
 * Created by billy on 2/13/16.
 */
public class FixedOptions {

	private static Map<String, Set<String>> options = new HashMap<>();

	FixedOptions() {
		options.put("mode", travelModes());

	}

	public static Set<String> getOptionList(String s) {
		return options.get(s);
	}

	private Set<String> travelModes() {
		Set<String> m = new HashSet<>();
		m.add("driving");
		m.add("walking");
		m.add("bicycle");
		m.add("transit");
		return m;
	}

}
