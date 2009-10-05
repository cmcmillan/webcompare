package com.chris.utils.text.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


public class TestRegexLooks
{
    @Test
    public void testPositiveLookahead()
    {
	String regex = "(?=^255).*";

	Pattern pattern = Pattern.compile(regex);

	String candidate = "255.0.0.1";

	Matcher matcher = pattern.matcher(candidate);

	String ip = "not found";

	if (matcher.find())
	    ip = matcher.group();

	String msg = "ip: " + ip;

	System.out.println(msg);
    }

    @Test
    public void testPositiveLookBehind1()
    {
	String regex = "(?<=http://)\\S+";

	Pattern pattern = Pattern.compile(regex);

	String candidate = "The Java2s website can be found at ";
	candidate += "http://www.java-2s.com. There, ";
	candidate += "you can find some Java examples.";

	Matcher matcher = pattern.matcher(candidate);

	while (matcher.find())
	{
	    String msg = ":" + matcher.group() + ":";
	    System.out.println(msg);
	}
    }

    @Test
    public void testNegativeLookaheadExample()
    {
	String regex = "John (?!Smith)[A-Z]\\w+";
	Pattern pattern = Pattern.compile(regex);

	String candidate = "I think that John Smith ";
	candidate += "is a fictional character. His real name ";
	candidate += "might be John Jackson, John Westling, ";
	candidate += "or John Holmes for all we know.";

	Matcher matcher = pattern.matcher(candidate);

	String tmp = null;

	while (matcher.find())
	{
	    tmp = matcher.group();
	    System.out.println("MATCH:" + tmp);
	}
    }
}
