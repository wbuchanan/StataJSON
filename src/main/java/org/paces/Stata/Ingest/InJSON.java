package org.paces.Stata.Ingest;

import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.util.List;

/**
 * @author Billy Buchanan
 * @version 0.0.0
 */
public class InJSON {

	private ObjectMapper mapper = new ObjectMapper();
	private MappingJsonFactory jsonFactory = new MappingJsonFactory(mapper);
	private JsonNode rootNode;
	private StataJson stataJson;

	public static void main(String[] args) {
		try {
			new InJSON("/Users/billy/Desktop/waypointsResponse.json");
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}

	public InJSON(String fileName) throws IOException {
		File json = new File(fileName);
		this.rootNode = this.mapper.readTree(json);
		List<Integer> maxDepths = JsonUtils.nodeDepths(this.rootNode, 0);
		this.stataJson = new StataJson(this.rootNode);
		this.stataJson.printString();
	}

	/*
	public InJSON(File json) {
		try  {
			this.rootNode = this.mapper.readTree(json);
			this.stataJson = new StataJson(this.rootNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InJSON(InputStream json) {
		try  {
			this.rootNode = this.mapper.readTree(json);
			this.stataJson = new StataJson(this.rootNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InJSON(URL json) {
		try  {
			this.rootNode = this.mapper.readTree(json);
			this.stataJson = new StataJson(this.rootNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/


}
