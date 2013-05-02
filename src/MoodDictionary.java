import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;


public class MoodDictionary {
	AffectMap positive, negative;
	
	//AffectMaps passed in
	public MoodDictionary(AffectMap pos, AffectMap neg){
		positive = pos;
		negative = neg;
	}
	
	//if either positive or negative map contains a word returns true
	public boolean containsWord(String w){
		if(positive.containsWord(w) || negative.containsWord(w)){
			return true;
		}
		else
			return false;
		
	}	
	
	//returns the amount of positive words in relation to total words
	public double getPositivity(String w){
		double posSize, negSize;
		if(positive.containsWord(w)){
			posSize = positive.getAssociationsOfWord(w).size();		//ensures word has positive, if not returns 0
		}
		else
			posSize = 0;
		
		if(negative.containsWord(w)){
			negSize = negative.getAssociationsOfWord(w).size();		//checks for negative associations
		}
		else
			negSize = 0;
		
		return posSize/(posSize+negSize);							//returns between 1-0
	}
	
	//Same as getPositivity, returns negative associations
	public double getNegativity(String w){
		double posSize, negSize;
		if(positive.containsWord(w)){
			posSize = positive.getAssociationsOfWord(w).size();
		}
		else
			posSize = 0;
		
		if(negative.containsWord(w)){
			negSize = negative.getAssociationsOfWord(w).size();
		}
		else
			negSize = 0;
		
		return negSize/(posSize+negSize);	
	}
	
	//method to process a word
	private String wordProcessing(String w){
		int wordLen = w.length();
		
		if(w.endsWith("thes") || w.endsWith("zes") || w.endsWith("ses") || w.endsWith("xes")){
			return w.substring(0, wordLen-2);		//removes the "es" from the word
		}
		else if(w.endsWith("ies") && wordLen > 4){		//some words end with ies, but are too short to have it as suffix
			w = w.substring(0, wordLen-3);				// if long enough to be word, appends a "y"
			w = w.concat("y");
			return w;
		}
		else if(w.endsWith("s")){
			return w.substring(0, wordLen-1);		//removes "s"
		}
		else if(w.endsWith("ing")){
			if(w.substring(0, wordLen-3).matches(".*(.)\\1\\z")){	//removes "ing" then checks for ending in double letter at end of string using lookbehind 1 character
				return w.substring(0, wordLen-4);
			}
			else
				return w.substring(0, wordLen-3);
		}
		else if(w.endsWith("ied")){	//remove "ied", append a "y"
			w = w.substring(0, wordLen-3);
			w = w.concat("y");
			return w;
		}
		else if(w.endsWith("ed")){
			if(w.substring(0, wordLen-2).matches(".*(.)\\1\\z")){	//removes "ed" then checks for ending in double letter
				return w.substring(0, wordLen-3);
			}
			else
				return w.substring(0, wordLen-2);
		}
		else{		//uses the irregular verb list
			AffectMap map = new AffectMap("src/irregular verbs.idx");	//uses affect map as it assigns verbs to a root
			
			//getAffectWords returns hashmap from AffectMap class
			Set<Entry<String, Vector<String>>> verbs = map.getAffectWords().entrySet();  //put the values in a Set to be iterated
			
			for(Entry<String, Vector<String>> v: verbs){
				if(v.getValue().contains(w)){ //if a vector in the set contains word retrun the the key
					return v.getKey().toString();
				}
			}
		}
		return w;
	}
	
	public String getRootForm(String w){
		if(w.length() < 4){
			return w;
		}
		else{
			String word = wordProcessing(w);
			
			if(containsWord(word)){
				return word;
			}
			else if(containsWord(word + "e")){		//some words have an "e" sliced off, so if the word isnt present try add an "e"
				return word + "e";
			}
			else
				return word;			//returns null if the word root is not present
		}	
	}
}
