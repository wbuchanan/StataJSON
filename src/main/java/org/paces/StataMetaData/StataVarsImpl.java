package org.paces.StataMetaData;
import com.stata.sfi.Data;
import com.stata.sfi.Macro;
import com.stata.sfi.ValueLabel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Variable MetaData Object</h2>
 * <p>Class used for Stata's Java API to access the metadata for Stata variables
 * .</p>
 */
public class StataVarsImpl implements StataVars {

	// Member variable containing variable indices
	private List<Integer> varindex;

	// Member variable containing Stata variable names
	private List<String> varnames;

	// Member variable containing Stata variable labels
	private List<String> varlabels;

	// Member variable containing Stata value label names associated with a
	// given variable
	private List<String> valueLabelNames;

	// Member variable containing a list of Map objects with the values and
	// associated labels contained in the Map object
	private List<Map<Integer, String>> valueLabels;

	// Member variable containing indicators for whether or not the variable
	// is of type String
	private List<Boolean> varTypes;

	/***
	 * Constructor method for class StataVarsImpl
	 */
	public StataVarsImpl() {

		// Set the variable index member variable
		setVariableIndex();

		// Set the variable name member variable
		setVariableNames();

		// Set the variable label member variable
		setVariableLabels();

		// Set the value label name member variable
		setValueLabelNames();

		// Set the value label value/label pair member variable
		setValueLabels();

		// Set the variable is string index member variable
		setVariableTypeIndex();

	} // End constructor method

	/***
	 * Sets an object containing the indices for variables accessed from Stata
	 */
	@Override
	public void setVariableIndex() {

		// Initialize an empty array list of Integer objects
		List<Integer> vars = new ArrayList<Integer>();

		if (Data.isVarlistSpecified()) {

			// Get a string object with the contents of the varlist macro
			String varl = Macro.getLocalSafe("varlist");

			String[] varlist = varl.split(" ");

			// Loop over the variable names passed from javacall
			for (String vindex : varlist) {

				// Get the variable index for a given variable name and append
				// the variable index to the ArrayList object
				vars.add(Data.getVarIndex(vindex));

			} // End Loop over variable names

		} else {

			// Get the total number of variables
			int allVars = Data.getParsedVarCount();

			// Iterate over the number of variables
			for (int vindex = 0; vindex <= allVars - 1; vindex++) {

				// Append values to ArrayList object
				vars.add(vindex);

			} // End Loop over values

		} // End IF/ELSE Block for java call w/ w/o varlist included

		// Set the variable index member variable's values
		this.varindex = vars;

	} // End setter method for varindex variable


	/***
	 * Sets an object containing variable names from Stata data set.
	 * Requires the variable index.
	 */
	@Override
	public void setVariableNames() {

		List<String> tmp = new ArrayList<String>();

		// Iterate over the variable indices
		for (Integer vdx : this.varindex) {

			// Add the variable name to the list object
			tmp.add(Data.getVarName(vdx));

		} // End Loop

		// Set the variable names member variable
		this.varnames = tmp;

	} // End setter method for variable names

	/***
	 * Sets an object containing variable labels from Stata data set.
	 * Requires the variable index.
	 */
	@Override
	public void setVariableLabels() {

		List<String> tmp = new ArrayList<String>();

		// Iterate over the variable indices
		for (Integer vdx : this.varindex) {

			// Add the variable name to the list object
			tmp.add(Data.getVarLabel(vdx));

		} // End Loop

		// Set the variable names member variable
		this.varlabels = tmp;

	} // End setter method for variable labels

	/***
	 * Sets an object containing name of value label associated with the
	 * index value
	 * Requires the variable index.
	 */
	@Override
	public void setValueLabelNames() {

		List<String> tmp = new ArrayList<String>();

		// Iterate over the variable indices
		for (Integer vdx : this.varindex) {

			// Assign the variable label name to a temporary variable
			String tmpLabel = ValueLabel.getVarValueLabel(vdx);

			// If the method returned null add an empty string to the list
			// object.  Otherwise, add the value returned from the method
			if (tmpLabel == null) tmp.add("");
			else tmp.add(tmpLabel);

		} // End Loop

		// Set the variable names member variable
		this.valueLabelNames = tmp;

	} // End setter method for variable labels


	/***
	 * Sets an object with the value labels defined for a given variable.
	 * Requires the variable index.
	 */
	@Override
	public void setValueLabels() {

		// Initialize temporary container object
		List<Map<Integer, String>> valabs = new ArrayList<Map<Integer,
				String>>();

		// Loop over the variable indices
		for (String vdx : this.valueLabelNames) {

			// Create temp object to store the value label set
			Map<Integer, String> labels = new HashMap<Integer, String>();

			// Test whether the object is null/empty
			if (vdx == null) {

				// If the temporary variable is null assign an empty string to
				// the map object
				labels.put(0, "");

			} else if (!vdx.isEmpty()) {

				// Add the value labels to the object
				labels.putAll(ValueLabel.getValueLabels(vdx));

			} else {

				// For variables w/o valid variable labels add this as the
				// pseudo value label
				labels.put(0, "");

			} // End IF/ELSE Block for value labels

			// Add the specific value label metadata to the list object
			valabs.add(labels);

		} // End Loop over value label name index

		// Set the value label index variable
		this.valueLabels = valabs;

	} // End setter method for value label index

	/***
	 * Sets an object containing booleans indicating whether the variable
	 * is/isn't a string.
	 * Requires the variable index.
	 */
	@Override
	public void setVariableTypeIndex() {

		List<Boolean> tmp = new ArrayList<Boolean>();

		// Iterate over the variable indices
		for (Integer vdx : this.varindex) {

			// Add the variable name to the list object
			tmp.add(Data.isVarTypeString(vdx));

		} // End Loop

		// Set the variable type index
		this.varTypes = tmp;

	} // End setter method for variable type index

	/***
	 * Accessor method for variable index variable
	 *
	 * @return A list of Integer objects containing variable indices
	 */
	@Override
	public List<Integer> getVariableIndex() {
		return this.varindex;
	}

	/***
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing variable names
	 */
	@Override
	public List<String> getVariableNames(List<Integer> varidx) {
		return this.varnames;
	}

	/***
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing variable labels.
	 */
	@Override
	public List<String> getVariableLabels(List<Integer> varidx) {
		return this.varlabels;
	}

	/***
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing value label names.
	 */
	@Override
	public List<String> getValueLabelNames(List<Integer> varidx) {
		return this.valueLabelNames;
	}

	/***
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of Map objects containing the value/label pairs for
	 * labeled variables or the keyword "skip" to indicate the variable does not
	 * have any value labels associated with it.
	 */
	@Override
	public List<Map<Integer, String>> getValueLabels(List<Integer> varidx) {
		return this.valueLabels;
	}

	/***
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of Boolean objects indicating if variable is a string
	 */
	@Override
	public List<Boolean> getVariableTypes(List<Integer> varidx) {
		return this.varTypes;
	}

} // End Class definition
