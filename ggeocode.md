---
layout: page
title: ggeocode
permalink: /ggeocode/
---

# Ingesting Google Geocode API Data
As a first step in building out the input capabilities for the jsonio package 
I thought it might be useful if I focused on a well used API that uses JSON for 
its payload delivery.  The `ggeocode` command handles your needs for 
parsing/cleaning the strings you pass to it for subsequent use in API calls 
to the Google Geocoding API, parses the payload, provides options allowing 
you to specify what elements from the payload you wish to return from the API 
call, and also handles null elements in an intelligent Stata-centered format 
(e.g., missing elements that are numeric will be returned with the extended 
missing value of `.n` for No Data Found).

Some examples of how the program can be used are listed below.

# Get some data that you can use for geocoding

```
// Creates a small data set of addresses that you can use for geocoding
input int housenum str13 street str11 city str2 state str5 zip
4287 "46th Ave N" "Robbinsdale" "MN" "55422"
6675 "Old Canton Rd" "Ridgeland" "MS" "39157"
12313 "33rd Ave NE" "Seattle" "WA" "98125"
310 "Cahir St" "Providence" "RI" "02903"
22 "Oaklawn Ave" "Cranston" "RI" "02920"
61 "Pine St" "Attleboro" "MA" "02703"
10 "Larkspur Rd" "Warwick" "RI" "02886"
91 "Fallon Ave" "Providence" "RI" "02908"
195 "Arlington Ave" "Providence" "RI" "02906"
end
```
 
## Example 1. Default behavior
When no arguments are passed to the return parameter, the program will only 
return the latitude and longitude coordinates.

```
. ggeocode housenum street city state zip

. li

     +---------------------------------------------------------------------------------+
     | housenum          street          city   state     zip         lat          lon |
     |---------------------------------------------------------------------------------|
  1. |     4287      46th Ave N   Robbinsdale      MN   55422   45.039025   -93.335434 |
  2. |     6675   Old Canton Rd     Ridgeland      MS   39157   32.405078   -90.108584 |
  3. |    12313     33rd Ave NE       Seattle      WA   98125   47.717745   -122.29312 |
  4. |      310        Cahir St    Providence      RI   02903   41.817458   -71.418378 |
  5. |       22     Oaklawn Ave      Cranston      RI   02920   41.774516   -71.467927 |
     |---------------------------------------------------------------------------------|
  6. |       61         Pine St     Attleboro      MA   02703   41.941816   -71.281024 |
  7. |       10     Larkspur Rd       Warwick      RI   02886    41.72289   -71.460404 |
  8. |       91      Fallon Ave    Providence      RI   02908   41.834768   -71.443153 |
  9. |      195   Arlington Ave    Providence      RI   02906   41.835855   -71.395398 |
     +---------------------------------------------------------------------------------+

```


## Example 2. Passing a concatenated string
If you wanted, you can also manually format the address string that will be 
used when constructing the URL for the API call.  Additionally, you can request 
several different types of values be returned.  


```
. qui: g num = strofreal(housenum)
. qui: replace street = subinstr(street, " ", "+", .)
. qui: egen addy_string = concat(street city state zip), p(",+")
. qui: replace addy_string = num + "+" + addy_string
. ggeocode addy_string, ret(viewport address bbox)
. li lat lon formatted_address bbox* viewport*

     +---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
     |       lat          lon                              formatted_address   bbox_ma~t   bbox_max~n   bbox_mi~t   bbox_min~n   vie~x_lat   view~x_lon   vie~n_lat   view~n_lon |
     |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  1. | 45.039025   -93.335434                           Robbinsdale, MN, USA    45.04285    -93.31839   45.006042   -93.356257    45.04285    -93.31839   45.006042   -93.356257 |
  2. | 32.405078   -90.108584   6675 Old Canton Rd, Ridgeland, MS 39157, USA          .n           .n          .n           .n   32.406427   -90.107235   32.403729   -90.109933 |
  3. | 47.717745   -122.29312                         Seattle, WA 98125, USA   47.736788   -122.25414   47.697345   -122.33142   47.736788   -122.25414   47.697345   -122.33142 |
  4. | 41.817458   -71.418378        310 Cahir St, Providence, RI 02903, USA   41.817465   -71.418362   41.817458   -71.418378    41.81881   -71.417021   41.816112   -71.419719 |
  5. | 41.774516   -71.467927        22 Oaklawn Ave, Cranston, RI 02920, USA          .n           .n          .n           .n   41.775865   -71.466578   41.773167   -71.469276 |
     |---------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
  6. | 41.941816   -71.281024           61 Pine St, Attleboro, MA 02703, USA   41.941816   -71.281005   41.941813   -71.281024   41.943163   -71.279666   41.940466   -71.282364 |
  7. |  41.72289   -71.460404         10 Larkspur Rd, Warwick, RI 02886, USA          .n           .n          .n           .n   41.724239   -71.459055   41.721541   -71.461753 |
  8. | 41.834768   -71.443153       91 Fallon Ave, Providence, RI 02908, USA          .n           .n          .n           .n   41.836117   -71.441804   41.833419   -71.444502 |
  9. | 41.835855   -71.395398   195 Arlington Ave, Providence, RI 02906, USA          .n           .n          .n           .n   41.837204   -71.394049   41.834506   -71.396747 |
     +---------------------------------------------------------------------------------------------------------------------------------------------------------------------------+

```

For additional information, please see the help file.

