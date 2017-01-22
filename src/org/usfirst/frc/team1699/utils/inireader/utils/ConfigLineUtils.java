package org.usfirst.frc.team1699.utils.inireader.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;

public class ConfigLineUtils {
	
	public static String ObjectEncoding = "ISO-8859-1";
	public static String EncodedNewline = "nl*"; 
	
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
	
	public static ConfigLine<String> makeObjectLine(String name, Object obj, boolean editable) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		String serialized_obj = "";
		try {
			oos = new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			serialized_obj = bos.toString(ConfigLineUtils.ObjectEncoding); // ISO-8859-1
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ConfigLine<String>(name, "o*" + serialized_obj.replace("\n", ConfigLineUtils.EncodedNewline), editable);
		
		
	}
}
