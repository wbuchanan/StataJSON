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

_This method is currently exhibiting unanticipated behavior.  The correct data is returned in the pay load, but the call is also returning multiple internal objects that may not be necessary/useful for most end users._

```Stata
. jsonio out, what(record) obid(74)
{
  "metaob" : {
    "stataobs" : {
      "iterator" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 5
> 9, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74 ],
      "nobs" : 74,
      "sobs" : 1,
      "observationIndex" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 5
> 7, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74 ],
      "eobs" : 74
    },
    "statavars" : {
      "nvars" : 12,
      "valueLabelNames" : {
        "foreign" : "origin"
      },
      "valueLabels" : {
        "foreign" : {
          "0" : "Domestic",
          "1" : "Foreign"
        }
      },
      "variableIndex" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
      "variableTypes" : {
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
      "varIndexObject" : {
        "name" : "variable index",
        "values" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
        "varIndexValues" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ]
      },
      "nvarsObject" : {
        "name" : "number of variabels",
        "values" : 12
      },
      "varNamesObject" : {
        "name" : "Variable Names",
        "iterator" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
        "values" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ]
      },
      "varLabelsObject" : {
        "name" : "Variable Labels",
        "iterator" : [ "mpg", "price", "headroom", "rep78", "length", "weight", "displacement", "turn", "trunk", "make", "gear_ratio", "foreign" ],
        "values" : {
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
      },
      "valLabNamesObject" : {
        "name" : "Variable Label Names",
        "iterator" : [ "foreign" ],
        "values" : {
          "foreign" : "origin"
        }
      },
      "valLabelsObject" : {
        "name" : "Value Labels",
        "iterator" : [ "foreign" ],
        "values" : {
          "foreign" : {
            "0" : "Domestic",
            "1" : "Foreign"
          }
        }
      },
      "varTypesObject" : {
        "name" : "String Variable Indicator",
        "iterator" : [ "mpg", "price", "headroom", "rep78", "length", "weight", "displacement", "turn", "trunk", "make", "gear_ratio", "foreign" ],
        "values" : {
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
        }
      },
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
      "variableNames" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ]
    },
    "varindex" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 ],
    "obsindex" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59,
>  60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74 ],
    "obs13" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
> , 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74 ],
    "obs14" : [ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60
> , 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74 ],
    "varNames" : [ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio", "foreign" ],
    "areStrings" : {
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
    }
  },
  "obid" : 74,
  "name" : "DataRecord",
  "data" : {
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
  } ],
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
  "values" : [ {
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
  ...
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

