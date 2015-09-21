package org.paces.Stata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stata.sfi.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Record Class Object</h2>
 * <p>A POJO representation of a single observation from the Stata dataset
 * loaded in memory. </p>
 */
public class DataRecord extends Meta implements StataData {

	// Observation ID variable
	@JsonProperty
	public Long obid;

	// Variable containing the data for a given observation
	public Map<String, Object> observation;

	/***
	 * Constructor method for DataRecord class
	 * @param id The observation index value for which to retrieve the data for
	 */
	DataRecord(Long id) {

		// Set the observation ID variable
		setObid(id);

		// Set the data (observation) variable
		setData();

	} // End declaration of constructor method

	/***
	 * Setter method for the observation ID variable
	 * @param observationNumber An observation index value
	 */
	public void setObid(long observationNumber) {

		// Observation IDs need to be offset by 1 when converting from Stata to
		// Java.
		this.obid = observationNumber;

	} // End Method declaration to set observation ID

	/***
	 * Method to get an individual record for a given observation index
	 * variable index member variable
	 */
	@Override
	@JsonAnyGetter
	public void setData() {

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
		this.observation = record;

	} // End of Method declaration

	/***
	 * Getter method to retrieve the data variable from the object
	 * @return A map object containing key/value pairs for the data related
	 * to that object
	 */
	public Map<String, Object> getData() {
		return this.observation;
	}

}
