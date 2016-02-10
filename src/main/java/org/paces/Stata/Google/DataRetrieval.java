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

		for (long i = 0L; i < this.obs.getObservationIndex().size(); i++) {

			List<String> obaddress = new ArrayList<>();

			for (Integer j : this.vars.getVariableIndex()) {
				obaddress.add(getAddy(j, i));
			}

			addresses.put(i, formAddy(obaddress));

		}

		this.addressList = addresses;

	}

	public static String getAddy(Integer idx, Long obidx) {
		if (!Data.isVarTypeStr(idx)) {
			return cleaner(String.valueOf(Data.getNum(idx, obidx)));
		}
		else {
			return cleaner(Data.getStr(idx, obidx));
		}
	}

	public static String formAddy(List<String> addressComponents) {

		return addressComponents.stream().toString();

	}

	public static String cleaner(String component) {
		return component.replaceAll("[^0-9a-zA-Z,\\. ]", "")
						.replaceAll("  ", "+")
						.replaceAll(",", ",+");

	}


}
