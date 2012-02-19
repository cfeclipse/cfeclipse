package org.cfeclipse.cfml.parser;

import java.util.LinkedHashMap;
import java.util.Map;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;

import cfml.parsing.cfscript.CFScriptParser;

public class FunctionInfo extends ScriptItem {

	private String functionname;
	private String returnType = "any";
	private String access = "public";
	private Map parameters;
	private Boolean atLeastOneParam = false;

	public FunctionInfo(CommonTree tree) {
		super(tree.getLine(), tree.getTokenStartIndex(), tree.getTokenStopIndex(), "ASTFunctionDeclaration");
		StringBuilder functionText = new StringBuilder();
		parameters = new LinkedHashMap();
		for (Object kidTree : tree.getChildren()) {
			CommonTree kid = (CommonTree) kidTree;
			CommonToken token = (CommonToken) kid.getToken();
			switch (kid.getType()) {
			case CFScriptParser.PUBLIC:
			case CFScriptParser.PRIVATE:
			case CFScriptParser.PACKAGE:
				setStartPosition(token.getStartIndex());
				setAccess(kid.getText());
				functionText.append(kid.getText() + " ");
				break;
			case CFScriptParser.FUNCTION_RETURNTYPE:
				setReturnType(kid.getChild(0).getText());
				break;
			case CFScriptParser.IDENTIFIER:
				setStartPosition(kid.getTokenStartIndex());
				setFunctionName(kid.getText());
				functionText.append(kid.getText());
				break;
			case CFScriptParser.FUNCTION_PARAMETER:
				if (!atLeastOneParam) {
					functionText.append(" (");
					atLeastOneParam = true;
				} else {
					functionText.append(", ");
				}
				addParameter(functionText, kid);
				trimStringBuilder(functionText);
				break;
			}
		}
		if (!atLeastOneParam)
			functionText.append("(");
		functionText.append(')');
		if (this.returnType != null) {
			functionText.append(" : " + this.returnType);
		}
		this.setItemData(functionText.toString());
	}

	private void addParameter(StringBuilder functionText, CommonTree tree) {
		LinkedHashMap<String, String> paramaterAttributes = new LinkedHashMap<String, String>();
		for (Object kidTree : tree.getChildren()) {
			CommonTree kid = (CommonTree) kidTree;
			paramaterAttributes.put("required", "false");
			paramaterAttributes.put("type", "any");
			paramaterAttributes.put("default", "");
			switch (kid.getType()) {
			case CFScriptParser.REQUIRED:
				functionText.append(kid.getText() + " ");
				paramaterAttributes.put("required", "true");
				break;
			case CFScriptParser.IDENTIFIER:
				functionText.append(kid.getText() + " ");
				parameters.put(kid.getText(), paramaterAttributes);
				break;
			case CFScriptParser.PARAMETER_TYPE:
				functionText.append(kid.getChild(0).getText() + " ");
				paramaterAttributes.put("type", kid.getChild(0).getText().toLowerCase());
				break;
			case CFScriptParser.EQUALSOP:
				trimStringBuilder(functionText);
				functionText.append(kid.getText());
				paramaterAttributes.put("default", kid.getText());
				break;
			default:
				functionText.append(kid.getText() + " " + kid.getType());
			}
		}
		trimStringBuilder(functionText);
	}

	private void setAccess(String text) {
		this.access = text;
	}

	public String getAccess(String text) {
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

	public Map getParameters() {
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

}
