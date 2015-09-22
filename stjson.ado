********************************************************************************
*                                                                              *
* Description -                                                                *
*   Program to serialize a dataset and return a JSON representation of the     *
*   data.                                                                      *
*                                                                              *
* System Requirements -                                                        *
*   JRE 1.8 or Higher.                                                         *
*                                                                              *
* Output -                                                                     *
*   Either prints a JSON object to the Stata console or writes one to disk.    *
*                                                                              *
********************************************************************************

*! stjson
*! 21sep2015
*! v 0.0.1

// Drop program from memory if it exists
cap prog drop stjson

// Define program as an r-class program
prog def stjson, rclass

	// Set version
	version 14.0

	// Define syntax
	syntax [varlist] [if] [in] [using/] , 				 					 ///
	[ FILEnm(string asis) OBid(real 0) METAprint(string asis) WHAt(string asis) ]

	// Check what options
	if !inlist(proper("`what'"), "Record", "Data") & "`metaprint'" == "" {

		// Print error
		di as err "Illegal option.  Must enter a value for the metaprint " 	 ///
		"argument, or specify Record/Data to the what parameter"

		// Exit from program
		err 198

	} // End IF Block for invalid options

	// Otherwise
	else if inlist(proper("`what'"), "Record", "Data") & "`metaprint'" == "" {

		// Make value proper cased
		loc what = proper("`what'")

	} // End ELSE Block for what argument

	// Create a where macro
	if `"`filenm'"' != "" {

		// Added to define method name
		loc where "ToFile"

	} // End IF Block for partial method name

	// Preserve the data
	preserve

		// Check for using option
		if `"`using'"' != "" {

			// Load the specified file
			qui: use `"`using'"', clear

		} // End IF Block to load data from using

		// Mark the sample
		marksample touse

		// Check for metadata argument
		if inlist(`"`metaprint'"', "varlabels", "varnames", "vallabs", "labelnames") {

			// Call java method to print metadata
			javacall org.paces.Stata.JSON.StataMetaToJSON metaToJSON if `touse'

		} // End IF Block for printing metadata to JSON

		// For non metadata cases
		else {

			// Call java method to print data/record
			javacall org.paces.Stata.JSON.StataJSON print`what'`where' if `touse'

		} // End else block for printing data to JSON

		// Set the local macro that needs to be returned
		ret loc thejson `"`thejson'"'

	// Restore data to that originally in memory
	restore

// End function definition
end



