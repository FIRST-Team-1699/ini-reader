/**
 * FIRST Team 1699
 * 
 * Temporary storage and processing of lines of configuration files.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.parser;

import java.io.File;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;
import org.usfirst.frc.team1699.utils.inireader.utils.ConfigLineUtils;
import org.usfirst.frc.team1699.utils.inireader.utils.StringUtils;

/**
 * Temporary storage and processing of lines of configuration files.
 */
public class RawLine {
	
	private String line;
	private File config_location;
	private boolean editable = false;
	
	/**
	 * Creates a place to store and process a line in a configuration file
	 * 
	 * @param line a line from a configuration file before processing
	 * @param editable if the ConfigLine should be editable
	 */
	public RawLine(String line, File config_location) {
		this.line = line;
		if (!line.endsWith("\n")) {
			line += "\n";
		}
		this.editable = false;
		this.config_location = config_location;
	}
	
	/**
	 * Creates a place to store and process a line in a configuration file
	 * @param line a line from a configuration file before processing
	 * @param editable if the ConfigLine should be editable
	 * @param config_location the file from the ConfigFile (used for dynamic file locations)
	 */
	public RawLine(String line, boolean editable, File config_location) {
		this.line = line;
		if (!line.endsWith("\n")) {
			line += "\n";
		}
		this.editable = editable;
		this.config_location = config_location;
	}
	
	/**
	 * Set the editable value that is used to construct the ConfigLine
	 * 
	 * @param is_editable the new value for editable
	 */
	public void setEditable(boolean is_editable) {
		this.editable = is_editable;
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
		
		if (this.line.contains("=")) {
			String[] sections = line.split("=");
			section1 = sections[0];
			section2 = sections[1];
		} else {
			// Lines that do not have names and values are automatically Strings
			return new ConfigLine<String>("", this.line);
		}
				
		// Start the madness that is processing this value into a type
		section1 = section1.trim();
		section2 = section2.trim();
		
		// Check for object mode
		if (StringUtils.isSerializedObejct(section2)) {
			byte[] serial_object_bytes = StringUtils.toByteArray(section2);
			return ConfigLineUtils.readSerializedObject(section1, serial_object_bytes, this.editable);
		// Check for file mode
		} else if (StringUtils.isFilePointer(section2)) {
			return ConfigLineUtils.readFilePointer(section1, section2, this.editable, this.config_location);
		// Check if the value is a List
		} else if (StringUtils.containsList(section2)) {			
			// Clean the sting and remove the brackets
			section2 = section2.replace('{', ' ');
			section2 = section2.replace('}', ' ');
			section2 = section2.trim();
			
			try {
				// Check to see if the value contains Integers
				if (StringUtils.containsInteger(section2)) {
					return ConfigLineUtils.makeListInteger(section1, section2, this.editable);
				// Check to see if the value contains Doubles
				} else if (StringUtils.containsDouble(section2)) {
					return ConfigLineUtils.makeListDouble(section1, section2, this.editable);
				// Check to see if the value contains Booleans
				} else if (StringUtils.isBooleanList(section2)) {
					return ConfigLineUtils.makeListBoolean(section1, section2, this.editable);
				// Check to see if the value is a List of Characters
				} else if (StringUtils.isCharacterList(section2)) {
					return ConfigLineUtils.makeListCharacter(section1, section2, this.editable);
				// If nothing else, it's a List of Strings
				} else {
					return ConfigLineUtils.makeListString(section1, section2, this.editable);
				}
			} catch (NumberFormatException e) {
				// If there are any errors in conversion, then return a List<String>
				return ConfigLineUtils.makeListString(section1, section2, this.editable);
			}
		// If it's not a List
		} else {
			try {
				// Checks to see if the value is an Integer
				if (StringUtils.containsInteger(section2)) {
					return new ConfigLine<Integer>(section1, Integer.parseInt(section2), this.editable);
				// Checks to see if the value is a Double
				} else if (StringUtils.containsDouble(section2)) {
					return new ConfigLine<Double>(section1, Double.parseDouble(section2), this.editable);
				// Checks to see if the value is a Boolean
				} else if (StringUtils.isBoolean(section2)) {
					return new ConfigLine<Boolean>(section1, Boolean.parseBoolean(section2), this.editable);
				// Checks to see if the value is a Character
				} else if (StringUtils.isCharacter(section2)) {
					return new ConfigLine<Character>(section1, section2.charAt(0), this.editable);
				// If nothing else, it's a String
				} else {
					return new ConfigLine<String>(section1, section2, this.editable);
				}	
			} catch (NumberFormatException e) {
				return new ConfigLine<String>(section1, section2, this.editable);
			}	
		}
	}

}
