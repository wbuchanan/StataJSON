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
public class NumDataColumn {

	/***
	 * A Meta class object contains meta data for the dataset in memory
	 */
	public Meta metaob;

	/***
	 * A List of double values from a single variable
	 */
	public List<Double> colvar;

	/***
	 * Generic constructor for the class
	 * @param args Arguments passed to the Meta class constructor
	 * @param varidx The variable index from which to get records
	 */
	public NumDataColumn(String[] args, int varidx) {

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
		List<Double> dblvar = new ArrayList<Double>();

		// Populate the temp variable
		dblvar.addAll(metaob.obsindex.stream().map(
				(Function<Long, Double>) (ob) -> {
					return Data.getNum(metaob.getVarindex(var), ob);
				}).collect(Collectors.<Double>toList()));

		// Set the member variable to the values of the temp variable
		this.colvar = dblvar;

	} // End of setter method declaration

	/***
	 * Getter method to retrieve the List class object containing values
	 * @return A List of Double objects containing the data from the Stata
	 * dataset in memory
	 */
	public List<Double> getData() { return this.colvar; }

	/***
	 * Getter method to retrieve the data as an Array
	 * @return An array of Doubles containing values form the Stata dataset
	 * in memory
	 */
	public Double[] getDataAsDoubleArray() {

		// Get the length of the vector
		int x = this.colvar.size();

		// Initialize a new array class object with the same length
		Double[] y = new Double[x];

		// Returns the converted list object as an array
		return(this.colvar.toArray(y));

	} // End of method declaration

} // End of numeric data column class
