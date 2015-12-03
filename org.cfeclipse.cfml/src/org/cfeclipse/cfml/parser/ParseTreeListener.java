package org.cfeclipse.cfml.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.cfeclipse.cfml.parser.docitems.DocItem;
import org.cfeclipse.cfml.parser.docitems.ScriptItem;

import cfml.CFSCRIPTParser;
import cfml.CFSCRIPTParserBaseListener;
import cfml.CFSCRIPTParser.AssignmentExpressionContext;
import cfml.CFSCRIPTParser.ComponentAttributeContext;
import cfml.CFSCRIPTParser.ComponentDeclarationContext;
import cfml.CFSCRIPTParser.FunctionCallContext;
import cfml.CFSCRIPTParser.ImplicitArrayContext;
import cfml.CFSCRIPTParser.ImplicitStructContext;
import cfml.CFSCRIPTParser.JavaCallMemberExpressionContext;
import cfml.CFSCRIPTParser.LocalAssignmentExpressionContext;
import cfml.CFSCRIPTParser.NewComponentExpressionContext;
import cfml.CFSCRIPTParser.PropertyStatementContext;
import cfml.CFSCRIPTParser.SwitchStatementContext;

public class ParseTreeListener extends CFSCRIPTParserBaseListener {
    private final List<String> ruleNames = Arrays.asList(CFSCRIPTParser.ruleNames);
	private DocItem currentNode;
	private DocItem childNode;
	private FunctionInfo currentFunction;

    public ParseTreeListener(DocItem rootNode) {
    	this.currentNode = rootNode;
    }
    
    private String valueOrDefault(ParserRuleContext rule, String defaultValue) {
    	if(rule != null)
    		return rule.getText();
    	return defaultValue;
    }
    
    @Override
    public void enterComponentDeclaration(ComponentDeclarationContext ctx) {
    	super.enterComponentDeclaration(ctx);
    	List<ComponentAttributeContext> name = ctx.componentAttribute();
    	String attribs = "";
    	for(ComponentAttributeContext attrib : ctx.componentAttribute()) {
    		attribs += attrib.getText();
    	}
		childNode = new ScriptItem(ctx, "cfcomponent");
		childNode.setItemData("component (" + attribs +")");
		currentNode.addChild(childNode);
		currentNode = childNode;
    }
    @Override
    public void enterComponentAttribute(ComponentAttributeContext ctx) {
    	super.enterComponentAttribute(ctx);
		childNode = new ScriptItem(ctx, "ASTComponentAttribute");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);

    }
    @Override
    public void exitComponentDeclaration(ComponentDeclarationContext ctx) {
    	super.exitComponentDeclaration(ctx);
		currentNode = currentNode.getParent();
    }
    
    @Override 
    public void enterFunctionDeclaration(cfml.CFSCRIPTParser.FunctionDeclarationContext ctx) {
    	super.exitFunctionDeclaration(ctx);
    	currentFunction = new FunctionInfo(ctx);
    	currentNode.addChild(currentFunction);
    	currentNode = currentFunction;
    };

	@Override 
	public void exitFunctionDeclaration(cfml.CFSCRIPTParser.FunctionDeclarationContext ctx) {
		super.exitFunctionDeclaration(ctx);
		currentNode = currentNode.getParent();
	};
    
	@Override
	public void enterPropertyStatement(PropertyStatementContext ctx) {
		super.enterPropertyStatement(ctx);
		childNode = new ScriptItem(ctx, "ASTPropertyStatement");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void exitNewComponentExpression(NewComponentExpressionContext ctx) {
		super.exitNewComponentExpression(ctx);
		childNode = new ScriptItem(ctx, "cfcomponent");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}

	@Override
	public void enterImplicitArray(ImplicitArrayContext ctx) {
		super.exitImplicitArray(ctx);
		childNode = new ScriptItem(ctx, "ASTUnassigned");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	@Override
	public void enterImplicitStruct(ImplicitStructContext ctx) {
		super.exitImplicitStruct(ctx);
		childNode = new ScriptItem(ctx, "ASTUnassigned");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void enterJavaCallMemberExpression(JavaCallMemberExpressionContext ctx) {
		super.exitJavaCallMemberExpression(ctx);
		childNode = new ScriptItem(ctx, "Call");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void enterFunctionCall(FunctionCallContext ctx) {
		super.exitFunctionCall(ctx);
		//ctx.identifierOrReservedWord().getText();
		childNode = new ScriptItem(ctx, "FunctionCall");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void enterAssignmentExpression(AssignmentExpressionContext ctx) {
		super.exitAssignmentExpression(ctx);
		childNode = new ScriptItem(ctx, "ASTIdentifier");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void enterLocalAssignmentExpression(LocalAssignmentExpressionContext ctx) {
		super.exitLocalAssignmentExpression(ctx);
		childNode = new ScriptItem(ctx, "ASTVarDeclaration");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
	
	@Override
	public void enterSwitchStatement(SwitchStatementContext ctx) {
		super.enterSwitchStatement(ctx);
		childNode = new ScriptItem(ctx, "ASTSwitchStatement");
		childNode.setItemData(ctx.getText());
		currentNode.addChild(childNode);
	}
}
