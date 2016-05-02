---
layout: post
title:  "JSONIO now supports importing data"
date:   2016-05-02 05:51:00
categories: news
---

# Latest update to JSONIO adds import support
No changes have been made to the export functionality or `ggeocode` command, but the jsonio command has been modified to add subcommands to support import functions.  

# Two import modes
`jsonio` supports loading the data into Stata in two different methods.  

## Key-Value mode
key-value mode loads the data into two variables with the names key and value.  This can be useful for cases where you want to work with a single JSON payload/dataset that you want to further restructure. One of the disadvantages is that all data are loaded as string if all of the nodes are not the same type.  If the data are the same type, they will automatically be cast to the correct data type.  

|---------|------------|
|   Key   |    Value   |
| /grandparent/parent\_1/child\_1/terminal\_node | node value |
| /grandparent/parent\_1/child\_2/terminal\_node | node value |
| /grandparent/parent\_1/child\_3/terminal\_node | node value |
| /grandparent/parent\_1/child\_4/terminal\_node | node value |
| /grandparent/parent\_2/child\_1/terminal\_node | node value |
| /grandparent/parent\_2/child\_2/terminal\_node | node value |
| /grandparent/parent\_3/child\_1/terminal\_node | node value |
| /grandparent/parent\_3/child\_2/terminal\_node | node value |
| /grandparent/parent\_3/child\_3/terminal\_node | node value |
|   |   |

The table above shows a generic structure representing the way that nodes are renamed by the program internally.  Terminal nodes (with the exception of array elements) have no numeric IDs, but their ancestors include numeric values that indicate the order in which the node appears in the traversal and also allows the name names to uniquely identify the values.

## Row-Value mode

In row-value mode, the same hypothetical example above would be added as a single 1 x 9 row vector of values, which can be useful if making calls to an API based on the values from individual records.

|--------|-------|-------|-------|-------|-------|-------|-------|-------|
| jsonval1 | jsonval2 | jsonval3 | jsonval4 | jsonval5 | jsonval6 | jsonval7 | jsonval8 | jsonval9 |
| value | value | value | value | value | value | value | value | value |
|   |   |   |   |   |   |   |   |   |
 
You'll notice that the variable names are different, and the stub (`jsonval` in the example above) can be specified by users.  Additionally, the lineage of each node is still retained when loading the data into Stata in this format.  The difference is that in row-value mode, the lineage is stored in variable labels : 

|---------|------------|
| Variable Name   |    Variable Label  |
| jsonval1 | /grandparent/parent\_1/child\_1/terminal\_node |
| jsonval2 | /grandparent/parent\_1/child\_2/terminal\_node |
| jsonval3 | /grandparent/parent\_1/child\_3/terminal\_node |
| jsonval4 | /grandparent/parent\_1/child\_4/terminal\_node |
| jsonval5 | /grandparent/parent\_2/child\_1/terminal\_node |
| jsonval6 | /grandparent/parent\_2/child\_2/terminal\_node |
| jsonval7 | /grandparent/parent\_3/child\_1/terminal\_node |
| jsonval8 | /grandparent/parent\_3/child\_2/terminal\_node |
| jsonval9 | /grandparent/parent\_3/child\_3/terminal\_node |
|     |      |      


Only a few tests have been performed, but the traversal method should be fairly robust to arbitrarily structured JSON (e.g., regardless of what gets thrown at it, it should be able to flatten it and load it as key-value or row-value).  If you find any problems please submit an issue in the project repository that can be replicated (e.g., if I can't see the JSON causing the issue it becomes difficult to address the problem).
