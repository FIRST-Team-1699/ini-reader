/**
 * FIRST Team 1699
 * 
 * A class that represents a section of a configuration file.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.exception.NotEditableException;
import org.usfirst.frc.team1699.utils.inireader.exception.NotFoundException;

/**
 * Represents a section of a configuration file. 
 * It stores multiple ConfigLine objects and has methods to access and process them. 
 */
public class ConfigSection implements Serializable {

	private static final long serialVersionUID = -5690561791468993953L;
	
	private String name;
	private List<ConfigLine<?>> lines;
	
	public final boolean editable;

	/**
	 * Creates a ConfigSection with the name provided
	 * 
	 * @param name the name of the ConfiSection
	 * @param lines the lines of this ConfigSection
	 */
	public ConfigSection(String name, List<ConfigLine<?>> lines) {
		this.name = name;
		this.lines = lines;
		this.editable = false;
	}
	
	/**
	 * Creates a ConfigSection with the name provided and editable if given true
	 * 
	 * @param name the name of the ConfigSection
	 * @param lines the lines of this ConfigSection
	 * @param editable if the ConfigSection should be editable
	 */
	public ConfigSection(String name, List<ConfigLine<?>> lines, boolean editable) {
		this.name = name;
		this.lines = lines;
		this.editable = editable;
	}
	
	/**
	 * Creates a copy of the given ConfigSection
	 * 
	 * @param section a ConfigSection to copy
	 */
	public ConfigSection(ConfigSection section) {
		this.name = section.getName();
		this.lines = section.getLines();
		this.editable = section.editable;
	}

	/**
	 * Gets the name of the ConfigSection
	 * 
	 * @return name of the ConfigSection
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add the ConfigLine to the ConfigSection
	 * 
	 * @param line the ConfigLine to be added
	 */
	public void add(ConfigLine<?> line) {
		if (!this.editable) {
			throw new NotEditableException("ConfigSection "  + this.name + " is not editable!");
		}
		lines.add(line);
	}

	/**
	 * Create a ConfigLine and add it to the ConfigSection
	 * 
	 * @param <Type> the type of the new ConfigLine
	 * 
	 * @param name  the name for the ConfigLine to be added
	 * @param value  the value for the ConfigLine to be added
	 */
	public <Type> void add(String name, Type value) {
		if (!this.editable) {
			throw new NotEditableException("ConfigSection "  + this.name + " is not editable!");
		}		
		ConfigLine<Type> line = new ConfigLine<Type>(name, value);
		this.add(line);
	}
	
	/**
	 * Overwrite a line if it is editable. 
	 * 
	 * Note: in order for a line to be overwritten, the names must match.
	 * 
	 * @param line the line to overwrite, with the new value
	 */
	public void overwrite(ConfigLine<?> line) {
		
		// Avoid a NullPointer
		if (this.lines.isEmpty()) {
			return;
		}
		
		// Iterate over all the values in the List
		ConfigLine<?> cl;
		for (int i = 0; i < this.lines.size(); i += 1) {
			// Get the value from the list
			cl = lines.get(i);
			
			// See if the names match
			if (cl.getName().trim().equals(line.getName().trim())) {
				// Make sure that the ConfigLine (or this section) is editable
				if (!(cl.editable || this.editable)) {
					throw new NotEditableException("ConfigLine " + cl.getName() + " is not editable!");
				}
				
				// Remove the old ConfigLine and replace it with the new one
				this.lines.remove(i);
				this.lines.add(i, line);
				return;
			}
		}
		System.err.println("ConfigLine " + line.getName() + " was not found when attempting to overwrite!");
	}
	
	/**
	 * Get a ConfigLine based on the provided name. Returns the first ConfigLine with that name.
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @return the ConfigLine that matches the specified name, null if it does not exist
	 * @throws NotFoundException if the given name is not found
	 */
	public ConfigLine<?> getLine(String name) throws NotFoundException {
		for (ConfigLine<?> cl : this.lines) {
			if (cl.getName().trim().equals(name.trim())) {
				return new ConfigLine<>(cl);
			}
		}
		throw new NotFoundException("Line not found " + name + ".");
	}
	
	/**
	 * Get the contents of this ConfigSection. Useful for autonomous. 
	 * 
	 * @return an ArrayList of ConfigLines that make up this ConfigSection
	 */
	public List<ConfigLine<?>> getLines() {
		if (this.lines == null) {
			return null;
		}
		List<ConfigLine<?>> output = new ArrayList<>();
		for (ConfigLine<?> cl : this.lines) {
			output.add(new ConfigLine<>(cl));
		}
		return output;
	}
	
	/**
	 * Gets the value of a ConfigLine without using a dot operator! Cool!
	 * 
	 * @param <Check_Type> the type to check against
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @param class_type the Class of the type
	 * @return the the value of this ConfigLine or null if the types do not match
	 * @throws NotFoundException if the given name is not found
	 */
	@SuppressWarnings("unchecked") // it is checked so...
	public <Check_Type> Check_Type getLineValue(String name, Class<Check_Type> class_type) throws NotFoundException {
		ConfigLine<?> out = this.getLine(name);
		if (out.getRawValue().getClass().equals(class_type)) {
			return (Check_Type) out.getRawValue(); 
		} 
		return null;
	}
	
	/**
	 * Get the contents of this ConfigSection as a List of Strings. 
	 * 
	 * @return an ArrayList of Strings made from the ConfigLines in the ConfigSection
	 */
	public List<String> getStringValues() {
		if (this.lines == null) {
			return null;
		} else {
			List<String> output = new ArrayList<>();
			for (ConfigLine<?> cl : this.lines) {
				output.add(cl.getRawValue().toString());
			}
			return output;
		}
	}
	
	/**
	 * Get the number of lines in this ConfigSection
	 * 
	 * @return the number of lines in this ConfigSection
	 */
	public int size() {
		return this.lines.size();
	}
	
	/**
	 * Test if the given name exists in this ConfigSection
	 * 
	 * @param name the name to test
	 * @return true if the name exists in this ConfigSection
	 */
	public boolean contains(String name) {
		for (ConfigLine<?> cl : this.lines) {
			if (cl.getName().trim().equals(name.trim())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Attempts to generate the code that made this ConfigSection.
	 * 
	 * @return a String with the code that made this ConfigSection
	 */
	public String generateCode() {
		// Make the output String
		String output = "";
		
		// Marks this section editable if it is
		if (this.editable) {
			output = "%";
		}
		
		// If this is the global section, then the header is not needed
		if (!this.name.equals("global")) {
			output += "[" + this.name + "]\n";
		}
		
		// Generate all the code for this section's ConfigLines
		for (ConfigLine<?> cl : this.lines) {
			output += cl.generateCode();
		}
		return output;
	}
	
	/**
	 * @inheritDoc 
	 */
	@Override
	public String toString() {
		String output = "";
		output = output + "Section: " + this.name + "\n";
		for (ConfigLine<?> cl : lines) {
			output += cl.toString() + "\n";
		}
		/*if (output.charAt(output.length() - 1) == '\n') {
			output = output.substring(0, output.length() - 1);
		}*/
		return output;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lines == null) ? 0 : lines.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigSection) {
			ConfigSection cs_test = (ConfigSection) obj;
			return (cs_test.getLines().equals(this.lines) && cs_test.getName().equals(this.name));
		}
		return false;
	}
	
	
}
