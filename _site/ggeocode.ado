*! ggeocode
*! v 0.0.0
*! 12feb2016

// Drops the program from memory if it is already loaded
cap prog drop ggeocode

// Defines the program
prog def ggeocode

	// Version required for the program
	version 14.1

	// Specifies the syntax used by the program
	syntax varlist(min = 1) [if] [in] [, RETurn(string asis) APIkey(string asis)]

	// If the user supplies any return types
	if `"`return'"' != "" {

		// Loop over the possible arguments passed to the return parameter
		forv i = 1/`: word count `return'' {

			// Get an individual argument from the parameter
			loc arg `: word `i' of `return''

			// Validate the argument
			if !inlist(`"`arg'"', "location", "bbox", "viewport", 			 ///
			"address", "geotype", "placeid", "types") {

				// Replace invalid argument with nothing
				loc return `: subinstr loc return "`arg'" "", all'

			} // End IF Block to validate return type arguments

		} // End Loop over return arguments

	} // End IF Block for case where the user supplies return type parameters

	// Call the Java plugin and pass it the appropriate parameters
	javacall org.paces.Stata.Google.Google geocode `varlist' `if' `in',      ///
	args("`return'")

// End program definition
end


