---
layout: page
title: about
permalink: /about/
---

# JSON I/O Functions for Stata

Java plugin for Stata to convert in-memory data to JSON output.  The plugin 
uses the [Jackson JSON Library](https://github.com/FasterXML/jackson). This 
is still early in development, and could use help from others to test/refine 
the programs.


# Examples
This requires the source to be compiled to a JAR.  The JAR must then be placed 
on the `ADOPATH`. All examples use the auto.dta dataset that is automatically
 installed by Stata.


```

. sysuse auto, clear
(1978 Automobile Data)

```

## Example of serializing a single record

```

. jsonio, what(record) obid(74)
{
  "source" : "/Applications/Stata/ado/base/a/auto.dta",
  "_id" : 74,
  "name" : "record",
  "values" : {
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
}


```

## Serialize all records that satisfy a given condition

```

. jsonio if rep78 == 1, what(data)
{
  "source" : "/Applications/Stata/ado/base/a/auto.dta",
  "name" : "StataJSON",
  "values" : [ {
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

## Serialize all data and meta data for records 71 - 74

```

. jsonio in 71/74, w(all)
{
  "data" : {
    "source" : "/Applications/Stata/ado/base/a/auto.dta",
    "name" : "StataJSON",
    "values" : [ {
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
  "first record id" : 71,
  "last record id" : 74,
  "number of records" : 4,
  "variable indices" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
  "number of variables" : 12,
  "variable type string" : {
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
  "variable names" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacem
> ent", "gear_ratio", "foreign" ],
  "variable labels" : {
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
  "value label names" : {
    "foreign" : "origin"
  },
  "value labels" : {
    "foreign" : {
      "0" : "Domestic",
      "1" : "Foreign"
    }
  }
}


```

## Serialize selected variables for observations satisfying a given condition

```

. jsonio mpg weight price make if rep78 == 1, w(data)
{
  "source" : "/Applications/Stata/ado/base/a/auto.dta",
  "name" : "StataJSON",
  "values" : [ {
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

## Writing output to the harddrive

Serialize all data and metadata for selected variables and observations 
satisfying a given condition and [write the output to disk](./test.json)

```

. jsonio mpg foreign weight price make if rep78 == 1, w(all) filenm(test.json)

```

## Working with meta data
Serialize variable names to JSON object that is printed to the Stata console:

```
 
. jsonio, metaprint(varnames)
[ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ]
```

Serialize variable labels to JSON object that is printed to the Stata console:

```

. jsonio, metaprint(varlabels)

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

Serialize value labels from the data set in memory:

```

. jsonio, metaprint(vallabs)
{
  "foreign" : {
    "0" : "Domestic",
    "1" : "Foreign"
  }
}

```

Serialize value label names from the dataset in memory:

```

. jsonio, metaprint(labelnames)
{
  "foreign" : "origin"
}

```



