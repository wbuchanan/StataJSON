package org.paces.Stata;

import com.stata.sfi.Data;

import java.util.HashMap;
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
	public Observations stataobs;

	// Variables metadata object
	public Variables statavars;

	// Variable index metadata object
	public List<Integer> varindex;

	// Observation index metadata object
	public List<Long> obsindex;

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
	public void setStataobs() {

		// Initialize a new observations object
		stataobs = new Observations();

	} // End setter method for observations member variable

	/***
	 * Generic setter method for variables member variable
	 */
	public void setStatavars() {

		// Initialize a new variables metadata object
		statavars = new Variables();

	} // End setter method for variables metadata member variable

	/***
	 * Generic setter method for variables member variable
	 */
	public void setStatavars(List<String> args) {

		// Initialize a new variables metadata object
		statavars = new Variables(args);

	} // End setter method for variables metadata member variable

	/***
	 * Sets teh observation index member variable
	 * @param observations An observations class object
	 */
	public void setObsindex(Observations observations) {

		// Initialize a new observation index object
		obsindex = observations.getObservationIndex();

	} // End setter method for observation index member variable

	/***
	 * Sets the variable index member variable
	 * @param variables A variables class object
	 */
	public void setVarindex(Variables variables) {

		// Initialize a new variable index object
		varindex = variables.getVariableIndex();

	} // End setter method for variable index member variable

	/***
	 * Getter for the observations member variable
	 * @return Returns the observation member variable
	 */
	public Observations getStataobs() { return stataobs; }

	/***
	 * Getter for the variables member variable
	 * @return Returns the variables member variable
	 */
	public Variables getStatavars() { return statavars; }

	/***
	 * Getter for the observation index member variable
	 * @return Returns the observation index member variable
	 */
	public List<Long> getObsindex() { return obsindex; }

	/***
	 * Getter for the variable index member variable
	 * @return Returns the variable index member variable
	 */
	public List<Integer> getVarindex() { return varindex; }

	/*
	public Map<String, ?> getRecord(Long obid, List<Integer> varidx) {

		// Initialize empty variable to store the variable name
		String key;

		// Initialize empty variable to store the value for a given
		// observation on a specified variable
		Object value;

		// Initialize empty container for key/value pairs
		Map<String, Object> record = new HashMap<>();

		// Loop over the variable indices
		for (Integer i : varidx) {

			// Set the variable name as the key in the map object
			key = statavars.getName(i);

			// Test for string/numeric
			if (statavars.getVarType(i)) {

				// Store string value if variable contains strings
				value = Data.getStr(i, obid);

			} else {

				// Convert numeric variables to string
				value = Data.getNum(i, obid);

			} // End IF/ELSE Block for string/numeric type handling

			// Add the key/value pair to the container object
			record.put(key, value);

		} // End of Loop

		// Return the object containing the observation ID and the key/value
		// pairs
		return (record);

	} // End of Method declaration
	*/

	public Object getRecord(Long obid) {

		// Initialize empty variable to store the variable name
		String key;

		// Initialize empty variable to store the value for a given
		// observation on a specified variable
		Object value;

		// Initialize empty container for key/value pairs
		Map<String, Object> record = new HashMap<>();

		// Loop over the variable indices
		for (Integer i : this.varindex) {

			// Set the variable name as the key in the map object
			key = statavars.getName(i);

			// Test for string/numeric
			if (statavars.getVarType(i)) {

				// Store string value if variable contains strings
				value = Data.getStr(i, obid);

			} else {

				// Convert numeric variables to string
				value = Data.getNum(i, obid);

			} // End IF/ELSE Block for string/numeric type handling

			// Add the key/value pair to the container object
			record.put(key, value);

		} // End of Loop

		// Return the object containing the observation ID and the key/value
		// pairs
		return (record);

	} // End of Method declaration

}
