package org.cfeclipse.cfml.editors.contentassist.proposal;

import org.cfeclipse.cfml.dictionary.Parameter;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposalExtension6;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

public class FunctionProposal implements ICompletionProposal, ICompletionProposalExtension6
{
	/** The string to be displayed in the completion proposal popup. */
	private String fDisplayString;
	/** The replacement string. */
	private String fReplacementString;
	/** The replacement offset. */
	private int fReplacementOffset;
	/** The replacement length. */
	private int fReplacementLength;
	/** The cursor position after this proposal has been applied. */
	private int fCursorPosition;
	/** The image to be displayed in the completion proposal popup. */
	private Image fImage;
	private Parameter[] fParams;
	/** The context information of this proposal. */
	private IContextInformation fContextInformation;
	/** The additional info of this proposal. */
	private String fAdditionalProposalInfo;

	/**
	 * Creates a new completion proposal. All fields are initialized based on the provided information.
	 * 
	 * @param replacementString
	 *            the actual string to be inserted into the document
	 * @param replacementOffset
	 *            the offset of the text to be replaced
	 * @param replacementLength
	 *            the length of the text to be replaced
	 * @param cursorPosition
	 *            the position of the cursor following the insert relative to replacementOffset
	 * @param image
	 *            the image to display for this proposal
	 * @param displayString
	 *            the string to be displayed for the proposal
	 * @param contextInformation
	 *            the context information associated with this proposal
	 * @param additionalProposalInfo
	 *            the additional information associated with this proposal
	 */
	public FunctionProposal(String replacementString, int replacementOffset, int replacementLength, int cursorPosition, Image image,
			Parameter[] params, String displayString, IContextInformation contextInformation, String additionalProposalInfo) {
		Assert.isNotNull(replacementString);
		Assert.isTrue(replacementOffset >= 0);
		Assert.isTrue(replacementLength >= 0);
		Assert.isTrue(cursorPosition >= 0);

		fParams = params;
		fReplacementString = replacementString;
		fReplacementOffset = replacementOffset;
		fReplacementLength = replacementLength;
		fCursorPosition = cursorPosition;
		fImage = image;
		fDisplayString = displayString;
		fContextInformation = contextInformation;
		fAdditionalProposalInfo = additionalProposalInfo;
	}

	public void apply(IDocument document) {
		/*
		 * ISourceViewer viewer; IRegion fSelectedRegion; try {
		 * 
		 * int offset = fReplacementOffset; int start = fReplacementOffset; int end = fReplacementOffset +
		 * fReplacementLength; end = Math.max(end, start);
		 * 
		 * if (end > document.getLength()) end = offset; String functionString = "the(sutff)"; document.replace(start,
		 * end - start, functionString);
		 * 
		 * // translate positions LinkedModeModel model = new LinkedModeModel();
		 * 
		 * 
		 * boolean hasPositions = false; for (int i = 0; i != fParams.length; i++) { Parameter variable = fParams[i];
		 * 
		 * LinkedPositionGroup group = new LinkedPositionGroup();
		 * 
		 * int[] offsets = variable.getOffsets(); int length = variable.getLength();
		 * 
		 * LinkedPosition first; if (guess != null && variable instanceof MultiVariable) { first = new
		 * VariablePosition(document, offsets[0] + start, length, guess, (MultiVariable) variable);
		 * guess.addSlave((VariablePosition) first); } else { String[] values = variable.getValues();
		 * ICompletionProposal[] proposals = new ICompletionProposal[values.length]; for (int j = 0; j < values.length;
		 * j++) { ensurePositionCategoryInstalled(document, model); Position pos = new Position(offsets[0] + start,
		 * length); document.addPosition(getCategory(), pos); proposals[j] = new
		 * PositionBasedCompletionProposal(values[j], pos, length); }
		 * 
		 * if (proposals.length > 1) first = new ProposalPosition(document, offsets[0] + start, length, proposals); else
		 * first = new LinkedPosition(document, offsets[0] + start, length); }
		 * 
		 * for (int j = 0; j != offsets.length; j++) if (j == 0) group.addPosition(first); else group.addPosition(new
		 * LinkedPosition(document, offsets[j] + start, length));
		 * 
		 * model.addGroup(group); hasPositions = true; }
		 * 
		 * if (hasPositions) { model.forceInstall(); CFMLEditor editor = (CFMLEditor)
		 * PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();. if (editor != null)
		 * { model.addLinkingListener(new EditorSynchronizer(editor)); } viewer = editor.getViewer();
		 * 
		 * LinkedModeUI ui = new EditorLinkedModeUI(model, viewer); ui.setExitPosition(viewer,
		 * getCaretOffset(templateBuffer) + start, 0, Integer.MAX_VALUE); ui.enter();
		 * 
		 * fSelectedRegion = ui.getSelectedRegion(); } else fSelectedRegion = new Region(getCaretOffset(templateBuffer)
		 * + start, 0);
		 * 
		 * } catch (BadLocationException e) { JavaPlugin.log(e); openErrorDialog(viewer.getTextWidget().getShell(), e);
		 * fSelectedRegion = fRegion; } catch (BadPositionCategoryException e) { JavaPlugin.log(e);
		 * openErrorDialog(viewer.getTextWidget().getShell(), e); fSelectedRegion = fRegion; }
		 */
	}

	public StyledString getStyledDisplayString() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * @see ICompletionProposal#getSelection(IDocument)
	 */
	public Point getSelection(IDocument document) {
		return new Point(fReplacementOffset + fCursorPosition, 0);
	}

	/*
	 * @see ICompletionProposal#getContextInformation()
	 */
	public IContextInformation getContextInformation() {
		return fContextInformation;
	}

	/*
	 * @see ICompletionProposal#getImage()
	 */
	public Image getImage() {
		return fImage;
	}

	/*
	 * @see ICompletionProposal#getDisplayString()
	 */
	public String getDisplayString() {
		if (fDisplayString != null)
			return fDisplayString;
		return fReplacementString;
	}

	/*
	 * @see ICompletionProposal#getAdditionalProposalInfo()
	 */
	public String getAdditionalProposalInfo() {
		return fAdditionalProposalInfo;
	}
}
