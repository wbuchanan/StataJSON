package org.paces.StataMetaData;
import com.stata.sfi.Data;
import com.stata.sfi.Macro;
import com.stata.sfi.SFIToolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Observations Interface Implementation</h2>
 * <p>Implementation of the Stata Observation interface class.</p>
 */

public class StataObsImpl implements StataObs {

	// Starting observation index number
	private long sobs;

	// Ending observation index number
	private long eobs;

	// Total Number of Observations
	private long nobs;

	// Observation indices
	private List<Long> obindex;

	// Get printme argument
	final String printme = Macro.getLocalSafe("print");

	/***
	 * Constructor method for class ObservationsImpl
	 */
	public StataObsImpl() {

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
	public void setSobs() {
		this.sobs = Data.getObsParsedIn1();
	}

	/***
	 * Sets the starting observation index
	 */
	public void setEobs() {
		this.eobs = Data.getObsParsedIn2();
	}

	/***
	 * Sets the number of effective observations
	 */
	public void setNobs() {

		// Return the size of the list object + 1 to account for java indexing
		this.nobs = ((long) this.obindex.size() + 1);

	} // End Method returning the number of observations

	/***
	 * Sets a list of effective observation indices
	 * @param start The starting observation index
	 * @param end The ending observation index
	 */
	public void setObservationIndex(Long start, Long end) {

		// Initialize temp variable
		List tmp = new ArrayList<Long>();

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
	public long getSobs() { return(this.sobs); }

	/***
	 *
	 * @return The value of the local macro print
	 */
	public String getPrintme() { return(this.printme); }

	/***
	 * @return the ending observation index value
	 */
	public long getEobs() { return(this.eobs); }

	/***
	 *
	 * @return the number of effective observations
	 */
	public long getNobs() {
		return(this.nobs);
	}

	/***
	 * @return the list of effective observation indices
	 */
	public List<Long> getObservationIndex() { return(this.obindex); }

	/***
	 * Prints the starting observation index value to the Stata console
	 * @param scalar A scalar value of type Long to print
	 */
	public void print(Long scalar)  {
		SFIToolkit.displayln(String.valueOf(scalar));
	}


	/***
	 * Prints the starting observation index value to the Stata console
	 * @param obIndices The list of effective observation indices
	 */
	public void print(List<Long> obIndices) {

		// Use foreach loop to iterate over the items and print them to the
		// Stata console.
		for (Long obIndex : obIndices) {

			// Print value to Stata console
			SFIToolkit.displayln(String.valueOf(obIndex));

		} // End While loop over iterator elements

	} // End Print method for observation indices

} // End Class definition
