{smcl}
{* *! version 0.0.1  24SEP2015}{...}
{cmd:help jsonio}
{hline}

{title:Title}

{hi:jsonio {hline 2}} A Stata JSON Serializer/Deserializer built on the {browse "https://github.com/FasterXML/jackson": Jackson} Java library.  For the most up to date version of this program, visit

{browse "https://github.com/wbuchanan/StataJSON":Stata JSON on Github}

Currently, the program only supports serializing an existing Stata dataset in 
memory, but will evolve to support injesting JSON data in the near future as well. {p_end}

{title:Syntax}

{p 4 4 4}{cmd:jsonio} [{opt:varlist}] [{ifin}] [{opt:using}] , [
{cmdab:file:nm(}{it:string}{cmd:)}
{cmdab:ob:id(}{it:real}{cmd:)}
{cmdab:meta:print(}{it:string}{cmd:)}
{cmdab:w:hat(}{it:string}{cmd:)}] {break}

{title:Description}

{p 4 4 4}{cmd:jsonio} is in the earliest stages of development and currently is
focused on serializing Stata data/metadata to JSON objects/files.  As the output
side of the program stabalizes work on the input side will begin.  Until then,
see {stata search insheetjson} for methods to deserialize JSON data. {p_end}

{title:Options}
{p 4 4 8}{cmdab:file:nm} this argument is used to specify a file where the JSON
object will be written.  If this parameter is not used, the resulting JSON object
will be printed to the Stata console. {break}

{p 4 4 8}{cmdab:ob:id} is used to define a single observation to be serialized
to a JSON object. {break}

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


{marker examples}{title:Examples}{break}

{p 4 4 4} Serialize the last record of the auto dataset {p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio, what(record) obid(74){p_end}


{p 4 4 4} Serialize the auto dataset {p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio, what(data){p_end}
{p 8 8 12}jsonio, what(all){p_end}

{p 4 4 4} Serialize the auto dataset to a file{p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio, what(data) file(autoDTA.json){p_end}
{p 8 8 12}jsonio, what(all) file(autoDTA-withMetaData.json){p_end}

{p 4 4 4} Serialize the auto dataset metadata{p_end}

{p 8 8 12}sysuse auto, clear{p_end}
{p 8 8 12}jsonio, metaprint(varnames){p_end}
{p 8 8 12}jsonio, metaprint(varlabels){p_end}
{p 8 8 12}jsonio, metaprint(labelnames){p_end}
{p 8 8 12}jsonio, metaprint(vallabs){p_end}

{p 4 4 4}Additional examples are available at {browse "https://github.com/wbuchanan/StataJSON"}{p_end}
{break}

{title: Author}{break}
{p 1 1 1} William R. Buchanan, Ph.D. {break}
Data Scientist {break}
{browse "http://mpls.k12.mn.us":Minneapolis Public Schools} {break}
William.Buchanan at mpls [dot] k12 [dot] mn [dot] us
