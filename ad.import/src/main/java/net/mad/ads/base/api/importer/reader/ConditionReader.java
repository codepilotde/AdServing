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
package net.mad.ads.base.api.importer.reader;

import java.util.List;

import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.definition.AdSlot;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.Keyword;
import net.mad.ads.db.definition.condition.AdSlotConditionDefinition;
import net.mad.ads.db.definition.condition.ClickExpirationConditionDefinition;
import net.mad.ads.db.definition.condition.CountryConditionDefinition;
import net.mad.ads.db.definition.condition.DateConditionDefinition;
import net.mad.ads.db.definition.condition.DayConditionDefinition;
import net.mad.ads.db.definition.condition.DistanceConditionDefinition;
import net.mad.ads.db.definition.condition.ExcludeSiteConditionDefinition;
import net.mad.ads.db.definition.condition.KeywordConditionDefinition;
import net.mad.ads.db.definition.condition.SiteConditionDefinition;
import net.mad.ads.db.definition.condition.StateConditionDefinition;
import net.mad.ads.db.definition.condition.TimeConditionDefinition;
import net.mad.ads.db.definition.condition.ViewExpirationConditionDefinition;
import net.mad.ads.db.enums.ConditionDefinitions;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.ExpirationResolution;
import net.mad.ads.db.enums.State;
import net.mad.ads.db.utils.geo.GeoLocation;

public class ConditionReader {
	
	private static final Logger logger = LoggerFactory.getLogger(ConditionReader.class);
	
	public static BannerDefinition processConditions (BannerDefinition definition, Element conditions) {
		
		if (conditions == null || conditions.getChildren() == null || conditions.getChildren().size() == 0) {
			
			DateConditionDefinition dateDef = new DateConditionDefinition();
			dateDef.addPeriod(null, null);
			definition.addConditionDefinition(ConditionDefinitions.DATE, dateDef);
			
			TimeConditionDefinition tdef = new TimeConditionDefinition();
//			tdef.setFrom(AdDBConstants.ADDB_BANNER_TIME_ALL);
//			tdef.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
			tdef.getPeriods().add(TimeConditionDefinition.ALL_TIMES);
			definition.addConditionDefinition(ConditionDefinitions.TIME, tdef);
			
			
			DayConditionDefinition ddef = new DayConditionDefinition();
			ddef.addDay(Day.All);
			definition.addConditionDefinition(ConditionDefinitions.DAY, ddef);
			
			StateConditionDefinition stDef = new StateConditionDefinition();
			stDef.addState(State.All);
			definition.addConditionDefinition(ConditionDefinitions.STATE, stDef);
			
			CountryConditionDefinition cdef = new CountryConditionDefinition();
			cdef.addCountry(Country.ALL);
			definition.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
			
			// keine Keyword Einschwänkung für dieses Banner
			Keyword kw = new Keyword();
			kw.setWord(AdDBConstants.ADDB_BANNER_KEYWORD_ALL);
			
			KeywordConditionDefinition kdef = new KeywordConditionDefinition();
			kdef.addKeyword(kw);
			definition.addConditionDefinition(ConditionDefinitions.KEYWORD, kdef);
			
			// Banner soll auf allen Seiten angezeeit werden
			SiteConditionDefinition sdef = new SiteConditionDefinition();
			sdef.addSite(AdDBConstants.ADDB_BANNER_SITE_ALL);
			definition.addConditionDefinition(ConditionDefinitions.SITE, sdef);
			
			AdSlotConditionDefinition adsdef = new AdSlotConditionDefinition();
			definition.addConditionDefinition(ConditionDefinitions.ADSLOT, adsdef);
			
		} else {
			Element elem = conditions.getChild("time");
			if (elem != null) {
				TimeConditionDefinition tdef = new TimeConditionDefinition();
				
//				tdef.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
				
				Element periods = elem.getChild("periods");
				if (periods != null) {
					List<Element> periodsListe = periods.getChildren("period");
					for (Element perion : periodsListe) {
						
						TimeConditionDefinition.Period timep = new TimeConditionDefinition.Period();
						
						if (perion.getChild("from") != null) {
							timep.setFrom(elem.getChildText("from"));
						} else {
							timep.setFrom(AdDBConstants.ADDB_BANNER_TIME_ALL);
						}
						if (perion.getChild("to") != null) {
							timep.setTo(elem.getChildText("to"));
						} else {
							timep.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
						}
						tdef.getPeriods().add(timep);
					}
				}
				
				
				definition.addConditionDefinition(ConditionDefinitions.TIME, tdef);
			} else {
				TimeConditionDefinition tdef = new TimeConditionDefinition();
//				tdef.setFrom(AdDBConstants.ADDB_BANNER_TIME_ALL);
//				tdef.setTo(AdDBConstants.ADDB_BANNER_TIME_ALL);
				tdef.getPeriods().add(TimeConditionDefinition.ALL_TIMES);
				definition.addConditionDefinition(ConditionDefinitions.TIME, tdef);
			}
			elem = conditions.getChild("date");
			if (elem != null) {
				DateConditionDefinition dateDef = new DateConditionDefinition();
				
				Element periods = elem.getChild("periods");
				if (periods != null) {
					List<Element> periodsListe = periods.getChildren("period");
					for (Element perion : periodsListe) {
						
						DateConditionDefinition.Period timep = new DateConditionDefinition.Period();
						
						if (perion.getChild("from") != null) {
							timep.setFrom(elem.getChildText("from"));
						} else {
							timep.setFrom(AdDBConstants.ADDB_BANNER_DATE_ALL);
						}
						if (perion.getChild("to") != null) {
							timep.setTo(elem.getChildText("to"));
						} else {
							timep.setTo(AdDBConstants.ADDB_BANNER_DATE_ALL);
						}
						dateDef.getPeriods().add(timep);
					}
				}
				
				definition.addConditionDefinition(ConditionDefinitions.DATE, dateDef);
			} else {
				DateConditionDefinition dateDef = new DateConditionDefinition();
				dateDef.addPeriod(null, null);
				definition.addConditionDefinition(ConditionDefinitions.DATE, dateDef);
			}
			elem = conditions.getChild("days");
			if (elem != null) {
				List<Element> childs = elem.getChildren();
				if (childs != null) {
					DayConditionDefinition ddef = new DayConditionDefinition();
					for (Element e : childs) {
						String text = e.getTextTrim();
						try {
							Day d = Day.getDayForInt(Integer.valueOf(text));
							ddef.addDay(d);
						} catch (Exception ex) {
							logger.error("", e);
						}
					}
					definition.addConditionDefinition(ConditionDefinitions.DAY, ddef);
				}
			} else {
				DayConditionDefinition ddef = new DayConditionDefinition();
				ddef.addDay(Day.All);
				definition.addConditionDefinition(ConditionDefinitions.DAY, ddef);
			}
			elem = conditions.getChild("states");
			if (elem != null) {
				List<Element> childs = elem.getChildren();
				if (childs != null) {
					StateConditionDefinition stDef = new StateConditionDefinition();
					for (Element e : childs) {
						String text = e.getTextTrim();
						try {
							State s = State.getStateForInt(Integer.valueOf(text));
							stDef.addState(s);
						} catch (Exception ex) {
							logger.error("", e);
						}
					}
					definition.addConditionDefinition(ConditionDefinitions.STATE, stDef);
				}
			} else {
				StateConditionDefinition stDef = new StateConditionDefinition();
				stDef.addState(State.All);
				definition.addConditionDefinition(ConditionDefinitions.STATE, stDef);
			}
			elem = conditions.getChild("countries");
			if (elem != null) {
				List<Element> childs = elem.getChildren();
				if (childs != null) {
					CountryConditionDefinition cdef = new CountryConditionDefinition();
					for (Element e : childs) {
						String text = e.getTextTrim();
						try {
							Country c = Country.getCountryForString(text);
							cdef.addCountry(c);
						} catch (Exception ex) {
							logger.error("", e);
						}
					}
					definition.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
				}
			} else {
				CountryConditionDefinition cdef = new CountryConditionDefinition();
				cdef.addCountry(Country.ALL);
				definition.addConditionDefinition(ConditionDefinitions.COUNTRY, cdef);
			}
			
			// Adslots
			elem = conditions.getChild("slots");
			if (elem != null) {
				List<Element> childs = elem.getChildren("slot");
				if (childs != null) {
					AdSlotConditionDefinition stDef = new AdSlotConditionDefinition();
					for (Element e : childs) {
						String text = e.getTextTrim();
						try {
							stDef.addSlots(AdSlot.fromString(text));
						} catch (Exception ex) {
							logger.error("", e);
						}
					}
					definition.addConditionDefinition(ConditionDefinitions.ADSLOT, stDef);
				}
			} else {
				definition.addConditionDefinition(ConditionDefinitions.ADSLOT, new AdSlotConditionDefinition());
			}
			
			elem = conditions.getChild("keywords");
			if (elem != null) {
				List<Element> childs = elem.getChildren("kw");
				if (childs != null) {
					KeywordConditionDefinition kdef = new KeywordConditionDefinition();
					
					for (Element e : childs) {
						String text = e.getTextTrim();
						try {
							Keyword kw = new Keyword();
							kw.setWord(text);
							kdef.addKeyword(kw);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
					definition.addConditionDefinition(ConditionDefinitions.KEYWORD, kdef);
				}
			} else {
				Keyword kw = new Keyword();
				kw.setWord(AdDBConstants.ADDB_BANNER_KEYWORD_ALL);
				
				KeywordConditionDefinition kdef = new KeywordConditionDefinition();
				kdef.addKeyword(kw);
				definition.addConditionDefinition(ConditionDefinitions.KEYWORD, kdef);
			}
			elem = conditions.getChild("sites");
			if (elem != null) {
				List<Element> childs = elem.getChildren("site");
				if (childs != null) {
					SiteConditionDefinition sdef = new SiteConditionDefinition();
					
					for (Element e : childs) {
						String text = e.getTextTrim();
						sdef.addSite(text);
					}
					definition.addConditionDefinition(ConditionDefinitions.SITE, sdef);
				}
			} else {
				SiteConditionDefinition sdef = new SiteConditionDefinition();
				sdef.addSite(AdDBConstants.ADDB_BANNER_SITE_ALL);
				definition.addConditionDefinition(ConditionDefinitions.SITE, sdef);
			}
			
			elem = conditions.getChild("exclude_sites");
			if (elem != null) {
				List<Element> childs = elem.getChildren("site");
				if (childs != null) {
					ExcludeSiteConditionDefinition sdef = new ExcludeSiteConditionDefinition();
					
					for (Element e : childs) {
						String text = e.getTextTrim();
						sdef.addSite(text);
					}
					definition.addConditionDefinition(ConditionDefinitions.EXCLUDE_SITE, sdef);
				}
			}
			
			elem = conditions.getChild("distance");
			if (elem != null) {
				String latitude = elem.getChild("latitude").getValue();
				String longitude = elem.getChild("longitude").getValue();
				String radius = elem.getChild("radius").getValue();

				GeoLocation geo = new GeoLocation(Double.parseDouble(latitude), Double.parseDouble(longitude));
				DistanceConditionDefinition sdef = new DistanceConditionDefinition();
				sdef.setGeoLocation(geo);
				sdef.setGeoRadius(Integer.parseInt(radius));
				
				definition.addConditionDefinition(ConditionDefinitions.DISTANCE, sdef);
			}
			
			elem = conditions.getChild("expiration");
			if (elem != null) {
				List<Element> childs = elem.getChildren();
				if (childs != null) {
					ViewExpirationConditionDefinition vdef = new ViewExpirationConditionDefinition();
					ClickExpirationConditionDefinition cdef = new ClickExpirationConditionDefinition();
					
					for (Element elem2 : childs) {
						String name = elem2.getName();
						String count = elem2.getValue();
						String resolution = elem2.getAttributeValue("resolution");
						
						ExpirationResolution res = ExpirationResolution.forName(resolution);
						if (res.equals(ExpirationResolution.NONE)) {
							continue;
						}
						if (name.equalsIgnoreCase("clicks")) {
							cdef.getClickExpirations().put(res, Integer.parseInt(count.trim()));
						} else if (name.equalsIgnoreCase("views")) {
							vdef.getViewExpirations().put(res, Integer.parseInt(count.trim()));
						}	
						
						
					}
					if (vdef.getViewExpirations().size() > 0) {
						definition.addConditionDefinition(ConditionDefinitions.VIEW_EXPIRATION, vdef);
					}
					if (cdef.getClickExpirations().size() > 0) {
						definition.addConditionDefinition(ConditionDefinitions.CLICK_EXPIRATION, cdef);
					}
				}
			}
		}
		
		return definition;
	}
}
