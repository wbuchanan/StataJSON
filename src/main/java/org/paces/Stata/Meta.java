package org.paces.Stata;

import org.paces.StataMetaData.StataObsImpl;
import org.paces.StataMetaData.StataVarsImpl;

import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Meta Class Object</h2>
 * <p>Class used for Stata's Java API to access dataset Metadata. </p>
 */
public class Meta {

	// Observations metadata object
	public static StataObsImpl stataobs;

	// Variables metadata object
	public static StataVarsImpl statavars;

	// Variable index metadata object
	public static List<Integer> varindex;

	// Observation index metadata object
	public static List<Long> obsindex;

	// Construct a new Meta class object
	public static void main(String[] args) {

		// Create a new observations object
		setStataobs();

		// Create a new variables object
		setStatavars();

		// Create a new variable index object
		setVarindex(statavars);

		// Create a new observation index object
		setObsindex(stataobs);

	} // End generic main method

	/***
	 * Generic setter method for observations member variable
	 * @return An integer value required by the Stata Java API.  Value not used.
	 */
	public static int setStataobs() {

		// Initialize a new observations object
		stataobs = new StataObsImpl();

		// Return a value to satisfy the Stata API
		return 0;

	} // End setter method for observations member variable

	/***
	 * Generic setter method for variables member variable
	 * @return An integer value required by the Stata Java API.  Value not used.
	 */
	public static int setStatavars() {

		// Initialize a new variables metadata object
		statavars = new StataVarsImpl();

		// Return a value to satisfy the Stata API
		return 0;

	} // End setter method for variables metadata member variable

	/***
	 * Sets teh observation index member variable
	 * @param observations An observations class object
	 * @return An integer value required by the Stata Java API.  Value not used.
	 */
	public static int setObsindex(StataObsImpl observations) {

		// Initialize a new observation index object
		obsindex = observations.getObservationIndex();

		// Return a value to satisfy the Stata API
		return 0;

	} // End setter method for observation index member variable

	/***
	 * Sets the variable index member variable
	 * @param variables A variables class object
	 * @return An integer value required by the Stata Java API.  Value not used.
	 */
	public static int setVarindex(StataVarsImpl variables) {

		// Initialize a new variable index object
		varindex = variables.getVariableIndex();

		// Return a value to satisfy the Stata API
		return 0;

	} // End setter method for variable index member variable

	/***
	 * Getter for the observations member variable
	 * @return Returns the observation member variable
	 */
	public static StataObsImpl getStataobs() { return stataobs; }

	/***
	 * Getter for the variables member variable
	 * @return Returns the variables member variable
	 */
	public static StataVarsImpl getStatavars() { return statavars; }

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


}
