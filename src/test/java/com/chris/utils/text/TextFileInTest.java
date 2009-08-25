/**
 * 
 */
package com.chris.utils.text;

import com.chris.utils.Convert;

import junit.framework.TestCase;

/**
 * @author cjmcmill
 * 
 */
public class TextFileInTest extends TestCase
{
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
	    fail(e.getMessage());
	}
    }
}
