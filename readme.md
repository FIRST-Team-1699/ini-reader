# ini-reader

Our .ini reader, used in many projects over many years, since 2011.

Archived versions of iniReader are in `2015-archive` and `2016-archive`. `master` needs to be tested before it should be used in projects.

# Usage

This version of ini-Reader sticks to the "definition" for ini files. The syntax can be found [here](https://en.wikipedia.org/wiki/INI_file).

### Starting

Instantiate `ConfigFile`, has three constructors:
- `ConfigFile()`, which looks for a file at `/home/lvuser/1699-config.ini`
- `ConfigFile(String name)`, which looks for a file at `<name>` (see FAQ for suggested dirs on a roboRIO)
- `ConfigFile(File file)`, which looks for the file specified in `<file>`

### File Setup

Because this version of the ini-Reader conforms to ini syntax, mentioned aboved, the file setup is different then pervious versions.

First off, only **one** file is used (in convention; there are no restrictions in the code though). In order to accommodate for different subsystems or autonomous modes, sections are used. Just like the defined syntax, a section is defined by `[section]`.

Below a section header (and until the next header), is a group of configuration lines, where `name = value`. Names are **not** case-sensitive. Values can be integers, doubles, strings, booleans, a character, or a list of integers, doubles, booleans,  or strings. See `example.ini` for an example.

### Getting Values

After instantiating a `ConfigFile`, use the `findSection(String name)` method to get access a `ConfigSection`, which contains all the configuration lines for the section named.

From the `ConfigSection`, use the `findInteger(String name)` to get an integer for the line named. Use the `findDouble` method for doubles, `findString` for strings, etc. In addition, the `findList*` methods return a list for the type specified, with the line name specified. See the `example.java` for getting values from `example.ini`.

# FAQ

Can I have two values on one line? *No, values and section headers are separated by line.*

What directories can the file be put in on a roboRIO? *We use `/home/lvuser` because that is the working directory for robot code, but it can theoretically go anywhere, as long as it won't overwrite another file and won't be deleted. I highly recommend using `/home/lvuser`, unless you know a better place.*

Why did you make this more complicated? *If the previous version worked well for you, go use it, it's under `2016-archive`. But, we were having a problem where we have 10+ ini files on our RIO, and if we wanted more autonomous modes, we needed more files. This allows us to have one file, and a lot less clutter.*

# One Last Note

Please report any bugs, even if you could tell me in person. Most bugs can be patched rather easily and quickly.
