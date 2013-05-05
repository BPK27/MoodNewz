import java.io.IOException;
import java.util.Vector;




public class Indexer {

    public Indexer(String indexPath, boolean create) throws IOException
	{
    	MyLuceneIndexer indexer = new MyLuceneIndexer(indexPath, create); //create index from scratch
    	
    	CreateDocuments guardian = new CreateDocuments("src\\Guardian.txt");
    	CreateDocuments irishtimes = new CreateDocuments("src\\irishtime.txt");
    	CreateDocuments techradar = new CreateDocuments("src\\Techradar.txt");
    	
    	
    	
    	Vector<WebNewsDocument> documents = guardian.getDocuments();
    	documents.addAll(irishtimes.getDocuments());
    	documents.addAll(techradar.getDocuments());
    	
    	for(WebNewsDocument doc : documents){
    		indexer.writeIndex(new MyDocument(doc.getHeadlineText(),doc.getDate(), doc.getAuthor(), doc.getBodyText()).getLuceneDoc());
    	}
	    
		indexer.closeIndex();
    	
    	
	}
}
