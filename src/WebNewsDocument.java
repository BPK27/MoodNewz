import java.net.*;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Vector;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
		
	public String getHeadlineText(){
		if(getRawText() == error ){
			return error + " " + getURL();   //returns the bad url to user if detected
		}
		else{
			StringBuffer text = new StringBuffer(getRawText());
			String headline;
			text = text.delete(0, text.indexOf("og:title")+19);  //19 characters from beginnning of string to title location
			headline = text.substring(0, text.indexOf("\""));		//finds closing quote of title
		
			return headline;
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
			if(text.indexOf("<div id=\"article-body-blocks\">") != -1){						//if string is found in text
				//Guardian
				System.out.println(this.getHeadlineText());
				text = text.delete(0, text.indexOf("<div id=\"article-body-blocks\">"));   //get rid of everything before string
				text = text.delete(0, text.indexOf("<p>")+3);								//get rid of everything including the new 1st "<p>"
				article = text.substring(0, text.indexOf("</div>"));						
			}
			else if(text.indexOf("<section>") != -1){
				//Irishtimes
				System.out.println(this.getURL());
				text = text.delete(0, text.indexOf("<section>"));
				article = text.substring(0, text.lastIndexOf("</section>"));
			}
			else{
				System.out.println(this.getURL());
				System.out.println("NOTHING TO SEE HERE");
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


