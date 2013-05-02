package Assignment_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class CreateDocuments {
	private String file ="";
	public CreateDocuments(String file){
		this.file = file;
	}
	
	private Vector<WebNewsDocument> create(){
		
		Vector<WebNewsDocument> documents = new Vector();
		WebNewsDocument doc;
		
		try{
			BufferedReader buffer = new BufferedReader(new  FileReader(file));
			String line = "";
			while((line = buffer.readLine())!= null){
				String[] temp = line.split(" ");
				documents.add(doc = new WebNewsDocument(temp[0]));
				System.out.println("i am a doc hear me roar!! "+ "\n" + doc.getBodyText() );

			}

			}
			catch (IOException e) { System.out.println("Error while reading in file\n");}
			
		System.out.println(documents.toString()	);
		return documents;
	}
	
	public Vector<WebNewsDocument> getDocuments(){
		return this.create();
	}

}
