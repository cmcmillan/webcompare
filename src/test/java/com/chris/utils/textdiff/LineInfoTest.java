package com.chris.utils.textdiff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.chris.utils.textdiff.exceptions.InvalidLineStateException;

public class LineInfoTest
{
    @Test(expected = InvalidLineStateException.class)
    public void testInvalidUpdateState() throws InvalidLineStateException
    {
	LineInfo line = new LineInfo(1, "Hello World!");
	line.updateState(LineState.UniqueMatch, -1);
    }

    @Test
    public void testToString()
    {
	try
	{
	    LineInfo line = new LineInfo(1, "Hello World!", LineState.PrimaryOnly, -1);
	    String expected = "PrimaryOnly\t1\t-1\tHello World!";
	    assertEquals(expected, line.toString());
	}
	catch (InvalidLineStateException e)
	{
	    e.printStackTrace();
	    fail(e.getLocalizedMessage());
	}
    }

    @Test
    public void testUpdateState() throws InvalidLineStateException
    {
	LineInfo line = new LineInfo(1, "Hello World!");
	line.updateState(LineState.SecondaryOnly, -1);

	// Verify the State and Other Line changed
	assertEquals(LineState.SecondaryOnly, line.getState());
	assertEquals(-1, line.getOtherLine());

    }
}
