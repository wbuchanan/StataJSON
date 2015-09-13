package org.paces.StataMetaData;

import java.util.List;
import java.util.Map;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Variable MetaData Interface</h2>
 * <p>Interface class object for accessing Stata variable metadata.</p>
 */
interface StataVars {

	/***
	 * Sets an object containing the indices for variables accessed from Stata
	 */
	void setVariableIndex();

	/***
	 * Sets an object containing variable names from Stata data set.
	 * Requires the variable index.
	 */
	void setVariableNames();

	/***
	 * Sets an object containing variable labels from Stata data set.
	 * Requires the variable index.
	 */
	void setVariableLabels();

	/***
	 * Sets an object containing name of value label associated with the
	 * index value
	 * Requires the variable index.
	 */
	void setValueLabelNames();

	/***
	 * Sets an object with the value labels defined for a given variable.
	 * Requires the variable index.
	 */
	void setValueLabels();

	/***
	 * Sets an object containing booleans indicating whether the variable
	 * is/isn't a string.
	 * Requires the variable index.
	 */
	void setVariableTypeIndex();

	/***
	 * Accessor method for variable index variable
	 * @return A list of Integer objects containing variable indices
	 */
	List<Integer> getVariableIndex();

	/***
	 *
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing variable names
	 */
	List<String> getVariableNames(List<Integer> varidx);

	/***
	 *
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing variable labels.
	 */
	List<String> getVariableLabels(List<Integer> varidx);

	/***
	 *
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of String objects containing value label names.
	 */
	List<String> getValueLabelNames(List<Integer> varidx);

	/***
	 *
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of Map objects containing the value/label pairs for
	 * labeled variables or the keyword "skip" to indicate the variable does not
	 * have any value labels associated with it.
	 */
	List<Map<Integer, String>> getValueLabels(List<Integer> varidx);

	/***
	 *
	 * @param varidx The Stata Dataset variable index member variable
	 * @return A list of Boolean objects indicating if variable is a string
	 */
	List<Boolean> getVariableTypes(List<Integer> varidx);

} // End of Interface definition
