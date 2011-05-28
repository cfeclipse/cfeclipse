package org.cfeclipse.cfml.frameworks;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.contrib.input.LineNumberSAXBuilder;
import org.jdom.input.SAXBuilder;

public class ConfigLoader {

	public ConfigLoader() {
		super();
	}

	
	public static Document loadConfig(String configFileName){
		
		try {
			URL actionConfigURL = new URL(
					Activator.getDefault().getBundle().getEntry("/"),
					"config/" + configFileName
				);
			
			
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(actionConfigURL);
			return document;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	/**
	 * This function allows you to define how you should parse the XML file, so you can get it with linenumbers or not
	 * @param configFileName
	 * @param useLineNumbers
	 * @return
	 */
	public static Document loadConfig(String configFileName, boolean useLineNumbers){
		try {
			URL actionConfigURL = new URL(
					Activator.getDefault().getBundle().getEntry("/"),
					"config/" + configFileName
				);
			if(useLineNumbers){
				LineNumberSAXBuilder lbuilder = new LineNumberSAXBuilder();
				Document document = lbuilder.build(actionConfigURL);
				return document;
			}
			else{
				SAXBuilder builder = new SAXBuilder();
				Document document = builder.build(actionConfigURL);
				return document;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		return null;
	}
	
	
}
