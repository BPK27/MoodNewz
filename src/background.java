import java.io.IOException;
import java.util.Vector;


public class background {
	String query = "";
	QueryBasedSummarizer qbs;
	Searcher searcher;
	
	public background(){
		AffectMap pos = new AffectMap("src/positive map tabbed.idx");
		AffectMap neg = new AffectMap("src/negative map tabbed.idx");
		MoodDictionary dict = new MoodDictionary(pos, neg);
		qbs = new QueryBasedSummarizer(dict);
		searcher = new Searcher("src\\Articles\\");
	}
	
	public void search(String input){
		Vector vec = qbs.queryAnalyser(input);
		for(int i=0; i< vec.size(); i++){
			query += vec.get(i) + " ";
		}
		//System.out.println(query);
		searcher.printHits(searcher.search(query, "article"));
	}	
	
	public Vector<String> Headlines(){
		//System.out.println(searcher.resultsHeadlines);
		Vector<String> headlines = searcher.resultsHeadlines;
		return headlines;
	}
	
	public Vector<String> Articles(){
		Vector<String> articles =  searcher.resultsArticles;
		return articles;
	}
	
	
}
