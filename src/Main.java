import java.io.IOException;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		
		CreateDocuments docs = new CreateDocuments("src/Guardian.txt");
		docs.getDocuments();

	}

}
