/**
 * FIRST Team 1699
 * 
 * This exception is used to tell the user that their item was not found.
 * 
 * This is a checked exception, so the user should provide a backup to be used when this exception is thrown.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.exception;

/**
 * This exception is thrown when the object you are looking for is not found.
 */
public class NotFoundException extends Exception {

	// Auto-generated Serial ID
	private static final long serialVersionUID = 2272833937598728478L;

	// Auto-generated constructors, praise Eclipse
	public NotFoundException() {
		super();
	}

	public NotFoundException(String arg0) {
		super(arg0);
	}

	public NotFoundException(Throwable arg0) {
		super(arg0);
	}
}
