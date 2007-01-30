package org.cfeclipse.cfml.parser.cfscript;

public class CFScriptErrorItem {

    private int line, column;
    private String image;
    // private int[][] expectedTokenSequences;

    public CFScriptErrorItem(ParseException e) {
        if (e.currentToken != null) {
            Token tok = e.currentToken;
            this.image = tok.image;
            this.line = tok.beginLine;
            this.column = tok.beginColumn;
            // ParseException gives us this as well. Don't know if it can be useful
            // Each entry in this array is an array of integers.  Each array
            // of integers represents a sequence of tokens (by their ordinal
            // values) that is expected at this point of the parse.
            // this.expectedTokenSequences = e.expectedTokenSequences;
        }
    }
        

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getImage() {
        return image;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Error at line ")
          .append(line)
          .append(" Column ")
          .append(column)
          .append(". Invalid token: \"")
          .append(image)
          .append("\"");
        return sb.toString();
    }
}
