/*
 * FIRST Team 1699
 *
 * @author thatging3rkid, FIRST Team 1699
 *
 * A class that contains the contents from a line.
 *
 */

package org.usfirst.frc.team1699.robot.inireader;

public class ConfigLine {

	// Initializers
	private String name;
	private Object value;

	
	// Constructors
	public ConfigLine()
	{
		this.name = new String();
		this.value = new Object();
	}
	
	public ConfigLine(String _name, Object _value)
	{
		this.name = _name;
		this.value = _value;
	}
	
	
	// Getters and Setters
	public String getName() {return name;}
	public Object getValue() {return value;}
	public void setName(String _name) {this.name = _name;}
	public void setValue(Object _value) {this.value = _value;}
	
	
	// toString() method
	public String toString()
	{
		return "Name: " + this.name + "\nValue: " + this.value.toString();
	}

}
