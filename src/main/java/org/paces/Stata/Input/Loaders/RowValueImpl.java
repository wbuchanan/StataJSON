package org.paces.Stata.Input.Loaders;

import com.stata.sfi.*;
import org.paces.Stata.Input.*;
import org.paces.Stata.Input.Interfaces.RowValue;

import java.util.List;

/**
 * Row Value pairs implementation class.
 * Like the key value implementation class, but instead of loading data into
 * prespecified
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class RowValueImpl implements RowValue {

	/**
	 * Class constructor used to make sure the appropriate number of
	 * observations are available in the data set before calling the method
	 * to load the data into Stata.
	 *
	 * The Constructor with the signature assumes the user wants to load the
	 * data into the first observation.
	 * @param keys The list of keys containing the elements to load into Stata
	 * @param nodeMap A FlatStataJSON object containg a key-value pair of the
	 *                   flattened JSON object
	 */
	public RowValueImpl(List<String> keys, FlatStataJSON nodeMap) {
		this(keys, nodeMap, 1, "jsonvar");
	}

	/**
	 * Class constructor used to make sure the appropriate number of
	 * observations are available in the data set before calling the method
	 * to load the data into Stata
	 * @param keys The list of keys containing the elements to load into Stata
	 * @param nodeMap A FlatStataJSON object containg a key-value pair of the
	 *                   flattened JSON object
	 * @param obid The observation ID where the data will be stored
	 * @param stubname The root string used to construct variable names for
	 *                    the JsonNodes
	 */
	public RowValueImpl(List<String> keys, FlatStataJSON nodeMap, Integer obid,
	                    String stubname) {

		if (Data.getObsCount() < obid) Data.setObsCount(obid);

		// First line in class constructor
		toVariables(keys, nodeMap, obid, stubname);
		
	}
	

	/**
	 * Method used to initialize new Stata variables and load them into the data
	 * set in memory
	 *
	 * @param keys     The list of keys that match the user's query or all the
	 *                 keys
	 * @param nodeMap  The Flattened JSON Object
	 * @param obid     The observation ID identifying the row in the data set
	 *                 where these data will be loaded.
	 * @param stubname A string used to construct variable names by appending
	 *                    an ID/Iterator value as a suffix
	 */
	@Override
	public void toVariables(List<String> keys, FlatStataJSON nodeMap,
	                        Integer obid, String stubname) {
		for(Integer i = 0; i < keys.size(); i++) {
			String varlab = keys.get(i);
			String varname = stubname + String.valueOf(1 + i);
			Integer len = varlab.length();
			StataTypeMap dataType = nodeMap.getTypeMap().get(varlab);
			Integer varidx = addStataVar(varname, varlab, len, dataType.getName());
			switch (dataType) {
				case BOOL : loadBool(obid, varidx, nodeMap, varlab); break;
				case BYTE : loadByte(obid, varidx, nodeMap, varlab); break;
				case INT : loadInt(obid, varidx, nodeMap, varlab); break;
				case LONG : loadLong(obid, varidx, nodeMap, varlab); break;
				case FLOAT : loadFloat(obid, varidx, nodeMap, varlab); break;
				case DOUBLE : loadDouble(obid, varidx, nodeMap, varlab); break;
				case STRL : loadStrL(obid, varidx, nodeMap, varlab); break;
				case MISSING : loadMissing(obid, varidx, nodeMap, varlab); break;
				default : loadString(obid, varidx, nodeMap, varlab); break;
			}

		}
	}
}
