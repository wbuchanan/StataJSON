# JSON I/O Functions for Stata

Java plugin for Stata to convert in-memory data to JSON output.  The plugin 
uses the [Jackson JSON Library](https://github.com/FasterXML/jackson).  
__Early in development and at best should be considered a pre-alpha release__
.  Testers/collaborators are always appreciated and if you encounter issues 
feel free to submit an issue on Github. 


## Example
With org.paces.Stata-jar-with-dependencies.jar on the ADOPATH:


```
. sysuse auto
(1978 Automobile Data)

. jsonio, w(data)
{
  "name" : "Stata Data Set",
  "stataDataSet" : [ {
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
    "gear_ratio" : 3.5799999237060547
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
    "gear_ratio" : 2.5299999713897705
  }, 
    { ... }
  ]
}

. jsonio if rep78 == 5 & foreign == 1, w(data)
{
  "name" : "Stata Data Set",
  "stataDataSet" : [ {
    "mpg" : 17.0,
    "price" : 9690.0,
    "headroom" : 3.0,
    "rep78" : 5.0,
    "length" : 189.0,
    "weight" : 2830.0,
    "displacement" : 131.0,
    "turn" : 37.0,
    "trunk" : 15.0,
    "make" : "Audi 5000",
    "gear_ratio" : 3.200000047683716
  }, {
    "mpg" : 35.0,
    "price" : 4589.0,
    "headroom" : 2.0,
    "rep78" : 5.0,
    "length" : 165.0,
    "weight" : 2020.0,
    "displacement" : 85.0,
    "turn" : 32.0,
    "trunk" : 8.0,
    "make" : "Datsun 210",
    "gear_ratio" : 3.700000047683716
  }, 
  { ... } 
  ]
}

. jsonio make price if foreign == 1 & rep78 == 2, w(data)
{
  "name" : "Stata Data Set",
  "stataDataSet" : [ ],
  "data" : [ ]
}

. jsonio make price if foreign == 1 & rep78 == 5, w(data)
{
  "name" : "Stata Data Set",
  "stataDataSet" : [ {
    "price" : 9690.0,
    "make" : "Audi 5000"
  }, {
    "price" : 4589.0,
    "make" : "Datsun 210"
  }, 
  { ... } 
  ]
}

. jsonio make price in 70/74, w(data)
{
  "name" : "Stata Data Set",
  "stataDataSet" : [ {
    "price" : 7140.0,
    "make" : "VW Dasher"
  }, {
    "price" : 5397.0,
    "make" : "VW Diesel"
  }, {
    "price" : 4697.0,
    "make" : "VW Rabbit"
  }, {
    "price" : 6850.0,
    "make" : "VW Scirocco"
  }, {
    "price" : 11995.0,
    "make" : "Volvo 260"
  } ],
  "data" : [ {
    "price" : 7140.0,
    "make" : "VW Dasher"
  }, {
    "price" : 5397.0,
    "make" : "VW Diesel"
  }, {
    "price" : 4697.0,
    "make" : "VW Rabbit"
  }, {
    "price" : 6850.0,
    "make" : "VW Scirocco"
  }, {
    "price" : 11995.0,
    "make" : "Volvo 260"
  } ]
}

. jsonio make price, w(record) ob(74)
{
  "price" : 11995.0,
  "make" : "Volvo 260"
}

. jsonio, metaprint(varnames)
[ "make", "price", "mpg", "rep78", "headroom", "trunk", "weight", "length", "turn", "displacement", "gear_ratio" ]{
  "mpg" : 0.0,
  "price" : 0.0,
  "headroom" : 0.0,
  "rep78" : 0.0,
  "length" : 0.0,
  "weight" : 0.0,
  "displacement" : 0.0,
  "turn" : 0.0,
  "trunk" : 0.0,
  "make" : null,
  "gear_ratio" : 0.0
}

. jsonio, metaprint(varlabels)
[ "Make and Model", "Price", "Mileage (mpg)", "Repair Record 1978", "Headroom (in.)", "Trunk space (cu. ft.)", "Weight (lbs.)", "Length (in.)", "Turn Circle (ft.) ", "Displacement (cu. in.)", "Gear Ratio" ]{
  "mpg" : 0.0,
  "price" : 0.0,
  "headroom" : 0.0,
  "rep78" : 0.0,
  "length" : 0.0,
  "weight" : 0.0,
  "displacement" : 0.0,
  "turn" : 0.0,
  "trunk" : 0.0,
  "make" : null,
  "gear_ratio" : 0.0
}
```