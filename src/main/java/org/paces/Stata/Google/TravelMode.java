package org.paces.Stata.Google;

/**
 * Enumeration containing the travel modes for the Google directions API
 * @author Billy Buchanan
 * @version 0.0.0
 */
public enum TravelMode {

	DRIVING("driving"),
	WALKING("walking"),
	BICYCLING("bicycle"),
	TRANSIT("transit");

	private final String name;

	private TravelMode(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return otherName != null && name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}

}
