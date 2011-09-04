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
package net.mad.ads.db.condition;


import com.google.common.base.Predicate;

import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;

/**
 * Filter werden nach der Suche �ber das Ergebnis laufen gelassen. Dadurch können 
 * noch Banner gefiltert werden, die eine Bestimmte Eigenschaft nicht erfüllen
 * 
 * @author thmarx
 *
 */
public interface Filter {
	/**
	 * Die Eigenschaft, die ein Banner erfüllen muss um nicht aus der List herrausgefiltert zu werden
	 * 
	 * @param request
	 * @return
	 */
	public Predicate<BannerDefinition> getFilterPredicate (AdRequest request);
}
