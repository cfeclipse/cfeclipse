package com.aptana.ide.editor.cfml;

import com.aptana.ide.core.IdeLog;
import com.aptana.ide.core.StringUtils;
import com.aptana.ide.editor.cfml.parsing.CFMLMimeType;
import com.aptana.ide.editor.css.CSSFileLanguageService;
import com.aptana.ide.editor.css.CSSLanguageEnvironment;
import com.aptana.ide.editor.css.parsing.CSSMimeType;
import com.aptana.ide.editor.html.HTMLLanguageEnvironment;
import com.aptana.ide.editor.html.parsing.HTMLMimeType;
import com.aptana.ide.editor.js.JSFileLanguageService;
import com.aptana.ide.editor.js.JSFileServiceFactory;
import com.aptana.ide.editor.js.JSLanguageEnvironment;
import com.aptana.ide.editor.js.parsing.JSMimeType;
import com.aptana.ide.editor.jscomment.JSCommentFileLanguageService;
import com.aptana.ide.editor.jscomment.parsing.JSCommentMimeType;
import com.aptana.ide.editor.scriptdoc.ScriptDocFileLanguageService;
import com.aptana.ide.editor.scriptdoc.parsing.ScriptDocMimeType;
import com.aptana.ide.editors.UnifiedEditorsPlugin;
import com.aptana.ide.editors.profiles.ProfileManager;
import com.aptana.ide.editors.unified.BaseFileLanguageService;
import com.aptana.ide.editors.unified.FileService;
import com.aptana.ide.editors.unified.IFileServiceFactory;
import com.aptana.ide.editors.unified.IFileSourceProvider;
import com.aptana.ide.editors.unified.LanguageRegistry;
import com.aptana.ide.editors.unified.ParentOffsetMapper;
import com.aptana.ide.editors.unified.errors.UnifiedErrorManager;
import com.aptana.ide.parsing.IParseState;
import com.aptana.ide.parsing.IParser;

public final class CFMLFileServiceFactory implements IFileServiceFactory {
	
	private static CFMLFileServiceFactory instance;
	private CFMLParser _parser;
	
	private CFMLFileServiceFactory(){
		ProfileManager profileManager = UnifiedEditorsPlugin.getDefault().getProfileManager();
		profileManager.addLanguageSupport(HTMLMimeType.MimeType, HTMLLanguageEnvironment.getInstance(), this);
		profileManager.addLanguageSupport(JSMimeType.MimeType, JSLanguageEnvironment.getInstance(), JSFileServiceFactory.getInstance());
		profileManager.addLanguageSupport(CSSMimeType.MimeType, CSSLanguageEnvironment.getInstance(), JSFileServiceFactory.getInstance());
	}
	
	public static IFileServiceFactory getInstance(){
		if (instance == null)
		{
			instance = new CFMLFileServiceFactory();
		}
		return instance;
	}
	
	public FileService createFileService(IFileSourceProvider sourceProvider) {
		IParser parser = this.createParser();
		IParseState parseState = null;
		
		FileService fileService = new FileService(parser, parseState, sourceProvider, CFMLMimeType.MimeType);
		ParentOffsetMapper parentMapper = new ParentOffsetMapper(fileService);
		BaseFileLanguageService languageService = new BaseFileLanguageService(fileService, parseState, parser, parentMapper);
		
		ParentOffsetMapper mapper = new ParentOffsetMapper(fileService);
		
		fileService.setErrorManager(new UnifiedErrorManager(fileService, CFMLMimeType.MimeType));
		fileService.addLanguageService(CFMLMimeType.MimeType, languageService);
		
		this.createChildFileServices(parser, fileService, parseState, mapper);
		
		
		return fileService;
	}
	
	protected void createChildFileServices(IParser parser, FileService fileService, IParseState parseState, ParentOffsetMapper mapper)
	{
		this.createCSSFileService(parser, fileService, parseState, mapper);
		this.createJSFileService(parser, fileService, parseState, mapper);
	}
	private void createCSSFileService(IParser parser, FileService fileService, IParseState parseState, ParentOffsetMapper mapper)
	{
		IParser cssParser = parser.getParserForMimeType(CSSMimeType.MimeType);
		CSSFileLanguageService cssService = new CSSFileLanguageService(fileService, parseState, cssParser, mapper);
		fileService.addLanguageService(CSSMimeType.MimeType, cssService);
	}
	private void createJSFileService(IParser parser, FileService fileService, IParseState parseState, ParentOffsetMapper mapper)
	{
		IParser jsParser = parser.getParserForMimeType(JSMimeType.MimeType);
		JSFileLanguageService jsfs = new JSFileLanguageService(fileService, parseState, jsParser, mapper);
		fileService.addLanguageService(JSMimeType.MimeType, jsfs);

		FileService jsContext = (FileService) jsfs.getFileContext();

		if (jsParser != null)
		{
			IParser scriptDocParser = jsParser.getParserForMimeType(ScriptDocMimeType.MimeType);
			ScriptDocFileLanguageService scriptDocService = new ScriptDocFileLanguageService(fileService, parseState, scriptDocParser, mapper);
			jsContext.addLanguageService(ScriptDocMimeType.MimeType, scriptDocService);

			IParser jsCommentParser = jsParser.getParserForMimeType(JSCommentMimeType.MimeType);
			JSCommentFileLanguageService jsCommentService = new JSCommentFileLanguageService(fileService, parseState, jsCommentParser, mapper);
			jsContext.addLanguageService(JSCommentMimeType.MimeType, jsCommentService);
		}
	}
	
	private IParser createParser() {
		IParser parser = LanguageRegistry.getParser(CFMLMimeType.MimeType);
		if(parser == null){
			IdeLog.logError(CFMLPlugin.getDefault(), StringUtils.format("UUnable to create parser. Parser for MIME type {0} not registered in LanguageRegistry", CFMLMimeType.MimeType));
			
		}
		return parser;
	}

}
