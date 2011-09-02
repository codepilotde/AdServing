package net.mad.ads.base.api.model.ads;

/**
 * Basis Klasse für die Werbung
 * hier werden alle Konfigurationen vorgenommen
 * 
 * In den konkreten Implementierungen werden dann die verschiedenen 
 * Arten (Text, Bild usw.) implementiert
 * 
 * @author thorsten
 *
 */
public abstract class Advertisement {
	private String id;
	private String campaignId;
}
