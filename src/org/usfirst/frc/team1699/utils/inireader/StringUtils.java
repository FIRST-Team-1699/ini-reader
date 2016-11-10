/**
 * FIRST Team 1699
 * 
 * A bunch of static methods used to test Strings
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

/**
 * A class filled with static methods that are used for processing String
 */
public class StringUtils {
	
	/**
	 * Tests if the String contains any numbers
	 * 
	 * @param test the String to test
	 * @return true if there are numbers in the String
	 */
	public static boolean containsNumbers(String test) {
		return (test.contains("0") || test.contains("1") || test.contains("2") || test.contains("3") ||
				test.contains("4") || test.contains("5") || test.contains("6") || test.contains("7") || 
				test.contains("8") || test.contains("9"));
	}
	
	/**
	 * Tests if the String contains Integers
	 * 
	 * @param test the String to test
	 * @return true if there are Integers in the String
	 */
	public static boolean containsInteger(String test) {
		return (containsNumbers(test) && !(test.contains(".")));
	}
	
	/**
	 * Tests if the String contains Doubles
	 * 
	 * @param test the String to test
	 * @return true if there are Doubles in the String
	 */
	public static boolean containsDouble(String test) {
		return (containsNumbers(test) && test.contains("."));
	}
	
	/**
	 * Tests if the String is a Boolean
	 * 
	 * @param test the String to test
	 * @return true if the String is a Boolean
	 */
	public static boolean isBoolean(String test) {
		return (test.trim().toLowerCase().equals("true") || test.trim().toLowerCase().equals("false"));
	}
	
	/**
	 * Tests if the String is a List of Booleans
	 * 
	 * @param test the String to test
	 * @return true if the String is a List of Booleans
	 */
	public static boolean isBooleanList(String test) {
		for (String s : test.split(",")) {
			if (!isBoolean(s)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tests if the String is a Character
	 * 
	 * @param test the String to test
	 * @return true if the String is a Character
	 */
	public static boolean isCharacter(String test) {
		return (test.trim().length() == 1);
	}
	
	/**
	 * Tests if the String is a List of Characters
	 * 
	 * @param test the String to test
	 * @return true if the String is a Character
	 */
	public static boolean isCharacterList(String test) {
		for (String s : test.split(",")) {
			if (!isCharacter(s)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tests if the String is a List
	 * 
	 * @param test the String to test
	 * @return true if the String is a List
	 */
	public static boolean containsList(String test) {
		String test1 = test.trim();
		return ((test1.charAt(0) == '{') && (test1.charAt(test.length() - 1) == '}'));
	}	
}