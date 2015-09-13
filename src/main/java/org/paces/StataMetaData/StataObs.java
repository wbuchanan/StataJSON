package org.paces.StataMetaData;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Observations Interface</h2>
 * <p>Interface class object for accessing Stata observation metadata.</p>
 */

interface StataObs {

	void setSobs();

	/***
	 * Sets the ending observation index
	 */
	void setEobs();

	/***
	 * Sets the number of effective observations
	 */
	void setNobs();

	/***
	 * Sets a list of effective observation indices
	 * @param start The starting observation index
	 * @param end The ending observation index
	 */
	void setObservationIndex(Long start, Long end);

	/***
	 *
	 * @return the starting observation index value
	 */
	long getSobs();

	/***
	 *
	 * @return The value of the local macro print from Stata
	 */
	String getPrintme();

	/***
	 *
	 * @return the ending observation index value
	 */
	long getEobs();

	/***
	 *
	 * @return the number of effective observations
	 */
	long getNobs();

	/***
	 *
	 * @return the list of effective observation indices
	 */
	List<Long> getObservationIndex();

	/***
	 * Prints the starting observation index value to the Stata console
	 * @param scalar A scalar value of type Long to print
	 */
	void print(Long scalar);

	/***
	 * Prints the starting observation index value to the Stata console
	 * @param obIndices The list of effective observation indices
	 */
	void print(List<Long> obIndices);

} // End of Interface definition
