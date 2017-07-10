/*
 * Copyright 2012-2014 Dristhi software
 * Copyright 2015 Arkni Brahim
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package org.paces.Stata.Input.external;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import com.stata.sfi.Data;
import com.stata.sfi.SFIToolkit;

public class JSONTableLoader {

    /**
     * Order preserving method to get the keys from the JSON objects
     * @param flatJson A List of Map&lt;String, String&gt; Objects containing the JSON Key : Value pairs
     * @return a Set of key names
     */
    public static Set<String> getKeys(List<Map<String, String>> flatJson) {

        // Used to get the unique set of variable names
        Set<String> headers = new TreeSet<String>();

        // Loop over the Map values of the JSON object to collect the set of unique variable names
        for (Map<String, String> map : flatJson) {

            // Adds the set of unique variable names for this set of values in the list of Map objects
            headers.addAll(map.keySet());

        } // End of For Loop

        // Returns the Set<String> object
        return headers;

    } // End of Method Definition

    /**
     * Method to generate a set of Stata variable names given JSON keys
     * @param keyNames The Set of JSON key names
     * @return A map from JSON key names to valid Stata variable names
     */
    private static Map<String, String> getStataNames(Set<String> keyNames) {

        // Used to store the mapping from the keyName to the Stata variable name
        Map<String, String> stVarNames = new HashMap<String, String>();

        // Loops over the values of the strings in the Set object
        for(String j : keyNames) {

            // Creates the mapping from the JSON key name to the Stata variable name
            stVarNames.put(j, Data.makeVarName(j, true));

        } // Ends Loop

        // Returns the map object
        return stVarNames;

    } // End of Method definition

    /**
     * Method used to create Stata variables in memory from a set of JSON key names
     * @param keys The Set of JSON keys used to generate Stata variables
     * @return A map from JSON keys to Stata variable indices.  All Stata variables are created as Strings with a length of 2000 characters
     */
    public static Map<String, Integer> createStataVariables(Set<String> keys) {

        // Calls method to create mapping from JSON keys to Stata variable names
        Map<String, String> variableMapping = getStataNames(keys);

        // Stores the mapping from JSON key names to Stata variable indices
        Map<String, Integer> variableIndices = new HashMap<String, Integer>();

        // Loops over the JSON keys
        for (String j : keys) {

            // Adds all variables as string variables of length 2000
            Data.addVarStr(variableMapping.get(j), 2000);

            // Stores the mapping from the JSON key name to the Stata variable index
            variableIndices.put(j, Data.getVarIndex(j));

            // Sets the variable label equal to the JSON key name
            Data.setVarLabel(variableIndices.get(j), j);

        } // End of Loop

        // Returns the JSON key to Stata variable index mapping
        return variableIndices;

    } // End of Method declaration

    /**
     * Main method used for Stata Java API interaction
     * @param args An Array of values passed from Stata ado wrapper
     * @return a C-style success indicator
     */
    public static int readJSON(String[] args) throws URISyntaxException {
        List<Map<String, String>> data = null;
        if (args[1] != "url") {
            File jsonData = new File(args[0]);
            data = JSONFlattener.parseJson(jsonData);
        } else {
            URI jsonData = new URI(args[0]);
            data = JSONFlattener.parseJson(jsonData);
        }
        Set<String> jsonKeys = getKeys(data);
        Map<String, Integer> stataIndices = createStataVariables(jsonKeys);
        loadStata(data, stataIndices);
        return 0;
    }

    /**
     * Loads the data into Stata dataset
     * @param flatJson The JSON data to load into Stata
     * @param stataMapping The mapping from the Key name to the Stata variable index
     */
    public static void loadStata(List<Map<String, String>> flatJson, Map<String, Integer> stataMapping){

        // Gets the number of "records" from the data
        Integer mapLength = flatJson.size();

        Map<String, String> stataVarNames = getStataNames(getKeys(flatJson));

        // Set the number of observations in the data set if there are no observations
        if (Data.getObsTotal() == 0) Data.setObsTotal(mapLength);

        // Loops over the record indices i
        for(Integer i = 0; i < mapLength; i++) {

            // Gets the data for the ith observation
            Map<String, String> datum = flatJson.get(i);

            // Loops over the data for the ith observation
            for (String j : datum.keySet()) {

                // Stores the data for the jth variable of the ith observation in the Stata data set
                Data.storeStr(Data.getVarIndex(stataVarNames.get(j)), i, datum.get(j));

            } // Ends loop over the jth variable

        } // Ends loop over the ith observation

    } // Ends Method definition

}
