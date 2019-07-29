package org.pelatro.veena.scrabbleApplication;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ScrabbleApp {
 
	public static void main(String[] args)
	{
		ScrabbleApp scrabbleApp = new ScrabbleApp();
	
		AlphabetFactory[] alphabetFactory = scrabbleApp.loadConfigurationFile("/home/veenarevankar/test2scrabbleconfiguration.txt");
		
		if(alphabetFactory == null)
		System.exit(0);
		
		scrabbleApp.startDaemonProcess(alphabetFactory);
		
		scrabbleApp.getUserInput();
		
		System.exit(0);
		
	}

	public void getUserInput() {
		
		String word = "initial";
		
		Scanner sc = new Scanner(System.in);
		
		while(true)
		{
			System.out.println("Enter the word to build and exit to quit\n");
			
			word = sc.next();
			if(word.equalsIgnoreCase("Exit"))
				return;
			if((word != null) && (!word.equals("")) && (word.matches("^[a-zA-Z]*$")))
			{
				

				word = word.toLowerCase();
				WordBuilder wordBuilder = new WordBuilder(word);
				Thread wordBuilderThread = new Thread(wordBuilder,"WordBuilder");
				wordBuilderThread.start();
			
			}
			else
			{
				System.out.println("Invalid input\n");
			}
			
		}
	}
	
	public  void startDaemonProcess(AlphabetFactory[] alphabetFactory)
	{
		if(alphabetFactory == null)
			{
			System.out.println("Alphabet Factory is not producing any characters ");
			return;
			}
		
		AlphabetRepository alpRepository = new AlphabetRepository();
		
		Thread alpRepositoryThread = new Thread(alpRepository,"AlpRepository");
		
		Thread[] alpFactoryThread = new Thread[alphabetFactory.length];
		
		alpRepositoryThread.start();
		
		for(int i=0;i<alphabetFactory.length;i++)
			{
			alpFactoryThread[i] = new Thread(alphabetFactory[i],"AlpFactory");
			alpFactoryThread[i].setDaemon(true);
			alpFactoryThread[i].start();
			}
		
	}
	
	public AlphabetFactory[] loadConfigurationFile(String filePath) {
		
		if(!checkFileExists(filePath))
			{
				System.out.println("Configuration file does not exists\n");
				return null;
					
			}
		
		AlphabetFactory[] alphabetFactoryOld = null;
		
		AlphabetFactory[] alphabetFactoryNew = null;
		
		try {
			
			BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
			String data = bufferedReader.readLine();
			String[] d = data.split(":");
			int numberOfFactory = Integer.parseInt(d[1]);
			alphabetFactoryOld = new AlphabetFactory[numberOfFactory];
			
			for(int count=0;count<numberOfFactory;count++)
			{
				data = bufferedReader.readLine();
				String[] rowContent = data.split(":");
				if(isEmptyAlphabetList(rowContent[1]) || isInvalidRate(rowContent[3]))
				{
					numberOfFactory--;
					count--;
					continue;
				}
				alphabetFactoryOld[count] = new AlphabetFactory(rowContent[1],Integer.parseInt(rowContent[3]));
				
			}
			alphabetFactoryNew = new AlphabetFactory[numberOfFactory];
			for(int count = 0;count<numberOfFactory;count++)
			{
				alphabetFactoryNew[count] = alphabetFactoryOld[count];
			}
		
		} 
		
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return alphabetFactoryNew;
		
	}
	
	public static boolean isEmptyAlphabetList(String alphabets)
	{
		if(alphabets.length() >0)
		return false;
		return true;
	}
	
	public static boolean isInvalidRate(String rate)
	{
		
		if(rate.matches("^[0-9]+$"))
			return false;
		
		return true;
	}
	
	public static boolean checkFileExists(String filepath)
	{
		if(!filepath.matches(".+\\.txt$"))
			return false;
		
		File f = new File(filepath);
		
		return f.exists();
	}
	
}
