package org.cfeclipse.cfml.editors.hover;

import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.core.resources.IMarker;

import org.eclipse.jface.text.source.Annotation;

import org.eclipse.ui.texteditor.MarkerAnnotation;

/**
 * Filters problems based on their types.
 */
public class CFMLAnnotationIterator implements Iterator<Annotation> {

	private Iterator<Annotation> fIterator;
	private Annotation fNext;
	private boolean fReturnAllAnnotations;

	/**
	 * Returns a new CFMLAnnotationIterator.
	 * 
	 * @param parent
	 *            the parent iterator to iterate over annotations
	 * @param returnAllAnnotations
	 *            whether to return all annotations or just problem annotations
	 */
	public CFMLAnnotationIterator(Iterator<Annotation> parent, boolean returnAllAnnotations) {
		fReturnAllAnnotations = returnAllAnnotations;
		fIterator = parent;
		skip();
	}

	private void skip() {
		while (fIterator.hasNext()) {
			Annotation next = fIterator.next();

			if (next.isMarkedDeleted())
				continue;

			if (fReturnAllAnnotations || isProblemMarkerAnnotation(next)) {
				fNext = next;
				return;
			}
		}
		fNext = null;
	}

	private static boolean isProblemMarkerAnnotation(Annotation annotation) {
		if (!(annotation instanceof MarkerAnnotation))
			return false;
		try {
			return (((MarkerAnnotation) annotation).getMarker().isSubtypeOf(IMarker.PROBLEM));
		} catch (CoreException e) {
			return false;
		}
	}

	/*
	 * @see Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return fNext != null;
	}

	/*
	 * @see Iterator#next()
	 */
	@Override
	public Annotation next() {
		try {
			return fNext;
		} finally {
			skip();
		}
	}

	/*
	 * @see Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}