package org.paces.Stata.Input.Interfaces;

import com.stata.sfi.*;

import java.util.*;

/**
 * Interface that handles the creation of new Stata variables for all loaders
 * . Defines a single default method (addStataVars()) with signatures that
 * are specific to different implementations/use cases (e.g., row/key value).
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface StataVariableCreator {

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
		if (Data.getObsTotal() == 0L) Data.setObsTotal(obs);
		List<Integer> varidx = new ArrayList<>();
		varidx.add(0, addStataVar("key", keyLength, "str"));
		varidx.add(1, addStataVar("value", keyLength, type));
		return varidx;
	}

	/**
	 * Method used to create a new Stata variable of the correct type with
	 * arbitrary variable names.
	 * @param varname The number of observations to create in the data set
	 * @param keyLength The maximum length of the strings used to store the keys
	 * @param type String type identifying the Stata data type to create
	 * @return An integer representing the Stata dataset index value where
	 * the data will be stored.
	 */
	default Integer addStataVar(String varname, Integer keyLength, String type) {
		switch (type) {
			case "bool" : Data.addVarByte(varname); break;
			case "byte" : Data.addVarByte(varname); break;
			case "int" : Data.addVarInt(varname); break;
			case "long" : Data.addVarDouble(varname); break;
			case "double" : Data.addVarDouble(varname); break;
			case "float" : Data.addVarDouble(varname); break;
			case "strl" : Data.addVarStrL(varname); break;
			case "missing" : Data.addVarByte(varname); break;
			default : Data.addVarStr(varname, keyLength); break;
		}
		return Data.getVarIndex(varname);
	}

	/**
	 * Method used to create a new Stata variable of the correct type with
	 * arbitrary variable names.
	 * @param varname The number of observations to create in the data set
	 * @param varlabel A variable label to attach to a variable
	 * @param keyLength The maximum length of the strings used to store the keys
	 * @param type String type identifying the Stata data type to create
	 * @return An integer representing the Stata dataset index value where
	 * the data will be stored.
	 */
	default Integer addStataVar(String varname, String varlabel,
	                            Integer keyLength, String type) {
		Integer varidx = addStataVar(varname, keyLength, type);
		Data.setVarLabel(varidx, varlabel);
		return varidx;
	}

}
