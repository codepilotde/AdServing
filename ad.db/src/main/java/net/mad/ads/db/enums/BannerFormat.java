package net.mad.ads.db.enums;

import java.awt.Dimension;
import java.awt.geom.Dimension2D;
import java.io.Serializable;

/**
 * Definition der verfügbaren Bannerformate
 * 
 * @author tmarx
 *
 */
public enum BannerFormat implements Serializable {
	MEDIUM_RECTANGLE("Medium Rectangle", 300, 250),
	SQUARE("Square", 250, 250),
	VERTICAL_RECTANGLE ("Vertical Rectangle", 240, 400),
	LARGE_RECTANGLE ("Large Rectangle", 336, 280),
	RECTANGLE ("Rectangle", 180, 150),
	
	LEADERBOARD ("Leaderboard", 728, 90),
	FULL_BANNER("Full Banner", 468, 60),
	HALF_BANNER("Half Banner", 234, 60),
	VERTICAL_BANNER ("Vertical Banner", 120, 400),
	SQUARE_BUTTON ("Vertical Banner", 125, 125),
	MICROBUTTON("Microbutton", 80, 15),
	BUTTON_1("Button 1", 120, 90),
	BUTTON_2("Microbutton", 120, 60),
	BUTTON_3("Microbutton", 120, 40),
	WIDE_BUTTON_1("Button 1", 160, 90),
	WIDE_BUTTON_2("Microbutton", 160, 60),
	WIDE_BUTTON_3("Microbutton", 160, 40),
	MICRO_BAR ("Micro Bar", 88, 31),
	
	HALF_PAGE_AD("Half Page Ad", 300, 600),
	SKYSCRAPER("Skyscraper", 120, 600),
	WIDE_SKYSCRAPER("Wide Skyscraper", 160, 600);
	
	private String name = null;
	private int width = -1;
	private int height = -1;
	private BannerFormat (String name, int width, int height) {
		this.name = name;
		this.width = width;
		this.height = height;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public String getCompoundName () {
		return width + "x" + height;
	}
	
	public static BannerFormat fromCompoundName (String compound) {
		for (BannerFormat format : BannerFormat.values()) {
			if (format.getCompoundName().equalsIgnoreCase(compound)) {
				return format;
			}
		}
		return null;
	}
	
	public static BannerFormat forName (String name) {
		for (BannerFormat format : BannerFormat.values()) {
			if (format.getName().equalsIgnoreCase(name)) {
				return format;
			}
		}
		return null;
	}
}
