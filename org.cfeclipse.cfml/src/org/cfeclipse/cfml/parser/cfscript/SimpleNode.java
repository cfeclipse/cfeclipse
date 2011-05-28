package org.cfeclipse.cfml.parser.cfscript;

import java.util.ArrayList;

import org.cfeclipse.cfml.parser.CFNodeList;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;


/*
 * Copyright © 2002 Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * California 95054, U.S.A. All rights reserved.  Sun Microsystems, Inc. has
 * intellectual property rights relating to technology embodied in the product
 * that is described in this document. In particular, and without limitation,
 * these intellectual property rights may include one or more of the U.S.
 * patents listed at http://www.sun.com/patents and one or more additional
 * patents or pending patent applications in the U.S. and in other countries.
 * U.S. Government Rights - Commercial software. Government users are subject
 * to the Sun Microsystems, Inc. standard license agreement and applicable
 * provisions of the FAR and its supplements.  Use is subject to license terms.
 * Sun,  Sun Microsystems,  the Sun logo and  Java are trademarks or registered
 * trademarks of Sun Microsystems, Inc. in the U.S. and other countries.  This
 * product is covered and controlled by U.S. Export Control laws and may be
 * subject to the export or import laws in other countries.  Nuclear, missile,
 * chemical biological weapons or nuclear maritime end uses or end users,
 * whether direct or indirect, are strictly prohibited.  Export or reexport
 * to countries subject to U.S. embargo or to entities identified on U.S.
 * export exclusion lists, including, but not limited to, the denied persons
 * and specially designated nationals lists is strictly prohibited.
 */


public class SimpleNode extends DocItem implements Node {

	public Token aNodeToken = null;
	
  protected Node parent;
  public Node[] children;
  protected int id;

public ScriptItem scriptItem;

  public SimpleNode(int i) {
    id = i;
  }
  
  public int getId() {
	  return id;
  }

  public void jjtOpen() {
  	
  }

  public void jjtClose() {
	this.itemName = this.getClass().getSimpleName();
	if(this.itemName.endsWith("ASTFunctionDeclaration")) {
		
	}
  	if(this.aNodeToken != null) {
  		this.itemData = this.aNodeToken.image;
  		this.setItemData(this.aNodeToken.image);
  		this.startPosition = this.aNodeToken.beginColumn;
  		this.endPosition = this.aNodeToken.endColumn;
  		this.lineNumber = this.aNodeToken.beginLine;
  		scriptItem = new ScriptItem(this.lineNumber,this.startPosition,this.endPosition,this.itemName);
  		scriptItem.setItemData(this.aNodeToken.image);
  		if(this.itemName.endsWith("ASTIfStatement")) {
  	  		String ifStatement = this.aNodeToken.image;
			Token nextPartOfStatement = this.aNodeToken.next;
			while(nextPartOfStatement.next != null && !nextPartOfStatement.image.equalsIgnoreCase(")")) {
				ifStatement = ifStatement.concat(nextPartOfStatement.image + " ");
				nextPartOfStatement = nextPartOfStatement.next;
			}
    		this.setItemData(ifStatement + ")");  			
  		}
  		if(this.itemName.endsWith("ASTFunctionCallNode")) {
  	  		String ifStatement = this.aNodeToken.image;
			Token nextPartOfStatement = this.aNodeToken.next;
			while(nextPartOfStatement.next != null && !nextPartOfStatement.image.equalsIgnoreCase(")")) {
				ifStatement = ifStatement.concat(nextPartOfStatement.image + " ");
				nextPartOfStatement = nextPartOfStatement.next;
			}
    		this.setItemData(ifStatement + ")");  			
  		}
  		else if(this.itemName.endsWith("ASTStatementExpression")) {
  	  		String statementExp = "";
			Token nextPartOfStatement = this.aNodeToken.next;
			for(int x =0; x <= 2; x++) {
				statementExp = statementExp.concat(nextPartOfStatement.image + " ");
				nextPartOfStatement = nextPartOfStatement.next;
			}
    		this.setItemData(statementExp);  			
  		}
  		else if(this.itemName.endsWith("ASTFunctionDeclaration")) {
  	  		String functionString = this.aNodeToken.image;
  	  		if(this.getFirstChild().getClass().getSimpleName().endsWith("ASTParameterList")) {
				Token nextPartOfStatement = this.aNodeToken.next;
				while(nextPartOfStatement.next != null && !nextPartOfStatement.image.equalsIgnoreCase(")")) {
					functionString = functionString.concat(nextPartOfStatement.image + " ");
					nextPartOfStatement = nextPartOfStatement.next;
				}
	    		functionString = functionString.concat(")");
	    		this.setItemData(functionString);
  	  		}
  	  		if(this.getLastChild().getClass().getSimpleName().endsWith("ASTBlock")) {
  	  			Node[] paramArray = ((SimpleNode)this.getLastChild()).children;
  	  			ScriptItem funcParamsItem = new ScriptItem(this.lineNumber,this.startPosition,this.endPosition,"param");
  	  			scriptItem.addChild(funcParamsItem);
  	    		for(Node node:paramArray) {
  	    			functionString = functionString.concat(((SimpleNode)node).aNodeToken.image + ", ");
  	    		}
  	  		}
  		}
  		else if(this.itemName.endsWith("ASTBlock")) {
  			
  		}

  	}
  }
  
  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

	public void jjtRemoveChild(int elementToDelete) {
		int i = 0;
		int j = 0;
		int newLen;
		if(children == null) {
			newLen = -1;
		} else {
			newLen = children.length - 1;			
		}
		if (newLen < 0) {
			children = null;
		} else {
			Node b[] = new Node[newLen];
			while (i < children.length && j < b.length) {
				if (i == elementToDelete) {
					i++;
				} else {
					b[j] = children[i];
					i++;
					j++;
				}
			}
			children = b;
		}
	}

  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  //public String toString() { return SPLParserTreeConstants.jjtNodeName[id]; }
  public String toString(String prefix) { return prefix + toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
// System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
	SimpleNode n = (SimpleNode)children[i];
	if (n != null) {
	  n.dump(prefix + " ");
	}
      }
    }
  }

  /************************* Added by Sreeni. *******************/

  /** Symbol table */
  protected static java.util.Hashtable symtab = new java.util.Hashtable();

  /** Stack for calculations. */
  protected static Object[] stack = new Object[1024];
  protected static int top = -1;

  public void interpret()
  {
     throw new Error(); // It better not come here.
  }
  
    public DocItem getFirstChild() {
        if(this.children.length > 0)
        {
            return (SimpleNode)this.children[0];
        }
        else
        {
            return null;
        }
    }
    public DocItem getLastChild() {
        if(this.children.length > 0)
        {
            return (SimpleNode)this.children[this.children.length-1];
        }
        else
        {
            return null;
        }
    }
    public boolean hasChildren() {
        if(this.children == null)
        {
            return false;
        }
        return this.children.length > 0;
    }
  /***** Added by Oliver Tupman ******/
  public SimpleNode(SPLParser p, int id) {
  }
    public CFNodeList getChildNodes() {
        CFNodeList list = new CFNodeList();
        if(children != null){        	
	    	for(int childIndex = 0; childIndex < this.children.length; childIndex++)
	    	{
	    		list.add(this.children[childIndex]);
	    	}
        }
        
        return list;
    }
    
    public ArrayList getChildren() {
        return this.getChildNodes();
    }
    public DocItem getParent() {
        return (SimpleNode)this.parent;
    }
    public String toString()
    {
        return "CFScript: " + this.getName();
    }
	public boolean validChildAddition(DocItem parentItem)
	{
		return true;
	}

}

