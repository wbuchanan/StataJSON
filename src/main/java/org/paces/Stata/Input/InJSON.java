package org.paces.Stata.Input;

import com.fasterxml.jackson.databind.*;
import com.stata.sfi.*;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 * Currently set up for testing, but this will be the parent class used to
 * call/create the object that will store the JSON Data.  Methods will be
 * available to provide different options over time (e.g., putting into Stata
 * as two string variables, creating series of variables, etc...).
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class InJSON {

	private static ObjectMapper mapper = new ObjectMapper();
	private static MappingJsonFactory jsonFactory = new MappingJsonFactory(mapper);
	private static JsonNode rootNode;
	private static String emptyObjects =
		"/Users/billy/Desktop/emptyObjectTest.json";
	private static String places =
		"/Users/billy/Desktop/placesExample.json";
	private static String waypoints =
		"/Users/billy/Desktop/waypointsResponse.json";
	private static String bigJSON =
		"/Users/billy/Desktop/Programs/Java/Stata/src/main/java/resources" +
			"/legiscanPayload.json";

	public static void main(String[] args) {

		try {
			new InJSON(waypoints);
//			new InJSON(emptyObjects);
//			new InJSON(args[0]);
//			new InJSON(bigJSON);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
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
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			insheetLoadKeyValue(nodeMap);
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
			rootNode = mapper.readTree(site);
			FlatStataJSON nodeMap = new FlatStataJSON(rootNode);
			insheetLoadKeyValue(nodeMap);
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
	 */
	public static void insheetLoadKeyValue(FlatStataJSON nodeMap) {
		Data.setObsTotal(nodeMap.getLineage().size());
		Data.addVarStr("key", nodeMap.getMaxKeyLength());
		Data.addVarStr("value", nodeMap.getMaxKeyLength());
		Integer key = Data.getVarIndex("key");
		Integer value = Data.getVarIndex("value");
		Integer i = 1;
		for(String j : nodeMap.getLineage()) {
			Data.storeStr(key, i, j);
			Data.storeStr(value, i, nodeMap.get(j).toString());
			i++;
		}
	}

	/**
	 * Class constructor.  Used when testing the FlatJSON class
	 * @param fileName
	 * @throws IOException
	 */
	public InJSON(String fileName) throws IOException {
		File json = new File(fileName);
		rootNode = mapper.readTree(json);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String start = dateFormat.format(Calendar.getInstance().getTime());
		FlatJSON nodeMap = new FlatJSON(rootNode, 0);
		nodeMap.flatten();
		// queryKey("/routes_1/legs_2/*")
		for(String key : nodeMap.queryKey("/routes_1/legs_2/*")) {
			StringJoiner sj = new StringJoiner("\t");
			sj.add("Key = ").add(key).add("Value =").add(nodeMap.get(key).toString());
			System.out.println(sj.toString());
		}
		for(Integer idx : nodeMap.queryIdx("/routes_1/legs_2/*")) {
			System.out.println("Index = " + idx.toString());
		}
		String end = dateFormat.format(Calendar.getInstance().getTime());
		System.out.println("Started Job at: " + start);
		System.out.println("Ended Job at : " + end);
		System.out.println(String.valueOf(nodeMap.getLineage().size()) + " " +
			"elements total");
		// for(String i : nodeMap.getLineage()) System.out.println(i);
		// for(String i : nodeMap.getTypeMap()) System.out.println(i);
	}



}
