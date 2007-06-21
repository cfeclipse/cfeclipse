package org.cfeclipse.cfml.core.parser;

import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;

public class CFScriptParser extends	org.cfeclipse.cfml.core.parser.antlr.CFScriptParser
{
	private ErrorObservable observable;
	
	public CFScriptParser(TokenStream input)
	{
		super(input);
		setObservable(new ErrorObservable());
	}

	public void addObserver(IErrorObserver observer)
	{
		observable.addObserver(observer);
	}
	
	public void displayRecognitionError(String[] tokenNames, RecognitionException e)
	{
		reportError(e, getErrorMessage(e, tokenNames));
		
		super.displayRecognitionError(tokenNames, e);
	}
	
	protected void reportError(RecognitionException e, String errorMessage)
	{
		ErrorEvent event = new ErrorEvent(e, errorMessage);
		
		getObservable().notifyObservers(event);		
	}

	public void removeObserver(IErrorObserver observer)
	{
		observable.removeObserver(observer);
	}

	private ErrorObservable getObservable()
	{
		return observable;
	}

	private void setObservable(ErrorObservable observable)
	{
		this.observable = observable;
	}

}
