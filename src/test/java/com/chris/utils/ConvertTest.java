package com.chris.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertTest.class);

    @Test
    public void testNumeralName()
    {
	// Setup two arrays with corresponding values
	int[] numerals = new int[] { 5, 10, 13, 23, 156 };
	String[] expectedNames =
		new String[] { "five", "ten", "thirteen", "twenty three", "one hundred fifty six" };

	for (int i = 0; i < numerals.length; i++)
	{
	    // Get the name of the numeral
	    String numeralName = Convert.getNumeralName(numerals[i]);
	    // Print out the numeral name
	    LOGGER.info(expectedNames[i] + " , " + numeralName);
	    // Verify they are the same
	    assertEquals(expectedNames[i], numeralName);
	}
    }
}
