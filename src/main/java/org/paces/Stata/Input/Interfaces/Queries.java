package org.paces.Stata.Input.Interfaces;

import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface Queries {

	/**
	 * Method used to query the key values of the JSON object.
	 * @param pattern The string pattern to attempt matching in the Keys of
	 *                   the flattened JSON object
	 * @return A List of strings containing the matched values
	 */
	List<String> queryKey(String pattern);

	/**
	 * This method will first call the queryIdx method of the QueryIndex
	 * interface and then will combine the minimum and maximum values of the
	 * returned indices.
	 * @param pattern A regular expression used to query JSON elements
	 * @return A two element list containing the minimum and maximum indices
	 * that match the given string.
	 */
	List<Integer> queryRange(String pattern);

	/**
	 * Method used to query the key values of the JSON object.
	 * @param pattern The string pattern to attempt matching in the Keys of
	 *                   the flattened JSON object
	 * @return A List of integers containing the indices of the matched keys.
	 */
	List<Integer> queryIndex(String pattern);


}
