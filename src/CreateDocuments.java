import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class CreateDocuments {
	private String file ="";
	private Vector<String> vector = new Vector<String>();
	
	public CreateDocuments(String file){
		this.file = file;

	}
	
	private Vector<WebNewsDocument> create(){
		
		Vector<WebNewsDocument> documents = new Vector<WebNewsDocument>();
		
		try{
			BufferedReader buffer = new BufferedReader(new  FileReader(file));
			String line = "";
			while((line = buffer.readLine())!= null){
				String[] temp = line.split(" ", 2);
				documents.add(new WebNewsDocument(temp[0]));
				vector.add(temp[1]);
			}

		}
			catch (IOException e) { System.out.println("Error while reading in file\n");}
			
		return documents;
	}
	
	public Vector<WebNewsDocument> getDocuments() throws IOException{
		
		return this.create();
		
	}

}
