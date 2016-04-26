package org.paces.Stata.Input.Interfaces;

import java.util.List;

/**
 * Convenience method used to return the minimum and maximum values of
 * queried indices (assumes that there are no gaps between the minimum and
 * maximum values of the indices).
 * @author Billy Buchanan
 * @version 0.0.0
 */
public interface QueryRange {

	/**
	 * This method will first call the queryIdx method of the QueryIndex
	 * interface and then will combine the minimum and maximum values of the
	 * returned indices.
	 * @param pattern A regular expression used to query JSON elements
	 * @return A two element list containing the minimum and maximum indices
	 * that match the given string.
	 */
	List<Integer> queryRange(String pattern);

}
