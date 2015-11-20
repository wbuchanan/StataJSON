package org.paces.Stata.JSON;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
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
@JsonRootName("StataJSON")
@JsonPropertyOrder({ "data", "first record id", "last record id",
		"number of records", "variable indices", "number of variables",
		"variable type string", "variable names", "variable labels",
		"value label names", "value labels" })
public class StataAllToJSON  {

	/***
	 * A Meta class object containing the meta data for the dataset in memory
	 */
	@JsonIgnore
	public Meta theMetaData;

	/***
	 * A member variable to store the name of the JSON object
	 */
	@JsonIgnore
	public final String name = "StataJSON";

	/***
	 * A DataSet class object (e.g., the data from the dataset in memory)
	 */
	@JsonProperty("data")
	public DataSet theData;

	/***
	 * Starting observation index number
	 */
	@JsonProperty("first record id")
	private long sobs;

	/***
	 * Ending observation index number
	 */
	@JsonProperty("last record id")
	private long eobs;

	/***
	 * Total Number of Observations
	 */
	@JsonProperty("number of records")
	private long nobs;

	/***
	 * Member variable containing variable indices
	 */
	@JsonProperty("variable indices")
	public List<Integer> varindex;

	/***
	 * Number of variables passed from javacall
	 */
	@JsonProperty("number of variables")
	public int nvars;

	/***
	 * Member variable containing indicators for whether or not the variable
	 * is of type String
	 */
	@JsonProperty("variable type string")
	public Map<String, Boolean> varTypes;

	/***
	 * Member variable containing Stata variable names
	 */
	@JsonProperty("variable names")
	public List<String> varnames;

	/***
	 * Member variable containing Stata variable labels
	 */
	@JsonProperty("variable labels")
	public Map<String, String> varlabels;

	/***
	 * Member variable containing Stata value label names associated with a
	 * given variable
	 */
	@JsonProperty("value label names")
	public Map<String, String> valueLabelNames;

	/***
	 * Member variable containing a list of Map objects with the values and
	 * associated labels contained in the Map object
	 */
	@JsonProperty("value labels")
	public Map<String, Map<Integer, String>> valueLabels;

	/***
	 * Observation indices
	 */
	@JsonIgnore
	private List<Long> obindex;


	/***
	 * Constructor method for StataAllToJSON class
	 * @param args Arguments passed from Stata's javacall
	 */
	@JsonIgnore
	public StataAllToJSON(String[] args) {

		// Populate member variables
		setData(args);

	} // End of Constructor declaration

	/***
	 * Method to Retrieve the data from the object in a single container
	 * @param args Value passed to method from constructor
	 */
	@JsonIgnore
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

		// Variable indices
		this.varindex = this.theMetaData.statavars.getVariableIndex();

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

		this.nvars = this.theMetaData.statavars.getNvars();

	} // End of getData method declaration

	/***
	 * Method to get a POJO with all available data and metadata
	 * @return A map of String, Object types where Strings indicate the type
	 * of data object and objects contain the data to be serialized to JSON
	 */
	@JsonIgnore
	public Map<String, Object> getData() {

		// Initialize new map object to store the data
		Map<String, Object> makeJSON = new HashMap<String, Object>();

		/* Add starting observation #, ending observation #,
		total # of obs, variable names, variable labels, indicators for string
		variables, value label names, value labels, and the data it self to the
		map object. */
		makeJSON.put("name", getName());
		makeJSON.put("data", this.theData.getData());
		makeJSON.put("first record id", getSobs());
		makeJSON.put("last record id", getEobs());
		makeJSON.put("number of records", getNobs());
		makeJSON.put("variable indices", getVarindex());
		makeJSON.put("number of variables", getNvars());
		makeJSON.put("variable type string", getVariableTypes());
		makeJSON.put("variable names", getVarnames());
		makeJSON.put("variable labels", getVarlabels());
		makeJSON.put("value label names", getValueLabelNames());
		makeJSON.put("value labels", getValueLabels());

		// Return the map object
		return makeJSON;

	} // End of getter method declaration

	@JsonIgnore
	public String getName() { return this.name; }

	@JsonIgnore
	public long getSobs() {
		return this.sobs;
	}

	@JsonIgnore
	public long getEobs() {
		return this.eobs;
	}

	@JsonIgnore
	public long getNobs() {
		return this.nobs;
	}

	@JsonIgnore
	public int getNvars() {
		return this.nvars;
	}

	@JsonIgnore
	public List<Integer> getVarindex() {
		return this.varindex;
	}

	@JsonIgnore
	public List<String> getVarnames() {
		return this.varnames;
	}

	@JsonIgnore
	public Map<String, String> getVarlabels() {
		return this.varlabels;
	}

	@JsonIgnore
	public Map<String, String> getValueLabelNames() {
		return this.valueLabelNames;
	}

	@JsonIgnore
	public Map<String, Map<Integer, String>> getValueLabels() {
		return this.valueLabels;
	}

	@JsonIgnore
	public Map<String, Boolean> getVariableTypes() { return this.varTypes; }

} // End of StataAllToJSON object declaration
