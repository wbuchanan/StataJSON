{smcl}
{* *! version 0.0.1  12FEB2016}{...}
{cmd:help ggeocode}
{hline}

{title:Title}

{hi:ggeocode {hline 2}} a Java based Stata interface with the Google geocode
API.  This program requires Java 8 or above and handles all of the parsing of
the data on the JVM (e.g., requires no dependencies on other packages).  This
is being packaged with the {help jsonio} package and is the first
deserializer.  User's are welcome to submit pull requests to add additional
customized deserializers for popular JSON based API's, and more general tools
for JSON deserialization will be added in the future. {p_end}

{title:Syntax}

{p 4 4 4}{cmd:ggeocode} [{opt:varlist}] [{ifin}] , [
{cmdab:ret:urn(}{it:string}{cmd:)}
{cmdab:api:key(}{it:string}{cmd:)}] {break}

{title:Description}

{p 4 4 4}{cmd:ggeocode} is an alternative to the existing geocoding tools
available in the Stata ecosystem.  One of the biggest differences between this
tool and others that interface with Google's geocoding API is the flexibility
it provides to users both in terms of providing the data needed and in
returning results from the API call.{p_end}

{title:Options}
{p 4 4 8}{cmdab:ret:urn} this parameter is used to specify which values to return from the API call and can be any combination of:{break}

{col 10}{hline 85}
{col 10}{hi:Argument} {col 35}{hi: Result}
{col 10}{hline 85}
{col 10}{hi:location}{col 25}Returns the point coordinates
{col 10}{hi:bbox}{col 25}Returns the bounding box around the geographical area
{col 10}{hi:viewport}{col 25}Returns the viewport surround the address
{col 10}{hi:address}{col 25}Returns the formatted address string from the JSON Payload
{col 10}{hi:geotype}{col 25}Returns the geocode type
{col 10}{hi:placeid}{col 25}Returns Google's geocode API place identifier
{col 10}{hi:types}{col 25}Returns the type of address
{col 10}{hline 85}{break}

{p 4 4 8}Each of these options creates a different set of mutually exclusive variables listed below: {p_end}{break}

{col 10}{hline 85}
{col 10}{hi:Return type} {col 35}{hi: Variables Created}
{col 10}{hline 85}
{col 10}{hi:location}{col 25}lat lon
{col 10}{hi:bbox}{col 25}bbox_max_lat, bbox_max_lon, bbox_min_lat, bbox_min_lon
{col 10}{hi:viewport}{col 25}viewport_max_lat, viewport_max_lon, viewport_min_lat, viewport_min_lon
{col 10}{hi:address}{col 25}formatted_address
{col 10}{hi:geotype}{col 25}geo_type
{col 10}{hi:placeid}{col 25}place_id
{col 10}{hi:types}{col 25}google_types
{col 10}{hline 85}{break}

{p 4 4 8}All of the potential string data are returned as
{mansection U 12.4.8strL:strLs}.  This is for two purposes, the first of which
is providing a more generic input type for the return values of the JSON data
(e.g., the length of a strL does not need to be known apriori) and the second
is that it may provide a more efficient means of storing the datum since the
.dta specification suggests that the values of strLs are unqiue with 8-byte
values that serve as pointers for the string value in the observations. {p_end}

{p 4 4 8}Additionally, unlike other tools, ggeocode will automatically handle
missing/null values in a smart way for you.  For example, if batch geocoding,
you may find that some data elements (e.g., viewports, bounding boxes, etc...)
are non-existent for some observations in the raw JSON payloads.  ggeocode
uses the extended missing value of .n (e.g., No Data Found) to handle these
observations when pushing the data back into Stata or using an empty string
in the case of string data.{p_end}

{p 4 4 8}{cmdab:api:key} if you have an API key ggeocode will default to using
the https protocol for the API call and the datum is stored with private access
in the javaclass used to make the class and process the data.{break}


{marker examples}{title:Examples}{break}

{p 4 4 4} Create some data to geocode {p_end}

{p 8 8 12}input int housenum str13 street str10 city str2 state str5 zip{p_end}
{p 8 8 12}4287 "46th Ave N" "Robbinsdale" "MN" "55422"{p_end}
{p 8 8 12}6675 "Old Canton Rd" "Ridgeland" "MS" "39157"{p_end}
{p 8 8 12}12313 "33rd Ave NE" "Seattle" "WA" "98125"{p_end}
{p 8 8 12}310 "Cahir St" "Providence" "RI" "02903"{p_end}
{p 8 8 12}22 "Oaklawn Ave" "Cranston" "RI" "02920"{p_end}
{p 8 8 12}61 "Pine St" "Attleboro" "MA" "02703"{p_end}
{p 8 8 12}10 "Larkspur Rd" "Warwick" "RI" "02886"{p_end}
{p 8 8 12}91 "Fallon Ave" "Providence" "RI" "02908"{p_end}
{p 8 8 12}195 "Arlington Ave" "Providence" "RI" "02906"{p_end}
{p 8 8 12}end{p_end}
{p 8 8 12}{p_end}

{p 4 4 4} Geocode the data and return the point, bounding box, geocode type, and the cleaned/formatted address string {p_end}

{p 8 8 12}ggeocode housenum street city state zip in 1/2, ret(bbox location
geotype address location){p_end}

{p 4 4 4} The default behavior if no return types are specified is to return the point coordinates only. {p_end}

{p 8 8 12}ggeocode housenum street city state zip if state == "RI"{p_end}

{p 4 4 4}Additional examples are available at {browse "http://github.com/wbuchanan/StataJSON"}{p_end}
{break}

{title: Author}{break}
{p 1 1 1} William R. Buchanan, Ph.D. {break}
Data Scientist {break}
{browse "http://mpls.k12.mn.us":Minneapolis Public Schools} {break}
William.Buchanan at mpls [dot] k12 [dot] mn [dot] us
