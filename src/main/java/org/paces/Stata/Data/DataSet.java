package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.*;
import com.stata.sfi.Macro;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata DataSet Class Object</h2>
 * <p>A POJO representation of the Stata dataset currently in memory.
 * Created by iterating over calls to DataRecord.</p>
 */
@JsonPropertyOrder({"source", "name", "values"})
public class DataSet implements StataData {

	/***
	 * A new Meta object
	 */
	@JsonIgnore
	public Meta metaob;

	/***
	 * The name of the data set in memory to be converted to a JSON object
	 */
	@JsonProperty("source")
	public String source;

	@JsonProperty("name")
	private final String name = "StataJSON";

	/***
	 * POJO Representation of the data set in memory of Stata
	 */
	@JsonProperty("values")
	public List<Object> stataDataSet;

	/***
	 * Generic constructor method for the class
	 * @param metaobject A Meta class object containing metadata for the
	 *                      Stata dataset.
	 */
	@JsonIgnore
	public DataSet(Meta metaobject) {

		// Set the meta object to the value passed to the constructor
		this.metaob = metaobject;

		// Set the name variable
		setSource();

		// Builds the data object
		setData();

	} // End constructor declaration

	/***
	 * Generic Setter method for the name of the dataset object
	 */
	@JsonIgnore
	public void setSource() {

		// Store the value of `"`c(filename)'"' as a Java string
		String nm = Macro.getLocalSafe("filename");

		// If the dataset name is not empty
		if (!nm.isEmpty()) {

			// Assign the Stata file name as the name of the object
			this.source = nm;

		} else {

			// Otherwise set a generic name
			this.source = "Stata Data";

		} // End IF/ELSE Block for object name

	} // End declaration of setname method

	/***
	 * Method to store Stata dataset in a List of objects containing maps of
	 * key value pairs where the key is the variable name and the value is
	 * the value on that variable for the given observation
	 */
	@Override
	@JsonIgnore
	public void setData() {

		// Initialize container to ID the observation and contains a Map
		// object with key/value pairs
		List<Object> obs = new ArrayList<Object>();

		obs.addAll(metaob.obsindex.stream().map(
				(Function<Long, Object>) (id) -> {
					return new DataRecord(id, this.metaob).getData();
				}
		).collect(Collectors.<Object>toList()));

		// Set the member variable to this value
		stataDataSet = obs;

	} // End method declaration to set data value of class

	/***
	 * Getter method to access the POJO representation of the Stata dataset
	 * @return A POJO representation of the Stata Dataset
	 */
	@Override
	@JsonIgnore
	public Object getData() {

		// Returns the sole member variable of the class
		return this.stataDataSet;

	} // End getter method declaration

	/***
	 * Getter method to the name of the Object
	 * @return The name of the Stata Data object
	 */
	@JsonIgnore
	public String getSource() {

		// Returns the name of the Stata dataset (or generic placeholder)
		// used to construct a JSON object
		return this.source;

	} // End of getName method declaration

	@JsonIgnore
	public String getName() {
		return this.name;
	}

} // End Class declaration


