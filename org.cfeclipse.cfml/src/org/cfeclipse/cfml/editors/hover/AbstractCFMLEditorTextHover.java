package org.cfeclipse.cfml.editors.hover;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.editors.text.EditorsUI;



/**
 * Abstract class for providing hover information for CFML elements.
 *
 * @since 2.1
 */
public abstract class AbstractCFMLEditorTextHover implements ICFMLEditorTextHover, ITextHoverExtension, ITextHoverExtension2 {
	private IEditorPart fEditor;

	public void setEditor(IEditorPart editor) {
		fEditor= editor;
	}

	protected IEditorPart getEditor() {
		return fEditor;
	}

	/*
	 * @see ITextHover#getHoverRegion(ITextViewer, int)
	 */
	@Override
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		return CFMLWordFinder.findWord(textViewer.getDocument(), offset);
	}
//	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
//		Point selection= textViewer.getSelectedRange();
//		if (selection.x <= offset && offset < selection.x + selection.y)
//			return new Region(selection.x, selection.y);
//		return new Region(offset, 0);
//	}

	/**
	 * Returns the CFML elements at the given hover region.
	 *
	 * @param textViewer the text viewer
	 * @param hoverRegion the hover region
	 * @return the array with the CFML elements or <code>null</code>
	 */
//	protected ICFMLElement[] getCFMLElementsAt(ITextViewer textViewer, IRegion hoverRegion) {
//		/*
//		 * The region should be a word region an not of length 0.
//		 * This check is needed because codeSelect(...) also finds
//		 * the CFML element if the offset is behind the word.
//		 */
//		if (hoverRegion.getLength() == 0)
//			return null;
//		
//		IDocument document= textViewer.getDocument();
//		if (document != null && isInheritDoc(document, hoverRegion))
//			return null;
//
//		ICodeAssist resolve= getCodeAssist();
//		if (resolve != null) {
//			try {
//				return resolve.codeSelect(hoverRegion.getOffset(), hoverRegion.getLength());
//			} catch (CFMLModelException x) {
//				return null;
//			}
//		}
//		return null;
//	}

//	/**
//	 * Returns whether the word is "inheritDoc".
//	 * 
//	 * @param document the document
//	 * @param wordRegion the word region
//	 * @return <code>true</code> iff the word is "inheritDoc"
//	 * @since 3.7
//	 */
//	private static boolean isInheritDoc(IDocument document, IRegion wordRegion) {
//		try {
//			String word= document.get(wordRegion.getOffset(), wordRegion.getLength());
//			return "inheritDoc".equals(word); //$NON-NLS-1$
//		} catch (BadLocationException e) {
//			return false;
//		}
//	}

	/*
	 * @see ITextHoverExtension#getHoverControlCreator()
	 */
	@Override
	public IInformationControlCreator getHoverControlCreator() {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell parent) {
				return new DefaultInformationControl(parent, EditorsUI.getTooltipAffordanceString());
			}
		};
	}

	/**
	 * Delegate method for {@link CFMLInformationProvider#getInformationPresenterControlCreator()}
	 * 
	 * @return the information control creator or null if none is available
	 * @since 3.4
	 */
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return new IInformationControlCreator() {
			@Override
			public IInformationControl createInformationControl(Shell shell) {
				return new DefaultInformationControl(shell, true);
			}
		};
	}

//	protected ITypeRoot getEditorInputCFMLElement() {
//		IEditorPart editor= getEditor();
//		if (editor != null)
//			return EditorUtility.getEditorInputCFMLElement(editor, false);
//		return null;
//	}
}