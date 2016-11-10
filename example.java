/**
 * An example of how to use the ConfigFile reader
 */
import java.util.ArrayList;

import org.usfirst.frc.team1699.utils.inireader.ConfigFile;
import org.usfirst.frc.team1699.utils.inireader.ConfigLine;
import org.usfirst.frc.team1699.utils.inireader.ConfigSection;

public class example
{
    public static void main(String[] args) {
        // Make a ConfigFile object
        ConfigFile file = new ConfigFile(new File("example.ini"));

        // Finds the sections in the file
        ConfigSection global = file.getSection("global");
        ConfigSection teleop = file.getSection("teleop");
        ConfigSection autonomous1 = file.getSection("autonomous1");
        ConfigSection autonomousGlobal = file.getSection("autonomousGlobal");

        // Raises a NotFoundException
        ConfigSection init = file.getSection("init");

        // Find the value in the section
        
        // These lines get the value the long way
        ConfigLine<Integer> temp_value0 = global.getLine("value1");
        int value0 = temp_value0.getValue(Integer.class); // 3 
        int value0 = (int) temp_value0.getRawValue(); // This works, but this is not a safe cast. Don't use it ever. See the readme.
        
        // A little shorter...
        double value1 = global.getLine("value1").getValue(Double.class); // 3.1415
        
        // The quick way to get values (with a saft cast)
        double value2 = global.getLineValue("value2", Double.class); // 2.7182

        // Now, data is in the teleop section
        String value3 = teleop.getLineValue("value3", String.class); // number
        boolean value4 = teleop.getLineValue("value4", Boolean.class); // true
        char value5 = teleop.getLineValue("value5", Character.class); // g

        // This is a small section of how to process custom commands in the ConfigFile
        for (String command : autonomous1.getStringValues()) {
        	// command will be:
        	// "drive(1)"
        	// "turn(90)"
        	// "stopEncoder(80)"
        	// "customCommand("1=6")"
        }
        
        // Finally, the autonomous globals section
        ArrayList<Double> value6 = autonomousGlobal.getLineValue("value6", ArrayList.class); // [10.4, 42.6, 32.7, -4.0]
        ArrayList<Boolean> value7 = autonomousGlobal.getLineValue("value7", value7.getClass()); // [true, true, false, true]
        // Note that the Type.class and Object.getClass() are interchangable
    }
}
