---
layout: page
title: output
permalink: /output/
---


All the examples below use the same standard data set that comes with Stata.
```Stata

. sysuse auto, clear
(1978 Automobile Data)

```

## Example 1 
Serializing a single record

```Stata

. jsonio, what(record) obid(74)
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
}

```

## Example 2
Serialize all records that satisfy a given condition

```Stata

. jsonio if rep78 == 1, what(data)
{
  "fileName" : "Stata Data Set",
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

. jsonio in 71/74, w(all)
{
  "Value Labels" : [ {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : "Domestic",
    "1" : "Foreign"
  } ],
  "Starting Observation Number" : 71,
  "Data Set" : [ {
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
  } ],
  "Variable Names" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
  "Is Variable String" : [ true, false, false, false, false, false, false, false, false, false, false, false ],
  "Number of Observations" : 4,
  "Ending Observation Number" : 74,
  "Value Label Names" : [ "", "", "", "", "", "", "", "", "", "", "", "origin" ],
  "Variable Labels" : [ "Make and Model", "Price", "Mileage (mpg)", "Repair Record 1978", "Headroom (in.)", "Trunk space (cu. ft.)", "Weight (lbs.)", "Length (in.)", "Turn Circle (ft.) ", "Displacement (cu. in.)", "Gear Ratio", "Car type" ]
}

```

## Example 4 
Serialize all data and metadata for the dataset in memory

```Stata

. jsonio, w(all)
{
  "Value Labels" : [ {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : ""
  }, {
    "0" : "Domestic",
    "1" : "Foreign"
  } ],
  "Starting Observation Number" : 1,
  "Data Set" : [ {
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
  }, {...}, {
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
  } ],
  "Variable Names" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
  "Is Variable String" : [ true, false, false, false, false, false, false, false, false, false, false, false ],
  "Number of Observations" : 74,
  "Ending Observation Number" : 74,
  "Value Label Names" : [ "", "", "", "", "", "", "", "", "", "", "", "origin" ],
  "Variable Labels" : [ "Make and Model", "Price", "Mileage (mpg)", "Repair Record 1978", "Headroom (in.)", "Trunk space (cu. ft.)", "Weight (lbs.)", "Length (in.)", "Turn Circle (ft.) ", "Displacement (cu. in.)", "Gear Ratio", "Car type" ]
}

```

## Example 5 
Serialize selected variables for observations satisfying a given condition

```Stata

. jsonio mpg weight price make if rep78 == 1, w(data)
{
  "fileName" : "Stata Data Set",
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

. jsonio mpg foreign weight price make if rep78 == 1, w(all) filenm(test.json)

```

## Example 7
Working with meta data

Serialize variable names to JSON object that is printed to the Stata console:

```Stata
 
. jsonio, metaprint(varnames)
[ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ]
```

Serialize variable labels to JSON object that is printed to the Stata console:

```Stata

. jsonio, metaprint(varlabels)

[ "Make and Model", "Price", "Mileage (mpg)", "Repair Record 1978", "Headroom (in.)", "Trunk space (cu. ft.)", "Weight (lbs.)", "Length (in.)", "Turn Circle (ft.) ", "Displacement (cu. in.)", "Gear Ratio", "Car type" ]
```

