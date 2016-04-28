package org.paces.Stata.Google;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.Data;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Class used to geocode the addresses requested by the user.  The class
 * includes methods used to format the address string, call the Google
 * Geocode API, parse the payload, and return the results to the user.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class Geocode {

	/**
	 * Returns the .n extended missing value of the datum was Not found in
	 * the payload
	 */
	public static final Double GEOMISSING = 9.019187969096824E307D;

	/**
	 * Object used to store the bounding box data temporarily
	 */
	private List<Double> bbox = new ArrayList<>();

	/**
	 * Object used to store the viewport data temporarily
	 */
	private List<Double> viewport = new ArrayList<>();

	/**
	 * Object used to store the point location data temporarily
	 */
	private List<Double> location = new ArrayList<>();

	/**
	 * Object used to store the location type string data temporarily
	 */
	private String geoType;

	/**
	 * Object used to store the formatted address string data temporarily
	 */
	private String formattedAddress;

	/**
	 * Object used to store the Google place ID value temporarily
	 */
	private String placeId;

	/**
	 * Object used to store the Google types data temporarily
	 */
	private String types;

	/**
	 * Object containing the base of the URL used to call the API.  Protocol is
	 * determined based on the class constructor (e.g., with an API key it
	 * uses HTTPS and without the key it uses HTTP)
	 */
	private static final String urlBase = "://maps.googleapis.com/maps/api/geocode/json?address=";

	/**
	 * String used to define the protocol to use for making the API call
	 */
	private String protocol;

	/**
	 * String containing the API key
	 */
	private String key;

	/**
	 * Configured ObjectMapper object used to generate parsers for the JSON data
	 */
	private ObjectMapper objmap = configMapper();

	/**
	 * Object used to store the root node of the JSON Payload
	 */
	private JsonNode root;

	/**
	 * Object used to store the geometry node of the JSON payload
	 */
	private JsonNode geom;

	/**
	 * Class constructor
	 * @param address The API call formatted address string
	 * @param apiKey An API key if Applicable
	 * @param obid The long value identifying the observation ID
	 * @param idx A map of string, integer types connecting newly constructed
	 *               variables (e.g., the strings) to their variable indices.
	 *               The map elements are accessed using a type string that
	 *               identified what type of results the end user would like
	 *               to return to Stata
	 * @param retvals Contains the unique list of return type values that the
	 *                   user requests
	 */
	public Geocode(String address, String apiKey, Long obid,
				   Map<String, Map<String, Integer>> idx, HashSet<String> retvals) {

		// Populates the API key value
		if (!apiKey.isEmpty()) {
			this.key = "key=" + apiKey;
			this.protocol = "https";
		} else {
			this.key = "";
			this.protocol = "http";
		}
		String addy = callString(address);
		this.root = parsePayload(addy);
		this.geom = getGeometry();
		setBoundingBoxes();
		setViewPort();
		setLocation();
		setGeoType();
		setFormattedAddress();
		setPlaceID();
		setTypes();
		addVars(retvals, obid, idx);
	}

	/**
	 * Class constructor
	 * @param address The API call formatted address string
	 * @param obid The long value identifying the observation ID
	 * @param idx A map of string, integer types connecting newly constructed
	 *               variables (e.g., the strings) to their variable indices.
	 *               The map elements are accessed using a type string that
	 *               identified what type of results the end user would like
	 *               to return to Stata
	 * @param retvals Contains the unique list of return type values that the
	 *                   user requests
	 */
	public Geocode(String address, Long obid, Map<String, Map<String, Integer>> idx,
				   HashSet<String> retvals) {
		new Geocode(address, "", obid, idx, retvals);
	}

	/**
	 * Method used to construct the API call string
	 * @param address The address passed to the class constructor
	 * @return A string formatted for the API call
	 */
	private String callString(String address) {
		return this.protocol + urlBase + address + this.key;
	}

	/**
	 * Method to configure the Jackson JSON Object Mapper object
	 * @return Returns a configured ObjectMapper object
	 */
	private ObjectMapper configMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, false);
		mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		mapper.configure(DeserializationFeature.EAGER_DESERIALIZER_FETCH, true);
		mapper.configure(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS, true);
		mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, false);
		mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
		mapper.configure(MapperFeature.AUTO_DETECT_FIELDS, true);
		return mapper;
	}

	/**
	 * Method to parse the JSON Payload into a JsonNode object
	 * @param uri A URL Class used to call the API
	 * @return An object of class JsonNode containing the returned payload
	 */
	private JsonNode parsePayload(String uri) {
		JsonNode x = objmap.createObjectNode().path(0);
		try {
			URL apiCall = new URL(uri);
			x = objmap.readTree(apiCall);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return x;
	}

	/**
	 * Currently not used, but here to provide accessor to the address
	 * components object in the payload
	 * @param root The JsonNode containing the payload
	 * @return A list of JsonNodes with the address components
	 */
	private List<JsonNode> getAddressComponents(JsonNode root) {
		return root.findParents("short_name");
	}

	/**
	 * Method used to access the geometry components of the payload
	 * @return The JsonNode containing the geometry elements
	 */
	private JsonNode getGeometry() {
		return this.root.findParent("location");
	}

	/**
	 * Parses the bounding box geometry from the geometry object
	 */
	private void setBoundingBoxes() {
		this.bbox.add(0, this.geom.path("bounds").path("northeast").path("lat").asDouble(Geocode.GEOMISSING));
		this.bbox.add(1, this.geom.path("bounds").path("northeast").path("lng").asDouble(Geocode.GEOMISSING));
		this.bbox.add(2, this.geom.path("bounds").path("southwest").path("lat").asDouble(Geocode.GEOMISSING));
		this.bbox.add(3, this.geom.path("bounds").path("southwest").path("lng").asDouble(Geocode.GEOMISSING));
	}

	/**
	 * Parses the viewport geometry from the geometry object
	 */
	private void setViewPort() {
		this.viewport.add(0, this.geom.path("viewport").path("northeast").path("lat").asDouble(Geocode.GEOMISSING));
		this.viewport.add(1, this.geom.path("viewport").path("northeast").path("lng").asDouble(Geocode.GEOMISSING));
		this.viewport.add(2, this.geom.path("viewport").path("southwest").path("lat").asDouble(Geocode.GEOMISSING));
		this.viewport.add(3, this.geom.path("viewport").path("southwest").path("lng").asDouble(Geocode.GEOMISSING));
	}

	/**
	 * Parses the point location geometry from the geometry object
	 */
	private void setLocation() {
		this.location.add(0, this.geom.path("location").path("lat").asDouble(Geocode.GEOMISSING));
		this.location.add(1, this.geom.path("location").path("lng").asDouble(Geocode.GEOMISSING));
	}

	/**
	 * Parses the location type element from the geometry object
	 */
	private void setGeoType() {
		this.geoType = this.geom.path("location_type").asText("").replace("_", " ").toLowerCase();
	}

	/**
	 * Parses the formatted address value from the payload
	 */
	private void setFormattedAddress() {
		this.formattedAddress = this.root.findPath("formatted_address").asText("");
	}

	/**
	 * Parses the place id value from the payload
	 */
	private void setPlaceID() {
		this.placeId = this.root.findPath("place_id").asText("");
	}

	/**
	 * Parses the geocode types from the payload
	 */
	private void setTypes() {
		this.types = this.root.findPath("types").asText("").replaceAll("\\p{Punct}",	" ").replaceAll("  ", "");
	}

	/**
	 * Adds the point location variables datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               point location variables
	 * @param i The observation to which the datum will be appended
	 */
	private void makeLocation(Map<String, Integer> idx, Long i) {
		Data.storeNum(idx.get("lat"), i, this.location.get(0));
		Data.storeNum(idx.get("lon"), i, this.location.get(1));
	}

	/**
	 * Adds the bounding box variables datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               bounding box variables
	 * @param i The observation to which the datum will be appended
	 */
	private void makeBoundingBox(Map<String, Integer> idx, Long i) {
		Data.storeNum(idx.get("bbox_max_lat"), i, this.bbox.get(0));
		Data.storeNum(idx.get("bbox_max_lon"), i, this.bbox.get(1));
		Data.storeNum(idx.get("bbox_min_lat"), i, this.bbox.get(2));
		Data.storeNum(idx.get("bbox_min_lon"), i, this.bbox.get(3));
	}


	/**
	 * Adds the Viewport variables to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               viewport variables
	 * @param i The observation to which the datum will be appended
	 */
	private void makeViewport(Map<String, Integer> idx, Long i) {
		Data.storeNum(idx.get("viewport_max_lat"), i, this.viewport.get(0));
		Data.storeNum(idx.get("viewport_max_lon"), i, this.viewport.get(1));
		Data.storeNum(idx.get("viewport_min_lat"), i, this.viewport.get(2));
		Data.storeNum(idx.get("viewport_min_lon"), i, this.viewport.get(3));
	}


	/**
	 * Adds the location_type variable datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               location_type variable
	 * @param i The observation to which the datum will be appended
	 */
	private void makeGeoType(Map<String, Integer> idx, Long i) {
		Data.storeStr(idx.get("geo_type"), i, this.geoType);
	}

	/**
	 * Adds the formatted_address variable datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               formatted_address variable
	 * @param i The observation to which the datum will be appended
	 */
	private void makeFormattedAddress(Map<String, Integer> idx, Long i) {
		Data.storeStr(idx.get("formatted_address"), i, this.formattedAddress);
	}

	/**
	 * Adds the place_id variable datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               place_id variable
	 * @param i The observation to which the datum will be appended
	 */
	private void makePlaceId(Map<String, Integer> idx, Long i) {
		Data.storeStr(idx.get("place_id"), i, this.placeId);
	}

	/**
	 * Adds the google_types variable datum to the dataset
	 * @param idx Map object used to look up the variable index for the
	 *               google_types variable
	 * @param i The observation to which the datum will be appended
	 */
	private void makeTypes(Map<String, Integer> idx, Long i) {
		Data.storeStr(idx.get("google_types"), i, this.types);
	}

	/**
	 * Method used to populate data to the added variables in the data set
	 * @param types The list of type options specifying which elements to return
	 * @param obidx The observation ID to add the data to
	 * @param idx An object used to identify the variable name to variable
	 *               index mappings.
	 */
	private void addVars(HashSet<String> types, Long obidx, Map<String, Map<String, Integer>> idx) {
		for (String i : types) {
			switch (i) {
				default:
					makeLocation(idx.get(i), obidx);
					break;
				case "location":
					makeLocation(idx.get(i), obidx);
					break;
				case "bbox":
					makeBoundingBox(idx.get(i), obidx);
					break;
				case "viewport":
					makeViewport(idx.get(i), obidx);
					break;
				case "address":
					makeFormattedAddress(idx.get(i), obidx);
					break;
				case "geotype":
					makeGeoType(idx.get(i), obidx);
					break;
				case "placeid":
					makePlaceId(idx.get(i), obidx);
					break;
				case "types":
					makeTypes(idx.get(i), obidx);
					break;
			}
		}
	}



}
