package org.paces.Stata.JSON;

import com.fasterxml.jackson.annotation.*;
import org.paces.Stata.DataSets.DataSet;
import org.paces.Stata.MetaData.Meta;

import java.util.*;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>An Object to construct a full representation of the Stata Dataset
 * in memory</h2>
 * <p>Object used to create a JSON object with the data and metadata bound
 * to it. </p>
 */
@JsonRootName("StataJSON")
@JsonPropertyOrder({ "dataSource", "values", "firstRecordId", "lastRecordId",
		"numberRecords", "variableIndices", "numberVariables",
		"variableIsString", "variableNames", "variableLabels",
		"valueLabelNames", "valueLabels" })
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
	public static final String name = "StataJSON";

	/***
	 * A DataSet class object (e.g., the data from the dataset in memory)
	 */
	@JsonIgnore
	//public DataSet theData;

	@JsonProperty("dataSource")
	public String datasource;

	@JsonProperty("values")
	public Object dataObject;

	/***
	 * Starting observation index number
	 */
	@JsonProperty("firstRecordId")
	private Number sobs;

	/***
	 * Ending observation index number
	 */
	@JsonProperty("lastRecordId")
	private Number eobs;

	/***
	 * Total Number of Observations
	 */
	@JsonProperty("numberOfRecords")
	private Number nobs;

	/***
	 * Member variable containing variable indices
	 */
	@JsonProperty("variableIndices")
	public List<Integer> varindex;

	/***
	 * Number of variables passed from javacall
	 */
	@JsonProperty("numberOfVariables")
	public Integer nvars;

	/***
	 * Member variable containing indicators for whether or not the variable
	 * is of type String
	 */
	@JsonProperty("variableIsString")
	public Map<String, Boolean> varTypes;

	/***
	 * Member variable containing Stata variable names
	 */
	@JsonProperty("variableNames")
	public List<String> varnames;

	/***
	 * Member variable containing Stata variable labels
	 */
	@JsonProperty("variableLabels")
	public Map<String, String> varlabels;

	/***
	 * Member variable containing Stata value label names associated with a
	 * given variable
	 */
	@JsonProperty("valueLabelNames")
	public Map<String, String> valueLabelNames;

	/***
	 * Member variable containing a list of Map objects with the values and
	 * associated labels contained in the Map object
	 */
	@JsonProperty("valueLabels")
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
		this.theMetaData = new Meta();

		// Create new dataset object
		DataSet theData = new DataSet(this.theMetaData);

		this.dataObject = theData.getData();

		this.datasource = theData.getSource();

		// Starting observation index
		this.sobs = this.theMetaData.getStataobs().getSobs().longValue();

		// Ending observation index
		this.eobs = this.theMetaData.getStataobs().getEobs().longValue();

		// Total number of observations
		this.nobs = this.theMetaData.getStataobs().getNobs().longValue();

		// Variable indices
		this.varindex = this.theMetaData.getVarindex();

		// Variable names
		this.varnames = this.theMetaData.getVarNames();

		// Variable Labels
		this.varlabels = this.theMetaData.getVariableLabels();

		// String type boolean
		this.varTypes = this.theMetaData.getStatavars().getVariableTypes();

		// Value Label Names
		this.valueLabelNames = this.theMetaData.getValueLabelNames();

		// Value Labels
		this.valueLabels = this.theMetaData.getStatavars().getValueLabels();

		this.nvars = this.theMetaData.getStatavars().getNvars();

		// Gets a list containing Long values with observation indices
		this.obindex = this.theMetaData.getObs14();

	} // End of getData method declaration

	/***
	 * Method to get a POJO with all available data and metadata
	 * @return A map of String, Object types where Strings indicate the type
	 * of data object and objects contain the data to be serialized to JSON
	 */
	public Map<String, Object> getData() {

		// Initialize new map object to store the data
		Map<String, Object> makeJSON = new HashMap<>();

		/* Add starting observation #, ending observation #,
		total # of obs, variable names, variable labels, indicators for string
		variables, value label names, value labels, and the data it self to the
		map object. */
		makeJSON.put("name", getName());
		makeJSON.put("source", this.datasource);
		makeJSON.put("values", this.dataObject);
		makeJSON.put("firstObservationId", getSobs());
		makeJSON.put("lastObservationId", getEobs());
		makeJSON.put("numberRecords", getNobs());
		makeJSON.put("variableIndices", getVarindex());
		makeJSON.put("numberVariables", getNvars());
		makeJSON.put("variableTypeIsString", getVariableTypes());
		makeJSON.put("variableNames", getVarnames());
		makeJSON.put("variableLabels", getVarlabels());
		makeJSON.put("valueLabelNames", getValueLabelNames());
		makeJSON.put("valueLabels", getValueLabels());
		// makeJSON.put("observationIndex", getObservationIndex());

		// Return the map object
		return makeJSON;

	} // End of getter method declaration

	@JsonIgnore
	public String getName() { return name; }

	@JsonIgnore
	public Number getSobs() {
		return this.sobs;
	}

	@JsonIgnore
	public Number getEobs() {
		return this.eobs;
	}

	@JsonIgnore
	public Number getNobs() {
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

	@JsonIgnore
	public List<Long> getObservationIndex() { return this.obindex; }

} // End of StataAllToJSON object declaration
