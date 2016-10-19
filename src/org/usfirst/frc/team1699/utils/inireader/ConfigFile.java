/**
 * FIRST Team 1699
 *
 * A class that represents a file containing configuration info.
 * 
 * @author thatging3rkid, FIRST Team 1699
 *
 * @version v2.0rc1, released on 10/19/2016
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
	 * @param file
	 */
	public ConfigFile(File file) {
		this.file = file;
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath());
		this.readFile();
	}

	/**
	 * Reads the file. Executed automatically on construction, but it can be force reread.
	 */
	public void readFile() {
		ConfigSection section = new ConfigSection("global");
		this.sections.add(section);

		try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
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
				String section1 = new String();
				String section2 = new String();
				while (count1 != line.length()) {
					indexCh = line.charAt(count1);
					if (indexCh == '=') {
						// Gets the previous character
						indexCh = line.charAt(count1 - 1);

						// Check for escape sequence
						if (indexCh == '\\') {
							line = line.substring(0, count1 - 2) + line.substring(count1 - 1, line.length());
							continue;
						}
						// Check for space
						else if (indexCh == ' ') {
							section1 = line.substring(0, count1 - 1);
						} else {
							section1 = line.substring(0, count1);
						}

						// Gets the next character
						indexCh = line.charAt(count1 + 1);
						// Checks for space
						if (indexCh == ' ') {
							section2 = line.substring(count1 + 2, line.length());
						} else {
							section2 = line.substring(count1 + 1, line.length());
						}

						// All done!
						break;
					}
					count1 += 1;
				}
				
				// Case if '=' is not found, allows for more freedom, but you have to code it yourself
				if (count1 == line.length()) {
					section1 = "";
					section2 = line; 
				}
				
				// <insert witty joke here>
				ConfigLine cld = new ConfigLine(section1.toLowerCase(), (Object) section2);
				section.add(cld);

				// Read the next line
				line = reader.readLine();
			}
		}
		// Catches, exceptions, and debugging
		catch (FileNotFoundException e) {
			MessageMaker.out("File not found. Probably crashing about now.");
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
	public ConfigSection findSection(String name) {
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
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
		
	}
}
