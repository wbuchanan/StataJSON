package org.paces.Stata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.stata.sfi.Macro;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata DataSet Class Object</h2>
 * <p>A POJO representation of the Stata dataset currently in memory.
 * Created by iterating over calls to DataRecord.</p>
 */
public class DataSet extends Meta implements StataData {

	@JsonProperty
	public String name;

	// POJO Representation of the data set in memory of Stata
	public List<Object> stataDataSet;

	/***
	 * Generic constructor method for the class
	 */
	public DataSet() {

		// Builds the data object
		setData();

	} // End constructor declaration

	/***
	 * Generic Setter method for the name of the dataset object
	 */
	public void setName() {

		// Store the value of `"`c(filename)'"' as a Java string
		String nm = Macro.getLocalSafe("c(filename)");

		// If the dataset name is not empty
		if (!nm.isEmpty()) {

			// Assign the Stata file name as the name of the object
			this.name = nm;

		} else {

			// Otherwise set a generic name
			this.name = "Stata Data Set";

		} // End IF/ELSE Block for object name

	} // End declaration of setname method

	/***
	 * Method to store Stata dataset in a List of objects containing maps of
	 * key value pairs where the key is the variable name and the value is
	 * the value on that variable for the given observation
	 */
	@Override
	@JsonAnyGetter
	public void setData() {

		// Initialize container to ID the observation and contains a Map
		// object with key/value pairs
		List<Object> obs = new ArrayList<Object>();

		// Loop over the observation indices
		for (Long obid : this.obsindex) {

			// Add a new Data Record to the Map object
			obs.add(new DataRecord(obid).getData());

		} // End Loop over observation indices

		// Set the member variable to this value
		stataDataSet = obs;

	} // End method declaration to set data value of class

	/***
	 * Getter method to access the POJO representation of the Stata dataset
	 * @return A POJO representation of the Stata Dataset
	 */
	@Override
	public Object getData() {

		// Returns the sole member variable of the class
		return this.stataDataSet;

	} // End getter method declaration

	/***
	 * Getter method to the name of the Object
	 * @return The name of the Stata Data object
	 */
	@JsonGetter
	public String getName() {

		// Returns the name of the Stata dataset (or generic placeholder)
		// used to construct a JSON object
		return this.name;

	} // End of getName method declaration



} // End Class declaration

