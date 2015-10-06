package org.paces.Stata.Data;

import com.stata.sfi.Data;
import com.stata.sfi.Macro;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata DataSet Class Object</h2>
 * <p>A 2d Array of Double containing the data from the active data set in
 * memory.</p>
 */
public class DataSetDoubleArrays implements StataData {
	
	/***
	 * A new Meta object
	 */
	public Meta metaob;
	
	/***
	 * The name of the data set in memory to be converted to a JSON object
	 */
	public String filename;
	
	/***
	 * A 2d array of Double objects
	 */
	public Double[][] stataDataSet;
	
	/***
	 * Generic constructor method for the class
	 * @param metaobject A Meta class object containing metadata for the
	 *                      Stata dataset.
	 */
	public DataSetDoubleArrays(Meta metaobject) {
		
		// Set the meta object to the value passed to the constructor
		this.metaob = metaobject;
		
		// Set the name variable
		setFileName();
		
		// Builds the data object
		setData();
		
	} // End constructor declaration
	
	/***
	 * Generic Setter method for the name of the dataset object
	 */
	public void setFileName() {
		
		// Store the value of `"`c(filename)'"' as a Java string
		String nm = Macro.getLocalSafe("c(filename)");
		
		// If the dataset name is not empty
		if (!nm.isEmpty()) {
			
			// Assign the Stata file name as the name of the object
			this.filename = nm;
			
		} else {
			
			// Otherwise set a generic name
			this.filename = "Stata Data Set";
			
		} // End IF/ELSE Block for object name
		
	} // End declaration of setname method
	
	/***
	 * Method to store Stata dataset as a 2d Array of Double objects.  This is
	 * used for passing the data set to objects/methods in the com.itemanalysis
	 * .psychometrics package to expand IRT modeling options in Stata.
	 */
	@Override
	public void setData() {
		
		// Initialize container to ID the observation and contains a Map
		// object with key/value pairs
		Double[][] obs = new Double[metaob.obsindex.size()][];
		
		for (int i = 0; i < metaob.obsindex.size(); i++) {
			
			// Loop over the variable indices
			for (int j = 0; j < metaob.varindex.size(); j++) {
				
				// Check to see if value is missing
				if (Data.isValueMissing(Data.getNum(metaob.getVarindex(j), (long) i))) {
					
					// If value is missing, set value to -1.0
					obs[i][j] =  -1.0;
					
				} else {
					
					// Convert numeric variables to string
					obs[i][j] = Data.getNum(metaob.getVarindex(j), (long) i);
					
				} // End ELSE Block for non-missing values
				
			} // End of Loop over variable
			
		} // End of Loop over observations
		
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
	public String getFileName() {
		
		// Returns the name of the Stata dataset (or generic placeholder)
		// used to construct a JSON object
		return this.filename;
		
	} // End of getName method declaration
	
} // End Class declaration


