package org.cfml.parser.antlr;
import java.io.*;
import org.antlr.runtime.*;
import org.antlr.runtime.debug.DebugEventSocketProxy;

import org.cfml.parser.antlr.*;


public class testinput {

    public static void main(String args[]) throws Exception {
        CFMLLexer lex = new CFMLLexer(new ANTLRFileStream("/Users/denny/Documents/workspace-cfe/org.cfml.parser/src/org/cfml/parser/antlr/testinput.txt"));
        CommonTokenStream tokens = new CommonTokenStream(lex);

        CFMLParser g = new CFMLParser(tokens);
        try {
            g.cfml();
        } catch (RecognitionException e) {
            e.printStackTrace();
        }
    }
}