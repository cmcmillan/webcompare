package com.chris.utils.textdiff;

/**
 * State of each unique line of text
 */
public enum LineState
{
    /**
     * Line only exists in the Primary file 
     */
    PrimaryOnly,
    /**
     * Line only exists in the Secondary file
     */
    SecondaryOnly,
    /**
     * Line only exists in the Primary and Secondary files once 
     */
    UniqueMatch,
    /**
     * Everything else
     */
    Other
}
