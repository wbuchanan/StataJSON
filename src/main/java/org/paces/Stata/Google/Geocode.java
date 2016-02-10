package org.paces.Stata.Google;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class Geocode {

	private List<Double> bbox = new ArrayList<>();
	private List<Double> viewport = new ArrayList<>();
	private List<Double> location = new ArrayList<>();
	private String locationType;
	private String formattedAddress = "";
	private String placeId = "";
	private String types = "";
	private static final String urlBase = "://maps.googleapis.com/maps/api/geocode/json?address=";
	private static String protocol;
	private static String key;
	private static final ObjectMapper objmap = configMapper();

	/**
	 * Class constructor to call and access results from the Google Geocoding
	 * API
	 * @param address The address string to geocode
	 * @param key A private API key or a blank string
	 */
	public Geocode(String address, String key) {

		// Populates the API key value
		if (!key.isEmpty()) {
			key = "key=" + key;
			protocol = "https";
		} else {
			key = "";
			protocol = "http";
		}

		// "4287 46th Ave North, Robbinsdale, MN, 55422"
		try {
			JsonNode root = parsePayload(new URL(callString(address.replace(" ", "+"))));
			JsonNode geom = getGeometry(root);
			setBoundingBoxes(geom);
			setViewPort(geom);
			setLocation(geom);
			setLocationType(geom);
			setFormattedAddress(root);
			setPlaceID(root);
			setTypes(root);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * Single parameter class constructor ensures that the key value is empty
	 * @param address The Address string to use for geocoding
	 */
	public Geocode(String address) {
		new Geocode(address, "");
	}

	/**
	 * Method used to construct the API call string
	 * @param address The address passed to the class constructor
	 * @return A string formatted for the API call
	 */
	private String callString(String address) {
		return protocol + urlBase + address + key;
	}

	/**
	 * Method to configure the Jackson JSON Object Mapper object
	 */
	private static ObjectMapper configMapper() {
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
	 * @param apiCall A URL Class used to call the API
	 * @return An object of class JsonNode containing the returned payload
	 * @throws IOException Exception thrown if issue with call to the API
	 */
	public JsonNode parsePayload(URL apiCall) throws IOException {
		return objmap.readTree(apiCall);
	}

	/**
	 * Currently not used, but here to provide accessor to the address
	 * components object in the payload
	 * @param root The JsonNode containing the payload
	 * @return A list of JsonNodes with the address components
	 */
	public List<JsonNode> getAddressComponents(JsonNode root) {
		return root.findParents("short_name");
	}

	/**
	 * Method used to access the geometry components of the payload
	 * @param root The payload returned from Google
	 * @return The JsonNode containing the geometry elements
	 */
	public JsonNode getGeometry(JsonNode root) {
		return root.findParent("location");
	}

	/**
	 * Parses the bounding box geometry from the geometry object
	 * @param geom The JsonNode containing the geometry elements
	 */
	public void setBoundingBoxes(JsonNode geom) {
		this.bbox.add(0, geom.path("bounds").path("northeast").path("lat").asDouble());
		this.bbox.add(1, geom.path("bounds").path("northeast").path("lng").asDouble());
		this.bbox.add(2, geom.path("bounds").path("southwest").path("lat").asDouble());
		this.bbox.add(3, geom.path("bounds").path("southwest").path("lng").asDouble());
	}

	/**
	 * Parses the viewport geometry from the geometry object
	 * @param geom The JsonNode containing the geometry elements
	 */
	public void setViewPort(JsonNode geom) {
		this.viewport.add(0, geom.path("viewport").path("northeast").path("lat").asDouble());
		this.viewport.add(1, geom.path("viewport").path("northeast").path("lng").asDouble());
		this.viewport.add(2, geom.path("viewport").path("southwest").path("lat").asDouble());
		this.viewport.add(3, geom.path("viewport").path("southwest").path("lng").asDouble());
	}

	/**
	 * Parses the point location geometry from the geometry object
	 * @param geom The JsonNode containing the geometry elements
	 */
	public void setLocation(JsonNode geom) {
		this.location.add(0, geom.path("location").path("lat").asDouble());
		this.location.add(1, geom.path("location").path("lng").asDouble());
	}

	/**
	 * Parses the location type element from the geometry object
	 * @param geom The JsonNode containing the geometry elements
	 */
	public void setLocationType(JsonNode geom) {
		this.locationType = geom.path("location_type").asText("").replace("_", " ").toLowerCase();
	}

	/**
	 * Parses the formatted address value from the payload
	 * @param root The JSON Payload from Google
	 */
	public void setFormattedAddress(JsonNode root) {
		this.formattedAddress = root.findPath("formatted_address").asText("");
	}

	/**
	 * Parses the place id value from the payload
	 * @param root The JSON Payload from Google
	 */
	public void setPlaceID(JsonNode root) {
		this.placeId = root.findPath("place_id").toString();
	}

	/**
	 * Parses the geocode types from the payload
	 * @param root The JSON Payload from Google
	 */
	public void setTypes(JsonNode root) {
		this.types = root.findPath("types").toString().replaceAll("\\p{Punct}", " ").replaceAll("  ", "");
	}

	/**
	 * Method to access the bounding box of the point location
	 * @return A list of double values corresponding to the maximum latitude,
	 * max longitude, minimum latitude, and minimum longitude in that order
	 */
	public List<Double> getBoundingBox() {
		return this.bbox;
	}

	/**
	 * Method to access the viewport of the point location
	 * @return A list of double values corresponding to the maximum latitude,
	 * max longitude, minimum latitude, and minimum longitude in that order
	 */
	public List<Double> getViewport() {
		return this.viewport;
	}

	/**
	 * Method that returns the geographic coordinates
	 * @return A list of double values containing the latitude and longitude
	 */
	public List<Double> getLocation() {
		return this.location;
	}

	/**
	 * Method to return the place ID value
	 * @return A String containing Google's place ID
	 */
	public String getPlaceId() {
		return this.placeId;
	}

	/**
	 * Method containing the location type string
	 * @return A string containing the location type from the geocoding
	 */
	public String getLocationType() {
		return this.locationType;
	}

	/**
	 * Method to access the type array values from the geocoding api
	 * @return A string containing the values from the type array of the payload
	 */
	public String getTypes() {
		return this.types;
	}

	/**
	 * Method to return the formatted address
	 * @return A string containing a pretty printed address string
	 */
	public String getFormattedAddress() {
		return this.formattedAddress;
	}

}
