package com.rohanclan.cfml.parser;

import com.rohanclan.cfml.parser.DocItem;
/** Represents a variable within a document. Derived from DocItem to enable standard document creation to find it.
 *  
 *  In theory it should be stored separately from the document tree.
 */
class Variable extends DocItem 
{

protected String varType;
  /* {transient=false, volatile=false, author=Oliver Tupman, version=0.1}*/

  
/** Constructor for a Variable.
   *  
   *  All the parameters apart from newType are passed to the DocItem constructor.
   */

	public Variable(int line, int startDocPos, int endDocPos, String name)
	{
		super(line, startDocPos, endDocPos, name);
		varType = "any";
	}
	public Variable(int line, int startDocPos, int endDocPos, String name, String newType) 
	{
		super(line, startDocPos, endDocPos, name);
		varType = newType;
	}
	public String getType() {
		return varType;
	}
}