package org.paces.Stata.Input;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public enum StataTypeMap {
	BOOL(0), BYTE(1), DOUBLE(2), FLOAT(3), INT(4), LONG(5),
	STR(6), STRL(7), MISSING(8), MIXED(9), UNKNOWN(10);

	private Integer numid;

	StataTypeMap(Integer type) {
		this.numid = type;
	}

	public Integer getValue() {
		return this.numid;
	}

	public Integer[] getValues() {
		return new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	}

	public String[] getTypes() {
		return new String[]{"BOOL", "BYTE", "DOUBLE", "FLOAT", "INT", "LONG",
		"STR", "STRL", "MISSING", "MIXED", "UNKNOWN"};
	}

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
