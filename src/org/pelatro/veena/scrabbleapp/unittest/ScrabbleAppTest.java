package org.pelatro.veena.scrabbleapp.unittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.pelatro.veena.scrabbleApplication.AlphabetFactory;
import org.pelatro.veena.scrabbleApplication.ScrabbleApp;

import junit.framework.Assert;

public class ScrabbleAppTest {

	ScrabbleApp scrabbleApp = null;
	Exception exception = null;
	
	@Before
	public void setUp() {
		scrabbleApp = new ScrabbleApp();
	}

	//@Order(1)
	@Test
	public void testNullAlphabetObjects() {
		try {
			scrabbleApp.startDaemonProcess(null);
		}
		catch(Exception e)
		{
			exception = e;
		}
		Assert.assertEquals(null,exception);

	}
	
	//@Order(2)
	@Test
	public void testInvalidFilePath() {
		Assert.assertNull(scrabbleApp.loadConfigurationFile("/home/$"));
	}
	
	//@Order(3)
	@Test
	public void testInvalidFileExtension() {
		Assert.assertTrue(scrabbleApp.checkFileExists("/home/veena")==false);
	}
	
	//@Order(4)
	@Test
	public void testInvalidConfigurationFileContent() {
		String testfilepath = "/home/veenarevankar/test1scrabbleconfiguration.txt";
		AlphabetFactory[] alphabetFactory = scrabbleApp.loadConfigurationFile(testfilepath);
		
		Assert.assertTrue(alphabetFactory.length==1);
	}
	
	
	//@Order(5)
	@Test
	public void testValid400AlphabetObjects() {
		//AlphabetFactory[] alphabetFactory = new AlphabetFactory[4];
		try {
			String testfilepath = "/home/veenarevankar/test2scrabbleconfiguration.txt";
			AlphabetFactory[] alphabetFactory = scrabbleApp.loadConfigurationFile(testfilepath);
			
			scrabbleApp.startDaemonProcess(alphabetFactory);
		}
		catch(Exception e)
		{
			exception = e;
		}
		Assert.assertEquals(null,exception);
	}
	
	
	
	
	//@Order(6)
	@Test
	public void testInvalidWord() {
		
		try {
			scrabbleApp.getUserInput();
			
		}
		catch(Exception ex)
		{
			exception = ex;
		}
		Assert.assertEquals(null, exception);
	}
	
	
	
}
