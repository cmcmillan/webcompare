package com.chris.utils.textdiff;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.utils.text.TextFileIn;
import com.chris.utils.textdiff.exceptions.InvalidLineStateException;

/**
 * <h2>Text Difference Engine</h2>
 * <p>
 * Engine for comparing two Texts and producing a report of differences and
 * matches.
 * </p>
 * 
 * @author cjmcmill
 * 
 */
public class TextDiff
{
    private static final Logger LOGGER = LoggerFactory.getLogger(TextDiff.class);

    /**
     * Compare two Text Files
     * 
     * @param primaryInput
     *            leftside of the compare
     * @param secondaryInput
     *            rightside of the compare
     * @throws IOException
     *             error accessing an input file
     */
    public static void compare(String primaryInput, String secondaryInput) throws IOException
    {
	TextFileIn primaryTextFile = new TextFileIn(primaryInput);
	TextFileIn secondaryTextFile = new TextFileIn(secondaryInput);

	try
	{
	    compare(primaryTextFile.asArray(), secondaryTextFile.asArray());
	}
	catch (InvalidLineStateException e)
	{
	    e.printStackTrace();
	    LOGGER.error("Unable to compare input", e);
	}
    }

    /**
     * Compare two String[]
     * 
     * @param primaryInput
     *            leftside of the compare
     * @param secondaryInput
     *            rightside of the compare
     * @throws InvalidLineStateException
     */
    public static void compare(String[] primaryInput, String[] secondaryInput)
	    throws InvalidLineStateException
    {
	List<LineInfo> primaryLines = LineInfo.asLineInfo(primaryInput, LineState.PrimaryOnly);
	List<LineInfo> secondaryLines =
		LineInfo.asLineInfo(secondaryInput, LineState.SecondaryOnly);

	int curPrimIndex = 0;
	int curSecIndex = 0;

	// Initial attempt at matching lines
	for (int primIndex = 0; primIndex < primaryLines.size(); primIndex++)
	{
	    LineInfo primInfo = primaryLines.get(primIndex);

	    // Find the next matching line
	    int secIndex = matchLine(primInfo, curSecIndex, secondaryLines);
	    if (secIndex >= curSecIndex)
	    {
		// Match found, Update and set the links
		LineInfo secondaryInfo = secondaryLines.get(secIndex);

		primInfo.updateState(LineState.UniqueMatch, secIndex);
		secondaryInfo.updateState(LineState.UniqueMatch, primIndex);

		primaryLines.set(primIndex, primInfo);
		secondaryLines.set(secIndex, secondaryInfo);

		curSecIndex = secIndex++;
	    }
	}

	printList(primaryLines);
	printList(secondaryLines);
    }

    /**
     * Find the first matching {@link LineInfo} in lines. If line is not found,
     * -1 is returned, otherwise the index of the first matching
     * {@link LineInfo} in lines is returned.
     * 
     * @param line
     *            search {@link LineInfo}
     * @param startIndex
     *            index at which to begin searching
     * @param lines
     * @return
     */
    static int matchLine(LineInfo line, int startIndex, List<LineInfo> lines)
    {
	int index = -1;

	if (lines.size() == 0)
	{
	    return index;
	}
	else if (startIndex < 0 || startIndex >= lines.size())
	{
	    throw new IllegalArgumentException("startIndex must be within the bounds of lines.");
	}

	String searchLine = line.getLine();
	for (int i = startIndex; i < lines.size(); i++)
	{
	    LineInfo currLine = lines.get(i);
	    if (currLine.isMatch(searchLine))
	    {
		// Found a matching line
		index = i;
		break;
	    }
	}

	return index;
    }

    /**
     * Print each item in the list
     * 
     * @param <E>
     *            type of items in the list
     * @param list
     *            items to print using {@code Object.toString()}
     */
    static <E> void printList(List<E> list)
    {
	for (int i = 0; i < list.size(); i++)
	{
	    // LOGGER.info(list.get(i).toString());
	    System.out.println(list.get(i));
	}
    }

}
