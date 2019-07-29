
package org.pelatro.veena.scrabbleApplication;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public class AlphabetFactory implements Runnable{
	
	private String alphabetList="";

	private int rate;
	
	public AlphabetFactory() {
		
	}
	
	public AlphabetFactory(String alphabetList,int rate)
	{
		this.alphabetList = alphabetList;
		this.rate = rate;
	}
	
	public  void run()
	{
		try
		{
			int z = 0;
			Character ch;
			while(true)
			{
				ch = alphabetList.charAt(z);
				z = (z+1) % alphabetList.length();
			    
				Socket soc = new Socket("LOCALHOST",3007);
				
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(soc.getOutputStream()),true);
				
				String msg = "~"+ch;
				
				pw.write(msg);
				
				pw.close();
				
				Thread.sleep(this.rate);
				
				soc.close();
				
				
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/*public String getAlphabetList() {
		return alphabetList;
	}

	public void setAlphabetList(String alphabetList) {
		this.alphabetList = alphabetList;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}*/

	
	
	
	
	
}
