package org.paces.Stata.Ingest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.MissingNode;

import java.io.*;
import java.util.*;
import static org.paces.Stata.Ingest.NodeUtils.*;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class StJSON {

	private static LinkedList<String> emptyList = new LinkedList<>();

	private Map<String, JsonNode> jsonData;

	public StJSON(JsonNode node) {
		this(node, 0, emptyList);
	}

	public StJSON(JsonNode node, Integer currentLevel) {
		this(node, currentLevel, emptyList);
	}

	public StJSON(JsonNode node, Integer currentLevel, LinkedList<String> parent) {
		try {
			this.jsonData = flatten(node, currentLevel, parent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Public method used handle recasting ObjectNode types to JsonNode types
	 * to overload the descent method
	 * @param node An JsonNode which will be descended into to determine
	 *                the depth of nested objects
	 * @param currentLevel The level of the descent where this node occurs
	 * @param parent A string containing the field name for the node; the
	 *                  first level should always be named root.  Beyond
	 *                  this, the format of this string will be:
	 *               root/child_#/grandchild_#/greatgrandchild_#/etc...
	 *               Where the number appended to the string indicates the
	 *               depth of the specific node
	 * @return a Map object containing the JsonNodes keyed by the generation
	 * string.
	 */
	public Map<String, JsonNode> flatten(JsonNode node, Integer currentLevel,
	                       LinkedList<String> parent) throws IOException {

		List<JsonNode> children = nodeList(node);
		List<String> fieldNames = fieldList(node);
		Map<String, JsonNode> nodeMap = new LinkedHashMap<>();
		LinkedList<String> lineage = new LinkedList<>();
		lineage.addAll(parent);
		Integer depth = currentLevel;
		Boolean.valueOf(lineage.size() == currentLevel);

		// Iterate over the child nodes based on their numeric indices
		for(Integer i = 0; i < children.size(); i++) {

			// Create incremented node ID
			Integer nodeID = i + 1;

			// Need to handle case of null arrays and arrays with terminal
			// values correctly.  Use empty string for null arrays
			if (node.isArray() && containsContainers(children.get(i))) {

				nodeMap.putAll(flatten(children.get(i), depth, checkName(lineage, nodeID)));


			} else if (containsContainers(children.get(i))) {

				nodeMap.putAll(flatten(children.get(i), depth, checkName(lineage, fieldNames.get(i))));

				lineage.pop();

			} else if (children.get(i).isObject() && !containsContainers(children.get(i))) {

				nodeMap.putAll(flatten(children.get(i), depth, checkName(lineage, fieldNames.get(i), depth)));
				lineage.pop();

			} else if (children.get(i).isArray() && !containsContainers(children.get(i))) {

				nodeMap.putAll(flatten(children.get(i), depth, checkName(lineage, fieldNames.get(i))));
				lineage.pop();

			} else  {
				String field;
				if (fieldNames.size() > 0) field = fieldNames.get(i);
				else if (fieldNames.size() == 0) field = "element_" + nodeID.toString();
				else field = lineage.pop() + "_" + depth.toString();
				nodeMap.putAll(processTerminalNode(lineage, field, children.get(i), depth + 1));

			}
		}

		return nodeMap;
	}

	protected JsonNode checkEmptyNodes(JsonNode jn) {
		if ((jn.isArray() || jn.isObject()) && jn.size() == 0) return MissingNode.getInstance();
		else return jn;
	}

	/**
	 * Creates the Map object that contains a key value pair of the full node
	 * lineage and the node object
	 * @param generations A LinkedList of Strings that holds the descent
	 *                       lineage through the JSON object
	 * @param thisField The name of this terminal node field
	 * @param theNode The object containing the datum
	 * @param depth the descent depth of the current node used to add numeric
	 *                 ID to root level nodes
	 * @return A Map where the key contains the descent tree with the field
	 * name appended to it.
	 */
	protected Map<String, JsonNode> processTerminalNode(LinkedList<String> generations,
                                                       String thisField,
                                                       JsonNode theNode,
	                                                   Integer depth) {

		// Creates a new Map object used to create a key/value pair object
		Map<String, JsonNode> nodeMap = new HashMap<>();

		// Creates the generation string which will have the field name appended
		String genString = getGenerationString(generations.descendingIterator());

		// If the generation name isn't empty, add the path delimiter before
		// the field name as the key and add the JsonNode object as the value
		if (!genString.isEmpty()) nodeMap.put(genString + "/" + thisField, checkEmptyNodes(theNode));

		// Otherwise add the field name with a root delimiter before it
		else nodeMap.put("/" + thisField + "_" + depth.toString(), checkEmptyNodes(theNode));

		return nodeMap;

	}

	/**
	 * Method used to process and create the generation string used to identify
	 * the lineage of a terminal node
	 * @param iter An iterator created from the parent list object (use the
	 *                descendingIterator() method on the parent list object).
	 * @return Returns a String object with all the generations with path
	 * delimiters separating them
	 */
	public String getGenerationString(Iterator<String> iter) {

		// Create a new string joiner object
		StringJoiner genString = new StringJoiner("/");

		// As long as there are more values
		while(iter.hasNext()) {

			// Get the next string value
			String var = iter.next();

			// If it isn't empty add it to the string joiner object
			if (!var.isEmpty()) genString.add(var);

		} // End While loop over string iterator

		// Convert the string joiner to a string and return it
		if (!genString.toString().isEmpty()) return "/" + genString.toString();

		// If the generation string is empty just return the empty string
		else return genString.toString();

	} // End Method declaration

	/**
	 * Method used to implement logic related to lineage of data element
	 * being represented in the LinkedList of string elements
	 * @param lineage The LinkedList of String elements containing the field
	 *                   names from all generations prior to the current
	 *                   generation.
	 * @param fieldName The name of the field to add to the generation string
	 * @return Returns the LinkedList with the appropriate handling of the
	 * new field name being added to the stack.
	 */
	private LinkedList<String> checkName(LinkedList<String> lineage,
	                                     String fieldName) {

		// Checks to see if the list contains any data
		if (lineage.size() > 0) {

			// Pops the top name off the stack and removes any depth
			// indicators so they can be replaced with current value
			String curgen = lineage.pop();

			// If the previous field name is not the same as this one, push it
			// back onto the stack
			if (!curgen.replaceAll("(.*)(_[0-9]+)$", "$1").equals(fieldName)) lineage.push(curgen);

			// Add the field name to the stack
			lineage.push(fieldName);

		} // End IF Block when there are elements in the list

		// Adds field name to the list
		else lineage.push(fieldName);

		// Returns the generation string container
		return lineage;

	} // End Method declaration

	/**
	 * Method used to implement logic related to lineage of data element
	 * being represented in the LinkedList of string elements
	 * @param lineage The LinkedList of String elements containing the field
	 *                   names from all generations prior to the current
	 *                   generation.
	 * @param fieldName The name of the field to add to the generation string
	 * @param depth An Integer value indicating the depth of the descent into
	 *                 the JSON Tree
	 * @return Returns the LinkedList with the appropriate handling of the
	 * new field name being added to the stack.
	 */
	private LinkedList<String> checkName(LinkedList<String> lineage,
	                                            String fieldName,
	                                            Integer depth) {

		// Test the side of the generation string container
		if (lineage.size() > 0) {

			// Pops the top name off the stack and removes any depth
			// indicators so they can be replaced with current value
			String curgen = lineage.pop();

			// If the previous generation name is not the same as the current
			// field name, push the previous generation name back onto the stack
			if (!curgen.replaceAll("(.*)(_[0-9]+)$", "$1").equals(fieldName)) lineage.push(curgen);

			// If it was the same name, just add the field name
			lineage.push(fieldName);

		} // End IF Block for pre-populated generation string

		// If the generation string has no values, add the depth value to the
		// field name and add it to the generation string
		else lineage.push(fieldName + "_" + depth.toString());

		// Return the generation string container
		return lineage;

	} // End of Method declaration

	/**
	 * Method used to implement logic related to lineage of data element
	 * being represented in the LinkedList of string elements
	 * @param lineage The LinkedList of String elements containing the field
	 *                   names from all generations prior to the current
	 *                   generation.
	 * @param nodeID An integer value used to identify container elements
	 *                  stored in an array object
	 * @return Returns the LinkedList with the appropriate handling of the
	 * new field name being added to the stack.
	 */
	private LinkedList<String> checkName(LinkedList<String> lineage, Integer nodeID) {

		// Pops the top name off the stack and removes any depth
		// indicators so they can be replaced with current value
		String curgen = lineage.pop().replaceAll("(.*)(_[0-9]+)$", "$1");

		// If the previous generation name isn't equal to the generation
		// prior to it, push it back onto the stack with a new numeric ID
		if (!curgen.equals(lineage.peek())) lineage.push(curgen + "_" + nodeID.toString());

		// If it is the same it gets pushed back onto the stack without the
		// numeric ID value appended
		else lineage.push(curgen);

		// Return the generation string container
		return lineage;

	} // End of Method declaration

	/**
	 * Method that returns the keys for the underlying Map object
	 * @return A Set containing all of the Map object's Key Elements
	 */
	public Set<String> keySet() {
		return this.jsonData.keySet();
	}

	/**
	 * Method used to retrieve individual JSON Datum
	 * @param key The key used to identify the node to retrieve
	 * @return A JSON Node object identified by the string passed to the method
	 */
	public JsonNode get(String key) {
		return this.jsonData.get(key);
	}
}
