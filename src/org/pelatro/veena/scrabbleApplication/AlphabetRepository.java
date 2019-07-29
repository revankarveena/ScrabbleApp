package org.pelatro.veena.scrabbleApplication;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.net.ServerSocket;
import java.net.Socket;



import java.util.concurrent.ConcurrentHashMap;

public class AlphabetRepository implements Runnable {


	private ConcurrentHashMap<Character,Integer> alphabetsList = new  ConcurrentHashMap<>();
	
	public void run() {
		
		try
		{
		AlphabetRepository al  = new AlphabetRepository();
		
		al.initialiseAlphabetList();
		
		ServerSocket ss =  new ServerSocket(3007);
		
			while(true)
			{
				Socket clientSoc = null;
				clientSoc = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(clientSoc.getInputStream()));
				String msg = br.readLine();
				Thread clientHandler = new ClientHandler(al,msg);
				clientHandler.start();
				clientSoc.close();
		
			}
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		
	}
	
	
	public void initialiseAlphabetList() {
		Integer count = 0;
		
		for(int i=97;i<=122;i++)
			{
			alphabetsList.put(Character.valueOf((char)i),count);
			}
		
	}
	
	/*public void displayAlphabetList() {
		
		Set<Character> keys = alphabetsList.keySet();
        for(Character key: keys){
          System.out.println("Value of "+key+" is: "+alphabetsList.get(key));
		}
	}*/
	
	synchronized public void addGeneratedAlphabets(String alphabets)
	{
		
		for(Character c:alphabets.toCharArray())
		{
			alphabetsList.put(c,alphabetsList.get(c)+1);
			
		}
		
	}

	synchronized public void updateAlphabetList(String word)
	{
		for(Character c:word.toCharArray())
		{
			alphabetsList.put(c,alphabetsList.get(c)+1);
		}
		
	}
	
	synchronized public boolean checkWordAvailability(String word)
	{
		
		boolean result = true;
		
		
		String obtainedWord = "";
		try {
		
			for(Character c:word.toCharArray())
			{
				Character ch = c;
				if(alphabetsList.get(ch) == 0)
				{
					notifyAll();
					
					wait(1000);
					
					
					if(alphabetsList.get(ch) == 0)
						{
						
						updateAlphabetList(obtainedWord);
						return false;
						
						}
					
				}
				alphabetsList.put(c,alphabetsList.get(c)-1);
				obtainedWord += c.toString();
					
				
			}
			
				return true;
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	

}
