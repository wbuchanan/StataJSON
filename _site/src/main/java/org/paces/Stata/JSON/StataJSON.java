package org.paces.Stata.JSON;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.*;
import org.paces.Stata.DataRecords.DataRecord;
import org.paces.Stata.DataSets.DataSet;
import org.paces.Stata.MetaData.Meta;

import java.io.*;
import java.nio.charset.*;
import java.util.*;


/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata JSON Serializer</h2>
 * <p>Objects and methods to create JSON representation of Stata data. </p>
 */
public class StataJSON {
	
	/**
	 * Member containing the maximum length used to return JSON object to local
	 */
	private static Integer macroLength;

	public static Charset charset;
	public static final String jsOpen = "var stataData = ";
	public static final String jsClose = " ; ";
	public static Boolean asJavaScriptVar;

	private static final String[] workAround = {""};

	/**
	 * New object mapper to parse JSON
	 */
	private static ObjectMapper themap = new ObjectMapper();


	/***
	 * Main entry point to application
	 * @param args Arguments passed from javacall
	 */
	public static void main(String[] args) {
		
	}

	/***
	 * Method to print the all data from the dataset currently in memory
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 */
	public static int printAll(String[] args) throws JsonProcessingException,
			NullPointerException {

		// Create a new StataAllToJSON Object
		StataAllToJSON allData = new StataAllToJSON(args);
		
		macroLength = new Integer(Macro.getLocalSafe("maxlen"));

		// Print the data object to the Stata Console
		toJSON(allData, macroLength);

		// Garbage collection for the object
		allData = null;

		// Returns success code
		return 0;

	} // End of method declaration to print all data

	/***
	 * Method to print the data for a single record to the console as a JSON
	 * formatted string
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 */
	public static int printRecord(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(workAround);

		// Check to make sure there is only a single element
		List<Number> obs = dbg.getObsindex();

		// Gets the maximimum macro length
		macroLength = new Integer(Macro.getLocalSafe("maxlen"));

		String appender;

		// Creates comma delimiter for records
		if (obs.size() > 1) appender = ", ";

		// Otherwise adds a blank string after the object
		else appender = "";

		// Gets the maximimum macro length
		macroLength = new Integer(Macro.getLocalSafe("maxlen"));

		// Display an opening array bracket
		SFIToolkit.display("[");

		// Loops over all the potential observations
		for (Number obid : obs) {

			// Initialize a new DataRecord object with the data for a given
			// observation
			DataRecord x = new DataRecord(obid.intValue(), dbg);

			// Print the resulting data record to the console
			toJSON(x, macroLength);

			// For all but the last record, append the appender value
			if (!obid.equals(obs.get(obs.size() - 1))) SFIToolkit.displayln(appender);

		} // End of loop

		// Display the array closing bracket
		SFIToolkit.display("]");

		dbg = null;

		// Return a success indicator
		return 0;

	} // End printRecord method declaration

	/***
	 * Method to print the data for the dataset in memory to the console
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 */
	public static int printData(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(workAround);

		// Initialize a new StataData object
		DataSet stataData = new DataSet(dbg);
		
		macroLength = new Integer(Macro.getLocalSafe("maxlen"));
		
		// Print the resulting data record to the console
		toJSON(stataData, macroLength);

		stataData = null;
		dbg = null;

		// Return a success indicator
		return 0;

	} // End printData method declaration

	/***
	 * Method to print the data for a single record to the console as a JSON
	 * formatted string
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	public static int printRecordToFile(String[] args) throws
		IOException, JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(workAround);

		// Check to make sure there is only a single element
		List<Number> obs = dbg.getObsindex();

		// Gets the maximimum macro length
		macroLength = new Integer(Macro.getLocalSafe("maxlen"));

		File nfile = new File(Macro.getLocalSafe("filenm"));

		nfile.createNewFile();

		// New File object
		FileOutputStream jsonOutput = new FileOutputStream(nfile, true);

		// Loops over all the potential observations
		for (Number obid : obs) {

			// Initialize a new DataRecord object with the data for a given
			// observation
			DataRecord x = new DataRecord(obid.intValue(), dbg);

			// Print the resulting data record to the console
			toJSON(x, jsonOutput, macroLength);

		} // End of loop

		// Close the open file connection
		jsonOutput.close();

		dbg = null;
		jsonOutput = null;

		// Return a success indicator
		return 0;

	} // End printRecord method declaration

	/***
	 * Method to print the data for the dataset in memory to the console
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 */
	public static int printDataToFile(String[] args) throws
			JsonProcessingException, IOException {

		// Create a new Metadata object
		Meta dbg = new Meta(workAround);

		// Create a new StataData object and print all data to the Stata console
		DataSet stataData = new DataSet(dbg);

		macroLength = new Integer(Macro.getLocalSafe("maxlen"));
		
		// New File object
		FileOutputStream jsonOutput = new FileOutputStream(Macro.getLocalSafe("filenm"), true);

		// Print the resulting data record to the console
		toJSON(stataData, jsonOutput, macroLength);

		// Close the file connection
		jsonOutput.close();

		stataData = null;
		dbg = null;

		// Return a success indicator
		return 0;

	} // End printData method declaration

	/***
	 * Method to print the all data from the dataset currently in memory
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	public static int printAllToFile(String[] args) throws IOException,
			JsonProcessingException {

		// Create a new StataAllToJSON Object
		StataAllToJSON allData = new StataAllToJSON(args);

		macroLength = new Integer(Macro.getLocalSafe("maxlen"));
		
		// New File object
		FileOutputStream jsonOutput = new FileOutputStream(Macro.getLocalSafe("filenm"), true);

		// Print the data object to the Stata Console
		toJSON(allData, jsonOutput, macroLength);

		// Close the file connection
		jsonOutput.close();

		// Garbage collection for the object
		allData = null;

		// Returns success code
		return 0;

	} // End of method declaration to print all data

	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @param maclen Maximum Macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	public static void toJSON(DataRecord observation, Integer maclen) throws
			JsonProcessingException {

		// Gets the data component from the data record object
		Map<String, Object> theData = observation.getData();

		// Gets the string value of the data converted as a JSON object
		String thejson = themap.writeValueAsString(theData);

		// Tests the length of the JSON object
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		} // Ends the IF Block
			
		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(theData));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	public static void toJSON(DataRecord observation, FileOutputStream filename, Integer maclen) throws
			IOException, JsonProcessingException {

		// Gets the data component from the data record object
		Map<String, Object> theData = observation.getData();

		// Gets the string value of the data converted as a JSON object
		String thejson = themap.writeValueAsString(theData);

		// Tests for the length of the string
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		} // End of IF Block
		
		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, observation);

	} // End toJSON method declaration for single observation

	public static void screenPrint(ObjectMapper object, Boolean jsVariable) {

	}

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	public static void toJSON(DataSet stataData, Integer maclen) throws
			JsonProcessingException {

		String thejson = themap.writeValueAsString(stataData);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(stataData));
		
	} // End toJSON method declaration for dataset

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @param maclen Maximum Macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	public static void toJSON(DataSet stataData, FileOutputStream filename, Integer maclen) throws
			IOException, JsonProcessingException {

		String thejson = themap.writeValueAsString(stataData);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, stataData);

	} // End toJSON method declaration for dataset


	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @param maclen maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	public static void toJSON(StataAllToJSON metaobject, Integer maclen) throws
			JsonProcessingException {

		String thejson = themap.writeValueAsString(metaobject);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(metaobject));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	public static void toJSON(StataAllToJSON metaobject, FileOutputStream filename, Integer maclen) throws
			IOException, JsonProcessingException {

		String thejson = themap.writeValueAsString(metaobject);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to screen
		themap.writerWithDefaultPrettyPrinter().writeValue(filename, metaobject);

	} // End toJSON method declaration for single observation


	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
/*
	public static void toJSON(List<Object> thedata, Integer maclen) throws
			JsonProcessingException {

		String thejson = themap.writeValueAsString(thedata);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(thedata));

	} // End toJSON method declaration for List of Object types
*/

	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
/*
	public static void toJSON(List<Object> thedata, FileOutputStream filename, Integer maclen) throws
			IOException, JsonProcessingException {

		String thejson = themap.writeValueAsString(thedata);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, thedata);

	} // End toJSON method declaration for List of Object types
*/

	/***
	 * Method to print all data and metadata to the Stata console
	 * @param allData A List of Object types
	 * @param maclen Maximum macro length
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An exception when null objects are
	 * referenced
	 */
/*
	public static void toJSON(Map<String, Object> allData, Integer maclen) throws
			JsonProcessingException, NullPointerException {

		String thejson = themap.writeValueAsString(allData);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(allData));

	} // End method declaration
*/

	/***
	 * Method to print all data and metadata to the Stata console
	 * @param allData A List of Object types
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @param maclen Maximum macro length
	 * @throws NullPointerException An exception when null objects are
	 * referenced
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
/*
	public static void toJSON(Map<String, Object> allData, FileOutputStream filename, Integer maclen)
			throws
			IOException, NullPointerException {

		String thejson = themap.writeValueAsString(allData);
		
		if (thejson.length() <= maclen) {
			
			// Return the JSON string in a local macro
			Macro.setLocal("thejson", thejson);
			
		}
		
		// Print JSON to screen
		themap.writerWithDefaultPrettyPrinter().writeValue(filename, allData);

	} // End method declaration
*/
} // End of StataJSON object declaration