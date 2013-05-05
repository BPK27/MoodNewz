import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;


public class AffectMap {
	private String error = "File not found\n";
	private HashMap<String, Vector<String>> affectWords = new HashMap<String, Vector<String>>();  //hashmap containing file contents
																									// used over several methods
	/* affectMap constructor, takes in filename as string
	 * and processes it to produce the HashMap
	 */
	public AffectMap(String file){
		try {
			Vector<String> assocWords;		//Vector containing the associated words
			StringTokenizer st;				//tokenizer to split the lines
			String nextLine;
			FileReader wordFile = new FileReader(file);
			BufferedReader myReader = new BufferedReader(wordFile);
			
			while((nextLine = myReader.readLine()) != null){
				st = new StringTokenizer(nextLine, "\t");			//creates new tokenizer on every iteration of while loop
				String head = st.nextToken();						//separates head of list from the associated words
				assocWords = new Vector<String>();					//creates new Vector on each iteration
				while(st.hasMoreElements()){
					assocWords.add(st.nextToken());					//inserts words into vector
				}
				affectWords.put(head, assocWords);					//head, and associated words go into hashmap together
			}
			myReader.close();
		}
		catch (IOException e) {
			System.out.println(error + file);
			} 
	}
	
	public HashMap<String, Vector<String>> getAffectWords(){
		return affectWords;
	}
	
	public Vector<String> getAssociationsOfWord(String word){
		word = word.toLowerCase();	//allows for capitalisation of words
		if(affectWords.get(word) == null){
			System.out.println(word + " not found");
			return null;
		}
		else{
			return affectWords.get(word);  //returns associated words
		}
	}
	
	public boolean containsWord(String word){
		word = word.toLowerCase();
		return affectWords.containsKey(word);	//returns true or false
		
	}
}
