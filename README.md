# JSON I/O Functions for Stata

Java plugin for Stata to convert in-memory data to JSON output.  The plugin 
uses the [Jackson JSON Library](https://github.com/FasterXML/jackson). 

# Deserialization (Ingest)
The examples below are all focused on the ingest side of jsonio.  Currently, 
two modes are supported (one which loads the data in an n x 2 structure of 
key and value pairs and the other which loads the values in separate 
variables in the analog of a row vector).  

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
----------------------------------------------------------------------------------------------------------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
----------------------------------------------------------------------------------------------------------------------------------------------------------------
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
----------------------------------------------------------------------------------------------------------------------------------------------------------------
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
----------------------------------------------------------------------------------------------------------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
----------------------------------------------------------------------------------------------------------------------------------------------------------------
key             str44   %44s                  
value           double  %10.0g                
----------------------------------------------------------------------------------------------------------------------------------------------------------------
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
----------------------------------------------------------------------------------------------------------------------------------------------------------------
              storage   display    value
variable name   type    format     label      variable label
----------------------------------------------------------------------------------------------------------------------------------------------------------------
key             str44   %44s                  
value           strL    %9s                   
----------------------------------------------------------------------------------------------------------------------------------------------------------------
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

It is clear that the key-value mode is a bit less efficient.  The is the
result of trying to handle the type casting automatically for users; it
requires verifying that all of the requested nodes are of the same type.  

# Serialization (output)

All the examples below use the same standard data set that comes with Stata.
```Stata

. sysuse auto, clear
(1978 Automobile Data)

```

## Example 1 
Serializing a single record

```Stata

. jsonio out in 73/74, what(record)
[{
  "mpg" : 25.0,
  "price" : 6850.0,
  "headroom" : 2.0,
  "rep78" : 4.0,
  "length" : 156.0,
  "weight" : 1990.0,
  "displacement" : 97.0,
  "turn" : 36.0,
  "trunk" : 16.0,
  "make" : "VW Scirocco",
  "gear_ratio" : 3.7799999713897705,
  "foreign" : 1.0
}, 
{
  "mpg" : 17.0,
  "price" : 11995.0,
  "headroom" : 2.5,
  "rep78" : 5.0,
  "length" : 193.0,
  "weight" : 3170.0,
  "displacement" : 163.0,
  "turn" : 37.0,
  "trunk" : 14.0,
  "make" : "Volvo 260",
  "gear_ratio" : 2.9800000190734863,
  "foreign" : 1.0
}]

```

## Example 2
Serialize all records that satisfy a given condition

```Stata

. jsonio out if rep78 == 1, what(data)
{
  "source" : "/Applications/Stata/ado/base/a/auto.dta",
  "name" : "DataSet",
  "data" : [ {
    "mpg" : 24.0,
    "price" : 4195.0,
    "headroom" : 2.0,
    "rep78" : 1.0,
    "length" : 180.0,
    "weight" : 2730.0,
    "displacement" : 151.0,
    "turn" : 40.0,
    "trunk" : 10.0,
    "make" : "Olds Starfire",
    "gear_ratio" : 2.7300000190734863,
    "foreign" : 0.0
  }, {
    "mpg" : 18.0,
    "price" : 4934.0,
    "headroom" : 1.5,
    "rep78" : 1.0,
    "length" : 198.0,
    "weight" : 3470.0,
    "displacement" : 231.0,
    "turn" : 42.0,
    "trunk" : 7.0,
    "make" : "Pont. Firebird",
    "gear_ratio" : 3.0799999237060547,
    "foreign" : 0.0
  } ]
}


```

## Example 3
Serialize all data and meta data for records 71 - 74

```Stata

. jsonio out in 71/74, w(all)
{
  "values" : {
    "source" : "/Applications/Stata/ado/base/a/auto.dta",
    "name" : "DataSet",
    "data" : [ {
      "mpg" : 41.0,
      "price" : 5397.0,
      "headroom" : 3.0,
      "rep78" : 5.0,
      "length" : 155.0,
      "weight" : 2040.0,
      "displacement" : 90.0,
      "turn" : 35.0,
      "trunk" : 15.0,
      "make" : "VW Diesel",
      "gear_ratio" : 3.7799999713897705,
      "foreign" : 1.0
    }, {
      "mpg" : 25.0,
      "price" : 4697.0,
      "headroom" : 3.0,
      "rep78" : 4.0,
      "length" : 155.0,
      "weight" : 1930.0,
      "displacement" : 89.0,
      "turn" : 35.0,
      "trunk" : 15.0,
      "make" : "VW Rabbit",
      "gear_ratio" : 3.7799999713897705,
      "foreign" : 1.0
    }, {
      "mpg" : 25.0,
      "price" : 6850.0,
      "headroom" : 2.0,
      "rep78" : 4.0,
      "length" : 156.0,
      "weight" : 1990.0,
      "displacement" : 97.0,
      "turn" : 36.0,
      "trunk" : 16.0,
      "make" : "VW Scirocco",
      "gear_ratio" : 3.7799999713897705,
      "foreign" : 1.0
    }, {
      "mpg" : 17.0,
      "price" : 11995.0,
      "headroom" : 2.5,
      "rep78" : 5.0,
      "length" : 193.0,
      "weight" : 3170.0,
      "displacement" : 163.0,
      "turn" : 37.0,
      "trunk" : 14.0,
      "make" : "Volvo 260",
      "gear_ratio" : 2.9800000190734863,
      "foreign" : 1.0
    } ]
  },
  "firstRecordId" : 71,
  "lastRecordId" : 74,
  "variableIndices" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
  "variableIsString" : {
    "mpg" : false,
    "price" : false,
    "headroom" : false,
    "rep78" : false,
    "length" : false,
    "weight" : false,
    "displacement" : false,
    "turn" : false,
    "trunk" : false,
    "make" : true,
    "gear_ratio" : false,
    "foreign" : false
  },
  "variableNames" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
  "variableLabels" : {
    "mpg" : "Mileage (mpg)",
    "price" : "Price",
    "headroom" : "Headroom (in.)",
    "rep78" : "Repair Record 1978",
    "length" : "Length (in.)",
    "weight" : "Weight (lbs.)",
    "displacement" : "Displacement (cu. in.)",
    "turn" : "Turn Circle (ft.) ",
    "trunk" : "Trunk space (cu. ft.)",
    "make" : "Make and Model",
    "gear_ratio" : "Gear Ratio",
    "foreign" : "Car type"
  },
  "valueLabelNames" : {
    "foreign" : "origin"
  },
  "valueLabels" : {
    "foreign" : {
      "0" : "Domestic",
      "1" : "Foreign"
    }
  },
  "numberOfRecords" : 4,
  "numberOfVariables" : 12
}

```

## Example 4 
Serialize all data and metadata for the dataset in memory

```Stata

. jsonio out, w(all)
{
  "values" : {
    "source" : "/Applications/Stata/ado/base/a/auto.dta",
    "name" : "DataSet",
    "data" : [ {
      "mpg" : 22.0,
      "price" : 4099.0,
      "headroom" : 2.5,
      "rep78" : 3.0,
      "length" : 186.0,
      "weight" : 2930.0,
      "displacement" : 121.0,
      "turn" : 40.0,
      "trunk" : 11.0,
      "make" : "AMC Concord",
      "gear_ratio" : 3.5799999237060547,
      "foreign" : 0.0
    }, {
      "mpg" : 17.0,
      "price" : 4749.0,
      "headroom" : 3.0,
      "rep78" : 3.0,
      "length" : 173.0,
      "weight" : 3350.0,
      "displacement" : 258.0,
      "turn" : 40.0,
      "trunk" : 11.0,
      "make" : "AMC Pacer",
      "gear_ratio" : 2.5299999713897705,
      "foreign" : 0.0
    }, 
    ...
    {
      "mpg" : 17.0,
      "price" : 11995.0,
      "headroom" : 2.5,
      "rep78" : 5.0,
      "length" : 193.0,
      "weight" : 3170.0,
      "displacement" : 163.0,
      "turn" : 37.0,
      "trunk" : 14.0,
      "make" : "Volvo 260",
      "gear_ratio" : 2.9800000190734863,
      "foreign" : 1.0
    } ]
  },
  "firstRecordId" : 1,
  "lastRecordId" : 74,
  "variableIndices" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
  "variableIsString" : {
    "mpg" : false,
    "price" : false,
    "headroom" : false,
    "rep78" : false,
    "length" : false,
    "weight" : false,
    "displacement" : false,
    "turn" : false,
    "trunk" : false,
    "make" : true,
    "gear_ratio" : false,
    "foreign" : false
  },
  "variableNames" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
  "variableLabels" : {
    "mpg" : "Mileage (mpg)",
    "price" : "Price",
    "headroom" : "Headroom (in.)",
    "rep78" : "Repair Record 1978",
    "length" : "Length (in.)",
    "weight" : "Weight (lbs.)",
    "displacement" : "Displacement (cu. in.)",
    "turn" : "Turn Circle (ft.) ",
    "trunk" : "Trunk space (cu. ft.)",
    "make" : "Make and Model",
    "gear_ratio" : "Gear Ratio",
    "foreign" : "Car type"
  },
  "valueLabelNames" : {
    "foreign" : "origin"
  },
  "valueLabels" : {
    "foreign" : {
      "0" : "Domestic",
      "1" : "Foreign"
    }
  },
  "numberOfRecords" : 74,
  "numberOfVariables" : 12
}

```

## Example 5 
Serialize selected variables for observations satisfying a given condition

```Stata

. jsonio out mpg weight price make if rep78 == 1, w(data)
{
  "source" : "/Applications/Stata/ado/base/a/auto.dta",
  "name" : "DataSet",
  "data" : [ {
    "mpg" : 24.0,
    "price" : 4195.0,
    "weight" : 2730.0,
    "make" : "Olds Starfire"
  }, {
    "mpg" : 18.0,
    "price" : 4934.0,
    "weight" : 3470.0,
    "make" : "Pont. Firebird"
  } ]
}

```

## Example 6
Writing output to the harddrive

Serialize all data and metadata for selected variables and observations 
satisfying a given condition and write the output to disk
```Stata

. jsonio out mpg foreign weight price make if rep78 == 1, w(all) filenm(test.json)

```

## Example 7
Working with meta data

Serialize variable names to JSON object that is printed to the Stata console:

```Stata
 
. jsonio out, metaprint(varnames)
[ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ]
```

Serialize variable labels to JSON object that is printed to the Stata console:

```Stata

. jsonio out, metaprint(varlabels)
{
  "mpg" : "Mileage (mpg)",
  "price" : "Price",
  "headroom" : "Headroom (in.)",
  "rep78" : "Repair Record 1978",
  "length" : "Length (in.)",
  "weight" : "Weight (lbs.)",
  "displacement" : "Displacement (cu. in.)",
  "turn" : "Turn Circle (ft.) ",
  "trunk" : "Trunk space (cu. ft.)",
  "make" : "Make and Model",
  "gear_ratio" : "Gear Ratio",
  "foreign" : "Car type"
}

```

Serialize value labels associating the variable name with the mapping

```Stata

. jsonio out, metaprint(vallabs)  
{
  "foreign" : {
    "0" : "Domestic",
    "1" : "Foreign"
  }
}

```

You can also serialize the names of the value labels associated with variables

```Stata

. jsonio out, metaprint(labelnames)
{
  "foreign" : "origin"
}

```
