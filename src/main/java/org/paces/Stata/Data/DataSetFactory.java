package org.paces.Stata.Data;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h1>Factory Object to Create Data Set Objects</h1>
 * <p>Object has a single method (getData) that takes a single string
 * argument for the dispatch in addition to the arugments to be passed to
 * the data set constructors. </p>
 */
public class DataSetFactory {

	/***
	 * Constructor method for the class
	 */
	public DataSetFactory() {
	}

	/***
	 * Method to create a DataSet class object
	 * @param type A string argument defining what type of Record class
	 *                object to create
	 * @param metaob An object of class Meta used to define the
	 *                  observation/variable indices and metadata overall
	 * @return A Record class object of type defined by the user.  An empty
	 * string will return the object used for JSON serialization
	 */
	public StataData getData(String type, Meta metaob) {

		if ("byte".equals(type)) {
			return new DataSetByteArrays(metaob);
		} else if ("int".equals(type)) {
			return new DataSetIntArrays(metaob);
		} else if ("long".equals(type)) {
			return new DataSetLongArrays(metaob);
		} else if ("double".equals(type)) {
			return new DataSetDoubleArrays(metaob);
		} else if ("string".equals(type)) {
			return new DataSetStringArrays(metaob);
		} else {
			return new DataSet(metaob);
		}

	}
}
