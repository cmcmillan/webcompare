package com.chris.utils.text;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestRegex
{
    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestRegex.class);

    @Test
    public void testDependencyTreeRegEx() throws Exception
    {
	String regex = "-[^:]*:[^:]*:[^:]*:[^:]*:[^:]*$";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher("This should not be found");
	assertFalse(String.format("Regex found\r\t%1$s\rSearch text:\r%2$s", regex, matcher
		.replaceAll(null)), matcher.find());
	assertTrue(String.format("Regex not found\r\t%1$s\rSearch text:\r%2$s", regex, matcher
		.replaceAll(null)), matcher.find());
	// // Get the input text file
	// TextFileIn txtFile = new TextFileIn("deptree.txt");
	// String myLine;
	//
	// while ((myLine = txtFile.readLine()) != null)
	// {
	// matcher = pattern.matcher(myLine);
	// // while()
	// }
    }
}
