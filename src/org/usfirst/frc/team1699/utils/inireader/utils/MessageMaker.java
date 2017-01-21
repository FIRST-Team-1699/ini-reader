/**
 * FIRST Team 1699
 * 
 * @author thatging3rkid, FIRST Team 1699
 * 
 *         This class provides a static method for outputting Strings in boxes.
 * 
 *         A full rewrite is coming...
 */
package org.usfirst.frc.team1699.utils.inireader.utils;

import java.io.PrintStream;

public class MessageMaker {

	public static void out(String output) {
		// Time to make sure people can make sense of this after I leave...
		System.out.println("|-------------------------------------------------------|");
		System.out.println("| Team 1699 ini-reader                                  |");

		// Available characters for print
		// "Team 1699 ini-Reader " should match printRoom
		final int printRoom = 53;

		// Some variables
		String printed;
		String notPrinted = output.substring(0, output.length());
		while (notPrinted.length() != 0) {
			// Checks if it can be printed on one line
			if (notPrinted.length() <= printRoom) {
				System.out.print("| " + notPrinted);
				for (int count1 = notPrinted.length(); count1 != printRoom; count1 += 1) {
					System.out.print(" ");
				}
				System.out.print(" |\n");
				break;
			} else { // Break up lines :O
				printed = "| " + notPrinted.substring(0, printRoom) + " |";
				notPrinted = notPrinted.substring(53, notPrinted.length());
				System.out.println(printed);
			}
		}

		// Closer.
		System.out.println("|-------------------------------------------------------|");
	}
	
	public static void out(String output, PrintStream out) {
		// Time to make sure people can make sense of this after I leave...
		out.println("|-------------------------------------------------------|");
		out.println("| Team 1699 ini-reader                                  |");

		// Available characters for print
		// "Team 1699 ini-reader " should match printRoom
		final int printRoom = 53;

		// Some variables
		String printed;
		String notPrinted = output.substring(0, output.length());
		while (notPrinted.length() != 0) {
			// Checks if it can be printed on one line
			if (notPrinted.length() <= printRoom) {
				out.print("| " + notPrinted);
				for (int count1 = notPrinted.length(); count1 != printRoom; count1 += 1) {
					out.print(" ");
				}
				out.print(" |\n");
				break;
			} else { // Break up lines :O
				printed = "| " + notPrinted.substring(0, printRoom) + " |";
				notPrinted = notPrinted.substring(53, notPrinted.length());
				out.println(printed);
			}
		}

		// Closer.
		out.println("|-------------------------------------------------------|");
	}
}
