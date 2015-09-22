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
* Lines -                                                                      *
* 	121	                                                                       *
*                                                                              *
********************************************************************************

*! jsonio
*! 22sep2015
*! v 0.0.1

// Drop program from memory if it exists
cap prog drop jsonio

// Define program as an r-class program
prog def jsonio, rclass

	// Set version
	version 14.0

	// Define syntax
	syntax [varlist] [if] [in] [using/] , 				 					 ///
	[ FILEnm(string asis) OBid(real 0) METAprint(string asis) What(string asis) ]

	// Check for aguments defining what to convert
	if inlist(proper("`what'"), "Record", "Data") & "`metaprint'" == "" {

		// Make value proper cased
		loc what = proper("`what'")

	} // End IF Block for what argument

	// If All arguments are missing
	else if "`what'" == "" & "`metaprint'" == "" {
		
		// Print message to Stata console
		di as res "No arguments provided to define what to serialize." _n ///   
		"Will serialize entire dataset by default."
		
		// Set the what macro to Data
		loc what Data
		
	} // End ELSE IF Block for options to define what to serialize

	// Preserve the data
	preserve

		// Check for using option
		if `"`using'"' != "" {

			// Load the specified file
			qui: use `"`using'"', clear

		} // End IF Block to load data from using

		// Check for metadata argument
		if inlist(`"`metaprint'"', "varlabels", "varnames", "vallabs", 		 ///   
		"labelnames") {

			// Call java method to print metadata
			javacall org.paces.Stata.JSON.StataMetaToJSON metaToJSON 		 ///
			`varlist' `if' `in'

		} // End IF Block for printing metadata to JSON

		// For non metadata cases
		if proper("`what'") == "Data" & "`filenm'" != "" {

			// Call java method to write JSON object to disk
			javacall org.paces.Stata.JSON.StataJSON printDataToFile 		 ///
			`varlist' `if' `in'

		} // End else block for printing data to JSON

		// Call to print dataset to Stata console
		else if proper("`what'") == "Data" & "`filenm'" == "" {

			// Call java method to write JSON object to the Stata console
			javacall org.paces.Stata.JSON.StataJSON printData `varlist' `if' ///   
			`in'

		} // End ELSEIF Block to print dataset to Stata console

		// Call to write individual record to file
		else if proper("`what'") == "Record" & "`filenm'" != "" {

			// Call java method to write individual record to disk
			javacall org.paces.Stata.JSON.StataJSON printRecordToFile 		 ///
			`varlist' `if' `in'

		} // End ELSEIF Block for writing individual record to disk

		// Option to print individual record to the screen
		else {

			// Call java method to print record to the Stata console
			javacall org.paces.Stata.JSON.StataJSON printRecord `varlist' 	 ///   
			`if' `in'

		} // End ELSE Block to print JSON record to Stata console

		// Set the local macro that needs to be returned
		ret loc thejson `"`thejson'"'

	// Restore data to that originally in memory
	restore

	// Set all of the local macros to null
	loc filenm
	loc what
	loc obid
	loc metaprint

// End function definition
end

