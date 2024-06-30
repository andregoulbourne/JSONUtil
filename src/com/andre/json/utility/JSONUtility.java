package com.andre.json.utility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.andre.util.MetaModel;

public class JSONUtility {
	private JSONUtility() {
	}
	
	private static final Logger logger = Logger.getLogger(JSONUtility.class);
	
	public static List<String> getJSONData(String input, String field){
		var result = new ArrayList<String>();
		
		var countForPropertyStringCharacter = 0;
		
		var propertyStr = "\""+field+"\"";
		Contains.setField(propertyStr);
		Contains.setPreviousCharacter('\u0000');
		
		
		var indexStartOfElement = 0;
		var indexEndOfElement = 0;
		
		
		var countOfOpeningCurlBraces = 0;
		var countOfClosingCurlBraces = 0;
		
		char delimiterForField = '{';
		
		
		var isAnIntegerField = false;
		var isStringField = false;
		var isInitialQuote = 0;
		
		for(int i=0; i<input.length(); i++) {
			
			if(Contains.contains(input.charAt(i), countForPropertyStringCharacter-1)) {
				Contains.setPreviousCharacter(input.charAt(i));
				countForPropertyStringCharacter++;
			} else {
				Contains.setPreviousCharacter('\u0000');
				countForPropertyStringCharacter = 0;
			}
				
			
			if(countForPropertyStringCharacter == propertyStr.length()) {
				indexStartOfElement= i+2;
				if(input.charAt(indexStartOfElement) == '{' || input.charAt(indexStartOfElement) == '[') {
					delimiterForField = input.charAt(indexStartOfElement);
				} else if(input.charAt(indexStartOfElement) == '\"') {
					delimiterForField = input.charAt(indexStartOfElement);
					isStringField = true;
				} else {
					isAnIntegerField = true;
				}
			}
				
				
		if(!isStringField && !isAnIntegerField) {	
			
			if(indexStartOfElement !=0 && input.charAt(i) == delimiterForField)
				countOfOpeningCurlBraces++;
			
			if(indexStartOfElement !=0 && ('{' == delimiterForField ? input.charAt(i) == '}' : input.charAt(i) == ']'))
				countOfClosingCurlBraces++;
			
			if(indexStartOfElement != 0 && countOfOpeningCurlBraces != 0 && countOfOpeningCurlBraces == countOfClosingCurlBraces) {
				indexEndOfElement = i + 1;
			}
			
		}
		
			if(isAnIntegerField && input.charAt(i) == ',')
				indexEndOfElement = i;
			if(isStringField && input.charAt(i) == '\"') {
				if(isInitialQuote < 2)
					isInitialQuote++;
				else {
					indexEndOfElement = i + 1;
					isInitialQuote = 0;
				}
					
			}
				
			
			if(indexStartOfElement != 0 && indexEndOfElement != 0) {
				try {
					result.add(input.substring(indexStartOfElement, indexEndOfElement));
				} catch(Exception e) {
					logger.info("substring operation failed");
				}
				
				countForPropertyStringCharacter=0;
				indexStartOfElement=0;
				indexEndOfElement=0;
				countOfOpeningCurlBraces=0;
				countOfClosingCurlBraces=0;
				isAnIntegerField = false;
			}
		}
		
		return result;
	}
	
	public static List<String> getJSONData(String input){
		var result = new ArrayList<String>();
		
		var indexStartOfElement = -1;
		var indexEndOfElement = 0;
		
		
		var countOfOpeningCurlBraces = 0;
		var countOfClosingCurlBraces = 0;
		
		char delimiterForField = '{';
		
		for(int i=0; i<input.length(); i++) {
			
				if(input.charAt(i) == '{' && indexStartOfElement == -1) {
					indexStartOfElement=i;
				}
				
				
			
			if(indexStartOfElement !=0 && input.charAt(i) == delimiterForField)
				countOfOpeningCurlBraces++;
			
			if(indexStartOfElement !=0 && ('{' == delimiterForField ? input.charAt(i) == '}' : input.charAt(i) == ']'))
				countOfClosingCurlBraces++;
			
			if(indexStartOfElement != 0 && countOfOpeningCurlBraces != 0 && countOfOpeningCurlBraces == countOfClosingCurlBraces) {
				indexEndOfElement = i + 1;
			}
				
			
			if(indexStartOfElement != 0 && indexEndOfElement != 0) {
				try {
					result.add(input.substring(indexStartOfElement, indexEndOfElement));
				} catch(Exception e) {
					logger.info("substring operation failed");
				}
				
				indexStartOfElement=-1;
				indexEndOfElement=0;
				countOfOpeningCurlBraces=0;
				countOfClosingCurlBraces=0;
			}
		}
		
		return result;
	}
	
	public static <T> void addFieldsFromAnnotations(T jsonModel, String propertyJSON, boolean isEnrichment) {
		@SuppressWarnings("unchecked")
		MetaModel<T> metadata = (MetaModel<T>) MetaModel.of(jsonModel.getClass()); 
		
		metadata.getJSONFields().stream()
		.filter(jsonField -> isEnrichment ? jsonField.isEnrichment() : !jsonField.isEnrichment())
        .forEach(jsonField -> {
        	try {
				Method setter
				  = jsonModel.getClass().getMethod("set"+ CustomStringUtil.capitalLizeFirstLetter(jsonField.getName()), jsonField.getType());
				
				var result =  JSONUtility.getJSONData(propertyJSON, jsonField.getJsonFieldName()); 
				Object fieldResult = "";
				if(result.isEmpty() && String.class != jsonField.getType())
					fieldResult	= "0";
				else if(!result.isEmpty())	
					fieldResult = result.get(0);
				
				if(String.class == jsonField.getType()) 
					fieldResult = santizeResult((String) fieldResult);
				
				if(int.class == jsonField.getType())
					fieldResult = Integer.parseInt(santizeResult((String) fieldResult));
				
				if(Double.class == jsonField.getType())
					fieldResult = Double.parseDouble(santizeResult((String) fieldResult));					
				
				setter.invoke(jsonModel, fieldResult);
				
			} catch (Exception e) {
				logger.error("An exception occur while reading the json fieldAnnotations", e);
			}
        });
	}
	

	public static String santizeResult(String input) {
		return CustomStringUtil.removeBrackets(CustomStringUtil.removeQuotes(input));
	}
}
