/**
 * 
 */
package com.chris.utils.dbutils;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author cjmcmill
 * 
 */
public abstract class NamedPreparedStatement implements PreparedStatement
{
    /**
     * Maps parameter names to arrays of Integers which are the parameter
     * indices.
     * 
     * @since 07 Sept 2009 - Updated to use generics - Chris McMillan
     */
    private final Map<String, List<Integer>> indexMap = new HashMap<String, List<Integer>>();

    /**
     * Parses a query with named parameters. The parameter-index mappings are
     * put into the map, and the parsed query is returned. DO NOT CALL FROM
     * CLIENT CODE. This method is non-private so JUnit code can test it.
     * 
     * @param query
     *            query to parse
     * @param paramMap
     *            map to hold parameter-index mappings
     * @return the parsed query
     * @since 07 September 2009 - Chris McMillan - Removed replacement of list
     *        of Integers to array of ints
     */
    static final String parse(String query, Map<String, List<Integer>> paramMap)
    {
	// I was originally using regular expressions, but they didn't work well
	// for ignoring
	// parameter-like strings inside quotes.
	int length = query.length();
	StringBuffer parsedQuery = new StringBuffer(length);
	boolean inSingleQuote = false;
	boolean inDoubleQuote = false;
	int index = 1;

	for (int i = 0; i < length; i++)
	{
	    char c = query.charAt(i);
	    if (inSingleQuote)
	    {
		if (c == '\'')
		{
		    inSingleQuote = false;
		}
	    }
	    else if (inDoubleQuote)
	    {
		if (c == '"')
		{
		    inDoubleQuote = false;
		}
	    }
	    else
	    {
		if (c == '\'')
		{
		    inSingleQuote = true;
		}
		else if (c == '"')
		{
		    inDoubleQuote = true;
		}
		else if (c == ':' && i + 1 < length
			&& Character.isJavaIdentifierStart(query.charAt(i + 1)))
		{
		    int j = i + 2;
		    while (j < length && Character.isJavaIdentifierPart(query.charAt(j)))
		    {
			j++;
		    }
		    String name = query.substring(i + 1, j);
		    c = '?'; // replace the parameter with a question mark
		    i += name.length(); // skip past the end if the parameter

		    List<Integer> indexList = paramMap.get(name);
		    if (indexList == null)
		    {
			indexList = new LinkedList<Integer>();
			paramMap.put(name, indexList);
		    }
		    indexList.add(new Integer(index));

		    index++;
		}
	    }
	    parsedQuery.append(c);
	}

	return parsedQuery.toString();
    }

    /**
     * Returns the indexes for a parameter.
     * 
     * @param parameterName
     *            parameter name
     * @return parameter indexes
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 07 September 2009 - Chris McMillan - Updated to return Integer[]
     */
    private Integer[] getIndexes(String name)
    {
	List<Integer> indexes = indexMap.get(name);
	if (indexes == null)
	{
	    throw new IllegalArgumentException("Parameter not found: " + name);
	}
	return indexes.toArray(new Integer[0]);
    }

    /**
     * Sets the designated parameter to the given {@link java.sql.Array} value.
     * The driver converts this to an SQL {@code ARRAY} value when it sends it
     * to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            an {@code Array} object that maps an SQL {@code ARRAY} value
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setArray(String, java.sql.Array)
     */
    public void setArray(String parameterName, Array x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setArray(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.io.InputStream}
     * value. The driver converts this to an SQL {@code DATALINK} value when it
     * sends it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the Java input stream that contains the ASCII parameter value
     * @param length
     *            the number of bytes in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     *      java.io.InputStream, int)
     */
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setAsciiStream(indexes[i], x, length);
	}
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes. When a very large ASCII value is input to
     * a <code>LONGVARCHAR</code> parameter, it may be more practical to send it
     * via a <code>java.io.InputStream</code>. Data will be read from the stream
     * as needed until end-of-file is reached. The JDBC driver will do any
     * necessary conversion from ASCII to the database char format.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the Java input stream that contains the ASCII parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     *      java.io.InputStream, long)
     */
    public void setAsciiStream(String parameterName, InputStream x, long length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setAsciiStream(indexes[i], x, length);
	}
    }

    /**
     * Sets the designated parameter to the given input stream. When a very
     * large ASCII value is input to a {@code LONGVARCHAR} parameter, it may be
     * more practical to send it via a {@code java.io.InputStream}. Data will be
     * read from the stream as needed until end-of-file is reached. The JDBC
     * driver will do any necessary conversion from ASCII to the database char
     * format.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of {@code setAsciiStream} which
     * takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the Java input stream that contains the ASCII parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     *      java.io.InputStream)
     */
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setAsciiStream(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.math.BigDecimal}
     * value. The driver converts this to an SQL {@code NUMERIC} value when it
     * sends it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.math.BigDecimal} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBigDecimal(String,
     *      java.math.BigDecimal)
     */
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBigDecimal(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes. When a very large binary value is input to
     * a <code>LONGVARBINARY</code> parameter, it may be more practical to send
     * it via a <code>java.io.InputStream</code> object. The data will be read
     * from the stream as needed until end-of-file is reached.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the java input stream which contains the binary parameter val
     * @param length
     *            the number of bytes in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     *      java.io.InputStream, int)
     */
    public void setBinaryStream(String parameterName, InputStream x, int length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBinaryStream(indexes[i], x, length);
	}
    }

    /**
     * Sets the designated parameter to the given input stream, which will have
     * the specified number of bytes. When a very large binary value is input to
     * a <code>LONGVARBINARY</code> parameter, it may be more practical to send
     * it via a <code>java.io.InputStream</code> object. The data will be read
     * from the stream as needed until end-of-file is reached.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the java input stream which contains the binary parameter val
     * @param length
     *            the number of bytes in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     *      java.io.InputStream, long)
     */
    public void setBinaryStream(String parameterName, InputStream x, long length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBinaryStream(indexes[i], x, length);
	}
    }

    /**
     * Sets the designated parameter to the given input stream. When a very
     * large binary value is input to a <code>LONGVARBINARY</code> parameter, it
     * may be more practical to send it via a <code>java.io.InputStream</code>
     * object. The data will be read from the stream as needed until end-of-file
     * is reached.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setBinaryStream</code>
     * which takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the java input stream which contains the binary parameter val
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     *      java.io.InputStream)
     */
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBinaryStream(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.sql.Blob} value.
     * The driver converts this to an SQL {@code BLOB} value when it sends it to
     * the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            an {@code Blob} object that maps an SQL {@code BLOB} value
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBlob(String, java.sql.Blob)
     */
    public void setBlob(String parameterName, Blob x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBlob(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to a <code>InputStream</code> object. The
     * inputstream must contain the number of characters specified by length
     * otherwise a <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the
     * <code>setBinaryStream (int, InputStream, int)</code> method because it
     * informs the driver that the parameter value should be sent to the server
     * as a <code>BLOB</code>. When the <code>setBinaryStream</code> method is
     * used, the driver may have to do extra work to determine whether the
     * parameter data should be sent to the server as a
     * <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param inputStream
     *            An object that contains the data to set the parameter value
     *            to.
     * @param length
     *            the number of bytes in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBlob(String, java.io.InputStream,
     *      long)
     */
    public void setBlob(String parameterName, InputStream inputStream, long length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBlob(indexes[i], inputStream, length);
	}
    }

    /**
     * Sets the designated parameter to a {@code InputStream} object. This
     * method differs from the {@code setBinaryStream (int, InputStream)} method
     * because it informs the driver that the parameter value should be sent to
     * the server as a <code>BLOB</code>. When the <code>setBinaryStream</code>
     * method is used, the driver may have to do extra work to determine whether
     * the parameter data should be sent to the server as a
     * <code>LONGVARBINARY</code> or a <code>BLOB</code>
     * 
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setBlob</code> which
     * takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param inputStream
     *            An object that contains the data to set the parameter value
     *            to.
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBlob(String, java.io.InputStream)
     */
    public void setBlob(String parameterName, InputStream inputStream) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBlob(indexes[i], inputStream);
	}
    }

    /**
     * Sets the designated parameter to the given {@code boolean} value. The
     * driver converts this to an SQL {@code BIT} or {@code BOOLEAN} value when
     * it sends it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code boolean} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBoolean(String, boolean)
     */
    public void setBoolean(String parameterName, boolean x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBoolean(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code byte} value. The driver
     * converts this to an SQL {@code TINYINT} value when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code byte} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setByte(String, byte)
     */
    public void setByte(String parameterName, byte x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setByte(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code array} of bytes. The
     * driver converts this to an SQL {@code VARBINARY} or {@code LONGVARBINARY}
     * (depending on the argument's size relative to the driver's limits on
     * {@code LONGVARBINARY} values) value when it sends it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setBytes(String, byte[])
     */
    public void setBytes(String parameterName, byte[] x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBytes(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code> object,
     * which is the given number of characters long. When a very large UNICODE
     * value is input to a <code>LONGVARCHAR</code> parameter, it may be more
     * practical to send it via a <code>java.io.Reader</code> object. The data
     * will be read from the stream as needed until end-of-file is reached. The
     * JDBC driver will do any necessary conversion from UNICODE to the database
     * char format.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            the {@code java.io.Reader} object that contains the Unicode
     *            data
     * @param length
     *            the number of characters in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     *      java.io.Reader, int)
     */
    public void setCharacterStream(String parameterName, Reader reader, int length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setCharacterStream(indexes[i], reader, length);
	}
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code> object,
     * which is the given number of characters long. When a very large UNICODE
     * value is input to a <code>LONGVARCHAR</code> parameter, it may be more
     * practical to send it via a <code>java.io.Reader</code> object. The data
     * will be read from the stream as needed until end-of-file is reached. The
     * JDBC driver will do any necessary conversion from UNICODE to the database
     * char format.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            the {@code Reader} object that contains the Unicode data
     * @param length
     *            the number of characters in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     *      java.io.Reader, long)
     */
    public void setCharacterStream(String parameterName, Reader reader, long length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setCharacterStream(indexes[i], reader, length);
	}
    }

    /**
     * Sets the designated parameter to the given <code>Reader</code> object.
     * When a very large UNICODE value is input to a <code>LONGVARCHAR</code>
     * parameter, it may be more practical to send it via a
     * <code>java.io.Reader</code> object. The data will be read from the stream
     * as needed until end-of-file is reached. The JDBC driver will do any
     * necessary conversion from UNICODE to the database char format.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of
     * <code>setCharacterStream</code> which takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            the {@code java.io.Reader} object that contains the Unicode
     *            data
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     *      java.io.Reader)
     */
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setCharacterStream(indexes[i], reader);
	}
    }

    /**
     * Sets the designated parameter to the given {@code Clob} value. The driver
     * converts this to an SQL {@code CLOB} value when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            a {@code Clob} object that maps an SQL {@code CLOB}
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setClob(String, java.sql.Clob)
     */
    public void setClob(String parameterName, Clob x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setClob(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The reader
     * must contain the number of characters specified by length otherwise a
     * <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the
     * <code>setCharacterStream (int, Reader, int)</code> method because it
     * informs the driver that the parameter value should be sent to the server
     * as a <code>CLOB</code>. When the <code>setCharacterStream</code> method
     * is used, the driver may have to do extra work to determine whether the
     * parameter data should be sent to the server as a <code>LONGVARCHAR</code>
     * or a <code>CLOB</code>
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            the {@code Reader} object that contains the Unicode data
     * @param length
     *            the number of characters in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setClob(String, java.io.Reader, long)
     */
    public void setClob(String parameterName, Reader reader, long length) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setClob(indexes[i], reader, length);
	}
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. This
     * method differs from the <code>setCharacterStream (int, Reader)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>CLOB</code>. When the
     * <code>setCharacterStream</code> method is used, the driver may have to do
     * extra work to determine whether the parameter data should be sent to the
     * server as a <code>LONGVARCHAR</code> or a <code>CLOB</code>
     * 
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setClob</code> which
     * takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            the {@code Reader} object that contains the Unicode data
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setClob(String, java.io.Reader)
     */
    public void setClob(String parameterName, Reader reader) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setClob(indexes[i], reader);
	}
    }

    /**
     * Sets the designated parameter to the given <code>java.sql.Date</code>
     * value, using the given <code>Calendar</code> object. The driver uses the
     * <code>Calendar</code> object to construct an SQL <code>DATE</code> value,
     * which the driver then sends to the database. With a <code>Calendar</code>
     * object, the driver can calculate the date taking into account a custom
     * timezone. If no <code>Calendar</code> object is specified, the driver
     * uses the default timezone, which is that of the virtual machine running
     * the application.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @param cal
     *            the {@code Calendar} object the driver will use to construct
     *            the date
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setDate(String, java.sql.Date,
     *      java.util.Calendar)
     */
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setDate(indexes[i], x, cal);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.sql.Date} value
     * using the default time zone of the virtual machine that is running the
     * application. The driver converts this to an SQL {@code DATE} value when
     * it sends it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.sql.Date} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setDate(String, java.sql.Date)
     */
    public void setDate(String parameterName, Date x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setDate(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code double} value. The
     * driver converts this to an SQL {@code DOUBLE} value when it sends it to
     * the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code double} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setDouble(String, double)
     */
    public void setDouble(String parameterName, double x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setDouble(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code float} value. The
     * driver converts this to an SQL {@code REAL} value when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code double} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setFloat(String, float)
     */
    public void setFloat(String parameterName, float x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setFloat(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code int} value. The driver
     * converts this to an SQL {@code INTEGER} value when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code int} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setInt(String, int)
     */
    public void setInt(String parameterName, int x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setInt(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@code long} value. The driver
     * converts this to an SQL {@code BIGINT} value when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code double} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setLong(String, long)
     */
    public void setLong(String parameterName, long x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setLong(indexes[i], x);
	}
    }

    /**
     *Sets the designated parameter to a <code>Reader</code> object. The
     * <code>Reader</code> reads the data till end-of-file is reached. The
     * driver does the necessary conversion from Java character format to the
     * national character set in the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param value
     *            the {@link java.net.URL} object to be set
     * @param length
     *            the number of characters in the stream
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNCharacterStream(String,
     *      java.io.Reader, long)
     */
    public void setNCharacterStream(String parameterName, Reader value, long length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNCharacterStream(indexes[i], value, length);
	}
    }

    /**
     * Sets the designated parameter to a {@code Reader} object. The {@code
     * Reader} reads the data till end-of-file is reached. The driver does the
     * necessary conversion from Java character format to the national character
     * set in the database.
     * 
     * <P>
     * <B>Note:</B> This stream object can either be a standard Java stream
     * object or your own subclass that implements the standard interface.
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of
     * <code>setNCharacterStream</code> which takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param value
     *            the {@code Reader} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNCharacterStream(String,
     *      java.io.Reader)
     */
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNCharacterStream(indexes[i], value);
	}
    }

    /**
     * Sets the designated parameter to the given {@code java.sql.NClob} object.
     * The driver converts this to an SQL {@code NCLOB} value when it sends it
     * to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@code java.sql.NClob} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNClob(String, java.sql.NClob)
     */
    public void setNClob(String parameterName, NClob value) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNClob(indexes[i], value);
	}
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. The reader
     * must contain the number of characters specified by length otherwise a
     * <code>SQLException</code> will be generated when the
     * <code>PreparedStatement</code> is executed. This method differs from the
     * <code>setCharacterStream (int, Reader, int)</code> method because it
     * informs the driver that the parameter value should be sent to the server
     * as a <code>NCLOB</code>. When the <code>setCharacterStream</code> method
     * is used, the driver may have to do extra work to determine whether the
     * parameter data should be sent to the server as a
     * <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            An object that contains the data to set the parameter value
     *            to.
     * @param length
     *            the number of characters in the parameter data
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNClob(String, java.io.Reader, long)
     */
    public void setNClob(String parameterName, Reader reader, long length) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNClob(indexes[i], reader, length);
	}
    }

    /**
     * Sets the designated parameter to a <code>Reader</code> object. This
     * method differs from the <code>setCharacterStream (int, Reader)</code>
     * method because it informs the driver that the parameter value should be
     * sent to the server as a <code>NCLOB</code>. When the
     * <code>setCharacterStream</code> method is used, the driver may have to do
     * extra work to determine whether the parameter data should be sent to the
     * server as a <code>LONGNVARCHAR</code> or a <code>NCLOB</code>
     * <P>
     * <B>Note:</B> Consult your JDBC driver documentation to determine if it
     * might be more efficient to use a version of <code>setNClob</code> which
     * takes a length parameter.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param reader
     *            An object that contains the data to set the parameter value
     *            to.
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNClob(String, java.io.Reader)
     */
    public void setNClob(String parameterName, Reader reader) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNClob(indexes[i], reader);
	}
    }

    /**
     * Sets the designated parameter to the given {@code String} value. The
     * driver converts this to a SQL{@code NCHAR} or {@code NVARCHAR} or {@code
     * LONGNVARCHAR} value (depending on the argument's size relative to the
     * driver's limits on {@code NVARCHAR} values) when it sends it to the
     * database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNString(String, java.lang.String)
     */
    public void setNString(String parameterName, String value) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNString(indexes[i], value);
	}
    }

    /**
     * Sets the designated parameter to SQL <code>NULL</code>. This version of
     * the method <code>setNull</code> should be used for user-defined types and
     * REF type parameters. Examples of user-defined types include: STRUCT,
     * DISTINCT, JAVA_OBJECT, and named array types.
     * 
     * <P>
     * <B>Note:</B> To be portable, applications must give the SQL type code and
     * the fully-qualified SQL type name when specifying a NULL user-defined or
     * REF parameter. In the case of a user-defined type the name is the type
     * name of the parameter itself. For a REF parameter, the name is the type
     * name of the referenced type. If a JDBC driver does not need the type code
     * or type name information, it may ignore it.
     * 
     * Although it is intended for user-defined and Ref parameters, this method
     * may be used to set a null parameter of any JDBC type. If the parameter
     * does not have a user-defined or REF type, the given typeName is ignored.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param sqlType
     *            a value from java.sql.Types
     * @param typeName
     *            the fully-qualified name of an SQL user-defined type; ignored
     *            if the parameter is not a user-defined type or REF
     * @throws SQLException
     *             if an error occurred
     * @exception SQLFeatureNotSupportedException
     *                if <code>sqlType</code> is a <code>ARRAY</code>,
     *                <code>BLOB</code>, <code>CLOB</code>,
     *                <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                <code>NCHAR</code>, <code>NCLOB</code>,
     *                <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                or <code>STRUCT</code> data type and the JDBC driver does
     *                not support this data type or if the JDBC driver does not
     *                support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNull(String, int, java.lang.String)
     */
    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNull(indexes[i], sqlType, typeName);
	}
    }

    /**
     * Sets the designated parameter to SQL {@code NULL}.
     * <P>
     * <B>Note:</B> You must specify the parameter's SQL type.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param sqlType
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @exception SQLFeatureNotSupportedException
     *                if <code>sqlType</code> is a <code>ARRAY</code>,
     *                <code>BLOB</code>, <code>CLOB</code>,
     *                <code>DATALINK</code>, <code>JAVA_OBJECT</code>,
     *                <code>NCHAR</code>, <code>NCLOB</code>,
     *                <code>NVARCHAR</code>, <code>LONGNVARCHAR</code>,
     *                <code>REF</code>, <code>ROWID</code>, <code>SQLXML</code>
     *                or <code>STRUCT</code> data type and the JDBC driver does
     *                not support this data type
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setNull(String, int)
     */
    public void setNull(String parameterName, int sqlType) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNull(indexes[i], sqlType);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setObject(String, java.lang.Object, int,
     *      int)
     */
    public void setObject(String parameterName, Object x, int targetSqlType, int scaleOrLength)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setObject(indexes[i], x, targetSqlType, scaleOrLength);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setObject(String, java.lang.Object, int)
     */
    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setObject(indexes[i], x, targetSqlType);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setObject(String, java.lang.Object)
     */
    public void setObject(String parameterName, Object x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setObject(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setRef(String, java.sql.Ref)
     */
    public void setRef(String parameterName, Ref x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setRef(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setRowId(String, java.sql.RowId)
     */
    public void setRowId(String parameterName, RowId x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setRowId(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setShort(String, short)
     */
    public void setShort(String parameterName, short x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setShort(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setSQLXML(String, java.sql.SQLXML)
     */
    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setSQLXML(indexes[i], xmlObject);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setString(String, java.lang.String)
     */
    public void setString(String parameterName, String x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setString(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setTime(String, java.sql.Time,
     *      java.util.Calendar)
     */
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTime(indexes[i], x, cal);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setTime(String, java.sql.Time)
     */
    public void setTime(String parameterName, Time x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTime(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setTimestamp(String, java.sql.Timestamp,
     *      java.util.Calendar)
     */
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTimestamp(indexes[i], x, cal);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setTimestamp(String, java.sql.Timestamp)
     */
    public void setTimestamp(String parameterName, Timestamp x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTimestamp(indexes[i], x);
	}
    }

    /**
     * Sets the designated parameter to the given {@link java.net.URL} value.
     * The driver converts this to an SQL {@code DATALINK} value when it sends
     * it to the database.
     * 
     * @param parameterName
     *            the name of the parameter. for example, {@code :x, :y, :z}
     * @param x
     *            the {@link java.net.URL} object to be set
     * @throws SQLException
     *             if an error occurred
     * @throws SQLFeatureNotSupportedException
     *             if the JDBC driver does not support this method
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @since 1.2
     * @see java.sql.PreparedStatement#setURL(String, java.net.URL)
     */
    public void setURL(String parameterName, URL x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setURL(indexes[i], x);
	}
    }

}
