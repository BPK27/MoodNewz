package assignments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class AffectMap {			//takes in a file name a produces a HashMap with  head of line as key and tail as a Vector value 
	private HashMap<String, Vector<String>> Hashed=new HashMap<String, Vector<String>>();
	
	AffectMap(String filename) throws IOException{		//constructor takes in file name
		InputStream input = getClass().getResourceAsStream(filename);//takes in file as a stream , with filereader paths must be absolute in this way paths can be relative
		BufferedReader in=new BufferedReader(new InputStreamReader(input)); //creates a bufferedReader of the input stream
		while (in.ready()){// while stream is not depleted 
			addToHash(in.readLine());//reads line into addToHash
			
		}
		in.close();//closes stream
	}
	
	private void addToHash(String line){	//adds a member to the hash table
		StringTokenizer tokes=new StringTokenizer(line,"\t");// parses line on the token tab
		Vector<String> associated=new Vector<String>();//creates a new vector
		String key=tokes.nextToken();					//takes first  token as key  this is the main word
		while(tokes.hasMoreTokens()){					//adds all associated words to a vector
			associated.add(tokes.nextToken());
		}
		Hashed.put(key, associated);					// creates a hashmap where key is the key and associated is the value
	}
	
	public boolean containsWord(String word){			// boolean method to see if  word is a key in out hashmap
		if(Hashed.containsKey(word))
			return true;
		else
			return false;
		
	}
	
	public Vector<String> getAssosciationsOfWord(String word){	// returns the vector<String> of associated words  given a key 
		if(containsWord(word)){
			return (Vector<String>)Hashed.get(word);
		}
		else
			return null;
	}

}
