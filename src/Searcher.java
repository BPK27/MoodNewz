import java.io.File;
import java.util.Vector;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Searcher {

	int hitsPerPage = 10;
    Directory index;
    IndexReader reader;
    IndexSearcher searcher;
    TopScoreDocCollector collector;// = TopScoreDocCollector.create(hitsPerPage, true);
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
    public Vector<String> resultsHeadlines = new Vector<String>();
    public Vector<String> resultsArticles = new Vector<String>();
    
	public Searcher(String indexPath){
		try{
			index = FSDirectory.open(new File(indexPath));   //open the index 	
			reader = DirectoryReader.open(index);//read the index
			searcher = new IndexSearcher(reader);//search the index
		}catch(Exception e){e.printStackTrace();}
	}
	
	public ScoreDoc[] search(String q, String field){
		ScoreDoc[] hits = null;
		collector = TopScoreDocCollector.create(hitsPerPage, true);
		try{
			searcher.search(getQuery(q,field), collector);
			hits = collector.topDocs().scoreDocs;
		}catch(Exception e){e.printStackTrace();}
		return hits;
	}
	
	//returns a Query for the input query string and the given field
	public Query getQuery(String q, String field){
		try{
			return new QueryParser(Version.LUCENE_42, field, analyzer).parse(q);
		}catch(Exception e){e.printStackTrace();}
		return null;
	}
	
	//print results
	public void printHits(ScoreDoc[] hits){
		//Vector<String> results = new Vector<String>();
		try{
			System.out.println("Found " + hits.length + " hits.");
			for(int i=0;i<hits.length;++i) {
			    int docId = hits[i].doc;
			    float score = hits[i].score;			 
			    Document d = searcher.doc(docId);	
			    resultsHeadlines.add(d.get("headline"));
			    resultsArticles.add(d.get("article"));
			    //System.out.println((i + 1) + ". " + d.get("headline") + "\t" + d.get("article")+" --- "+score);
			}	
		}catch(Exception e){e.printStackTrace();}
	}
}
