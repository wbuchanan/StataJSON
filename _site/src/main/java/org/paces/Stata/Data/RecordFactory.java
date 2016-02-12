package org.paces.Stata.Data;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h1>Factory Object to Create Data Record Objects</h1>
 * <p>Object has a single method (getRecord) that takes a single string
 * argument for the dispatch in addition to the arugments to be passed to
 * the data record constructors. </p>
 */
public class RecordFactory {

	/***
	 * Constructor method for the class
	 */
	public RecordFactory() {
	}

	/***
	 * Method to create a Record class object
	 * @param type A string argument defining what type of Record class
	 *                object to create
	 * @param obid An observation index defining which values to retrieve
	 *                from the Stata dataset in memory
	 * @param metaob An object of class Meta used to define the
	 *                  observation/variable indices and metadata overall
	 * @return A Record class object of type defined by the user.  An empty
	 * string will return the object used for JSON serialization
	 */
	public Record recordFactory(String type, Long obid, Meta metaob) {

		// Dispatch class creation based on value of the argument passed to
		// the type parameter.
		switch (type) {

			// If type argument is 'byte'
			case "byte":

				// Return a Byte Array class data record
				return new DataRecordByteArray(obid, metaob);

			// If type argument is 'int'
			case "int":

				// Return an Integer Array class data record
				return new DataRecordIntArray(obid, metaob);

			// If type argument is 'long'
			case "long":

				// Return a Long Array class data record
				return new DataRecordLongArray(obid, metaob);

			// If type argument is 'double'
			case "double":

				// Return a Double Array class data record
				return new DataRecordDoubleArray(obid, metaob);

			// If type argument is 'string'
			case "string":

				// Return a String Array class data record
				return new DataRecordStringArray(obid, metaob);

			// If type argument is anything else
			default :

				// Return a Data record used for JSON serialization
				return new DataRecord(obid, metaob);

		} // End switch statement

	} // End Method declaration

} // End Factory class declaration
