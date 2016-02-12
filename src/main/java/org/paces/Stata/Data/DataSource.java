package org.paces.Stata.Data;

import com.stata.sfi.Macro;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * Class with Static Methods to retrive data about the Stata data
 */
public class DataSource {

	/***
	 * Method to get the Stata data source from a local macro named filename
	 * @return A string containing the name of the file from which the data
	 * were derived or Stata Data.
	 */
	public static String get() {

		// Store the value of `"`c(filename)'"' as a Java string
		String nm;

		// If the dataset name is not empty
		if (!Macro.getLocalSafe("filename").isEmpty()) {

			// Assign the Stata file name as the name of the object
			nm = Macro.getLocalSafe("filename");

		} else {

			// Otherwise set a generic name
			nm = "Stata Data";

		} // End IF/ELSE Block for object name

		return nm;

	} // End Method declaration

}
