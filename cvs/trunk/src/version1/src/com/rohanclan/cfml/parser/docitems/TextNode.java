/*
 * Created on Dec 9, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Oliver Tupman
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.cfml.parser.docitems;

//import org.eclipse.core.internal.utils.Assert;

/**
 * A text node within a document. This will consist of purely no text
 * and will have no children (i.e. it will always be a leaf node). 
 *
 * @author Oliver Tupman
 */
public class TextNode extends DocItem {
    
    private String nodeText = null;
    
    /**
     * Sets the text for this node.
     * 
     * @param nodeText The text for this node.
     */
    public void setNodeText(String nodeText)
    {
        //Assert.isNotNull(nodeText);
        if(nodeText == null)
        		throw new IllegalArgumentException("nodeText is null");
        this.nodeText = nodeText;
    }

    /**
     * Gets the text for this node.
     * 
     * @return The text of this node.
     */
    public String getNodeText()
    {
        return this.nodeText;
    }
    
    
    /**
     * @param line
     * @param startDocPos
     * @param endDocPos
     * @param name
     */
    public TextNode(int line, int startDocPos, int endDocPos, String name) {
        super(line, startDocPos, endDocPos, name);
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    public TextNode() {
        super();
        // TODO Auto-generated constructor stub
    }

}
