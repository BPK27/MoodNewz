import java.io.IOException;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;


public class Main {

	/**
	 * @param args
	 * @throws InvalidTokenOffsetsException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InvalidTokenOffsetsException {
		background back = new background();
		back.search("football nigger");
		System.out.println(back.Headlines());
		System.out.println(back.Articles());
		
		
		
		

	}

}
