package net.mad.ads.db.db.search;

import java.io.IOException;
import java.util.BitSet;
import java.util.Collection;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Scorer;

public class BannerCollector extends Collector {

	private Scorer scorer;
	private int docBase;

	private BitSet hits = null;

	public BannerCollector(int size) {
		hits = new BitSet(size);
	}

	// simply print docId and score of every matching document
	@Override
	public void collect(int doc) throws IOException {
		hits.set(doc);
	}

	@Override
	public boolean acceptsDocsOutOfOrder() {
		return true;
	}

	@Override
	public void setNextReader(IndexReader reader, int docBase)
			throws IOException {
		this.docBase = docBase;
	}

	@Override
	public void setScorer(Scorer scorer) throws IOException {
		this.scorer = scorer;
	}
	
	public BitSet getHits () {
		return hits;
	}
}
