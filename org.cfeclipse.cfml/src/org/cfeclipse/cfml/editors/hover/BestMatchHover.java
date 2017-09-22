package org.cfeclipse.cfml.editors.hover;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.information.IInformationProviderExtension2;

import org.eclipse.ui.IEditorPart;

import org.cfeclipse.cfml.CFMLPlugin;
import org.cfeclipse.cfml.preferences.CFMLPreferenceConstants;


/**
 * Caution: this implementation is a layer breaker and contains some "shortcuts"
 */
public class BestMatchHover extends AbstractCFMLEditorTextHover {

	private List<CFMLEditorTextHoverDescriptor> fTextHoverSpecifications;
	private List<ICFMLEditorTextHover> fInstantiatedTextHovers;
	private ITextHover fBestHover;

	public BestMatchHover() {
		installTextHovers();
	}

	public BestMatchHover(IEditorPart editor) {
		this();
		setEditor(editor);
	}

	/**
	 * Installs all text hovers.
	 */
	private void installTextHovers() {

		// initialize lists - indicates that the initialization happened
		fTextHoverSpecifications= new ArrayList<>(2);
		fInstantiatedTextHovers= new ArrayList<>(2);

		// populate list
		CFMLEditorTextHoverDescriptor[] hoverDescs= CFMLPlugin.getDefault().getCFMLEditorTextHoverDescriptors();
		for (int i= 0; i < hoverDescs.length; i++) {
			// ensure that we don't add ourselves to the list
			if (!CFMLPreferenceConstants.ID_BESTMATCH_HOVER.equals(hoverDescs[i].getId()))
				fTextHoverSpecifications.add(hoverDescs[i]);
		}
	}

	private void checkTextHovers() {
		if (fTextHoverSpecifications == null)
			return;

		boolean done= true;
		int i= -1;
		for (Iterator<CFMLEditorTextHoverDescriptor> iterator= fTextHoverSpecifications.iterator(); iterator.hasNext();) {
			i++;
			CFMLEditorTextHoverDescriptor spec= iterator.next();
			if (spec == null)
				continue;
			
			System.out.println(spec.getId());

			done= false;

			ICFMLEditorTextHover hover= spec.createTextHover();
			if (hover != null) {
				hover.setEditor(getEditor());
				fTextHoverSpecifications.set(i, null);
			}
			if (i == fInstantiatedTextHovers.size())
				fInstantiatedTextHovers.add(i, hover);
			else
				fInstantiatedTextHovers.set(i, hover);

		}
		if (done)
			fTextHoverSpecifications= null;
	}

	/*
	 * @see ITextHover#getHoverInfo(ITextViewer, IRegion)
	 */
	@Override
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {

		checkTextHovers();
		fBestHover= null;

		if (fInstantiatedTextHovers == null)
			return null;

		for (Iterator<ICFMLEditorTextHover> iterator= fInstantiatedTextHovers.iterator(); iterator.hasNext(); ) {
			ITextHover hover= iterator.next();
			if (hover == null)
				continue;

			String s= hover.getHoverInfo(textViewer, hoverRegion);
			if (s != null && s.trim().length() > 0) {
				fBestHover= hover;
				return s;
			}
		}

		return null;
	}

	/*
	 * @see org.eclipse.jface.text.ITextHoverExtension2#getHoverInfo2(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion)
	 */
	@Override
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {
		return getHoverInfo2(textViewer, hoverRegion, false);
	}
	
	/**
	 * Returns the information which should be presented when a hover or persistent popup is shown
	 * for the specified hover region.
	 * 
	 * @param textViewer the viewer on which the hover popup should be shown
	 * @param hoverRegion the text range in the viewer which is used to determine the hover display
	 *            information
	 * @param forInformationProvider <code>true</code> iff the hover info is requested by the
	 *            information presenter. In this case, the method only considers text hovers for
	 *            which a proper IInformationControlCreator is available that can supply focusable
	 *            and resizable information controls.
	 * 
	 * @return the hover popup display information, or <code>null</code> if none available
	 * 
	 * @see ITextHoverExtension2#getHoverInfo2(ITextViewer, IRegion)
	 * @since 3.8
	 */
	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion, boolean forInformationProvider) {

		checkTextHovers();
		fBestHover= null;

		if (fInstantiatedTextHovers == null)
			return null;

		for (Iterator<ICFMLEditorTextHover> iterator= fInstantiatedTextHovers.iterator(); iterator.hasNext(); ) {
			ITextHover hover= iterator.next();
			if (hover == null)
				continue;

			if (hover instanceof ITextHoverExtension2) {
				Object info= ((ITextHoverExtension2) hover).getHoverInfo2(textViewer, hoverRegion);
				if (info != null && !(forInformationProvider && getInformationPresenterControlCreator(hover) == null)) {
					fBestHover= hover;
					return info;
				}
			} else {
				String s= hover.getHoverInfo(textViewer, hoverRegion);
				if (s != null && s.trim().length() > 0) {
					fBestHover= hover;
					return s;
				}
			}
		}

		return null;
	}

	/*
	 * @see org.eclipse.jface.text.ITextHoverExtension#getHoverControlCreator()
	 * @since 3.0
	 */
	@Override
	public IInformationControlCreator getHoverControlCreator() {
		if (fBestHover instanceof ITextHoverExtension)
			return ((ITextHoverExtension)fBestHover).getHoverControlCreator();

		return null;
	}

	/*
	 * @see org.eclipse.jdt.internal.ui.text.java.hover.AbstractCFMLEditorTextHover#getInformationPresenterControlCreator()
	 * @since 3.0
	 */
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		return getInformationPresenterControlCreator(fBestHover);
	}

	private static IInformationControlCreator getInformationPresenterControlCreator(ITextHover hover) {
		if (hover instanceof IInformationProviderExtension2) // this is wrong, but left here for backwards compatibility
			return ((IInformationProviderExtension2)hover).getInformationPresenterControlCreator();

		if (hover instanceof AbstractCFMLEditorTextHover) {
			return ((AbstractCFMLEditorTextHover) hover).getInformationPresenterControlCreator();
		}
		return null;
	}
}