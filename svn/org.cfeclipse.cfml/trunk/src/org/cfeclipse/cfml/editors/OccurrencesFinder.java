/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.cfeclipse.cfml.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.editors.dnd.SelectionCursorListener;
import org.cfeclipse.cfml.parser.CFDocument;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.ui.texteditor.MarkerUtilities;

public class OccurrencesFinder {
    
	private CFDocument fAntModel;
	private CFMLEditor fEditor;
	private int fOffset;
	private IDocument fDocument;
	private ISourceViewer fViewer;
	private String[] fWordCharArray;
	
	public OccurrencesFinder(CFMLEditor editor, CFDocument model, IDocument document, int offset) {
		fAntModel= model;
		fEditor= editor;
		fDocument= document;
		fOffset= offset;
		fViewer = editor.getViewer();
		if(editor!=null) {			
			fWordCharArray = editor.getSelectionCursorListener().getWordSelectionChars();
		}
	}
	
	public List perform() {
		if (fOffset == 0 || fAntModel == null || fDocument == null) {
			return null;
		}
		//IRegion region= getRegionOld(fEditor.getViewer(), fOffset);
		IRegion region= getRegionNew(fEditor.getViewer(), fOffset, fEditor.getSelectionCursorListener().getLastMouseEvent(),fWordCharArray);
		if(region == null || region.getLength() < 0) {			
			return null;
		}
        List positions= new ArrayList();
		String findString = "";
		try {
			findString = fDocument.get(region.getOffset(), region.getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if (fViewer != null && findString.length() > 1) {

			ISelection initialSelection = this.fViewer.getSelectionProvider().getSelection();

			if (initialSelection instanceof ITextSelection) {
				ITextSelection textSelection = (ITextSelection) initialSelection;
				if (!textSelection.isEmpty()) {
					String text = this.fViewer.getDocument().get();
					IDocument iDoc = (ICFDocument) this.fViewer.getDocument();
					IResource resource = ((ICFDocument) this.fViewer.getDocument()).getResource();
					int index = text.indexOf(findString);
					while (index != -1) {
						Map attrs = new HashMap();
						positions.add(new Position(index, findString.length()));
						index = text.indexOf(findString, index + findString.length());
					}
				}
			}
		}

		return positions;
	}

	
	public static IRegion getRegion(ITextViewer textViewer, int offset, MouseEvent e, String[] wordCharArray) {
		IDocument doc = textViewer.getDocument();
		int startPos, endPos, pos;
		char c;
		String normalWordChars = wordCharArray[0];
		String breakWordChars = wordCharArray[1];
		String wordChars = normalWordChars;
		String altWordChars = wordCharArray[2];
		String altBreakWordChars = wordCharArray[3];
		String shiftWordChars = wordCharArray[4];
		String shiftBreakWordChars = wordCharArray[5];
		IRegion region= null;
		try {
			if(e != null) {				
				if ((e.stateMask == SWT.ALT || e.stateMask == SWT.SHIFT + SWT.ALT)) {
					wordChars = wordChars + altWordChars;
					breakWordChars = altBreakWordChars;
				}
				if ((e.stateMask == SWT.SHIFT || e.stateMask == SWT.SHIFT + SWT.ALT)) {
					wordChars = wordChars + shiftWordChars;
					breakWordChars = shiftBreakWordChars;
				}
			}

			pos = offset;
			int length = doc.getLength();
			while (pos < length) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				++pos;
			}
			endPos = pos;

			pos = offset;

			while (pos >= 0) {
				c = doc.getChar(pos);
				if (breakWordChars.indexOf(c) >= 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				--pos;
			}

			startPos = pos;

			
			if(startPos != endPos) {	
				return new Region(startPos+1, endPos - offset);
//				markOccurrences(this.selectionText);
			}

		} catch (BadLocationException x) {
			// ?
		}		
		return region;
	}

	public static IRegion getRegionNew(ITextViewer textViewer, int offset, MouseEvent mouseEvent, String[] wordCharArray) {
		IDocument document= textViewer.getDocument();
		
		String normalWordChars = wordCharArray[0];
		String breakWordChars = wordCharArray[1];
		String wordChars = normalWordChars;
		String altWordChars = wordCharArray[2];
		String altBreakWordChars = wordCharArray[3];
		String shiftWordChars = wordCharArray[4];
		String shiftBreakWordChars = wordCharArray[5];

		int start= -1;
		int end= -1;
	    IRegion region= null;

		if(mouseEvent != null) {				
			if ((mouseEvent.stateMask == SWT.ALT || mouseEvent.stateMask == SWT.SHIFT + SWT.ALT)) {
				wordChars = wordChars + altWordChars;
				breakWordChars = altBreakWordChars;
			}
			if ((mouseEvent.stateMask == SWT.SHIFT || mouseEvent.stateMask == SWT.SHIFT + SWT.ALT)) {
				wordChars = wordChars + shiftWordChars;
				breakWordChars = shiftBreakWordChars;
			}
		}
	    
	    try {
			int pos= offset;
			char c;
            
            if (document.getChar(pos) == '"') {
                pos--;
            }
			while (pos >= 0) {
				c= document.getChar(pos);
				if (breakWordChars.indexOf(c) > 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
				--pos;
			}
			
			start= pos;
			
			pos= offset;
			int length= document.getLength();
			
			while (pos < length) {
				c= document.getChar(pos);
				if (breakWordChars.indexOf(c) > 0 || !Character.isJavaIdentifierPart(c) && wordChars.indexOf(c) < 0)
					break;
                if (c == '/' && (document.getLength() - 1) > (pos + 1) && document.getChar(pos + 1) == '>') {
                   //e.g. <name/>
                    break;
                }
				++pos;
			}
			
			end= pos;
			
		} catch (BadLocationException x) {
		}
		
		if (start > -1 && end > -1) {
			if (start == offset && end == offset) {
				return new Region(offset, 0);
			} else if (start == offset) {
				return new Region(start, end - start);
			} else {
                try { //correct for spaces at beginning or end
                    while(document.getChar(start + 1) == ' ') {
                        start++;
                    }
                    while(document.getChar(end - 1) == ' ') {
                        end--;
                    }
                } catch (BadLocationException e) {
                }
                region= new Region(start + 1, end - start - 1);
            }
        }
        
        if (region != null) {
            try {
                char c= document.getChar(region.getOffset() - 1);
				if (c == '"') {
					if (document.get(offset, region.getLength()).indexOf(',') != -1) {
						region = cleanRegionForNonProperty(offset, document, region);
					}
				} else if (c != '{') {
                	region = cleanRegionForNonProperty(offset, document, region);
                }
            } catch (BadLocationException e) {
            }
        }
            
		return region;
	}	
	
	public static IRegion getRegionOld(ITextViewer textViewer, int offset) {
		IDocument document= textViewer.getDocument();
		
		int start= -1;
		int end= -1;
	    IRegion region= null;
		try {
			int pos= offset;
			char c;
            
            if (document.getChar(pos) == '"') {
                pos--;
            }
			while (pos >= 0) {
				c= document.getChar(pos);
				if (c != '-' && c != '/' &&  c != '\\' && c != ' ' && c != ')' && c != '('&& c != ':' && !Character.isJavaIdentifierPart(c) && pos != offset)
					break;
				--pos;
			}
			
			start= pos;
			
			pos= offset;
			int length= document.getLength();
			
			while (pos < length) {
				c= document.getChar(pos);
				if (c != '-' && c != '/' &&  c != '\\' && c != ' ' && c != ')' && c != '('&& c != ':' && !Character.isJavaIdentifierPart(c))
					break;
                if (c == '/' && (document.getLength() - 1) > (pos + 1) && document.getChar(pos + 1) == '>') {
                   //e.g. <name/>
                    break;
                }
				++pos;
			}
			
			end= pos;
			
		} catch (BadLocationException x) {
		}
		
		if (start > -1 && end > -1) {
			if (start == offset && end == offset) {
				return new Region(offset, 0);
			} else if (start == offset) {
				return new Region(start, end - start);
			} else {
                try { //correct for spaces at beginning or end
                    while(document.getChar(start + 1) == ' ') {
                        start++;
                    }
                    while(document.getChar(end - 1) == ' ') {
                        end--;
                    }
                } catch (BadLocationException e) {
                }
                region= new Region(start + 1, end - start - 1);
            }
        }
        
        if (region != null) {
            try {
                char c= document.getChar(region.getOffset() - 1);
				if (c == '"') {
					if (document.get(offset, region.getLength()).indexOf(',') != -1) {
						region = cleanRegionForNonProperty(offset, document, region);
					}
				} else if (c != '{') {
                	region = cleanRegionForNonProperty(offset, document, region);
                }
            } catch (BadLocationException e) {
            }
        }
            
		return region;
	}
	
	private static IRegion cleanRegionForNonProperty(int offset, IDocument document, IRegion region) throws BadLocationException {
		//do not allow spaces in region that is not a property
		String text= document.get(region.getOffset(), region.getLength());
		if (text.startsWith("/")) { //$NON-NLS-1$
			text= text.substring(1);
			region= new Region(region.getOffset() + 1, region.getLength() - 1);
		}
		StringTokenizer tokenizer= new StringTokenizer(text, " "); //$NON-NLS-1$
		if (tokenizer.countTokens() != 1) {
		    while(tokenizer.hasMoreTokens()) {
		        String token= tokenizer.nextToken();
		        int index= text.indexOf(token);
		        if (region.getOffset() + index <= offset && region.getOffset() + index + token.length() >= offset) {
		            region= new Region(region.getOffset() + index, token.length());
		            break;
		        }
		    }
		}
		
		return region;
	}	
	
}