package org.usfirst.frc.team1699.robot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class iniReader
{
	ArrayList<Double> iniContents = new ArrayList<Double>();
	BufferedReader reader = null;
	
	public void getFile(String file){
		File iniFile = new File("/home/lvuser/" + file);
		try 
		{
			reader = new BufferedReader(new FileReader (iniFile));
			String line = reader.readLine();
			while (line != null) {iniContents.add(Double.parseDouble(line));}
		} 
		catch (FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
		finally {
			try
			{
				if (reader != null) {reader.close();}
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}