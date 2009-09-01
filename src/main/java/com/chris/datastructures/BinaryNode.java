package com.chris.datastructures;

public class BinaryNode<E>
{
    protected BinaryNode<E> left;
    protected BinaryNode<E> right;
    protected E value;

    /**
     * Create a new BinaryNode with provided value
     * 
     * @param value
     */
    public BinaryNode(E value)
    {
	left = right = null;
	this.value = value;
    }

    /**
     * Compare {@code node} to this node.
     * 
     * @param node
     * @return 0 if the symbols are equal, -1 if {@code symbol} is less than
     *         {@code otherSymbol}, 1 if {@code symbol} is greater than {@code
     *         otherSymbol}
     */
    public int compareTo(BinaryNode<E> node)
    {
	return compareTo(node.value);
    }

    /**
     * Compare the symbols
     * 
     * @param otherValue
     * @return 0 if the symbols are equal, -1 if {@code value} is less than
     *         {@code otherValue}, 1 if {@code symbol} is greater than {@code
     *         otherSymbol}
     */
    public int compareTo(E otherValue)
    {
	if (value.hashCode() == otherValue.hashCode())
	{
	    // Same thing, no need to compare the string for speed sake
	    return 0;
	}
	else
	{
	    return value.toString().compareTo(otherValue.toString());
	}
    }

    /**
     * Insert the symbol into the binary tree
     * 
     * @param symbol
     * @return new {@link BinaryNode}
     */
    public BinaryNode<E> insertSymbol(E symbol)
    {
	int comparison = compareTo(symbol);
	if (comparison <= 0 && this.left == null)
	{
	    // insert the node on the left side of the tree
	    this.left = new BinaryNode<E>(symbol);
	    return this.left;

	}
	else if (this.right == null)
	{
	    // insert the node on the right side of the tree
	    this.right = new BinaryNode<E>(symbol);
	    return this.right;

	}
	// Go further into the tree
	return insertSymbol(symbol);
    }
}
