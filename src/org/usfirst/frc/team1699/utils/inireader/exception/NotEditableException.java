/**
 * FIRST Team 1699
 * 
 * This exception is thrown when an ConfigLine or ConfigSection is not editable, but the user is trying to edit them.
 * 
 * @author thatging3rkid, FIRST Team 1699
 */
package org.usfirst.frc.team1699.utils.inireader.exception;

/**
 * This exception is thrown when an ConfigLine or ConfigSection is not editable, but the user is trying to edit them.
 */
public class NotEditableException extends RuntimeException {

	// Auto-generated Serial ID
	private static final long serialVersionUID = 5720083831277648387L;
	
	// Auto-generated constructors, praise Eclipse
	public NotEditableException() {
		super();
	}

	public NotEditableException(String arg0) {
		super(arg0);
	}

	public NotEditableException(Throwable arg0) {
		super(arg0);
	}

}
