package org.paces.Stata.Google;

import com.stata.sfi.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class GoogleStataVars {

	public static void makeGeocodeLocationVars() {
		Data.addVarDouble("lat");
		Data.addVarDouble("lon");
	}

	public static void makeGeocodeBoundingBoxVars() {
		Data.addVarDouble("bbox_max_lat");
		Data.addVarDouble("bbox_max_lon");
		Data.addVarDouble("bbox_min_lat");
		Data.addVarDouble("bbox_min_lon");
	}

	public static void makeGeocodeViewportVars() {
		Data.addVarDouble("viewport_max_lat");
		Data.addVarDouble("viewport_max_lon");
		Data.addVarDouble("viewport_min_lat");
		Data.addVarDouble("viewport_min_lon");
	}

	public static void makeGeocodeLocationTypeVars() {
		Data.addVarStrL("location_type");
	}

	public static void makeGeocodeFormattedAddressVars() {
		Data.addVarStrL("formatted_address");
	}

	public static void makeGeocodePlaceIdVars() {
		Data.addVarStrL("place_id");
	}

	public static void makeGeocodeTypesVars() {
		Data.addVarStrL("google_types");
	}

	public static Map<String, Integer> getLocationIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("lat", Data.getVarIndex("lat"));
		positions.put("lon", Data.getVarIndex("lon"));
		return positions;
	}

	public static Map<String, Integer> getBoundingBoxIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("bbox_max_lat", Data.getVarIndex("bbox_max_lat"));
		positions.put("bbox_max_lon", Data.getVarIndex("bbox_max_lon"));
		positions.put("bbox_min_lat", Data.getVarIndex("bbox_min_lat"));
		positions.put("bbox_min_lon", Data.getVarIndex("bbox_min_lon"));
		return positions;
	}

	public static Map<String, Integer> getViewportIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("viewport_max_lat", Data.getVarIndex("viewport_max_lat"));
		positions.put("viewport_max_lon", Data.getVarIndex("viewport_max_lon"));
		positions.put("viewport_min_lat", Data.getVarIndex("viewport_min_lat"));
		positions.put("viewport_min_lon", Data.getVarIndex("viewport_min_lon"));
		return positions;
	}

	public static Map<String, Integer> getLocationTypeIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("location_type", Data.getVarIndex("location_type"));
		return positions;
	}

	public static Map<String, Integer> getFormattedAddressIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("formatted_address", Data.getVarIndex("formatted_address"));
		return positions;
	}

	public static Map<String, Integer> getPlaceIdIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("place_id", Data.getVarIndex("place_id"));
		return positions;
	}

	public static Map<String, Integer> getTypesIndices() {
		Map<String, Integer> positions = new HashMap<>();
		positions.put("google_types", Data.getVarIndex("google_types"));
		return positions;
	}



}
