package org.cfml.parser;

import org.antlr.runtime.CharStream;
import org.antlr.runtime.RecognitionException;

public class CFScriptLexer extends org.cfml.parser.antlr.CFScriptLexer 
{
	private ErrorObservable observable;

	public CFScriptLexer() 
	{
		super();
		setObservable(new ErrorObservable());
	}

	public CFScriptLexer(CharStream input) 
	{
		super(input);
		setObservable(new ErrorObservable());
	}

	public void displayRecognitionError(String[] tokenNames, RecognitionException e)
	{
		ErrorEvent event = new ErrorEvent(e, getErrorMessage(e, tokenNames));
		getObservable().notifyObservers(event);		
		
		super.displayRecognitionError(tokenNames, e);
	}

	public void addObserver(IErrorObserver observer)
	{
		observable.addObserver(observer);
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
