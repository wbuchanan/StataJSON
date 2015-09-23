package org.paces.Stata.JSON;

import com.fasterxml.jackson.annotation.*;
import org.paces.Stata.Data.DataSet;
import org.paces.Stata.Data.Meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>An Object to construct a full representation of the Stata Dataset
 * in memory</h2>
 * <p>Object used to create a JSON object with the data and metadata bound
 * to it. </p>
 */
@JsonPropertyOrder(value = {"Starting Observation Number",
		"Ending Observation Number",
		"Number of Observations",
		"Variable Names", "Variable Labels",
		"Is Variable String", "Value Label Names",
		"Value Labels", "Data Set"}, alphabetic = false)
public class StataAllToJSON  {

	/***
	 * A Meta class object containing the meta data for the dataset in memory
	 */
	@JsonIgnore
	public Meta theMetaData;

	/***
	 * A DataSet class object (e.g., the data from the dataset in memory)
	 */
	@JsonIgnore
	public DataSet theData;

	/***
	 * Member variable containing variable indices
	 */
	@JsonProperty(required = true, value = "Variable Indices")
	public List<Integer> varindex;

	/***
	 * Member variable containing Stata variable names
	 */
	@JsonProperty(required = true, value = "Variable Names")
	public List<String> varnames;

	/***
	 * Member variable containing Stata variable labels
	 */
	@JsonProperty(required = true, value = "Variable Labels")
	public List<String> varlabels;

	/***
	 * Member variable containing Stata value label names associated with a
	 * given variable
	 */
	@JsonProperty(required = true, value = "Value Label Names")
	public List<String> valueLabelNames;

	/***
	 * Member variable containing a list of Map objects with the values and
	 * associated labels contained in the Map object
	 */
	@JsonProperty(required = true, value = "Value Labels")
	public List<Object> valueLabels;

	/***
	 * Member variable containing indicators for whether or not the variable
	 * is of type String
	 */
	@JsonProperty(required = true, value = "Is Variable String")
	public List<Boolean> varTypes;

	/***
	 * Number of variables passed from javacall
	 */
	@JsonProperty(required = true, value = "Number of Variables")
	public int nvars;

	/***
	 * Starting observation index number
	 */
	@JsonProperty(required = true, value = "Starting Observation")
	private long sobs;

	/***
	 * Ending observation index number
	 */
	@JsonProperty(required = true, value = "Ending Observation")
	private long eobs;

	/***
	 * Total Number of Observations
	 */
	@JsonProperty(required = true, value = "Total Number of Observations")
	private long nobs;

	/***
	 * Observation indices
	 */
	@JsonProperty(required = true, value = "Observation Indices")
	private List<Long> obindex;

	/***
	 * Constructor method for StataAllToJSON class
	 * @param args Arguments passed from Stata's javacall
	 */
	@JsonCreator
	public StataAllToJSON(String[] args) {

		// Populate member variables
		setData(args);

	} // End of Constructor declaration

	/***
	 * Method to Retrieve the data from the object in a single container
	 * @param args Value passed to method from constructor
	 */
	@JsonAnySetter
	public void setData(String[] args) {

		// Create new metadata object
		this.theMetaData = new Meta(args);

		// Create new dataset object
		this.theData = new DataSet(this.theMetaData);

		// Starting observation index
		this.sobs = this.theMetaData.stataobs.getSobs();

		// Ending observation index
		this.eobs = this.theMetaData.stataobs.getEobs();

		// Total number of observations
		this.nobs = this.theMetaData.stataobs.getNobs();

		// Variable names
		this.varnames = this.theMetaData.statavars.getVariableNames();

		// Variable Labels
		this.varlabels = this.theMetaData.statavars.getVariableLabels();

		// String type boolean
		this.varTypes = this.theMetaData.statavars.getVariableTypes();

		// Value Label Names
		this.valueLabelNames = this.theMetaData.statavars.getValueLabelNames();

		// Value Labels
		this.valueLabels = this.theMetaData.statavars.getValueLabels();

	} // End of getData method declaration

	/***
	 * Method to get a POJO with all available data and metadata
	 * @return A map of String, Object types where Strings indicate the type
	 * of data object and objects contain the data to be serialized to JSON
	 */
	@JsonAnyGetter
	@JsonValue
	@JsonPropertyOrder(value = {"Starting Observation Number",
			"Ending Observation Number",
			"Number of Observations",
			"Variable Names", "Variable Labels",
			"Is Variable String", "Value Label Names",
			"Value Labels", "Data Set"}, alphabetic = false)
	public Map<String, Object> getData() {

		// Initialize new map object to store the data
		Map<String, Object> makeJSON = new HashMap<String, Object>();

		/* Add starting observation #, ending observation #,
		total # of obs, variable names, variable labels, indicators for string
		variables, value label names, value labels, and the data it self to the
		map object. */
		makeJSON.put("Starting Observation Number", this.sobs);
		makeJSON.put("Ending Observation Number", this.eobs);
		makeJSON.put("Number of Observations", this.nobs);
		makeJSON.put("Variable Names", this.varnames);
		makeJSON.put("Variable Labels", this.varlabels);
		makeJSON.put("Is Variable String", this.varTypes);
		makeJSON.put("Value Label Names", this.valueLabelNames);
		makeJSON.put("Value Labels", this.valueLabels);
		makeJSON.put("Data Set", this.theData.getData());

		// Return the map object
		return makeJSON;

	} // End of getter method declaration

} // End of StataAllToJSON object declaration
