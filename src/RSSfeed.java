package week1;

import com.sun.syndication.feed.synd.*;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class RSSfeed {
  public static void main(String args[]) throws Exception {
    String feed = "http://www.irishtimes.com/cmlink/news-1.1319192";
    String feed2 = "http://feeds.guardian.co.uk/theguardian/rss";
	String feed3 = "http://feeds2.feedburner.com/techradar/allnews";
	RSSfeed rss = new RSSfeed();
	rss.getLinks(feed, "Irishtime.txt");
	rss.getLinks(feed2, "Guardian.txt");
	rss.getLinks(feed3, "Techradar.txt");
	rss.fileCleaner("Guardian.txt");
	rss.fileCleaner("Irishtime.txt");
	rss.fileCleaner("Techradar.txt");
  }
  private void getLinks(String feed, String dest) throws IllegalArgumentException, FeedException, IOException{
	    URL feedUrl = new URL(feed);

	    SyndFeedInput input = new SyndFeedInput();
	    SyndFeed sf = input.build(new XmlReader(feedUrl));

	    List entries = sf.getEntries();
	    Iterator<String> it = entries.iterator();
	    while (it.hasNext()) {
	      SyndEntry entry = (SyndEntry)it.next();
	      try
	      {
	          FileWriter fw = new FileWriter(dest,true); //the true will append the new data
	          fw.write(entry.getLink());//appends the string to the file
	          fw.write(" "+entry.getTitle() + "\n");
	          fw.close();
	      }
	      catch(IOException ioe)
	      {
	          System.err.println("IOException: " + ioe.getMessage());
	      }
	    }
  }
  
  private void fileCleaner(String file) throws IOException{
	  Set<String> links = new LinkedHashSet<String>();
	  BufferedReader buff = new BufferedReader(new FileReader(file));
	  String line = "";
	  while((line = buff.readLine()) != null){
		  links.add(line);
	  }
	  buff.close();
	  
	 BufferedWriter write = new BufferedWriter(new FileWriter(file));
	 for(String u : links){
		 System.out.println(u);
		 write.write(u);
		 write.newLine();
	 }
	 write.close();
  }

}
