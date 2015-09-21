package org.paces.Stata;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 * <h2>Stata Data Interface Class Object</h2>
 * <p>Interface used to access and construct representations of Stata data
 * set as POJOs.  </p>
 */
public interface StataData {

	/***
	 * 	Method to build the Stata data object
	 */
	void setData();

	/***
	 * Method to retrieve
	 * @return A POJO containing a Stata data representation
	 */
	Object getData();

} // End interface declaration
