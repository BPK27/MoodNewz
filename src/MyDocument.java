import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;


public class MyDocument {

	private String _headline;
	private String _date;
	private String _author;
	private String _article;
	private Document doc;
	
	public MyDocument(){}
	
	public MyDocument(String h, String d, String a, String art){
		this._headline = h;
		this._date = d;
		this._author = a;
		this._article = art;
	}
	
	public void setheadline(String t){this._headline = t;}
	public void setDate(String d){this._date = d;}
	public void setAuthor(String a){this._author = a;}
	public void setArticle(String art){this._article = art;}
	
	public String getheadline(){return this._headline;}
	public String getDate(){return this._date;}
	public String getAuthor(){return this._author;}
	
	public Document getLuceneDoc(){
		
		doc = new Document();
		
		doc.add(new TextField("headline", _headline, Field.Store.YES));

	    // use a string field for isbn because we don't want it tokenized
	    doc.add(new StringField("date", _date, Field.Store.YES));
	    
	    doc.add(new StringField("author", _author, Field.Store.YES));
	    
	    doc.add(new TextField("article", _article, Field.Store.YES));
	    
	    return doc;
	}
}
