import java.io.IOException;
import java.util.Vector;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;


public class background {
	String query = "";
	QueryBasedSummarizer qbs;
	Searcher searcher;
	Vector<String> boldedArticles = new Vector<String>();
	Vector<String> boldedFragments = new Vector<String>();
	
	public background(){
		AffectMap pos = new AffectMap("src/positive map tabbed.idx");
		AffectMap neg = new AffectMap("src/negative map tabbed.idx");
		MoodDictionary dict = new MoodDictionary(pos, neg);
		qbs = new QueryBasedSummarizer(dict);
		searcher = new Searcher("src\\Articles\\");
	}
	
	public void search(String input) throws IOException, InvalidTokenOffsetsException{
		Vector vec = qbs.queryAnalyser(input);
		for(int i=0; i< vec.size(); i++){
			query += vec.get(i) + " ";
		}
		//System.out.println(query)
		searcher.printHits(searcher.search(query, "article"));
		searcher.Highlighter(query, "article");
		
		for(String[] st : searcher.resultsBoldedArticles){
			String article = "";
			//String s = "";
			for(String s : st){
				article += s;
			}
			boldedArticles.add(article);
		}
		
		for(String[] st : searcher.fragments){
			String article = "";
			//String s = "";
			for(String s : st){
				article += s;
			}
			boldedFragments.add(article);
		}
	}	
	
	public Vector<String> Headlines(){
		Vector<String> headlines = searcher.resultsHeadlines;
		return headlines;
	}
	
	public Vector<String> Articles(){
		
		return boldedArticles;
	}
	
	public Vector<String> Fragments(){
		
		return boldedFragments;
	}
	
	public Vector<String> Dates(){
		Vector<String> dates = searcher.resultsDates;
		return dates;
	}
	
	
}
