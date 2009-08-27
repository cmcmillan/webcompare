/**
 * 
 */
package com.chris.utils.textdiff;

import java.util.ArrayList;
import java.util.List;

import com.chris.utils.textdiff.exceptions.InvalidLineStateException;

/**
 * Wrapper class to provide information about a line and its state
 * 
 * @author cjmcmill
 * 
 */
public class LineInfo
{
    private LineState state;
    private int lineNumber;
    private int otherLine;
    private String line;

    /**
     * Convert a {@link String}[] to a {@code List<LineInfo>}. {@link LineInfo}
     * objects are initialized using the line index and line.
     * 
     * @param lines
     *            {@link String}[]
     * @return {@code List<LineInfo>}
     * @throws InvalidLineStateException
     */
    public static List<LineInfo> asLineInfo(String[] lines, LineState initialState)
	    throws InvalidLineStateException
    {
	switch (initialState)
	{
	    case UniqueMatch:
		throw new IllegalArgumentException("initialState," + initialState
			+ " , is invalid.");
	    default:
		break;
	}

	List<LineInfo> lineInfos = new ArrayList<LineInfo>(lines.length);

	for (int i = 0; i < lines.length; i++)
	{
	    lineInfos.add(new LineInfo(i, lines[i], initialState, -1));
	}

	return lineInfos;
    }

    /**
     * @param lineNumber
     * @param line
     */
    public LineInfo(int lineNumber, String line)
    {
	this.lineNumber = lineNumber;
	this.line = line;
    }

    /**
     * @param lineNumber
     *            index of the unique line of text
     * @param line
     *            unique line of text
     * @param state
     *            state of the line as a {@link LineState}
     * @param otherLine
     * @throws InvalidLineStateException
     *             OtherLine must be a non-negative integer if state is not
     *             {@link LineState.PrimaryOnly} or
     *             {@link LineState.SecondaryOnly}
     */
    public LineInfo(int lineNumber, String line, LineState state, int otherLine)
	    throws InvalidLineStateException
    {
	this.lineNumber = lineNumber;
	this.line = line;
	this.otherLine = otherLine;
	this.state = state;
    }

    /**
     * @return the line
     */
    public String getLine()
    {
	return line;
    }

    /**
     * @return the lineNumber
     */
    public int getLineNumber()
    {
	return lineNumber;
    }

    /**
     * @return the otherLine
     */
    public int getOtherLine()
    {
	return otherLine;
    }

    /**
     * @return the state
     * @throws InvalidLineStateException
     *             OtherLine must be a non-negative integer if state is not
     *             {@link LineState.PrimaryOnly} or
     *             {@link LineState.SecondaryOnly}
     */
    public LineState getState() throws InvalidLineStateException
    {
	return state;
    }

    /**
     * Check if inputLine matches line and it is not already a unique match.
     * 
     * @param inputLine
     *            line to compare to this.line
     * @return true if inputLine matches line and it is not already a unique
     *         match.
     */
    public boolean isMatch(String inputLine)
    {
	boolean match = false;
	// Verify the new LineState and OtherLine values
	switch (state)
	{
	    case UniqueMatch:
		// this LineInfo is already matched
		match = false;
		break;
	    case PrimaryOnly:
	    case SecondaryOnly:
	    default:
		// Check if this.line is equal to the inputLine
		match = this.line.equals(inputLine);
		break;
	}
	return match;
    }

    /**
     * Returns a string representation of the object values in the a tab
     * delimited format:
     * 
     * <pre>
     * {@code state}	{@code lineNumber}	{@code otherLine}	{@code line}
     * </pre>
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return String.format("%1$s\t%2$s\t%3$s\t%4$s", state, lineNumber, otherLine, line);
    }

    /**
     * Update the state and link to the otherLine
     * 
     * @param newState
     *            the state to set
     * @param newOtherLine
     *            the otherLine to set, -1 if newState is
     *            {@link LineState.PrimaryOnly} or
     *            {@link LineState.SecondaryOnly}
     * @throws InvalidLineStateException
     *             newOtherLine is invalid for newState
     */
    public void updateState(LineState newState, int newOtherLine) throws InvalidLineStateException
    {
	// Verify the new LineState and OtherLine values
	switch (newState)
	{
	    case PrimaryOnly:
	    case SecondaryOnly:
		if (newOtherLine != -1)
		{
		    throw new InvalidLineStateException("newOtherLine must be -1.");
		}
		break;
	    case UniqueMatch:
		if (newOtherLine == -1)
		{
		    throw new InvalidLineStateException(
			    "newOtherLine must be a greater than or equal to zero.");
		}
		break;
	    default:
		break;
	}
	// Update the state and otherLine
	this.state = newState;
	this.otherLine = newOtherLine;
    }
}
