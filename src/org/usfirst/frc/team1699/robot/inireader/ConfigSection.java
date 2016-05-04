/*
 * FIRST Team 1699
 * 
 * @author thatging3rkid, FIRST Team 1699
 * 
 * A class that represents a section of a configuration file.
 * 
 */
package org.usfirst.frc.team1699.robot.inireader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigSection {

	// Initializers
	private String name;
	
	private ArrayList<ConfigLine> lines = new ArrayList<ConfigLine>();
	
	// Constructor
	public ConfigSection(String _name) 
	{
		this.name = _name;
	}

	// Getters and setters
	public String getName() {return this.name;}
	
	
	// Methods
	public void add(ConfigLine line) {lines.add(line);}
	public void add(String _name, Object _value)
	{
		ConfigLine line = new ConfigLine(_name, _value);
		this.add(line);
	}

	private Object findObject(String name)
	{
		// Initializes variables
		int count1 = 0;
		Object result = new Object();
		name = name.toLowerCase();
						
		// Runs through ArrayList
		while (count1 != (lines.size()))
		{
			// Checks for equality to parameter
			if (lines.get(count1).getName().equals(name))
			{
				result = lines.get(count1).getValue();
				break;
			}
		
			// Onward! *coconuts clapping*
			count1 += 1;
		}
				
		return result;
	}
	
	private String cleanString(String s)
	{
		for (int count1 = 0; count1 < s.length(); count1 += 1)
		{
			if ((s.charAt(count1) == '[') || (s.charAt(count1) == ']'))
			{
				String before = "";
				String after = "";
				try {before = s.substring(0, count1);}
				catch (Exception e) {before = "";}
				try {after = s.substring(count1 + 1);}
				catch (Exception e) {after = "";}
				s = before + after;
			}
		}
		return s;
	}
	
	public Integer findInt(String name) {return Integer.parseInt(this.findObject(name).toString());}
	public Double findDouble(String name) {return Double.parseDouble(this.findObject(name).toString());}
	public Boolean findBool(String name) {return Boolean.parseBoolean(this.findObject(name).toString());}
	public String findString(String name) {return this.findObject(name).toString();}
	public Character findChar(String name) {return this.findObject(name).toString().charAt(0);}
	public List<String> findListString(String name) {return Arrays.asList(this.cleanString(this.findObject(name).toString()).split("\\s*,\\s*"));}
	public List<Integer> findListInt(String name)
	{
		List<Integer> out = new ArrayList<Integer>();
		for(String s : this.findListString(name)) 
		{
			s = this.cleanString(s);
			out.add(Integer.parseInt(s));
		}
		return out;
	}
	public List<Double> findListDouble(String name)
	{
		List<Double> out = new ArrayList<Double>();
		for(String s : this.findListString(name))
		{
			s = this.cleanString(s);
			out.add(Double.parseDouble(s));
		}
		return out;
	}
	public List<Boolean> findListBool(String name)
	{
		List<Boolean> out = new ArrayList<Boolean>();
		for(String s : this.findListString(name))
		{
			s = this.cleanString(s);
			out.add(Boolean.parseBoolean(s));
		}
		return out;
	}
	
	// toString Method
	public String toString()
	{
		String output = "";
		output = output + "Section: " + this.name + "\n";
		for(ConfigLine cl : lines)
		{
			output = output + cl.toString() + "\n";
		}
		if (output.charAt(output.length() - 1) == '\n') {output = output.substring(0, output.length() - 1);}
		return output;
	}
}
