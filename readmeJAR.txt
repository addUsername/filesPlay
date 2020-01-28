===============================
PracticaProgramacion3Swing.jar
==============================

To run the project from the command line, go to the jar folder and
type the following:

java -jar "PracticaProgramacion3Swing.jar"

 
This jar should be allowed to create folders/files and write them,
please give it the necessary permissions.

This program creates a folder named "FicherosCreados" in the same
directory the .jar is located. All the new files and modificated
ones will be place inside that directory.

When a "new file" action is performanced the program will
automatically search for a file named "TEST.txt" or "TEST.bin", if
the file is found, it will be added to the array, so the user can
use it in the program.

This programs is NOT crossplataform, others SO like linux or
MacOS may raise an error because the file's path returned by the
program it's not correct, that fix needs to be done at code level.
+++DEV NOTE:rewrite "\\" for "/".

@Version: 1.0 (further updates not scheduled)
06/01/2020