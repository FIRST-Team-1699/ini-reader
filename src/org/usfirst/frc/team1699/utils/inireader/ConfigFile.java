/**
 * FIRST Team 1699
 *
 * A class that represents a file containing configuration info.
 * 
 * @author thatging3rkid, FIRST Team 1699
 *
 * @version v2.1rc1, released on 11/9/2016
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * A way to store, access, and process data in a configuration file.  
 */
public class ConfigFile {

	private File file;
	
	private ArrayList<ConfigSection> sections = new ArrayList<>();

	/**
	 * Creates a basic ConfigFile with a file at /home/lvuser/1699-config.ini
	 */
	public ConfigFile() {
		this.file = new File("/home/lvuser/1699-config.ini");
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath() + " (default)");
		this.readFile();
	}

	/**
	 * Creates a ConfigFile, opening the file specified
	 * 
	 * @param fullPath full path to the file
	 */
	public ConfigFile(String fullPath) {
		this.file = new File(fullPath);
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath());
		this.readFile();
	}

	/**
	 * Creates a ConfigFile with the specified File
	 * 
	 * @param file the file that will be read from
	 */
	public ConfigFile(File file) {
		this.file = file;
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath());
		this.readFile();
	}

	/**
	 * Reads the file. Executed automatically on construction, but it can be force-reread.
	 */
	public void readFile() {
		ConfigSection section = new ConfigSection("global");
		this.sections.add(section);

		try (
				BufferedReader reader = new BufferedReader(new FileReader(file));
			) {
			String line = reader.readLine();
			while (line != null) {
				// Checks for blank line
				if ((line.equals("")) || (line.equals(" ")) || (line.equals("\n")) || (line.equals("\r\n"))) {
					line = reader.readLine();
					continue;
				}

				// Checks for comment
				if ((line.substring(0, 1).equals(";")) || (line.substring(0, 1).equals("#"))) {
					line = reader.readLine();
					continue;
				}
				
				// Checks for new section
				if (line.substring(0, 1).equals("[")) {
					int count1 = 0;
					char indexCh;
					while (count1 != line.length()) {
						indexCh = line.charAt(count1);

						if (indexCh == ']') {
							break;
						}

						count1 += 1;
					}
					section = new ConfigSection("" + line.substring(1, count1));
					this.sections.add(section);
					line = reader.readLine();
					continue;
				}

				// Starts looking for values in the line
				int count1 = 0;
				char indexCh;
				String section1 = null;
				String section2 = null;
				
				while (count1 != line.length()) {
					// Update the character
					indexCh = line.charAt(count1);
					
					// Check for an '='
					if (indexCh == '=') {
						section1 = line.substring(0, count1).trim();
						section2 = line.substring(count1 + 1).trim();
						break;
						
					// Check for an escape sequence and cut it out
					} else if (indexCh == '\\') {
						line = line.substring(0, count1).trim() + line.substring(count1 + 1, line.length()).trim();
					} 
					
					// To to the next character!
					count1 += 1;
				}
				
				// Checks to see if the sections have been assigned values yet
				if (section1 == null) {
					section1 = "";
					section2 = new String(line);
				} 
				
				// Start the madness that is processing this value into a type
				String cleaned = section2.trim();
				ConfigLine<?> cld;
				
				// Check if the value is a List
				if (StringUtils.containsList(cleaned)) {
					// Clean the sting and remove the brackets
					section2 = section2.trim();
					cleaned = section2.substring(1, section2.length() - 1);
					
					// Check to see if the value contains Integers
					if (StringUtils.containsInteger(cleaned)) {
						try {
							List<Integer> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(Integer.parseInt(s.trim()));
							}
							cld = new ConfigLine<List<Integer>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cld = new ConfigLine<List<String>>(section1, out);
						}					
					
					// Check to see if the value contains Doubles
					} else if (StringUtils.containsDouble(cleaned)) {
						try {
							List<Double> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(Double.parseDouble(s.trim()));
							}
							cld = new ConfigLine<List<Double>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cld = new ConfigLine<List<String>>(section1, out);
						}
					
					// Check to see if the value contains Booleans
					} else if (StringUtils.isBooleanList(cleaned)) {
						try {
							List<Boolean> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(Boolean.parseBoolean(s.trim()));
							}
							cld = new ConfigLine<List<Boolean>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cld = new ConfigLine<List<String>>(section1, out);
						}
					
					// Check to see if the value is a List of Characters
					} else if (StringUtils.isCharacterList(cleaned)) {
						List<Character> out = new ArrayList<>();
						for (String s : cleaned.split(",")) {
							out.add(s.trim().charAt(0));	
						}
						cld = new ConfigLine<List<Character>>(section1, out);
					// If nothing else, it's a List of Strings
					} else {
						List<String> out = new ArrayList<>();
						for (String s : cleaned.split(",")) {
							out.add(s.trim());	
						}
						cld = new ConfigLine<List<String>>(section1, out);
					}
					
				// If it's not a List
				} else {
					// Clean the string
					section2 = section2.trim();
					
					// Checks to see if the value is an Integer
					if (StringUtils.containsInteger(cleaned)) {
						try {
							cld = new ConfigLine<Integer>(section1, Integer.parseInt(section2));
						} catch (NumberFormatException e) {
							cld = new ConfigLine<String>(section1, section2);
						}
						
					// Checks to see if the value is a Double
					} else if (StringUtils.containsDouble(cleaned)) {
						try {
							cld = new ConfigLine<Double>(section1, Double.parseDouble(section2));
						} catch (NumberFormatException e) {
							cld = new ConfigLine<String>(section1, section2);
						}
					
					// Checks to see if the value is a Boolean
					} else if (StringUtils.isBoolean(cleaned)) {
						try {
							cld = new ConfigLine<Boolean>(section1, Boolean.parseBoolean(section2));
						} catch (NumberFormatException e) {
							cld = new ConfigLine<String>(section1, section2);
						}
					
					// Checks to see if the value is a Character
					} else if (StringUtils.isCharacter(cleaned)) {
						cld = new ConfigLine<Character>(section1, cleaned.charAt(0));
					
					// If nothing else, it's a String. 
					} else {
						cld = new ConfigLine<String>(section1, section2);
					}
				}
				
				// Add the ConfigLine to the section
				section.add(cld);

				// Read the next line
				line = reader.readLine();
			}
		}
		// Catches, exceptions, and debugging
		catch (FileNotFoundException e) {
			MessageMaker.out("File not found.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the specified ConfigSection if it exists. If not, then throw an exception.
	 * 
	 * @param name the name of the ConfigSection to find.
	 * @return the ConfigSection matching name if found.
	 */
	public ConfigSection getSection(String name) {
		// Cycle through the ArrayList, return ConfigSection if names are equal
		for (ConfigSection cs : sections) {
			if (cs.getName().equals(name)) {
				return cs; // this needs to be replaced to return a copy of the section, not a reference to it
			}
		}
		// Throw exception if not found
		throw new NotFoundException("Section not found: " + name + ".");
	}
	
	/**
	 * Returns the ConfigSection at the specified index. Useful for autonomous. Returns null if out of bounds.
	 * 
	 * @param index the index of the internal ArrayList
	 * @return the ConfigSection at that index if it exists, else null
	 */
	public ConfigSection getSection(int index) {
		try {
			return this.sections.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	/**
	 * Returns the number of ConfigSections that are in this ConfigFile.
	 * 
	 * @return number of COnfigSections in this ConfigFile
	 */
	public int size() {
		return this.sections.size();
	}
}
