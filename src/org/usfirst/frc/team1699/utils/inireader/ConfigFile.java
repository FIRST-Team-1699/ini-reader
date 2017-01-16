/**
 * FIRST Team 1699
 *
 * A class that represents a file containing configuration info.
 * 
 * @author thatging3rkid, FIRST Team 1699
 *
 * @version v2.1rc3, released on 1/16/2016
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

/**
 * A way to store, access, and process data in a configuration file.  
 */
public class ConfigFile {

	private File file;
	private List<ConfigSection> sections = null;

	/**
	 * Creates a basic ConfigFile with a file at /home/lvuser/1699-config.ini
	 */
	public ConfigFile() {
		this.file = new File("/home/lvuser/1699-config.ini");
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath() + " (default)");
		this.readFile();
	}

	/**
	 * Creates a ConfigFile, opening the file specified
	 * 
	 * @param fullPath full path to the file
	 */
	public ConfigFile(String fullPath) {
		this.file = new File(fullPath);
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath());
		this.readFile();
	}

	/**
	 * Creates a ConfigFile with the specified File
	 * 
	 * @param file the file that will be read from
	 */
	public ConfigFile(File file) {
		this.file = file;
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath());
		this.readFile();
	}
	
	/**
	 * Creates a ConfigFile with the specified File, but gives the user the option to skip reading the file
	 * 
	 * @param file the file that will be read from
	 * @param no_file_read if true, the file will not be read into memory until called using .readFile()
	 */
	public ConfigFile(File file, boolean no_file_read) {
		this.file = file;
		if (no_file_read) {
			MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath() + "; not reading file in constructor!");
		} else {
			MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath());
			this.readFile();
		}
		
	}
	
	/**
	 * Reads the file. Executed automatically on construction, but it can be force-reread.
	 */
	public void readFile() {
		ConfigSection section = new ConfigSection("global");
		this.sections = new ArrayList<>();
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
				ConfigLine<?> cl_add;
				
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
							cl_add = new ConfigLine<List<Integer>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cl_add = new ConfigLine<List<String>>(section1, out);
						}					
					
					// Check to see if the value contains Doubles
					} else if (StringUtils.containsDouble(cleaned)) {
						try {
							List<Double> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(Double.parseDouble(s.trim()));
							}
							cl_add = new ConfigLine<List<Double>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cl_add = new ConfigLine<List<String>>(section1, out);
						}
					
					// Check to see if the value contains Booleans
					} else if (StringUtils.isBooleanList(cleaned)) {
						try {
							List<Boolean> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(Boolean.parseBoolean(s.trim()));
							}
							cl_add = new ConfigLine<List<Boolean>>(section1, out);
						} catch (NumberFormatException e ) {
							List<String> out = new ArrayList<>();
							for (String s : cleaned.split(",")) {
								out.add(s.trim());	
							}
							cl_add = new ConfigLine<List<String>>(section1, out);
						}
					
					// Check to see if the value is a List of Characters
					} else if (StringUtils.isCharacterList(cleaned)) {
						List<Character> out = new ArrayList<>();
						for (String s : cleaned.split(",")) {
							out.add(s.trim().charAt(0));	
						}
						cl_add = new ConfigLine<List<Character>>(section1, out);
					// If nothing else, it's a List of Strings
					} else {
						List<String> out = new ArrayList<>();
						for (String s : cleaned.split(",")) {
							out.add(s.trim());	
						}
						cl_add = new ConfigLine<List<String>>(section1, out);
					}
					
				// If it's not a List
				} else {
					// Clean the string
					section2 = section2.trim();
					
					// Checks to see if the value is an Integer
					if (StringUtils.containsInteger(cleaned)) {
						try {
							cl_add = new ConfigLine<Integer>(section1, Integer.parseInt(section2));
						} catch (NumberFormatException e) {
							cl_add = new ConfigLine<String>(section1, section2);
						}
						
					// Checks to see if the value is a Double
					} else if (StringUtils.containsDouble(cleaned)) {
						try {
							cl_add = new ConfigLine<Double>(section1, Double.parseDouble(section2));
						} catch (NumberFormatException e) {
							cl_add = new ConfigLine<String>(section1, section2);
						}
					
					// Checks to see if the value is a Boolean
					} else if (StringUtils.isBoolean(cleaned)) {
						try {
							cl_add = new ConfigLine<Boolean>(section1, Boolean.parseBoolean(section2));
						} catch (NumberFormatException e) {
							cl_add = new ConfigLine<String>(section1, section2);
						}
					
					// Checks to see if the value is a Character
					} else if (StringUtils.isCharacter(cleaned)) {
						cl_add = new ConfigLine<Character>(section1, cleaned.charAt(0));
					
					// If nothing else, it's a String. 
					} else {
						cl_add = new ConfigLine<String>(section1, section2);
					}
				}
				
				// Add the ConfigLine to the section
				section.add(cl_add);

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
	 * Gets the file of the ConfigFile
	 * 
	 * @return the file with this ConfigFile
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * Gets the specified ConfigSection if it exists. If not, then throw an exception.
	 * 
	 * @param name the name of the ConfigSection to find.
	 * @return the ConfigSection matching name if found.
	 */
	public ConfigSection getSection(String name) {
		if (this.sections == null) {
			throw new NullPointerException("ConfigFile's section List is null, did you read the file?");
		}
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
		if (this.sections == null) {
			throw new NullPointerException("ConfigFile's section List is null, did you read the file?");
		}
		try {
			return this.sections.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		
	}
	
	/**
	 * Gets the contents of this ConfigFile
	 * 
	 * @return an ArrayList of ConfigSections that make up this ConfigFile
	 */
	public List<ConfigSection> getSections() {
		if (this.sections == null) {
			return null;
		}
		List<ConfigSection> out = new ArrayList<>();
		for (ConfigSection cs : this.sections) {
			out.add(new ConfigSection(cs));
		}
		return out;
		
	}
	
	/**
	 * Gets the value of the line without having to use two dot operators! Super cool!
	 * 
	 * @param section_name the name of the section to look at
	 * @param line_name the name of the line to look for
	 * @param class_type a reference to the class (for safety)
	 * @return the value of the line in the specified section with the specified name
	 */
	public <Check_Type> Check_Type getLineValue(String section_name, String line_name, Class<Check_Type> class_type) {
		return this.getSection(section_name).getLineValue(line_name, class_type);
	}
	
	/**
	 * Returns the number of ConfigSections that are in this ConfigFile.
	 * 
	 * @return number of COnfigSections in this ConfigFile
	 */
	public int size() {
		if (this.sections == null) {
			throw new NullPointerException("ConfigFile's section List is null, did you read the file?");
		}
		return this.sections.size();
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		String output = "ConfigFile at: " + this.file.getAbsolutePath() + "\n";
		for(ConfigSection cs : this.sections) {
			output += cs.toString();
		}
		return output;
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigFile) {
			ConfigFile cf_test = (ConfigFile) obj;
			return (cf_test.getSections().equals(this.sections) && cf_test.getFile().equals(this.file));
		}
		return false;
	}
}
