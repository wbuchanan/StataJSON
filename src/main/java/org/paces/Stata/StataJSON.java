package org.paces.Stata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stata.sfi.SFIToolkit;

import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class StataJSON {

	/*
	public static int fromStata(String[] args) {

		Meta toconvert = new Meta(args);

		String dbug = Macro.getLocalSafe("dbug");

		return 0;
	}
	*/

	static Meta dbg;
	static List<Long> obidx;

	/***
	 * Main entry point to application
	 * @param args Arguments passed from javacall
	 */
	public static void main(String[] args) {
	}

	public static int dbug(String[] args) throws JsonProcessingException, NullPointerException {
		dbg = new Meta(args);
		obidx = dbg.getObsindex();
		obidx.parallelStream().forEach(ob -> {
			try {
				toJSON(ob);
			} catch (JsonProcessingException e) {
				SFIToolkit.errorln(String.valueOf(e));
			}
		});
		return 0;
	}

	public static void toJSON(Long id) throws JsonProcessingException {
		ObjectMapper themap = new ObjectMapper();
		SFIToolkit.displayln(themap.writeValueAsString(dbg.getRecord(id)));
	}

	public static String printRecord(Map<String, ?> records) {
		StringBuilder stringRec = new StringBuilder();
		stringRec.append("{ \n");
		for (String x : records.keySet()) {
			stringRec.append("\"").append(x).append("\" : ")
					.append(records.get(x)).append(", ");
		}
		stringRec.append("\n },");
		return stringRec.toString();
	}

}
