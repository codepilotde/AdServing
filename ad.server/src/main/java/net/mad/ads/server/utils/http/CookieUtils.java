/**
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package net.mad.ads.server.utils.http;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import net.mad.ads.common.util.Strings;



/**
 * Utilities zum arbeiten mit Cookies
 * 
 * @author thmarx
 */
public class CookieUtils {

	
	public static final int ONE_HOUR = 60 * 60;
	public static final int ONE_DAY = 60 * 60 * 24;
	public static final int ONE_WEEK = 60 * 60 * 24 * 7;
	public static final int ONE_MONTH = 60 * 60 * 24 * 30;
	public static final int ONE_YEAR = 12 * 60 * 60 * 24 * 30;

	/**
	 * Liefert ein Cookie mit einem bestimmte Namen aus einem Array von Cookies
	 * 
	 * @param cookies
	 *            ein Array von Cookies
	 * @param name
	 *            Der Name des Cookies
	 * @return das Cookie oder null, wenn keins mit diesem Namen gefunden wurdew
	 */
	public static Cookie getCookie(Cookie cookies[], String name) {

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie;
				}
			}
		}

		// we've got this far so the specified cookie wasn't found
		return null;
	}

	/**
	 * schreibt ein Cookie in den Request
	 * 
	 * @param response
	 *            der HttpServletResponse
	 * @param name
	 *            der Name des Cookie
	 * @param value
	 *            der Wert des Cookie
	 * @param maxAge
	 *            die Lebensdauer in Sekunden
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge, String domain) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		if (!Strings.isEmpty(domain)) {
			cookie.setDomain(domain);
		}
		response.addCookie(cookie);
	}

	/**
	 * entfernt ein cookie aus dem Request
	 * 
	 * @param response
	 *            der HttpServletResponse
	 * @param name
	 *            der Name des Cookies
	 */
	public static void removeCookie(HttpServletResponse response, String name) {
		addCookie(response, name, "", 0, null);
	}

}