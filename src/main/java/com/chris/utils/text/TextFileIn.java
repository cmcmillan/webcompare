package com.chris.utils.text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a text file input from disk. Usage:
 * 
 * <pre>
 * TextFileIn MyFileIn = newTextIn(fileid);
 * while( (MyLine = MyFileIn.readLine() ) != null )
 * {
 *   do something with MyLine
 * }
 * </pre>
 * 
 * Note: No close is necessary if you read to then end of file. If you do not
 * read to the end of file, call close().
 * 
 * @author Stan James
 * 
 */
public class TextFileIn
{
    private BufferedReader mReader = null;

    /**
     * Constructor opens provided file for input.
     * 
     * @param file
     * @throws FileNotFoundException
     */
    public TextFileIn(File file) throws FileNotFoundException
    {
	mReader = new BufferedReader(new FileReader(file));
    }

    /**
     * Constructor opens named file for input
     * 
     * @param fileName
     *            java.lang.String
     */
    public TextFileIn(String fileName) throws FileNotFoundException
    {
	mReader = new BufferedReader(new FileReader(fileName));
    }

    /**
     * Returns contents of a file as an array of Strings.
     * 
     * @return java.lang.String[]
     * @throws IOException
     *             java.io.IOException
     */
    public String[] asArray() throws IOException
    {
	String line;
	List<String> lineList = new ArrayList<String>();
	while ((line = this.readLine()) != null)
	{
	    lineList.add(line);
	}
	return lineList.toArray(new String[0]);
    }

    /**
     * Returns contents of a file as one String. NewLine characters in the file
     * are converted to single spaces
     * 
     * @return java.lang.String
     * @throws IOException
     *             java.io.IOException
     */
    public String asString() throws IOException
    {
	String line;
	StringBuffer rtn = new StringBuffer();
	while ((line = this.readLine()) != null)
	{
	    rtn.append(line);
	    rtn.append(" ");
	}
	return rtn.toString();
    }

    /**
     * Close the input file. This is not necessary if the client reads to the
     * end of file.
     */
    public void close()
    {
	if (mReader == null)
	{
	    return;
	}
	try
	{
	    mReader.close();
	    mReader = null;
	}
	catch (Exception e)
	{
	}
    }

    /**
     * Read one line from input file. On read past end of file closes the file
     * and returns null.
     * 
     * @return java.lang.String
     * @throws IOException
     */
    public String readLine() throws IOException
    {
	if (mReader == null)
	{
	    throw new IOException();
	}

	String line = mReader.readLine();

	if (line == null)
	{
	    this.close();
	}
	return line;
    }
}
