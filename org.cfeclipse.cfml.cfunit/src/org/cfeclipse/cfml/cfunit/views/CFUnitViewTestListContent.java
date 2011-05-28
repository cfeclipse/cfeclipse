package org.cfeclipse.cfml.cfunit.views;

import org.cfeclipse.cfml.cfunit.CFUnitTestCase;
import org.cfeclipse.cfml.cfunit.CFUnitTestSuite;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CFUnitViewTestListContent implements IStructuredContentProvider, ITreeContentProvider {

	public CFUnitViewTestListContent() {
	}
	
	public Object[] getElements(Object inputElement) {
		return getChildren( inputElement );
	}
	
	public void dispose() {}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof CFUnitTestSuite) {
			CFUnitTestSuite ts = (CFUnitTestSuite)parentElement;
			return ts.getTestCases();
		} else if(parentElement instanceof CFUnitTestCase) {
			CFUnitTestCase tc = (CFUnitTestCase)parentElement;
			return tc.getResults();
		}
		return new Object[0];
	}

	public Object getParent(Object element) {
		return null;
	}

	public boolean hasChildren(Object element) {
		if(element instanceof CFUnitTestSuite) {
			if( CFUnitTestSuite.getRunCount() > 0 ) {
				return true;
			}
		} else if(element instanceof CFUnitTestCase) {
			CFUnitTestCase tc = (CFUnitTestCase)element;
			if( tc.getRunCount() > 0) {
				return true;
			}
		}
		
		return false;
	}

}
