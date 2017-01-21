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

import org.usfirst.frc.team1699.utils.inireader.ConfigSection;

/**
 * Temporary storage and processing of sections of configuration files
 */
public class RawSection {
	
	private String name = null;
	private List<RawLine> r_lines;
	
	/**
	 * Creates a place to store and process sections in a configuration file
	 * 
	 * @param contents multiple lines from a configuration file
	 */
	public RawSection(String contents) {
		r_lines = new ArrayList<>();
		
		// Make RawLine objects
		for (String line : contents.split("\n")) {
			// make sure that this class has a name
			if (this.name == null) {
				String t;
				t = line.replace('[', ' ');
				t = t.replace(']', ' ');
				this.name = t.trim();
			} else {
				r_lines.add(new RawLine(line));
			}
		}
	}
	
	/**
	 * Generates a ConfigSection
	 * 
	 * @return a ConfigSection filled with data from the given text
	 */
	public ConfigSection toConfigSection() {
		ConfigSection output = new ConfigSection(this.name);
		for (RawLine rl : this.r_lines) {
			output.add(rl.toConfigLine());
		}
		return output;
	}

}
