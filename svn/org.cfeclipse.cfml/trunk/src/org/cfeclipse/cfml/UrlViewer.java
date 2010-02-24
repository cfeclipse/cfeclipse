package org.cfeclipse.cfml;

import java.net.MalformedURLException;
import java.net.URL;

import org.cfeclipse.cfml.editors.ICFDocument;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.browser.DefaultBrowserSupport;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

public class UrlViewer {

	public void load(final String url) {
		Job job = new Job("What's New in CFEclipse") {
			protected IStatus run(IProgressMonitor monitor) {
				showResults(url);
				return Status.OK_STATUS;
			}
		};
		job.schedule(7000);

	}
	
	public void loadHelp(final String url) {
		Job job = new Job("What's New in CFEclipse") {
			protected IStatus run(IProgressMonitor monitor) {
				showResults(getHelpURL(url).toString());
				return Status.OK_STATUS;
			}
		};
		job.schedule(3000);

	}
	
	

	public URL getHelpURL(String url) {
		URL resolve = CFMLPlugin.getDefault().getWorkbench().getHelpSystem().resolve(url, false);
		return resolve;
		
	}
	
    protected static void showResults(final String url) {
		final DefaultBrowserSupport wb = new DefaultBrowserSupport();
        Display.getDefault().asyncExec(new Runnable() {
           public void run() {
				try {
					wb.createBrowser(wb.AS_EDITOR, "myId", "What's New", "What's new with CFEclipse")
					.openURL(new URL(url));
				} catch (PartInitException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

           }
        });
     }
}
