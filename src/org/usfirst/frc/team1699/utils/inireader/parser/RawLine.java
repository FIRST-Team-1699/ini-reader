/**
 * FIRST Team 1699
 * 
 * Temporary storage and processing of lines of configuration files.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.parser;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;
import org.usfirst.frc.team1699.utils.inireader.utils.StringUtils;
import org.usfirst.frc.team1699.utils.inireader.utils.TypeUtils;

/**
 * Temporary storage and processing of lines of configuration files.
 */
public class RawLine {
	
	private String line;
	
	/**
	 * Creates a place to store and process a line in a configuration file
	 * 
	 * @param line a line from a configuration file before processing
	 */
	public RawLine(String line) {
		this.line = line;		
	}
	
	/**
	 * Generates a ConfigLine
	 * 
	 * @return a ConfigLine made from the contents of the line
	 */
	public ConfigLine<?> toConfigLine() {
		String section1;
		String section2;
		
		// TODO escape character detection is not complete
		
		if (line.contains("=")) {
			String[] sections = line.split("=");
			section1 = sections[0];
			section2 = sections[1];
		} else {
			// Lines that do not have names and values are automatically Strings
			return new ConfigLine<String>("", line);
		}
				
		// Start the madness that is processing this value into a type
		section2 = section2.trim();
		
		// Check if the value is a List
		if (StringUtils.containsList(section2)) {
			// Clean the sting and remove the brackets
			section2 = section2.replace('{', ' ');
			section2 = section2.replace('}', ' ');
			section2 = section2.trim();
			
			try {
				// Check to see if the value contains Integers
				if (StringUtils.containsInteger(section2)) {
					return TypeUtils.makeCLListInteger(section1, section2);
				// Check to see if the value contains Doubles
				} else if (StringUtils.containsDouble(section2)) {
					return TypeUtils.makeCLListDouble(section1, section2);
				// Check to see if the value contains Booleans
				} else if (StringUtils.isBooleanList(section2)) {
					return TypeUtils.makeCLListBoolean(section1, section2);
				// Check to see if the value is a List of Characters
				} else if (StringUtils.isCharacterList(section2)) {
					return TypeUtils.makeCLListCharacter(section1, section2);
				// If nothing else, it's a List of Strings
				} else {
					return TypeUtils.makeCLListString(section1, section2);
				}
			} catch (NumberFormatException e) {
				// If there are any errors in conversion, then return a List<String>
				return TypeUtils.makeCLListString(section1, section2);
			}
		// If it's not a List
		} else {
			try {
				// Checks to see if the value is an Integer
				if (StringUtils.containsInteger(section2)) {
					return new ConfigLine<Integer>(section1, Integer.parseInt(section2));
				// Checks to see if the value is a Double
				} else if (StringUtils.containsDouble(section2)) {
					return new ConfigLine<Double>(section1, Double.parseDouble(section2));
				// Checks to see if the value is a Boolean
				} else if (StringUtils.isBoolean(section2)) {
					return new ConfigLine<Boolean>(section1, Boolean.parseBoolean(section2));
				// Checks to see if the value is a Character
				} else if (StringUtils.isCharacter(section2)) {
					return new ConfigLine<Character>(section1, section2.charAt(0));
				// If nothing else, it's a String
				} else {
					return new ConfigLine<String>(section1, section2);
				}	
			} catch (NumberFormatException e) {
				return new ConfigLine<String>(section1, section2);
			}	
		}
	}

}
