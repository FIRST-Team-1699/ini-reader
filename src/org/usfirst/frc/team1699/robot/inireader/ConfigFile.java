/*
 * FIRST Team 1699
 *
 * @author thatging3rkid, FIRST Team 1699
 *
 * A class that represents a file containing configuration info.
 * 
 * v2.0-alpha, released on 5/18/2016
 *
 */
package org.usfirst.frc.team1699.robot.inireader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;

public class ConfigFile {

	// Initializers
	File file;
	BufferedReader reader;
    ArrayList<ConfigSection> sections = new ArrayList<ConfigSection>();


    // Constructors
	public ConfigFile()
	{
		file = new File("/home/lvuser/1699-config.ini");
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath() + " (default)");
        this.readFile();
    }

	public ConfigFile(String fullPath)
	{
		file = new File(fullPath);
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath());
        this.readFile();
    }

	public ConfigFile(File _file)
	{
		this.file = _file;
		MessageMaker.out("Initalized with file: " + file.getAbsolutePath());
        this.readFile();
    }


    // Methods
    public void readFile()
    {
        ConfigSection section = new ConfigSection("global");
        this.sections.add(section);

        try
        {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null)
            {
                // Checks for blank line
                if ((line.equals("")) || (line.equals(" ")) || (line.equals("\n")) || (line.equals("\r\n")))
				{
					line = reader.readLine();
					continue;
				}

                // Checks for comment
				if ((line.substring(0, 1).equals(";")) || (line.substring(0, 1).equals("#")))
				{
					line = reader.readLine();
					continue;
				}

                // Checks for new section
                if (line.substring(0, 1).equals("["))
                {
                    int count1 = 0;
                    char indexCh;
                    while (count1 != line.length())
                    {
                    	indexCh = line.charAt(count1);
                    	
                    	if (indexCh == ']')
                    	{
                    		break;
                    	}
                    	
                    	count1 += 1;
                    }
                    section = new ConfigSection("" + line.substring(1, count1));
                    this.sections.add(section);
                    line = reader.readLine();
                    continue;
                }

                // Starts looking for values in the line
				int count1 = 0;
				char indexCh;
				String section1 = new String();
				String section2 = new String();
				while (count1 != line.length())
				{
					indexCh = line.charAt(count1);
					if (indexCh == '=')
					{
						// Gets the previous character
						indexCh = line.charAt(count1 - 1);

                        // Check for escape sequence
                        if (indexCh == '\\')
                        {
                            line = line.substring(0, count1 - 2) + line.substring(count1 - 1, line.length());
                            continue;
                        }
                        // Check for space
                        else if (indexCh == ' ') {section1 = line.substring(0, count1 - 1);}
						else {section1 = line.substring(0, count1);}

                        // Gets the next character
						indexCh = line.charAt(count1 + 1);
                        // Checks for space
						if (indexCh == ' ') {section2 = line.substring(count1 + 2, line.length());}
						else {section2 = line.substring(count1 + 1, line.length());}

                        // All done!
                        break;
					}
					count1 += 1;
                }

                // <insert witty joke here>
                ConfigLine cld = new ConfigLine(section1.toLowerCase(), (Object) section2);
                section.add(cld);

                // Read the next line
                line = reader.readLine();
            }
        }
        // Catches, exceptions, and debugging
        catch (FileNotFoundException e) {MessageMaker.out("File not found. Probably crashing about now.");}
		catch (IOException e) {e.printStackTrace();}
		finally {
			try {
				// Closes file
				if (reader != null) {reader.close();}
			} 
			catch (IOException e) {e.printStackTrace();}
			}
        
    }
    
	public ConfigSection findSection(String name) 
    {
    	// Cycle through the ArrayList, return ConfigSection if names are equal
		for (ConfigSection cs : sections)
		{
			if (cs.getName().equals(name)) {return cs;}
		}
    	// Throw exception if not found
		throw new NotFoundException("Section not found: " + name + ".");
    }
    
    
    // Testing method
    protected void dump()
    {
    	for (ConfigSection cs : sections)
    	{
    		System.out.println(cs.toString());
    	}
    	
    }


}
