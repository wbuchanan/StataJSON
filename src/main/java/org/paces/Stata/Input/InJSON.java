package org.paces.Stata.Input;

import com.fasterxml.jackson.databind.*;
import com.stata.sfi.*;
import org.paces.Stata.Input.Loaders.*;

import java.io.*;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.StringJoiner;

/**
 * Class that serves as the point of entry for loading JSON data into Stata.
 * Currently contains two "modes" or "interfaces" (not in the Java sense of
 * an interface): <strong>key-value</strong> and <strong>row-value</strong>.
 *
 * <h2>Key-Value</h2>
 * This mode is used to return/store the data in Stata as an n x 2 matrix of
 * key and value pairs.  The methods insheetUrl and insheetFile are used.  These
 * methods create two variables in Stata with the names key and value.  If the
 * values of the payload to be loaded are of varying types the values are all
 * loaded as Strings.  If the values are all the same numeric type, the
 * values are loaded as that numeric type.  Users can specify a regular
 * expression string that will be used to query and return a subset of the
 * available JSON.
 *
 * <h2>Row-Value</h2>
 * This mode is used to return the payload as distinct variables in the data
 * set (e.g., 1 JSON payload = 1 x m elements), or a row vector.  This mode
 * uses the insheetFileToVars and insheetUrlToVars methods.  There are two
 * major differences between this and the key-value mode:
 *
 *                          Naming Conventions
 *                          Data Types
 *
 * Unlike the key-value mode, more than two variables are required to store
 * the data in this mode.  At the moment
 *
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class InJSON {

	/**
	 * A single ObjectMapper class used by all instances to create JsonNode
	 * objects from the file on disk and/or HTTP request
	 */
	private static ObjectMapper mapper = new ObjectMapper();

	private static MappingJsonFactory jsonFactory = new MappingJsonFactory(mapper);

	/**
	 * A JsonNode object that will be initialized when a method in this class
	 * gets called
	 */
	private static JsonNode rootNode;

	/**
	 * A static Key Value Implementation object used if data needs to be
	 * loaded as a key value pair
	 */
	private static KeyValueImpl kv = new KeyValueImpl();
	private static String emptyObjects = "/Users/billy/Desktop/emptyObjectTest.json";
	private static String places = "/Users/billy/Desktop/placesExample.json";
	private static String waypoints = "/Users/billy/Desktop/waypointsResponse.json";
	private static String bigJSON = "/Users/billy/Desktop/Programs/Java/Stata/src/main/java/resources/legiscanPayload.json";
	private static String jcIssue = "/Users/billy/Desktop/Programs/Java/Stata/cannerTest.json";
	private static String issue2b = "/Users/billy/Desktop/Programs/Java/Stata/issue2Test2.json";
	private static String jsonioOutput = "/Users/billy/Desktop/Programs/Java/Stata/jsonioOutput.json";

	public static void main(String[] args) {

		try {
			new InJSON(issue2b);
			new InJSON(jsonioOutput);
			new InJSON(jcIssue);
//			new InJSON(args[0]);
//			new InJSON(bigJSON);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	*  Class constructor.  Used when testing the FlatJSON class
	*  @param fileName A string containing a fully qualified file path
	*  @throws IOException An exception thrown if the file is not found
	 */
	public InJSON(String fileName) throws IOException {
		File json = new File(fileName);
		rootNode = mapper.readTree(json);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String start = dateFormat.format(Calendar.getInstance().getTime());
		FlatStataJSON nodeMap = new FlatStataJSON(rootNode, 0);
		nodeMap.flatten();
		// queryKey("/routes_1/legs_2/*")
		List<String> keys = nodeMap.queryKey(".*");
		StataTypeMap types = kv.sameType(keys, nodeMap.getTypeMap());
		//kv.asKeyValue(types, keys, nodeMap);
/*		for(String key : nodeMap.queryKey(".*")) {
			StringJoiner sj = new StringJoiner("\t");
			sj.add("Key = ").add(key).add("Value =").add(nodeMap.get(key).toString());
			System.out.println(sj.toString());
		}
		for(Integer idx : nodeMap.queryIndex(".*")) {
			System.out.println("Index = " + idx.toString());
		}
		String end = dateFormat.format(Calendar.getInstance().getTime());
		System.out.println("Started Job at: " + start);
		System.out.println("Ended Job at : " + end);
		System.out.println(String.valueOf(nodeMap.getLineage().size()) + " " +
			"elements total");
*/
		for(String i : nodeMap.getLineage()) System.out.println(i);
		//for(String i : nodeMap.getTypeMap()) System.out.println(i);
	}


	/**
	 * Method used to read JSON payload from a URL into Stata in a key/value
	 * dataset
	 * @param args Only takes a single argument (e.g., the URL with the payload)
	 * @return Returns a success/failure indicator
	 */
	public static int insheetUrl(String[] args) {
		try {
			URL site = new URL(args[0]);
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			nodeMap.flatten();
			insheetLoadKeyValue(nodeMap, args[1]);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Method used to read JSON payload from a File into Stata in a key/value
	 * dataset
	 * @param args Only takes a single argument (e.g., the URL with the payload)
	 * @return Returns a success/failure indicator
	 */
	public static int insheetFile(String[] args) {
		try {
			File site = new File(args[0]);
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			nodeMap.flatten();
			Macro.setLocal("totalelements", String.valueOf(nodeMap.getNumberOfElements()));
			insheetLoadKeyValue(nodeMap, args[1]);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Method called by insheet URL and File methods to process and load the
	 * data into Stata
	 * @param nodeMap A FlatJSON object
	 * @param pattern A regular expression or empty string used to identify
	 *                   the JsonNode objects to return to the user
	 */
	private static void insheetLoadKeyValue(FlatStataJSON nodeMap, String pattern) {
		List<String> keys = nodeMap.queryKey(pattern);
		StataTypeMap types = kv.sameType(keys, nodeMap.getTypeMap());
		kv.asKeyValue(types, keys, nodeMap);
		Macro.setLocal("totalkeys", String.valueOf(nodeMap.getTypeMap().size()));
	}

	/**
	 * Method used to parse JSON from a file and load into a single row in
	 * the data set in memory
	 * @param args A String array of arguments passed from the javacall
	 *                command in Stata
	 * @return Returns a success/failure indicator
	 */
	public static int insheetFileToVars(String[] args) {
		try {
			File site = new File(args[0]);
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			nodeMap.flatten();
			Macro.setLocal("totalelements", String.valueOf(nodeMap.getNumberOfElements()));
			insheetLoadRowValue(nodeMap, args[1], Integer.parseInt(args[2]),
				args[3]);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * Method used to parse JSON from URL and load into a single row
	 * @param args A String array of arguments passed from the javacall
	 *                command in Stata
	 * @return Returns a success/failure indicator
	 */
	public static int insheetUrlToVars(String[] args) {
		try {
			URL site = new URL(args[0]);
			mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			nodeMap.flatten();
			Macro.setLocal("totalelements", String.valueOf(nodeMap.getNumberOfElements()));
			insheetLoadRowValue(nodeMap, args[1], Integer.parseInt(args[2]), args[3]);
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
	}


	/**
	 * Method used to load the flattened JSON object as a single row vector
	 * in the Stata DataSet in memory.
	 * @param nodeMap The Flattened JSON Object
	 * @param pattern The query pattern from the user
	 * @param obid The observation ID where these data will be stored
	 * @param stubname A string used to construct variable names by appending
	 *                    ID/Iterator values as a suffix
	 */
	private static void insheetLoadRowValue(FlatStataJSON nodeMap,
	                                       String pattern,
	                                       Integer obid,
	                                       String stubname) {
		List<String> keys = nodeMap.queryKey(pattern);
		new RowValueImpl(keys, nodeMap, obid, stubname);
	}

}
