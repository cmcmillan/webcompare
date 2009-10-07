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
        int length=query.length();
        StringBuffer parsedQuery=new StringBuffer(length);
        boolean inSingleQuote=false;
        boolean inDoubleQuote=false;
        int index=1;

        for(int i=0;i<length;i++) {
            char c=query.charAt(i);
            if(inSingleQuote) {
                if(c=='\'') {
                    inSingleQuote=false;
                }
            } else if(inDoubleQuote) {
                if(c=='"') {
                    inDoubleQuote=false;
                }
            } else {
                if(c=='\'') {
                    inSingleQuote=true;
                } else if(c=='"') {
                    inDoubleQuote=true;
                } else if(c==':' && i+1<length &&
                        Character.isJavaIdentifierStart(query.charAt(i+1))) {
                    int j=i+2;
                    while(j<length && Character.isJavaIdentifierPart(query.charAt(j))) {
                        j++;
                    }
                    String name=query.substring(i+1,j);
                    c='?'; // replace the parameter with a question mark
                    i+=name.length(); // skip past the end if the parameter

		    List<Integer> indexList = paramMap.get(name);
                    if(indexList==null) {
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
     * @param name
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     * java.io.InputStream, int)
     */
    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setAsciiStream(indexes[i], x, length);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     * java.io.InputStream, long)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setAsciiStream(String,
     * java.io.InputStream)
     */
    public void setAsciiStream(String parameterName, InputStream x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setAsciiStream(indexes[i], x);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setBigDecimal(String,
     * java.math.BigDecimal)
     */
    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBigDecimal(indexes[i], x);
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     * java.io.InputStream, int)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     * java.io.InputStream, long)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setBinaryStream(String,
     * java.io.InputStream)
     */
    public void setBinaryStream(String parameterName, InputStream x) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setBinaryStream(indexes[i], x);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setBlob(String, java.io.InputStream,
     * long)
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     * java.io.Reader, int)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     * java.io.Reader, long)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setCharacterStream(String,
     * java.io.Reader)
     */
    public void setCharacterStream(String parameterName, Reader reader) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setCharacterStream(indexes[i], reader);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setDate(String, java.sql.Date,
     * java.util.Calendar)
     */
    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setDate(indexes[i], x, cal);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setNCharacterStream(String,
     * java.io.Reader, long)
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setNCharacterStream(String,
     * java.io.Reader)
     */
    public void setNCharacterStream(String parameterName, Reader value) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setNCharacterStream(indexes[i], value);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setObject(String, java.lang.Object, int,
     * int)
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setTime(String, java.sql.Time,
     * java.util.Calendar)
     */
    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTime(indexes[i], x, cal);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setTimestamp(String, java.sql.Timestamp,
     * java.util.Calendar)
     */
    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setTimestamp(indexes[i], x, cal);
	}
    }

    /*
     * (non-Javadoc)
     * 
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

    /*
     * (non-Javadoc)
     * 
     * @see java.sql.PreparedStatement#setUnicodeStream(String,
     * java.io.InputStream, int)
     */
    public void setUnicodeStream(String parameterName, InputStream x, int length)
	    throws SQLException
    {
	Integer[] indexes = getIndexes(parameterName);
	for (int i = 0; i < indexes.length; i++)
	{
	    setUnicodeStream(indexes[i], x, length);
	}
    }

    /*
     * (non-Javadoc)
     * 
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
