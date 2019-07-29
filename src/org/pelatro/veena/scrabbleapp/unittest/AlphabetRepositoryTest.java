package org.pelatro.veena.scrabbleapp.unittest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pelatro.veena.scrabbleApplication.AlphabetRepository;

public class AlphabetRepositoryTest {

	AlphabetRepository alphabetRepository = null;
	
	Exception ex = null;
	@Before
	public void setUp() {
		alphabetRepository = new AlphabetRepository();
	}
	
	@Test
	public void testEmptyStringAdditionToRepository() {
		
		try {
		alphabetRepository.addGeneratedAlphabets("");
		}
		catch(Exception e)
		{
			ex = e;
		}
		Assert.assertNull(ex);
		
	}
	
	@Test
	public void testStringAdditionToRepository() {
		
		try {
			
		alphabetRepository.initialiseAlphabetList();
		alphabetRepository.addGeneratedAlphabets("abcd");
		
		}
		catch(Exception e)
		{
			ex = e;
		}
		Assert.assertNull(ex);
		
	}
	@Test
	public void testEmptyStringUpdationToRepository() {
		
		try {
			
		alphabetRepository.initialiseAlphabetList();
		alphabetRepository.updateAlphabetList("");
		
		}
		catch(Exception e)
		{
			ex = e;
		}
		Assert.assertNull(ex);
		
	}
	
	@Test
	public void testStringUpdationToRepository() {
		
		try {
			
		alphabetRepository.initialiseAlphabetList();
		alphabetRepository.updateAlphabetList("abcd");
		
		}
		catch(Exception e)
		{
			ex = e;
		}
		Assert.assertNull(ex);
		
	}
	
	
	
}
