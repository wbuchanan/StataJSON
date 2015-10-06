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
	public Record getData(String type, Long obid, Meta metaob) {

		if ("byte".equals(type)) {
			return new DataRecordByteArray(obid, metaob);
		} else if ("int".equals(type)) {
			return new DataRecordIntArray(obid, metaob);
		} else if ("long".equals(type)) {
			return new DataRecordLongArray(obid, metaob);
		} else if ("double".equals(type)) {
			return new DataRecordDoubleArray(obid, metaob);
		} else if ("string".equals(type)) {
			return new DataRecordStringArray(obid, metaob);
		} else {
			return new DataRecord(obid, metaob);
		}

	}
}
