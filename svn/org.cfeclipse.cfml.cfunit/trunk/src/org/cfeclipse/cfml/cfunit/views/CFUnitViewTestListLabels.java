package org.cfeclipse.cfml.cfunit.views;


import org.cfeclipse.cfml.cfunit.CFUnitTestResult;
import org.cfeclipse.cfml.cfunit.CFUnitTestCase;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.IColorProvider;

public class CFUnitViewTestListLabels extends LabelProvider implements IColorProvider {
	private static final Color ERROR_COLOR = new Color(null, 255, 0, 0);
	private static final Color FAILURE_COLOR = new Color(null, 255, 153, 0);
	private static final Color BASES_COLOR = new Color(null, 0, 0, 0);
	private static final Color INACTIVE_COLOR = new Color(null, 204, 204, 204);
	private static final Color WHITE = new Color(null, 255, 255, 255);
	
	private static final Image blankIcon = CFUnitView.getIcon( CFUnitView.ICON_NONE );
	private static final Image errorIcon = CFUnitView.getIcon( CFUnitView.ICON_ERROR );
	private static final Image failureIcon = CFUnitView.getIcon( CFUnitView.ICON_FAILURE );
	private static final Image nwErrorIcon = CFUnitView.getIcon( CFUnitView.ICON_NWERROR );
	
	public Image getImage(Object element) {
		if(element instanceof CFUnitTestResult) {
			CFUnitTestResult tc = (CFUnitTestResult)element;
			
			switch( tc.getType() ) {
				case CFUnitTestResult.ERROR:
					return errorIcon;

				case CFUnitTestResult.FAILURE:
					return failureIcon;

				case CFUnitTestResult.NWERROR:
					return nwErrorIcon;

				default:
					return blankIcon;
			}
		}else if(element instanceof CFUnitTestCase) {
			CFUnitTestCase tc = (CFUnitTestCase)element;
			
			if( tc.getErrorCount() > 0 ) {
				return errorIcon;
			} else if( tc.getFailureCount() > 0 ) {
				return failureIcon;
			}
		}
		
		
		return blankIcon;
	}
	
	public Color getForeground(Object element) { 
		if(element instanceof CFUnitTestResult) {
			CFUnitTestResult tc = (CFUnitTestResult)element;
			
			switch( tc.getType() ) {
				case CFUnitTestResult.ERROR:
					return ERROR_COLOR;

				case CFUnitTestResult.FAILURE:
					return FAILURE_COLOR;

				case CFUnitTestResult.NWERROR:
					return ERROR_COLOR;

				default:
					return BASES_COLOR;
			}
		} else if(element instanceof CFUnitTestCase) {
			CFUnitTestCase tc = (CFUnitTestCase)element;
			
			if( tc.getTestCount() == 0) {
				return INACTIVE_COLOR;
			} if( tc.getErrorCount() > 0 ) {
				return ERROR_COLOR;
			} else if( tc.getFailureCount() > 0 ) {
				return FAILURE_COLOR;
			}
		}
			
		return BASES_COLOR;
	}

	public Color getBackground(Object element) { return WHITE; }
	
	/* TODO - Implement org.eclipse.jface.viewers.IFontProvider
	public Font getFont(Object element) {
		FontData fd = new FontData();
		fd.setStyle( SWT.BOLD );
		return new Font(null, fd );
		
	}
	*/
}
