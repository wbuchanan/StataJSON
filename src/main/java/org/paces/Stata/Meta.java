package org.paces.Stata;

import com.stata.sfi.SFIToolkit;

import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Meta Class Object</h2>
 * <p>Class used for Stata's Java API to access dataset Metadata. </p>
 */
public class Meta {

	// Observations metadata object
	public static Observations stataobs;

	// Variables metadata object
	public static Variables statavars;

	// Variable index metadata object
	public static List<Integer> varindex;

	// Observation index metadata object
	public static List<Long> obsindex;

	public static void main(String[] args) {
		new Meta(args);
	}

	/***
	 * Generic constructor when args are passed from javacall
	 * @param args String array to transform into collector object
	 */
	public Meta(String[] args){

		// Create a new observations object
		setStataobs();

		List<String> newargs;

		if (args.length > 0) {

			newargs = StataArgsToCollector.argsToCollector(args);

			// Create a new variables object
			setStatavars(newargs);

		} else {

			// Create a new variables object
			setStatavars();
		}

		// Create a new variable index object
		setVarindex(statavars);

		// Create a new observation index object
		setObsindex(stataobs);

	}

	/*

	to JSON should be formatted as

	[
		{
			"var" : value,
			"var" : "value"
		},
		{
			"var" : value,
			"var" : "value"
		}
	]

	 */


	/***
	 * Generic setter method for observations member variable
	 */
	public static void setStataobs() {

		// Initialize a new observations object
		stataobs = new Observations();

	} // End setter method for observations member variable

	/***
	 * Generic setter method for variables member variable
	 */
	public static void setStatavars() {

		// Initialize a new variables metadata object
		statavars = new Variables();

	} // End setter method for variables metadata member variable

	/***
	 * Generic setter method for variables member variable
	 */
	public static void setStatavars(List<String> args) {

		// Initialize a new variables metadata object
		statavars = new Variables(args);

	} // End setter method for variables metadata member variable

	/***
	 * Sets teh observation index member variable
	 * @param observations An observations class object
	 */
	public static void setObsindex(Observations observations) {

		// Initialize a new observation index object
		obsindex = observations.getObservationIndex();

	} // End setter method for observation index member variable

	/***
	 * Sets the variable index member variable
	 * @param variables A variables class object
	 */
	public static void setVarindex(Variables variables) {

		// Initialize a new variable index object
		varindex = variables.getVariableIndex();

	} // End setter method for variable index member variable

	/***
	 * Getter for the observations member variable
	 * @return Returns the observation member variable
	 */
	public static Observations getStataobs() { return stataobs; }

	/***
	 * Getter for the variables member variable
	 * @return Returns the variables member variable
	 */
	public static Variables getStatavars() { return statavars; }

	/***
	 * Getter for the observation index member variable
	 * @return Returns the observation index member variable
	 */
	public static List<Long> getObsindex() { return obsindex; }

	/***
	 * Getter for the variable index member variable
	 * @return Returns the variable index member variable
	 */
	public static List<Integer> getVarindex() { return varindex; }

	public static int printvarnames(String[] args) {
		for (Integer i : varindex) {
			SFIToolkit.displayln(statavars.getName(i));
		}
		return 0;
	}

	public static int printvarlabels(String[] args) {

		statavars.getVariableLabels().forEach(SFIToolkit::displayln);

		return 0;

	}

	public static int printvalueLabelnames(String[] args) {

		statavars.getValueLabelNames().forEach(SFIToolkit::displayln);

		return 0;

	}

	public static int printvaluelabels(String[] args) {

		for (Map<Integer, String> vallabel : statavars.getValueLabels()) {

			vallabel.values().forEach(SFIToolkit::displayln);

		}

		return 0;

	}

}
