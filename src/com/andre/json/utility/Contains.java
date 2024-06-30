package com.andre.json.utility;

public class Contains {
	
	private Contains() {
	}
	
	private static char previousCharacter;
	
	private static String field;
	
	public static boolean contains(char input, int previousIndex) {
		return field.contains(input+"") 
				&& (
					 (previousIndex == -1) || field.charAt(previousIndex) == previousCharacter
				 ); 
	}

	public static void setPreviousCharacter(char previousCharacter) {
		Contains.previousCharacter = previousCharacter;
	}


	public static void setField(String field) {
		Contains.field = field;
	}
	
}
