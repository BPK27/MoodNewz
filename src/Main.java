import java.io.IOException;
import java.util.Vector;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		String query = "";
		//WebNewsDocument doc = new WebNewsDocument("http://www.techradar.com/us/news/gaming/consoles/ps4-release-date-news-and-features-937822");
		//new Indexer("src\\Articles\\", true);
//		System.out.println(doc.getHeadlineText());
//		System.out.println(doc.getBodyText());
		AffectMap pos = new AffectMap("src/positive map tabbed.idx");
		AffectMap neg = new AffectMap("src/negative map tabbed.idx");
		MoodDictionary dict = new MoodDictionary(pos, neg);
		QueryBasedSummarizer qbs = new QueryBasedSummarizer(dict);
		Searcher searcher = new Searcher("src\\Articles\\");
		Vector vec = qbs.queryAnalyser("-traffic");
		for(int i=0; i< vec.size(); i++){
			query += vec.get(i) + " ";

		}
		System.out.println(query);
		searcher.printHits(searcher.search(query, "article"));

	}

}
