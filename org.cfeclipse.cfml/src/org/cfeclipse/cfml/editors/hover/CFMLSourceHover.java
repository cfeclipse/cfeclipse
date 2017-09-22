package org.cfeclipse.cfml.editors.hover;

import org.cfeclipse.cfml.dictionary.DictionaryManager;
import org.cfeclipse.cfml.dictionary.Function;
import org.cfeclipse.cfml.dictionary.SyntaxDictionary;
import org.cfeclipse.cfml.dictionary.Tag;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;


/**
 * Provides as hover info the source of the selected CFMLElement, or the source near the matching
 * opening curly brace.
 */
public class CFMLSourceHover extends AbstractCFMLEditorTextHover {

	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		//
		// hoverRegion contains the offset in the document of the hovered region
		String message = "";	// This is the eventual message we will give to the user for the infopop.
		
		//System.err.println(hoverRegion.toString());
		
		if (hoverRegion != null) 
		{
			try 
			{
				if(hoverRegion.getLength() > 0)
				{
					SyntaxDictionary dictionary = DictionaryManager.getDictionary(DictionaryManager.CFDIC);
					int	startSpacePos	= hoverRegion.getOffset();
					int endSpacePos 	= startSpacePos + hoverRegion.getLength();
					IDocument doc 		= textViewer.getDocument();
					String wordFound 	= "";
					char currentChar 	= 'a';
					//
					// Cop out without a string if the user is already hovering 
					//over a non-ascii character
					if(IsNonAlpha(doc.getChar(hoverRegion.getOffset())))
						return "";
					
					while(!IsNonAlpha(currentChar) && startSpacePos > 0)
					{
						currentChar = doc.getChar(startSpacePos);
						startSpacePos--;
					}
					
					currentChar = 'a';	// Reset the currentChar
					while(!IsNonAlpha(currentChar) && endSpacePos < doc.getLength())
					{
						currentChar = doc.getChar(endSpacePos);
						endSpacePos++;
					}
					
					//
					// Now get the text string out. The resultant string will have non-alpha characters
					// either side. This is so we can tell whether it has a < or > either side and therefore
					// whether it's a tag or not.					
					wordFound += doc.get(startSpacePos+1, endSpacePos-startSpacePos-1);
					wordFound = wordFound.toLowerCase();
					
					boolean wordIsTag = false;
					if(wordFound.charAt(0) == '<')
						wordIsTag = true;
						
					// Now we know, whip out the characters on either side.
					wordFound = wordFound.substring(1, wordFound.length()-1);
					
					//System.err.println(wordFound);
					
					if(!wordIsTag)
					{
						
						if(dictionary.functionExists(wordFound))
						{	
							Function fun = dictionary.getFunction(wordFound); 
							//message = ((SyntaxDictionaryInterface)dictionary).getFunctionUsage(wordFound);
							message = " " + fun.getHelp();
						}
						//CFSyntaxDictionary.getFunctionUsage(wordFound);
					}
					else
					{
						//message = "<" + wordFound + ">";
						String tglookup = wordFound;
						/*
						if(wordFound.startsWith("cf"))
							tglookup = wordFound.substring(2, wordFound.length());
					*/
						
						//System.err.println("]" + tglookup + "[");
						
						if(dictionary.tagExists(tglookup))
						{	
							Tag tag = dictionary.getTag(tglookup);
							message = " " + tag.getHelp();
							//System.err.print(tag.getHelp());
						}
						
						/*
						Set tagAttributes = ((SyntaxDictionaryInterface)dictionary).getElementAttributes(wordFound.substring(2, wordFound.length()));
						//CFSyntaxDictionary.getElementAttribtues(wordFound.substring(2, wordFound.length()));
						
						// null, I think, means that we didn't find the word. 
						// Therefore it doesn't exist, return a blank string.
						if(tagAttributes == null)
							return "";
						
						Object obj[] = new Object[tagAttributes.size()];
						obj = new TreeSet(tagAttributes).toArray();
						
						//@r2 if there are no attribtues suppress showing 'em
						if(obj.length > 0)
						{
							String attributeString = " attributes: ";
							
							for(int i = 0; i < obj.length; i++)
							{
								if(i != 0)
									attributeString += ", ";
								attributeString+= (String)obj[i];
							}
							
							message += attributeString;
						}
						*/
					}
					return message;
				}
			} 
			catch (BadLocationException x) 
			{
				message = "A BadLocationException occurred. Message from exception is: " + x.getMessage();
				return message;
			}
			catch(Exception excep)
			{
				message = "An exception occurred whilst getting the location: " + excep.getMessage();
				return message;
			}
		}
		return "Unknown area at " + hoverRegion.getOffset() + " to " + hoverRegion.getLength();
	} 
	
	
	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion region) {
		return getHoverInfo(textViewer, region);
	}

	private boolean IsNonAlpha(char character2test)
	{
		return !Character.isLetter(character2test);
	}


}