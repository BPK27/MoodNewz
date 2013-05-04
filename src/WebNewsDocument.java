import java.net.*;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

import java.io.*;

/* Class uses Jsoup for parsing of article */
public class WebNewsDocument{
	private String address; 
	private String error = "Bad URL entered";
	
	public WebNewsDocument(String s){
		address = s;
	}
			
	/* Returns source of web page in one string
	 * Throws IOExcption if url is malformed, or does
	 * not exist */
	public String getRawText(){
		
		try {
		URL link = new URL(address);
        URLConnection urlConn = link.openConnection();
        
        
        String inputLine = "";
    	StringBuffer webContent = new StringBuffer(inputLine);
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
		while((inputLine = in.readLine()) != null){
			webContent.append(inputLine);
		}
		
			in.close();
		return webContent.toString();
		
		} catch (IOException e) {
			return error;
		}
		
		
	}
	
	public String getURL(){
		return address;
		
	}
	
	public String getWebSite(){
		StringBuffer web = new StringBuffer(address);
		web.append("/");
		web.indexOf("//");  //finds end of "http://"
		web.delete(0, web.indexOf("//")+2);  //deletes http:// from url
		
		return web.substring(0,web.indexOf("/"));  //returns substring without url after website domain
	}
		
	public String getAuthor(){
		StringBuffer text = new StringBuffer(getRawText());
		String author = "";
		if(text.indexOf("<span itemscope itemprop=\"author\"") != -1){
			//Guardian
			text = text.delete(0, text.indexOf("<span itemscope itemprop=\"author\"")); 
			author = text.substring(0, text.indexOf("</div>"));
		}
		else if(text.indexOf("<div class=\"author_name\">") != -1){
			//Irish Times
			text = text.delete(0, text.indexOf("<div class=\"author_name\">")); 
			author = text.substring(0, text.indexOf("</div>"));
		}
		else if(text.indexOf("<a href=\"/author") != -1){
			text = text.delete(0, text.indexOf("<a href=\"/author")); 
			author = text.substring(0, text.indexOf("</a>"));
			author = author.replace("&nbsp;", "");  //get rid of tag after author name
		}
		else{
			return "Author not found";
		}
		
		Document doc = Jsoup.parse(author);
		String clean = Jsoup.clean(doc.body().html(), Whitelist.simpleText());
	    return clean;
		//text = text.delete(0, text.indexOf("<title>")+7);  //19 characters from beginnning of string to title 
		//return author;
	}
	
	public String getHeadlineText(){
		if(getRawText() == error ){
			return error + " " + getURL();   //returns the bad url to user if detected
		}
		else{
			StringBuffer text = new StringBuffer(getRawText());
			String headline = "";
			text = text.delete(0, text.indexOf("<title>")+7);  //19 characters from beginnning of string to title location
			if(text.indexOf("    - ") != -1){
				headline = text.substring(0, text.indexOf("    - "));
			}
			else{
				headline = text.substring(0, text.indexOf("|"));		//finds closing quote of title	
			}
								
			return headline.trim();
		}
		
	}
	
	
	public String getBodyText(){
		if(getRawText() == error ){
			return error+" "+getURL();
		}
		else{
			StringBuffer text = new StringBuffer(getRawText());
			String article = "";
			
			/* Proccessing of Raw Text done by differences in formatting
			 * rather than using specific domains, this allows for
			 * sites with similar layout to be processed too
			 */
			if(text.indexOf("<div id=\"article-body-blocks\">") != -1){
				//Guardian
				text = text.delete(0, text.indexOf("<div id=\"article-body-blocks\">"));   //get rid of everything before string
				text = text.delete(0, text.indexOf("<p>"));								//get rid of everything including the new 1st "<p>"
				article = text.substring(0, text.indexOf("</div>"));						
			}
			else if(text.indexOf("</aside>") != -1){
				text = text.delete(0, text.indexOf("</aside>"));
				article = text.substring(0, text.lastIndexOf("<!-- article_footer_panel -->"));
			}
			else if(text.indexOf("<section>") != -1){
				//Irishtimes
				text = text.delete(0, text.indexOf("<section>"));
				article = text.substring(0, text.lastIndexOf("</section>"));
			}
			
			//Jsoup code
			Document doc = Jsoup.parse(article);
			StringBuilder sb = new StringBuilder();					


			for(Element element : doc.select("p") )
			{
			    sb.append(element.text()).append('\n');
			}

		    return sb.toString().trim();
		}
		
	}
	
	public Vector<String> getSentencesOfText(){
		Vector<String> sentences = new Vector<String>();
		BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.UK);  //iterates over body of text looking for sentence breaks
		
		String article = getBodyText();
		iterator.setText(article);
		
		int start = iterator.first();
		
		for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator.next()) {
			sentences.add(article.substring(start,end));
		}
		  
		return sentences;
		
	}
}


