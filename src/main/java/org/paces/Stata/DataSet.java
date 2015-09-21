package org.paces.Stata;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by billy on 9/20/15.
 */
public class DataSet extends Meta implements StataData {

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

} // End Class declaration


