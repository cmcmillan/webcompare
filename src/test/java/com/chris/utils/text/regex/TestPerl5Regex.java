package com.chris.utils.text.regex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Matcher;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestPerl5Regex
{
    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestPerl5Regex.class);

    @Test
    public void parseMavenCoordinates() throws MalformedPatternException
    {
	String regex = "(" + RegexUtils.A_START_GARBAGE + ")*(" + RegexUtils.A_MVN_COORD + ")";
	String inputString = "[INFO] +- commons-pool-C:commons-pool-A:jar:1.5.2:compile";
	String[][] expectedResults =
		new String[][] {
			{ "[INFO] +- commons-pool-C:", "[INFO] +", "+", "- commons-pool-C:",
				"- commons-pool-C" },
			{ "commons-pool-A:", null, null, "commons-pool-A:", "commons-pool-A" },
			{ "jar:", null, null, "jar:", "jar" },
			{ "1.5.2:", null, null, "1.5.2:", "1.5.2" },
			{ "compile", null, null, "compile", "compile" } };

	RegexUtils.debugRegex(regex, inputString, false);
	// Verify that the provide regex is true
	RegexUtils.assertRegexTrue(regex, inputString);

	// Initialize the pattern
	Pattern pattern = RegexUtils.initPattern(regex);
	PatternMatcherInput input = new PatternMatcherInput(inputString);

	PatternMatcher matcher = new Perl5Matcher();
	MatchResult result;

	LOGGER.debug("Verify parsing.");
	int match = 0;
	while (matcher.contains(input, pattern))
	{
	    result = matcher.getMatch();
	    for (int group = 0; group < result.groups(); group++)
	    {
		// Write out the information
		LOGGER.debug("Match({}) Group({}), Expected: \"{}\", Actual: \"{}\"", new Object[] {
			match, group, expectedResults[match][group], result.group(group) });
		// Verify they are equal
		assertEquals(String.format(
		    "Match(%1$s) Group(%2$s), Expected: \"%3$s\", Actual: \"%4$s\"", match, group,
		    expectedResults[match][group], result.group(group)),
		    expectedResults[match][group], result.group(group));
	    }
	    match++;
	}
	LOGGER
		.debug("Expected Total Matches: {}, Total Matches: {}", expectedResults.length,
		    match);
	assertEquals(String.format("Expected: \"%1$s\", Actual: \"%2$s\"", expectedResults.length,
	    match), expectedResults.length, match);
    }

    @Ignore
    public void simpleRegex() throws MalformedPatternException
    {
	// NOT A BLANK SPACE
	String regex = "\\S+";
	String inputString = "[INFO] +- commons-pool-C:commons-pool-A:jar:1.5.2:compile";

	// Initialize the pattern
	Pattern pattern = RegexUtils.initPattern(regex);
	PatternMatcherInput input = new PatternMatcherInput(inputString);

	PatternMatcher matcher = new Perl5Matcher();
	MatchResult result;

	int match = 0;
	while (matcher.contains(input, pattern))
	{
	    result = matcher.getMatch();
	    match++;
	    LOGGER.debug("Match ({}): \"{}\" : Groups({})", new Object[] { match, result.group(0),
		    result.groups() });
	}
	LOGGER.debug("Total Matches: {}", match);
	assertTrue("No match found in simpleRegex.", match > 0);
    }

    /**
     * Initialize an invalid pattern. {@link MalformedPatternException}
     * expected.
     * 
     * @throws MalformedPatternException
     *             Regular expression pattern is not a valid pattern.
     */
    @Ignore
    @Test(expected = MalformedPatternException.class)
    public void initInvalidPattern() throws MalformedPatternException
    {
	// Get the Pattern for the provided regular expression.

	// 0-5 letters or numbers followed by optional white space
	String regex = "[a-zA-Z0-9]{,5)(\\s*)";
	try
	{
	    // 0-5 letters or numbers followed by optional white space
	    RegexUtils.initPattern(regex);
	}
	catch (MalformedPatternException e)
	{
	    String errMsg =
		    String.format("Invalid regular expression pattern, %1$s, %2$s", regex, e
			    .getLocalizedMessage());
	    LOGGER.error(errMsg, e);
	    throw e;
	}
	fail("Invalid pattern initialized.");
    }

}
