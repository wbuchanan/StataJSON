package org.paces.Stata;
import com.stata.sfi.Data;
import com.stata.sfi.ValueLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.stata.sfi.Data.getParsedVarCount;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Variable MetaData Object</h2>
 * <p>Class used for Stata's Java API to access the metadata for Stata variables
 * .</p>
 */
public class Variables {

	// Member variable containing variable indices
	public List<Integer> varindex;

	// Member variable containing Stata variable names
	public List<String> varnames;

	// Member variable containing Stata variable labels
	public List<String> varlabels;

	// Member variable containing Stata value label names associated with a
	// given variable
	public List<String> valueLabelNames;

	// Member variable containing a list of Map objects with the values and
	// associated labels contained in the Map object
	public List<Map<Integer, String>> valueLabels;

	// Member variable containing indicators for whether or not the variable
	// is of type String
	public List<Boolean> varTypes;

	/***
	 * Generic constructor when varlist is passed
	 * @param arguments Transformed list of strings from the args parameter
	 */
	Variables(List<String> arguments) {

		// Set the variable index member variable
		setVariableIndex(arguments);

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
	 * Generic constructor when no varlist is passed
	 */
	Variables() {

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
	 * @param args Passed from javacall
	 */
	public void setVariableIndex(List<String> args) {
		this.varindex.addAll(args.stream().map(Data::getVarIndex).
				collect(Collectors.<Integer>toList()));
	}

	public void setVariableIndex() {

		// Initialize an empty array list of Integer objects
		List<Integer> vars = new ArrayList<>();

		// Loop over the total indices of variables
		for (int i = 0; i < getParsedVarCount(); i++) {

			// Add the index value to the list object
			vars.add(i);

		} // End Loop over values

		// Set the variable index member variable's values
		this.varindex = vars;

	} // End setter method for varindex variable


	/***
	 * Sets an object containing variable names from Stata data set.
	 * Requires the variable index.
	 */
	public void setVariableNames() {

		List<String> tmp = new ArrayList<>();

		// Iterate over the variable indices
		// Add the variable name to the list object
		tmp.addAll(this.varindex.stream().map(Data::getVarName).
					collect(Collectors.toList()));

		// Set the variable names member variable
		this.varnames = tmp;

	} // End setter method for variable names

	/***
	 * Sets an object containing variable labels from Stata data set.
	 * Requires the variable index.
	 */
	public void setVariableLabels() {

		List<String> tmp = new ArrayList<>();

		// Iterate over the variable indices
		// Add the variable name to the list object
		tmp.addAll(this.varindex.stream().map(Data::getVarLabel).
				collect(Collectors.toList()));

		// Set the variable names member variable
		this.varlabels = tmp;

	} // End setter method for variable labels

	/***
	 * Sets an object containing name of value label associated with the
	 * index value
	 * Requires the variable index.
	 */
	public void setValueLabelNames() {

		List<String> tmp = new ArrayList<>();

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
	public void setValueLabels() {

		// Initialize temporary container object
		List<Map<Integer, String>> valabs = new ArrayList<>();

		// Loop over the variable indices
		for (String vdx : this.valueLabelNames) {

			// Create temp object to store the value label set
			Map<Integer, String> labels = new HashMap<>();

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
	public void setVariableTypeIndex() {

		List<Boolean> tmp = new ArrayList<>();

		// Iterate over the variable indices
		// Add the variable name to the list object
		tmp.addAll(this.varindex.stream().map(Data::isVarTypeString).
				collect(Collectors.toList()));

		// Set the variable type index
		this.varTypes = tmp;

	} // End setter method for variable type index

	/***
	 * Accessor method for variable index variable
	 *
	 * @return A list of Integer objects containing variable indices
	 */
	public List<Integer> getVariableIndex() {
		return this.varindex;
	}

	/***
	 * @return A list of String objects containing variable names
	 */
	public List<String> getVariableNames() {
		return this.varnames;
	}

	/***
	 * @param varidx valid variable index value
	 * @return Name of variable at index varidx
	 */
	public String getName(int varidx) {
		return this.varnames.get(varidx);
	}

	/***
	 * @param varidx valid variable index value
	 * @return Variable Label
	 */
	public String getVarLabel(int varidx) {
		return this.varlabels.get(varidx);
	}

	/***
	 * @param varidx valid variable index value
	 * @return Name of variable at index varidx
	 */
	public Boolean getVarType(int varidx) {
		return this.varTypes.get(varidx);
	}

	/***
	 * @param varidx valid variable index value
	 * @return Name of value label associated with a given variable index
	 */
	public String getValueLabelName(int varidx) { return this.valueLabelNames
			.get(varidx); }


	/***
	 * @return A list of String objects containing variable labels.
	 */
	public List<String> getVariableLabels() {
		return this.varlabels;
	}

	/***
	 * @return A list of String objects containing value label names.
	 */
	public List<String> getValueLabelNames() {
		return this.valueLabelNames;
	}

	/***
	 * @return A list of Map objects containing the value/label pairs for
	 * labeled variables or the keyword "skip" to indicate the variable does not
	 * have any value labels associated with it.
	 */
	public List<Map<Integer, String>> getValueLabels() {
		return this.valueLabels;
	}

	/***
	 * @return A list of Boolean objects indicating if variable is a string
	 */
	public List<Boolean> getVariableTypes() {
		return this.varTypes;
	}

} // End Class definition
