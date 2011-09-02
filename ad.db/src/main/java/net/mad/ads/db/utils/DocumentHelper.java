package net.mad.ads.db.utils;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import net.mad.ads.db.AdDBConstants;
import net.mad.ads.db.AdDBManager;
import net.mad.ads.db.condition.Condition;
import net.mad.ads.db.definition.BannerDefinition;
import net.mad.ads.db.enums.Country;
import net.mad.ads.db.enums.Day;
import net.mad.ads.db.enums.State;

public class DocumentHelper {
	private static DocumentHelper INSTANCE = null;
	
	private DocumentHelper (){
	}
	
	public static synchronized DocumentHelper getInstance () {
		if (INSTANCE == null){
			INSTANCE = new DocumentHelper();
		}
		return INSTANCE;
	}
	
	public Document getBannerDocument (BannerDefinition banner) {
		Document doc = new Document();
		doc.add(new Field(AdDBConstants.ADDB_BANNER_ID, String.valueOf(banner.getId()), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
		doc.add(new Field(AdDBConstants.ADDB_BANNER_FORMAT, banner.getFormat().getCompoundName(), Field.Store.NO, Field.Index.ANALYZED));
		doc.add(new Field(AdDBConstants.ADDB_BANNER_TYPE, banner.getType().name(), Field.Store.NO, Field.Index.ANALYZED));
		
		if (banner.isProduct()) {
			doc.add(new Field(AdDBConstants.ADDB_BANNER_PRODUCT, AdDBConstants.ADDB_BANNER_PRODUCT_TRUE, Field.Store.NO, Field.Index.ANALYZED));
		} else {
			doc.add(new Field(AdDBConstants.ADDB_BANNER_PRODUCT, AdDBConstants.ADDB_BANNER_PRODUCT_FALSE, Field.Store.NO, Field.Index.ANALYZED));
		}
		
		doc = addConditions(banner, doc);
		
		return doc;
	}
	
	private Document addConditions (BannerDefinition banner, Document doc) {
		
		for (Condition condition : AdDBManager.getInstance().getConditions()) {
			condition.addFields(doc, banner);
		}
		
		return doc;
	}
}
