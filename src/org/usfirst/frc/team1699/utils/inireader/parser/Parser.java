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
		keywords.put("",  KeywordFunction.EMPTY);
		keywords.put(" ", KeywordFunction.EMPTY);
		keywords.put("#", KeywordFunction.COMMENT);
		keywords.put(";", KeywordFunction.COMMENT);
		keywords.put("[", KeywordFunction.SECTION);
		keywords.put("%", KeywordFunction.EDITABLE);
		// new first character keywords can be added here
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
				
				// Case for when the line is editable
				case EDITABLE:
					// If this is an editable section
					if (keywords.get(line.substring(1, 2)) == KeywordFunction.SECTION) {
						// If the stack of lines is 0, then skip the section header
						if (stack_lines.size() == 0) {
							continue;
						} else {
							// If not, then add this RawSection to the completed RawSections
							this.r_sections.add(new RawSection(section_name, stack_lines, section_editable));
						}
						
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
						stack_lines.add(new RawLine(line.substring(1), true));
					}
					break;
					
					
				// Case for when the line is a section
				case SECTION:
					// If the stack of lines is 0, then skip the section header
					if (stack_lines.size() == 0) {
						continue;
					} else { 
						// If not, then add this RawSection to the completed RawSections
						this.r_sections.add(new RawSection(section_name, stack_lines, section_editable));
					}
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
					stack_lines.add(new RawLine(line));
					break;				
				}
			// Just a case for normal lines :/
			} else {
				stack_lines.add(new RawLine(line));
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
