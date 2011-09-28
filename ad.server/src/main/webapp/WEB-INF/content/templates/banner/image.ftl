<#--

    Mad-Advertisement
    Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>

    This program is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free Software
    Foundation, either version 3 of the License, or (at your option) any later
    version.

    This program is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
    FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
    details.

    You should have received a copy of the GNU General Public License along with
    this program. If not, see <http://www.gnu.org/licenses/>.

-->
<#--  

Image-Banner

-->

(function () {
	var c_ad_date = new Date().getTime();
	var c_ad_node = "c_ad_" + c_ad_date;
	var style = "opacity:0;overflow:hidden; width:${banner.format.width}px; height:${banner.format.height}px; position:relative;";
	document.write("<div id='" + c_ad_node + "' style='" + style + "'></div>");
	
	
	function insertImageBanner (c_ad_date, c_ad_node) {
		
		var aNode = document.createElement("a");
		aNode.setAttribute("target", "${banner.linkTarget}");
		aNode.setAttribute("href", "${clickUrl}");
		<#if banner.linkTitle??>
			aNode.setAttribute("title", "${banner.linkTitle}");	
		</#if>
	
		var iNode = document.createElement("img");		
		iNode.setAttribute("src", "${staticUrl}${banner.imageUrl}");
		iNode.setAttribute("id", "c_ad_i" + c_ad_date);
		iNode.setAttribute("style", "position:relative;")
		iNode.setAttribute("width", "${banner.format.width}px")
		iNode.setAttribute("height", "${banner.format.height}px")
		<#if banner.linkTitle??>
			iNode.setAttribute("title", "${banner.linkTitle}");	
		</#if>
	
		aNode.appendChild(iNode);
		
		document.getElementById(c_ad_node).appendChild(aNode);
		document.getElementById(c_ad_node).style.opacity = 1;
	}	
	
	madApi.onload(madApi.delegate(insertImageBanner,this, [c_ad_date, c_ad_node]));
})();
