package org.paces.Stata.Data;

import com.fasterxml.jackson.annotation.*;
import com.stata.sfi.Data;
import com.stata.sfi.ValueLabel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Variable MetaData Object</h2>
 * <p>Class used for Stata's Java API to access the metadata for Stata
 * variables.  This class is initialized by the Meta class object.</p>
 */
@JsonPropertyOrder({ "variable indices", "number of variables",
		"variable type string", "variable names",
		"variable labels", "value label names", "value labels"})
public class Variables {

	/***
	 * Inner class containing the variable indices
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("variable indices")
	public class VariableIndex {

		/***
		 * Member variable used to create a name : "", value : {} JSON output
		 */
		@JsonProperty("name")
		private final String name = "variable index";

		/***
		 * Member variable containing variable indices
		 */
		@JsonProperty("values")
		private List<Integer> varindex;

		/***
		 * Inner class for variable indices
		 */
		@JsonCreator
		public VariableIndex() {
			setVariableIndex();
		}

		/***
		 * Populates the variable index member variable with the indices used to
		 * identify variables in the Stata dataset in memory.
		 */
		@JsonSetter
		public void setVariableIndex() {

			// Initialize an empty array list of Integer objects
			List<Integer> vars = new ArrayList<>();

			// Get the number of parsed variables
			int parsed = Data.getParsedVarCount();

			// Get the total number of variables in the dataset
			int allvars = Data.getVarCount();

			// If there are variables parsed from the variable list
			if (parsed != allvars) {

				// Loop over the variables
				for (int i = 1; i <= parsed; i++) {

					// Get the index for the individual variables passed as a
					// varlist
					vars.add(Data.mapParsedVarIndex(i));

				} // End Loop over varlist variables

				// Set the variable index member variable's values
				this.varindex = vars;

			} else {

				// Loop over the total indices of variables
				for (int i = 0; i < allvars; i++) {

					// Add the index value to the list object
					vars.add(i + 1);

				} // End Loop over values

				// Set the variable index member variable's values
				this.varindex = vars;

			} // End IF/ELSE Block for variable list handling

		} // End setter method for varindex variable

		/***
		 * Accessor method for variable index variable
		 *
		 * @return A list of Integer objects containing variable indices
		 */
		@JsonGetter
		public List<Integer> getValues() {
			return this.varindex;
		}

		@JsonGetter
		public int getValue(Integer indx) {
			return this.varindex.get(indx);
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class definition

	/***
	 * Inner class containing number of variables
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("number of variables")
	public class Nvars {

		@JsonProperty("name")
		private final String name = "number of variabels";

		/***
		 * Number of variables passed from javacall
		 */
		@JsonProperty("values")
		private int nvars;

		/***
		 * Inner class definition
		 * @param varIndex A VariableIndex class object
		 */
		@JsonCreator
		public Nvars(VariableIndex varIndex) {

			// Pass the variable index to the setter method
			setNvars(varIndex.getValues());

		} // End constructor declaration

		/***
		 * Method to set the number of variables passed to javacall
		 *
		 * @param varidx A variable index
		 */
		@JsonSetter
		public void setNvars(List<Integer> varidx) {

			// Set nvars based on the size of the List of integer objects
			this.nvars = varidx.size();

		} // End setter method for nvars

		/***
		 * Method to access the number of variables passed from javacall
		 *
		 * @return An integer value with the number of variables passed to javacall
		 */
		@JsonGetter
		public int getValues() {

			// Returns the nvars member variable
			return this.nvars;

		} // End of getter method for nvars member variable

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Inner class containing variable names list
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("variable names")
	public class VarNames {

		@JsonProperty("name")
		private final String name = "Variable Names";

		/***
		 * Member variable containing Stata variable names
		 */
		@JsonProperty("values")
		private List<String> varnames;

		/***
		 * Sets an object containing variable names from Stata data set.
		 * Requires the variable index.
		 */
		@JsonSetter
		public void setVariableNames(List<Integer> vdx) {

			List<String> tmp = new ArrayList<>();

			// Iterate over the variable indices
			// Add the variable name to the list object
			tmp.addAll(vdx.stream().map(Data::getVarName).
					collect(Collectors.toList()));

			// Set the variable names member variable
			this.varnames = tmp;

		} // End setter method for variable names

		/***
		 * Constructor for inner class
		 * @param varIndex A variable index object
		 */
		@JsonCreator
		public VarNames(VariableIndex varIndex) {
			setVariableNames(varIndex.getValues());
		}

		/***
		 * @return A list of String objects containing variable names
		 */
		@JsonGetter
		public List<String> getValues() {
			return this.varnames;
		}

		/***
		 * @param varidx valid variable index value
		 * @return Name of variable at index varidx
		 */
		@JsonGetter
		public String getValue(int varidx) {
			return this.varnames.get(varidx);
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Inner class containing variable label map
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("variable labels")
	public class VarLabels {

		@JsonProperty("name")
		private final String name = "Variable Labels";

		/***
		 * Variable labels inner class object
		 * @param varIndex A variable index object
		 */
		@JsonCreator
		public VarLabels(VariableIndex varIndex) {
			setVariableLabels(varIndex.getValues());
		}

		/***
		 * Member variable containing Stata variable labels
		 */
		@JsonProperty("values")
		private Map<String, String> varlabels;

		/***
		 * Sets an object containing variable labels from Stata data set.
		 * Requires the variable index.
		 */
		@JsonSetter
		public void setVariableLabels(List<Integer> vdx) {

			Map<String, String> newVariableLabels = new HashMap<String, String>();

			// Iterate over the variable indices
			for (Integer varid : vdx) {

				// Create a Map object with the variable name as the key and the
				// variable label as the value for the Map object
				newVariableLabels.put(Data.getVarName(varid), Data.getVarLabel
						(varid));

			} // End Loop over variable index

			// Set the member variable varlabels to the value of newVariableLabels
			this.varlabels = newVariableLabels;

		} // End setter method for variable labels

		/***
		 * @return A list of String objects containing variable labels.
		 */
		@JsonGetter
		public Map<String, String> getValues() {
			return this.varlabels;
		}

		/***
		 * @param varnm valid variable name
		 * @return Variable Label
		 */
		@JsonGetter
		public String getValue(String varnm) {
			return this.varlabels.get(varnm);
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Inner class method used to store value label names
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("value label names")
	public class ValLabNames {

		/***
		 * Member variable containing JSON object name
		 */
		@JsonProperty("name")
		private final String name = "Variable Label Names";

		/***
		 * Member variable containing Stata value label names associated with a
		 * given variable
		 */
		@JsonProperty("values")
		public Map<String, String> valueLabelNames;

		/***
		 * Inner class constructor method
		 * @param varIndex A variable index class object
		 */
		@JsonCreator
		public ValLabNames(VariableIndex varIndex) {
			setValueLabelNames(varIndex.getValues());
		}

		/***
		 * Sets an object containing name of value label associated with the
		 * index value
		 * Requires the variable index.
		 */
		@JsonSetter
		public void setValueLabelNames(List<Integer> vdx) {

			// Initialize object used to store the variabel name to value label
			// name mapping object
			Map<String, String> nValueLabelNames = new HashMap<String, String>();

			// Iterate over the variable indices
			for (Integer vindx : vdx) {

				// Get the Variable name
				String tmpVarName = Data.getVarName(vindx);

				// Assign the variable label name to a temporary variable
				String tmpLabel = ValueLabel.getVarValueLabel(vindx);

				// If the method returned null add an empty string to the list
				// object.  Otherwise, add the value returned from the method
				if (tmpLabel != null) {
					if (!tmpLabel.isEmpty()) nValueLabelNames.put(tmpVarName, tmpLabel);
				} // End IF Block for value label names

			} // End Loop

			// Set the variable names member variable
			this.valueLabelNames = nValueLabelNames;

		} // End setter method for variable labels

		/***
		 * @return A list of String objects containing value label names.
		 */
		@JsonGetter
		public Map<String, String> getValues() {
			return this.valueLabelNames;
		}

		/***
		 * @param varnm valid variable name
		 * @return Name of value label associated with a given variable index
		 */
		@JsonGetter
		public String getValue(String varnm) {
			return this.valueLabelNames.get(varnm);
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Inner class containing variable -> value label maps
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("value labels")
	public class ValLabels {

		@JsonProperty("name")
		private final String name = "Value Labels";

		@JsonCreator
		public ValLabels(VariableIndex varIndex) {
			setValueLabels(varIndex.getValues());
		}

		/***
		 * Member variable containing a list of Map objects with the values and
		 * associated labels contained in the Map object
		 */
		@JsonProperty("values")
		public Map<String, Map<Integer, String>> valueLabels;

		/***
		 * Sets an object with the value labels defined for a given variable.
		 * Requires the variable index.
		 */
		@JsonSetter
		public void setValueLabels(List<Integer> vdx) {

			// New Value Label object uses variable name for key and contains the
			// map of integers/strings for the key and value pairs used for
			// value labels
			Map<String, Map<Integer, String>> valLabels = new HashMap<String,
					Map<Integer, String>>();

			// Loop over the variable indices
			for (Integer vindx : vdx) {

				// Create temp object to store the value label set
				Map<Integer, String> labels = new HashMap<>();

				// Get the Variable name
				String tmpVarName = Data.getVarName(vindx);

				// Assign the variable label name to a temporary variable
				String tmpLabel = ValueLabel.getVarValueLabel(vindx);

				// Test whether the object is null/empty
				if (!tmpLabel.isEmpty()) {

					// Add the value labels to the object
					labels.putAll(ValueLabel.getValueLabels(tmpLabel));

					// Add the value labels to the object
					valLabels.put(tmpVarName, labels);

				} // End IF Block for variables with associated value labels

			} // End Loop over value label name index

			// Set the value label index variable
			this.valueLabels = valLabels;

		} // End setter method for value label index

		/***
		 * @return A list of Map objects containing the value/label pairs for
		 * labeled variables or the keyword "skip" to indicate the variable does not
		 * have any value labels associated with it.
		 */
		@JsonGetter
		public Map<String, Map<Integer, String>> getValues() {
			return this.valueLabels;
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Inner class containing string variable indicators
	 */
	@JsonPropertyOrder({ "name", "values"})
	@JsonRootName("variable type string")
	public class VarTypes {

		@JsonProperty("name")
		private final String name = "String Variable Indicator";

		@JsonCreator
		public VarTypes(VariableIndex varIndex) {
			setVariableTypeIndex(varIndex.getValues());
		}

		/***
		 * Member variable containing indicators for whether or not the variable
		 * is of type String
		 */
		@JsonProperty("values")
		public Map<String, Boolean> varTypes;

		/***
		 * Sets an object containing booleans indicating whether the variable
		 * is/isn't a string.
		 * Requires the variable index.
		 */
		@JsonSetter
		public void setVariableTypeIndex(List<Integer> vdx) {

			// Temporary Map variable
			Map<String, Boolean> tmpMap = new HashMap<String, Boolean>();

			// Loop over the variable index
			for (Integer varidx : vdx) {

				// Add elements to the map object
				tmpMap.put(Data.getVarName(varidx), Data.isVarTypeStr(varidx));

			} // End Loop

			// Set the variable type index
			this.varTypes = tmpMap;

		} // End setter method for variable type index

		/***
		 * @return A list of Boolean objects indicating if variable is a string
		 */
		@JsonGetter
		public Map<String, Boolean> getValues() {
			return this.varTypes;
		}

		/***
		 * @param varnm valid variable index value
		 * @return Name of variable at index varidx
		 */
		@JsonGetter
		public Boolean getValue(String varnm) {
			return this.varTypes.get(varnm);
		}

		/***
		 * Method to retrieve the name of the JSON object
		 * @return A string with the name of the JSON object
		 */
		@JsonGetter
		public String getName() {
			return this.name;
		}

	} // End of Inner class object definition

	/***
	 * Class containing the variable index
	 */
	@JsonProperty("variable indices")
	private VariableIndex varindex;

	/***
	 * Class containing the variable index
	 */
	@JsonProperty("number of variables")
	private Nvars nvars;

	/***
	 * Class containing variable names
	 */
	@JsonProperty("variable names")
	private VarNames varnames;

	/***
	 * Class containing variable -> variable label Map
	 */
	@JsonProperty("variable labels")
	private VarLabels varlabels;

	/***
	 * Class containing value label names
	 */
	@JsonProperty("value label names")
	private ValLabNames valueLabelNames;

	/***
	 * Class containing variable -> variable label map
	 */
	@JsonProperty("value labels")
	private ValLabels valueLabels;

	/***
	 * Class containing string variable indicators
	 */
	@JsonProperty("variable type string")
	private VarTypes varTypes;

	@JsonSetter
	public void setVarNames(VariableIndex varindex) {
		this.varnames = new VarNames(this.varindex);
	}

	@JsonSetter
	public void setNvars(VariableIndex varindex) {
		this.nvars = new Nvars(this.varindex);
	}

	@JsonSetter
	public void setVarLabels(VariableIndex varindex) {
		this.varlabels = new VarLabels(this.varindex);
	}

	@JsonSetter
	public void setValLabNames(VariableIndex varindex) {
		this.valueLabelNames = new ValLabNames(this.varindex);
	}

	@JsonSetter
	public void setValLabels(VariableIndex varindex) {
		this.valueLabels = new ValLabels(this.varindex);
	}

	@JsonSetter
	public void setVarTypes(VariableIndex varindex) {
		this.varTypes = new VarTypes(this.varindex);
	}

	@JsonSetter
	public void setVariableIndex() {
		this.varindex = new VariableIndex();
	}

	@JsonGetter
	public List<Integer> getVariableIndex() {
		return this.varindex.getValues();
	}

	@JsonGetter
	public int getVariableIndex(Integer indx) { return this.varindex.getValue(indx); }

	@JsonGetter
	public int getNvars() {
		return this.nvars.getValues();
	}

	@JsonGetter
	public List<String> getVariableNames() {
		return this.varnames.getValues();
	}

	@JsonGetter
	public String getVariableName(int varidx) {
		return this.varnames.getValue(varidx);
	}

	@JsonGetter
	public Map<String, String> getVariableLabels() {
		return this.varlabels.getValues();
	}

	@JsonGetter
	public String getVariableLabel(String varnm) {
		return this.varlabels.getValue(varnm);
	}

	@JsonGetter
	public Map<String, String> getValueLabelNames() {
		return this.valueLabelNames.getValues();
	}

	@JsonGetter
	public String getVarLabelName(String varnm) {
		return this.varlabels.getValue(varnm);
	}

	@JsonGetter
	public Map<String, Map<Integer, String>> getValueLabels() {
		return this.valueLabels.getValues();
	}

	@JsonGetter
	public Map<String, Boolean> getVariableTypes() {
		return this.varTypes.getValues();
	}

	@JsonGetter
	public Boolean getVarType(String varnm) {
		return this.varTypes.getValue(varnm);
	}

	/***
	 * Generic constructor when no varlist is passed
	 */
	@JsonCreator
	Variables() {

		// Set the variable index member variable
		setVariableIndex();

		// Set the number of variables member variable
		setNvars(this.varindex);

		// Set the variable name member variable
		setVarNames(this.varindex);

		// Set the variable label member variable
		setVarLabels(this.varindex);

		// Set the value label name member variable
		setValLabNames(this.varindex);

		// Set the value label value/label pair member variable
		setValLabels(this.varindex);

		// Set the variable is string index member variable
		setVarTypes(this.varindex);

	} // End constructor method


} // End Class definition
