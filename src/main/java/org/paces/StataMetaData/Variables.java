package org.paces.StataMetaData;

import com.stata.sfi.SFIToolkit;

import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Observations Class Object</h2>
 * <p>Class used for Stata's Java API to access the StataObsImpl class.</p>
 */
class Variables {

	// Initialize a new Observations class object
	public static StataVarsImpl statavars  = new StataVarsImpl();

	// Create a variable index object
	public static List<Integer> varindex = statavars.getVariableIndex();

	public static int printVarNames() {

		// List of variable names
		List<String> varnms = statavars.getVariableNames(varindex);

		// Loop over variable names
		for (String vdx : varnms) {

			// Print variable name to console
			SFIToolkit.displayln(vdx);

		} // End Loop over variable names

		return 0;

	}

	public static int printVarLabels() {

		// List of variable names
		List<String> varlabs = statavars.getVariableLabels(varindex);

		// Loop over variable names
		for (String vdx : varlabs) {

			// Print variable name to console
			SFIToolkit.displayln(vdx);

		} // End Loop over variable names

		return 0;

	}

}
