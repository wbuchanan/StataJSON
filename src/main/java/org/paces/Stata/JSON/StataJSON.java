package org.paces.Stata.JSON;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.Macro;
import com.stata.sfi.SFIToolkit;
import org.paces.Stata.Data.DataRecord;
import org.paces.Stata.Data.DataSet;
import org.paces.Stata.Data.Meta;
import org.paces.Stata.Data.StataData;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata JSON Serializer</h2>
 * <p>Objects and methods to create JSON representation of Stata data. </p>
 */
public class StataJSON {

	/***
	 * Main entry point to application
	 * @param args Arguments passed from javacall
	 */
	public static void main(String[] args) {
	}

	/***
	 * Member variable that names the object
	 */
	@JsonProperty("name")
	public static final String name = "StataJSON";


	/***
	 * Method to print the all data from the dataset currently in memory
	 * @param args Passed from javacall function in Stata
	 * @return A success value of 0
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 */
	@JsonCreator
	@JsonPropertyOrder({ "name", "values", "first record id", "last record id",
			"number of records", "variable indices", "number of variables",
			"variable type string", "variable names", "variable labels",
			"value label names", "value labels" })
	public static int printAll(String[] args) throws JsonProcessingException,
			NullPointerException {

		// Create a new StataAllToJSON Object
		StataAllToJSON allData = new StataAllToJSON(args);

		// Print the data object to the Stata Console
		toJSON(allData.getData());

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
	@JsonCreator
	public static int printRecord(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(args);

		// Get the value of the observation to print from the local macro obid
		Long obid = Long.valueOf(Macro.getLocalSafe("obid"));

		// Initialize a new DataRecord object with the data for a given
		// observation
		DataRecord x = new DataRecord(obid, dbg);

		// Print the resulting data record to the console
		toJSON(x.getData());

		x = null;
		obid = null;
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
	@JsonCreator
	public static int printData(String[] args) throws
			JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(args);

		// Initialize a new StataData object
		StataData stataData = new DataSet(dbg);

		// Print the resulting data record to the console
		toJSON(stataData);

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
	@JsonCreator
	public static int printRecordToFile(String[] args) throws
			IOException, JsonProcessingException, NullPointerException {

		// Create a new Metadata object
		Meta dbg = new Meta(args);

		// Get the value of the observation to print from the local macro obid
		Long obid = Long.valueOf(Macro.getLocalSafe("obid"));

		// Initialize a new DataRecord object with the data for a given
		// observation
		DataRecord x = new DataRecord(obid, dbg);

		// New File object
		File jsonOutput = new File(Macro.getLocalSafe("filenm"));

		// Print the resulting data record to the console
		toJSON(x.getData(), jsonOutput);

		x = null;
		obid = null;
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
	@JsonCreator
	public static int printDataToFile(String[] args) throws
			JsonProcessingException, IOException {

		// Create a new Metadata object
		Meta dbg = new Meta(args);

		// Create a new StataData object and print all data to the Stata console
		StataData stataData = new DataSet(dbg);

		// New File object
		File jsonOutput = new File(Macro.getLocalSafe("filenm"));

		// Print the resulting data record to the console
		toJSON(stataData, jsonOutput);

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
	@JsonCreator
	public static int printAllToFile(String[] args) throws IOException,
			JsonProcessingException {

		// Create a new StataAllToJSON Object
		StataAllToJSON allData = new StataAllToJSON(args);

		// New File object
		File jsonOutput = new File(Macro.getLocalSafe("filenm"));

		// Print the data object to the Stata Console
		toJSON(allData.getData(), jsonOutput);

		// Garbage collection for the object
		allData = null;

		// Returns success code
		return 0;

	} // End of method declaration to print all data

	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	@JsonCreator
	public static void toJSON(DataRecord observation) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(observation));

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(observation));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a DataRecord object to the Stata console
	 * @param observation A DataRecord class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonCreator
	public static void toJSON(DataRecord observation, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(observation));

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, observation);

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	@JsonCreator
	public static void toJSON(DataSet stataData) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(stataData));

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(stataData));

	} // End toJSON method declaration for dataset

	/***
	 * Method to print a DataSet object to the Stata console
	 * @param stataData A DataSet class object
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonCreator
	public static void toJSON(DataSet stataData, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(stataData));

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, stataData);

	} // End toJSON method declaration for dataset

	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	@JsonCreator
	public static void toJSON(Object metaobject) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(metaobject));

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(metaobject));

	} // End toJSON method declaration for single observation

	/***
	 * Method to print a generic Object type variable
	 * @param metaobject A generic Object type variable
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonCreator
	public static void toJSON(Object metaobject, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(metaobject));

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(metaobject));

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, metaobject);

	} // End toJSON method declaration for single observation


	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 */
	@JsonCreator
	public static void toJSON(List<Object> thedata) throws
			JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(thedata));

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(thedata));

	} // End toJSON method declaration for List of Object types

	/***
	 * Method to print a List of object types to the Stata console
	 * @param thedata A List of Object types
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonCreator
	public static void toJSON(List<Object> thedata, File filename) throws
			IOException, JsonProcessingException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(thedata));

		// Print JSON to file
		themap.writerWithDefaultPrettyPrinter()
				.writeValue(filename, thedata);

	} // End toJSON method declaration for List of Object types

	/***
	 * Method to print all data and metadata to the Stata console
	 * @param allData A List of Object types
	 * @throws JsonProcessingException A processing error thrown by the
	 * Jackson JSON API
	 * @throws NullPointerException An exception when null objects are
	 * referenced
	 */
	@JsonCreator
	public static void toJSON(Map<String, Object> allData) throws
			JsonProcessingException, NullPointerException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(allData));

		// Print JSON to screen
		SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
				.writeValueAsString(allData));

	} // End method declaration

	/***
	 * Method to print all data and metadata to the Stata console
	 * @param allData A List of Object types
	 * @param filename A file object containing the name where the JSON data
	 *                    will be written
	 * @throws NullPointerException An exception when null objects are
	 * referenced
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonCreator
	public static void toJSON(Map<String, Object> allData, File filename) throws
			IOException, NullPointerException {

		// New object mapper to parse JSON
		ObjectMapper themap = new ObjectMapper();

		// Return the JSON string in a local macro
		Macro.setLocal("thejson", themap.writeValueAsString(allData));

		// Print JSON to screen
		themap.writerWithDefaultPrettyPrinter().writeValue(filename, allData);

	} // End method declaration

} // End of StataJSON object declaration
