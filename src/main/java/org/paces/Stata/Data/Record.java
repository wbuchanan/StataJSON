package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/***
 * @author Billy Buchanan
 * @version 0.0.0
 * <h1>Interface for Record Objects</h1>
 * <p>Defines methods for Objects that construct individual observation
 * records.</p>
 */
public interface Record {

	/***
	 * Method to set the observation index value for the record
	 * @param observationNumber An observation index value
	 */
	@JsonProperty("_id")
	@JsonSetter
	public void setObid(long observationNumber);

	/***
	 * Constructs the object containing the data for the record
	 */
	@JsonSetter
	public void setData();

	/***
	 * Retrieves the data for a given record
	 * @return An object with the values for variables of interest on a given
	 * observation
	 */
	@JsonGetter
	public Object getData();


} // End of Interface Definition
