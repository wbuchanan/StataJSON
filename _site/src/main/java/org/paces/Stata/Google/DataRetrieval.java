package org.paces.Stata.Google;

import com.stata.sfi.Data;
import org.paces.Stata.Data.Meta;
import org.paces.Stata.Data.Observations;
import org.paces.Stata.Data.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class DataRetrieval {

	/**
	 * Contains Variable metadata used in subsequent methods to retrieve data
	 */
	private Variables vars;

	/**
	 * Contains Observation level metadata used to identify observations from
	 * which, and to which, data will be taken and returned
	 */
	private Observations obs;

	/**
	 * A container object storing the for
	 */
	private Map<Long, String> addressList = new HashMap<>();

	/**
	 * Class constructor
	 * @param args Arguments that need to be passed to the Meta object
	 *                constructor
	 */
	public DataRetrieval(String[] args) {
		Meta meta = new Meta(args);
		this.vars = meta.getStatavars();
		this.obs = meta.getStataobs();
		setAddressList();
	}

	/**
	 * Method to retrieve the formatted address strings
	 * @return A Map containing observation index keys and formatted address
	 * string values
	 */
	public Map<Long, String> getAddressList() {
		return this.addressList;
	}

	/**
	 * Method to retrieve all of the addresses that need to be geocoded
	 */
	public void setAddressList() {

		Map<Long, String> addresses = new HashMap<>();

		for (Long i : this.obs.getObservationIndex()) {

			List<String> obaddress = new ArrayList<>();

			for (Integer j : this.vars.getVariableIndex()) {
				obaddress.add(getAddy(j, i));
			}

			addresses.put(i, formAddy(obaddress));

		}

		this.addressList = addresses;

	}

	/**
	 * Method used to access the data elements from Stata to construct the
	 * address strings
	 * @param idx The variable index to retrieve the data from
	 * @param obidx The observation index to retrieve the data from
	 * @return A raw address string component
	 */
	public static String getAddy(Integer idx, Long obidx) {
		if (Data.isVarTypeStr(idx)) {
			return Data.getStr(idx, obidx);
		}
		else {
			return String.valueOf(Data.getNum(idx, obidx)).replaceAll("\\.0", "");
		}
	}

	/***
	 * Method that constructs the raw address string from any component parts
	 * and includes logic to omit elements that could be references to
	 * apartment numbers, suite numbers, and/or floor numbers.
	 * @param addressComponents A list of string valued address string
	 *                             components
	 * @return The cleaned and formatted address string
	 */
	public static String formAddy(List<String> addressComponents) {
		StringBuilder addy = new StringBuilder();
		for(String comp : addressComponents)
			if (!comp.matches("^Apt\\.|^Ste\\.|^Fl\\.|^Apartment|^Suite|^Floor")) addy.append(comp).append(" ");
		return cleaner(addy.toString());
	}


	/**
	 * Method that removes the most erroneous illegal characters from the
	 * address string and uses the appropriate delimiters for the API call
	 * @param component The address to clean
	 * @return The address string formatted for use in the Google Geocode API
	 */
	public static String cleaner(String component) {
		String comp = component.replaceAll("[#\\(\\)\\.\\+]", "");
		comp = comp.replaceAll("  ", " ");
		comp = comp.replaceAll(" ", "\\+");
		comp = comp.replaceAll("\\+$", "");
		return comp;
	}


}
