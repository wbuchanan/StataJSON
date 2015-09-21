package org.paces.Stata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Convenience Method to Convert String[] args to List of String objects
 * that can use the Streaming API and other newer iterator methods in Java.
 * </h2>
 */
class StataArgsToCollector {

	/***
	 * Argument converter method
	 * @param args Value passed in args parameter of javacall
	 * @return A List of String objects
	 */
	public static List<String> argsToCollector(String[] args) {

		// Create local collector to be used to parse/store the macro
		List<String> tmp = new ArrayList<>();

		// Add all of the array elements to the collector
		Collections.addAll(tmp, args);

		// Return the List object
		return tmp;

	}

	/***
	 * Argument converter method
	 * @param macro A value returned from a call to Macro.getLocal, Macro
	 *                 .getGlobal, Macro.getLocalSafe, or Macro.getGlobalSafe
	 *                 .  The parameter is unchecked and does not handle any
	 *                 exceptions.
	 * @see com.stata.sfi.Macro
	 * @return A List of String objects
	 */
	public static List<String> argsToCollector(String macro) {

		// Create local collector to be used to parse/store the macro
		List<String> tmp = new ArrayList<>();

		// Create an array of Strings from the string value
		String[] varlist = macro.split(" ");

		// Add all of the array elements to the collector
		Collections.addAll(tmp, varlist);

		// Return the List object
		return tmp;

	}

}
