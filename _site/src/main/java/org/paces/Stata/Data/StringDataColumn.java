package org.paces.Stata.Data;

import com.stata.sfi.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h1>Class to return all values of a single variable</h1>
 */
public class StringDataColumn {

	/***
	 * A Meta class object contains meta data for the dataset in memory
	 */
	public Meta metaob;

	/***
	 * A List of String values from a single variable
	 */
	public List<String> colvar;

	/***
	 * Generic constructor for the class
	 * @param args Arguments passed to the Meta class constructor
	 * @param varidx The variable index from which to get records
	 */
	public StringDataColumn(String[] args, int varidx) {

		// Populates the Meta class member variable
		metaob = new Meta(args);

		// Sets the values in the object based on a variable from the Stata
		// dataset
		setData(varidx) ;

	} // End Class constructor method

	/***
	 * Setter method used to retrieve the data from Stata and populate the
	 * member variable
	 * @param var The variable index to retrieve
	 */
	public void setData(int var) {

		// Temp variable to store results
		List<String> stringvar = new ArrayList<String>();

		// Populate the temp variable
		stringvar.addAll(metaob.obsindex.stream().map(
				(Function<Long, String>) (ob) -> {
					return Data.getStr(metaob.getVarindex(var), ob);
				}).collect(Collectors.<String>toList()));

		// Set teh values of the member variable to the values of the temp
		// variable
		this.colvar = stringvar;

	}// End of setter method declaration

	/***
	 * Getter method to retrieve the List class object containing values
	 * @return A List of String objects containing the data from the Stata
	 * dataset in memory
	 */
	public List<String> getData() {
		return this.colvar;
	}  // End of getter method


	/***
	 * Getter method to retrieve the List class object containing values and
	 * convert it to a String array
	 * @return A Array of String objects containing the data from the Stata
	 * dataset in memory
	 */
	public String[] getDataAsArray() {

		// Get the size of the list object
		int x = this.colvar.size();

		// Initialize a string array of the same size
		String[] y = new String[x];

		// Convert to array and return the string array
		return(this.colvar.toArray(y));

	} // End of getter method

} // End of string data column class
