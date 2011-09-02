package net.mad.ads.db.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public final class Version {
	private final static String mainVersion;
	private final static String subVersion;
	private final static String bugfix;
	private final static String dev;
	static {
		mainVersion = ResourceBundle.getBundle("META-INF.version", new Locale("addb")).getString("app.main");
		subVersion = ResourceBundle.getBundle("META-INF.version", new Locale("addb")).getString("app.sub");
		bugfix = ResourceBundle.getBundle("META-INF.version", new Locale("addb")).getString("app.bugfix");
		dev = ResourceBundle.getBundle("META-INF.version", new Locale("addb")).getString("app.dev");
	}
	
	private Version () {}
	
	public static void main (String [] args ) {
		System.out.println(Version.mainVersion);
		System.out.println(Version.subVersion);
		System.out.println(Version.bugfix);
		System.out.println(Version.dev);
		
		System.out.println(Version.getVersion());
	}
	
	public static String getVersion () {
		StringBuilder version = new StringBuilder().append(mainVersion).append(".").append(subVersion);
		
		if (bugfix != null && !bugfix.trim().equals("")) {
			version.append(" (bugfix ");
			version.append(bugfix);
			version.append(")");
		}
		if (dev != null && !dev.trim().equals("")) {
			version.append(" (development ");
			version.append(dev);
			version.append(")");
		}
		
		return version.toString();
	}
}
