package org.paces.Stata.Ingest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;

import java.util.*;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class JsonUtils {

	public static Boolean isTerminal(ArrayNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = true;
		while(elems.hasNext()) {
			if (!elems.next().isValueNode()) returnValue = false;
		}
		return returnValue;
	}

	public static Boolean containsArray(ArrayNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isArray()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsObject(ArrayNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isObject()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsContainers(ArrayNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			JsonNode temp = elems.next();
			if (temp.isArray() || temp.isObject()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean isTerminal(ObjectNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = true;
		while(elems.hasNext()) {
			if (!elems.next().isValueNode()) returnValue = false;
		}
		return returnValue;
	}

	public static Boolean containsArray(ObjectNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isArray()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsObject(ObjectNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isObject()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsContainers(ObjectNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			JsonNode temp = elems.next();
			if (temp.isArray() || temp.isObject()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean isTerminal(JsonNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = true;
		while(elems.hasNext()) {
			if (!elems.next().isValueNode()) returnValue = false;
		}
		return returnValue;
	}

	public static Boolean containsArray(JsonNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isArray()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsObject(JsonNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			if (elems.next().isObject()) returnValue = true;
		}
		return returnValue;
	}

	public static Boolean containsContainers(JsonNode an) {
		Iterator<JsonNode> elems = an.elements();
		Boolean returnValue = false;
		while(elems.hasNext()) {
			JsonNode temp = elems.next();
			if (temp.isArray() || temp.isObject()) returnValue = true;
		}
		return returnValue;
	}


	public static List<JsonNode> nodeList(JsonNode jn) {
		List<JsonNode> nodes = new ArrayList<>();
		Iterator<JsonNode> iter = jn.elements();
		while (iter.hasNext()) nodes.add(iter.next());
		return nodes;
	}

	public static List<Integer> nodeDepths(JsonNode root, Integer startIndex) {
		List<Integer> nodeDepths = new ArrayList<>();
		List<JsonNode> nodes = JsonUtils.nodeList(root);
		Integer startLevel;
		for(JsonNode i : nodes) nodeDepths.add(descent(i, startIndex));
		return nodeDepths;
	}

	public static List<Integer> nodeDepths(ObjectNode root, Integer startIndex) {
		return nodeDepths((JsonNode) root, startIndex);
	}

	public static List<Integer> nodeDepths(ArrayNode root, Integer startIndex) {
		return nodeDepths((JsonNode) root, startIndex);
	}

	public static List<Integer> nodeDepths(JsonNode root) {
		return nodeDepths(root, 0);
	}

	public static List<Integer> nodeDepths(ObjectNode root) {
		return nodeDepths((JsonNode) root, 0);
	}

	public static List<Integer> nodeDepths(ArrayNode root) {
		return nodeDepths((JsonNode) root, 0);
	}

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

	public static Integer descent(ArrayNode node, Integer currentLevel) {
		return descent((JsonNode)node, currentLevel);
	}

	public static Integer descent(ObjectNode node, Integer currentLevel) {
		return descent((JsonNode)node, currentLevel);
	}

	public static Integer descent(BaseJsonNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(BigIntegerNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(BinaryNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(BooleanNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(DecimalNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(DoubleNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(FloatNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(IntNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(LongNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(MissingNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(NullNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(NumericNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(POJONode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(ShortNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(TextNode node, Integer currentLevel) {
		return currentLevel;
	}

	public static Integer descent(ValueNode node, Integer currentLevel) {
		return currentLevel;
	}


}
