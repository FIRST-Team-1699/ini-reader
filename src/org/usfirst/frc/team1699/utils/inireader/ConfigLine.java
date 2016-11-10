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
public class ConfigLine<T> {

	private String name;
	private T value;

	/**
	 * Creates a ConfigLine with the specified contents
	 * 
	 * @param name the name of the value, what comes before the "="
	 * @param value an Object that can be any value
	 */
	public ConfigLine(String name, T value) {
		this.name = name;
		this.value = value;
	}
	
	public ConfigLine(ConfigLine<T> line) {
		this.name = line.getName();
		this.value = (T) line.getRawValue();
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
	public T getRawValue() {
		return value;
	}
	
	/**
	 * Gets the value of the line in a safe manner. It checks the class type before returning a value.
	 *  
	 * @param <L> the type to check against
	 *  
	 * @param class_type the Class of the type
	 * @return the value of this ConfigLine or null if the types do not match
	 */
	public <L> T getValue(Class<L> class_type) {
		if (class_type.equals(this.value.getClass())) {
			return this.value;
		} else {
			return null;
		}
	}

	/**
	 * Creates a String that represents the line. For debugging or writing to a file.
	 * 
	 * @return a String that represents the line
	 */
	@Override
	public String toString() {
		if ((this.name == null) || (this.name.trim().equals(""))) {
			return "Value: " + this.value.toString();
		} else {
			return "Name: " + this.name + ", Value: " + this.value.toString();
		}
	}
	
	/**
	 * Generates a hash code for this object, used for hashed data sets.
	 * 
	 * @return the hash code of this object
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
	 * Tells if two objects are equal
	 * 
	 * @param obj the object to compare to 
	 * @return true if the two objects are equal to each other
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConfigLine<?>) {
			ConfigLine<?> cl = (ConfigLine<?>) obj;
			return this.name.equals(cl.getName()) && this.value.equals(cl.getRawValue());
		} else {
			return false;
		}
	}
}
