{smcl}
{* *! version 0.0.7  27APR2017}{...}

{hline}
{p 2 2 8}I/O operations with JSON Data{p_end}
{hline}

{marker jsoniotitle}{title:help for jsonio}

{p 4 4 8}{hi:jsonio {hline 2}} A Stata JSON Serializer/Deserializer built on
the {browse "http://github.com/FasterXML/jackson": Jackson} Java library.  For the most up to date version of this program, visit
{browse "http://github.com/wbuchanan/StataJSON":Stata JSON on Github}{break}

{title:Description}

{p 4 4 4}{cmd:jsonio} provides a simple user interface to serialize/deserialize JSON data.
When serializing the data in memory, jsonio preserves as much of the meta data as possible
(e.g., variable/value labels, etc...).  When deserializing JSON, users currently have
two options available to load the data using either the {help jsonio##kv:key-value} interface
or flattening the payload into a single row vector with the {help jsonio##rv:row-value}
interface. Additionally, users are able to use simple string expressions to return a subset
of the data in both of these interfaces. {p_end}

{title:Syntax}

{p 4 4 4}{cmd:jsonio} {it:{opt kv|rv|out}}
[{opt varlist}] {ifin} , [ {cmdab:elem:ents(}{it:string}{cmd:)}
{cmdab:no:URL} {cmdab:file:nm(}{it:string}{cmd:)}
{cmdab:ob:id(}{it:real}{cmd:)} {cmdab:meta:print(}{it:string}{cmd:)}
{cmdab:w:hat(}{it:string}{cmd:)} {cmdab:stub:name(}{it:string}{cmd:)}] {break}


{title:Subcommands}
{marker kv}{p 4 4 8}{cmd:kv} is a subcommand for {help jsonio} that is used to
load data from the JSON file into the active dataset as a key/value pair.
This method defines two variables in the dataset - key and value.  If the type of each
datum is not constant across all elements the values will be loaded as a string.
However, if the values are of a single type, they will be loaded into the dataset with
the appropriate type casting applied.  {p_end}

{marker rv}{p 4 4 8}{cmd:rv} is a subcommand for {help jsonio} that is used to load
the JSON data into the data set in memory as a single row vector.  Unlike the
{help jsonio##kv:key/value} interface, all values are automatically cast to the
correct type for you.  While the key-value interface stores the generation string in
a single variable (key), this is not possible with this interface.  Instead, each
of the datum is loaded as a variable with the name specified by {hi:stubname}
 and an ID/Iterator value appended as a suffix.  Additionally, the generation string
is stored as the variable label for the variable.  Unless a user passes
an observation ID value, this method will write the data to the first row only.  The
benefit, however, is that it automatically joins the data to the appropriate observation
if you are looping over observations to make calls to an API or to read a sequence of JSON
data from files. {p_end}

{marker out}{p 4 4 8}{cmd:out} is a subcommand used to serialize the data set currently
in memory to a JSON file.  {p_end}

{title:Options}
{p 4 4 8}{cmdab:elem:ents} this option provides a method that can be used to query
specific elements from the JSON data.  In the process of flattening the JSON data,
the names of elements are modified to include their full lineage (e.g., show all of
the parent nodes up to the root).  The naming convention used for this purpose is
similar to *nix-based file systems where the "/" character by itself indicates the
root level of the data.  When multiple objects are included in the same JSON data,
the branches/nodes can be identified by appending '_#' to the name, where the number
indicates the order in which the node appeared in the data.{p_end}

{p 8 8 12}{hi:terminal nodes} typically will not include any modification, since the
generation string up to that point will be capable of uniquely identifying the
data element.  However, when the terminal node is an element from an Array object,
you can identify distinct elements using the name 'element_#'.  This only applies
to cases where the terminal nodes are elements from an array. {p_end}

{p 8 8 12}For example, the Google Directions API may include specific way points
in the JSON Payload like: {p_end}

{p 14 12 16}{c -(}{p_end}
{p 18 12 16}"geocoded_waypoints" : [{p_end}
{p 22 12 16}{c -(}{p_end}
{p 28 12 16}"geocoder_status" : "OK",{p_end}
{p 28 12 16}"place_id" : "ChIJGzE9DS1l44kRoOhiASS_fHg",{p_end}
{p 28 12 16}"types" : [ "locality", "political" ]{p_end}
{p 22 12 16}{c )-},{p_end}
{p 22 12 16}{c -(}{p_end}
{p 28 12 16}"geocoder_status" : "OK",{p_end}
{p 28 12 16}"place_id" : "ChIJGwVKWe5w44kRcr4b9E25-Go",{p_end}
{p 28 12 16}"types" : [ "neighborhood", "political" ]{p_end}
{p 22 12 16}{c )-},{p_end}
{p 22 12 16}{c -(}{p_end}
{p 28 12 16}"geocoder_status" : "OK",{p_end}
{p 28 12 16}"place_id" : "ChIJy1hS39qd44kRzRM2FsiFNoU",{p_end}
{p 28 12 16}{hi:"types" : [ "locality", "political" ]}{p_end}
{p 22 12 16}{c )-},{p_end}
{p 22 12 16}{c -(}{p_end}
{p 28 12 16}"geocoder_status" : "OK",{p_end}
{p 28 12 16}"place_id" : "ChIJ9SKkJkCa44kRkKR4K5p3zlg",{p_end}
{p 28 12 16}"types" : [ "locality", "political" ]{p_end}
{p 22 12 16}{c )-}{p_end}
{p 18 12 16}]{p_end}
{p 14 12 16}{c )-}{p_end}

{p 8 8 12}To retrieve the first of the two highlighted elements from the example
above, you would pass "/geocoder_status_3/types/element_1" and the value that
would be returned would be "locality". {p_end}

{p 4 4 8}{cmd:nourl} because the program can handle data from files or
API calls/requests, you need to specify the source type of the data.  If the
option is not specified, the program will assume the data are to be retrieved
 from a request to the URL passed to it.  If the data are stored on a file
 system, you can use this option to make the program read the data from a
 file instead of a URL.{p_end}

{p 4 4 8}{cmdab:file:nm} this parameter serves two purposes.  For the
key-value and row-value modes, this specifies the location of the JSON to
ingest.  If the JSON is stored in a file, be sure to use the {cmd:nourl}
option.  If the JSON is from a webservice/URL, place the URL in this
parameter and {hi:do not} set the {cmd:nourl} option.  For the out mode, this
specified the location where the resulting JSON object will be saved.  If this
parameter is not used, the resulting JSON object
will be printed to the Stata console. {p_end}

{p 4 4 8}{cmdab:ob:id} has two uses depending on the interface.  If you are
serializing data currently loaded in memory, this option will cause the
program to convert only that observation to a JSON object.  If you are using
the row-value interface to load JSON data, this indicates the observation
where the data should be stored.{p_end}

{p 4 4 8}{cmdab:meta:print[(}{it:"varnames", "varlabels", "labelnames", "vallabs"}{cmd:)]}
an optional argument used to serialize metadata from the Stata dataset to JSON
objects.  The table below defines what each argument returns. {break}{p_end}

{col 10}{hline 70}
{col 10}{hi:Argument} {col 35}{hi: Result}
{col 10}{hline 70}
{col 10}{hi:varnames}{col 25}Will serialize the variable names as a JSON object.
{col 10}{hi:varlabels}{col 25}Will serialize variable labels as a JSON object.
{col 10}{hi:labelnames}{col 25}Will serialize value label names as a JSON object
{col 10}{hi:vallabs}{col 25}Will serialize value labels as a JSON object
{col 10}{hline 70}{break}

{p 4 4 8}{cmdab:w:hat} is an argument used to define what will be serialized.
The acceptable parameter values are {hi:data} and {hi:record}.  Passing a
value of {hi:data} to this argument will trigger the program to serialize the
entire dataset.  Passing a value of {hi:record} to this argument will serialize
a single record from the dataset.  Passing a value of {hi:all} to this
argument will serialize all of the metadata and the data set into a single
JSON object.  If this argument is empty and the metaprint argument has a
valid value, the metaprint argument will define what to serialize. {p_end}

{p 4 4 8}{cmdab:stub:name} is used to define the base to use when
constructing variable names in row-value mode.  If unspecified, the default
value "jsonvar" will be used.  This will result in variable names of the
form: {p_end}
{center:stubname#{break}}
{p 4 4 8}where the value of {hi:#} indicates the traversal order of the data
(or of the queried subset of the data). {p_end}

{marker examples}{title:Examples}{break}

{p 4 4 4} Serialize the last record of the auto dataset {p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio out, what(record) obid(74){p_end}


{p 4 4 4} Serialize the auto dataset {p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio out, what(data){p_end}
{p 8 8 12}jsonio out, what(all){p_end}

{p 4 4 4} Serialize the auto dataset to a file{p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio out, what(data) file(autoDTA.json){p_end}
{p 8 8 12}jsonio out, what(all) file(autoDTA-withMetaData.json){p_end}

{p 4 4 4} Serialize the auto dataset metadata{p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio out, metaprint(varnames){p_end}
{p 8 8 12}jsonio out, metaprint(varlabels){p_end}
{p 8 8 12}jsonio out, metaprint(labelnames){p_end}
{p 8 8 12}jsonio out, metaprint(vallabs){p_end}

{p 4 4 4}Additional examples are available at {browse "http://github.com/wbuchanan/StataJSON"}{p_end}
{break}

{title: Author}{break}
{p 2 2 2}William R. Buchanan, Ph.D. {p_end}
{p 2 2 2}Director, Office of Data, Research, & Accountability{p_end}
{p 2 2 2}{browse "http://www.fcps.net":Fayette County Public Schools}{p_end}
{p 2 2 2}Billy.Buchanan at fayette [dot] kyschools [dot] us{p_end}
