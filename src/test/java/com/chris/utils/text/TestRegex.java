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
    public void simpleRegex()
    {
	String regex = "-[^:]*:";
	String input = "";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(input);

	LOGGER.debug(String.format("Check against empty string: %1$s", matcher.find()));
	assertRegexFalse(matcher);

	input = "[INFO] +- commons-pool:commons-pool:jar:1.5.2:compile";
	matcher.reset(input);

	LOGGER.debug(String.format("Check against \r%1$s\r%2$s", input, matcher.find()));
	assertRegexTrue(matcher);
    }

    @Test
    public void testDependencyTreeRegEx() throws Exception
    {
	String regex = ".*( ([^:]*):([^:]*):([^:]*):([^:])*:([^:]*)$)";
	// regex = ".*( ((([^:]*):){1,6})([^:]*)$)";
	// regex =
	// "(?:.*\\s|^)(([^:]+)(?::|$)([^:]+)(?::|$)([^:]+)(?::|$)([^:]+)(?::|$)([^:]+)(?::|$))";
	// regex =
	// (?:.*\\s|^)(([^:]+)(?::|$)([^:]+)(?::|$)([^:]+)(?::|$)(([^:]+)(?::|$)([^:]+)(?::|$)))";
	// regex =
	// (?:.*\\s|^)(([^:]+)(?::|$)([^:]+)(?::|$)([^:]+)(?::|$)(([^:]+)(?::|$))+)";
	// regex = "(?:.*\\s|^)(([^:]+)(?::|$)){2}";
	// regex = "(?:.*\\s|^)((([^:]+)(?::|$)){2})";
	// regex = "(?:.*\\s|^|:)(([^:]+)(?::|$))";
	regex = "(?:(?:^.*?\\s)|^|:)([^:]+)(:|$)";
	regex = "(?:(?:^.*?\\s)|^|:)";

	final String A_START = "^";
	/**
	 * regex fragment to look for some spaces
	 */
	final String A_SPACES = "\\s++";
	/**
	 * regex fragment to look for a phrase at the start of a line
	 */
	final String A_START_PHRASE = "^.*\\s";
	/**
	 * regex fragment to look for a maven coordinate
	 */
	final String A_MVN_COORDINATE = "([^:]+)(:|$)";

	regex = "(?:(?:^.*?\\s)|^|:)";

	String input = "";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(input);

	LOGGER.debug(String.format("Check against empty string: %1$s", matcher.find()));
	// assertRegexFalse(matcher);

	// Update the input
	// input = "commons-pool:commons-pool-artifact:jar:1.5.2:compile";
	input = "xx x:jar:1.5.2:compile";
	LOGGER.debug(String.format("Input String: %1$s", input));
	// Update the matcher with the new input
	matcher.reset(input);

	LOGGER.debug(String.format("Check against \r%1$s\r%2$s", input, matcher.find()));
	assertRegexTrue(matcher);

	LOGGER.debug("------------------------------------------------------------------------");
	LOGGER.debug("--------------------------------- FIND ---------------------------------");
	LOGGER.debug("------------------------------------------------------------------------");
	matcher.reset();
	int found = 0;
	while (matcher.find())
	{
	    found = printRegexGroups(matcher, found, true);
	}
	LOGGER.debug(String.format("Found: %1$s", found));
	LOGGER.debug("-------------------------------------------------------------------------");
	LOGGER.debug("------------------------------ LOOKING AT -------------------------------");
	LOGGER.debug("-------------------------------------------------------------------------");
	matcher.reset();
	found = 0;
	matcher.useTransparentBounds(true);
	if (matcher.lookingAt())
	{
	    found = printRegexGroups(matcher, found, true);
	    LOGGER.debug(String.format("Start: %1$s, End: %2$s", matcher.start(), matcher.end()));
	    LOGGER.debug(String.format("Region Start: %1$s, Region End: %2$s", matcher
		    .regionStart(), matcher.regionEnd()));

	}
	matcher.useTransparentBounds(false);
	LOGGER.debug(String.format("Found: %1$s", found));
	LOGGER.debug("-------------------------------------------------------------------------");
	LOGGER.debug("--------------------------------- MATCH ---------------------------------");
	LOGGER.debug("-------------------------------------------------------------------------");
	matcher.reset();
	found = 0;
	if (matcher.matches())
	{
	    found = printRegexGroups(matcher, found, true);
	}
	LOGGER.debug(String.format("Found: %1$s", found));

	// // Get the input text file
	// TextFileIn txtFile = new TextFileIn("deplist.txt");
	// // TextFileIn txtFile = new TextFileIn("deptree.txt");
	// // TextFileIn txtFile = new TextFileIn("deptree2.txt");
	// String myLine;
	// int totalMatchCount = 0;
	// int totalNoMatchCount = 0;
	// while ((myLine = txtFile.readLine()) != null)
	// {
	// matcher = pattern.matcher(myLine);
	// if (matcher.matches())
	// {
	// totalMatchCount++;
	// System.out.println(String.format("Group %1$s: %2$s", 1,
	// matcher.group(1)));
	// // for (int j = 0; j <= matcher.groupCount(); j++)
	// // {
	// // LOGGER.debug(String.format("Group %1$s: %2$s", j,
	// // matcher.group(j)));
	// // }
	// }
	// else
	// {
	// totalNoMatchCount++;
	// }
	// }
	// LOGGER.debug(String.format("No Matches (%2$s) > Matches (%1$s)",
	// totalMatchCount,
	// totalNoMatchCount), totalNoMatchCount < totalMatchCount);
	// assertTrue(String.format("No Matches (%2$s) > Matches (%1$s)",
	// totalMatchCount,
	// totalNoMatchCount), totalNoMatchCount < totalMatchCount);
    }

    /**
     * @param matcher
     * @param found
     * @param showGroupCount
     *            TODO
     * @return
     */
    private int printRegexGroups(Matcher matcher, int found, boolean showGroupCount)
    {
	// Skip Group(0) by default since it is the entire match
	int groupStart = 1;
	if (showGroupCount)
	{
	    LOGGER.debug(String.format("Match %1$s: Group Count %2$s", found + 1, matcher
		    .groupCount()));
	    groupStart = 0;
	}
	for (int groupNum = groupStart; groupNum <= matcher.groupCount(); groupNum++)
	{
	    LOGGER.debug(String.format("Match %1$s: Group %2$s: %3$s", found + 1, groupNum, matcher
		    .group(groupNum)));
	}
	found++;
	assertTrue("To many matches, more than 100 matches found.", found < 100);
	return found;
    }

    /**
     * Assert the provided Regular Expression is false
     * 
     * @param matcher
     *            Regular Expression Match to check
     */
    private static void assertRegexFalse(Matcher matcher)
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
    private static void assertRegexTrue(Matcher matcher)
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
}
