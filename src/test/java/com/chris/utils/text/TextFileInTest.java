/**
 * 
 */
package com.chris.utils.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.chris.utils.Convert;

/**
 * @author cjmcmill
 * 
 */
public class TextFileInTest
{
    @Test
    public void testReadLine()
    {
	String line;
	try
	{
	    TextFileIn file = new TextFileIn("TextFileInTest.txt");
	    int lineCount = 0;
	    while ((line = file.readLine()) != null)
	    {
		lineCount++;
		String expectedNumeralName = Convert.getNumeralName(lineCount);
		assertEquals(lineCount + " " + expectedNumeralName, line);
	    }
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    fail(e.getLocalizedMessage());
	}
    }
}
