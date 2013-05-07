package src;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;
import javax.swing.text.Highlighter;

import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;

public class SearchBox extends JPanel implements ActionListener{
    protected JTextField textField;
    protected static JTextPane textArea;
    
    
    public SearchBox() {
        super(new GridBagLayout());
        textField = new JTextField(20);
        textField.addActionListener(this);
 
        textArea = new JTextPane();
        textArea.setSize(500, 500);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        GridBagConstraints c = new GridBagConstraints();  
        c.gridwidth = GridBagConstraints.REMAINDER;
 
        c.fill = GridBagConstraints.HORIZONTAL;

        add(textField, c);
 
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 100.0;
        c.weighty = 100.0;
        c.gridheight = 50;
        c.gridwidth = 50;
        add(scrollPane, c);
        

    }
	
    public void actionPerformed(ActionEvent evt) {

        String text = textField.getText();
        background back = new background();
        textArea.setText("");

        try {
			back.search(text);
		} catch (IOException | InvalidTokenOffsetsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        textArea.setText("");
        String article = "<html>";
        for(int i = 0; i<back.Headlines().size();i++){
        	article += "<h1>"+ back.Headlines().get(i)+"</h1>" + "\n"+ "<h2>"+back.Dates().get(i)+"</h2>"+"\n"+ "<p>" +back.Articles().get(i) +"</p>" + "\n\n" + "</p>"; 
        	
        }
        article += "</html>";
    	textArea.setContentType("text/html");
        textArea.setText(article);
        textField.selectAll();     
    }
 

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Search Box");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
 
        //Add contents to the window.
        
        frame.add(new SearchBox());
 
        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }
	 
	    public void run(){
	    	this.createAndShowGUI();
	    	
	    }
	

}
