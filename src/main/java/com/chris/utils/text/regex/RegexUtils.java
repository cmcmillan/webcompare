package com.chris.utils.text.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RegexUtils
{
    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RegexUtils.class);

    /**
     * regex snippet for a MAVEN COORDINATE
     */
    public static final String A_MVN_COORD = "([^: ][^:]*)(?::?)";
    /**
     * regex fragment to look for a white space or :
     */
    public static final String A_SPACE_COLON = "[:\\s]";
    /**
     * regex fragment to look for a not a white space or :
     */
    public static final String A_NO_COLON_SPACE = "[^:\\s]";
    /**
     * regex fragment to look for a phrase at the start of a line
     */
    public static final String A_START_GARBAGE =
	    "^\\S+" + A_SPACE_COLON + "*(" + A_NO_COLON_SPACE + "*)";
    /**
     * regex fragment to look for some spaces
     */
    public static final String A_SPACES = "\\s++";
    /**
     * regex fragment to look for a phrase at the start of a line
     */
    public static final String A_START_PHRASE = "^.*\\s";

    private static final PatternCompiler compiler = new Perl5Compiler();;
    private static final PatternMatcher matcher = new Perl5Matcher();

    /**
     * @param pattern
     * @param input
     */
    public static final void outputMatchResults(Pattern pattern, PatternMatcherInput input)
    {
	int groups;
	MatchResult result;
	LOGGER.debug("Pattern: {}", pattern.getPattern());
	if (matcher.contains(input, pattern))
	{
	    int matchCount = 0;
	    do
	    {
		result = matcher.getMatch();
		matchCount++;
		// Perform whatever processing on the result you want.
		// Here we just print out all its elements to show how its
		// methods are used.

		LOGGER.debug("Match: {}", result.toString());
		// LOGGER.debug("Length: {}" , result.length());
		groups = result.groups();
		// LOGGER.debug("Groups: {}" , groups);
		// LOGGER.debug("Begin offset: {}" , result.beginOffset(0));
		// LOGGER.debug("End offset: {}" , result.endOffset(0));
		// LOGGER.debug("Saved Groups: ");
		//
		// Start at 1 because we just printed out group 0
		for (int group = 1; group < groups; group++)
		{
		    LOGGER.debug(group + ": {}", result.group(group));
		    // LOGGER.debug("Begin: {}" , result.begin(group));
		    // LOGGER.debug("End: {}" , result.end(group));
		}
	    }
	    while (matcher.contains(input, pattern));
	    LOGGER.debug("{} matches", matchCount);
	}
	else
	{
	    LOGGER.debug("No matches.");
	}
    }

    /**
     * @param regex
     * @throws MalformedPatternException
     *             Bad pattern
     */
    public static final Pattern initPattern(String regex) throws MalformedPatternException
    {
	Pattern pattern;
	try
	{
	    pattern = compiler.compile(regex);
	}
	catch (MalformedPatternException e)
	{
	    LOGGER.error("Bad pattern.", e);
	    fail(e.getMessage());
	    throw e;
	}
	return pattern;
    }

    /**
     * @param regex
     * @param inputString
     * @throws MalformedPatternException
     */
    public static final void debugRegex(String regex, String inputString)
	    throws MalformedPatternException
    {
	Pattern pattern = initPattern(regex);
	PatternMatcherInput input = new PatternMatcherInput(inputString);

	outputMatchResults(pattern, input);
    }

    /**
     * Assert the provided Regular Expression is false
     * 
     * @param matcher
     *            Regular Expression Match to check
     */
    public static final void assertRegexFalse(Matcher matcher)
    {
	// Reset the Matcher in case it has been used
	matcher.reset();
	// This madness is needed to get the search input text
	StringBuffer sb = new StringBuffer();
	matcher.appendTail(sb);
	// Run the Assert statement
	assertFalse(String.format("Regex should not have a match.\r%1$s\rSearch text:\r%2$s",
	    matcher.pattern().pattern(), sb), matcher.find());
    }

    /**
     * Assert the provided Regular Expression is true
     * 
     * @param matcher
     *            Regular Expression Match to check
     */
    public static final void assertRegexTrue(Matcher matcher)
    {
	// Reset the Matcher in case it has been used
	matcher.reset();
	// This madness is needed to get the search input text
	StringBuffer sb = new StringBuffer();
	matcher.appendTail(sb);
	// Run the Assert statement
	assertTrue(String.format("Regex should have a match.\r%1$s\rSearch text:\r%2$s", matcher
		.pattern().pattern(), sb), matcher.find());
    }

    /**
     * @param matcher
     * @param found
     * @param showGroupCount
     * @throws IndexOutOfBoundsException
     *             More than 100 matches have been found.
     * @return
     */
    public static final int printRegexGroups(Matcher matcher, int found, boolean showGroupCount)
	    throws IndexOutOfBoundsException
    {
	// Skip Group(0) by default since it is the entire match
	int groupStart = 1;
	if (showGroupCount)
	{
	    LOGGER.debug("Match {}: Group Count {}", found + 1, matcher.groupCount());
	    groupStart = 0;
	}
	for (int groupNum = groupStart; groupNum <= matcher.groupCount(); groupNum++)
	{
	    LOGGER.debug("Match {}: Group {}: {}", new Object[] { found + 1, groupNum,
		    matcher.group(groupNum) });
	}
	found++;
	if (found > 100)
	{
	    throw new IndexOutOfBoundsException("To many matches, more than 100 matches found.");
	}
	return found;
    }
}
