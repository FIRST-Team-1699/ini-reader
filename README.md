# ini-Reader
Our .ini reader, used in many projects over many years, since 2011. 

The 2015-archive branch was written in LabView, master is written in Java.

# Usage

## iniReader
- iniReader(fileName) - new iniReader object read from a file named fileName in the "/home/lvuser" directory
- iniReader(dir, fileName) - same as above, except reads from dir + fileName
- getFile() - returns ArrayList<ArrayList> where [[1st string, 1st double], [2nd string, 2nd double]...] 
- getValue(name) - returns a double with whatever corresponds with the String name in the file
- getValue note: if you are getting a returned value of -101.3141592, it cannot find your variable name

## ini Config
- To make a variable (named speed1 with a value of .85), use speed1:.85 or speed1 : .85 or speed1: .85 (or speed1 :.85 if you really want)
- Varaiable note: they are case (and type sensitive) and can contain spaces (just not at the end or they will be considered whitespace)
- Comments in the ini are supported (and recommended); they can be made with a // or # at the beginning of a line.

# Final Comments

Please report any bugs, even if you could tell me in person. Most bugs can be patched rather easily and quickly.
