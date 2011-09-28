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

Extern-Banner

-->
var c_ad_date = new Date().getTime();
var c_ad_node = "c_ad_" + c_ad_date;
var style = "width:${banner.width}px; height:${banner.height}px; position:relative;";
document.write("<div id='" + c_ad_node + "' style='" + style + "'>");
document.write("${banner.externContent}");
document.write("</div>");