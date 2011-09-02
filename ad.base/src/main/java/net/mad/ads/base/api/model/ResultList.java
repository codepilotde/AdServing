package net.mad.ads.base.api.model;

import java.util.ArrayList;


public class ResultList<T> extends ArrayList<T>{
	private int page;
	private int count;
	private int perPage;
	
	public ResultList () {
		
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	
	
}
