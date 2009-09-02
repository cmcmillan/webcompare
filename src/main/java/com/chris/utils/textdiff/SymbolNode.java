package com.chris.utils.textdiff;

import com.chris.datastructures.BinaryNode;

/**
 * Class "node". The symbol table routines in this class all understand the
 * symbol table format, which is a binary tree. The methods are: addSymbol,
 * symbolIsUnique, showSymbol.
 * 
 * @see {@link LineInfo}
 */
public class SymbolNode extends BinaryNode<String>
{
    LineState lineState;

    /**
     * Symtab, What's this?, is a tree hung from this
     */
    static SymbolNode anchor = null;

    /**
     * Construct a new Symbol table node and fill in its fields
     * 
     * @param line
     *            A line of the text
     */
    public SymbolNode(String line)
    {
	super(line);
	lineState = LineState.Fresh;
    }

    public static SymbolNode matchSymbol(String line)
    {
	int comparison;
	SymbolNode currNode = anchor;
	if (currNode == null)
	{
	    return anchor = new SymbolNode(line);
	}
	while (true)
	{
	    comparison = currNode.compareTo(line);
	    if (comparison == 0)
	    {
		/**
		 * Found
		 */
		return currNode;
	    }
	    else if (comparison < 0)
	    {
		if (currNode.left == null)
		{
		    currNode.left = new SymbolNode(line);
		    return (SymbolNode) currNode.left;
		}
		currNode = (SymbolNode) currNode.left;
	    }
	}
	// NOTE: There are return statements in the loop, so control does not
	// get here
    }

    @Override
    public int compareTo(String otherSymbol)
    {
	// TODO Auto-generated method stub
	return 0;
    }
}
