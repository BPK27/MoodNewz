import java.io.IOException;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		
		WebNewsDocument wnd = new WebNewsDocument("http://www.irishtimes.com/news/social-affairs/religion-and-beliefs/new-pope-urges-return-to-gospel-roots-1.1326236");

		AffectMap negMap = new AffectMap("src/negative map tabbed.idx");
		AffectMap posMap = new AffectMap("src/positive map tabbed.idx");
		MoodDictionary dict = new MoodDictionary(posMap, negMap);
		QueryBasedSummarizer qbs = new QueryBasedSummarizer(dict);
		System.out.println(qbs.getBestMatchingSentence("Pope -church", wnd));

	}

}
