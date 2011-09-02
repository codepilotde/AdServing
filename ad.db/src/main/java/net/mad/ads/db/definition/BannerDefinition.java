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
	 */
	public boolean isProduct ();
	public String getProduct ();
	public void setProduct (String product);
}