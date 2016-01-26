package org.paces.Stata.JSON;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by billy on 12/12/15.
 */
public class LoadJSON {

	/***
	 * Jackson JSON Api ObjectMapper class object
	 */
	ObjectMapper mapper = new ObjectMapper();

	/***
	 * Jackson JSON API JsonFactor class object
	 */
	JsonFactory jf = new JsonFactory();

	/***
	 * Jackson JSON Api JsonParser object
	 */
	JsonParser jp;

	/***
	 * Method to parse the JSON input
	 * @param options A string array passed from Stata's javacall command
	 *                   used to change default behavior of JsonParser object
	 *                   and methods.
	 * @return A JsonNode object used to represent the JSON Object that can
	 * be queried and retrieved for the active Stata Session
	 * @throws IOException An Exception thrown if there is an error loading the
	 * data passed to the read* methods.
	 */
	public JsonNode getNodes(String[] options) throws IOException {

		if ("true".equals(options[0])) jp.enable(JsonParser.Feature.ALLOW_COMMENTS);
		if ("true".equals(options[1])) jp.enable(JsonParser.Feature.ALLOW_YAML_COMMENTS);
		if ("true".equals(options[2])) jp.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
		if ("true".equals(options[3])) jp.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
		if ("true".equals(options[4])) jp.enable(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
		if ("true".equals(options[5])) jp.enable(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
		if ("true".equals(options[6])) jp.enable(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS);
		if ("true".equals(options[7])) jp.enable(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS);
		if ("true".equals(options[8])) jp.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
		if ("true".equals(options[9])) jp.enable(JsonParser.Feature.IGNORE_UNDEFINED);

		return(mapper.readTree(this.jp));

	}

	public void readFile(String filenm) throws IOException {

		this.jp = jf.createParser(new File(filenm));

	}

	public void readUrl(String uri) throws IOException {

		this.jp = jf.createParser(new URL(uri));

	}

	public void readValue(String jsonstring) throws IOException {

		this.jp = mapper.readTree(jf.createParser(jsonstring));

	}

}
