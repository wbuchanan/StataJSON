package org.paces.Stata.Ingest;

import com.fasterxml.jackson.databind.*;

import java.io.*;
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

	private ObjectMapper mapper = new ObjectMapper();
	private MappingJsonFactory jsonFactory = new MappingJsonFactory(mapper);
	private JsonNode rootNode;
	private static String emptyObjects =
		"/Users/billy/Desktop/emptyObjectTest.json";
	private static String waypoints =
		"/Users/billy/Desktop/waypointsResponse.json";
	public static void main(String[] args) {

		try {
			new InJSON(waypoints);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public InJSON(String fileName) throws IOException {
		File json = new File(fileName);
		this.rootNode = this.mapper.readTree(json);
		StJSON nodeMap = new StJSON(this.rootNode, 0);
		for(String key : nodeMap.keySet()) {
			StringJoiner sj = new StringJoiner("\t");
			sj.add("Key = ").add(key).add("Value =").add(nodeMap.get(key).toString());
			System.out.println(sj.toString());
		}
	}



}
