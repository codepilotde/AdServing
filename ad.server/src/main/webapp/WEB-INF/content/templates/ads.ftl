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
include der MadApi über die einige nötige Methoden zur Verfügung gestellt werden
-->
		

<#if enviroment == "development">
	<#include "madapi.js"> 
<#elseif enviroment == "production">
  	<#include "madapi_production.js">
<#else>
  
</#if>

(function () {

<#--  
Anhand der Request-ID kann sichergestellt werden, das ein User auf der selben Seite
nicht zweimal das gleiche Banner sieht
-->
if (typeof madRequestID == "undefined") {
	madRequestID = "${adrequest_id}";
}

var mad_ad_date = new Date();

var differenceInMinutes = -mad_ad_date.getTimezoneOffset();
differenceInMinutes;

var flashVersion = madApi.flash(7, 10).available;

var selectString = "?_p1=" + mad_ad_format + "&_p2=" + mad_ad_type + "&_p3=" + differenceInMinutes + "&_p4=" + madRequestID + "&_t=" + mad_ad_date.getTime() + "&_p5=" + flashVersion;
if (typeof mad_ad_slot != "undefined") {
	selectString +=  "&_p6=" + mad_ad_slot;
}
if (typeof mad_ad_keywords != "undefined") {
	selectString +=  "&_p7=" + encodeURIComponent(mad_ad_keywords);
}

if (typeof mad_ad_tcolor != "undefined") {
	selectString +=  "&tcolor=" + encodeURIComponent(mad_ad_tcolor);
}
if (typeof mad_ad_bcolor != "undefined") {
	selectString +=  "&bcolor=" + encodeURIComponent(mad_ad_bcolor);
}

selectString += "&_p8=" + encodeURIComponent(document.referrer);

document.write("<script type='text/javascript' src='${adselect_url}" + selectString + "'></script>");

})();