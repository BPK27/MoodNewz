import java.io.IOException;
import java.util.Vector;




public class Indexer {

    public Indexer(String indexPath, boolean create) throws IOException
	{
    	MyLuceneIndexer indexer = new MyLuceneIndexer(indexPath, create); //create index from scratch
    	
    	CreateDocuments docs = new CreateDocuments("src\\Guardian.txt");
    	Vector<WebNewsDocument> documents = docs.getDocuments();
    	
    	for(WebNewsDocument doc : documents){
    		indexer.writeIndex(new MyDocument(doc.getHeadlineText(),doc.getDate(), doc.getAuthor(), doc.getBodyText()).getLuceneDoc());
    	}
	    
		indexer.closeIndex();
    	
    	
	}
}
