package org.cfeclipse.cfml.wizards.projectfromtemplatewizard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

public class AntRunner {
//	private static Main antMain;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String antFile = args[0];
		String srcDir = args[1];
		String destDir = args[2];
		
		System.out.println(deploy(antFile, srcDir, destDir));
	}
	
	public static String deploy(String antFile, String from, String to){
		
		return deploy(antFile, from, to, Project.MSG_INFO);
		
	}
	
	
	public static String deploy(String antFile, String from, String to, int msgLevel){
			
		int debugLevel = Project.MSG_INFO;
		
		
		
		  switch (msgLevel) {
          case 0:  debugLevel = Project.MSG_ERR; break;
          case 1:  debugLevel = Project.MSG_WARN; break;
          case 2:  debugLevel = Project.MSG_INFO; break;
          case 3:  debugLevel = Project.MSG_VERBOSE; break;
          case 4:  debugLevel = Project.MSG_DEBUG; break;
          
		  }
		
		
		OutputStream outStream = new ByteArrayOutputStream();
		PrintStream prnt = new PrintStream(outStream);
		
		
		File buildFile = new File(antFile);
		Project p = new Project();
		p.setUserProperty("ant.file", buildFile.getAbsolutePath());
		p.setUserProperty("srcDir", from);
		p.setUserProperty("destDir", to);
		
		
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setEmacsMode(true);
		
		
		consoleLogger.setErrorPrintStream(prnt);
		consoleLogger.setOutputPrintStream(prnt);
		consoleLogger.setMessageOutputLevel(debugLevel);
		p.addBuildListener(consoleLogger);
		
		
		try {
			p.fireBuildStarted();
			p.init();
			ProjectHelper helper = ProjectHelper.getProjectHelper();
			p.addReference("ant.projectHelper", helper);
			helper.parse(p, buildFile);
			p.executeTarget(p.getDefaultTarget());
			p.fireBuildFinished(null);
			
			
			
		} catch (BuildException e) {
			p.fireBuildFinished(e);
		}
		
		
		return outStream.toString();

	}

}
