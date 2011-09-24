package net.mad.ads.base.api.model.site;

import net.mad.ads.base.api.model.BaseModel;

public abstract class BasePageModel extends BaseModel {
	
	/*
	 * the name of the object
	 */
	private String name;
	/*
	 * The description of the object 
	 */
	private String description;
	
	
	
	

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
