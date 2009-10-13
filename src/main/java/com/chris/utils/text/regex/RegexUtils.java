package com.chris.utils.text.regex;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;
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
    /**
     * regex fragment for any character except a colon
     */
    public static final String A_NO_COLON = "[^:]";
    /**
     * regex fragment for a colon boundary
     */
    public static final String A_COLON_BOUND = ":\\b";
    /**
     * regex fragment for a maven coordinate ending at the end of the line
     */
    public static final String A_MVN_COORD_EOL = "(" + A_NO_COLON + "*)$";
    /**
     * regex fragment for the initial maven coordinate consists of first word
     * without a space before a colon
     */
    public static final String A_MVN_COORD_START = "^.*?([\\w-.]*)" + A_COLON_BOUND;
    /**
     * regex fragment for a maven coordinate bound by a colon
     */
    public static final String A_MVN_COORD_COLON = "(" + A_NO_COLON + "*)" + A_COLON_BOUND;
    /**
     * regex fragment for the middle maven coordinates consisting of
     * {@link A_MVN_COORD_COLON} 2-4x
     */
    public static final String A_MVN_COORD_CENTER =
	    "(" + A_MVN_COORD_COLON + A_MVN_COORD_COLON + "|" + A_MVN_COORD_COLON
		    + A_MVN_COORD_COLON + A_MVN_COORD_COLON + "|" + A_MVN_COORD_COLON
		    + A_MVN_COORD_COLON + A_MVN_COORD_COLON + A_MVN_COORD_COLON + ")";
    /**
     * regex fragment for a maven coordinate ending at the end of the line
     */
    public static final String A_MVN_COORD_END = "(" + A_NO_COLON + "*)$";

    /**
     * regex fragment to parse out coordinates from a maven log line
     */
    public static final String A_MVN_LINE =
	    A_MVN_COORD_START + A_MVN_COORD_CENTER + A_MVN_COORD_END;
    /**
     * Map of regular expressions that have been initialized.
     */
    private static Map<String, Pattern> patterns = new HashMap<String, Pattern>();
    private static final PatternCompiler compiler = new Perl5Compiler();;
    private static final PatternMatcher matcher = new Perl5Matcher();

    /**
     * Assert the provided Regular Expression is false
     * 
     * @param javaMatcher
     *            {@link Matcher} Regular Expression Match to check
     */
    public static final void assertRegexFalse(Matcher javaMatcher)
    {
	// Reset the Matcher in case it has been used
	javaMatcher.reset();
	// This madness is needed to get the search input text
	StringBuffer input = new StringBuffer();
	javaMatcher.appendTail(input);
	// Run the Assert statement
	assertRegex(javaMatcher.pattern().pattern(), input.toString(), false, javaMatcher.find());
    }

    /**
     * Assert the provided Regular Expression is false
     * 
     * @param pattern
     *            Regular Expression to test
     * @param input
     *            Input to be parsed
     */
    public static final void assertRegexFalse(Pattern pattern, PatternMatcherInput input)
    {
	// Run the Assert statement
	assertRegex(pattern.getPattern(), String.valueOf(input.getInput()), false, matcher
		.contains(input, pattern));
    }

    /**
     * Assert the provided Regular Expression is false
     * 
     * @param regex
     *            Regular Expression to test
     * @param inputString
     *            Input to be parsed
     */
    public static void assertRegexFalse(String regex, String inputString)
    {
	// Initialize the pattern
	try
	{
	    Pattern pattern = initPattern(regex);
	    PatternMatcherInput input = new PatternMatcherInput(inputString);
	    assertRegexFalse(pattern, input);
	}
	catch (MalformedPatternException e)
	{
	    LOGGER.error("Invalid regular expression", e);
	    fail(e.getLocalizedMessage());
	}
    }

    /**
     * Assert the provided Regular Expression is true
     * 
     * @param javaMatcher
     *            {@link Matcher} Regular Expression Match to check
     */
    public static final void assertRegexTrue(Matcher javaMatcher)
    {
	// Reset the Matcher in case it has been used
	javaMatcher.reset();
	// This madness is needed to get the search input text
	StringBuffer input = new StringBuffer();
	javaMatcher.appendTail(input);
	// Run the Assert statement
	assertRegex(javaMatcher.pattern().pattern(), input.toString(), true, javaMatcher.find());
    }

    /**
     * Assert the provided Regular Expression is true
     * 
     * @param pattern
     *            Regular Expression to test
     * @param input
     *            Input to be parsed
     */
    public static final void assertRegexTrue(Pattern pattern, PatternMatcherInput input)
    {
	// Run the Assert statement
	assertRegex(pattern.getPattern(), String.valueOf(input.getInput()), true, matcher.contains(
	    input, pattern));
    }

    /**
     * Assert the provided Regular Expression is true
     * 
     * @param regex
     *            Regular Expression to test
     * @param inputString
     *            Input to be parsed
     */
    public static void assertRegexTrue(String regex, String inputString)
    {
	// Initialize the pattern
	try
	{
	    Pattern pattern = initPattern(regex);
	    PatternMatcherInput input = new PatternMatcherInput(inputString);
	    assertRegexTrue(pattern, input);
	}
	catch (MalformedPatternException e)
	{
	    LOGGER.error("Invalid regular expression", e);
	    fail(e.getLocalizedMessage());
	}
    }

    /**
     * Output the results of using {@code regex} on {@code inputString}
     * 
     * @param regex
     *            Regular Expression
     * @param inputString
     *            Input to be parsed
     * @param verbose
     *            Enable more verbose logging of Regex match information
     * @throws MalformedPatternException
     *             {@code regex} is not a valid pattern.
     */
    public static final void debugRegex(String regex, String inputString, boolean verbose)
	    throws MalformedPatternException
    {
	// Initialize the pattern
	Pattern pattern = initPattern(regex);
	PatternMatcherInput input = new PatternMatcherInput(inputString);
	// Output the Match results using the Logger
	outputMatchResults(pattern, input, verbose);
    }

    /**
     * Get a pattern associated with {@code regex}
     * 
     * @param regex
     *            Regular Expression
     * @throws MalformedPatternException
     *             {@code regex} is not a valid pattern.
     */
    public static final Pattern initPattern(String regex) throws MalformedPatternException
    {
	if (!patterns.containsKey(regex))
	{
	    // Create a new pattern, and stash a copy for reuse.
	    try
	    {
		patterns.put(regex, compiler.compile(regex));
	    }
	    catch (MalformedPatternException e)
	    {
		String errMsg =
			String.format("Invalid regular expression pattern, %1$s, %2$s", regex, e
				.getLocalizedMessage());
		LOGGER.error(errMsg, e);
		// fail(errMsg);
		throw e;
	    }
	}
	return patterns.get(regex);
    }

    /**
     * Print the groups of the {@link Matcher} {@code javaMatcher} match.
     * 
     * @param javaMatcher
     *            {@link Matcher} Regular Expression Match to check
     * @param found
     *            Number of matches that have been found
     * @param showGroupCount
     *            Print the number of groups in the match and group(0) if true,
     *            otherwise prints only nested groups
     * @throws IndexOutOfBoundsException
     *             More than 100 matches have been found.
     * @return Number of matches that have been found
     */
    public static final int outputJavaRegexGroups(Matcher javaMatcher, int found,
	    boolean showGroupCount) throws IndexOutOfBoundsException
    {
	return outputJavaRegexGroups(javaMatcher, found, showGroupCount, LOGGER);
    }

    /**
     * Print the groups of the {@link Matcher} {@code javaMatcher} match.
     * 
     * @param javaMatcher
     *            {@link Matcher} Regular Expression Match to check
     * @param found
     *            Number of matches that have been found
     * @param showGroupCount
     *            Print the number of groups in the match and group(0) if true,
     *            otherwise prints only nested groups
     * @param LOGGER
     *            Logger to output debug information
     * @throws IndexOutOfBoundsException
     *             More than 100 matches have been found.
     * @return Number of matches that have been found
     */
    public static final int outputJavaRegexGroups(Matcher javaMatcher, int found,
	    boolean showGroupCount, Logger LOGGER) throws IndexOutOfBoundsException
    {
	// Skip Group(0) by default since it is the entire match
	int groupStart = 1;
	if (showGroupCount)
	{
	    LOGGER.debug("Match {}: Group Count {}", found + 1, javaMatcher.groupCount());
	    groupStart = 0;
	}
	for (int groupNum = groupStart; groupNum <= javaMatcher.groupCount(); groupNum++)
	{
	    LOGGER.debug("Match {}: Group {}: {}", new Object[] { found + 1, groupNum,
		    javaMatcher.group(groupNum) });
	}
	found++;
	if (found > 100)
	{
	    throw new IndexOutOfBoundsException("To many matches, more than 100 matches found.");
	}
	return found;
    }

    /**
     * Output the {@link MatchResult} information
     * 
     * @param pattern
     *            Regular Expression Pattern
     * @param input
     *            Input to be parsed
     * @param verbose
     *            Enable more verbose logging of Regex match information
     */
    public static final void outputMatchResults(Pattern pattern, PatternMatcherInput input,
	    boolean verbose)
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
		groups = result.groups();
		LOGGER.debug("Groups: {}", groups);
		if (verbose)
		{
		    LOGGER.debug("Length: {}", result.length());
		    LOGGER.debug("Begin offset: {}", result.beginOffset(0));
		    LOGGER.debug("End offset: {}", result.endOffset(0));
		}
		LOGGER.debug("Saved Groups: ");
		// Start at 1 because we just printed out group 0
		for (int group = 1; group < groups; group++)
		{
		    LOGGER.debug(group + ": {}", result.group(group));
		    if (verbose)
		    {
			LOGGER.debug("Begin: {}", result.begin(group));
			LOGGER.debug("End: {}", result.end(group));
		    }
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
     * Assert the provided Regular Expression is true or false
     * 
     * @param pattern
     *            Regular Expression to test
     * @param inputText
     *            Input to be parsed
     * @param assertType
     *            Type of assert to run
     * @param condition
     *            Condition to be tested
     */
    private static final void assertRegex(String pattern, String inputText, boolean assertType,
	    boolean condition)
    {
	String message =
		String.format("Regex should%1$s have a match.\r%2$s\rSearch text:\r%3$s",
		    assertType ? "" : " not", pattern, inputText);
	// Run the Assert statement
	if (assertType)
	    assertTrue(message, condition);
	else
	    assertFalse(message, condition);
    }
}
