/**
 * FIRST Team 1699
 * 
 * Temporary storage and processing of sections of configuration files.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.parser;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;
import org.usfirst.frc.team1699.utils.inireader.ConfigSection;

/**
 * Temporary storage and processing of sections of configuration files
 */
public class RawSection {
	
	private String name = null;
	private List<RawLine> r_lines;
	private boolean editable = false;
	
	/**
	 * Creates a place to store and process sections in a configuration file
	 * 
	 * @param contents multiple lines from a configuration file
	 */
	public RawSection(String name, List<RawLine> r_lines) {
		this.name = name;
		this.r_lines = r_lines;
	}
	
	/**
	 * Creates a place to store and process sections in a configuration file
	 * 
	 * @param contents multiple lines from a configuration file
	 * @param editable if the ConfigSection should be editable
	 */
	public RawSection(String name, List<RawLine> r_lines, boolean editable) {
		this.name = name;
		this.r_lines = r_lines;
		this.editable = editable;
	}
	
	/**
	 * Set the editable value that is used to construct the ConfigSection
	 * 
	 * @param is_editable the new value for editable
	 */
	public void setEditable(boolean is_editable) {
		this.editable = is_editable;
	}
	
	/**
	 * Generates a ConfigSection
	 * 
	 * @return a ConfigSection filled with data from the given text
	 */
	public ConfigSection toConfigSection() {
		// Convert the RawLines to ConfigLines before constructing the ConfigSection
		List<ConfigLine<?>> lines = new ArrayList<>();
		for (RawLine rl : this.r_lines) {
			lines.add(rl.toConfigLine());
		}
		
		return new ConfigSection(this.name, lines, editable);
	}

}
