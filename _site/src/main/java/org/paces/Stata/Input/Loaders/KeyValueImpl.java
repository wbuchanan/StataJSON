package org.paces.Stata.Input.Loaders;

import org.paces.Stata.Input.*;
import org.paces.Stata.Input.Interfaces.KeyValue;

import java.util.*;

import static org.paces.Stata.Input.StataTypeMap.*;

/**
 * Implementing class for loading the data in Stata as a KeyValue pair.
 * Created statically from the entry point to the class.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class KeyValueImpl implements KeyValue {

	/**
	 * Empty class constructor (unused).
	 */
	public KeyValueImpl() {
	}

	/**
	 * Method to test whether all the node elements identified by the indices in
	 * the parameter are of the same type
	 *
	 * @param keys    A list of keys used to identify elements and the node
	 *                types
	 * @param typeMap A map object with Stata data type mapping for each
	 *                   JsonNode object
	 *
	 * @return A StataTypeMap
	 */
	@Override
	public StataTypeMap sameType(List<String> keys, Map<String, StataTypeMap> typeMap) {

		// Should prevent needing to iterate over en
		if (new HashSet(typeMap.values()).size() == 1) return typeMap.get(keys.get(0));

		// Gets the first node type
		StataTypeMap thisType;
		StataTypeMap lastType = typeMap.get(keys.get(0));

		// Iterate over the rest of the elements
		for(Integer i = 0; i < keys.size(); i++) {

			// If the Last type is Missing and the current type is not missing
			if (lastType == MISSING &&
				typeMap.get(keys.get(i)) != MISSING) {

				// Set the last type to the current type
				lastType = typeMap.get(keys.get(i));

				// Set the current type
				thisType = lastType;

				// If the current type is Missing
			} else if (typeMap.get(keys.get(i)) == MISSING) {

				// Set the current type based on the last type
				thisType = lastType;

				// If the current type doesn't equal the last type
			} else if (typeMap.get(keys.get(i)) != lastType) {

				// Return mixed type (this will be used to force load as String
				return MIXED;

				// For cases where the types match, do nothing
			} else {

			}

		} // End Loop

		// If method makes it to the end of the loop all elements are the
		// same type
		return lastType;

	} // End Method declaration

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata missing values;  these will be cast byte values of 127 to minimize
	 * the amount of memory needed to store the missing values (this also
	 * assumes all values in the key list contain missing nodes).
	 * @param type A map object with Stata data type mapping for each
	 *                   JsonNode object
	 * @param keys    The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object
	 */
	@Override
	public void asKeyValue(StataTypeMap type, List<String> keys, FlatStataJSON nodeMap) {
		switch(type) {
			case BOOL : loadBool(keys, nodeMap); break;
			case DOUBLE : loadDouble(keys, nodeMap); break;
			case FLOAT : loadFloat(keys, nodeMap); break;
			case INT : loadInt(keys, nodeMap); break;
			case LONG : loadLong(keys, nodeMap); break;
			case STRL : loadStrL(keys, nodeMap); break;
//			case MISSING : loadMissing(keys, nodeMap); break;
			default : loadStr(keys, nodeMap); break;
		}
	}
}
