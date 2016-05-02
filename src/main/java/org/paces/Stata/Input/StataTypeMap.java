package org.paces.Stata.Input;

/**
 * An ENUM used to map the JsonNode objects to Stata data types.
 *
 * <table summary="Mappings from Enum values, to Stata and JsonNode types">
 *     <tr><td>Enum Value</td><td>Stata Type</td><td>JsonNode Type</td></tr>
 *     <tr><td>BOOL</td><td>byte (0 = false; 1 =
 *     true)</td><td>boolean</td></tr>
 *     <tr><td>BYTE</td><td>byte</td><td>N/A</td></tr>
 *     <tr><td>DOUBLE</td><td>double</td><td>Double, Float,
 *     BigDecimal, BigInteger, FloatingPointNumber, Integral
 *     Number, Long, and Number</td></tr>
 *     <tr><td>FLOAT</td><td>double</td><td>Float</td></tr>
 *     <tr><td>INT</td><td>long</td><td>Integer</td></tr>
 *     <tr><td>LONG</td><td>double</td><td>Long</td></tr>
 *     <tr><td>STR</td><td>string</td><td>String</td></tr>
 *     <tr><td>STRL</td><td>strL</td><td>Binary, Object, and POJO</td></tr>
 *     <tr><td>MISSING</td><td>byte/string</td><td>Missing</td></tr>
 *     <tr><td>MIXED</td><td>string</td><td>N/A (multiple types in
 *     keyvalue mode)</td></tr>
 *     <tr><td>UNKNOWN</td><td>N/A</td><td>N/A</td></tr>
 * </table>
 *
 * @author Billy Buchanan
 * @version 0.0.0
 */
public enum StataTypeMap {

	/**
	 * Java Boolean to Stata indicator type
	 */
	BOOL(0),
	/**
	 * Java byte to Stata byte (currently unused, but will be used for
	 * integers in [-127, 100)
	 */
	BYTE(1),
	/**
	 * Maps 8-byte Integers, Floats, and Doubles to Stata double types
	 */
	DOUBLE(2),
	/**
	 * Float types are recast to doubles before pushing into Stata
	 */
	FLOAT(3),
	/**
	 * Java short types are loaded as Stata int types (e.g., 2-byte Integers)
	 */
	INT(4),
	/**
	 * Java int types are loaded as Stata long types (e.g., 4-byte Integers)
	 */
	LONG(5),
	/**
	 * Generic String type
	 */
	STR(6),
	/**
	 * Stata blob type, used for binary data, objects, and POJOs
	 */
	STRL(7),
	/**
	 * In key-value mode will return byte with value 127 if all values are
	 * missing or an empty string,  In row-value mode will always return a
	 * byte variable with value 127.
	 */
	MISSING(8),
	/**
	 * Only used in key-value mode to indicate multiple types of data.
	 * Forces all data to be loaded as string types.
	 */
	MIXED(9),
	/**
	 * Currently not used, but would load the data into a strL
	 */
	UNKNOWN(10);

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
