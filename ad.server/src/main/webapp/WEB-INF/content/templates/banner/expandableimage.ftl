<#--  

Expandierendes Image-Banner

-->

(function () {
	var c_ad_date = new Date().getTime();
	var c_ad_node = "c_ad_" + c_ad_date;
	var style = "opacity:0;width:${banner.format.width}px; height:${banner.format.height}px;";
	document.write("<div id='" + c_ad_node + "' style='" + style + "'></div>");
	
	
	function insertBanner (c_ad_date, c_ad_node) {
		
		var aNode = document.createElement("a");
		aNode.setAttribute("target", "${banner.linkTarget}");
		aNode.setAttribute("href", "${clickUrl}");
		<#if banner.linkTitle??>
			aNode.setAttribute("title", "${banner.linkTitle}");	
		</#if>
		
		var iNode = document.createElement("img");		
		iNode.setAttribute("src", "${staticUrl}${banner.imageUrl}");
		iNode.setAttribute("id", "c_ad_i_" + c_ad_date);
		iNode.setAttribute("style", "");
		iNode.setAttribute("width", "${banner.format.width}px");
		iNode.setAttribute("height", "${banner.format.height}px");
		<#if banner.linkTitle??>
			iNode.setAttribute("title", "${banner.linkTitle}");	
		</#if>
		//iNode.setAttribute("onmouseover", "doOnOver(\"" + "c_ad_i_" + c_ad_date + "\", \"" + "c_ad_ex_" + c_ad_date + "\")");
		iNode.onmouseover = madApi.delegate(mad_expandableImage_open, this, ["c_ad_i_" + c_ad_date, "c_ad_ex_" + c_ad_date]);
	
		var aNode2 = aNode.cloneNode(true);
		var iNode2 = iNode.cloneNode(true);
	
		aNode.appendChild(iNode);
		
		<#-- Das kleiner Bild in das Dom einf¸gen -->
		document.getElementById(c_ad_node).appendChild(aNode);
		
		<#-- Das groﬂe Bild erzeugen und in das Dom h‰ngen -->
		iNode2.setAttribute("src", "${staticUrl}${banner.expandedImageUrl}");
		iNode2.setAttribute("id", "c_ad_i2_" + c_ad_date);
		iNode2.setAttribute("style", "");
		iNode2.setAttribute("width", "${banner.expandedImageWidth}px");
		iNode2.setAttribute("height", "${banner.expandedImageHeight}px");
		iNode2.setAttribute("onmouseover", "");
		aNode2.appendChild(iNode2);
		
		<#-- Den Dialog erzeugen der Expandiert werden soll -->
		var container = document.createElement("div");
		container.setAttribute("id", "c_ad_ex_" + c_ad_date);
		container.setAttribute("style", "opacity: 0; position: absolute;");
		
		// <div style="" onclick="doOnOut();" title="">X</div>
		var close = document.createElement("div");
		close.setAttribute("style", "background-color: ${bcolor}; cursor: pointer; width: (${banner.expandedImageWidth} - 5)px; text-align: right; padding-right: 5px; color: ${tcolor}; ");
		close.setAttribute("title", "Schlieﬂen");
		//close.setAttribute("onclick", "doOnOut(\"" + "c_ad_ex_" + c_ad_date + "\")");
		close.onclick = madApi.delegate(mad_expandableImage_close, this, ["c_ad_ex_" + c_ad_date]);
		close.appendChild(document.createTextNode("X"));
		
		container.appendChild(close);
		container.appendChild(aNode2);
		
		document.getElementById(c_ad_node).appendChild(container);
		
		<#-- Das Banner anzeigen -->
		document.getElementById(c_ad_node).style.opacity = 1;
	}	
	
	madApi.onload(madApi.delegate(insertBanner,this, [c_ad_date, c_ad_node]));
})();
