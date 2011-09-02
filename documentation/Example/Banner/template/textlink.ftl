<#--  

Extern-Banner

-->
var c_ad_date = new Date().getTime();
var c_ad_node = "c_ad_" + c_ad_date;
var style = "width:${banner.width}px; height:${banner.height}px; position:relative;";
document.write("<div id='" + c_ad_node + "' style='" + style + "'>");
document.write("${banner.externContent}");
document.write("</div>");