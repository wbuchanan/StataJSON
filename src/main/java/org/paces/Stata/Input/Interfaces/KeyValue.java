package org.paces.Stata.Input.Interfaces;

import com.stata.sfi.*;
import org.paces.Stata.Input.*;

import java.util.*;

/**
 * Interface methods used to define methods used to handle how elements are
 * loaded in Stata
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface KeyValue {

	/**
	 * Method used to create Stata key/value variables
	 * @param obs The number of observations to create in the data set
	 * @param keyLength The maximum length of the strings used to store the keys
	 * @param type String type identifying the Stata data type to create
	 * @return A List of Integers containing the indices for the key and
	 * value variables.  The first element is the key (e.g., .get(0)) and
	 * second element returns the index for the values.
	 */
	default List<Integer> addStataVars(Integer obs, Integer keyLength, String
	 type) {
		Data.setObsTotal(obs);
		Data.addVarStr("key", keyLength);
		switch (type) {
			case "byte" :
				Data.addVarInt("value");
				break;
			case "int" :
				Data.addVarInt("value");
				break;
			case "long" :
				Data.addVarInt("value");
				break;
			case "double" :
				Data.addVarDouble("value");
				break;
			case "strL" :
				Data.addVarStrL("value");
				break;
			default :
				Data.addVarStr("value", keyLength);
				break;
		}
		List<Integer> varidx = new ArrayList<>();
		varidx.add(0, Data.getVarIndex("key"));
		varidx.add(1, Data.getVarIndex("value"));
		return varidx;
	}

	/**
	 * Method to test whether all the node elements identified by the indices
	 * in the parameter are of the same type
	 * @param keys A list of keys used to identify elements and the node types
	 * @param typeMap 
	 * @return A StataTypeMap
	 */
	StataTypeMap sameType(List<String> keys, Map<String, StataTypeMap> typeMap);

	/**
	 * Method used to load all of the nodes identified by the keys param;
	 * These are Java Booleans, but will be converted to a Stata byte type
	 * where 0 = false and 1 = true.
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object
	 */
	default void loadBool(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "byte");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			if (nodeMap.get(j).booleanValue()) Data.storeNum(idx.get(1), i, 1);
			else Data.storeNum(1, i, 0);
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata double type values
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadDouble(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "double");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeNum(idx.get(1), i, nodeMap.get(j).doubleValue());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata double type values; these are represented as a Float type on the
	 * JVM but will be cast to double before adding to the Stata dataset
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadFloat(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "double");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeNum(idx.get(1), i, (double)nodeMap.get(j).floatValue());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata int type values; these data are represented as Short types on
	 * the JVM (2-byte integers).
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadInt(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "int");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeNum(idx.get(1), i, nodeMap.get(j).intValue());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata long type values; these data are represented as Integer types on
	 * the JVM (4-byte integers).  True long values are cast to Double types
	 * prior to pushing into Stata.
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadLong(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "long");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeNum(idx.get(1), i, nodeMap.get(j).intValue());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata String type values
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadStr(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
			nodeMap.getMaxKeyLength(),
			"string");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeStr(idx.get(1), i, nodeMap.get(j).asText());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata strL type variables.  These are also used for any cases where
	 * the type of the data is binary/unknown.
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadStrL(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
			nodeMap.getMaxKeyLength(),
			"strL");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeStr(idx.get(1), i, nodeMap.get(j).asText());
			i++;
		}
	}

	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata missing values;  these will be cast byte values of 127 to
	 * minimize the amount of memory needed to store the missing values (this
	 * also assumes all values in the key list contain missing nodes).
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	default void loadMissing(List<String> keys, FlatStataJSON nodeMap) {
		List<Integer> idx = addStataVars(keys.size(),
										 nodeMap.getMaxKeyLength(),
										 "byte");
		Integer i = 1;
		for(String j : keys) {
			Data.storeStr(idx.get(0), i, j);
			Data.storeNum(idx.get(1), i, 127);
			i++;
		}

	}
	
	/**
	 * Method used to load all of the nodes identified by the keys param as
	 * Stata missing values;  these will be cast byte values of 127 to
	 * minimize the amount of memory needed to store the missing values (this
	 * also assumes all values in the key list contain missing nodes).
	 * @param keys The keys identifying the nodes to load
	 * @param nodeMap A FlatStataJSON object  
	 */
	void asKeyValue(StataTypeMap type, List<String> keys, FlatStataJSON nodeMap);

}
