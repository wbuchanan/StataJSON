package org.paces.Stata.Input.Interfaces;

import java.util.List;

/**
 * Interface used to query the keys of a Flattened JSON object and return a
 * list of Keys that match the query
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface QueryKey {

	/**
	 * Method used to query the key values of the JSON object.  Implemented
	 * as a single parameter interface to allow using stream/lambda
	 * expressions to process the query a bit faster.
	 * @param pattern The string pattern to attempt matching in the Keys of
	 *                   the flattened JSON object
	 * @return A List of strings containing the matched values
	 */
	List<String> queryKey(String pattern);

}
