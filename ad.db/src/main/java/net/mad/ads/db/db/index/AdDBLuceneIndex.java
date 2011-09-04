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
package net.mad.ads.db.db.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.db.AdDB;
import net.mad.ads.db.db.request.AdRequest;
import net.mad.ads.db.db.search.BannerCollector;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.enums.BannerFormat;
import net.mad.ads.db.enums.BannerType;
import net.mad.ads.db.utils.DocumentHelper;
import net.mad.ads.db.utils.QueryHelper;

public class AdDBLuceneIndex implements AdDBIndex {

	private static final Logger logger = LoggerFactory
			.getLogger(AdDBLuceneIndex.class);

	private Directory index = null;
	private IndexWriter writer = null;
	private IndexReader reader = null;
	private IndexSearcher searcher = null;

	private AdDB addb = null;

	public AdDBLuceneIndex(AdDB db) {
		this.addb = db;
	}

	@Override
	public void open() throws IOException {
		index = new RAMDirectory();
		IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_33,
				new KeywordAnalyzer());
		config.setOpenMode(OpenMode.CREATE);
		writer = new IndexWriter(index, config);

		this.reader = IndexReader.open(this.writer, true);
		this.searcher = new IndexSearcher(this.reader);
	}

	@Override
	public void close() throws IOException {
		this.writer.commit();
		this.writer.optimize();
		this.writer.close();
		this.searcher.close();
		this.reader.close();
		this.index.close();
	}

	@Override
	public void reopen() throws IOException {
		this.writer.optimize();
		this.writer.commit();
		IndexReader newReader = this.reader.reopen();
		if (this.reader != newReader) {
			synchronized (this.reader) {
				this.reader.close();
				this.reader = newReader;
			}
			synchronized (searcher) {
				this.searcher.close();
				this.searcher = new IndexSearcher(this.reader);
			}
		}
	}

	@Override
	public void addBanner(BannerDefinition banner) throws IOException {
		Document doc = DocumentHelper.getInstance().getBannerDocument(banner);
		this.writer.addDocument(doc, new KeywordAnalyzer());
	}

	@Override
	public void deleteBanner(String id) throws IOException {
		this.writer.deleteDocuments(new Term(AdDBConstants.ADDB_BANNER_ID, id));
	}

	@Override
	public List<BannerDefinition> search(AdRequest request) throws IOException {
		// Collector für die Banner
		BannerCollector collector = new BannerCollector(this.reader.numDocs());

		// MainQuery
		BooleanQuery mainQuery = new BooleanQuery();
		// Query für den/die BannerTypen
		BooleanQuery typeQuery = new BooleanQuery();
		for (BannerType type : request.getTypes()) {
			TermQuery tq = new TermQuery(new Term(
					AdDBConstants.ADDB_BANNER_TYPE, type.name()));
			typeQuery.add(tq, Occur.SHOULD);
		}
		mainQuery.add(typeQuery, Occur.MUST);

		// Query für den/die BannerFormate
		BooleanQuery formatQuery = new BooleanQuery();
		for (BannerFormat format : request.getFormats()) {
			TermQuery tq = new TermQuery(new Term(
					AdDBConstants.ADDB_BANNER_FORMAT, format.getCompoundName()));
			formatQuery.add(tq, Occur.SHOULD);
		}
		mainQuery.add(formatQuery, Occur.MUST);

		// Query für die Bedingungen unter denen ein Banner angezeigt werden soll
		Query cq = QueryHelper.getInstance().getConditionalQuery(request);
		if (cq != null) {
			mainQuery.add(cq, Occur.MUST);
		}
		
		/*
		 * Es sollen nur Produkte geliefert werden
		 */
		if (request.isProducts()) {
			mainQuery.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_PRODUCT, AdDBConstants.ADDB_BANNER_PRODUCT_TRUE)), Occur.MUST);
		} else {
			mainQuery.add(new TermQuery(new Term(AdDBConstants.ADDB_BANNER_PRODUCT, AdDBConstants.ADDB_BANNER_PRODUCT_FALSE)), Occur.MUST);
			
		}
 
		logger.debug(mainQuery.toString());
//		System.out.println(mainQuery.toString());

		this.searcher.search(mainQuery, collector);

		BitSet hits = collector.getHits();
		// Ergebnis
		List<BannerDefinition> result = new ArrayList<BannerDefinition>();
		for (int i = hits.nextSetBit(0); i != -1; i = hits.nextSetBit(i + 1)) {
			Document doc = this.reader.document(i);
			result.add(addb.getBanner(doc.get(AdDBConstants.ADDB_BANNER_ID)));
		}

		return result;
	}

	@Override
	public int size() {
		if (this.reader != null) {
			return this.reader.numDocs();
		}
		return 0;
	}

}
