/*
 * Created on Jan 30, 2004
 *
 * The MIT License
 * Copyright (c) 2004 Rob Rohan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the Software 
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 */
package com.rohanclan.coldfusionmx.editors;

import java.util.Set;
import java.util.HashSet;
import com.rohanclan.coldfusionmx.dictionary.SyntaxDictionary;
import com.rohanclan.coldfusionmx.dictionary.SyntaxDictionaryInterface;

/**
 * @author Rob
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CFSyntaxDictionary extends SyntaxDictionary implements SyntaxDictionaryInterface {
	//protected static Map syntaxelements;
	//protected static Map functions;
	protected static Set operators;
	protected static Set scriptkeywords;
	
	public CFSyntaxDictionary()
	{
		super();
		buildTagSyntax();
		buildFunctionSyntax();
		
		operators = new HashSet();
		buildOperatorSyntax();
		
		scriptkeywords = new HashSet();
		buildScriptKeywordSyntax();
	}
	
	
	/**
	 * get all top level language elements (tags)(lowercase only)
	 * @return
	 */
	/* public Set getAllElements()
	{
		return syntaxelements.entrySet();
		//return syntaxelements.keySet();
	} */

	/* public Set getFilteredElements(String start)
	{
		return limitSet(getAllElements(),start);
	} */

	/* public Set getFilteredAttributes(String tag, String start)
	{
		return limitSet(getElementAttributes(tag),start);
	} */
	
	/**
	 * get an elements attributes (mixed case)
	 *
	 */
	/* public Set getElementAttributes(String elementname)
	{
		//Set st = (Set)syntaxelements.get(elementname);
		Procedure p = (Procedure)syntaxelements.get(elementname);
		Set st = p.getParameters();
		return st;
	} */
	
	/**
	 * gets any operators (eq, or, and) (lowercase only)
	 * @param elementname
	 * @return
	 */
	public Set getOperators()
	{
		return operators;
	}
	
	/**
	 * gets cfscript specific keywords (if, while, etc);
	 * @return
	 */
	public Set getScriptKeywords()
	{
		return scriptkeywords;
	}
	
	/**
	 * gets all the functions (lowercase only)
	 * @param elementname
	 * @return
	 */
	/* public Set getFunctions()
	{
		return functions.keySet();
	} */

	/**
	 * retuns a functions usage
	 * @param functionname
	 * @return
	 */
	/* public String getFunctionUsage(String functionname)
	{
		return (String)functions.get(functionname.toLowerCase());
	} */

	
	///////////////////////////////////////////////////////////////////////////
	/** build all the cfscript keywords */
	protected static void buildScriptKeywordSyntax()
	{
		scriptkeywords.add("for");
		scriptkeywords.add("if");
		scriptkeywords.add("else");
		scriptkeywords.add("while");
		scriptkeywords.add("return");
		scriptkeywords.add("function");
		scriptkeywords.add("var");
		scriptkeywords.add("case");
		scriptkeywords.add("do");
		scriptkeywords.add("try");
		scriptkeywords.add("catch");
		scriptkeywords.add("continue");
		scriptkeywords.add("switch");
		scriptkeywords.add("default");
		scriptkeywords.add("break");
		scriptkeywords.add("true");
		scriptkeywords.add("false");
		/* scriptkeywords.add("=");
		scriptkeywords.add("*");
		scriptkeywords.add("+");
		scriptkeywords.add("-");
		scriptkeywords.add("/"); */
	}
	
	/** build all the operators in the language */
	protected static void buildOperatorSyntax()
	{
		operators.add("gt");
		operators.add("lt");
		operators.add("gte");
		operators.add("lte");
		operators.add("eq");
		operators.add("neq");
		operators.add("not");
		operators.add("and");
		operators.add("or");
	}
	/** build all the functions in the language */
	protected void buildFunctionSyntax()
	{
		/* functions.put("abs","number Abs(number)");
		functions.put("acos","number ACos(number)");
		functions.put("arrayappend","boolean ArrayAppend(array, value)");
		functions.put("arrayavg","number ArrayAvg(array)");
		functions.put("arrayclear","boolean ArrayClear(array)");
		functions.put("arraydeleteat","boolean ArrayDeleteAt(array, position)");
		functions.put("arrayinsertat","boolean ArrayInsertAt(array, position, value)");
		functions.put("arrayisempty","boolean ArrayIsEmpty(array)");
		functions.put("arraylen","number ArrayLen(array)");
		functions.put("arraymax","number ArrayMax(array)");
		functions.put("arraymin","number ArrayMin(array)");
		functions.put("arraynew","array ArrayNew(dimension)");
		functions.put("arrayprepend","boolean ArrayPrepend(array, value)");
		functions.put("arrayresize","boolean ArrayResize(array, minimum_size)");
		functions.put("arrayset","boolean ArraySet(array, start_pos, end_pos, value)");
		functions.put("arraysort","boolean ArraySort(array, sort_type [, sort_order ])");
		functions.put("arraysum","number ArraySum(array)");
		functions.put("arrayswap","boolean ArraySwap(array, position1, position2) ");
		functions.put("arraytolist","string ArrayToList(array [, delimiter ])");
		functions.put("asc","number Asc(string)");
		functions.put("asin","number ASin(number)");
		functions.put("atn","number Atn(number)");
		functions.put("bitand","number BitAnd(number1, number2)");
		functions.put("bitmaskclear","number BitMaskClear(number, start, length)");
		functions.put("bitmaskread","number BitMaskRead(number, start, length)");
		functions.put("bitmaskset","number BitMaskSet(number, mask, start, length)");
		functions.put("bitnot","number BitNot(number)");
		functions.put("bitor","number BitOr(number1, number2)");
		functions.put("bitshln","number BitSHLN(number, count)");
		functions.put("bitshrn","number BitSHRN(number, count)");
		functions.put("bitxor","number BitXor(number1, number2)");
		functions.put("ceiling","number Ceiling(number)");
		functions.put("chr","char Chr(number)");
		functions.put("cjustify","string Cjustify(string, length)");
		functions.put("compare","number Compare(string1, string2)");
		functions.put("comparenocase","number CompareNoCase(string1, string2)");
		functions.put("cos","number Cos(number)");
		functions.put("createdate","datetime CreateDate(year, month, day)");
		functions.put("createdatetime","datetime CreateDateTime(year, month, day, hour, minute, second)");
		functions.put("createobject","object CreateObject(type, class) || object CreateObject(type, urltowsdl) || object CreateObject(type, class, context, serverName) || object CreateObject(type, component-name) || object CreateObject(type, context, class, locale)");
		functions.put("createodbcdate","date CreateODBCDate(date)");
		functions.put("createodbcdatetime","datetime CreateODBCDateTime(date)");
		functions.put("createodbctime","time CreateODBCTime(date)");
		functions.put("createtime","time CreateTime(hour, minute, second)");
		functions.put("createtimespan","datetime CreateTimeSpan(days, hours, minutes, seconds)");
		functions.put("createuuid","uuid CreateUUID()");
		functions.put("dateadd","datetime DateAdd('datepart', number, 'date')");
		functions.put("datecompare","number DateCompare('date1', 'date2' [, 'datePart'])");
		functions.put("dateconvert","time DateConvert('conversion-type', 'date')");
		functions.put("datediff","number DateDiff('datepart', 'date1', 'date2')");
		functions.put("dateformat","string DateFormat('date' [, 'mask' ])");
		functions.put("datepart","number DatePart('datepart', 'date')");
		functions.put("day","number Day('date')");
		functions.put("dayofweek","number DayOfWeek('date')");
		functions.put("dayofweekasstring","string DayOfWeekAsString(day_of_week)");
		functions.put("dayofyear","number DayOfYear('date')");
		functions.put("daysinmonth","number DaysInMonth('date')");
		functions.put("daysinyear","number DaysInYear('date')");
		functions.put("de","string DE(string)");
		functions.put("decimalformat","string DecimalFormat(number)");
		functions.put("decrementvalue","number DecrementValue(number)");
		functions.put("decrypt","string Decrypt(encrypted_string, seed)");
		functions.put("deleteclientvariable","boolean DeleteClientVariable('name')");
		functions.put("directoryexists","boolean DirectoryExists(absolute_path)");
		functions.put("dollarformat","string DollarFormat(number)");
		functions.put("duplicate","clone Duplicate(variable_name)");
		functions.put("encrypt","string Encrypt(string, seed)");
		functions.put("evaluate","object Evaluate(string_expression1 [, string_expression2 [, ... ] ] )");
		functions.put("exp","number Exp(number)");
		functions.put("expandpath","string ExpandPath(relative_path)");
		functions.put("fileexists","boolean FileExists(absolute_path)");
		functions.put("find","number Find(substring, string [, start ])");
		functions.put("findnocase","number FindNoCase(substring, string [, start ])");
		functions.put("findoneof","number FindOneOf(set, string [, start ])");
		functions.put("firstdayofmonth","number FirstDayOfMonth(date)");
		functions.put("fix","number Fix(number)");
		functions.put("formatbasen","string FormatBaseN(number, radix)");
		functions.put("getauthuser","string GetAuthUser()");
		functions.put("getbasetagdata","object GetBaseTagData(tagname [, instancenumber ] )");
		functions.put("getbasetaglist","string GetBaseTagList()");
		functions.put("getbasetemplatepath","string GetBaseTemplatePath()");
		functions.put("getclientvariableslist","string GetClientVariablesList()");
		functions.put("getcurrenttemplatepath","string GetCurrentTemplatePath()");
		functions.put("getdirectoryfrompath","string GetDirectoryFromPath(path)");
		functions.put("getexception","exception getException(object)");
		functions.put("getfilefrompath","string GetFileFromPath(path)");
		functions.put("getfunctionlist","struct GetFunctionList()");
		functions.put("gethttprequestdata","struct GetHttpRequestData()");
		functions.put("gethttptimestring","string GetHttpTimeString(date_time_object)");
		functions.put("getk2serverdoccount","number GetK2ServerDocCount()");
		functions.put("getk2serverdoccountlimit","number GetK2ServerDocCountLimit()");
		functions.put("getlocale","string GetLocale()");
		functions.put("getmetadata","struct GetMetaData(object) || struct GetMetaData(this)");
		functions.put("getmetricdata","struct GetMetricData(mode)");
		functions.put("pagecontext","PageContext GetPageContext()");
		functions.put("getprofilesections","struct GetProfileSections(iniFile)");
		functions.put("getprofilestring","string GetProfileString(iniPath, section, entry)");
		functions.put("getservicesettings","struct GetServiceSettings()");
		functions.put("gettempdirectory","string GetTempDirectory()");
		functions.put("gettempfile","string GetTempFile(dir, prefix)");
		functions.put("gettickcount","number GetTickCount()");
		functions.put("gettimezoneinfo","struct GetTimeZoneInfo()");
		functions.put("gettoken","string GetToken(string, index [, delimiters ])");
		functions.put("hash","string Hash(string)");
		functions.put("hour","number Hour(date)");
		functions.put("htmlcodeformat","string HTMLCodeFormat(string [, version ])");
		functions.put("htmleditformat","string HTMLEditFormat(string [, version ])");
		functions.put("iif","object IIf(condition, string_expression1, string_expression2) ");
		functions.put("incrementvalue","number IncrementValue(number)");
		functions.put("inputbasen","string InputBaseN(string, radix)");
		functions.put("insert","string Insert(substring, string, position)");
		functions.put("int","string Int(number)");
		functions.put("isarray","boolean IsArray(value [, number ])");
		functions.put("isbinary","boolean IsBinary(value)");
		functions.put("isboolean","boolean IsBoolean(value)");
		functions.put("iscustomfunction","boolean IsCustomFunction('name')");
		functions.put("isdate","boolean IsDate(string)");
		functions.put("isdebugmode","boolean IsDebugMode()");
		functions.put("isdefined","boolean IsDefined('variable_name')");
		functions.put("isk2serverabroker","boolean IsK2ServerABroker()");
		functions.put("isk2serverdoccountexceeded","boolean IsK2ServerDocCountExceeded()");
		functions.put("isk2serveronline","boolean IsK2ServerOnline()");
		functions.put("isleapyear","boolean IsLeapYear(year)");
		functions.put("isnumeric","boolean IsNumeric(string)");
		functions.put("isnumericdate","boolean IsNumericDate(number)");
		functions.put("isobject","boolean IsObject(value [, type [, ... ]])");
		functions.put("isquery","boolean IsQuery(value)");
		functions.put("issimplevalue","boolean IsSimpleValue(value)");
		functions.put("isstruct","boolean IsStruct(variable)");
		functions.put("isuserinrole","boolean IsUserInRole('role_name')");
		functions.put("iswddx","boolean IsWDDX(value)");
		functions.put("isxmldoc","boolean IsXmlDoc(value)");
		functions.put("isxmlelement","boolean IsXmlElement(value)");
		functions.put("isxmlroot","boolean IsXmlRoot(value)");
		functions.put("javacast","object JavaCast(type, variable)");
		functions.put("jsstringformat","string JSStringFormat(string)");
		functions.put("lcase","string LCase(string)");
		functions.put("left","number Left(string, count)");
		functions.put("len","number Len(string) || number Len(binary_object)");
		functions.put("listappend","list ListAppend(list, value [, delimiters ])");
		functions.put("listchangedelims","list ListChangeDelims(list, new_delimiter [, delimiters ])");
		functions.put("listcontains","number ListContains(list, substring [, delimiters ])");
		functions.put("listcontainsnocase","number ListContainsNoCase(list, substring [, delimiters ])");
		functions.put("listdeleteat","list ListDeleteAt(list, position [, delimiters ])");
		functions.put("listfind","number ListFind(list, value [, delimiters ])");
		functions.put("listfindnocase","number ListFindNoCase(list, value [, delimiters ])");
		functions.put("listfirst","string ListFirst(list [, delimiters ])");
		functions.put("listgetat","number ListGetAt(list, position [, delimiters ])");
		functions.put("listinsertat","list ListInsertAt(list, position, value [, delimiters ])");
		functions.put("listlast","string ListLast(list [, delimiters ])");
		functions.put("listlen","number ListLen(list [, delimiters ])");
		functions.put("listprepend","list ListPrepend(list, value [, delimiters ])");
		functions.put("listqualify","list ListQualify(list, qualifier [, delimiters ] [, elements ])");
		functions.put("listrest","list ListRest(list [, delimiters ])");
		functions.put("listsetat","list ListSetAt(list, position, value [, delimiters ])");
		functions.put("listsort","list ListSort(list, sort_type [, sort_order] [, delimiters ])");
		functions.put("listtoarray","array ListToArray(list [, delimiters ])");
		functions.put("listvaluecount","number ListValueCount(list, value [, delimiters ])");
		functions.put("listvaluecountnocase","number ListValueCountNoCase(list, value [, delimiters ])");
		functions.put("ljustify","string LJustify(string, length)");
		functions.put("log","number Log(number)");
		functions.put("log10","number Log10(number)");
		functions.put("lscurrencyformat","string LSCurrencyFormat(number [, type ])");
		functions.put("lsdateformat","string LSDateFormat(date [, mask ])");
		functions.put("lseurocurrencyformat","string LSEuroCurrencyFormat(currency-number [, type ])");
		functions.put("lsiscurrency","boolean LSIsCurrency(string)");
		functions.put("lsisdate","boolean LSIsDate(string)");
		functions.put("lsisnumeric","boolean LSIsNumeric(string)");
		functions.put("lsnumberformat","string LSNumberFormat(number [, mask ])");
		functions.put("lsparsecurrency","string LSParseCurrency(string)");
		functions.put("lsparsedatetime","string LSParseDateTime(date/time-string)");
		functions.put("lsparseeurocurrency","string LSParseEuroCurrency(currency-string)");
		functions.put("lsparsenumber","string LSParseNumber(string)");
		functions.put("lstimeformat","string LSTimeFormat(time [, mask ])");
		functions.put("ltrim","string LTrim(string)");
		functions.put("max","number Max(number1, number2)");
		functions.put("mid","string Mid(string, start, count)");
		functions.put("min","number Min(number1, number2)");
		functions.put("minute","number Minute(date)");
		functions.put("month","number Month(date)");
		functions.put("monthasstring","string MonthAsString(month_number)");
		functions.put("now","datetime Now()");
		functions.put("numberformat","string NumberFormat(number [, mask ])");
		functions.put("paragraphformat","string ParagraphFormat(string)");
		functions.put("parsedatetime","string ParseDateTime(date/time-string [, pop-conversion ] )");
		functions.put("pi","number Pi()");
		functions.put("preservesinglequotes","void PreserveSingleQuotes(variable)");
		functions.put("quarter","number Quarter(date)");
		functions.put("queryaddcolumn","number QueryAddColumn(query, column-name, array-name)");
		functions.put("queryaddrow","number QueryAddRow(query [, number ])");
		functions.put("querynew","query QueryNew(columnlist)");
		functions.put("querysetcell","boolean QuerySetCell(query, column_name, value [, row_number ])");
		functions.put("quotedvaluelist","list QuotedValueList(query.column [, delimiter ])");
		functions.put("rand","number Rand()");
		functions.put("randomize","number Randomize(number)");
		functions.put("randrange","number RandRange(number1, number2)");
		functions.put("refind","number REFind(reg_expression, string [, start ][, false ]) || struct REFind(reg_expression, string [, start ][, true ])");
		functions.put("refindnocase","number REFindNoCase(reg_expression, string [, start ][, false ]) || struct REFindNoCase(reg_expression, string [, start ][, true ])");
		functions.put("removechars","string RemoveChars(string, start, count)");
		functions.put("repeatstring","string RepeatString(string, count)");
		functions.put("replace","string Replace(string, substring1, substring2 [, scope ])");
		functions.put("replacelist","string ReplaceList(string, list1, list2)");
		functions.put("replacenocase","string ReplaceNoCase(string, substring1, substring2 [, scope ])");
		functions.put("rereplace","string REReplace(string, reg_expression, substring [, scope ])");
		functions.put("rereplacenocase","string REReplaceNoCase(string, reg_expression, substring [, scope ])");
		functions.put("reverse","string Reverse(string)");
		functions.put("right","number Right(string, count)");
		functions.put("rjustify","string RJustify(string, length)");
		functions.put("round","number Round(number)");
		functions.put("rtrim","string RTrim(string)");
		functions.put("second","number Second(date)");
		functions.put("setencoding","void SetEncoding(scope_name,charset)");
		functions.put("setlocale","string SetLocale(new_locale)");
		functions.put("setprofilestring","string SetProfileString(iniPath, section, entry, value)");
		functions.put("setvariable","object SetVariable(name, value)");
		functions.put("sgn","number Sgn(number)");
		functions.put("sin","number Sin(number)");
		functions.put("spanexcluding","string SpanExcluding(string, set)");
		functions.put("spanincluding","string SpanIncluding(string, set)");
		functions.put("sqr","number Sqr(number)");
		functions.put("stripcr","string StripCR(string)");
		functions.put("structappend","boolean StructAppend(struct1, struct2, overwriteFlag)");
		functions.put("structclear","boolean StructClear(structure)");
		functions.put("structcopy","struct StructCopy(structure)");
		functions.put("structcount","number StructCount(structure)");
		functions.put("structdelete","struct StructDelete(structure, key [, indicatenotexisting ])");
		functions.put("structfind","object StructFind(structure, key)");
		functions.put("structfindkey","array StructFindKey(top, value, scope)");
		functions.put("structfindvalue","array StructFindValue(top, value [, scope])");
		functions.put("structget","object StructGet(pathDesired)");
		functions.put("structinsert","boolean StructInsert(structure, key, value [, allowoverwrite ])");
		functions.put("structisempty","boolean StructIsEmpty(structure)");
		functions.put("structkeyarray","array StructKeyArray(structure)");
		functions.put("structkeyexists","boolean StructKeyExists(structure, 'key')");
		functions.put("structkeylist","list StructKeyList(structure [, delimiter])");
		functions.put("structnew","struct StructNew()");
		functions.put("structsort","array StructSort(base, sortType, sortOrder, pathToSubElement)");
		functions.put("structupdate","boolean StructUpdate(structure, key, value)");
		functions.put("tan","number Tan(number)");
		functions.put("timeformat","string TimeFormat(time [, mask ])");
		functions.put("tobase64","string ToBase64(string [, encoding]) || string ToBase64(binary_object [, encoding])");
		functions.put("tobinary","object ToBinary(string_in_Base64) || object ToBinary(binary_value)");
		functions.put("tostring","string ToString(any_value[, encoding])");
		functions.put("trim","string Trim(string)");
		functions.put("ucase","string UCase(string)");
		functions.put("urldecode","string URLDecode(urlEncodedString[, charset])");
		functions.put("urlencodedformat","string URLEncodedFormat(string [, charset ])");
		functions.put("urlsessionformat","url URLSessionFormat(request_URL)");
		functions.put("val","number Val(string)");
		functions.put("valuelist","list ValueList(query.column [, delimiter ])");
		functions.put("week","number Week(date)");
		functions.put("writeoutput","void WriteOutput(string)");
		functions.put("xmlchildpos","number XmlChildPos(elem, childName, N)");
		functions.put("xmlelemnew","xmlobject XmlElemNew(xmlObj, childName)");
		functions.put("xmlformat","string XmlFormat(string)");
		functions.put("xmlnew","xmlobject XmlNew([caseSensitive])");
		functions.put("xmlparse","xmlobject XmlParse(xmlString [, caseSensitive ] )");
		functions.put("xmlsearch","array XmlSearch(xmlDoc, xPathString)");
		functions.put("xmltransform","string XmlTransform(xmlString | xmlObj, xslString)");
		functions.put("year","number Year(date)");
		functions.put("yesnoformat","boolean YesNoFormat(value)");
		*/
	}


	/** 
	 * make all the elements for this language 
	 */
	protected void buildTagSyntax()
	{
		/* Tag tag = new Tag("abort",true,Byte.parseByte("1"));
		Attribute attr = new Attribute("showmessage","boolean");
		attr.addValue(new Value("true"));
		attr.addValue(new Value("false"));
		tag.addAttribute(attr);
		syntaxelements.put("abort",tag); */
		
		/* Set attrs = new HashSet();
		attrs.add("showMessage");
		syntaxelements.put("abort",attrs);
		
		attrs = new HashSet();
		attrs.add("appletSource");
		attrs.add("name");
		attrs.add("height");
		attrs.add("width");
		attrs.add("vSpace");
		attrs.add("hSpace");
		attrs.add("align");
		attrs.add("notSupported");
		attrs.add("param_1");
		attrs.add("param_2");
		attrs.add("param_n");
		syntaxelements.put("applet",attrs);
		*/

		/* <cfapplication 
		 name = "application_name"
		 clientManagement = "Yes" or "No"
		 clientStorage = "datasource_name" or "Registry" or "Cookie" 
		 setClientCookies = "Yes" or "No" 
		 sessionManagement = "Yes" or "No"
		 sessionTimeout = #CreateTimeSpan(days, hours, minutes, seconds)#
		 applicationTimeout = #CreateTimeSpan(days, hours,minutes, seconds)#
		 setDomainCookies = "Yes" or "No"> */
		/* attrs = new HashSet();
		attrs.add("name");
		attrs.add("clientManagement");
		attrs.add("clientStorage");
		attrs.add("setClientCookies");
		attrs.add("sessionManagement");
		attrs.add("sessionTimeout");
		attrs.add("applicationTimeout");
		attrs.add("setDomainCookies");
		syntaxelements.put("application",attrs);
		*/
		
		/*<cfargument 
		 name="..." 
		 type="..." 
		 required="..." 
		 default="..." 
		 ...> */
		/* attrs = new HashSet();
		attrs.add("name");
		attrs.add("type");
		attrs.add("required");
		attrs.add("default");
		syntaxelements.put("argument",attrs);
		*/
		
		/*<cfassociate 
		 baseTag = "base_tag_name"
		 dataCollection = "collection_name"> */
		/*attrs = new HashSet();
		attrs.add("baseTag");
		attrs.add("dataCollection");
		syntaxelements.put("associate",attrs);
		*/
		
		/* <cfbreak> */
		/* attrs = new HashSet();
		syntaxelements.put("break",attrs);
		*/
		
		/*<cfcache 
		 action = "action"
		 directory = "directory_name"
		 timespan = "value"
		 expireURL = "wildcarded_URL_reference"
		 username = "username"
		 password = "password"
		 port = "port_number"
		 protocol = "protocol">*/ 
		/*attrs = new HashSet();
		attrs.add("action");
		attrs.add("directory");
		attrs.add("timespan");
		attrs.add("expireURL");
		attrs.add("username");
		attrs.add("password");
		attrs.add("port");
		attrs.add("protocol");
		syntaxelements.put("cache",attrs);
		*/
		
		/* <cfcase 
		 value = "value" 
		 delimiters = "delimiters">
		 HTML and CFML tags
		 </cfcase> */
		/* attrs = new HashSet();
		attrs.add("value");
		attrs.add("delimiters");
		syntaxelements.put("case",attrs);
		*/
		
		/* <cfcatch type = "exceptiontype">
		 Exception processing code here
		 </cfcatch> */
		/* attrs = new HashSet();
		attrs.add("type");
		syntaxelements.put("catch",attrs);
		*/
		
		/* <cfchart 
		 format = "flash, jpg, png" 
		 chartHeight = "integer number of pixels" 
		 chartWidth = "integer number of pixels" 
		 scaleFrom = "integer minimum value" 
		 scaleTo = "integer maximum value" 
		 showXGridlines = "yes" or "no" 
		 showYGridlines = "yes" or "no" 
		 gridlines = "integer number of lines" 
		 seriesPlacement = "default, cluster, stacked, percent" 
		 foregroundColor = "Hex value or Web color" 
		 dataBackgroundColor = "Hex value or Web color" 
		 borderBackgroundColor = "Hex value or Web color" 
		 showBorder = "yes" or "no" 
		 font = "font name" 
		 fontSize = "integer font size" 
		 fontBold = "yes" or "no" 
		 fontItalic = "yes" or "no" 
		 labelFormat = "number, currency, percent, date" 
		 xAxisTitle = "title text" 
		 yAxisTitle = "title text" 
		 sortXAxis = "yes/no" 
		 show3D = "yes" or "no" 
		 xOffset = "number between -1 and 1" 
		 yOffset = "number between -1 and 1" 
		 rotated = "yes/no" 
		 showLegend = "yes/no" 
		 tipStyle = "MouseDown, MouseOver, Off" 
		 tipBGColor = "hex value or web color" 
		 showMarkers = "yes" or "no" 
		 markerSize = "integer number of pixels" 
		 pieSliceStyle = "solid, sliced" 
		 url = "onClick destination page" 
		 name = "String"
		 </cfchart> */ 
		/* attrs = new HashSet();
		attrs.add("format");
		attrs.add("chartHeight");
		attrs.add("chartWidth");
		attrs.add("scaleFrom");
		attrs.add("scaleTo");
		attrs.add("showXGridlines");
		attrs.add("showYGridlines");
		attrs.add("gridlines");
		attrs.add("seriesPlacement");
		attrs.add("foregroundColor");
		attrs.add("dataBackgroundColor");
		attrs.add("borderBackgroundColor");
		attrs.add("showBorder");
		attrs.add("font");
		attrs.add("fontSize");
		attrs.add("fontBold");
		attrs.add("fontItalic");
		attrs.add("labelFormat");
		attrs.add("xAxisTitle");
		attrs.add("yAxisTitle");
		attrs.add("sortXAxis");
		attrs.add("show3D");
		attrs.add("xOffset");
		attrs.add("yOffset");
		attrs.add("rotated");
		attrs.add("showLegend");
		attrs.add("tipStyle");
		attrs.add("tipBGColor");
		attrs.add("showMarkers");
		attrs.add("markerSize");
		attrs.add("pieSliceStyle");
		attrs.add("url");
		attrs.add("name");
		syntaxelements.put("chart",attrs);
		*/
		
		/*<cfchartdata 
		 item = "text" 
		 value = "number">*/ 
		/* attrs = new HashSet();
		attrs.add("item");
		attrs.add("value");
		syntaxelements.put("chartdata",attrs);
		*/

		/* <cfchartseries 
		 type="type"
		 query="queryName"
		 itemColumn="queryColumn"
		 valueColumn="queryColumn"
		 seriesLabel="Label Text"
		 seriesColor="Hex value or Web color" 
		 paintStyle="plain, raise, shade, light"
		 markerStyle="style"
		 colorlist = "list">
		 </cfchartseries> */ 
		/* attrs = new HashSet();
		attrs.add("type");
		attrs.add("query");
		attrs.add("itemColumn");
		attrs.add("valueColumn");
		attrs.add("seriesLabel");
		attrs.add("seriesColor");
		attrs.add("paintStyle");
		attrs.add("paintStyle");
		attrs.add("colorlist");
		syntaxelements.put("chartseries",attrs);
		*/

		/* <cfcol 
		 header = "column_header_text"
		 width = "number_indicating_width_of_column"
		 align = "Left" or "Right" or "Center"
		 text = "column_text"> */ 
		/* attrs = new HashSet();
		attrs.add("header");
		attrs.add("width");
		attrs.add("align");
		attrs.add("text");
		syntaxelements.put("col",attrs);
		*/

		/* <cfcollection 
		 action = "action"
		 collection = "collection_name"
		 path = "path_to_verity_collection"
		 language = "language"
		 name = "queryname" > */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("collection");
//		attrs.add("path");
//		attrs.add("language");
//		attrs.add("name");
//		syntaxelements.put("collection",attrs);
		
		/* <cfcomponent
		 extends ="anotherComponent"
		 output = "yes" or "no"> */
//		attrs = new HashSet();
//		attrs.add("extends");
//		attrs.add("output");
//		syntaxelements.put("component",attrs);

		/* <cfcontent 
		 type = "file_type"
		 deleteFile = "Yes" or "No"
		 file = "filename"
		 reset = "Yes" or "No"> */ 
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("deleteFile");
//		attrs.add("file");
//		attrs.add("reset");
//		syntaxelements.put("content",attrs);
		
		/* <cfcookie 
		 name = "cookie_name"
		 value = "text"
		 expires = "period"
		 secure = "Yes" or "No"
		 path = "url"
		 domain = ".domain"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("value");
//		attrs.add("expires");
//		attrs.add("secure");
//		attrs.add("path");
//		attrs.add("domain");
//		syntaxelements.put("cookie",attrs);
		
		/* <cfdefaultcase> */
//		attrs = new HashSet();
//		syntaxelements.put("defaultcase",attrs);
		
		/* <cfdirectory 
		 action = "directory action"
		 directory = "directory name"
		 name = "query name"
		 filter = "list filter"
		 mode = "permission"
		 sort = "sort specification"
		 newDirectory = "new directory name"> */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("directory");
//		attrs.add("name");
//		attrs.add("filter");
//		attrs.add("mode");
//		attrs.add("sort");
//		attrs.add("newDirectory");
//		syntaxelements.put("directory",attrs);
		
		/* <cfdump 
		 var = #variable#
		 expand = "Yes or No" 
		 label = "text"> */ 
//		attrs = new HashSet();
//		attrs.add("var");
//		attrs.add("expand");
//		attrs.add("label");
//		syntaxelements.put("dump",attrs);
		
		/* <cfif> */
//		attrs = new HashSet();
//		syntaxelements.put("if",attrs);
		
		/* <cfelse> */
//		attrs = new HashSet();
//		syntaxelements.put("else",attrs);
		
		/* <cfelseif> */
//		attrs = new HashSet();
//		syntaxelements.put("elseif",attrs);
		
		/* <cferror 
		 type = "a type"
		 template = "template_path"
		 mailTo = "email_address"
		 exception = "exception_type"> */
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("template");
//		attrs.add("mailTo");
//		attrs.add("exception");
//		syntaxelements.put("error",attrs);  
		
		/* <cfexecute 
		 name = " ApplicationName "
		 arguments = "CommandLine Arguments"
		 outputFile = "Output file name"
		 timeout = "Timeout interval">
		 ... */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("arguments");
//		attrs.add("outputFile");
//		attrs.add("timeout");
//		syntaxelements.put("execute",attrs); 

		/* <cfexit 
		 method = "method"> */ 
//		attrs = new HashSet();
//		attrs.add("method");
//		syntaxelements.put("exit",attrs);
		
		/* <cffile 
		 action = "upload"
		 fileField = "formfield"
		 destination = "full_path_name"
		 nameConflict = "behavior"
		 accept = "mime_type/file_type"
		 mode = "permission"
		 attributes = "file_attribute_or_list"> 
		 source = "full_path_name"
		 charset = "charset_option"> 
		 file = "full_path_name"> 
		 variable = "var_name"> 
		 output = "content"
		 addNewLine = "Yes" or "No"> */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("fileField");
//		attrs.add("destination");
//		attrs.add("nameConflict");
//		attrs.add("accept");
//		attrs.add("mode");
//		attrs.add("attributes");
//		attrs.add("source");
//		attrs.add("charset");
//		attrs.add("file");
//		attrs.add("variable");
//		attrs.add("output");
//		attrs.add("addNewLine");
//		syntaxelements.put("file",attrs); 
		
		/* <cfflush
		 interval = "integer number of bytes"> */ 
//		attrs = new HashSet();
//		attrs.add("interval");
//		syntaxelements.put("flush",attrs);
		
		/* <cfform 
		 name = "name"
		 action = "form_action"
		 preserveData = "Yes" or "No"
		 onSubmit = "javascript" 
		 target = "window_name"
		 encType = "type"
		 passThrough = "HTML_attribute(s)"
		 codeBase = "URL"
		 archive = "URL" 
		 scriptSrc = "path">
		 ... */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("action");
//		attrs.add("preserveData");
//		attrs.add("onSubmit");
//		attrs.add("target");
//		attrs.add("encType");
//		attrs.add("passThrough");
//		attrs.add("codeBase");
//		attrs.add("archive");
//		attrs.add("scriptSrc");
//		syntaxelements.put("form",attrs); 

		/* <cfftp 
		 action = "action"
		 username = "name"
		 password = "password"
		 server = "server"
		 timeout = "timeout in seconds"
		 port = "port"
		 connection = "name"
		 proxyServer = "proxyserver"
		 retryCount = "number"
		 stopOnError = "Yes" or "No"
		 passive = "Yes" or "No"> 
		 name = "query_name"
		 ASCIIExtensionList = "extensions"
		 transferMode = "mode"
		 failIfExists = "Yes" or "No"
		 directory = "directory name"
		 localFile = "filename"
		 remoteFile = "filename"
		 item = "directory or file"
		 existing = "file or directory name"
		 new = "file or directory name"
		 passive = "Yes" or "No"> */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("server");
//		attrs.add("timeout");
//		attrs.add("port");
//		attrs.add("connection");
//		attrs.add("proxyServer");
//		attrs.add("retryCount");
//		attrs.add("stopOnError");
//		attrs.add("passive");
//		attrs.add("name");
//		attrs.add("ASCIIExtensionList");
//		attrs.add("transferMode");
//		attrs.add("failIfExists");
//		attrs.add("directory");
//		attrs.add("localFile");
//		attrs.add("remoteFile");
//		attrs.add("item");
//		attrs.add("existing");
//		attrs.add("new");
//		attrs.add("passive");
//		syntaxelements.put("ftp",attrs);
		
		/*<cffunction
		 name = "methodName"
		 returnType = "dataType"
		 roles = "securityRoles"
		 access = "methodAccess"
		 output = "yes" or "no" > */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("returnType");
//		attrs.add("roles");
//		attrs.add("access");
//		attrs.add("output");
//		syntaxelements.put("function",attrs);
		
		/* <cfgrid 
		 name = "name"
		 height = "integer"
		 width = "integer"
		 autoWidth = "Yes" or "No"
		 vSpace = "integer"
		 hSpace = "integer"
		 align = "value"
		 query = "query_name"
		 insert = "Yes" or "No"
		 delete = "Yes" or "No"
		 sort = "Yes" or "No"
		 font = "column_font"
		 fontSize = "size"
		 italic = "Yes" or "No"
		 bold = "Yes" or "No"
		 textColor = "web color"
		 href = "URL"
		 hrefKey = "column_name"
		 target = "URL_target"
		 appendKey = "Yes" or "No"
		 highlightHref = "Yes" or "No"
		 onValidate = "javascript_function"
		 onError = "text"
		 gridDataAlign = "position"
		 gridLines = "Yes" or "No"
		 rowHeight = "pixels"
		 rowHeaders = "Yes" or "No"
		 rowHeaderAlign = "position"
		 rowHeaderFont = "font_name"
		 rowHeaderFontSize = "size"
		 rowHeaderItalic = "Yes" or "No"
		 rowHeaderBold = "Yes" or "No"
		 rowHeaderTextColor = "web color"
		 colHeaders = "Yes" or "No"
		 colHeaderAlign = "position"
		 colHeaderFont = "font_name"
		 colHeaderFontSize = "size"
		 colHeaderItalic = "Yes" or "No"
		 colHeaderBold = "Yes" or "No"
		 colHeaderTextColor = "web color"
		 bgColor = "web color"
		 selectColor = "web color"
		 selectMode = "mode"
		 maxRows = "number"
		 notSupported = "text"
		 pictureBar = "Yes" or "No"
		 insertButton = "text"
		 deleteButton = "text"
		 sortAscendingButton = "text"
		 sortDescendingButton = "text">
		 </cfgrid> */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("height");
//		attrs.add("width");
//		attrs.add("autoWidth");
//		attrs.add("vSpace");
//		attrs.add("hSpace");
//		attrs.add("align");
//		attrs.add("query");
//		attrs.add("insert");
//		attrs.add("delete");
//		attrs.add("sort");
//		attrs.add("font");
//		attrs.add("fontSize");
//		attrs.add("italic");
//		attrs.add("bold");
//		attrs.add("textColor");
//		attrs.add("href");
//		attrs.add("hrefKey");
//		attrs.add("target");
//		attrs.add("appendKey");
//		attrs.add("highlightHref");
//		attrs.add("onValidate");
//		attrs.add("onError");
//		attrs.add("gridDataAlign");
//		attrs.add("gridLines");
//		attrs.add("rowHeight");
//		attrs.add("rowHeaders");
//		attrs.add("rowHeaderAlign");
//		attrs.add("rowHeaderFont");
//		attrs.add("rowHeaderFontSize");
//		attrs.add("rowHeaderItalic");
//		attrs.add("rowHeaderBold");
//		attrs.add("rowHeaderTextColor");
//		attrs.add("colHeaders");
//		attrs.add("colHeadersAlign");
//		attrs.add("colHeadersFont");
//		attrs.add("colHeadersFontSize");
//		attrs.add("colHeadersItalic");
//		attrs.add("colHeadersBold");
//		attrs.add("colHeadersTextColor");
//		attrs.add("bgColor");
//		attrs.add("selectColor");
//		attrs.add("selectMode");
//		attrs.add("maxRows");
//		attrs.add("notSupported");
//		attrs.add("pictureBar");
//		attrs.add("insertButton");
//		attrs.add("deleteButton");
//		attrs.add("sortAscendingButton");
//		attrs.add("sortDescendingButton");
//		syntaxelements.put("grid",attrs);
		
		/*<cfgridcolumn 
		 name = "column_name"
		 header = "header"
		 width = "column_width"
		 font = "column_font"
		 fontSize = "size"
		 italic = "Yes" or "No"
		 bold = "Yes" or "No"
		 textColor = "web color" or "expression"
		 bgColor = "web color" or "expression"
		 href = "URL"
		 hrefKey = "column_name"
		 target = "URL_target"
		 select = "Yes" or "No"
		 display = "Yes" or "No"
		 type = "type"
		 headerFont = "font_name"
		 headerFontSize = "size"
		 headerItalic = "Yes" or "No"
		 headerBold = "Yes" or "No"
		 headerTextColor = "web color"
		 dataAlign = "position"
		 headerAlign = "position"
		 numberFormat = "format"
		 values = "Comma separated strings and/or numeric range"
		 valuesDisplay = "Comma separated strings and/or numeric range"
		 valuesDelimiter = "delimiter character"> 
		 */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("header");
//		attrs.add("width");
//		attrs.add("font");
//		attrs.add("fontSize");
//		attrs.add("italic");
//		attrs.add("bold");
//		attrs.add("textColor");
//		attrs.add("bgColor");
//		attrs.add("href");
//		attrs.add("hrefKey");
//		attrs.add("target");
//		attrs.add("select");
//		attrs.add("display");
//		attrs.add("type");
//		attrs.add("headerFont");
//		attrs.add("headerFontSize");
//		attrs.add("headerItalic");
//		attrs.add("headerBold");
//		attrs.add("headerTextColor");
//		attrs.add("dataAlign");
//		attrs.add("headerAlign");
//		attrs.add("numberFormat");
//		attrs.add("values");
//		attrs.add("valuesDisplay");
//		attrs.add("valuesDelimiter");
//		syntaxelements.put("gridcolumn",attrs);
		
		/* <cfgridrow 
		 data = "col1, col2, ..."> */ 
//		attrs = new HashSet();
//		attrs.add("data");
//		syntaxelements.put("gridrow",attrs);
		
		/* <cfgridupdate 
		 grid = "gridname"
		 dataSource = "data source name"
		 tableName = "table name"
		 username = "data source username"
		 password = "data source password"
		 tableOwner = "table owner"
		 tableQualifier = "qualifier"
		 keyOnly = "Yes" or "No"> */ 
//		attrs = new HashSet();
//		attrs.add("grid");
//		attrs.add("dataSource");
//		attrs.add("tableName");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("tableOwner");
//		attrs.add("tableQualifier");
//		attrs.add("keyOnly");
//		syntaxelements.put("gridupdate",attrs);
		
		/* <cfheader 
		 name = "header_name"
		 value = "header_value">
		 or
		 <cfheader 
		 statusCode = "status_code"
		 statusText = "status_text"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("value");
//		attrs.add("statusCode");
//		attrs.add("statusText");
//		syntaxelements.put("header",attrs);

		/* <cfhtmlhead 
		 text = "text"> */
//		attrs = new HashSet();
//		attrs.add("text");
//		syntaxelements.put("htmlhead",attrs);
		
		/* <cfhttp 
		 url = "hostname"
		 port = "port_number"
		 method = "get_or_post"
		 username = "username"
		 password = "password"
		 name = "queryname"
		 columns = "query_columns"
		 firstrowasheaders = "yes" or "no" 
		 path = "path"
		 file = "filename"
		 delimiter = "character"
		 textQualifier = "character"
		 resolveURL = "yes" or "no"
		 proxyServer = "hostname"
		 proxyPort = "port_number"
		 userAgent = "user_agent"
		 throwOnError = "yes" or "no"
		 redirect = "yes" or "no"
		 timeout = "timeout_period"
		 charset = "character set">
		 </cfhttp> */ 
//		attrs = new HashSet();
//		attrs.add("url");
//		attrs.add("port");
//		attrs.add("method");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("name");
//		attrs.add("columns");
//		attrs.add("firstrowasheaders");
//		attrs.add("path");
//		attrs.add("file");
//		attrs.add("delimiter");
//		attrs.add("textQualifier");
//		attrs.add("resolveURL");
//		attrs.add("proxyServer");
//		attrs.add("proxyPort");
//		attrs.add("userAgent");
//		attrs.add("throwOnError");
//		attrs.add("redirect");
//		attrs.add("timeout");
//		attrs.add("charset");
//		syntaxelements.put("http",attrs);
		
		/* <cfhttpparam 
		 name = "name"
		 type = "type"
		 value = "transaction type"
		 file = "filename"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("type");
//		attrs.add("method");
//		attrs.add("value");
//		attrs.add("file");
//		syntaxelements.put("httpparam",attrs);
		
		/* <cfimport 
		 taglib = "taglib-location"
		 prefix = "custom"
		 webservice = "URL"> */ 
//		attrs = new HashSet();
//		attrs.add("taglib");
//		attrs.add("prefix");
//		attrs.add("webservice");
//		syntaxelements.put("import",attrs);
		
		/* <cfinclude 
		 template = "template_name"> */ 
//		attrs = new HashSet();
//		attrs.add("template");
//		syntaxelements.put("include",attrs);
		
		/*<cfindex 
		 collection = "collection_name"
		 action = "action"
		 type = "type"
		 title = "title"
		 key = "ID"
		 body = "body"
		 custom1 = "custom_value"
		 custom2 = "custom_value"
		 URLpath = "URL"
		 extensions = "file_extensions"
		 query = "query_name"
		 recurse = "Yes" or "No"
		 language = "language"> */ 
//		attrs = new HashSet();
//		attrs.add("collection");
//		attrs.add("action");
//		attrs.add("type");
//		attrs.add("title");
//		attrs.add("key");
//		attrs.add("body");
//		attrs.add("custom1");
//		attrs.add("custom2");
//		attrs.add("URLpath");
//		attrs.add("extensions");
//		attrs.add("query");
//		attrs.add("recurse");
//		attrs.add("language");
//		syntaxelements.put("index",attrs);
		
		/* <cfinput 
		 type = "input_type"
		 name = "name"
		 value = "initial_value"
		 required = "Yes" or "No"
		 range = "min_value, max_value"
		 validate = "data_type"
		 onValidate = "javascript_function"
		 pattern = "regexp"
		 message = "validation_msg"
		 onError = "text"
		 size = "integer"
		 maxLength = "integer"
		 checked 
		 passThrough = "HTML_attributes"> */ 
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("name");
//		attrs.add("value");
//		attrs.add("required");
//		attrs.add("range");
//		attrs.add("validate");
//		attrs.add("onValidate");
//		attrs.add("pattern");
//		attrs.add("message");
//		attrs.add("onError");
//		attrs.add("size");
//		attrs.add("maxLength");
//		attrs.add("checked");
//		attrs.add("passThrough");
//		syntaxelements.put("input",attrs);
		
		/* <cfinsert 
		 dataSource = "ds_name"
		 tableName = "tbl_name"
		 tableOwner = "owner"
		 tableQualifier = "tbl_qualifier"
		 username = "username"
		 password = "password"
		 formFields = "formfield1, formfield2, ..."> */
//		attrs = new HashSet();
//		attrs.add("dataSource");
//		attrs.add("tableName");
//		attrs.add("tableOwner");
//		attrs.add("tableQualifier");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("formFields");
//		syntaxelements.put("insert",attrs);
		
		/*<cfinvoke
		 component = "component name or reference"
		 method = "method name"
		 returnVariable = "variable name"
		 argumentCollection = "argument collection"
		 webservice = "URLtoWSDL_location"
		 username = user name"
		 password = "password"
		 inputParam1 = "value1"
		 inputParam2 = "value2"
		 ...>
		 */
//		attrs = new HashSet();
//		attrs.add("component");
//		attrs.add("method");
//		attrs.add("returnVariable");
//		attrs.add("argumentCollection");
//		attrs.add("webservice");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("inputParam1");
//		attrs.add("inputParam2");
//		syntaxelements.put("invoke",attrs);

		/* <cfinvokeargument
		 name="argument name"
		 value="argument value"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("value");
//		syntaxelements.put("invokeargument",attrs);
		
		/* <cfldap 
		 server = "server_name"
		 port = "port_number"
		 username = "name"
		 password = "password"
		 action = "action"
		 name = "name"
		 timeout = "seconds"
		 maxRows = "number"
		 start = "distinguished_name"
		 scope = "scope"
		 attributes = "attribute, attribute"
		 filter = "filter"
		 sort = "attribute[, attribute]..."
		 sortControl = "nocase" and/or "desc" or "asc"
		 dn = "distinguished_name"
		 startRow = "row_number"
		 modifyType = "replace" or "add" or "delete"
		 rebind = "Yes" or "No"
		 referral = "number_of_allowed_hops"
		 secure = "multi_field_security_string"
		 separator = "separator_character"
		 delimiter = "delimiter_character"> */ 
//		attrs = new HashSet();
//		attrs.add("server");
//		attrs.add("port");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("action");
//		attrs.add("name");
//		attrs.add("timeout");
//		attrs.add("maxRows");
//		attrs.add("start");
//		attrs.add("scope");
//		attrs.add("attributes");
//		attrs.add("filter");
//		attrs.add("sort");
//		attrs.add("sortControl");
//		attrs.add("dn");
//		attrs.add("startRow");
//		attrs.add("modifyType");
//		attrs.add("rebind");
//		attrs.add("referral");
//		attrs.add("secure");
//		attrs.add("separator");
//		attrs.add("delimiter");
//		syntaxelements.put("ldap",attrs);
		
		/*<cflocation
		 url = "url"
		 addToken = "Yes" or "No">*/ 
//		attrs = new HashSet();
//		attrs.add("url");
//		attrs.add("addToken");
//		syntaxelements.put("location",attrs);
		
		/* <cflock 
		 timeout = "timeout in seconds "
		 scope = "Application" or "Server" or "Session"
		 name = "lockname" 
		 throwOnTimeout = "Yes" or "No"
		 type = "readOnly" or "exclusive "> 
		 <!--- CFML to be synchronized ---> 
		 </cflock> */ 
//		attrs = new HashSet();
//		attrs.add("timeout");
//		attrs.add("scope");
//		attrs.add("name");
//		attrs.add("throwOnTimeout");
//		attrs.add("type");
//		syntaxelements.put("lock",attrs);

		/* <cflog 
		 text = "text"
		 log = "log type"
		 file = "filename"
		 type = "message type"
		 application = "application name yes or no"> */
//		attrs = new HashSet();
//		attrs.add("text");
//		attrs.add("log");
//		attrs.add("file");
//		attrs.add("type");
//		attrs.add("application");
//		syntaxelements.put("log",attrs);
		
		/* <cflogin
		 idletimeout = "value"
		 applicationToken = "token"
		 cookieDomain = "domain"
		 ...
		 <cfloginuser
		 name = "name"
		 password = "password-string"
		 roles = "roles">
		 ...>
		 </cflogin> */ 
//		attrs = new HashSet();
//		attrs.add("idletimeout");
//		attrs.add("applicationToken");
//		attrs.add("cookieDomain");
//		syntaxelements.put("login",attrs);

//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("password");
//		attrs.add("roles");
//		syntaxelements.put("loginuser",attrs);
		
		/* <cflogout>*/ 
//		attrs = new HashSet();
//		syntaxelements.put("logout",attrs);
		
		/* <cfloop 
		 index = "parameter_name"
		 from = "beginning_value"
		 to = "ending_value"
		 step = "increment">
		 condition = "expression">
		 query = "query_name"
		 startRow = "row_num"
		 endRow = "row_num">
		 list = "list_items"
		 delimiters = "item_delimiter">
		 ...
		 </cfloop> */ 
//		attrs = new HashSet();
//		attrs.add("index");
//		attrs.add("from");
//		attrs.add("to");
//		attrs.add("step");
//		attrs.add("condition");
//		attrs.add("query");
//		attrs.add("startRow");
//		attrs.add("endRow");
//		attrs.add("list");
//		attrs.add("delimiters");
//		syntaxelements.put("loop",attrs);
		
		/* <cfmail 
		 to = "recipient"
		 from = "sender"
		 cc = "copy_to"
		 bcc = "blind_copy_to"
		 subject = "msg_subject"
		 type = "msg_type"
		 maxrows = "max_msgs"
		 mimeattach = "path"
		 query = "query_name"
		 group = "query_column"
		 groupcasesensitive = "yes" or "no"
		 startrow = "query_row"
		 server = "servername"
		 port = "port_id"
		 mailerid = "headerid"
		 timeout = "seconds"
		 spoolenable = "yes" or "no"> */ 
//		attrs = new HashSet();
//		attrs.add("to");
//		attrs.add("from");
//		attrs.add("cc");
//		attrs.add("bcc");
//		attrs.add("subject");
//		attrs.add("type");
//		attrs.add("maxrows");
//		attrs.add("mimeattach");
//		attrs.add("query");
//		attrs.add("group");
//		attrs.add("groupcasesensitive");
//		attrs.add("startrow");
//		attrs.add("server");
//		attrs.add("port");
//		attrs.add("mailerid");
//		attrs.add("timeout");
//		attrs.add("spoolenable");
//		syntaxelements.put("mail",attrs);

		/* <cfmailparam 
		 file = "file-name" >
		 name = "header-name"
		 value = "header-value" > */
//		attrs = new HashSet();
//		attrs.add("file");
//		attrs.add("name");
//		attrs.add("value");
//		syntaxelements.put("mailparam",attrs);
		
		/* <cfmodule 
		 template = "path"
		 name = "tag_name"
		 attributeCollection = "collection_structure"
		 attribute_name1 = "valuea"
		 attribute_name2 = "valueb"
		 ...> */ 
//		attrs = new HashSet();
//		attrs.add("template");
//		attrs.add("name");
//		attrs.add("attributeCollection");
//		attrs.add("attribute_name1");
//		attrs.add("attribute_name2");
//		syntaxelements.put("module",attrs);

		/* <cfobject 
		 type = "com"
		 action = "action"
		 class = "program_ID"
		 name = "text"
		 context = "context"
		 server = "server_name"
		 component = "component name"
		 locale = "type-value arguments"
		 webservice= "http://....?wsdl" or "name set in Administrator">
		 */
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("action");
//		attrs.add("class");
//		attrs.add("name");
//		attrs.add("context");
//		attrs.add("server");
//		attrs.add("component");
//		attrs.add("locale");
//		attrs.add("webservice");
//		syntaxelements.put("object",attrs);

		/* <cfobjectcache 
		 action = "clear"> */ 
//		attrs = new HashSet();
//		attrs.add("action");
//		syntaxelements.put("objectcache",attrs);

		/* <cfoutput 
		 query = "query_name"
		 group = "query_column"
		 groupCaseSensitive = "Yes" or "No"
		 startRow = "start_row"
		 maxRows = "max_rows_output">
		 </cfoutput> */
//		attrs = new HashSet();
//		attrs.add("query");
//		attrs.add("group");
//		attrs.add("groupCaseSensitive");
//		attrs.add("startRow");
//		attrs.add("maxRows");
//		syntaxelements.put("output",attrs); 

		/* <cfparam 
		 name = "param_name"
		 type = "data_type"
		 default = "value"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("type");
//		attrs.add("default");
//		syntaxelements.put("param",attrs);
		
		/* <cfpop 
		 server = "servername"
		 port = "port_number"
		 username = "username"
		 password = "password"
		 action = "action"
		 name = "queryname"
		 messageNumber = "number"
		 uid = "number" 
		 attachmentPath = "path"
		 timeout = "seconds"
		 maxRows = "number"
		 startRow = "number"
		 generateUniqueFilenames = "boolean"> */ 
//		attrs = new HashSet();
//		attrs.add("server");
//		attrs.add("port");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("action");
//		attrs.add("name");
//		attrs.add("messageNumber");
//		attrs.add("uid");
//		attrs.add("attachmentPath");
//		attrs.add("timeout");
//		attrs.add("maxRows");
//		attrs.add("startRow");
//		attrs.add("generateUniqueFilenames");
//		syntaxelements.put("pop",attrs);
		
		/* <cfprocessingdirective
		 pageencoding = "page-encoding literal string"
		 suppressWhiteSpace = "Yes" or "No"
		 pageEncoding = "page-encoding literal string">
		 </cfprocessingdirective> */ 
//		attrs = new HashSet();
//		attrs.add("pageencoding");
//		attrs.add("suppressWhiteSpace");
//		attrs.add("pageEncoding");
//		syntaxelements.put("processingdirective",attrs);
		
		/* <cfprocparam 
		 type = "in" or "out" or "inout"
		 variable = "variable name"
		 dbVarName = "DB variable name"
		 value = "parameter value"
		 CFSQLType = "parameter datatype"
		 maxLength = "length"
		 scale = "decimal places" 
		 null = "Yes" or "No"> */ 
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("variable");
//		attrs.add("dbVarName");
//		attrs.add("value");
//		attrs.add("CFSQLType");
//		attrs.add("maxLength");
//		attrs.add("scale");
//		attrs.add("null");
//		syntaxelements.put("procparam",attrs);

		/* <cfprocresult 
		 name = "query_name"
		 resultSet = "1-n" 
		 maxRows = "maxrows"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("resultSet");
//		attrs.add("maxRows");
//		syntaxelements.put("procresult",attrs);

		/* <cfproperty 
		 name="name" 
		 type="type" 
		 ...> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("type");
//		syntaxelements.put("property",attrs);
		
		/* <cfquery 
		 name = "query_name"
		 dataSource = "ds_name"
		 dbtype = "query"
		 username = "username"
		 password = "password"
		 maxRows = "number"
		 blockFactor = "blocksize"
		 timeout = "seconds"
		 cachedAfter = "date" 
		 cachedWithin = "timespan" 
		 debug = "Yes" or "No">
		 </cfquery> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("dataSource");
//		attrs.add("dbtype");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("maxRows");
//		attrs.add("blockFactor");
//		attrs.add("timeout");
//		attrs.add("cachedAfter");
//		attrs.add("cachedWithin");
//		attrs.add("debug");
//		syntaxelements.put("query",attrs);
		
		/* <cfqueryparam 
		 value = "parameter value"
		 CFSQLType = "parameter type"
		 maxLength = "maximum parameter length"
		 scale = "number of decimal places"
		 null = "Yes" or "No"
		 list = "Yes" or "No"
		 separator = "separator character"> */
//		attrs = new HashSet();
//		attrs.add("value");
//		attrs.add("CFSQLType");
//		attrs.add("maxLength");
//		attrs.add("scale");
//		attrs.add("null");
//		attrs.add("list");
//		attrs.add("separator");
//		syntaxelements.put("queryparam",attrs);
		
		/* <cfregistry 
		 action = "getAll"
		 branch = "branch"
		 type = "data type"
		 name = "query name" 
		 sort = "criteria"> 
		 entry = "key or value"
		 variable = "variable"
		 value = "data"> */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("branch");
//		attrs.add("type");
//		attrs.add("name");
//		attrs.add("sort");
//		attrs.add("entry");
//		attrs.add("variable");
//		attrs.add("value");
//		syntaxelements.put("registry",attrs);
		
		/* <cfreport 
		 report = "report_path"
		 dataSource = "ds_name" 
		 type = "type" 
		 timeout = "number of seconds" 
		 orderBy = "result_order"
		 username = "username"
		 password = "password"
		 formula = "formula">
		 </cfreport> */ 
//		attrs = new HashSet();
//		attrs.add("report");
//		attrs.add("dataSource");
//		attrs.add("type");
//		attrs.add("timeout");
//		attrs.add("orderBy");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("formula");
//		syntaxelements.put("report",attrs);
		
		/* <cfrethrow> */ 
//		attrs = new HashSet();
//		syntaxelements.put("rethrow",attrs);

		/* <cfreturn> */ 
//		attrs = new HashSet();
//		syntaxelements.put("return",attrs);

		/*<cfsavecontent 
		 variable = "variable name">
		 the content
		 </cfsavecontent> */ 
//		attrs = new HashSet();
//		attrs.add("variable");
//		syntaxelements.put("savecontent",attrs);
		
		/* <cfschedule 
		 action = "update"
		 task = "taskname"
		 operation = "HTTPRequest"
		 file = "filename"
		 path = "path_to_file"
		 startDate = "date"
		 startTime = "time"
		 url = "URL"
		 publish = "Yes" or "No"
		 endDate = "date"
		 endTime = "time"
		 interval = "seconds"
		 requestTimeOut = "seconds"
		 username = "username"
		 password = "password"
		 resolveURL = "Yes" or "No"
		 proxyServer = "hostname"
		 port = "port_number"
		 proxyPort = "port_number"> */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("task");
//		attrs.add("operation");
//		attrs.add("file");
//		attrs.add("path");
//		attrs.add("startDate");
//		attrs.add("startTime");
//		attrs.add("url");
//		attrs.add("publish");
//		attrs.add("endDate");
//		attrs.add("endTime");
//		attrs.add("interval");
//		attrs.add("requestTimeOut");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("resolveURL");
//		attrs.add("proxyServer");
//		attrs.add("port");
//		attrs.add("proxyPort");
//		syntaxelements.put("schedule",attrs);
		
		/* <cfscript> */ 
//		attrs = new HashSet();
//		syntaxelements.put("script",attrs);
		
		/* <cfsearch 
		 name = "search_name"
		 collection = "collection_name"
		 type = "criteria"
		 criteria = "search_expression"
		 maxRows = "number"
		 startRow = "row_number"
		 language = "language"> */ 
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("collection");
//		attrs.add("type");
//		attrs.add("criteria");
//		attrs.add("maxRows");
//		attrs.add("startRow");
//		attrs.add("language");
//		syntaxelements.put("search",attrs);
		
		/* <cfselect 
		 name = "name"
		 required = "Yes" or "No"
		 message = "text"
		 onError = "text"
		 size = "integer"
		 multiple = "Yes" or "No"
		 query = "queryname"
		 selected = "column_value"
		 value = "text"
		 display = "text"
		 passThrough = "HTML_attributes">
		 </cfselect> */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("required");
//		attrs.add("message");
//		attrs.add("onError");
//		attrs.add("size");
//		attrs.add("multiple");
//		attrs.add("query");
//		attrs.add("selected");
//		attrs.add("value");
//		attrs.add("display");
//		attrs.add("passThrough");
//		syntaxelements.put("select",attrs);
		
		/*<cfset 
		 variable_name = expression> */ 
//		attrs = new HashSet();
//		syntaxelements.put("set",attrs);
		
		/*<cfsilent>*/ 
//		attrs = new HashSet();
//		syntaxelements.put("silent",attrs);
		
		/* <cfsetting 
		 enableCFoutputOnly = "Yes" or "No" 
		 showDebugOutput = "Yes" or "No" 
		 requestTimeOut = "value in seconds" > */
//		attrs = new HashSet();
//		attrs.add("enableCFoutputOnly");
//		attrs.add("showDebugOutput");
//		attrs.add("requestTimeOut");
//		syntaxelements.put("setting",attrs);
		
		/* <cfslider 
		 name = "name"
		 label = "text"
		 refreshLabel = "Yes" or "No"
		 range = "min_value, max_value"
		 scale = "uinteger"
		 value = "integer"
		 onValidate = "script_name"
		 message = "text"
		 onError = "text"
		 height = "integer"
		 width = "integer"
		 vSpace = "integer"
		 hSpace = "integer"
		 align = "alignment"
		 tickMarkMajor = "Yes" or "No"
		 tickMarkMinor = "Yes" or "No"
		 tickMarkImages = "URL1, URL2, URLn"
		 tickMarkLabels = "Yes" or "No" or "list"
		 lookAndFeel = "motif" or "windows" or "metal"
		 vertical = "Yes" or "No"
		 bgColor = "color"
		 textColor = "color"
		 font = "font_name"
		 fontSize = "integer"
		 italic = "Yes" or "No"
		 bold = "Yes" or "No"
		 notSupported = "text"> */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("label");
//		attrs.add("refreshLabel");
//		attrs.add("range");
//		attrs.add("scale");
//		attrs.add("value");
//		attrs.add("onValidate");
//		attrs.add("message");
//		attrs.add("onError");
//		attrs.add("height");
//		attrs.add("width");
//		attrs.add("vSpace");
//		attrs.add("hSpace");
//		attrs.add("align");
//		attrs.add("tickMarkMajor");
//		attrs.add("tickMarkMinor");
//		attrs.add("tickMarkImages");
//		attrs.add("tickMarkLabels");
//		attrs.add("lookAndFeel");
//		attrs.add("vertical");
//		attrs.add("bgColor");
//		attrs.add("textColor");
//		attrs.add("font");
//		attrs.add("fontSize");
//		attrs.add("italic");
//		attrs.add("bold");
//		attrs.add("notSupported");
//		syntaxelements.put("slider",attrs);

		/* <cfstoredproc 
		 procedure = "procedure name"
		 dataSource = "ds_name"
		 username = "username"
		 password = "password"
		 blockFactor = "blocksize"
		 debug = "Yes" or "No"
		 returnCode = "Yes" or "No"> */
//		attrs = new HashSet();
//		attrs.add("procedure");
//		attrs.add("dataSource");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("blockFactor");
//		attrs.add("debug");
//		attrs.add("returnCode");
//		syntaxelements.put("storedproc",attrs);

		/* <cfswitch 
		 expression = "expression"> */
//		attrs = new HashSet();
//		attrs.add("expression");
//		syntaxelements.put("switch",attrs);
		
		/* <cftable 
		 query = "query_name"
		 maxRows = "maxrows_table"
		 colSpacing = "number_of_spaces"
		 headerLines = "number_of_lines"
		 HTMLTable
		 border
		 colHeaders
		 startRow = "row_number">
		 ...
		 </cftable> */ 
//		attrs = new HashSet();
//		attrs.add("query");
//		attrs.add("maxRows");
//		attrs.add("colSpacing");
//		attrs.add("headerLines");
//		attrs.add("HTMLTable");
//		attrs.add("border");
//		attrs.add("colHeaders");
//		attrs.add("startRow");
//		syntaxelements.put("table",attrs);
		
		/* <cftextinput 
		 name = "name"
		 value = "text"
		 required = "Yes" or "No"
		 range = "min_value, max_value"
		 validate = "data_type"
		 onValidate = "script_name"
		 message = "text"
		 onError = "text"
		 size = "integer"
		 font = "font_name"
		 fontSize = "integer"
		 italic = "Yes" or "No"
		 bold = "Yes" or "No"
		 height = "integer"
		 width = "integer"
		 vSpace = "integer"
		 hSpace = "integer"
		 align = "alignment"
		 bgColor = "color"
		 textColor = "color"
		 maxLength = "integer"
		 notSupported = "text"> */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("value");
//		attrs.add("required");
//		attrs.add("range");
//		attrs.add("validate");
//		attrs.add("onValidate");
//		attrs.add("message");
//		attrs.add("onError");
//		attrs.add("size");
//		attrs.add("font");
//		attrs.add("fontSize");
//		attrs.add("italic");
//		attrs.add("bold");
//		attrs.add("height");
//		attrs.add("width");
//		attrs.add("vSpace");
//		attrs.add("hSpace");
//		attrs.add("align");
//		attrs.add("bgColor");
//		attrs.add("textColor");
//		attrs.add("maxLength");
//		attrs.add("notSupported");
//		syntaxelements.put("textinput",attrs);

		/* <cfthrow 
		 type = "exception_type "
		 message = "message"
		 detail = "detail_description "
		 errorCode = "error_code "
		 extendedInfo = "additional_information" 
		 object = "java_except_object"> */
//		attrs = new HashSet();
//		attrs.add("type");
//		attrs.add("message");
//		attrs.add("detail");
//		attrs.add("errorCode");
//		attrs.add("extendedInfo");
//		attrs.add("object");
//		syntaxelements.put("throw",attrs);
		
		/* <cftrace 
		 abort = "Yes or No"
		 category = "string"
		 inline = "Yes or No"
		 text = "string"
		 type = "format"
		 var = "variable_name"
		 </cftrace> */
//		attrs = new HashSet();
//		attrs.add("abort");
//		attrs.add("category");
//		attrs.add("inline");
//		attrs.add("text");
//		attrs.add("type");
//		attrs.add("var");
//		syntaxelements.put("trace",attrs);

		/* <cftransaction 
		 action = "begin" or "commit" or "rollback"
		 isolation = "read_uncommitted" or "read_committed" or 
		 "repeatable_read" >
		 </cftransaction> */ 
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("isolation");
//		syntaxelements.put("transaction",attrs);
		
		/* <cftree 
		 name = "name"
		 required = "Yes" or "No"
		 delimiter = "delimiter"
		 completePath = "Yes" or "No"
		 appendKey = "Yes" or "No"
		 highlightHref = "Yes" or "No"
		 onValidate = "script_name"
		 message = "text"
		 onError = "text"
		 lookAndFeel = "motif" or "windows" or "metal"
		 font = "font"
		 fontSize = "size"
		 italic = "Yes" or "No"
		 bold = "Yes" or "No"
		 height = "integer"
		 width = "integer"
		 vSpace = "integer"
		 hSpace = "integer"
		 align = "alignment"
		 border = "Yes" or "No"
		 hScroll = "Yes" or "No"
		 vScroll = "Yes" or "No"
		 notSupported = "text">
		 </cftree> */
//		attrs = new HashSet();
//		attrs.add("name");
//		attrs.add("required");
//		attrs.add("delimiter");
//		attrs.add("completePath");
//		attrs.add("appendKey");
//		attrs.add("highlightHref");
//		attrs.add("onValidate");
//		attrs.add("message");
//		attrs.add("onError");
//		attrs.add("lookAndFeel");
//		attrs.add("font");
//		attrs.add("fontSize");
//		attrs.add("italic");
//		attrs.add("bold");
//		attrs.add("height");
//		attrs.add("width");
//		attrs.add("vSpace");
//		attrs.add("hSpace");
//		attrs.add("align");
//		attrs.add("border");
//		attrs.add("hScroll");
//		attrs.add("vScroll");
//		attrs.add("notSupported");
//		syntaxelements.put("tree",attrs);
		
		/* <cftreeitem 
		 value = "text"
		 display = "text"
		 parent = "parent_name"
		 img = "filename"
		 imgopen = "filename"
		 href = "URL"
		 target = "URL_target"
		 query = "queryname"
		 queryAsRoot = "Yes" or "No"
		 expand = "Yes" or "No"> */
//		attrs = new HashSet();
//		attrs.add("value");
//		attrs.add("display");
//		attrs.add("parent");
//		attrs.add("img");
//		attrs.add("imgopen");
//		attrs.add("href");
//		attrs.add("target");
//		attrs.add("query");
//		attrs.add("queryAsRoot");
//		attrs.add("expand");
//		syntaxelements.put("treeitem",attrs);

		/* <cftry> */ 
//		attrs = new HashSet();
//		syntaxelements.put("try",attrs);
		
		/* <cfupdate 
		 dataSource = "ds_name"
		 tableName = "table_name"
		 tableOwner = "name"
		 tableQualifier = "qualifier"
		 username = "username"
		 password = "password"
		 formFields = "field_names"> */
//		attrs = new HashSet();
//		attrs.add("dataSource");
//		attrs.add("tableName");
//		attrs.add("tableOwner");
//		attrs.add("tableQualifier");
//		attrs.add("username");
//		attrs.add("password");
//		attrs.add("formFields");
//		syntaxelements.put("update",attrs);

		/* <cfwddx 
		 action = "action" 
		 input = "inputdata" 
		 output = "resultvariablename" 
		 topLevelVariable = "toplevelvariablenameforjavascript"
		 useTimeZoneInfo = "Yes" or "No"
		 validate = "Yes" or "No" > */
//		attrs = new HashSet();
//		attrs.add("action");
//		attrs.add("input");
//		attrs.add("output");
//		attrs.add("topLevelVariable");
//		attrs.add("useTimeZoneInfo");
//		attrs.add("validate");
//		syntaxelements.put("wddx",attrs);
		
		/* <CFXML 
		 variable="xmlVarName" 
		 caseSensitive="yes" or "no"> */ 
//		attrs = new HashSet();
//		attrs.add("variable");
//		attrs.add("caseSensitive");
//		syntaxelements.put("xml",attrs);
	}
}

