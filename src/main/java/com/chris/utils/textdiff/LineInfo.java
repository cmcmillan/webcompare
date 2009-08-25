/**
 * 
 */
package com.chris.utils.textdiff;

import com.chris.utils.textdiff.exceptions.InvalidLineStateException;

/**
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
     * @return the line
     */
    public String getLine()
    {
	return line;
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
