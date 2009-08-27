package com.chris.utils.textdiff.exceptions;

import com.chris.utils.textdiff.LineState;

/**
 * Exception thrown when an invalid {@link LineState} state is encountered.
 * 
 * @author cjmcmill
 */
public class InvalidLineStateException extends Exception
{

    /**
     * Serialization Version ID to stop Eclipse from whining
     */
    private static final long serialVersionUID = 5782039699573714898L;

    /**
     * Construct a new LineState exception.
     * 
     * @see Exception
     */
    public InvalidLineStateException()
    {
	super();
    }

    /**
     * Constructs a new LineState exception with the specified detail message.
     * 
     * @param message
     *            detail message
     * @see Exception
     */
    public InvalidLineStateException(String message)
    {
	super(message);
    }

    /**
     * Constructs a new LineState exception with the specified detail message
     * and cause.
     * 
     * @param message
     *            detail message
     * @param cause
     *            {@link Throwable}
     * @see Exception
     */
    public InvalidLineStateException(String message, Throwable cause)
    {
	super(message, cause);
    }

    /**
     * Constructs a new LineState exception with the specified cause.
     * 
     * @param cause
     *            {@link Throwable}
     * @see Exception
     */
    public InvalidLineStateException(Throwable cause)
    {
	super(cause);

    }

}
