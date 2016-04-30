package org.paces.Stata.Input;

/**
 * An ENUM used to map the JsonNode objects to Stata data types.
 * @author Billy Buchanan
 * @version 0.0.0
 */
public enum StataTypeMap {

	BOOL(0), BYTE(1), DOUBLE(2), FLOAT(3), INT(4), LONG(5),
	STR(6), STRL(7), MISSING(8), MIXED(9), UNKNOWN(10);

	/**
	 * Stores the value of the ENUM
	 */
	private Integer numid;

	/**
	 * Stores the name of the enum value
	 */
	private String name;

	/**
	 * Class constructor; also sets the name
	 * @param type Integer value
	 */
	StataTypeMap(Integer type) {
		this.numid = type;
		setName();
	}

	/**
	 * Private setter method that transforms the name of the enum value to a
	 * lower cased string representation.
	 */
	private void setName() {
		this.name = this.getType(this.numid).toLowerCase();
	}

	/**
	 * Public method to retrieve the name of a given enum
	 * @return The lower cased name representing the value of this enum
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Public method to return the numeric value of the enum
	 * @return The integer value of the enum
	 */
	public Integer getValue() {
		return this.numid;
	}

	/**
	 * Public method that returns an array of possible values
	 * @return An array of Integer types representing all possible values
	 */
	public Integer[] getValues() {
		return new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	}

	/**
	 * Public method that returns an array of all possible enum values
	 * @return An array of String types containing the enum references
	 */
	public String[] getTypes() {
		return new String[]{"BOOL", "BYTE", "DOUBLE", "FLOAT", "INT", "LONG",
		"STR", "STRL", "MISSING", "MIXED", "UNKNOWN"};
	}

	/**
	 * Public method to lookup a specific enum type name given an integer value
	 * @param val The integer value to search for
	 * @return The enum reference name if found else returns UNKNOWN constant.
	 */
	public String getType(Integer val) {
		switch (val) {
			case 0: return "BOOL";
			case 1: return "BYTE";
			case 2: return "DOUBLE";
			case 3: return "FLOAT";
			case 4: return "INT";
			case 5: return "LONG";
			case 6: return "STR";
			case 7: return "STRL";
			case 8: return "MISSING";
			case 9: return "MIXED";
			default: return "UNKNOWN";
		}
	}

}
