package org.paces.Stata.Google.Places;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class PlaceTypes {

	public static Set<String> types = new HashSet<>();

	PlaceTypes() {

		types.add("accounting");
		types.add("airport");
		types.add("amusement_park");
		types.add("aquarium");
		types.add("art_gallery");
		types.add("atm");
		types.add("bakery");
		types.add("bank");
		types.add("bar");
		types.add("beauty_salon");
		types.add("bicycle_store");
		types.add("book_store");
		types.add("bowling_alley");
		types.add("bus_station");
		types.add("cafe");
		types.add("campground");
		types.add("car_dealer");
		types.add("car_rental");
		types.add("car_repair");
		types.add("car_wash");
		types.add("casino");
		types.add("cemetery");
		types.add("church");
		types.add("city_hall");
		types.add("clothing_store");
		types.add("convenience_store");
		types.add("courthouse");
		types.add("dentist");
		types.add("department_store");
		types.add("doctor");
		types.add("electrician");
		types.add("electronics_store");
		types.add("embassy");
		types.add("establishment");
		types.add("finance");
		types.add("fire_station");
		types.add("florist");
		types.add("food");
		types.add("funeral_home");
		types.add("furniture_store");
		types.add("gas_station");
		types.add("general_contractor");
		types.add("grocery_or_supermarket");
		types.add("gym");
		types.add("hair_care");
		types.add("hardware_store");
		types.add("health");
		types.add("hindu_temple");
		types.add("home_goods_store");
		types.add("hospital");
		types.add("insurance_agency");
		types.add("jewelry_store");
		types.add("laundry");
		types.add("lawyer");
		types.add("library");
		types.add("liquor_store");
		types.add("local_government_office");
		types.add("locksmith");
		types.add("lodging");
		types.add("meal_delivery");
		types.add("meal_takeaway");
		types.add("mosque");
		types.add("movie_rental");
		types.add("movie_theater");
		types.add("moving_company");
		types.add("museum");
		types.add("night_club");
		types.add("painter");
		types.add("park");
		types.add("parking");
		types.add("pet_store");
		types.add("pharmacy");
		types.add("physiotherapist");
		types.add("place_of_worship");
		types.add("plumber");
		types.add("police");
		types.add("post_office");
		types.add("real_estate_agency");
		types.add("restaurant");
		types.add("roofing_contractor");
		types.add("rv_park");
		types.add("school");
		types.add("shoe_store");
		types.add("shopping_mall");
		types.add("spa");
		types.add("stadium");
		types.add("storage");
		types.add("store");
		types.add("subway_station");
		types.add("synagogue");
		types.add("taxi_stand");
		types.add("train_station");
		types.add("travel_agency");
		types.add("university");
		types.add("veterinary_care");
		types.add("zoo");

	}

	public static Set<String> getTypes() {
		return types;
	}

	public static Boolean isValid(String thisType) {
		return types.contains(thisType);
	}

}
