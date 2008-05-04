This is basically a stand alone parser, I don't know if it will go
anywhere, but it's here for now.

Mark Mandel contributed an ANTLR parser, which is pretty much in
org.cfml.parser, and org.cfml.parser.antlr.

I created a blank javaCC parser, in case someone with knowledge of
javaCC would like to take a go at creating a CFML parser.  It is
located in org.cfml.parser.javacc.

There is a little test file, called testinput.java, within the
org.cfml.parser.antlr package-- you can run it to see the ANTRL
parser in action, sorta.

This is a very nice GUI IDE for ANTLR:

http://www.antlr.org/works/index.html

There's some example CFML to use for testing the parser in 
org.cfml.parser.antlr/testinput.txt