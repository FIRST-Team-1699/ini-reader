/**
 * FIRST Team 1699
 * 
 * The main class behind the construction of ConfigFiles
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.parser;

import java.io.File;
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
		keywords.put("",  KeywordFunction.EMPTY);
		keywords.put(" ", KeywordFunction.EMPTY);
		keywords.put("#", KeywordFunction.COMMENT);
		keywords.put(";", KeywordFunction.COMMENT);
		keywords.put("[", KeywordFunction.SECTION);
		keywords.put("%", KeywordFunction.EDITABLE);
		keywords.put(":", KeywordFunction.PREPROCESSED);
	}
	
	private String contents;
	private List<RawSection> r_sections;
	private File config_location = null;
	
	/**
	 * Make a ConfigFile Parser
	 * 
	 * @param contents the contents of a file
	 */
	public Parser(String contents, File config_location) {
		this.contents = contents;
		this.r_sections = new ArrayList<>();
		this.config_location = config_location;
	}
	
	/**
	 * Parses the String from the constructor
	 */
	public void parse() {
		String section_name = "global"; // starts off with a global section
		boolean section_editable = false;
		List<RawLine> stack_lines = new ArrayList<>();
		
		KeywordFunction k_line;
		
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
					// Skip the line.
					continue;
					
				// Case for when the line is a comment
				case COMMENT:
					// Skip the line.
					// At some point, if comment reconstruction needs to be done, then this is here.
					continue;
				
				// Case for when the line is a preprocessor/editor line
				case PREPROCESSED:
					// Skip the line
					continue;
				
				// Case for when the line is editable
				case EDITABLE:
					// If this is an editable section
					if (keywords.get(line.substring(1, 2)) == KeywordFunction.SECTION) {
						// Add this RawSection to the completed RawSections
						this.r_sections.add(new RawSection(section_name, stack_lines, section_editable));
						
						// Start off the next section with it's name, and strip off all the )
						section_name = line.replace('[', ' ');
						section_name = section_name.replace(']', ' ');
						section_name = section_name.replace('%', ' ');
						section_name = section_name.trim();
						
						// Reset the lines in the section and set the next section as editable
						stack_lines = new ArrayList<>(); 
						section_editable = true;
					// If not, this must be an editable line
					} else {
						// Add the new line with the header removed
						stack_lines.add(new RawLine(line.substring(1), true, this.config_location));
					}
					break;
					
					
				// Case for when the line is a section
				case SECTION:
					// Add this RawSection to the completed RawSections
					this.r_sections.add(new RawSection(section_name, stack_lines, section_editable));
					
					// Start off the next section with it's header (aka line)
					section_name = line.replace('[', ' ');
					section_name = section_name.replace(']', ' ');
					section_name = section_name.trim();
					
					// Reset the lines in the section
					stack_lines = new ArrayList<>();
					// An editable section will never be in here, it will be in the editable case
					// With that in mind, we know that this section is not editable.
					section_editable = false;
					break;
					
				// Default case
				default:
					stack_lines.add(new RawLine(line, false, this.config_location));
					break;				
				}
			// Just a case for normal lines :/
			} else {
				stack_lines.add(new RawLine(line, this.config_location));
			}
		}
		// Add the lines at the end of a file that don't have a section below them
		this.r_sections.add(new RawSection(section_name, stack_lines, section_editable));	
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
