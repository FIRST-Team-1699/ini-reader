package org.usfirst.frc.team1699.utils.inireader.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;

public class ConfigLineUtils {	
	
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
	public static ConfigLine<byte[]> makeSerializedObject(String name, Object obj, boolean editable) {
		// Try to write the object to a byte array stream, which can then be converted into a String
		try (
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
		){
			oos.writeObject(obj);
			oos.flush();
			return new ConfigLine<byte[]>(name, bos.toByteArray(), editable);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Error out
		System.err.println("Error making object with name " + name);
		return new ConfigLine<byte[]>(name, null, editable);
	}
	
	/**
	 * Generates a ConfigLine<Object> from a serialized object
	 * 
	 * @param name name of the ConfigLine
	 * @param ser_obj the byte array that contains the serialized object
	 * @param editable if the ConfigLine should be editable
	 * @return a ConfigLine made from a read-in serialized object
	 */
	public static ConfigLine<Object> readSerializedObject(String name, byte[] ser_obj, boolean editable) {
		// Try to read an object out of the byte array
		try (
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(ser_obj));
		){
			return new ConfigLine<Object>(name, ois.readObject(), editable, true);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Error out
		System.err.println("Error reading in object with name " + name);
		return new ConfigLine<Object>(name, null, editable);
	}	
	
}
