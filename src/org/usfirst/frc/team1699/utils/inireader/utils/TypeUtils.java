package org.usfirst.frc.team1699.utils.inireader.utils;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team1699.utils.inireader.ConfigLine;

public class TypeUtils {
	
	/**
	 * Generates a ConfigLine<List<String>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<String>> makeCLListString(String name, String line) {
		List<String> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim());	
		}
		return new ConfigLine<List<String>>(name, out);
	}
	
	/**
	 * Generates a ConfigLine<List<Integer>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Integer>> makeCLListInteger(String name, String line) {
		List<Integer> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Integer.parseInt(s.trim()));
		}
		return new ConfigLine<List<Integer>>(name, out);
	}
	
	/**
	 * Generates a ConfigLine<List<Double>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Double>> makeCLListDouble(String name, String line) {
		List<Double> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Double.parseDouble(s.trim()));
		}
		return new ConfigLine<List<Double>>(name, out);
	}
	
	/**
	 * Generates a ConfigLine<List<Boolean>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Boolean>> makeCLListBoolean(String name, String line) {
		List<Boolean> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(Boolean.parseBoolean(s.trim()));
		}
		return new ConfigLine<List<Boolean>>(name, out);
	}
	
	/**
	 * Generates a ConfigLine<List<Character>>
	 * 
	 * @param name name of the ConfigLine
	 * @param line value of the ConfigLine
	 * @return a ConfigLine made from the given line
	 */
	public static ConfigLine<List<Character>> makeCLListCharacter(String name, String line) {
		List<Character> out = new ArrayList<>();
		for (String s : line.split(",")) {
			out.add(s.trim().charAt(0));	
		}
		return new ConfigLine<List<Character>>(name, out);
	}	
}
