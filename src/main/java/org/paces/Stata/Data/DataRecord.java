package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.*;
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
@JsonPropertyOrder({ "source", "_id", "name", "values"})
public class DataRecord implements Record {

	/***
	 * A Stata Metadata object
	 */
	@JsonIgnore
	public Meta metaob;

	@JsonIgnore
	private Variables vars;

	@JsonProperty("source")
	private final String source = DataSource.get();

	/***
	 * Observation ID variable
	 */
	@JsonProperty("_id")
	private Long obid;

	@JsonProperty("name")
	private static final String name = "record";

	/***
	 * Variable containing the data for a given observation
	 */
	@JsonProperty("values")
	private Map<String, Object> observation;

	/***
	 * Constructor method for DataRecord class
	 * @param id The observation index value for which to retrieve the data for
	 * @param metaobject A Meta class object containing metadata from the
	 *                      Stata dataset
	 *
	 */
	@JsonIgnore
	public DataRecord(Long id, Meta metaobject) {

		// The metadata object
		this.metaob = metaobject;

		this.vars = metaob.getStatavars();

		// Set the observation ID variable
		setObid(id);

		// Set the data (observation) variable
		setData();

	} // End declaration of constructor method

	/***
	 * Setter method for the observation ID variable
	 * @param observationNumber An observation index value
	 */
	@JsonIgnore
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
	@JsonIgnore
	public void setData() {

		// Initialize empty variable to store the variable name
		String key;

		// Initialize empty variable to store the value for a given
		// observation on a specified variable
		Object value;

		// Initialize empty container for key/value pairs
		Map<String, Object> record = new HashMap<>();

		// Loop over the variable indices
		for (int i = 0; i < vars.getVariableIndex().size(); i++) {

			// Set the variable name as the key in the map object
			key = vars.getVariableName(i);

			// Test for string/numeric
			if (vars.getVarType(key)) {

				// Store string value if variable contains strings
				value = Data.getStr(vars.getVariableIndex(i), obid);

			} else {

				// Check for missing numeric values
				if (Data.isValueMissing(Data.getNum(vars.getVariableIndex(i),
						obid))) {

					// If value is missing, populate field with the string
					// value null.
					value = "null";

				} else {

					// Convert numeric variables to string
					value = Data.getNum(vars.getVariableIndex(i), obid);

				} // End ELSE Block for non-missing values

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
	@Override
	@JsonIgnore
	public Map<String, Object> getData() {

		// Returns the array for the observation
		return this.observation;

	} // End of getData method declaration

	/***
	 * Method to access the observation ID member variable
	 * @return The observation ID for a given record
	 */
	@JsonIgnore
	public Long getObid() {
		return this.obid;
	}

	@JsonIgnore
	public String getName() { return this.name; }

} // End of Class declaration
