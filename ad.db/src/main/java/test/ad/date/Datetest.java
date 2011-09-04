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
package test.ad.date;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;

public class Datetest {
	
	private static IndexSearcher searcher = null;
	
	public static void main (String [] args) throws Exception {
		RAMDirectory dir = new RAMDirectory();
//		FSDirectory dir =  FSDirectory.open(new File("lt/"));
		IndexWriter writer = new IndexWriter(dir, new KeywordAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
		
	
		List<Day> days = new ArrayList<Day>();
		days.add(Day.All);
		List<State> states= new ArrayList<State>();
		states.add(State.All);
		writer.addDocument(getDoc("b1", "0600", "0800", "all", "all", days, states));
		writer.addDocument(getDoc("b2", "all", "all", "all", "all", days, states));
		writer.addDocument(getDoc("b3", "0600", "0700", "all", "all", days, states));
		writer.addDocument(getDoc("b4", "0500", "0700", "all", "all", days, states));
		writer.addDocument(getDoc("b5", "0630", "0700", "all", "all", days, states));
		writer.addDocument(getDoc("b6", "0800", "0900", "all", "all", days, states));
		
		writer.optimize();
		writer.close();
		
		searcher = new IndexSearcher(dir);
		
		System.out.println("\n6Uhr");
		searchTest("0600", "20101221", Day.All, 4);
		
		System.out.println("\n6:30Uhr");
		searchTest("0630", null, Day.All, 5);
		
		System.out.println("\n5Uhr");
		searchTest("0500", null, Day.All, 2);
		
		System.out.println("\n8:30Uhr");
		searchTest("0830", null, Day.All, 2);
		
		dir.close();
	}
	
	public static void searchTest (String time, String date, Day day, int count) throws Exception {
		BooleanQuery mainQuery = new BooleanQuery();
		
		Query query = getTimeQuery(time);
		if (query != null) {
			mainQuery.add(query, Occur.MUST);
		}
		
		query = getDateQuery(date);
		if (query != null) {
			mainQuery.add(query, Occur.MUST);
		}
		
		query = getDayQuery(day.getDay());
		if (query != null) {
			mainQuery.add(query, Occur.MUST);
		}
		
//		
		System.out.println("mainQuery: " + mainQuery.toString());
		
		MyCollector result = new MyCollector();
		searcher.search(mainQuery, result);
		
		List<Integer> hits = result.getHits();
		System.out.println("Treffer " + hits.size() + " von erwartet: " + count);
		for (Integer docid : result.getHits()) {
			System.out.print(searcher.doc(docid).get("name") + " ");
		}
	}
	
	public static Query getDayQuery (int day) {
		if (day == Day.All.getDay()) {
			return null;
		}
		BooleanQuery query = new BooleanQuery();
		
		BooleanQuery temp = new BooleanQuery();
		temp.add(new TermQuery(new Term("day", String.valueOf(day))), Occur.SHOULD);
		temp.add(new TermQuery(new Term("day", String.valueOf(Day.All.getDay()))), Occur.SHOULD);
		
		query.add(temp, Occur.MUST);
		
		
		return query;
	}
	
	public static Query getStateQuery (int state) {
		if (state == State.All.getState()) {
			return null;
		}
		BooleanQuery query = new BooleanQuery();
		
		BooleanQuery temp = new BooleanQuery();
		temp.add(new TermQuery(new Term("state", String.valueOf(state))), Occur.SHOULD);
		temp.add(new TermQuery(new Term("state", String.valueOf(State.All.getState()))), Occur.SHOULD);
		
		query.add(temp, Occur.MUST);
		
		
		return query;
	}
	
	public static Query getTimeQuery (String time) {
		BooleanQuery query = null;
		
		if (time != null) { 
			query = new BooleanQuery();
			
			BooleanQuery temp = new BooleanQuery();
			TermRangeQuery tQuery = new TermRangeQuery("time_from", "0000", time, true, true);
			temp.add(tQuery, Occur.SHOULD);
			temp.add(new TermQuery(new Term("time_from", "all")), Occur.SHOULD);
			
			query.add(temp, Occur.MUST);
			
			temp = new BooleanQuery();
			tQuery = new TermRangeQuery("time_to", time, "2500", true, true);
			temp.add(tQuery, Occur.SHOULD);
			temp.add(new TermQuery(new Term("time_to", "all")), Occur.SHOULD);
			
			query.add(temp, Occur.MUST);
		}
		
		return query;
	}
	public static Query getDateQuery (String date) {
		BooleanQuery query = null;
		
		if (date != null) { 
			query = new BooleanQuery();
			
			BooleanQuery temp = new BooleanQuery();
			TermRangeQuery tQuery = new TermRangeQuery("date_from", null, date, true, true);
			temp.add(tQuery, Occur.SHOULD);
			temp.add(new TermQuery(new Term("date_from", "all")), Occur.SHOULD);
			
			query.add(temp, Occur.MUST);
			
			temp = new BooleanQuery();
			tQuery = new TermRangeQuery("date_to", date, null, true, true);
			temp.add(tQuery, Occur.SHOULD);
			temp.add(new TermQuery(new Term("time_to", "all")), Occur.SHOULD);
			
			query.add(temp, Occur.MUST);
		}
		
		return query;
	}
	
	public static Document getDoc (String name, String timefrom, String timeto, String datefrom, String dateto, List<Day> days, List<State> states) {
		Document doc = new Document();
		
		if (days != null) {
			for (Day day : days) {
				doc.add(new Field("day", String.valueOf(day.getDay()), Store.NO, Index.NOT_ANALYZED_NO_NORMS));
			}
		} else {
			doc.add(new Field("day", String.valueOf(Day.All.getDay()), Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		}
		if (states != null) {
			for (State state : states) {
				doc.add(new Field("state", String.valueOf(state.getState()), Store.NO, Index.NOT_ANALYZED_NO_NORMS));
			}
		} else {
			doc.add(new Field("state", String.valueOf(State.All.getState()), Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		}
		doc.add(new Field("time_from", timefrom, Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("time_from", timefrom, Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("time_to", timeto, Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("date_from", datefrom, Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("date_to", dateto, Store.NO, Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field("name", name, Store.YES, Index.NOT_ANALYZED_NO_NORMS));
		
		
		return doc;
	}
	
}
