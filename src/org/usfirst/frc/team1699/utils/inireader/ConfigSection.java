/**
 * FIRST Team 1699
 * 
 * A class that represents a section of a configuration file.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Represents a section of a configuration file. It stores multiple ConfigLine objects and has methods to access and process them. 
 */
public class ConfigSection {

	private String name;

	private ArrayList<ConfigLine> lines = new ArrayList<>();

	/**
	 * Creates a ConfigSection with the name provided
	 * 
	 * @param _name  the name of the ConfiSection
	 */
	public ConfigSection(String _name) {
		this.name = _name;
	}

	/**
	 * Gets the name of the ConfigSection
	 * 
	 * @return  name of the ConfigSection
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Add the ConfigLine to the ConfigSection
	 * 
	 * @param line  the ConfigLine to be added
	 */
	public void add(ConfigLine line) {
		lines.add(line);
	}

	/**
	 * Create a ConfigLine and add it to the ConfigSection
	 * 
	 * @param _name  the name for the ConfigLine to be added
	 * @param _value  the value for the ConfigLine to be added
	 */
	public void add(String _name, Object _value) {
		ConfigLine line = new ConfigLine(_name, _value);
		this.add(line);
	}

	/**
	 * Gets the ConfigLine at a specified point. Useful for autonomous. Returns null if the index is out of bounds.
	 * 
	 * @param index  index in the ArrayList to return
	 * @return  the ConfigFile at the specified index, null if it does not exist
	 */
	public ConfigLine getLine(int index) {
		try {
			ConfigLine cla = this.lines.get(index);
			return new ConfigLine(cla.getName(), cla.getValue());
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/**
	 * Get the contents of this ConfigSection. Useful for autonomous. 
	 * 
	 * @return an ArrayList of ConfigLines that make up this ConfigSection
	 */
	public ArrayList<ConfigLine> getContents() {
		if (this.lines == null) {
			return null;
		} else {
			ArrayList<ConfigLine> output = new ArrayList<>();
			for (ConfigLine cl : this.lines) {
				ConfigLine cla = new ConfigLine(cl.getName(), cl.getValue());
				output.add(cla);
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
	 * A private method to find the specified object. In order to access it, you have to use a wrapper method.
	 * 
	 * @param name  the name of the line to look for
	 * @return  the Object of the value if found
	 */
	private Object findObject(String name) {
		// Initializes variables
		int count1 = 0;
		Object result = null;
		name = name.toLowerCase();

		// Runs through ArrayList
		while (count1 != (lines.size())) {
			// Checks for equality to parameter
			if (lines.get(count1).getName().equals(name)) {
				result = lines.get(count1).getValue();
				break;
			}

			// Onward! *coconuts clapping*
			count1 += 1;
		}

		if (result == null) {
			throw new NotFoundException("Name not found: " + name + ".");
		}

		return result;
	}

	/**
	 * Internal method for cleaning Strings
	 * 
	 * @param s  String to be cleaned
	 * @return  the cleaned String
	 */
	private String cleanString(String s) {
		for (int count1 = 0; count1 < s.length(); count1 += 1) {
			if ((s.charAt(count1) == '[') || (s.charAt(count1) == ']')) {
				String before = "";
				String after = "";
				try {
					before = s.substring(0, count1);
				} catch (Exception e) {
					before = "";
				}
				try {
					after = s.substring(count1 + 1);
				} catch (Exception e) {
					after = "";
				}
				s = before + after;
			}
		}
		return s;
	}

	/**
	 * Wraps findObject() for an Integer
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return an Integer representation of the value at name
	 */
	public Integer findInt(String name) {
		return Integer.parseInt(this.findObject(name).toString());
	}

	/**
	 * Wraps findObject() for a Double
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a Double representation of the value at name
	 */
	public Double findDouble(String name) {
		return Double.parseDouble(this.findObject(name).toString());
	}

	/**
	 * Wraps findObject() for a Boolean
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a Boolean representation of the value at name
	 */
	public Boolean findBool(String name) {
		return Boolean.parseBoolean(this.findObject(name).toString());
	}

	/**
	 * Wraps findObject() for a String
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a String representation of the value at name
	 */
	public String findString(String name) {
		return this.findObject(name).toString();
	}

	/**
	 * Wraps findObject() for a Character
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a Character representation of the value at name
	 */
	public Character findChar(String name) {
		return this.findObject(name).toString().charAt(0);
	}

	/**
	 * Wraps findObject() for a {@literal List<String>}
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a {@literal List<String>} representation of the value at name
	 */
	public List<String> findListString(String name) {
		return Arrays.asList(this.cleanString(this.findObject(name).toString()).split("\\s*,\\s*"));
	}

	/**
	 * Wraps findObject() for a {@literal List<Integer>}
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a {@literal List<Integer>} representation of the value at name
	 */
	public List<Integer> findListInt(String name) {
		List<Integer> out = new ArrayList<Integer>();
		for (String s : this.findListString(name)) {
			s = this.cleanString(s);
			out.add(Integer.parseInt(s));
		}
		return out;
	}

	/**
	 * Wraps findObject() for a {@literal List<Double>}
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a {@literal List<Double>} representation of the value at name
	 */
	public List<Double> findListDouble(String name) {
		List<Double> out = new ArrayList<Double>();
		for (String s : this.findListString(name)) {
			s = this.cleanString(s);
			out.add(Double.parseDouble(s));
		}
		return out;
	}

	/**
	 * Wraps findObject() for a {@literal List<Boolean>}
	 * 
	 * @param name name of the ConfigLine to look for
	 * @return a {@literal List<Boolean>} representation of the value at name
	 */
	public List<Boolean> findListBool(String name) {
		List<Boolean> out = new ArrayList<Boolean>();
		for (String s : this.findListString(name)) {
			s = this.cleanString(s);
			out.add(Boolean.parseBoolean(s));
		}
		return out;
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
		for (ConfigLine cl : lines) {
			output = output + cl.toString() + "\n";
		}
		if (output.charAt(output.length() - 1) == '\n') {
			output = output.substring(0, output.length() - 1);
		}
		return output;
	}
}
