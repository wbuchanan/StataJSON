package org.paces.Stata.JSON;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.*;
import org.paces.Stata.MetaData.*;

import java.io.*;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata JSON MetaData Serializer</h2>
 * <p>Objects and methods to create JSON representation of Stata metadata. </p>
 */
public class StataMetaToJSON {

	/***
	 * Argument to serialize variable labels as JSON object
	 */
	@JsonIgnore
	public static final String VARLABS = "varlabels";

	/***
	 * Argument to serialize variable names as JSON object
	 */
	@JsonIgnore
	public static final String VARNAMES = "varnames";

	/***
	 * Argument to serialize value labels as JSON object
	 */
	@JsonIgnore
	public static final String VALLABS = "vallabs";

	/***
	 * Argument to serialize value label names as JSON object
	 */
	@JsonIgnore
	public static final String VALLABNAMES = "labelnames";

	/***
	 * Meta object member variable
	 */
	static Meta dbg;

	/***
	 * Observation index member variable
	 */
	@JsonIgnore
	static List<Long> obidx;

	/**
	 * Stores maximum macro length
	 */
	@JsonIgnore
	static Integer macroLength;

	/**
	 * Creates object used to generate JSON
	 */
	@JsonIgnore
	private static ObjectMapper themap = new ObjectMapper();

	/***
	 * Method to print meta data to the Stata Console
	 * @param args Argument used to define what values to print to the console
	 * @return An integer value of 0 if method succeeds
	 * @throws NullPointerException An error thrown for referencing a null
	 * object
	 * @throws IOException An error thrown when attempting to read/write a
	 * local file
	 */
	@JsonGetter
	public static int metaToJSON(String[] args) throws
			IOException, NullPointerException {

		macroLength = new Integer(Macro.getLocalSafe("maxlen"));

		// Metadata object
		dbg = new Meta();

		// Argument passed to method via Stata
		String metaprint = Macro.getLocal("metaprint");

		// Get variable list
		Variables x = dbg.getStatavars();

		// A generic object to store retrieved meta data
		Object toPrint;

		// Switch statement to store value of the metadata to print to the
		// console
		switch (metaprint) {

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
				toPrint = "Invalid argument";

				// Break out of this case of the switch statement
				break;

		} // End of Switch statement

		// String representation of the JSON object
		String theJSON;

		// Check if filenm local macro is empty/set
		if (Macro.getLocalSafe("filenm").isEmpty()) {

			theJSON = themap.writeValueAsString(toPrint);

			if (storeAsMacro(theJSON, macroLength) != 0) {
				SFIToolkit.displayln("The JSON object was too long to be stored in a local macro");
			}

			// Print JSON to screen
			SFIToolkit.display(themap.writerWithDefaultPrettyPrinter()
					.writeValueAsString(toPrint));

		} else {

			// New File object
			FileOutputStream jsonOutput = new FileOutputStream(Macro.getLocalSafe("filenm"));

			theJSON = themap.writeValueAsString(toPrint);

			if (storeAsMacro(theJSON, macroLength) != 0) {
				SFIToolkit.displayln("The JSON object was too long to be stored in a local macro");
			}

			// Print JSON to file
			themap.writerWithDefaultPrettyPrinter()
					.writeValue(jsonOutput, toPrint);

			// Close file connection
			jsonOutput.close();

		} // End IF/ELSE Block to test for filenm argument from Stata

		// Return success value
		return 0;

	} // End of metaToJSON method declaration

	/**
	 * Method to test whether JSON object can be stored in a macro
	 * @param printJSON The string value of the JSON object
	 * @param maxlen The maximum number of characters that can be stored in a macro on the users machine
	 * @return Returns an integer valued C-style success code (e.g., 0 for success, !0 for error)
	 */
	private static int storeAsMacro(String printJSON, Integer maxlen) {
		if (printJSON.length() <= maxlen) {
			Macro.setLocal("thejson", printJSON);
			return 0;
		} else {
			Macro.setLocal("thejson", "");
			return 1;
		}
	}

} // End of Object declaration