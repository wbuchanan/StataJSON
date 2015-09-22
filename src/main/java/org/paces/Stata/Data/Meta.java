package org.paces.Stata.Data;

import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Meta Class Object</h2>
 * <p>Class used for Stata's Java API to access dataset Metadata.
 * Initializes Observations and Variables objects to construct data for
 * individual records and the entire data set.  DataRecord and
 * DataSet objects inherit Meta to construct these representations.
 * </p>
 */
public class Meta {

	/***
	 * Observations metadata object
	 */
	public Observations stataobs;

	/***
	 * Variables metadata object
	 */
	public Variables statavars;

	/***
	 * Variable index metadata object
	 */
	public List<Integer> varindex;

	/***
	 * Observation index metadata object
	 */
	public List<Long> obsindex;

	/***
	 * Constructor for object w/o any arguments passed
	 */
	public Meta() {

		// Create an observations member variable
		setStataobs();

		// Create a variables member variable
		setStatavars();

		// Create a variable index member variable
		setVarindex(statavars);

		// Create an observation index member variable
		setObsindex(stataobs);

	} // End constructor declaration

	/***
	 * Generic constructor when args are passed from javacall
	 * @param args String array to transform into collector object
	 */
	public Meta(String[] args){

		// Create a new observations object
		setStataobs();

		// List object to store arguments
		List<String> newargs;

		// If there are arguments passed to the constructor
		if (args.length > 0) {

			// Convert the string array to a List of Strings
			newargs = StataArgsToCollector.argsToCollector(args);

			// Create a new variables object
			setStatavars(newargs);

		} else {

			// Create a new variables object
			setStatavars();

		} // End IF/ELSE Block

		// Create a new variable index object
		setVarindex(statavars);

		// Create a new observation index object
		setObsindex(stataobs);

	} // End constructor declaration with arguments


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
	 * @param args A list of string objects created by converting the string
	 *                array String[] args to a list object
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

	/***
	 * Method to get an individual record for a given observation index
	 * @param obid The observation index for which to retrieve the data
	 * @return An object containing each of the data elements defined in the
	 * variable index member variable
	 */
	/*
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
			key = statavars.getName(i - 1);

			// Test for string/numeric
			if (statavars.getVarType(i - 1)) {

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
	/***
	 * Method to store Stata dataset in a List of objects containing maps of
	 * key value pairs where the key is the variable name and the value is
	 * the value on that variable for the given observation
	 * @return A List of map objects with key/value pairs for the data set in
	 * memory
	 */
	/*
	public List<Object> getAllRecords() {

		// Temporary variable to store keys (variable names)
		String key;

		// Temporary variable to store values (variable value)
		Object value;

		// A list of Map<String, Object> variables containing key/value pairs
		// for individual observations
		List<Object> obs = new ArrayList<Object>();

		// An object to store all key/value pairs for an observation
		Map<String, Object> record = new HashMap<>();

		// Loop over the observation indices
		for (Long obid : this.obsindex) {

			obs.add(getRecord(obid));


			// Loop over the variable indices
			for (Integer varid : this.varindex) {

				// Get the names of the variables
				key = statavars.getName(varid - 1);

				// Test for string/numeric
				if (statavars.getVarType(varid - 1)) {

					// Store string value if variable contains strings
					value = Data.getStr(varid, obid);

				} else {

					// Convert numeric variables to string
					value = Data.getNum(varid, obid);

				} // End IF/ELSE Block for string/numeric type handling

				// Add the key/value pair to the container object
				record.put(key, value);

			} // End of Loop over variable indices

			// Add the key/value pairs for an individual observation to the
			// larger container
			obs.add(record);


		} // End of Loop over observation indices

		// Return the container with individual map objects
		return(obs);

	} // End method declaration for getAllRecords
	*/

} // End object declaration
