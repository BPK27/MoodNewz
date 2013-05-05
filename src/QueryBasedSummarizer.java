
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.Vector;


public class QueryBasedSummarizer {
	MoodDictionary moodDictionary;
	private boolean posAndneg = false;  //boolean to see if query has a "?"
	
	public QueryBasedSummarizer(MoodDictionary md) {
		moodDictionary = md;
	}
	
	//method to expand the query to use associated words
	public Vector<String> queryAnalyser(String query){
		StringTokenizer st = new StringTokenizer(query);
		Vector<String> allQueryWords = new Vector<String>();
		Vector<String> queryWords = new Vector<String>();
		
		while(st.hasMoreTokens()){
			queryWords.add(st.nextToken());
		}
		
		Iterator<String> it = queryWords.iterator();
		
		//while there are remaining words from the initial query it gets the associated words and add to new Vector
		while(it.hasNext()){
			String w = it.next();
			if(w.startsWith("+")){
				allQueryWords.add(w.substring(1));  //removes the indicator from start of query word
				allQueryWords.addAll(moodDictionary.positive.getAssociationsOfWord(w.substring(1))); 
			}
			else if(w.startsWith("-")){
				allQueryWords.add(w.substring(1));
				allQueryWords.addAll(moodDictionary.negative.getAssociationsOfWord(w.substring(1)));
			}
			else if(w.startsWith("?")){
				allQueryWords.add(w.substring(1));
				allQueryWords.addAll(moodDictionary.positive.getAssociationsOfWord(w.substring(1)));
				allQueryWords.addAll(moodDictionary.negative.getAssociationsOfWord(w.substring(1)));
				posAndneg = true;  //question mark detected, set to true
			}
			else{
				allQueryWords.add(w);
			}
		}
		
		return allQueryWords;
		
	}
	
	//gets all sentences which match the query
	public Vector<String> getMatchingSentences(String query, WebNewsDocument doc){
		
		Vector<String> allWords = queryAnalyser(query);  //expanded query
		Vector<String> sent = doc.getSentencesOfText();
		Iterator<String> it = sent.iterator();
		Vector<String> resultSentences = new Vector<String>();
		
		//while there  are sentences remaining
		while(it.hasNext()){
			String sentence = it.next();
			StringTokenizer st = new StringTokenizer(sentence);  //splits up sentence
			Vector<String> sentenceWords = new Vector<String>();
			//while words still in sentence
			while(st.hasMoreTokens()){
				String word = st.nextToken();
				word = word.toLowerCase();
				
				//adds the word and the root of the word to the vector for searching
				sentenceWords.add(word);
				sentenceWords.add(moodDictionary.getRootForm(word));
			}
		
			Iterator<String> words = allWords.iterator();
			//while query has words waiting
			while(words.hasNext()){
				String queryWord = words.next();
				queryWord = queryWord.toLowerCase();
				
				//if the queryWord is found in the sentence the sentence is added to the results
				if(sentenceWords.contains(queryWord)){
					if(!resultSentences.contains(sentence)){
						resultSentences.add(sentence);
					}	
				}
				else if(sentenceWords.contains(moodDictionary.getRootForm(queryWord))){
					if(!resultSentences.contains(sentence)){
						resultSentences.add(sentence);
					}
				}
			}
		}
		
		
		return resultSentences;
		
	}
	
	
	//Not fully working for queries with 2 or more instances of special query words Eg. "Pope -church -money"
	//Otherwise, returns the score received by a sentence
	public int getMatchScore(String query, String sentence){
		int score = 0;
		int i = 0;
		StringTokenizer st = new StringTokenizer(query);
		Vector<String> allWords = queryAnalyser(query);
		Iterator<String> words = allWords.iterator();
		
		while(words.hasNext()){
			String word = words.next();
			StringTokenizer sentenceWords = new StringTokenizer(sentence);
			
			//reads the first "x" amount of queries IE the original queries
			if(i < st.countTokens()){
				while(sentenceWords.hasMoreTokens()){
					String sentenceWord = sentenceWords.nextToken();
					//query word equal to a sentence word
					if(sentenceWord.toLowerCase().contains(word.toLowerCase())){
						score += 10;
					}
				}
				i++;
			}
			else{
				while(sentenceWords.hasMoreTokens()){
					String sentenceWord = sentenceWords.nextToken();
						if(sentenceWord.toLowerCase().contains(word.toLowerCase())){
							//if query is "?" adds 1 to score, otherwise adds 3
							if(posAndneg){
								score+= 1;
							}
							else{
								score += 3;
							}
							
						}
					}	
				}	
		}
		return score;
				
	}
	
	
	public String getBestMatchingSentence(String query,WebNewsDocument doc){
		Vector<String> sentences = getMatchingSentences(query, doc);
		String bestMatch = null;
		int prevScore = 0;
		for(String s: sentences){
			int score = getMatchScore(query, s);
			//if the score of the current sentence is better than previous best the switch, prevScore now the new high score
			if(score > prevScore){
				prevScore = score;
				bestMatch = s;
			}
		}
		return bestMatch;
				
	}

}
