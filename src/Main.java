import java.io.IOException;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		//Indexer index = new Indexer("src\\Articles\\", true);
		
		Searcher searcher = new Searcher("src\\Articles\\");
		
		searcher.printHits(searcher.search("Bush official", "article"));

	}

}
