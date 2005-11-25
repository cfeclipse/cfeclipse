package com.rohanclan.cfml.dialogs;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Shell;

import com.rohanclan.cfml.dictionary.Tag;

public class EditCustomTagDialog extends Dialog{
	protected String title;
	private Tag tag;
	private Set attributes;
	
	private Properties fieldStore;

	public EditCustomTagDialog(Shell parent) {
		super(parent);
		this.setTitle("New Custom Tag");
	}
	
	public EditCustomTagDialog(Shell parent, Tag tag){
		super(parent);
		this.setTitle(tag.getName());
		this.setTag(tag);
	}
	
	protected Control createDialogArea(Composite parent){
		Composite container = (Composite) super.createDialogArea(parent);
		FillLayout fl = new FillLayout();
		container.setLayout(fl);
		
		return container;
	}

	protected void configureShell(Shell newShell){
		super.configureShell(newShell);
		newShell.setText(this.getTitle());
	}
	
	
	/*
	 * Buttons in the dialog box
	 * ****************************************
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "OK", true);
		createButton(parent, IDialogConstants.CANCEL_ID,
				IDialogConstants.CANCEL_LABEL, false);
	}

	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
		}
		else{
			
			this.close();
		}
		super.buttonPressed(buttonId);

	}
	
	
	/* 
	 * Getters and setters
	 * ****************************************
	 */
	
	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
