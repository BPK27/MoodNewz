package Assignment_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class CreateDocuments {
	private String file ="";
	private Vector<String> vector = new Vector();
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
				String[] temp = line.split(" ", 2);
				documents.add(doc = new WebNewsDocument(temp[0]));
				vector.add(temp[1]);
			//	System.out.println("i am a doc hear me roar!! "+ "\n\n"+ temp[1] + "\n\n" + doc.getBodyText() );

			}

			}
			catch (IOException e) { System.out.println("Error while reading in file\n");}
			
		System.out.println(documents.toString()	);
		return documents;
	}
	
	private void Makefiles() throws IOException{
		Vector<WebNewsDocument> docs = this.create();
		int i = 0;
		
		for(WebNewsDocument temp: docs){
			System.out.println("im here "+vector.get(i));
		FileWriter fw = new FileWriter("src\\"+vector.get(i)+".txt", false);
		fw.write(temp.getBodyText());
		fw.close();
		i++;
		}
		
	}
	
	public Vector<WebNewsDocument> getDocuments() throws IOException{
		this.Makefiles();
		return this.create();
		
	}

}
