package org.pelatro.veena.scrabbleApplication;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class ClientHandler extends Thread {
 
	private String msg;
	private AlphabetRepository alpRepository = null;
	
	public ClientHandler(AlphabetRepository alpRepository,String msg)
	{
		
		this.alpRepository = alpRepository;
		this.msg = msg;
		
	}
	
	public void run()
	{
			try
			{ 
				
				//For the Alphabet Factory connections
				if(msg==null)
					return;
				if(msg.startsWith("~"))
					{
					msg = msg.substring(1);
					
					connectToAlphabetFactory(msg);
					
					}
				//For the WordBuilder connections
				if(msg.startsWith("$"))
				{
					
					
					msg = msg.substring(1);
					
					connectToWordBuilder(msg);
					
				}
					
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		
	
		public void connectToAlphabetFactory(String msg) {
				
				try
				{
				if(msg == null)
					return;
				alpRepository.addGeneratedAlphabets(msg);
				
				}
				catch (Exception e) {
					
					e.printStackTrace();
				}
		}
	
	
	
		public void connectToWordBuilder(String msg) {
			
			try
			{
			if(msg == null)
				return;
			Boolean result = alpRepository.checkWordAvailability(msg);
			if(result)
			System.out.println("Word build success !!!  "+msg+" word built");
			else
			System.out.println("Word build failure !!!  "+msg+" word did not built");
			
			String status="";
			
			if(result)
				status = "Success";
			else
				status = "Failure";
			
			updateInDatabase(msg,status);
			
			}
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		synchronized public void updateInDatabase(String word,String status)
		{
			Logger.getRootLogger().setLevel(Level.OFF);
	    	SessionFactory sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.getTransaction().begin();
			
			WordCollection wordCollection = new WordCollection();
			Date date= new Date();
			long time = date. getTime();
			
			Timestamp ts = new Timestamp(time);
			
			wordCollection.setStatus(status);
			wordCollection.setTimeStamp(ts);
			wordCollection.setWord(word);
			session.save(wordCollection);
			
			session.getTransaction().commit();
			session.close();
		}
	
}
