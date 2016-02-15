package org.paces.Stata.Google;

import com.stata.sfi.Macro;
import org.paces.Stata.Google.ReturnValues.GoogleStataVars;

import java.util.*;

/**
 * Class that acts as the entry point to the Google APIs
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class Google {

	/**
	 * Object used to retrieve/access an API key value if passed
	 */
	private static final String geocodeAPI = Macro.getLocalSafe("apikey");

	/**
	 * Method used to process/geocode addresses via the Google geocoding API
	 * @param args API Required argument parameter
	 * @return A success code
	 */
	public static int geocode(String[] args) {
		HashSet<String> geotypes = processGeoTypes(args[0]);
		DataRetrieval data = new DataRetrieval(args);
		Map<String, Map<String, Integer>> vdx = getAllVarIndices(geotypes);
		for(Long i : data.getAddressList().keySet()) {
			if (geocodeAPI.isEmpty()) new Geocode(data.getAddressList().get(i), i, vdx, geotypes);
			else new Geocode(data.getAddressList().get(i), geocodeAPI, i, vdx, geotypes);
		}
		return 0;
	}

	/**
	 * Method that ensures the option list is unique
	 * @param t The space delimited string with the options
	 * @return A HashSet object containing the options of which values the
	 * user would like to return
	 */
	public static HashSet<String> processGeoTypes(String t) {
		HashSet<String> types;
		if (t.isEmpty()) t = "location";
		types = new HashSet<>(Arrays.asList(t.split(" ")));
		for (String type : types) {
			setVars(type);
		}
		return types;
	}

	/**
	 * Method to retrieve all of the variable indices created by this class
	 * and stores them in a map with the variable name as the key and the
	 * index as the value which is stored in another map that uses the type
	 * option(s) as the key.
	 * @param geoTypes A HashSet object containing the string values
	 *                    identifying which values should be returned
	 * @return An object containing the newly created variable names and the
	 * corresponding indices.
	 */
	public static Map<String, Map<String, Integer>> getAllVarIndices
			(HashSet<String> geoTypes) {
		Map<String, Map<String, Integer>> newVarIndices = new HashMap<>();
		for (String i : geoTypes) {
			newVarIndices.put(i, getVarIndex(i));
		}
		return newVarIndices;
	}

	/**
	 * Gets the Map object containing the variable type to variable index
	 * mapping
	 * @param vars An individual type option string
	 * @return A map containing the names of the added variables and variable
	 * indices for that option
	 */
	public static Map<String, Integer> getVarIndex(String vars) {
		switch (vars) {
			case "location" :
				return GoogleStataVars.getLocationIndices();
			case "bbox" :
				return GoogleStataVars.getBoundingBoxIndices();
			case "viewport":
				return GoogleStataVars.getViewportIndices();
			case "address" :
				return GoogleStataVars.getFormattedAddressIndices();
			case "geotype":
				return GoogleStataVars.getGeoTypeIndices();
			case "placeid" :
				return GoogleStataVars.getPlaceIdIndices();
			case "types":
				return GoogleStataVars.getTypesIndices();
			default :
				return GoogleStataVars.getLocationIndices();
		}
	}

	/**
	 * Method that adds variables to the data set based on the type string(s)
	 * passed to the command
	 * @param vars An individual type option string
	 */
	private static void setVars(String vars) {
		switch (vars) {
			default :
				GoogleStataVars.makeGeocodeLocationVars();
				break;
			case "location" :
				GoogleStataVars.makeGeocodeLocationVars();
				break;
			case "bbox" :
				GoogleStataVars.makeGeocodeBoundingBoxVars();
				break;
			case "viewport":
				GoogleStataVars.makeGeocodeViewportVars();
				break;
			case "address" :
				GoogleStataVars.makeGeocodeFormattedAddressVars();
				break;
			case "geotype":
				GoogleStataVars.makeGeocodeLocationTypeVars();
				break;
			case "placeid" :
				GoogleStataVars.makeGeocodePlaceIdVars();
				break;
			case "types":
				GoogleStataVars.makeGeocodeTypesVars();
				break;
		}
	}

}
