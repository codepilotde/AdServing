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
package net.mad.ads.server.utils.selection;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Collections2;

import net.mad.ads.common.util.Strings;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.definition.impl.banner.flash.FlashBannerDefinition;
import net.mad.ads.db.definition.impl.banner.image.ImageBannerDefinition;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.server.utils.RuntimeContext;
import net.mad.ads.server.utils.context.AdContext;
import net.mad.ads.server.utils.filter.ClickExpirationFilter;
import net.mad.ads.server.utils.filter.DuplicatBannerFilter;
import net.mad.ads.server.utils.filter.FlashImageFallbackBannerFilter;
import net.mad.ads.server.utils.filter.FlashVersionBannerFilter;
import net.mad.ads.server.utils.filter.ViewExpirationFilter;
import net.mad.ads.server.utils.request.RequestHelper;
import net.mad.ads.server.utils.selection.impl.ImpressionPercentageSingleBannerSelector;
import net.mad.ads.server.utils.selection.impl.RandomSingleBannerSelector;


/**
 * Der BannerProvider führt die Suche nach Bannern aus und Filtert das Ergebnis nach bestimmten Kriterien
 * 
 * @author tmarx
 *
 */
public final class BannerProvider {
	
	private	static final BannerProvider INSTANCE = new BannerProvider();
	
//	private static final BannerSelector SELECTOR = new RandomSingleBannerSelector();
	private static final BannerSelector SELECTOR = new ImpressionPercentageSingleBannerSelector();
	
	private BannerProvider () {
	}
	
	public static final BannerProvider getInstance () {
		return INSTANCE;
	}
	
	/**
	 * Liefert ein Banner
	 * 
	 * @param request
	 * @return
	 */
	public BannerDefinition getBanner (AdContext context, HttpServletRequest request) {
		try {
			// Type
			String type = (String)request.getParameter(RequestHelper.type);
		
			if (type == null || type.equals("")) {
				type = "1";
			}
			
			BannerType btype = BannerType.forType(Integer.parseInt(type));
			
			AdRequest adr = RequestHelper.getAdRequest(context, request);
			// Laden der Banner 
			Collection<BannerDefinition> result = handleProducts(context, adr, request); 
					
			if (result == null) {
				result = RuntimeContext.getAdDB().search(adr);
				result = commonFilter(context, result);
			}
			
			if (btype.equals(BannerType.FLASH)) {
				result = handleFlash(context, result, adr, request);
			}
			
			
			List<BannerDefinition> processedResult = new ArrayList<BannerDefinition>();
			processedResult.addAll(result);
			/*
			 * Aus den restlichen Bannern eins auswählen
			 * 
			 * Aktuell wird dies zufällig gemacht!
			 */
			BannerDefinition banner = SELECTOR.selectBanner(processedResult, context);
			
			return banner;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Behandlung von Produkte
	 * 
	 * 1. Ladend der Produkte
	 * 2. sind Produkte vorhanden, werden diese verwendet
	 * 3. Wurde auf dieser Seite schon ein Produkt eingebunden, werden die passenden Banner für dieses Produkt verwendet
	 * 
	 * @param context
	 * @param adr
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private Collection<BannerDefinition> handleProducts (AdContext context, AdRequest adr, HttpServletRequest request) throws IOException {
		try {
			adr.setProducts(true);
			
			Collection<BannerDefinition> result = RuntimeContext.getAdDB().search(adr);
			if (result == null || result.isEmpty()) {
				return null;
			}
			
			// wird schon ein Produkt angezeigt, dann verwenden wir genau dieses Banner
			for (BannerDefinition banner : result) {
				if (RuntimeContext.getRequestBanners().containsKey("prod" + context.getRequestid() + "_" + banner.getProduct())) {
					Collection<BannerDefinition> result2 = new ArrayList<BannerDefinition>();
					result2.add(banner);
					
					return result2;
				}
			}
			
			result = commonFilter(context, result);
			// ansonten alle für die weiter Verwendung wählen
			return result;
		} finally {
			adr.setProducts(false);
		}
	}
	
	private Collection<BannerDefinition> handleFlash (AdContext context, Collection<BannerDefinition> result, AdRequest adr, HttpServletRequest request) throws IOException {
		/*
		 * Bei Flashbanner gibt es folgende Fallback-Lösung
		 * 1. Kein FLashbanner, dann laden wir ImageBanner
		 * 2. Flashbanner mit falscher Version
		 * 		1. Fallback-Image verwenden
		 * 		2. kein Fallback-Image vorhanden
		 * 			-> Imagebanner neuladen 
		 * 
		 * Diese Implementierung hat den Nachteil, dass Banner mit Fallback-Bild aber einer falschen
		 * Flash-Version seltener angezeigt werden als Banner mit einer passenden Flash-Version.
		 * Grund dafür ist die Bevorzugung des Flashbanners gegeüber der Fallback-Banner.
		 * TODO: andere Lösung erarbeiten
		 */
		if (result.isEmpty()) {
			// Fallback auf ImageBanner
			adr.getTypes().remove(BannerType.FLASH);
			adr.getTypes().add(BannerType.IMAGE);
			result = RuntimeContext.getAdDB().search(adr);
			result = commonFilter(context, result);
		} else {
			String flash = (String)request.getParameter(RequestHelper.flash);
			if (!Strings.isEmpty(flash)) {
				// Alle Banner die die Version erfüllen
				Collection<BannerDefinition> resultVersion = (Collection<BannerDefinition>) Collections2.filter(result, new FlashVersionBannerFilter(Integer.parseInt(flash)));
				if (!resultVersion.isEmpty()) {
					// Flashbanner mit passender Version
					result = resultVersion;
				} else {
					Collection<BannerDefinition> resultImageFallback = (Collection<BannerDefinition>) Collections2.filter(result, new FlashImageFallbackBannerFilter());
					if (!resultImageFallback.isEmpty()) {
						/*
						 * Fallback auf Flashbanner mit Bilder
						 * 
						 * Um Problem mit dem Rendern zu vermeiden werden hier neue Imagebanner erzeugt
						 */
						Collection<BannerDefinition> imageBanners = new ArrayList<BannerDefinition>();
						for (BannerDefinition bdf : resultImageFallback) {
							FlashBannerDefinition fbdf = (FlashBannerDefinition)bdf; 
							ImageBannerDefinition ibdf = new ImageBannerDefinition();
							ibdf.setFormat(bdf.getFormat());
							ibdf.setId(bdf.getId());
							ibdf.setImageUrl(fbdf.getFallbackImageUrl());
							ibdf.setLinkTarget(fbdf.getLinkTarget());
							ibdf.setTargetUrl(bdf.getTargetUrl());
							imageBanners.add(ibdf);
						}
						result = imageBanners;
					} else {
						// Fallback auf ImageBanner
						adr.getTypes().remove(BannerType.FLASH);
						adr.getTypes().add(BannerType.IMAGE);
						result = RuntimeContext.getAdDB().search(adr);
						result = commonFilter(context, result);
					}
				}
			} else {
				// Fallback auf ImageBanner um sicher zu gehen
				adr.getTypes().remove(BannerType.FLASH);
				adr.getTypes().add(BannerType.IMAGE);
				result = RuntimeContext.getAdDB().search(adr);
				
				result = commonFilter(context, result);
			}
		}
		
		return result;
	}
	
	/**
	 * führt die Standard-Filter aus um die Auswahl der Banner nach diesen Kriterien einzuschränken
	 * 
	 * @param context
	 * @param result
	 * @return
	 */
	private Collection<BannerDefinition> commonFilter (AdContext context, Collection<BannerDefinition> result) {
		/*
		 * Filtern der Banner deren maximale Anzahl an Clicks schon erreicht wurden
		 */
		result = (Collection<BannerDefinition>) Collections2.filter(result, new ClickExpirationFilter());
		/*
		 * Filtern der Banner deren maximale Anzahl an Impressions schon erreicht wurden
		 */
		result = (Collection<BannerDefinition>) Collections2.filter(result, new ViewExpirationFilter());
		/*
		 * Filter für das Filtern doppelter Banner
		 */
		result = (Collection<BannerDefinition>) Collections2.filter(result, new DuplicatBannerFilter(context.getRequestid()));
		
		return result;
	}
}
