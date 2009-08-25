package com.chris.utils;

import java.util.logging.Logger;

import junit.framework.TestCase;

public class ConvertTest extends TestCase
{
    private final Logger LOGGER;

    public ConvertTest(String name)
    {
	super(name);
	LOGGER = Logger.getLogger(getName());
    }
        
    public void testNumeralName()
    {
	// Setup two arrays with corresponding values
	int[] numerals = new int[] { 5, 10, 13, 23, 156 };
	String[] expectedNames =
		new String[] { "five", "ten", "thirteen", "twenty three", "one hundred fifty six" };
		
	for(int i = 0;i<numerals.length;i++)
	{
	    // Get the name of the numeral
	    String numeralName = Convert.getNumeralName(numerals[i]);
	    // Print out the numeral name
	    LOGGER.info(expectedNames[i]+" , "+numeralName);
	    // Verify they are the same
	    assertEquals(expectedNames[i], numeralName);
	}
    }
}
