/*
 * Created on Sep 20, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Matt Cristantello
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
 *
 */
package org.cfeclipse.cfml.editors.pairs;

import java.util.Iterator;
import java.util.Stack;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ICharacterPairMatcher;

/**
 * @author Matt
 */
public class CFMLPairMatcher implements ICharacterPairMatcher
{
	private java.util.Collection pairs;
	private int currentAnchor;
	private Pair matchingPair;
	
	/** The offset passed to us in the match function. */
	private int offset;
	/** the document passed to us in the match function */
	private IDocument document;
	
	public static final int UNSET_ANCHOR = -2;
	public static final int OFFSET_UNKNOWN = -9;

	/** The constructor for the pair matcher
	 * @param pairs
	 */
	public CFMLPairMatcher(java.util.Collection pairs)
	{
		this.pairs = pairs;
		this.currentAnchor = CFMLPairMatcher.UNSET_ANCHOR;
		this.matchingPair = null;
	}
	/* disposes the pair matcher, not sure why this is needed in java --
	 * as things are garbage collected. It calls clear at the moment and
	 * sets the instance data to null.
	 */
	public void dispose()
	{
		clear();
		pairs = null;
	}

	/* Clears the state of the pair matcher so it can be called again.
	 */
	public void clear()
	{
		this.currentAnchor = CFMLPairMatcher.UNSET_ANCHOR;
		this.matchingPair = null;
		this.document = null;
		this.offset = CFMLPairMatcher.OFFSET_UNKNOWN;
	}

	/* This is supposed to return the region of the document that contains both
	 * the original part of the pair and the matching part.
	 */
	public IRegion match(IDocument iDocument, int offset)
	{
		clear(); //this does not appear to get called automatically like it's supposed to
		IRegion region = null;
		//store the iDocument and offset
		this.document = iDocument;
		this.offset = offset;
		
		if( document != null && offset>=0)
		{
			//loop through the collection to see if we can find a match
			//and identify the pair we're working with
			Iterator pairIterator = pairs.iterator();
			while(matchingPair==null && pairIterator.hasNext())
			{
				Pair currentPair = (Pair)pairIterator.next();
				try
				{
					// look for the right-side of the pair first
					//always going to the left of the cursor
					if( iDocument.get(offset-currentPair.getLength(),currentPair.getLength()).equals(currentPair.getRight()) )
					{
						//we have a match and the current anchor is RIGHT
						currentAnchor = CFMLPairMatcher.RIGHT;
						matchingPair = currentPair;
					}
					//look for the left side of the pair
					else if( iDocument.get(offset-currentPair.getLength(),currentPair.getLength()).equals(currentPair.getLeft()) )
					{
						//we have a match and the current anchor is LEFT
						currentAnchor = CFMLPairMatcher.LEFT;
						matchingPair = currentPair;
					}
				}
				catch( BadLocationException e)
				{
					//here, this means that we're at or near the end of the document
					//and there was not enough room to check the whole pair
					//it should be safe to keep scanning
				}
			}
			
			//if we found a match
			if( matchingPair!=null )
			{
				try
				{
					//tweak the starting offset to offset - pair length
					//since we went backwards to find the match
					int startOffset = offset - matchingPair.getLength();
					
					//get the distance to the match
					int matchDistance = findDistanceToMatch();

					
					
					//note that the region must contain both pairs, hence the 2x factor
					
					//if the match is before the cursor, step back the match distance plus 1 pair length
					//and go forward the negated value (now positive) of the match distance
					if( matchDistance < 0 )
					{
						region = new Region(startOffset+matchDistance-matchingPair.getLength(),Math.abs(matchDistance)+2*matchingPair.getLength());
						
					}
					//if matchDistance is zero, check to see which side the anchor is on
					//and use the appropriate region tweaking (this handles matches right next to the
					//cursor
					else if( matchDistance == 0 )
					{
						if(currentAnchor == CFMLPairMatcher.RIGHT)
						{
							region = new Region(startOffset-1,2*matchingPair.getLength());
						}
						else
						{
							region = new Region(startOffset,2*matchingPair.getLength());
						}
					}
					//if match distance is positive, start at the offset
					else if( matchDistance > 0)
					{
						region = new Region(startOffset,matchDistance+2*matchingPair.getLength());
					}

					
					
				}
				catch( UnableToFindMatchException e)
				{
					//ensure the region is null and continue
					region = null;
				}
				catch( UnsetAnchorException e)
				{
					region = null;
				}
			}
		}
		
		return region;
	}

	/* Returns the anchor position we stored in the match function,
	 * will be LEFT or RIGHT, for the side of the region where the 
	 * character whose pair we searched for is.
	 */
	public int getAnchor()
	{
		return currentAnchor;
	}
	
	private int findDistanceToMatch()
		throws UnableToFindMatchException,UnsetAnchorException
	{
		int matchDistance = 0;

		//store our search strings and starting offset
		int startOffset = 0;
		String searchingFor = "";
		String notSearchingFor = "";
		if( currentAnchor == CFMLPairMatcher.LEFT )
		{
			searchingFor = matchingPair.getRight();
			notSearchingFor = matchingPair.getLeft();
			startOffset = offset;
		}
		else if( currentAnchor == CFMLPairMatcher.RIGHT)
		{
			searchingFor = matchingPair.getLeft();
			notSearchingFor = matchingPair.getRight();
			startOffset = offset - 2*matchingPair.getLength();
		}
		else
		{
			throw new UnsetAnchorException("anchor was unset.");
		}
		
		//figure out what direction to search in and set an incrementor appropriately
		int incrementor = 0;
		if( currentAnchor == CFMLPairMatcher.LEFT )
		{
			incrementor = 1;
		}
		else
		{
			incrementor = -1;
		}
		
		//search in our chosen direction until we find a match or throw an exception
		try
		{
			//just loop until we find the match or throw an exception
			boolean matchFound = false;
			//create the stack for tracking the "matches" that shouldn't count
			Stack trackingStack = new Stack();
			while(!matchFound)
			{
				String currentTestValue = document.get(startOffset+matchDistance,matchingPair.getLength());
				if( currentTestValue.equals(notSearchingFor) )
				{
					trackingStack.push(notSearchingFor);
				}
				else if( currentTestValue.equals(searchingFor) && 
						trackingStack.size() > 0 )
				{
					trackingStack.pop();
				}
				else if( currentTestValue.equals(searchingFor) )
				{
					matchFound = true;
				}
								
				//increment at the bottom
				if( !matchFound)
				{
					matchDistance+=incrementor;
				}
			}
		}
		catch( Exception e)
		{
			throw new UnableToFindMatchException(e);
		}
				
		return matchDistance;
	}

}
