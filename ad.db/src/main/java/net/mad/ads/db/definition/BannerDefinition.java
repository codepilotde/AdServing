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
package net.mad.ads.db.definition;

import java.io.Serializable;
import java.util.List;

import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;
import net.mad.ads.db.utils.geo.GeoLocation;

public interface BannerDefinition extends Serializable {
	/**
	 * ID des Banners
	 * @return
	 */
	public String getId ();
	public void setId (String id);
	
	/**
	 * liefert das BannerFormat
	 * @return
	 */
	public BannerFormat getFormat ();
	/**
	 * setzt das BannerFormat
	 * @param format
	 */
	public void setFormat (BannerFormat format);
	
	/**
	 * Liefert den BannerType (Image, Text)
	 * @return
	 */
	public BannerType getType ();
	/**
	 * Die Zielurl, die bei einem Klick aufgerufen werden soll
	 * @return
	 */
	public String getTargetUrl();
	public void setTargetUrl (String url);
	
	public String getLinkTarget ();
	public void setLinkTarget (String target);
	
	public String getLinkTitle ();
	public void setLinkTitle (String title);
	
	public boolean hasConditionDefinitions ();
	public boolean hasConditionDefinition (ConditionDefinitions key);
	public ConditionDefinition getConditionDefinition (ConditionDefinitions key);
	public void addConditionDefinition (ConditionDefinitions key, ConditionDefinition value);
	
	/*
	 * Banner können zu Produkten zusammengefasst werden, dadurch können mehrere Banner eines Kunden
	 * auf einer Seite an verschiedenen Plätzen angezeigt werden.
	 * 
	 * Bei der Erstellung von Produkten muss die extra Konfiguration bzgl. Auslieferung beachtet werden.
	 * Da es verschiedene Banner sind, muss darauf geachtet werden, dass sie auch den selben Einschränkungen unterliegen
	 * 
	 * Product können auch als Tandem-Banner bezeichnet werden 
	 */
	public boolean isProduct ();
	public String getProduct ();
	public void setProduct (String product);
}