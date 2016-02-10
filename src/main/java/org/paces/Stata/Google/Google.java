package org.paces.Stata.Google;

import com.stata.sfi.Data;
import com.stata.sfi.Macro;

import java.util.*;

/**
 * Class that acts as the entry point to the Google APIs
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class Google {

	public static int geocode(String[] args) {
		HashSet<String> geotypes = processGeoTypes(Macro.getLocalSafe("what"));
		DataRetrieval data = new DataRetrieval(args);
		Map<Long, Geocode> geo = new HashMap<>();
		for(Long i : data.getAddressList().keySet()) {
			geo.put(i, new Geocode(data.getAddressList().get(i)));
		}
		Map<String, Map<String, Integer>> vdx = getAllVarIndices(geotypes);
		addVars(geotypes, geo, vdx);
		return 1;
	}

	public static HashSet<String> processGeoTypes(String t) {
		HashSet<String> types;
		if (t.isEmpty()) t = "location";
		types = new HashSet<>(Arrays.asList(t.split(" ")));
		Iterator<String> typeLoop = types.iterator();
		while (typeLoop.hasNext()) {
			setVars(typeLoop.next());
		}
		return types;
	}

	public static Map<String, Map<String, Integer>> getAllVarIndices
			(HashSet<String> geoTypes) {
		Map<String, Map<String, Integer>> newVarIndices = new HashMap<>();
		Iterator<String> iterator = geoTypes.iterator();
		while(iterator.hasNext()) {
			String i = iterator.next();
			newVarIndices.put(i, getVarIndex(i));
		}
		return newVarIndices;
	}

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
			case "loctype":
				return GoogleStataVars.getLocationTypeIndices();
			case "placeid" :
				return GoogleStataVars.getPlaceIdIndices();
			case "types":
				return GoogleStataVars.getTypesIndices();
			default :
				return GoogleStataVars.getLocationIndices();
		}
	}


	public static void setVars(String vars) {
		switch (vars) {
			case "location" :
				GoogleStataVars.makeGeocodeLocationVars();
			case "bbox" :
				GoogleStataVars.makeGeocodeBoundingBoxVars();
			case "viewport":
				GoogleStataVars.makeGeocodeViewportVars();
			case "address" :
				GoogleStataVars.makeGeocodeFormattedAddressVars();
			case "loctype":
				GoogleStataVars.makeGeocodeLocationTypeVars();
			case "placeid" :
				GoogleStataVars.makeGeocodePlaceIdVars();
			case "types":
				GoogleStataVars.makeGeocodeTypesVars();
			default :
				GoogleStataVars.makeGeocodeLocationVars();
		}
	}

	public static void addVars(HashSet<String> types, Map<Long, Geocode> geo,
							   Map<String, Map<String, Integer>> idx) {
		Iterator<String> iterator = types.iterator();
		while(iterator.hasNext()) {
			String i = iterator.next();
			switch (i) {
				case "bbox" :
					setBoundingBox(geo, idx.get(i));
				case "viewport":
					setViewport(geo, idx.get(i));
				case "address" :
					setFormattedAddress(geo, idx.get(i));
				case "loctype":
					setLocationType(geo, idx.get(i));
				case "placeid" :
					setPlaceId(geo, idx.get(i));
				case "types":
					setTypes(geo, idx.get(i));
				default:
					setLocation(geo, idx.get(i));
			}
		}
	}


	public static void setLocation(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeNum(idx.get("lat"), i, geo.get(i).getLocation().get(0));
			Data.storeNum(idx.get("lon"), i, geo.get(i).getLocation().get(1));
		}
	}

	public static void setBoundingBox(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeNum(idx.get("bbox_max_lat"), i, geo.get(i).getBoundingBox().get(0));
			Data.storeNum(idx.get("bbox_max_lon"), i, geo.get(i).getBoundingBox().get(1));
			Data.storeNum(idx.get("bbox_min_lat"), i, geo.get(i).getBoundingBox().get(2));
			Data.storeNum(idx.get("bbox_min_lon"), i, geo.get(i).getBoundingBox().get(3));
		}
	}


	public static void setViewport(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeNum(idx.get("viewport_max_lat"), i, geo.get(i).getViewport().get(0));
			Data.storeNum(idx.get("viewport_max_lon"), i, geo.get(i).getViewport().get(1));
			Data.storeNum(idx.get("viewport_min_lat"), i, geo.get(i).getViewport().get(2));
			Data.storeNum(idx.get("viewport_min_lon"), i, geo.get(i).getViewport().get(3));
		}
	}


	public static void setLocationType(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeStr(idx.get("location_type"), i, geo.get(i).getLocationType());
		}
	}

	public static void setFormattedAddress(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeStr(idx.get("formatted_address"), i, geo.get(i).getFormattedAddress());
		}
	}


	public static void setPlaceId(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeStr(idx.get("place_id"), i, geo.get(i).getPlaceId());
		}
	}


	public static void setTypes(Map<Long, Geocode> geo, Map<String, Integer> idx) {
		for (Long i : geo.keySet()) {
			Data.storeStr(idx.get("google_types"), i, geo.get(i).getTypes());
		}
	}



}
