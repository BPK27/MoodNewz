import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;


public class MyLuceneIndexer {

    StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_42);
    IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_42, analyzer);
    IndexWriter writer;
    Directory dir;
    
    public MyLuceneIndexer(String indexPath, boolean create)
	{
    	try{
	    	dir = FSDirectory.open(new File(indexPath));
	    	if (create) {
	    		// Create a new index in the directory, removing any previously indexed documents:
	    		iwc.setOpenMode(OpenMode.CREATE);
	    	} else {
	    		// Add new documents to an existing index:
	    		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
	    	}
	    	writer = new IndexWriter(dir, iwc);
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
	}
		
    //write new document into index
    public void writeIndex (Document doc)
	{
		if(doc != null)
		{
			addNewDoc(doc);
			
		}	
	}
    
	private void addNewDoc(Document doc) 
  	{
	  try {
		  writer.addDocument(doc);
	  }
	  catch (Exception e){}
	}

	//close index
	public void closeIndex() 
	{
		try 
		{
			writer.close();

		}
		catch (Exception e){e.printStackTrace();}
	}
	
}
