package org.paces.StataMetaData;
import com.stata.sfi.SFIToolkit;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Observations Class Object</h2>
 * <p>Class used for Stata's Java API to access the StataObsImpl class.</p>
 */

class Observations {

	// Initialize a new Observations class object
	static StataObsImpl stataobs  = new StataObsImpl();

	// Create wrappers for underlying methods
	public static int obprinter(String[] args) {

		String s = stataobs.getPrintme();
		if (s.equals("indices")) {

			// Print indices to console
			stataobs.print(stataobs.getObservationIndex());

			// Return success code
			return (0);

		} else if (s.equals("sobs")) {

			// Print the starting observation to the Stata console
			stataobs.print(stataobs.getSobs());

			// Return success code
			return (0);

		} else if (s.equals("eobs")) {

			// Print the ending observation to the Stata console
			stataobs.print(stataobs.getEobs());

			// Return success code
			return (0);

		} else if (s.equals("nobs")) {

			// Print the number of effective observations to the Stata console
			stataobs.print(stataobs.getNobs());

			// Return success code
			return (0);

		} else {

			// Print generic error message
			SFIToolkit.displayln("No observation object to print");

			// move through last test
			return (0);
		}

	} // End method definition for printing for the observations class

} // End Class definition
