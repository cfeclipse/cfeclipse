CFEclipseCall is a custom EclipseCall client for OS X, used to open files with 
cfm, cfc, and cfml file extensions in Eclipse.

It also gives them a nifty icon on the filesystem.

INSTALLATION (OS X)

Copy the CFEclipseCall app bundle to wherever you want to keep it.

Then edit the properties.cfeclipsecall file to point at your Eclipse executable.

I use a .sh script, but you can specify the eclipse executable like so:

/Applications/Eclipse.app/Contents/MacOS/eclipse

Assuming you've got it installed in /Applications.  

You can context-click on the Eclipse icon and select "Show Package Contents", 
browse to Contents/MacOS/ and do "get info" on the eclipse file to get the path
if you need to.

Then double-click on the CFEclipseCall app, which will set the file association
for CFML files, and import (hopefully) your properties.cfeclipsecall settings.

PROPERTIES.CFECLIPSECALL

The properties.cfeclipsecall contents should look something like this:

cfeclipsecall.call=/Users/denny/programs/eclipse-inst/eclipse35.command
cfeclipsecall.socket=2342

The cfeclipsecall.call property is used for launching Eclipse if the CFEclipseCall
client can't connect to the CFEclipseCall plugin in a running instance.

If you change cfeclipsecall.socket property you need to change the socket for the 
CFEclipseCall plugin under Preferences > CFEclipse > CFEclipseCall as well.

USAGE

Double-click a CFML file and pray it opens in Eclipse!

or:

cfeclipsecall <filename> [-G<lineno>[,<col>-<col>]] [-S<socketno>] [-E<eclipse.exe>]
example:
mark column 10-20 in line 100 of myfile:
> cfeclipsecall D:\mydir\myfile -G100,10-20
only show myfile without marking:
> cfeclipsecall D:\mydir\myfile

USING THE JAR

java -jar cfeclipsecall.jar <filename> [-G<lineno>[,<col>-<col>]] [-S<socketno>] [-E<eclipse.exe>]
example:
mark column 10-20 in line 100 of myfile:
> java -jar cfeclipsecall.jar D:\mydir\myfile -G100,10-20
only show myfile without marking:
> java -jar cfeclipsecall.jar D:\mydir\myfile


APPENDIX

Here is a .bat file for windows:
=== cfeclipsecall.bat ===
@echo off
java -jar cfeclipsecall.jar %1 %2

This is what my eclipse35.command script looks like (I used .command just for
an Eclipse icon on the script, silly, I know):

workspace="~/workspace/cf"
~/programs/eclipse-inst/eclipse3.5/Eclipse.app/Contents/MacOS/eclipse \
-data $workspace -clean \
-vmargs \
 -Djava.library.path=/opt/local/lib \
 -Xverify:none -XX:+UseParallelGC  \
 -XX:PermSize=128M -XX:MaxNewSize=128M \
 -XX:NewSize=128M -Xmx612m -Xms612m $*