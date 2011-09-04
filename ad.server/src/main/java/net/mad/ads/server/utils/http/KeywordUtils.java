/*
 * Mad-Advertisement 
 * Copyright (C) 2011 Thorsten Marx
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

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.de.GermanAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.server.utils.helper.EncodeHelper;
import net.mad.ads.server.utils.request.RequestHelper;
import de.marx.common.tools.Strings;

public class KeywordUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(KeywordUtils.class);
	
	
	private static final Map<String, String> seParams = new HashMap<String, String>();
	static {
		seParams.put("google", "q");
		seParams.put("bing", "q");
		seParams.put("yahoo", "p");
		seParams.put("localhost", "q");
	}
	
	public static List<String> getKeywords (HttpServletRequest request) {
		
		String query = null;
		if (request.getParameter(RequestHelper.keywords) != null) {
			query = request.getParameter(RequestHelper.keywords);
		} else if (request.getParameter(RequestHelper.referrer) != null) {
			String referrer = request.getParameter(RequestHelper.referrer);
			
			query = getSearchEngineQueryString(request, referrer);
		} else {
			query = getSearchEngineQueryString(request);
		}
		
		if (Strings.isEmpty(query)) {
			return null;
		}
		
		query = decodeUrlString(query);
		
		return getTokens(query);
	}
	
	public static String getQueryStringParameter(
			String queryString, String parameterName) {
		
		String[] parametersArray = queryString.split("&");
		for (String parameter : parametersArray) {
			
			if (parameter.contains(parameterName + "=")) {
				
				return parameter.split("=")[1];
			}
		}
		return null;
	}
	
	public static String decodeUrlString (String urlString) {
		return EncodeHelper.decodeURIComponent(urlString);
	}
	
	public static List<String> getTokens (String queryString) {
		try {
			GermanAnalyzer a = new GermanAnalyzer(Version.LUCENE_33);

			TokenStream ts = a.tokenStream("",
					new StringReader(queryString));

			List<String> tokens = new ArrayList<String>();
			
			CharTermAttribute termAtt = ts.getAttribute(CharTermAttribute.class);
			ts.reset();
			while (ts.incrementToken()) {
				String token = termAtt.toString();
				tokens.add(token);
			}
			ts.end();
			ts.close();
			
			return tokens;
		} catch (IOException e) {
			logger.error("", e);
		}
		return null;
	}
	
	public static String getSearchEngineQueryString (HttpServletRequest request) {
		String referrer = request.getHeader("Referer");
		
		return getSearchEngineQueryString(request, referrer);
		
	}
	
	public static String getSearchEngineQueryString (
			HttpServletRequest request, String referrer) {

		
		String queryString = null;
		String hostName = null;
		if (referrer != null) {
			
			//Validate that Referer header value is a correct URL, a
			//MalformedURLException is thrown if not
			URL refererURL;
			try {
				refererURL = new URL(referrer);
			} catch (MalformedURLException e) {
				return null;
			}
			
			hostName = refererURL.getHost();
			queryString = refererURL.getQuery();
			
			if (Strings.isEmpty(queryString)) {
				return null;
			}
			
			Set<String> keys = seParams.keySet();
			for (String se : keys) {
				if (hostName.toLowerCase().contains(se)) {
					queryString = getQueryStringParameter(queryString, seParams.get(se));
				}
			}
			
//			if (hostName.toLowerCase().contains("google")) {
//				
//				queryString = getQueryStringParameter(queryString, "q");
//			
//			} else if (hostName.toLowerCase().contains("yahoo")) {
//				
//				queryString = getQueryStringParameter(queryString, "p");
//				
//			} else if (hostName.toLowerCase().contains("bing")) {
//				
//				queryString = getQueryStringParameter(queryString, "q");
//				
//			} else {
//
//				//Search engine not recognized
//				return null;
//			}
			return queryString;
		}
		//No Referer header found
		return null;
	}
	
	
}
