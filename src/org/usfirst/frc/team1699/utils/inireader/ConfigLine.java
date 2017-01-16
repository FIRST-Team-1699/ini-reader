/**
 * FIRST Team 1699
 *
 * A class that contains the contents from a line.
 *
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader;

/**
 * Stores data from a configuration file. 
 */
public class ConfigLine<Type> {

	private String name;
	private Type value;

	/**
	 * Creates a ConfigLine with the specified contents
	 * 
	 * @param name the name of the value, what comes before the "="
	 * @param value an Object that can be any value
	 */
	public ConfigLine(String name, Type value) {
		this.name = name;
		this.value = value;
	}
	
	public ConfigLine(ConfigLine<Type> line) {
		this.name = line.getName();
		this.value = (Type) line.getRawValue();
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
