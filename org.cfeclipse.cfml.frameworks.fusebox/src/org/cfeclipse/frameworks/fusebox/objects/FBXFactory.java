package org.cfeclipse.frameworks.fusebox.objects;

public class FBXFactory {

	public FBXObject getObject(String fbxtype){
		FBXObject obj = null;
		if(fbxtype.equalsIgnoreCase("fuseaction")){
			obj = new FBXFuseAction();
		} 
		else if (fbxtype.equalsIgnoreCase("prefuseaction")){
			obj = new FBXPreFuseAction();
		}
		else if (fbxtype.equalsIgnoreCase("postfuseaction")){
			obj = new FBXPostFuseAction();
		}
		else if (fbxtype.equalsIgnoreCase("xfa")){
			obj = new FBXxfa();
		}
		else if (fbxtype.equalsIgnoreCase("include")){
			obj = new FBXInclude();
		}
		else if (fbxtype.equalsIgnoreCase("do")){
			obj = new FBXDo();
		}
		else if (fbxtype.equalsIgnoreCase("relocate")){
			obj = new FBXRelocate();
		}
		else if (fbxtype.equalsIgnoreCase("if")){
			obj = new FBXIf();
		}
		else if (fbxtype.equalsIgnoreCase("true")){
			obj = new FBXTrue();
		}
		else if (fbxtype.equalsIgnoreCase("false")){
			obj = new FBXFalse();
		}
		else if (fbxtype.equalsIgnoreCase("set")){
			obj = new FBXSet();
		}
		else if (fbxtype.equalsIgnoreCase("invoke")){
			obj = new FBXInvoke();
		}
		else {
			obj = new FBXObject();
		}
		
		return obj;
	}

}
