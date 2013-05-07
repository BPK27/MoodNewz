import java.io.File;
import java.io.IOException;
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
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class Searcher {

	int hitsPerPage = 10;
	QueryScorer qs;
    Directory index;
    IndexReader reader;
    Highlighter highlight;
    IndexSearcher searcher;
    TopScoreDocCollector collector;// = TopScoreDocCollector.create(hitsPerPage, true);
    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
    public Vector<String> resultsHeadlines = new Vector<String>();
    private Vector<String> resultsArticles = new Vector<String>();
    public Vector<String[]> resultsBoldedArticles = new Vector<String[]>();
    public Vector<String[]> fragments = new Vector<String[]>();
    
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
			qs = new QueryScorer(getQuery(q,field));
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
	
	//writes results to vectors
	public void printHits(ScoreDoc[] hits){
		try{
			System.out.println("Found " + hits.length + " hits.");
			for(int i=0;i<hits.length;++i) {
			    int docId = hits[i].doc;
			    Document d = searcher.doc(docId);
			    resultsHeadlines.add(d.get("headline")); //headlines
			    resultsArticles.add(d.get("article"));  //articles(no formatting)
			}	
		}catch(Exception e){e.printStackTrace();}
	}
	
	//returns formatted article with highlighting of query terms
	public void Highlighter(String q, String field) throws IOException, InvalidTokenOffsetsException{
		QueryScorer qts = new QueryScorer(getQuery(q, field));
		Highlighter highlighter = new Highlighter(qts);
		
		for(String article : resultsArticles){
			fragments.add(highlighter.getBestFragments(analyzer, "article", article, 50));  //fragment for display
			resultsBoldedArticles.add(highlighter.getBestFragments(analyzer, "article", article, 1000));  //set to return 1000 numFragments to retrieve entire article
		}
		
	}
}
