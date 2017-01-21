/**
 * FIRST Team 1699
 *
 * A class that represents a file containing configuration info.
 * 
 * @author thatging3rkid, FIRST Team 1699
 *
 * @version v2.2-pre
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.io.File;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.exception.NotFoundException;
import org.usfirst.frc.team1699.utils.inireader.parser.Parser;
import org.usfirst.frc.team1699.utils.inireader.utils.MessageMaker;
import org.usfirst.frc.team1699.utils.inireader.utils.StringUtils;

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
	public ConfigFile() throws FileNotFoundException, IOException {
		this.file = new File("/home/lvuser/1699-config.ini");
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath() + " (default)");
		this.read();
	}

	/**
	 * Creates a ConfigFile, opening the file specified
	 * 
	 * @param fullPath full path to the file
	 */
	public ConfigFile(String fullPath) throws FileNotFoundException, IOException {
		this.file = new File(fullPath);
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath());
		this.read();
	}

	/**
	 * Creates a ConfigFile with the specified File
	 * 
	 * @param file the file that will be read from
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public ConfigFile(File file) throws FileNotFoundException, IOException {
		this.file = file;
		MessageMaker.out("Initalized with file: " + this.file.getAbsolutePath());
		this.read();
	}
	
	/**
	 * Reads the file. Executed automatically on construction, but it can be force-reread.
	 */
	public void read() throws FileNotFoundException, IOException {
		
		// Read the entire file into the variable contents
		BufferedReader reader = new BufferedReader(new FileReader(this.file));
		String line;
		String contents = "";
		while ((line = reader.readLine()) != null) {
			contents += line + "\n";
		}
		
		// Now, it's time to parse the contents of the file
		Parser parser = new Parser(contents);
		parser.parse();
		this.sections = parser.getSections();	
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
	 * @throws NotFoundException if the given name is not found
	 */
	public ConfigSection getSection(String name) throws NotFoundException {
		// Cycle through the ArrayList, return ConfigSection if names are equal
		for (ConfigSection cs : sections) {
			if (cs.getName().trim().equals(name.trim())) {
				return cs; // this needs to be replaced to return a copy of the section, not a reference to it
			}
		}
		// Throw exception if not found
		throw new NotFoundException("Section not found: " + name + ".");
	}
	
	/**
	 * Gets the contents of this ConfigFile
	 * 
	 * @return an ArrayList of ConfigSections that make up this ConfigFile
	 */
	public List<ConfigSection> getSections() {
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
	public <Check_Type> Check_Type getLineValue(String section_name, String line_name, Class<Check_Type> class_type) 
			throws NotFoundException {
		return this.getSection(section_name).getLineValue(line_name, class_type);
	}
	
	/**
	 * Returns the number of ConfigSections that are in this ConfigFile.
	 * 
	 * @return number of COnfigSections in this ConfigFile
	 */
	public int size() {
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
