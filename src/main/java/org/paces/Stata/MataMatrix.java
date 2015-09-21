package org.paces.Stata;

import com.stata.sfi.Macro;
import com.stata.sfi.Mata;
import com.stata.sfi.SFIToolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by billy on 9/20/15.
 */
public class MataMatrix {

	// Number of columns in the matrix
	public long ncols;

	// Number of rows in the matrix
	public long nrows;

	// Name of the Mata matrix
	public String matName;

	// List of column indices
	public List<Long> colindex;

	// List of row indices
	public List<Long> rowindex;

	// POJO representation of the Mata matrix
	public Object matrix;

	/***
	 * Constructor method used to build the MataMatrix object
	 */
	MataMatrix() {

		// Sets the name of the matrix
		setMatName();

		// Sets the number of columns
		setNcols(getMatName());

		// Sets the number of rows
		setNrows(getMatName());

		// Sets the column index variable
		setColIndex(getNcols());

		// Sets the row index variable
		setRowIndex(getNrows());

		// Builds the POJO representation of the Mata matrix
		setMatrix(getRowIndex(), getColIndex());

	} // End of Constructor method declaration

	/***
	 * Setter method for the name of the matrix accessed from a local macro
	 * defined during prior to calling the javacall function from Stata.
	 */
	public void setMatName(){

		// Get the name of the matrix from the local macro matname
		matName = Macro.getLocalSafe("matname");

	} // End matrix name setter method declaration

	/***
	 * Getter method to access the name of the matrix
	 * @return A string object containing the name of the matrix originally
	 * passed from the javacall function in Stata
	 */
	public String getMatName() {
		return this.matName;
	}

	/***
	 * Setter method for the number of columns in the named Mata Matrix
	 * @param matrixName The name of the Mata Matrix stored in the matName
	 *                      variable.
	 */
	public void setNcols(String matrixName) {
		ncols = Mata.getMataColTotal(matrixName);
	}

	/***
	 * Getter method to access the total number of columns
	 * @return A Long object with the value of the number of columns
	 */
	public Long getNcols() {
		return this.ncols;
	}

	/***
	 * Setter method for the number of rows in the named Mata Matrix
	 * @param matrixName The name of the Mata Matrix stored in the matName
	 *                      variable.
	 */
	public void setNrows(String matrixName) {
		ncols = Mata.getMataRowTotal(matrixName);
	}

	/***
	 * Getter method to access the total number of rows
	 * @return A Long object with the value of the number of rows
	 */
	public Long getNrows() {
		return this.nrows;
	}

	/***
	 * Setter method for the colindex member variable
	 * @param ncol The number of columns in the Matrix previously named
	 */
	public void setColIndex(Long ncol) {

		// Initialize a temporary index object
		List<Long> tmpidx = new ArrayList<Long>();

		// Loop over the values 1 - ncol
		for (long i = 1; i <= ncol; i++) {

			// Add the value to the List object
			tmpidx.add(i);

		} // End of Loop over column values

		// Set the column index variable to the value of this List object
		colindex = tmpidx;

	} // End declaration of method used to set the column index values

	/***
	 * Getter method to access the column indices
	 * @return A List of long values identifying columns of the previously
	 * named Mata matrix
	 */
	public List<Long> getColIndex() {
		return this.colindex;
	}

	/***
	 * Setter method for the rowindex member variable
	 * @param nrow The number of rows in the Matrix previously named
	 */
	public void setRowIndex(Long nrow) {

		// Initialize a temporary index object
		List<Long> tmpidx = new ArrayList<Long>();

		// Loop over the values 1 - nrow
		for (long i = 1; i <= nrow; i++) {

			// Add the value to the List object
			tmpidx.add(i);

		} // End of Loop over row values

		// Set the row index variable to the value of this List object
		rowindex = tmpidx;

	} // End of set row index method declaration

	/***
	 * Getter method to access the row indices
	 * @return A List of long objects identifying individual rows of the Mata
	 * Matrix
	 */
	public List<Long> getRowIndex() {
		return this.rowindex;
	}

	/***
	 * Method used to construct a POJO representation of the Mata matrix
	 * @param observations A List of row indices
	 * @param variables A List of column indices
	 */
	public void setMatrix(List<Long> observations, List<Long> variables) {

		// Initializes a Map object to store a Map object for each row
		Map<Long, Object> tmpMatrix = new HashMap<Long, Object>();

		// Initializes a Map object to store column values for a single row
		Map<Long, Object> tmprow = new HashMap<Long, Object>();

		// Loop over the rows of the matrix
		for (Long m : observations) {

			// Loop over the columns of the matrix
			for (Long n : variables) {

				// Add values from the column for the given row to the Map
				// object
				tmprow.put(n, getMatrixData(n, m));

			} // End Loop over column values

			// Add complete object to Map object containing individual row
			// records
			tmpMatrix.put(m, tmprow);

		} // End Loop over rows

		// Set the matrix data to the final value of the temporary matrix
		matrix = tmpMatrix;

	} // End declaration of setMatrix method

	/***
	 * Getter method to access the POJO representation of the Mata Matrix
	 * @return A POJO representation of the Mata Matrix
	 */
	public Object getMatrix() {
		return this.matrix;
	}

	/***
	 * Generic method to return data from a Mata Matrix
	 * @param colid The value of the matrix column from which to obtain a value
	 * @param rowid The value of the matrix row from which to obtain a value
	 * @return A POJO containing the value for X[m, n]; The method returns a
	 * value of class Object regardless of whether the type is complex, real,
	 * or string.
	 */
	public Object getMatrixData(Long colid, Long rowid) {

		// If the getMataEltype method returns an empty string return an error
		if ("".equals(Mata.getMataEltype(this.matName))) {

			// Initialize a new message object as a string builder
			StringBuilder msg = new StringBuilder();

			// Append the parts of the message
			msg.append("The type of the matrix ").append(this.matName).append
					(" could not be identified.\nNo matrix will be returned.");

			// Print as an error message to the Stata console
			SFIToolkit.errorln(msg.toString());

			// Return generic error code from Stata
			return 198;

		} else if ("real".equals(Mata.getMataEltype(this.matName))) {

			// If Matrix is of type real, use the method from the Stata API
			// that returns a real value
			return Mata.getMataRealAt(this.matName, colid, rowid);

		} else if ("complex".equals(Mata.getMataEltype(this.matName))) {

			// If the matrix is of type complex, use the method that returns
			// a complex type value
			return Mata.getMataCompAt(this.matName, colid, rowid);

		} else if ("string".equals(Mata.getMataEltype(this.matName))) {

			// If the matrix is of type String, use the method that returns a
			// String value
			return Mata.getMataStringAt(this.matName, colid, rowid);

		} else {

			// If these cases are all false, create a generic error message
			StringBuilder msg = new StringBuilder();

			// Append the generic message to the StringBuilder object
			msg.append("An unidentified error occurred while attempting to " +
					"read the matrix ").append(this.matName)
					.append(".\nNo matrix will be returned.");

			// Return the error message to the Stata console
			SFIToolkit.errorln(msg.toString());

			// And return a generic error code
			return 198;

		} // End IF/ELSE Blocks for Mata method dispatch

	} // End of getMatrixData method declaration

} // End of MataMatrix object declaration