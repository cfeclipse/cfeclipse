var browser = navigator.appName;
var version = navigator.appVersion;
   
if (version.indexOf("Safari") != -1) {
  document.write('<link rel=stylesheet href=\"help_safari.css\" TYPE=\"text/css\">');
}
else if ((browser == "Netscape") && (version.indexOf("Mac") != -1)) {
  document.write('<link rel=stylesheet href=\"help_ns_mac.css\" TYPE=\"text/css\">');
}
else if (browser == "Netscape") {
  document.write('<link rel=stylesheet href=\"help_ns_pc.css\" TYPE=\"text/css\">');
}
else if ((browser == "Microsoft Internet Explorer") && (version.indexOf("Mac") != -1)) {
  document.write('<link rel=stylesheet href=\"help_ie_mac.css\" TYPE=\"text/css\">');          
}
else if (browser == "Microsoft Internet Explorer") {
  document.write('<link rel=stylesheet href=\"help_ie_pc.css\" TYPE=\"text/css\">');
} 
else {
  document.write('<link rel=stylesheet href=\"help.css\" TYPE=\"text/css\">');                         
}