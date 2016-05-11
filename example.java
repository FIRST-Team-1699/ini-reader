public class example
{
    public void static main(String[] args)
    {
        // Make a ConfigFile object
        ConfigFile file = new ConfigFile(new File("example.ini"));

        // Finds the sections in the file
        ConfigSection global = file.findSection("global");
        ConfigSection teleop = file.findSection("teleop");
        ConfigSection autonomous = file.findSection("autonomous");

        // Raises a NotFoundException
        ConfigSection init = file.findSection("init");

        // Find the value in the section
        int value0 = global.findInteger("value0"); // 3
        double value1 = global.findDouble("value1"); // 3.1415
        double value2 = global.findDouble("value2"); // 2.7182

        String value3 = telop.findString("value3"); // number
        boolean value4 = telop.findBoolean("value4"); // true
        char value5 = telop.findChar("value5"); // g

        List<Integer> value6 = autonomous.findListInt("value6"); // [1, 2, 3, 4]
        List<Double> value7 = autonomous.findListDouble("value7"); // [5.5, 6.6, 7.7, 8.8]

    }
}
