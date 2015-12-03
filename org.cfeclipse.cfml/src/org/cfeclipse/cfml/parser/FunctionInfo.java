package org.cfeclipse.cfml.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;

import cfml.CFSCRIPTParser;
import cfml.CFSCRIPTParser.ParameterAttributeContext;
import cfml.CFSCRIPTParser.ParameterListContext;
import cfml.CFSCRIPTParser.ReturnStatementContext;
import cfml.CFSCRIPTParserBaseListener;

public class FunctionInfo extends ScriptItem {

	private String functionname;
	private String returnType = "any";
	private String access = "public";
	private List<LinkedHashMap<String, String>> parameters;
	private String[] ruleNames = CFSCRIPTParser.ruleNames;
	private Boolean atLeastOneParam = false;

	public void dumpTree(ParseTree tree, String[] strings) {
		System.out.println("");
		System.out.println("Kids:");
		for (int i = 0; i < tree.getChildCount(); i++) {
			ParseTree kid = (ParseTree) tree.getChild(i);
			System.out.println("Child: " + i);
			if (kid.getPayload() instanceof RuleContext) {
				RuleContext rule = (RuleContext) kid.getPayload();
				System.out.println("Rule: "+strings[rule.getRuleIndex()] + " " + rule.getRuleIndex());
			} else {
				System.out.println("Token");
			}
			System.out.println(kid.getText());
			System.out.println("Kids kids");
			for (int j = 0; j < kid.getChildCount(); j++) {
				dumpTree(kid,strings);
			}
			System.out.println();
			System.out.println();
		}
		
	}
	
	public FunctionInfo(ParserRuleContext tree) {
		super(tree, "ASTFunctionDeclaration");
		parameters = new ArrayList<LinkedHashMap<String, String>>();
		final TreeParameterListener listener = new TreeParameterListener();
		ParseTreeWalker.DEFAULT.walk(listener, tree);
	}

	private void setAccess(String text) {
		this.access = text;
	}

	public String getAccess() {
		return this.access;
	}

	private void setReturnType(String text) {
		this.returnType = text;
	}

	public String getReturnType() {
		return this.returnType;
	}

	private void setFunctionName(String text) {
		this.functionname = text;
	}

	public String getFunctionName() {
		return this.functionname;
	}

	public void setLineNumber(int line) {
		this.lineNumber = line;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public List<LinkedHashMap<String, String>> getParameters() {
		return this.parameters;
	}

	public void setStartPosition(int pos) {
		this.startPosition = pos;
	}

	public void setEndPosition(int pos) {
		this.endPosition = pos;
	}

	public static StringBuilder trimStringBuilder(StringBuilder sb) {
		int first, last;

		for (first = 0; first < sb.length(); first++)
			if (!Character.isWhitespace(sb.charAt(first)))
				break;

		for (last = sb.length(); last > first; last--)
			if (!Character.isWhitespace(sb.charAt(last - 1)))
				break;

		if (first == last) {
			sb.delete(0, sb.length());
		} else {
			if (last < sb.length()) {
				sb.delete(last, sb.length());
			}
			if (first > 0) {
				sb.delete(0, first);
			}
		}
		return sb;
	}

	private class TreeParameterListener extends CFSCRIPTParserBaseListener {
	    private final List<String> ruleNames;
	    Map<RuleContext,ArrayList<String>> stack = new HashMap<RuleContext,ArrayList<String>>();
		StringBuilder functionText = new StringBuilder();

	    public TreeParameterListener() {
	    	this.ruleNames = Arrays.asList(CFSCRIPTParser.ruleNames);
	    }
	    
	    private String valueOrDefault(ParserRuleContext rule, String defaultValue) {
	    	if(rule != null)
	    		return rule.getText();
	    	return defaultValue;
	    }
	    
	    /**
	     * {@inheritDoc}
	     *
	     * <p>The default implementation does nothing.</p>
	     */
	    @Override public void enterFunctionDeclaration(cfml.CFSCRIPTParser.FunctionDeclarationContext ctx) {
	    	super.exitFunctionDeclaration(ctx);
	    	String name = valueOrDefault(ctx.identifier(),"unknown");
	    	String access = valueOrDefault(ctx.accessType(),"public");
	    	String type = valueOrDefault(ctx.typeSpec(),"any");
	    	setFunctionName(name);
	    	setAccess(access);
	    	setReturnType(type);
	    	functionText.append(name);;
	    };

	    /**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitFunctionDeclaration(cfml.CFSCRIPTParser.FunctionDeclarationContext ctx) {
			super.exitFunctionDeclaration(ctx);
			trimStringBuilder(functionText);
			setItemData(functionText.toString());
		};
		
		@Override
		public void enterParameterList(ParameterListContext ctx) {
			super.enterParameterList(ctx);
			functionText.append("(");
		}
		@Override
		public void exitParameterList(ParameterListContext ctx) {
			super.exitParameterList(ctx);
			for( LinkedHashMap<String, String> param : parameters) {
				functionText.append(param.get("type") + " ");
				functionText.append(param.get("name") + " ");
				functionText.append(", ");
			}
			functionText.delete(functionText.length()-2, functionText.length());
			functionText.append(")");
		}

		/**
		 * {@inheritDoc}
		 *
		 * <p>The default implementation does nothing.</p>
		 */
		@Override public void exitParameter(cfml.CFSCRIPTParser.ParameterContext ctx) {
			List<ParameterAttributeContext> attribs = ctx.parameterAttribute();
			LinkedHashMap<String, String> paramaterAttributes = new LinkedHashMap<String, String>();
			String name = ctx.identifier().getText();
			boolean required = ctx.REQUIRED() != null;
			TerminalNode equals = ctx.EQUALSOP();
			String type = ctx.parameterType() == null ? "Any" : ctx.parameterType().getText();
			paramaterAttributes.put("required", required ? "true" : "false");
			paramaterAttributes.put("type", type);
			paramaterAttributes.put("name", name);
			if(ctx.EQUALSOP() != null) {
				cfml.CFSCRIPTParser.ParameterContext parent = (cfml.CFSCRIPTParser.ParameterContext) ctx.EQUALSOP().getParent();
				paramaterAttributes.put("default", parent.getStop().getText());
			}
			ScriptItem childNode = new ScriptItem(ctx, "ASTFunctionParameter");
			childNode.setItemData(ctx.getText());
			addChild(childNode);
			parameters.add(paramaterAttributes);
		};
		
		@Override
		public void enterReturnStatement(ReturnStatementContext ctx) {
			super.exitReturnStatement(ctx);
			ScriptItem childNode = new ScriptItem(ctx, "return");
			childNode.setItemData(ctx.getText());
			addChild(childNode);
		}

	    @Override
	    public String toString() {
	        return functionText.toString();
	    }
	    
	}
}
