package Assignment_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import sun.util.locale.StringTokenIterator;

public class DavidAffectMap {	// read the file in and use StringTokenizer  to get key and word vector line by line then add to hashmap

HashMap<String, Vector<String>> affectmap = new HashMap<String, Vector<String>>();
	
public DavidAffectMap(String filename) throws IOException{
	String line; 
	Vector<String> words;
	try{
	BufferedReader buffer = new BufferedReader(new  FileReader(filename));

	while((line = buffer.readLine())!= null){
			StringTokenizer st = new StringTokenizer(line, "\t");
			String key = st.nextToken();	//stores key as first token
		//	System.out.println(key);
			words = new Vector<String>();	//reinitialize words after each line
			while(st.hasMoreElements()){
			words.add(st.nextToken());		//adds words associated with key to the vector
			}
			affectmap.put(key, words);		//adds key and vector to hashmap

	}

	}
	catch (IOException e) { System.out.println("Error while reading in file\n");}
	
	
}

public boolean containsWord(String word){
	
	return affectmap.containsKey(word);
}

public Vector getAssociationsofWord(String word){
	Vector empty = new Vector();

	if(affectmap.containsKey(word)){
	return affectmap.get(word);
	}
	else{
	System.out.println("Key not found in hashTable");
	return empty;
	}
}

public HashMap<String, Vector<String>> getAffectmap(){
	return affectmap;
}


	
}
