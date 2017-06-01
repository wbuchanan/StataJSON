package org.paces.Stata.Input.Interfaces;

import com.stata.sfi.*;
import org.paces.Stata.Input.FlatStataJSON;
import java.util.List;

/**
 * Interface used to load JSON values into separate variables in the DataSet.
 * Essentially it takes the Flattened JSON object and transforms the n x 2
 * Matrix into a single row vector.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface RowValue extends StataVariableCreator {

	/**
	 * Method used to initialize new Stata variables and load them into the
	 * data set in memory
	 * @param keys The list of keys that match the user's query or all the keys
	 * @param nodeMap The Flattened JSON Object
	 * @param obid The observation ID identifying the row in the data set
	 *                where these data will be loaded.
	 * @param stubname A string used to construct variable names by appending
	 *                    ID/Iterator values as a suffix
	 */
	void toVariables(List<String> keys, FlatStataJSON nodeMap, Integer obid,
	                 String stubname);

	/**
	 * Method used to load boolean values in Stata as byte values where 1
	 * indicates true and 0 indicates false.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadBool(Integer obid, Integer varidx, FlatStataJSON datum,
	                      String key) {
		if(datum.get(key).booleanValue()) Data.storeNum(varidx, obid, (byte)1);
		else Data.storeNum(varidx, obid, (byte)0);
	}

	/**
	 * Method used to load Java byte/short numeric values into Stata as byte
	 * types.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadByte(Integer obid, Integer varidx, FlatStataJSON datum,
	                      String key) {
		Data.storeNum(varidx, obid, Byte.parseByte(datum.get(key).asText()));
	}

	/**
	 * Method used to load Java short (Stata int) values into Stata.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadInt(Integer obid, Integer varidx, FlatStataJSON datum,
	                     String key) {
		Data.storeNum(varidx, obid, datum.get(key).intValue());
	}

	/**
	 * Method used to load Java ints (Stata longs).  4-bit Integers are
	 * labelled "long" in Stata notation, so the method reflects what would
	 * be consumed by end users working in Stata.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadLong(Integer obid, Integer varidx, FlatStataJSON datum,
	                      String key) {
		Data.storeNum(varidx, obid, datum.get(key).intValue());
	}

	/**
	 * Method used to load floating point and 64-bit Integer numeric values
	 * into Stata.  Java long types require 64-bits, but the maximum integer
	 * length currently available in Stata is 32-bit; these values are cast
	 * up to a double via the underlying Number interface.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadDouble(Integer obid, Integer varidx, FlatStataJSON datum,
	                        String key) {
		Data.storeNum(varidx, obid, datum.get(key).doubleValue());
	}

	/**
	 * Method used to 4-byte floating point values.  Upcasts the value to a
	 * double when pushing into Stata.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadFloat(Integer obid, Integer varidx, FlatStataJSON datum,
	                       String key) {
		Data.storeNum(varidx, obid, (double) datum.get(key).floatValue());
	}

	/**
	 * Method used to load string values into Stata.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadString(Integer obid, Integer varidx, FlatStataJSON datum,
	                        String key) {
		Data.storeStr(varidx, obid, datum.get(key).textValue());
	}

	/**
	 * Method used to load strL values; This data type is used as a catch all
	 * for different objects that have no direct Stata equivalent (e.g.,
	 * media, POJOs, etc...).
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadStrL(Integer obid, Integer varidx, FlatStataJSON datum,
	                      String key) {
		Data.storeStr(varidx, obid, datum.get(key).textValue());
	}

	/**
	 * Method for loading Missing values into Stata.  Pushes the values into
	 * Stata as a single byte missing value.
	 * @param obid The observation ID where the datum will be stored
	 * @param varidx The variable index where the variable will be stored
	 * @param datum A FlatStataJSON object containing the JsonNode object
	 * @param key The key to the JsonNode object (also the variable label)
	 */
	default void loadMissing(Integer obid, Integer varidx, FlatStataJSON datum,
	                         String key) {
		Data.storeNum(varidx, obid, (byte) 127);
	}

}
