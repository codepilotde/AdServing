package net.mad.ads.db.definition;

import java.io.Serializable;

public class Keyword implements Serializable {
	private String word;
	
	public Keyword () {
	}

	public final String getWord() {
		return word;
	}

	public final void setWord(String word) {
		this.word = word;
	}
}
