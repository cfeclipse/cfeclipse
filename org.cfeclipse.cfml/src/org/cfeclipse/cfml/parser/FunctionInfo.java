package org.cfeclipse.cfml.parser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.antlr.runtime.CommonToken;
import org.antlr.runtime.tree.CommonTree;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;

import cfml.parsing.cfscript.CFScriptParser;

public class FunctionInfo extends ScriptItem {

	private String functionname;
	private String returnType = "any";
	private String access = "public";
	private List parameters;
	private Boolean atLeastOneParam = false;

	public FunctionInfo(CommonTree tree) {
		super(tree.getLine(), tree.getTokenStartIndex(), tree.getTokenStopIndex(), "ASTFunctionDeclaration");
		StringBuilder functionText = new StringBuilder();
		parameters = new ArrayList();
		for (Object kidTree : tree.getChildren()) {
			CommonTree kid = (CommonTree) kidTree;
			CommonToken token = (CommonToken) kid.getToken();
			switch (kid.getType()) {
			case CFScriptParser.FUNCTION_ACCESS:
				setStartPosition(token.getStartIndex());
				setAccess(kid.getChild(0).getText());
				functionText.append(kid.getChild(0).getText() + " ");
				break;
			case CFScriptParser.FUNCTION_RETURNTYPE:
				setReturnType(kid.getChild(0).getText());
				break;
			case CFScriptParser.FUNCTION_NAME:
				setStartPosition(kid.getTokenStartIndex());
				setFunctionName(kid.getChild(0).getText());
				functionText.append(kid.getChild(0).getText());
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
		paramaterAttributes.put("required", "false");
		paramaterAttributes.put("type", "any");
		paramaterAttributes.put("default", "");
		for (Object kidTree : tree.getChildren()) {
			CommonTree kid = (CommonTree) kidTree;
			switch (kid.getType()) {
			case CFScriptParser.REQUIRED:
				functionText.append(kid.getText() + " ");
				paramaterAttributes.put("required", "true");
				break;
			case CFScriptParser.IDENTIFIER:
				functionText.append(kid.getText() + " ");
				paramaterAttributes.put("name", kid.getText());
				if (tree.getChildCount() == 3 && kid.childIndex == 1
						&& tree.getChild(kid.childIndex + 1).getType() == CFScriptParser.EQUALSOP) {
					paramaterAttributes.put("default", tree.getChild(kid.childIndex + 2).getText());
				}
				functionText.append(kid.getText() + " " + kid.getType());
				break;
			case CFScriptParser.PARAMETER_TYPE:
				functionText.append(kid.getChild(0).getText() + " ");
				paramaterAttributes.put("type", kid.getChild(0).getText().toLowerCase());
				break;
			default:
				functionText.append(kid.getText() + " " + kid.getType());
			}
		}
		parameters.add(paramaterAttributes);
		trimStringBuilder(functionText);
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

	public List getParameters() {
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
