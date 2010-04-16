package org.cfeclipse.cfml.snippets.handlers;

import org.cfeclipse.cfml.snippets.editors.actions.GenericEncloserAction;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorActionDelegate;

public class SnippetActionHandler extends AbstractHandler {

	/**
	 * a simple wrapper for actions. Uses a command parameter and reflection to
	 * fire off the passed-in action class.
	 */

	public Object execute(ExecutionEvent event) throws ExecutionException {
		String actionClass = event
				.getParameter("org.eclipse.egit.ui.command.action.class");
		try {
			java.lang.reflect.Constructor co;
			co = Class.forName(actionClass).getConstructor();
			IEditorActionDelegate action = (IEditorActionDelegate) co.newInstance();
			action.run(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}