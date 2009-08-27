package com.chris.utils.textdiff;

import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.chris.utils.textdiff.exceptions.InvalidLineStateException;

public class TextDiffTest
{
    private String[] primaryInput;
    private String[] secondaryInput;

    @Before
    public void setUp() throws Exception
    {
	primaryInput =
		new String[] { "// Swap meet", "for each byte", "i = (i + 1) % 256;",
			"j = (j + S[i]) % 256;", "temp = S[i];", "S[i] = S[j]", "S[j] = temp;" };
	secondaryInput =
		new String[] { "for each byte", "{", "i = (i + 1) % 256;", "j = (j + S[i]) % 256;",
			"temp = S[i];", "S[i] = S[j];", "S[j] = temp;", "}" };
    }

    @Test
    public void testCompare()
    {
	try
	{
	    TextDiff.compare(primaryInput, secondaryInput);
	}
	catch (InvalidLineStateException e)
	{
	    e.printStackTrace();
	    fail(e.getLocalizedMessage());
	}
    }

    @Test
    public void testPrintList()
    {
	TextDiff.printList(Arrays.asList(primaryInput));
    }

}
