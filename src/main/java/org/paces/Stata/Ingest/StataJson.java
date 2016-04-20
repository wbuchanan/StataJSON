package org.paces.Stata.Ingest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.*;

import java.io.*;
import java.util.*;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class StataJson {

	private Deque<IndexedPeekIterator<?>> elements = new ArrayDeque<>();
	private List<Map<String, Object>> flattenedJsonMap = new ArrayList<>();
	private String flattenedJson = null;

	public StataJson(JsonNode jn) {
		parse(jn);
		flattenAsMap();
	}

	public void printString() {
		for(int i = 0; i < this.flattenedJsonMap.size(); i++) {
			for(String keys : this.flattenedJsonMap.get(i).keySet()) {
				StringJoiner sj = new StringJoiner("\t");
				sj.add("Key :").add(keys).add("Value :").add(this.flattenedJsonMap.get(i).get(keys).toString());
				System.out.println(sj.toString());
			}
		}
	}

	public void parse(JsonNode jn) {
		if ((jn.isObject() && jn.elements().hasNext()) ||
			(jn instanceof ObjectNode && jn.elements().hasNext())) {
			elements.add(new IndexedPeekIterator<JsonNode>(jn.elements()));
		} else if ((jn.isArray() && jn.elements().hasNext()) ||
			jn instanceof ArrayNode && jn.elements().hasNext()) {
			elements.add(new IndexedPeekIterator<JsonNode>(jn.iterator()));
		} else {
			try {
				/*
				Need to figure out why fieldNames() method
				 */
				Map<String, Object> retData = parseValues(jn.fields());
				this.flattenedJsonMap.add(retData);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void flattenAsMap() {
		while (!elements.isEmpty()) {
			IndexedPeekIterator<?> deepestIter = elements.getLast();
			if (!deepestIter.hasNext()) elements.removeLast();
			else parse((JsonNode) deepestIter.next());
		}
	}

	public Map<String, Object> parseValues(Iterator<Map.Entry<String, JsonNode>> datum) throws IOException {
		Map<String, Object> data = new HashMap<>();
		while(datum.hasNext()) {
			Map.Entry<String, JsonNode> entry = datum.next();
			String key = entry.getKey();
			JsonNode jn = entry.getValue();
			switch(jn.get(key).getNodeType()) {
				case BOOLEAN:
					if (jn.get(key).asBoolean()) data.put(key, 1);
					else data.put(key, 0);
					break;
				case STRING:
					data.put(key, jn.get(key).textValue());
					break;
				case NUMBER:
					if (jn.get(key).isBigDecimal()) data.put(key, jn.get(key)
						.asDouble());
					else if (jn.get(key).isBigInteger()) data.put(key, jn.get(key).numberValue());
					else if (jn.get(key).isDouble()) data.put(key, jn.get(key).doubleValue());
					else if (jn.get(key).isFloat()) data.put(key, jn.get(key).floatValue());
					else if (jn.get(key).isInt()) data.put(key, jn.get(key).intValue());
					else if (jn.get(key).isShort()) data.put(key, jn.get(key).shortValue());
					else if (jn.get(key).isNumber()) data.put(key, jn.get(key).numberValue());
					else if (jn.get(key).isLong()) data.put(key, jn.get(key).longValue());
					else if (jn.get(key).isFloatingPointNumber()) data.put(key, jn.get(key).doubleValue());
					break;
				case MISSING:
					data.put(key, "");
					break;
				case NULL:
					data.put(key, "");
					break;
				case BINARY:
					data.put(key, jn.get(key).binaryValue());
					break;
				default:
					if (jn.get(key).isValueNode()) data.put(key, jn.get(key).asText(""));
					else data.put(key, jn.get(key).toString());
					break;
			}
		}
		return data;
	}


}
