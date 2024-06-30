package com.andre.json.utility;

public class CustomStringUtil {

	private CustomStringUtil() {
	}
	
	public static String removeQuotes(String input) {
		return input.replace("\"", "");
	}
	public static String removeBrackets(String input) {
		return input.replace("[", "").replace("]", "");
	}
	
	public static String capitalLizeFirstLetter(String input) {
		if(input.charAt(0) == '\u0000')
			throw new IllegalStateException();
		
		return input.replaceFirst(input.charAt(0)+"", ((input.charAt(0)+"").toUpperCase()));
	}
	
}
