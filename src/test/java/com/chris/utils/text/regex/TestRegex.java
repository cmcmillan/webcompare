package com.chris.utils.text.regex;

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

	LOGGER.debug("Check against empty string: {}", matcher.find());
	RegexUtils.assertRegexFalse(matcher);

	input = "[INFO] +- commons-pool:commons-pool:jar:1.5.2:compile";
	matcher.reset(input);

	LOGGER.debug("Check against \r{}\r{}", input, matcher.find());
	RegexUtils.assertRegexTrue(matcher);
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
	regex = "(?:(?:^.*?\\s)|^|:)";

	String input = "";
	Pattern pattern = Pattern.compile(regex);
	Matcher matcher = pattern.matcher(input);

	LOGGER.debug("Check against empty string: {}", matcher.find());
	// assertRegexFalse(matcher);

	// Update the input
	// input = "commons-pool:commons-pool-artifact:jar:1.5.2:compile";
	input = "xx x:jar:1.5.2:compile";
	LOGGER.debug("Input String: {}", input);
	// Update the matcher with the new input
	matcher.reset(input);

	LOGGER.debug("Check against \r{}\r{}", input, matcher.find());
	RegexUtils.assertRegexTrue(matcher);

	LOGGER.debug("------------------------------------------------------------------------");
	LOGGER.debug("--------------------------------- FIND ---------------------------------");
	LOGGER.debug("------------------------------------------------------------------------");
	matcher.reset();
	int found = 0;
	while (matcher.find())
	{
	    found = RegexUtils.outputJavaRegexGroups(matcher, found, true, LOGGER);
	}
	LOGGER.debug("Found: {}", found);
	LOGGER.debug("-------------------------------------------------------------------------");
	LOGGER.debug("------------------------------ LOOKING AT -------------------------------");
	LOGGER.debug("-------------------------------------------------------------------------");
	matcher.reset();
	found = 0;
	matcher.useTransparentBounds(true);
	if (matcher.lookingAt())
	{
	    found = RegexUtils.outputJavaRegexGroups(matcher, found, true, LOGGER);
	    LOGGER.debug("Start: {}, End: {}", matcher.start(), matcher.end());
	    LOGGER.debug("Region Start: {}, Region End: {}", matcher.regionStart(), matcher
		    .regionEnd());

	}
	matcher.useTransparentBounds(false);
	LOGGER.debug("Found: {}", found);
	LOGGER.debug("-------------------------------------------------------------------------");
	LOGGER.debug("--------------------------------- MATCH ---------------------------------");
	LOGGER.debug("-------------------------------------------------------------------------");
	matcher.reset();
	found = 0;
	if (matcher.matches())
	{
	    found = RegexUtils.outputJavaRegexGroups(matcher, found, true, LOGGER);
	}
	LOGGER.debug("Found: {}", found);

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
	// Logger.debug("Group {} {}", 1, matcher.group(1));
	// // for (int j = 0; j <= matcher.groupCount(); j++)
	// // {
	// // LOGGER.debug("Group {}: {}", j, matcher.group(j));
	// // }
	// }
	// else
	// {
	// totalNoMatchCount++;
	// }
	// }
	// LOGGER.debug("Match: {}, totalMatchCount);
	// LOGGER.debug("No Match: {}, totalNoMatchCount);
	// assertTrue(String.format("No Matches (%2$s) > Matches (%1$s)",
	// totalMatchCount, totalNoMatchCount), totalNoMatchCount < totalMatchCount);
    }
}
