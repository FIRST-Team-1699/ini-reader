package org.usfirst.frc.team1699.utils.inireader.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;

public class ConfigLineUtils {
	
	/**
	 * What objects in line are encoded in
	 */
	public static String InLineObjectEncoding = "ISO-8859-1";
	
	/**
	 * What in file new-lines are replaced with
	 */
	public static String EncodedNewline = "nl*"; 
	
	/**
	 * What object in external files are encoded in
	 */
	public static String ExternalObjectEncoding = "UTF-16";
	
	/**
	 * Identifier for a static location, ie. /home/lvuser/obj-data.test.dat
	 */
	private static String StaticLocation = "s*";
	
	/**
	 * Identifier for a dynamic location (based on the location of the ConfigFile), ie. obj-data.test.dat
	 */
	private static String DynamicLocation = "i*";
	
	
	/**
	 * Generates a ConfigLine<List<String>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<String>> makeListString(String name, String line) {
		List<String> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim());	
		}
		return new ConfigLine<List<String>>(name.trim(), out);
	}
	
	/**
	 * Generates a ConfigLine<List<Integer>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Integer>> makeListInteger(String name, String line) {
		List<Integer> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Integer.parseInt(s.trim()));
		}
		return new ConfigLine<List<Integer>>(name.trim(), out);
	}
	
	/**
	 * Generates a ConfigLine<List<Double>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Double>> makeListDouble(String name, String line) {
		List<Double> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Double.parseDouble(s.trim()));
		}
		return new ConfigLine<List<Double>>(name.trim(), out);
	}
	
	/**
	 * Generates a ConfigLine<List<Boolean>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Boolean>> makeListBoolean(String name, String line) {
		List<Boolean> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Boolean.parseBoolean(s.trim()));
		}
		return new ConfigLine<List<Boolean>>(name.trim(), out);
	}
	
	/**
	 * Generates a ConfigLine<List<Character>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Character>> makeListCharacter(String name, String line) {
		List<Character> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim().charAt(0));	
		}
		return new ConfigLine<List<Character>>(name.trim(), out);
	}
	
	/**
	 * Generates a ConfigLine<List<String>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<String>> makeListString(String name, String line, boolean editable) {
		List<String> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim());	
		}
		return new ConfigLine<List<String>>(name.trim(), out, editable);
	}
	
	/**
	 * Generates a ConfigLine<List<Integer>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Integer>> makeListInteger(String name, String line, boolean editable) {
		List<Integer> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Integer.parseInt(s.trim()));
		}
		return new ConfigLine<List<Integer>>(name.trim(), out, editable);
	}
	
	/**
	 * Generates a ConfigLine<List<Double>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Double>> makeListDouble(String name, String line, boolean editable) {
		List<Double> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Double.parseDouble(s.trim()));
		}
		return new ConfigLine<List<Double>>(name.trim(), out, editable);
	}
	
	/**
	 * Generates a ConfigLine<List<Boolean>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Boolean>> makeListBoolean(String name, String line, boolean editable) {
		List<Boolean> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Boolean.parseBoolean(s.trim()));
		}
		return new ConfigLine<List<Boolean>>(name.trim(), out, editable);
	}
	
	/**
	 * Generates a ConfigLine<List<Character>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Character>> makeListCharacter(String name, String line, boolean editable) {
		List<Character> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim().charAt(0));	
		}
		return new ConfigLine<List<Character>>(name.trim(), out, editable);
	}
	
	/**
	 * Generates a ConfigLine that contains a serialized object
	 * 
	 * @param name name of the ConfigLine
	 * @param obj the object to serialize
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine that contains a serialized object
	 */
	public static ConfigLine<String> makeSerializedObject(String name, Object obj, boolean editable) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		String serialized_obj = "";
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			serialized_obj = bos.toString(ConfigLineUtils.InLineObjectEncoding);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ConfigLine<String>(name, "o*" + serialized_obj.replace("\n", ConfigLineUtils.EncodedNewline), editable);
	}
	
	/**
	 * Generates a ConfigLine<Object> from a serialized object
	 * 
	 * @param name name of the ConfigLine
	 * @param text the text that contains the serialized object
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from a read-in serialized object
	 */
	public static ConfigLine<Object> readSerializedObject(String name, String text, boolean editable) {
		// Removes the "o*" header (and removes custom encoding)
		String ser_object = text.substring(2).replace(ConfigLineUtils.EncodedNewline, "\n");
		
		try {
			// Reads in the object as an object, then returns it as a ConfigLine<Object>
			// The Java compiler will automatically figure out the type, so no casting is required.
			byte[] ba = ser_object.getBytes(ConfigLineUtils.InLineObjectEncoding);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ba));
			return new ConfigLine<Object>(name, ois.readObject(), editable, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Error out
		System.err.println("Error reading in object with name " + name);
		return new ConfigLine<Object>(name, null);
	}
	
	/**
	 * Reads in a serialized object from the specified file
	 * 
	 * @param name name of the ConfigLine
	 * @param pointer the location of the file containing the serialized object
	 * @param editable if the ConfigLine should be editable
	 * @param config_location the location of the ConfigFile, used for dynamic locations
	 * @return a ConfigLine made from the read-in serialized object
	 */
	public static ConfigLine<Object> readSerializedObjectFromFile(String name, String pointer, 
			boolean editable, File config_location) {
		// Removes the file header
		pointer = pointer.replace(StringUtils.FilePointerHeader, " ");
		pointer = pointer.trim();
		
		// Determines the location type (dynamic vs static)
		String location_type = pointer.substring(0, 2);
		File location = null;
		
		// Processes the pointer depending on the location type
		if (location_type.equals(ConfigLineUtils.StaticLocation)) {
			location = new File(pointer.substring(2));
		} else if (location_type.equals(ConfigLineUtils.DynamicLocation)) {
			File temp = new File(config_location.getAbsolutePath());
			location = new File(temp.getParent() + File.separator + pointer.substring(2));
		} else {
			System.err.println("Error reading file location header: " + location_type + "on line " + pointer);
			return null;
		}
		
		// Read the file from the disk, specify the encoding, then read the object from that data
		try {
			String stuff = new String(Files.readAllBytes(Paths.get(location.getAbsolutePath())));
			byte[] ba = stuff.getBytes(ConfigLineUtils.ExternalObjectEncoding);
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ba));
			return new ConfigLine<Object>(name, ois.readObject(), editable, true);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Houston, we have a problem...
		System.err.println("Error reading in object with name " + name);
		return new ConfigLine<Object>(name, null);
		
	}
}
