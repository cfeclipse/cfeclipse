/*
 * Created on 17-Feb-2006
 *
 * The MIT License
 * Copyright (c) 2006 markd
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
package com.rohanclan.cfml.views.packageview.objects;

import java.util.ArrayList;

import com.rohanclan.cfml.parser.docitems.TagItem;

/**
 * @author markd
 *
 */
public class FunctionNode implements IComponentViewObject{
	private ArrayList children;
	private TagItem functionTag;
	
	
	public FunctionNode(TagItem function){
		this.functionTag = function;
		
		
	}
	
	
	public ArrayList getChildren() {
		return children;
	}

	public String getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPackageName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IComponentViewObject getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setChildren(ArrayList children) {
		// TODO Auto-generated method stub
		
	}

	public void setImage(String image) {
		// TODO Auto-generated method stub
		
	}

	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	public void setParent(IComponentViewObject parent) {
		// TODO Auto-generated method stub
		
	}

}
