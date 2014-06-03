package com.cj.votron;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonParser;



public class Util {
	
    @SuppressWarnings("unchecked")
	Map<String,String>jsonToMap(String jsonString) throws IOException {
    	
//    	//Map<String,String> map = new HashMap<String,String>();
//    	ObjectMapper mapper = new ObjectMapper();
//    	@SuppressWarnings("unchecked")
//    	Map<String,String> map = mapper.readValue(jsonString, HashMap.class);
    	
//    	HashMap<String,String> hm;
//    	ObjectMapper objectMapper = new ObjectMapper();
//        HashMap<String,String> result = objectMapper.readValue(jsonString, hm.class);
//        		//objectMapper.readValue(jsonString, HashMap.class);
//        return result;
    	return null;
    }
//
//    String mapToJson(Map<String,String>map) throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String result = objectMapper.writeValueAsString(map);
//        return result;
//    }
//    
//    void foo(){
//    	ObjectMapper mapper;
//		JsonParser from;
//		JSONObject masterJSON = (JSONObject) mapper.readTree(from);
//    }
//	void bar(){
//		ObjectMapper m = new ObjectMapper();
//		// can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
//		JsonNode rootNode = m.readTree(new File("user.json"));
//		// ensure that "last name" isn't "Xmler"; if is, change to "Jsoner"
//		JsonNode nameNode = rootNode.path("name");
//		String lastName = nameNode.path("last").getTextValue().
//		if ("xmler".equalsIgnoreCase(lastName)) {
//		  ((ObjectNode)nameNode).put("last", "Jsoner");
//		}
//	}

}
