package org.paces.Stata.Google;

import com.stata.sfi.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used to construct variables, variable labels, and retrieve variable
 * indices used by the different return types from the Google geocode API.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class GoogleStataVars {

	/**
	 * Method that creates the variables used to store the coordinates of the
	 * requested location
	 */
	public static void makeGeocodeLocationVars() {
		Data.addVarDouble("lat");
		Data.addVarDouble("lon");
	}

	/**
	 * Method that creates the variables used to store the coordinates used
	 * to define the limits of the bounding box
	 */
	public static void makeGeocodeBoundingBoxVars() {
		Data.addVarDouble("bbox_max_lat");
		Data.addVarDouble("bbox_max_lon");
		Data.addVarDouble("bbox_min_lat");
		Data.addVarDouble("bbox_min_lon");
	}

	/**
	 * Method that creates the variables used to store the coordinates used
	 * to define the limits of the viewport bounding box
	 */
	public static void makeGeocodeViewportVars() {
		Data.addVarDouble("viewport_max_lat");
		Data.addVarDouble("viewport_max_lon");
		Data.addVarDouble("viewport_min_lat");
		Data.addVarDouble("viewport_min_lon");
	}

	/**
	 * Method that creates the goe_type variable to store the type of
	 * geocoded location
	 */
	public static void makeGeocodeLocationTypeVars() {
		Data.addVarStrL("geo_type");
	}

	/**
	 * Method that creates the formatted_address variable to store the
	 * address returned by the payload
	 */
	public static void makeGeocodeFormattedAddressVars() {
		Data.addVarStrL("formatted_address");
	}

	/**
	 * Method that creates the place_id variable to store Google's place
	 * identifier
	 */
	public static void makeGeocodePlaceIdVars() {
		Data.addVarStrL("place_id");
	}

	/**
	 * Method that creates the google_types variable to store the types data
	 */
	public static void makeGeocodeTypesVars() {
		Data.addVarStrL("google_types");
	}

	/**
	 * Method that returns the variable indices for the point coordinates.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getLocationIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("lat", Data.getVarIndex("lat"));
		positions.put("lon", Data.getVarIndex("lon"));
		Data.setVarLabel(positions.get("lat"), "Latitude");
		Data.setVarLabel(positions.get("lon"), "Longitude");
		return positions;
	}

	/**
	 * Method that returns the variable indices for bounding box.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getBoundingBoxIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("bbox_max_lat", Data.getVarIndex("bbox_max_lat"));
		positions.put("bbox_max_lon", Data.getVarIndex("bbox_max_lon"));
		positions.put("bbox_min_lat", Data.getVarIndex("bbox_min_lat"));
		positions.put("bbox_min_lon", Data.getVarIndex("bbox_min_lon"));
		Data.setVarLabel(positions.get("bbox_max_lat"), "Latitude of the Northeast Corner of the Bounding Box");
		Data.setVarLabel(positions.get("bbox_max_lon"), "Longitude of the Northeast Corner of the Bounding Box");
		Data.setVarLabel(positions.get("bbox_min_lat"), "Latitude of the Northeast Corner of the Bounding Box");
		Data.setVarLabel(positions.get("bbox_min_lon"), "Longitude of the Northeast Corner of the Bounding Box");
		return positions;
	}

	/**
	 * Method that returns the variable indices for viewport.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getViewportIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("viewport_max_lat", Data.getVarIndex("viewport_max_lat"));
		positions.put("viewport_max_lon", Data.getVarIndex("viewport_max_lon"));
		positions.put("viewport_min_lat", Data.getVarIndex("viewport_min_lat"));
		positions.put("viewport_min_lon", Data.getVarIndex("viewport_min_lon"));
		Data.setVarLabel(positions.get("viewport_max_lat"), "Latitude of the Northeast Corner of the Viewport");
		Data.setVarLabel(positions.get("viewport_max_lon"), "Longitude of the Northeast Corner of the Viewport");
		Data.setVarLabel(positions.get("viewport_min_lat"), "Latitude of the Northeast Corner of the Viewport");
		Data.setVarLabel(positions.get("viewport_min_lon"), "Longitude of the Northeast Corner of the Viewport");
		return positions;
	}

	/**
	 * Method that returns the variable indices for geocode type.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getGeoTypeIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("geo_type", Data.getVarIndex("geo_type"));
		Data.setVarLabel(positions.get("geo_type"), "The geocoding type used by the Google Geocoding API");
		return positions;
	}

	/**
	 * Method that returns the variable indices for the formatted address
	 * returned in the payload from the Geocoding API.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getFormattedAddressIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("formatted_address", Data.getVarIndex("formatted_address"));
		Data.setVarLabel(positions.get("formatted_address"), "The address string returned from Google in the JSON Payload");
		return positions;
	}

	/**
	 * Method that returns the variable indices for Google's place identifier.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getPlaceIdIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("place_id", Data.getVarIndex("place_id"));
		Data.setVarLabel(positions.get("place_id"), "Google's Place Identifier");
		return positions;
	}

	/**
	 * Method that returns the variable indices for the Google Type variable.
	 * This method also adds a variable label to the data set in memory
	 * @return A key/value pair where the key is the return type and the
	 * value is the variable index
	 */
	public static Map<String, Integer> getTypesIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("google_types", Data.getVarIndex("google_types"));
		Data.setVarLabel(positions.get("google_types"), "The classification of the value passed to Google's Geocoding API");
		return positions;
	}



}
