package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.stata.sfi.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Observations Interface Implementation</h2>
 * <p>Implementation of the Stata Observation interface class.</p>
 */
public class Observations {

	/***
	 * Starting observation index number
	 */
	@JsonProperty(required = true, value = "Starting Observation")
	private long sobs;

	/***
	 * Ending observation index number
	 */
	@JsonProperty(required = true, value = "Ending Observation")
	private long eobs;

	/***
	 * Total Number of Observations
	 */
	@JsonProperty(required = true, value = "Total Number of Observations")
	private long nobs;

	/***
	 * Observation indices
	 */
	@JsonProperty(required = true, value = "Observation Indices")
	private List<Long> obindex;

	/***
	 * Constructor method for class ObservationsImpl
	 */
	@JsonCreator
	public Observations() {

		// Set the starting observation index
		setSobs();

		// Set the ending observation index
		setEobs();

		// Set the observation indices
		setObservationIndex(this.sobs, this.eobs);

		// Set the total number of observations
		setNobs();

	} // End of constructor definition

	/***
	 * Sets the starting observation index
	 */
	@JsonSetter
	public void setSobs() {
		this.sobs = Data.getObsParsedIn1();
	}

	/***
	 * Sets the starting observation index
	 */
	@JsonSetter
	public void setEobs() {
		this.eobs = Data.getObsParsedIn2();
	}

	/***
	 * Sets the number of effective observations
	 */
	@JsonSetter
	public void setNobs() {

		// Return the size of the list object
		this.nobs = ((long) this.obindex.size());

	} // End Method returning the number of observations

	/***
	 * Sets a list of effective observation indices
	 * @param start The starting observation index
	 * @param end The ending observation index
	 */
	@JsonSetter
	public void setObservationIndex(Long start, Long end) {

		// Initialize temp variable
		List tmp = new ArrayList<>();

		// Loop over the observations
		for(Long i = start; i <= end; i++) {

			// Test whether the observation satisfies an existing if condition
			if (!Data.isParsedIfTrue(i)) continue;

			//noinspection unchecked
			tmp.add(i);

		} // End Loop over observations

		// Set the observation index member value based on the local ArrayList
		//noinspection unchecked
		this.obindex = tmp;

	} // End method definition to set observation index

	/***
	 * @return the starting observation index value
	 */
	@JsonGetter
	public long getSobs() { return(this.sobs); }

	/***
	 * @return the ending observation index value
	 */
	@JsonGetter
	public long getEobs() { return(this.eobs); }

	/***
	 *
	 * @return the number of effective observations
	 */
	@JsonGetter
	public long getNobs() {
		return(this.nobs);
	}

	/***
	 * @return the list of effective observation indices
	 */
	@JsonGetter
	public List<Long> getObservationIndex() { return(this.obindex); }

} // End Class definition
