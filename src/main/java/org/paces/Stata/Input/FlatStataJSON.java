package org.paces.Stata.Input;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.*;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class FlatStataJSON extends FlatJSON {

	/**
	 * Member containing strings to map individual datum to Stata types.
	 */
	private LinkedList<String> nodeTypes = new LinkedList<>();

	/**
	 * Member that holds the length of the key strings to set the string
	 * variable length
	 */
	private LinkedList<Integer> keyLengths = new LinkedList<>();

	/**
	 * Class constructor for the FlatJSON type
	 */
	public FlatStataJSON() {
		super();
	}

	/**
	 * Class constructor for the FlatJSON type
	 *
	 * @param node A JsonNode object that will be flattened/parsed
	 */
	public FlatStataJSON(JsonNode node) {
		super(node);
	}

	/**
	 * Class constructor for the FlatJSON type
	 *
	 * @param node         A JsonNode object that will be flattened/parsed
	 * @param currentLevel The current depth of the descent through the
	 */
	public FlatStataJSON(JsonNode node, Integer currentLevel) {
		super(node, currentLevel);
	}

	/**
	 * Class constructor for the FlatJSON type
	 *
	 * @param node         A JsonNode object that will be flattened/parsed
	 * @param currentLevel The current depth of the descent through the JsonNode
	 *                     elements
	 * @param parent       A LinkedList of String objects that contain the names of
	 */
	public FlatStataJSON(JsonNode node, Integer currentLevel, LinkedList<String> parent) {
		super(node, currentLevel, parent);
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
	@Override
	protected Map<String, JsonNode> processTerminalNode(LinkedList<String> generations,
	                                                    String thisField,
	                                                    JsonNode theNode,
	                                                    Integer depth) {

		// Creates a new Map object used to create a key/value pair object
		Map<String, JsonNode> nodeMap = new HashMap<>();

		// Creates the generation string which will have the field name appended
		String genString = makeGenerationString(generations.descendingIterator());

		String key;

		// If the generation name isn't empty, add the path delimiter before
		// the field name as the key and add the JsonNode object as the value
		if (!genString.isEmpty()) {

			key = genString + "/" + thisField;

			nodeMap.put(genString + "/" + thisField, checkEmptyNodes(theNode));
			this.generations.add(key);
			this.keyLengths.add(key.length());
			setTypeMap(checkEmptyNodes(theNode));
		}

		// Otherwise add the field name with a root delimiter before it
		else {
			key = "/" + thisField + "_" + depth.toString();
			nodeMap.put("/" + thisField + "_" + depth.toString(), checkEmptyNodes(theNode));
			this.generations.add(key);
			this.keyLengths.add(key.length());
			setTypeMap(checkEmptyNodes(theNode));
		}

		return nodeMap;

	}

	/**
	 * Method used to map the JsonNodeTypes onto types that can be created in
	 * Stata.  Both Ints and Longs in Java are equivalent to long types in
	 * Stata.  A short type in Java (e.g., 2-byte integer) is equivalent to
	 * an int in Stata.  Booleans are mapped to bytes and will have
	 * true/false converted to binary indicators.
	 * @param value
	 */
	protected void setTypeMap(JsonNode value) {
		if (value.isBigDecimal()) this.nodeTypes.add("double");
		else if (value.isBigInteger()) this.nodeTypes.add("double");
		else if (value.isBinary()) this.nodeTypes.add("strl");
		else if (value.isBoolean()) this.nodeTypes.add("byte");
		else if (value.isDouble() ) this.nodeTypes.add("double");
		else if (value.isFloat()) this.nodeTypes.add("double");
		else if (value.isFloatingPointNumber()) this.nodeTypes.add("double");
		else if (value.isInt()) this.nodeTypes.add("long");
		else if (value.isIntegralNumber()) this.nodeTypes.add("double");
		else if (value.isLong()) this.nodeTypes.add("double");
		else if (value.isMissingNode()) this.nodeTypes.add("missing");
		else if (value.isNull()) this.nodeTypes.add("missing");
		else if (value.isNumber()) this.nodeTypes.add("double");
		else if (value.isObject()) this.nodeTypes.add("strl");
		else if (value.isPojo()) this.nodeTypes.add("strl");
		else if (value.isShort()) this.nodeTypes.add("int");
		else if (value.isTextual()) this.nodeTypes.add("str");
		else this.nodeTypes.add("unknown");
	}

	/**
	 * Method to access the nodeTypes member
	 * @return A LinkedList of String types that identify the Stata type
	 * mapping for the element.
	 */
	public LinkedList<String> getTypeMap() {
		return this.nodeTypes;
	}

	/**
	 * Method returns the maximum string length of the keys in this object.
	 * This
	 * @return
	 */
	public Integer getMaxKeyLength() {
		return Collections.max(this.keyLengths);
	}


}
