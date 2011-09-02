package net.mad.ads.db.definition;

import de.marx.common.tools.Strings;
import de.marx.common.tools.code.Base16;




public class AdSlot {
	
	private String site;
	private String zone;
	private String place;
	
	public AdSlot (String site, String zone, String place) {
		this.site = site;
		this.zone = zone;
		this.place = place;
	}
	
	public String getSite() {
		return site;
	}



	public String getZone() {
		return zone;
	}



	public String getPlace() {
		return place;
	}

	@Override
	public String toString() {
//		new Base
		return Base16.encode(site) + "-" + Base16.encode(zone) + "-" + Base16.encode(place);
	}
	
	public static AdSlot fromString (String uuid) throws IllegalArgumentException{
		if (Strings.isEmpty(uuid)) {
            throw new IllegalArgumentException("Invalid AdUUID string: " + uuid);
        }
		String[] components = uuid.split("-");
        if (components.length != 3) {
            throw new IllegalArgumentException("Invalid AdUUID string: " + uuid);
        }
        
        AdSlot aduuid = new AdSlot(Base16.decode(components[0]), Base16.decode(components[1]), Base16.decode(components[2]));
        
        return aduuid;
	}


	public static void main (String []args) throws Exception {
		AdSlot uuid = new AdSlot("1", "2", "3");
		System.out.println(uuid.toString());
		AdSlot uuid2 = AdSlot.fromString(uuid.toString());
		
		System.out.println(uuid2.getSite());
		System.out.println(uuid2.getZone());
		System.out.println(uuid2.getPlace());
	}
}
