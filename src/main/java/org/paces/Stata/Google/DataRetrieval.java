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

	private Variables vars;
	private Observations obs;
	private Map<Long, String> addressList = new HashMap<>();

	public DataRetrieval(String[] args) {
		Meta meta = new Meta(args);
		this.vars = meta.getStatavars();
		this.obs = meta.getStataobs();
		setAddressList();
	}

	public Map<Long, String> getAddressList() {
		return this.addressList;
	}

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

	public static String getAddy(Integer idx, Long obidx) {
		if (Data.isVarTypeStr(idx)) {
			return Data.getStr(idx, obidx);
		}
		else {
			return String.valueOf(Data.getNum(idx, obidx)).replaceAll("\\.0", "");
		}
	}

	public static String formAddy(List<String> addressComponents) {
		StringBuilder addy = new StringBuilder();
		for(String comp : addressComponents)
			if (!comp.matches("^Apt\\.|^Ste\\.|^Fl\\.|^Apartment|^Suite|^Floor")) addy.append(comp).append(" ");
		return cleaner(addy.toString());
	}


	public static String cleaner(String component) {
		String comp = component.replaceAll("[#\\(\\)\\.\\+]", "");
		comp = comp.replaceAll("  ", " ");
		comp = comp.replaceAll(" ", "\\+");
		comp = comp.replaceAll("\\+$", "");
		return comp;
	}


}
