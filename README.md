# JSON I/O Functions for Stata

Java plugin for Stata to convert in-memory data to JSON output.  The plugin 
uses the [Jackson JSON Library](https://github.com/FasterXML/jackson).  
__Early in development and at best should be considered a pre-alpha release__
.  Testers/collaborators are always appreciated and if you encounter issues 
feel free to submit an issue on Github. 


## Example
With org.paces.Stata-jar-with-dependencies.jar on the ADOPATH:

```
sysuse auto, clear
javacall org.paces.Stata.StataJSON dbug
```
