/**
 * FIRST Team 1699
 * 
 * A class that represents a section of a configuration file.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents a section of a configuration file. It stores multiple ConfigLine objects and has methods to access and process them. 
 */
public class ConfigSection {

	private String name;

	private ArrayList<ConfigLine<?>> lines = new ArrayList<>();

	/**
	 * Creates a ConfigSection with the name provided
	 * 
	 * @param name the name of the ConfiSection
	 */
	public ConfigSection(String name) {
		this.name = name;
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
		lines.add(line);
	}

	/**
	 * Create a ConfigLine and add it to the ConfigSection
	 * 
	 * @param <T> the type of the new ConfigLine
	 * 
	 * @param name  the name for the ConfigLine to be added
	 * @param value  the value for the ConfigLine to be added
	 */
	public <T> void add(String name, T value) {
		ConfigLine<T> line = new ConfigLine<T>(name, value);
		this.add(line);
	}

	/**
	 * Gets the ConfigLine at a specified point. Useful for autonomous. Returns null if the index is out of bounds.
	 * 
	 * @param index index in the ArrayList to return
	 * @return the ConfigFile at the specified index, null if it does not exist
	 */
	public ConfigLine<?> getLine(int index) {
		try {
			return new ConfigLine<>(this.lines.get(index));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Get a ConfigLine based on the provided name. Returns the first ConfigLine with that name.
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @return the ConfigLine that matches the specified name, null if it does not exist
	 */
	public ConfigLine<?> getLine(String name) {
		for (ConfigLine<?> cl : lines) {
			if (cl.getName().equals(name)) {
				return new ConfigLine<>(cl);
			}
		}
		throw new NotFoundException("Line not found " + name + " .");
	}
	
	/**
	 * Get the contents of this ConfigSection. Useful for autonomous. 
	 * 
	 * @return an ArrayList of ConfigLines that make up this ConfigSection
	 */
	public List<ConfigLine<?>> getLines() {
		if (this.lines == null) {
			return null;
		} else {
			ArrayList<ConfigLine<?>> output = new ArrayList<>();
			for (ConfigLine<?> cl : this.lines) {
				ConfigLine<?> cla = new ConfigLine<>(cl);
				output.add(cla);
			}
			return output;
		}
	}
	
	/**
	 * Gets the value of a ConfigLine without using a dot operator! Cool!
	 * 
	 * @param <L> the type to check against
	 * 
	 * @param name the name of the ConfigLine to look for
	 * @param class_type the Class of the type
	 * @return the the value of this ConfigLine or null if the types do not match
	 */
	@SuppressWarnings("unchecked") // it is checked so...
	public <L> L getLineValue(String name, Class<L> class_type) {
		ConfigLine<?> out = this.getLine(name);
		if (out.getRawValue().getClass().equals(class_type)) {
			return (L) out.getRawValue(); 
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
			ArrayList<String> output = new ArrayList<>();
			for (ConfigLine<?> cl : this.lines) {
				try {
					output.add((String) cl.getRawValue());
				} catch (ClassCastException e) {
					System.err.println("Error casting on line: " + cl.toString());
				}
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
	 * Generates a String representing this ConfigSection
	 * 
	 * @return a String representing this ConfigSection 
	 */
	@Override
	public String toString() {
		String output = "";
		output = output + "Section: " + this.name + "\n";
		for (ConfigLine<?> cl : lines) {
			output = output + cl.toString() + "\n";
		}
		if (output.charAt(output.length() - 1) == '\n') {
			output = output.substring(0, output.length() - 1);
		}
		return output;
	}
}
