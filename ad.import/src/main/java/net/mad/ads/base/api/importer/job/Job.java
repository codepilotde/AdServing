package net.mad.ads.base.api.importer.job;

public abstract class Job {
	public enum Type {
		Import,
		Update,
		Delete
	}
	
	protected Type type = null;
	protected int num = 0;
	public Job (Type type) {
		this.type = type;
	}
	
	public Type getType () {
		return this.type;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
}
