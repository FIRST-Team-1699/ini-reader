/**
 * FIRST Team 1699
 * 
 * @author thatging3rkid, FIRST Team 1699
 * 
 *         This class provides a static method for outputting Strings in boxes.
 * 
 *         A full rewrite is coming...
 */
package org.usfirst.frc.team1699.utils.inireader;

public class MessageMaker {

	static void out(String output) {
		// Time to make sure people can make sense of this after I leave...
		System.out.println("|-------------------------------------------------------|");
		System.out.println("| Team 1699 ini-Reader                                  |");

		// Available characters for print
		// "Team 1699 ini-Reader " should match printRoom
		final int printRoom = 52;

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
				notPrinted = notPrinted.substring(47, notPrinted.length());
				System.out.println(printed);
			}
		}

		// Closer.
		System.out.println("|------------------------------------------------------|");
	}
}
