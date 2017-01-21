/**
 * FIRST Team 1699
 * 
 * The main class behind the construction of ConfigFiles
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.usfirst.frc.team1699.utils.inireader.ConfigSection;

/**
 * The main class behind the construction of ConfigFiles. 
 * This class takes in lines, and breaks everything out to the smaller Raw classes, then pulls 
 * everything back together again.
 */
public class Parser {

	private static final Map<String, KeywordFunction> keywords;
	static {
		// Makes a map of the possible keywords
		keywords = new HashMap<>();
		keywords.put("", KeywordFunction.EMPTY);
		keywords.put("#", KeywordFunction.COMMENT);
		keywords.put(";", KeywordFunction.COMMENT);
		keywords.put("[", KeywordFunction.SECTION);
		
		// might be added on to later 
	}
	
	private String contents;
	private List<RawSection> r_sections;
	
	/**
	 * Make a ConfigFile Parser
	 * 
	 * @param contents the contents of a file
	 */
	public Parser(String contents) {
		this.contents = contents;
		r_sections = new ArrayList<>();
	}
	
	/**
	 * Parses the String from the constructor
	 */
	public void parse() {
		
		KeywordFunction k_line;
		String section_contents = "";
		
		
		for (String line : this.contents.split("\n")) {
			
			// Remove whitespace at the beginning and end before processing
			line = line.trim();
			
			// See if the line is empty
			if (keywords.get(line) == KeywordFunction.EMPTY) {
				continue;
			}
			
			// Check for keywords at the beginning of the line
			if (keywords.containsKey(line.substring(0, 1))) {
				// Get the KeywordFuntion of this line
				k_line = keywords.get(line.substring(0, 1));
				
				switch(k_line) {
				
				// Case for when the line is empty
				case EMPTY:
					continue;
					
				// Case for when the line is a comment
				case COMMENT:
					continue;
					
				// Case for when the line is a section
				case SECTION:
					// Make a global section if required
					if (this.r_sections.size() == 0) {
						section_contents = "[global]\n" + section_contents;
					}
					
					// Add the previously processed lines to the previous RawSection 
					this.r_sections.add(new RawSection(section_contents.trim()));
					// Start off the next section with it's header (aka line)
					section_contents = line + "\n";
					break;
				
				// Default case
				default:
					section_contents += line + "\n";
					break;				
				}
			// Just a case for normal lines :/
			} else {
				section_contents += line + "\n";
			}
		}
		// Just in case you have no sections
		if (this.r_sections.size() == 0) {
			section_contents = "[global]\n" + section_contents;
		}
		// End of the line, add what's left to a section
		this.r_sections.add(new RawSection(section_contents.trim()));
		
	}
	
	/**
	 * Generates the ConfigSections from the given text
	 * 
	 * @return a List<ConfigSection> made from the given text
	 */
	public List<ConfigSection> getSections() {
		List<ConfigSection> output = new ArrayList<>();
		
		// Have every RawSection build it's full ConfigSection
		for (RawSection rs : this.r_sections) {
			output.add(rs.toConfigSection());
		}
		return output;
	}

}
