package org.cfeclipse.cfml.snippets.handlers;

import org.cfeclipse.cfml.snippets.commands.InsertSnippetCommand;
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
		InsertSnippetCommand snippetCommand = new InsertSnippetCommand();
		return snippetCommand.execute(event);
	}

}