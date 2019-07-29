package org.pelatro.veena.scrabbleApplication;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class WordBuilder implements Runnable {
	
	private String word;

	public WordBuilder() {
		
	}
	
	public WordBuilder(String word)
	{
		this.word = word;
	}
	
    public void run() {
		
		     try {
		    	 	Socket soc = new Socket("LOCALHOST",3007);
		    	 	
					try {
					
					PrintWriter pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()),true);
					
					String msg = "$"+this.word;
					pw.write(msg);
					pw.close();
					
					
					}
					
					catch(Exception e)
					{
						soc.close();
					}
		 		
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
	}
    
}
