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
	
		var iNode = document.createElement("img");		
		iNode.setAttribute("src", "${staticUrl}${banner.imageUrl}");
		iNode.setAttribute("id", "c_ad_i" + c_ad_date);
		iNode.setAttribute("style", "position:relative;")
		iNode.setAttribute("width", "${banner.format.width}px")
		iNode.setAttribute("height", "${banner.format.height}px")
	
		aNode.appendChild(iNode);
		
		document.getElementById(c_ad_node).appendChild(aNode);
		document.getElementById(c_ad_node).style.opacity = 1;
	}	
	
	madApi.onload(madApi.delegate(insertImageBanner,this, [c_ad_date, c_ad_node]));
})();
