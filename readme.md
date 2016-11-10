# ini-reader

Our .ini reader, used to read values in to the code and to read autonomous code.

Archived versions of iniReader are in `2015-archive` and `2016-archive`. `master` needs to be tested before it should be used in projects.

# Usage

This version of ini-reader sticks to the "definition" for ini files. The syntax can be found [here](https://en.wikipedia.org/wiki/INI_file).

### Starting

Instantiate `ConfigFile`, has three constructors:
- `ConfigFile()`, which looks for a file at `/home/lvuser/1699-config.ini`
- `ConfigFile(String name)`, which looks for a file at `<name>` (see FAQ for suggested dirs on a roboRIO)
- `ConfigFile(File file)`, which looks for the file specified in `<file>`

### File Setup

Because this version of the ini-reader conforms to ini syntax, mentioned above, the file setup is different then previous versions.

First off, only **one** file is used (in convention; there are no restrictions in the code though). In order to accommodate for different subsystems or autonomous modes, sections are used. Just like the defined syntax, a section is defined by `[section]`.

Below a section header (and until the next header), is a group of configuration lines, where `name = value`. Names are **not** case-sensitive. Values can be integers, doubles, strings, booleans, a character, or a list of integers, doubles, booleans, characters, or strings. See `example.ini` for an example.

### Getting Values

After instantiating a `ConfigFile`, use the `getSection(String name)` method to get access a `ConfigSection`, which contains all the configuration lines for the section named.

From the `ConfigSection`, use the `getLine(String name)` method to get access to the a `ConfigLine`. After this, use the `getValue(Class<K> class_type)` (where class_type is a reference to the future variable's type. See the example to see how to get the class of an object) to get the value of the ConfigLine. Because of the dot operator in Java, all this can (and should) be done on one line. You now have your value!

#### Safe Casting

A quick definition: a generic is an type that is set at run-time. A good example of this is the `List<K>` interface. The type K can be changed based on the situation.

The ConfigLine now uses a generic for it's value of the line. It is automatically assigned when the file is read based on a couple parameters. For example, if something starts and ends with brackets, it must be a list. We use assumptions like this to narrow down the type and assign it. But, returning the value back to the Robot class can be a little bit of a problem. Because we do not specifically define the type we are using (we usually define the type by doing `List<Integer>` and now Eclipse knows that we are dealing with Integers before executing the code, and makes sure that we handle it correctly i.e if we get a value from the list, it must be assigned to an Integer) and it varies between runs, Eclipse and the pre-compiler cannot make sure that types match before runtime. If the types do not match at runtime, i.e a variable is an Integer and the ConfigLine is returning a Boolean, then a ClassCastException will be thrown and the code will crash. 

By using a safe cast method, the type is checked before attempting a cast, ensuring that a ClassCastException will not be thrown. 

tl;dr **do not use the** `getRawValue()` **method. Get the class by using** `Class.class` **or** `Object.getClass()` **.**

# FAQ

Can I have two values on one line? *Kind of. The first `=` will always be used to split the line into name and value. However, if you use `\=`, you can have names and values on a single line, but you must process it yourself. You could also use an Array if you want two (or more) values under a single name.*

What directories can the file be put in on a roboRIO? *We use `/home/lvuser` because that is the working directory for robot code, but it can theoretically go anywhere, as long as it won't overwrite another file and won't be deleted (and has the correct permissions). I highly recommend using `/home/lvuser`, unless you know a better place.*

Why did you make this more complicated? *If the previous version worked well for you, go use it, it's under `2016-archive`. But, we were having a problem where we have 10+ ini files on our RIO, and if we wanted more autonomous modes, we needed more files. This allows us to have one file, and a lot less clutter.*

# One Last Note

Please report any bugs, even if you could tell me in person. Most bugs can be patched rather easily and quickly.

And please request more things to put in this tutorial. 
