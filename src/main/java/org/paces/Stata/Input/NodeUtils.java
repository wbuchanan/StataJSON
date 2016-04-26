package org.paces.Stata.Input;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class that contains various static methods used to assist the StJSON class
 * parse and flatten the JSON object.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class NodeUtils {

	/**
	 * Method used to indicate whether or not all elements of a given node
	 * are terminal (e.g., some non-container node type)
	 * @param an A JsonNode object to test
	 * @return A boolean where true indicates that all elements are
	 * value/terminal types, while false indicates that one or more elements
	 * are container types (i.e., ObjectNode or ArrayNode types).
	 */
	public static Boolean isTerminal(JsonNode an) {
		Boolean returnValue = true;
		for(JsonNode i : nodeList(an)) if (i.isContainerNode()) returnValue = false;
		return returnValue;
	}

	/**
	 * Method used to indicate whether or not all elements of a given node
	 * are terminal (e.g., some non-container node type)
	 * @param an An ArrayNode object to test
	 * @return A boolean where true indicates that all elements are
	 * value/terminal types, while false indicates that one or more elements
	 * are container types (i.e., ObjectNode or ArrayNode types).
	 */
	public static Boolean isTerminal(ArrayNode an) {
		return isTerminal((JsonNode) an);
	}

	/**
	 * Method used to indicate whether or not all elements of a given node
	 * are terminal (e.g., some non-container node type)
	 * @param an An ObjectNode object to test
	 * @return A boolean where true indicates that all elements are
	 * value/terminal types, while false indicates that one or more elements
	 * are container types (i.e., ObjectNode or ArrayNode types).
	 */
	public static Boolean isTerminal(ObjectNode an) {
		return isTerminal((JsonNode) an);
	}

	/**
	 * Tests if the node contains any elements
	 * @param an A JsonNode object to be tested
	 * @return A Boolean indicating if the object is both an ArrayNode and
	 * has size of 0
	 */
	public static Boolean isEmptyArray(JsonNode an) {
		if (an.isArray() && an.size() == 0) return true;
		else return false;
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ArrayNode objects.
	 * @param an A JsonNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ArrayNode object; a value of false indicates that no
	 * direct descendant nodes are ArrayNode objects.
	 */
	public static Boolean containsArray(JsonNode an) {
		Boolean returnValue = false;
		for(JsonNode i : nodeList(an)) if (i.isArray()) returnValue = true;
		return returnValue;
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ArrayNode objects.
	 * @param an An ArrayNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ArrayNode object; a value of false indicates that no
	 * direct descendant nodes are ArrayNode objects.
	 */
	public static Boolean containsArray(ArrayNode an) {
		return containsArray((JsonNode) an);
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ArrayNode objects.
	 * @param an An ObjectNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ArrayNode object; a value of false indicates that no
	 * direct descendant nodes are ArrayNode objects.
	 */
	public static Boolean containsArray(ObjectNode an) {
		return containsArray((JsonNode) an);
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * Missing Nodes
	 * @param an A JsonNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ObjectNode object; a value of false indicates that no
	 * direct descendant nodes are ObjectNode objects.
	 */
	public static Boolean containsMissing(JsonNode an) {
		Boolean returnValue = false;
		for(JsonNode i : nodeList(an)) if (i.isMissingNode()) returnValue = true;
		return returnValue;
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * Missing Nodes
	 * @param an A JsonNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ObjectNode object; a value of false indicates that no
	 * direct descendant nodes are ObjectNode objects.
	 */
	public static Boolean allMissing(JsonNode an) {
		Boolean returnValue = true;
		List<JsonNode> ans = nodeList(an);
		if (ans.size() > 0) for(JsonNode i : ans) returnValue = !i.isMissingNode();
		else returnValue = true;
		return returnValue;
	}


	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ObjectNode objects.
	 * @param an A JsonNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ObjectNode object; a value of false indicates that no
	 * direct descendant nodes are ObjectNode objects.
	 */
	public static Boolean containsObject(JsonNode an) {
		Boolean returnValue = false;
		for(JsonNode i : nodeList(an)) if (i.isObject()) returnValue = true;
		return returnValue;
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ObjectNode objects.
	 * @param an An ArrayNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ObjectNode object; a value of false indicates that no
	 * direct descendant nodes are ObjectNode objects.
	 */
	public static Boolean containsObject(ArrayNode an) {
		return containsObject((JsonNode) an);
	}

	/**
	 * Method used to indicate whether or not any child elements contain any
	 * ObjectNode objects.
	 * @param an An ObjectNode object to test
	 * @return A Boolean. a value of true indicates that one or more
	 * child nodes is an ObjectNode object; a value of false indicates that no
	 * direct descendant nodes are ObjectNode objects.
	 */
	public static Boolean containsObject(ObjectNode an) {
		return containsObject((JsonNode) an);
	}

	/**
	 * Method that tests whether the object contains any container type nodes
	 * @param an A JsonNode object which may/may not contain nested container
	 *              type nodes
	 * @return A Boolean : false indicates that no elements are container
	 * object types; true indicates that one or more elements are container
	 * type objects.
	 */
	public static Boolean containsContainers(JsonNode an) {
		Boolean returnValue = false;
		for(JsonNode i : nodeList(an)) if (i.isContainerNode()) returnValue = true;
		return returnValue;
	}

	/**
	 * Method that tests whether the object contains any container type nodes
	 * @param an An ArrayNode object which may/may not contain nested container
	 *              type nodes
	 * @return A Boolean : false indicates that no elements are container
	 * object types; true indicates that one or more elements are container
	 * type objects.
	 */
	public static Boolean containsContainers(ArrayNode an) {
		return containsContainers((JsonNode) an);
	}

	/**
	 * Method that tests whether the object contains any container type nodes
	 * @param an An ObjectNode object which may/may not contain nested container
	 *              type nodes
	 * @return A Boolean : false indicates that no elements are container
	 * object types; true indicates that one or more elements are container
	 * type objects.
	 */
	public static Boolean containsContainers(ObjectNode an) {
		return containsContainers((JsonNode) an);
	}

	/**
	 * Convenience wrapper that returns a List of JsonNodes using the
	 * JsonNode.elements() method to add successive elements to the list object
	 * @param jn A JsonNode with elements that will be returned in a list object
	 * @return A List of descendant JsonNode objects
	 */
	public static List<JsonNode> nodeList(JsonNode jn) {
		List<JsonNode> nodes = new ArrayList<>();
		Iterator<JsonNode> iter = jn.elements();
		while (iter.hasNext()) nodes.add(iter.next());
		return nodes;
	}

	/**
	 * Convenience wrapper that returns a List of JsonNodes using the
	 * JsonNode.fieldNames() method to add successive elements to the list
	 * object
	 * @param jn A JsonNode with field names that will be returned in a list
	 *              object
	 * @return A List of field names
	 */
	public static List<String> fieldList(JsonNode jn) {
		if (jn.isArray()) return fieldList((ArrayNode) jn);
		else if (jn.isObject()) return fieldList((ObjectNode) jn);
		else {
			List<String> fieldNames = new ArrayList<>();
			Iterator<String> fields = jn.fieldNames();
			while (fields.hasNext()) fieldNames.add(fields.next());
			return fieldNames;
		}
	}

	/**
	 * Convenience wrapper that returns a List of JsonNodes using the
	 * JsonNode.fieldNames() method to add successive elements to the list
	 * object
	 * @param jn An ObjectNode with field names that will be returned in a list
	 *              object
	 * @return A List of field names
	 */
	public static List<String> fieldList(ObjectNode jn) {
		List<String> fieldNames = new ArrayList<>();
		Iterator<String> fields = jn.fieldNames();
		while (fields.hasNext()) fieldNames.add(fields.next());
		return fieldNames;
	}

	/**
	 * Convenience wrapper that returns a List of JsonNodes using the
	 * JsonNode.fieldNames() method to add successive elements to the list
	 * object
	 * @param jn A ArrayNode with field names that will be returned in a list
	 *              object
	 * @return A List of field names
	 */
	public static List<String> fieldList(ArrayNode jn) {
		Iterator<JsonNode> arrayVals = jn.elements();
		Iterator<String> fields = jn.fieldNames();
		Set<String> s = new LinkedHashSet<>();
		while (arrayVals.hasNext()) s.addAll(fieldList(arrayVals.next()));
		List<String> uniqueNames = s.stream().collect(Collectors.toList());
		return uniqueNames;
	}


	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given JsonNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * @param root The JsonNode object which may/may not contain nested elements
	 * @param startIndex The value to use to indicate the starting value used
	 *                      for indexing (e.g., count from 0, 1, etc...)
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(JsonNode root, Integer startIndex) {
		List<Integer> nodeDepths = new ArrayList<>();
		List<JsonNode> nodes = nodeList(root);
		Integer startLevel;
		for(JsonNode i : nodes) nodeDepths.add(descent(i, startIndex));
		return nodeDepths;
	}

	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given ObjectNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * @param root The ObjectNode type which may/may not contain nested elements
	 * @param startIndex The value to use to indicate the starting value used
	 *                      for indexing (e.g., count from 0, 1, etc...)
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(ObjectNode root, Integer startIndex) {
		return nodeDepths((JsonNode) root, startIndex);
	}

	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given ArrayNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * @param root The ArrayNode type which contain nested elements
	 * @param startIndex The value to use to indicate the starting value used
	 *                      for indexing (e.g., count from 0, 1, etc...)
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(ArrayNode root, Integer startIndex) {
		return nodeDepths((JsonNode) root, startIndex);
	}

	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given JsonNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * <em>This is a wrapper around JsonUtils.nodeDepths(JsonNode, Integer)
	 * that specifies a value of 0 for the second parameter. </em>
	 * @param root The JsonNode object which may/may not contain nested elements
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(JsonNode root) {
		return nodeDepths(root, 0);
	}

	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given ObjectNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * <em>This is a wrapper around JsonUtils.nodeDepths(JsonNode, Integer)
	 * that specifies a value of 0 for the second parameter. </em>
	 * @param root The ObjectNode object which contains nested elements
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(ObjectNode root) {
		return nodeDepths((JsonNode) root, 0);
	}

	/**
	 * A method used to retrieve the maximum depth of each of the descendants
	 * of a given ArrayNode object.  This method returns a list of integers
	 * containing the maximum depth for each child element in this node.
	 * <em>This is a wrapper around JsonUtils.nodeDepths(ArrayNode, Integer)
	 * that specifies a value of 0 for the second parameter. </em>
	 * @param root The ArrayNode object which contains nested elements
	 * @return A list of integer values containing the maximum depth of all
	 * children of this node
	 */
	public static List<Integer> nodeDepths(ArrayNode root) {
		return nodeDepths((JsonNode) root, 0);
	}

	/**
	 * Public method used handle recasting ObjectNode types to JsonNode types
	 * to overload the descent method
	 * @param node An JsonNode which will be descended into to determine
	 *                the depth of nested objects
	 * @param currentLevel The level of the descent where this node occurs
	 */
	public static Integer descent(JsonNode node, Integer currentLevel) {
		List<JsonNode> children = nodeList(node);
		List<Integer> depths = new ArrayList<>();
		depths.add(currentLevel);
		for(JsonNode i : children) {
			if (containsContainers(i)) depths.add(descent(i, currentLevel + 1));
			else if (i.isContainerNode()) depths.add(currentLevel + 1);
			else depths.add(currentLevel);
		}
		return Collections.max(depths);
	}

	/**
	 * Public method used handle recasting ObjectNode types to JsonNode types
	 * to overload the descent method
	 * @param node An ArrayNode which will be descended into to determine
	 *                the depth of nested objects
	 * @param currentLevel The level of the descent where this node occurs
	 */
	public static Integer descent(ArrayNode node, Integer currentLevel) {
		return descent((JsonNode)node, currentLevel);
	}

	/**
	 * Public method used handle recasting ObjectNode types to JsonNode types
	 * to overload the descent method
	 * @param node An ObjectNode which will be descended into to determine
	 *                the depth of nested objects
	 * @param currentLevel The level of the descent where this node occurs
	 */
	public static Integer descent(ObjectNode node, Integer currentLevel) {
		return descent((JsonNode)node, currentLevel);
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Base64 type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(BaseJsonNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A BigInteger type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(BigIntegerNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Binary type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(BinaryNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Boolean type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(BooleanNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Decimal valued type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(DecimalNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Double type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(DoubleNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Float type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(FloatNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Integer type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(IntNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Long type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(LongNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Missing type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(MissingNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Null type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(NullNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Number type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(NumericNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A POJO (Plain Old Java Object) type JsonNode which contains
	 *                some type of non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(POJONode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Short type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(ShortNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Text type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(TextNode node, Integer currentLevel) {
		return currentLevel;
	}

	/**
	 * Private method used to prevent type errors when calling descent method on objects
	 * @param node A Value type JsonNode which contains some type of
	 *                non-container value
	 * @param currentLevel The level of the descent where this node occurs
	 */
	private static Integer descent(ValueNode node, Integer currentLevel) {
		return currentLevel;
	}

}
