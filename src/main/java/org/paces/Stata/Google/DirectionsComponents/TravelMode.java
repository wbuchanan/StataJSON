package org.paces.Stata.Google.DirectionsComponents;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class TravelMode {

	private String name = "mode=";
	private String type;
	private String ending = "&";

	public TravelMode(String arg) {
		if (FixedOptions.getOptionList("mode").contains(arg)) this.type = arg;
		else this.type = "";
	}


	private Boolean isEmpty() {
		return this.type.isEmpty();
	}

	/**
	 * Returns the direction's API default travel mode (e.g., driving) if no
	 * mode is specified.
	 * @return
	 */
	public String toString() {
		if (!isEmpty()) return this.name + this.type + this.ending;
		else return "mode=driving&";
	}

}
