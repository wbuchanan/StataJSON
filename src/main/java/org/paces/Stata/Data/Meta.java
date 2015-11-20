package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@JsonIgnore
	public Observations stataobs;

	/***
	 * Variables metadata object
	 */
	@JsonIgnore
	public Variables statavars;

	/***
	 * Variable index metadata object
	 */
	@JsonIgnore
	public List<Integer> varindex;

	/***
	 * Observation index metadata object
	 */
	@JsonIgnore
	public List<Long> obsindex;

	/***
	 * Constructor for object w/o any arguments passed
	 * @param args Arguments passed from the javacall command in Stata
	 */
	@JsonIgnore
	public Meta(String[] args) {

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
	 * Generic setter method for observations member variable
	 */
	@JsonIgnore
	public void setStataobs() {

		// Initialize a new observations object
		stataobs = new Observations();

	} // End setter method for observations member variable

	/***
	 * Generic setter method for variables member variable
	 */
	@JsonIgnore
	public void setStatavars() {

		// Initialize a new variables metadata object
		statavars = new Variables();

	} // End setter method for variables metadata member variable

	/***
	 * Sets teh observation index member variable
	 * @param observations An observations class object
	 */
	@JsonIgnore
	public void setObsindex(Observations observations) {

		// Initialize a new observation index object
		obsindex = observations.getObservationIndex();

	} // End setter method for observation index member variable

	/***
	 * Sets the variable index member variable
	 * @param variables A variables class object
	 */
	@JsonIgnore
	public void setVarindex(Variables variables) {

		// Initialize a new variable index object
		varindex = variables.getVariableIndex();

	} // End setter method for variable index member variable

	/***
	 * Getter for the observations member variable
	 * @return Returns the observation member variable
	 */
	@JsonIgnore
	public Observations getStataobs() { return stataobs; }

	/***
	 * Getter for the variables member variable
	 * @return Returns the variables member variable
	 */
	@JsonIgnore
	public Variables getStatavars() { return statavars; }

	/***
	 * Getter for the observation index member variable
	 * @return Returns the observation index member variable
	 */
	@JsonIgnore
	public List<Long> getObsindex() { return obsindex; }

	/***
	 * Getter for the variable index member variable
	 * @return Returns the variable index member variable
	 */
	@JsonIgnore
	public List<Integer> getVarindex() { return varindex; }

	/***
	 * Getter for single variable index value
	 * @param idxid The variable index element whose value is to be retrieved
	 * @return The element of the variable index passed to the method call
	 */
	@JsonIgnore
	public int getVarindex(int idxid) {

		// Return the element of the variable index identified by the value
		// of the argument passed to the method
		return varindex.get(idxid);

	} // End getVarIndex method declaration

} // End object declaration
