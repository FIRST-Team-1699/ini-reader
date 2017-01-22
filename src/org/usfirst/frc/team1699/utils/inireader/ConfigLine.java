/**
 * FIRST Team 1699
 *
 * A class that contains the contents from a line.
 *
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.utils.ConfigLineUtils;

/**
 * Stores data from a configuration file. 
 */
public class ConfigLine<Type> {

	private String name;
	private Type value;
	
	public final boolean editable;
	public final boolean object_mode;

	/**
	 * Creates a ConfigLine with the specified contents
	 * 
	 * @param name the name of the value, what comes before the "="
	 * @param value any value
	 */
	public ConfigLine(String name, Type value) {
		this.name = name;
		this.value = value;
		this.editable = false;
		this.object_mode = false;
	}
	
	/**
	 * Creates a ConfigLine with the specified contents and makes it editable if true.
	 * 
	 * @param name the name of the value, what comes before the "="
	 * @param value any value
	 * @param editable if the ConfigLine should be editable
	 */
	public ConfigLine(String name, Type value, boolean editable) {
		this.name = name;
		this.value = value;
		this.editable = editable;
		this.object_mode = false;
	}
	
	/**
	 * Creates a ConfigLine with the specified contents and makes it editable if true.
	 * 
	 * @param name the name of the value, what comes before the "="
	 * @param value any value
	 * @param editable if the ConfigLine should be editable
	 * @param object_mode if the ConfigLine is a Serialized Object
	 */
	public ConfigLine(String name, Type value, boolean editable, boolean object_mode) {
		this.name = name;
		this.value = value;
		this.editable = editable;
		this.object_mode = object_mode;
	}
	
	/**
	 * Copy constructor for ConfigLine
	 * 
	 * @param line a ConfigLine to copy
	 */
	public ConfigLine(ConfigLine<Type> line) {
		this.name = line.getName();
		this.value = (Type) line.getRawValue();
		this.editable = line.editable;
		this.object_mode = line.object_mode;
	}

	/**
	 * Gets the name of the line
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the value of the line as a generic value
	 * 
	 * @return the value of the line
	 */
	public Type getRawValue() {
		return value;
	}
	
	/**
	 * Gets the value of the line in a safe manner. It checks the class type before returning a value.
	 *  
	 * @param <Check_Type> the type to check against
	 *  
	 * @param class_type the Class of the type
	 * @return the value of this ConfigLine or null if the types do not match
	 */
	public <Check_Type> Type getValue(Class<Check_Type> class_type) {
		if (class_type.equals(this.value.getClass())) {
			return this.value;
		} else {
			return null;
		}
	}
	
	/**
	 * Attempts to generate the code that made this ConfigLine.
	 * 
	 * @return a String with the code that made this ConfigLine
	 */
	public String generateCode() {
		// Puts a % at the front of the line if editable
		String if_editable = "";
		if (this.editable) {
			if_editable = "%";
		}
		
		// If this ConfigLine is in Object mode
		if (this.object_mode) {
			return if_editable + ConfigLineUtils.makeObjectLine(this.name, this.value, this.editable).generateCode();
		}
		
		// If something is a List or ArrayList, then it needs to be changed to use '{' and '}' over '[' and ']'
		if (this.value instanceof List || this.value instanceof ArrayList) {
			String list = this.value.toString();
			
			// Replace the square brackets with curly brackets
			list = list.replace('[', '{');
			list = list.replace(']', '}');
			return if_editable + this.name + " = " + list; 
		} 
		
		// If this ConfigLine is just a value, then return only the value
		if (this.name == null || this.name.trim().equals("")) {
			return if_editable + this.value.toString() + "\n";
		} else {
			return if_editable + this.name + " = " + this.value.toString() + "\n";
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public String toString() {
		if ((this.name == null) || (this.name.trim().equals(""))) {
			return "Line: " + this.value.toString();
		} else {
			return "Name: " + this.name + ", Value: " + this.value.toString();
		}
	}
	
	/**
	 * @inheritDoc
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigLine<?>) {
			ConfigLine<?> cl = (ConfigLine<?>) obj;
			return this.name.equals(cl.getName()) && this.value.equals(cl.getRawValue());
		}
		return false;
	}
}
