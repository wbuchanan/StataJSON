package org.paces.Stata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.Macro;
import com.stata.sfi.SFIToolkit;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class StataJSON {

	public static final String VARLABS = "varlabels";
	public static final String VARNAMES = "varnames";
	public static final String VALLABS = "vallabs";
	public static final String VALLABNAMES = "labelnames";

	// Meta object member variable
	static Meta dbg;

	// Observation index member variable
	static List<Long> obidx;

	/***
	 * Main entry point to application
	 * @param args Arguments passed from javacall
	 */
	public static void main(String[] args) {
	}

	/***
	 * Method to print the data for a single record to the console as a JSON
	 * formatted string
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException
	 * @throws NullPointerException
	 */
	public static int printRecord(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		dbg = new Meta(args);

		// Get the value of the observation to print from the local macro obid
		Long obid = Long.valueOf(Macro.getLocalSafe("obid"));

		// Initialize a new DataRecord object with the data for a given
		// observation
		DataRecord x = new DataRecord(obid);

		// Print the resulting data record to the console
		toJSON(x.getData());

		// Return a success indicator
		return 0;

	} // End printRecord method declaration

	/***
	 * Method to print the data for the dataset in memory to the console
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException
	 * @throws NullPointerException
	 */
	public static int printData(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		dbg = new Meta(args);

		// Initialize a new StataData object
		StataData stataData = new DataSet();

		// Print the resulting data record to the console
		toJSON(stataData);

		// Return a success indicator
		return 0;

	} // End printData method declaration

	/***
	 * Method to print the data for a single record to the console as a JSON
	 * formatted string
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException
	 * @throws NullPointerException
	 * @throws IOException
	 */
	public static int printRecordToFile(String[] args) throws
			IOException, JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		dbg = new Meta(args);

		// Get the value of the observation to print from the local macro obid
		Long obid = Long.valueOf(Macro.getLocalSafe("obid"));

		// Initialize a new DataRecord object with the data for a given
		// observation
		DataRecord x = new DataRecord(obid);

		// New File object
		File jsonOutput = new File(Macro.getLocalSafe("filenm"));

		// Print the resulting data record to the console
		toJSON(x.getData(), jsonOutput);

		// Return a success indicator
		return 0;

	} // End printRecord method declaration

	/***
	 * Method to print the data for the dataset in memory to the console
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException
	 * @throws NullPointerException
	 */
	public static int printDataToFile(String[] args) throws
			JsonProcessingException, IOException {

		// Create a new Metadata object
		dbg = new Meta(args);

		// Create a new StataData object and print all data to the Stata console
		StataData stataData = new DataSet();

		// New File object
		File jsonOutput = new File(Macro.getLocalSafe("filenm"));

		// Print the resulting data record to the console
		toJSON(stataData, jsonOutput);

		// Return a success indicator
		return 0;

	} // End printData method declaration


	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @throws JsonProcessingException
	 */
	public static void toJSON(DataRecord observation) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(observation));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static void toJSON(DataRecord observation, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, observation);

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @throws JsonProcessingException
	 */
	public static void toJSON(DataSet stataData) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(stataData));

	} // End toJSON method declaration for dataset

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static void toJSON(DataSet stataData, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, stataData);

	} // End toJSON method declaration for dataset

	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @throws JsonProcessingException
	 */
	public static void toJSON(Object metaobject) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(metaobject));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static void toJSON(Object metaobject, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, metaobject);

	} // End toJSON method declaration for single observation


	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @throws JsonProcessingException
	 */
	public static void toJSON(List<Object> thedata) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(thedata));

	} // End toJSON method declaration for List of Object types

	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws IOException
	 * @throws JsonProcessingException
	 */
	public static void toJSON(List<Object> thedata, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, thedata);

	} // End toJSON method declaration for List of Object types

	/***
	 * Method to print meta data to the Stata Console
	 * @param args Argument used to define what values to print to the console
	 * @return An integer value of 0 if method succeeds
	 * @throws IOException
	 * @throws NullPointerException
	 */
	@NotNull
	public static int metaToJSON(String[] args) throws
			IOException, NullPointerException {

		// Metadata object
		dbg = new Meta(args);

		// Get variable list
		Variables x = dbg.getStatavars();

		// A generic object to store retrieved meta data
		Object toPrint;

		// Switch statement to store value of the metadata to print to the
		// console
		switch (Macro.getLocalSafe("metaprint")) {

			// For variable labels
			case VARLABS :

				// Store the variable labels
				toPrint = x.getVariableLabels();

				// Break out of this case of the switch statement
				break;

			// For variable names
			case VARNAMES :

				// Store variable names
				toPrint = x.getVariableNames();

				// Break out of this case of the switch statement
				break;

			// For value labels
			case VALLABS :

				// Store value label value/label pairs
				toPrint = x.getValueLabels();

				// Break out of this case of the switch statement
				break;

			// For value label names
			case VALLABNAMES :

				// Store the names of the value labels associated with variables
				toPrint = x.getValueLabelNames();

				// Break out of this case of the switch statement
				break;

			// If the parameter doesn't match one of the earlier values
			default:

				// Store default message
				toPrint = new String("Invalid argument");

				// Break out of this case of the switch statement
				break;

		} // End of Switch statement

		if (Macro.getLocalSafe("filenm").isEmpty()) {

			// Print the requested data to the screen
			toJSON(toPrint);

		} else {

			// New File object
			File jsonOutput = new File(Macro.getLocalSafe("filenm"));

			// Print the requested data to the screen
			toJSON(toPrint, jsonOutput);

		}

		// Return success value
		return 0;

	} // End of metaToJSON method declaration

} // End of StataJSON object declaration
