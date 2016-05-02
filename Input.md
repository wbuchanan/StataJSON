---
layout: page
title: input
permalink: /input/
---

`jsonio` now features several options for loading JSON data into Stata.  Part of this process involves a depth-first traversal of the JSON tree which is used to flatten the JSON data into a two dimensional key-value pair object.  This process also renames the nodes in such a way that each terminal node (e.g., a node containing no children or a scalar value only) can be uniquely identified based on its ancestry/lineage.  Doing so allows users to use simple regular expressions to query collections of nodes from the data.  The examples below attempt to show these features, as well as illustrate the difference between the key-value and row-value modes that define how the data will be loaded into Stata.

# Examples
The examples below are all focused on the ingest side of jsonio.  Currently, two modes are supported (one which loads the data in an n x 2 structure of key and value pairs and the other which loads the values in separate variables in the analog of a row vector).  

## Example 1
Reading JSON data into a single row vector

```Stata
. // Start the profiler
. profiler on

.
. // Shows example of reading a JSON file from Disk and loading it into individual variables in the dataset
. // The "(legs_[0-9]/((start)|(end))_location/((lat)|(lng)))" argument passed to the elements parameter
. // is used to query only the latitude or longitude values for start or end locations defined for 
. // individual legs of the trip
. jsonio rv, file("~/Desktop/waypointsResponse.json") nourl ob(1) elem("(legs_[0-9]/((start)|(end))_location/((lat)|(lng)))")

. 
. // Describe the parsed/returned data
. desc 

Contains data
  obs:             1                          
 vars:            12                          
 size:            96                          
--------------------------------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
--------------------------------------------------------------------------------------
jsonvar1        double  %10.0g                /routes_1/legs_1/end_location/lat
jsonvar2        double  %10.0g                /routes_1/legs_1/end_location/lng
jsonvar3        double  %10.0g                /routes_1/legs_1/start_location/lat
jsonvar4        double  %10.0g                /routes_1/legs_1/start_location/lng
jsonvar5        double  %10.0g                /routes_1/legs_2/end_location/lat
jsonvar6        double  %10.0g                /routes_1/legs_2/end_location/lng
jsonvar7        double  %10.0g                /routes_1/legs_2/start_location/lat
jsonvar8        double  %10.0g                /routes_1/legs_2/start_location/lng
jsonvar9        double  %10.0g                /routes_1/legs_3/end_location/lat
jsonvar10       double  %10.0g                /routes_1/legs_3/end_location/lng
jsonvar11       double  %10.0g                /routes_1/legs_3/start_location/lat
jsonvar12       double  %10.0g                /routes_1/legs_3/start_location/lng
--------------------------------------------------------------------------------------
Sorted by: 
     Note: Dataset has changed since last saved.

. 
. // Show the returned elements
. li, compress sep(0)

     +-----------------------------------------------------------------------------------------------------------------------------------------------------+
     |  jsonvar1     jsonvar2    jsonvar3     jsonvar4    jsonvar5     jsonvar6    jsonvar7     jsonvar8    jsonvar9    jsonvar10   jsonvar11    jsonvar12 |
     |-----------------------------------------------------------------------------------------------------------------------------------------------------|
  1. | 42.378175   -71.060226   42.359824   -71.059812   42.442609   -71.229336   42.378175   -71.060226   42.460387   -71.348931   42.442609   -71.229336 |
     +-----------------------------------------------------------------------------------------------------------------------------------------------------+

. 
. // Clear data from memory
. clear
```

## Example 2
Same as above, but uses the key-value mode instead.

```Stata
. 
. // Load the same data into Stata in a key-value pair structure
. jsonio kv, file("~/Desktop/waypointsResponse.json") nourl elem("(legs_[0-9]/((start)|(end))_location/((lat)|(lng)))")

. 
. // Describe the data set in memory
. desc 

Contains data
  obs:            12                          
 vars:             2                          
 size:           624                          
------------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
------------------------------------------------------------------
key             str44   %44s                  
value           double  %10.0g                
------------------------------------------------------------------
Sorted by: 
     Note: Dataset has changed since last saved.

. 
. // Show the data
. li, fast sep(0)

     +-----------------------------------------------------------+
     |                                          key        value |
     |-----------------------------------------------------------|
  1. |            /routes_1/legs_1/end_location/lat    42.378175 |
  2. |            /routes_1/legs_1/end_location/lng   -71.060226 |
  3. |          /routes_1/legs_1/start_location/lat    42.359824 |
  4. |          /routes_1/legs_1/start_location/lng   -71.059812 |
  5. |            /routes_1/legs_2/end_location/lat    42.442609 |
  6. |            /routes_1/legs_2/end_location/lng   -71.229336 |
  7. |          /routes_1/legs_2/start_location/lat    42.378175 |
  8. |          /routes_1/legs_2/start_location/lng   -71.060226 |
  9. |            /routes_1/legs_3/end_location/lat    42.460387 |
 10. |            /routes_1/legs_3/end_location/lng   -71.348931 |
 11. |          /routes_1/legs_3/start_location/lat    42.442609 |
 12. |          /routes_1/legs_3/start_location/lng   -71.229336 |
     +-----------------------------------------------------------+

. 
. // Clear data from memory
. clear
```

## Example 3
Handling of terminal nodes stored in Array objects.

```Stata
. 
. // This shows how arrays containing terminal nodes are handled
. jsonio kv, file("~/Desktop/waypointsResponse.json") nourl elem("types") 

. 
. // Show the returned/parsed data elements
. li

     +------------------------------------------------------+
     |                                   key          value |
     |------------------------------------------------------|
  1. | /geocoded_waypoints_1/types/element_1       locality |
  2. | /geocoded_waypoints_1/types/element_2      political |
  3. | /geocoded_waypoints_2/types/element_1   neighborhood |
  4. | /geocoded_waypoints_2/types/element_2      political |
  5. | /geocoded_waypoints_3/types/element_1       locality |
     |------------------------------------------------------|
  6. | /geocoded_waypoints_3/types/element_2      political |
  7. | /geocoded_waypoints_4/types/element_1       locality |
  8. | /geocoded_waypoints_4/types/element_2      political |
     +------------------------------------------------------+

. 
. // Clear data from memory
. clear
```

## Example 4
Reading JSON directly from webservices/REST APIs

```Stata
. 
. // Read data directly from a URL
. jsonio kv, file("http://maps.googleapis.com/maps/api/directions/json?origin=1250+W+Broadway+Ave,+Minneapolis,+MN&destination=4+Yawkey+Way,+Boston,+MA&waypoint
> s=optimize:true|Chicago,IL|Lexington,KY|Providence,RI") 

. 
. // Describe the data set in memory
. desc

Contains data
  obs:         1,327                          
 vars:             2                          
 size:       368,762                          
-----------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
-----------------------------------------------------------------
key             str44   %44s                  
value           strL    %9s                   
-----------------------------------------------------------------
Sorted by: 
     Note: Dataset has changed since last saved.

. 
. // Show the number of elements retrieved from the request
. count
  1,327

. 
. // List some of the entries from the returned results
. li in 20/30, fast sep(0)

     +----------------------------------------------------------+
     |                                          key       value |
     |----------------------------------------------------------|
 20. |               /routes_1/bounds/northeast/lng   -71.030.. |
 21. |               /routes_1/bounds/southwest/lat   38.0067.. |
 22. |               /routes_1/bounds/southwest/lng   -93.296.. |
 23. |                         /routes_1/copyrights   Map dat.. |
 24. |               /routes_1/legs_1/distance/text      413 mi |
 25. |              /routes_1/legs_1/distance/value      664140 |
 26. |               /routes_1/legs_1/duration/text   6 hours.. |
 27. |              /routes_1/legs_1/duration/value       22846 |
 28. |                 /routes_1/legs_1/end_address   Chicago.. |
 29. |            /routes_1/legs_1/end_location/lat   41.8781.. |
 30. |            /routes_1/legs_1/end_location/lng   -87.629.. |
     +----------------------------------------------------------+
```

In addition to the flexibility provided with these features, the programs is
also extremely efficient at the parsing/structuring of the data elements.
Below is the print out from the Stata profiler

```Stata
. 
. // Report the profiling of the commands
. profiler report
clear
     4    0.021  clear
label
     4    0.000  label
jsonio
     4    0.000  jsonio
     1    0.002  rowval
     3    0.217  keyval
          0.219  Total
desc
     3    0.000  desc
describe
     3    0.000  describe
Overall total count =     22
Overall total time  =      0.240 (sec)
```

It is clear that the key-value mode is a bit less efficient.  The is the result of trying to handle the type casting automatically for users; it requires verifying that all of the requested nodes are of the same type.  
