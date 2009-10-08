package com.chris.utils.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class wraps around a {@link PreparedStatement} and allows the programmer
 * to set parameters by name instead of by index. This eliminates any confusion
 * as to which parameter index represents what. This also means that rearranging
 * the SQL statement or adding a parameter doesn't involve renumbering your
 * indices. Code such as this:
 * 
 * <pre>
 * <code>
 * Connection con = getConnection();
 * String query = &quot;select * from my_table where name=? or address=?&quot;;
 * PreparedStatement p = con.prepareStatement(query);
 * p.setString(1, &quot;bob&quot;);
 * p.setString(2, &quot;123 terrace ct&quot;);
 * ResultSet rs = p.executeQuery();
 * </code>
 * </pre>
 * 
 * can be replaced with:
 * 
 * <pre>
 * <code>
 * Connection con=getConnection(); 
 * String query="select * from my_table
 * where name=:name or address=:address"; 
 * NamedParameterStatement p = new NamedParameterStatement(con, query); 
 * p.setString("name", "bob");
 * p.setString("address", "123 terrace ct");
 * ResultSet rs=p.executeQuery();
 * </code>
 * 
 * <pre>
 * 
 * @since 1.0 - 03 April 2007 - Adam Crume - Original Java World article
 * @since 1.1 - 07 September 2009 - Chris McMillan - Updated to use generics
 * @since 1.2 - 08 September 2009 - Chris McMillan - Filled out overloads of PreparedStatement 
 * 
 * @version 1.2
 * 
 * @author Adam Crume, JavaWorld.com
 * @author Chris McMillan, Ball Aerospace
 * 
 * @see <a href="http://www.javaworld.com/javaworld/jw-04-2007/jw-04-jdbc.html">Named Parameters for PreparedStatement</a>
 * {@link http://www.javaworld.com/javaworld/jw-04-2007/jw-04-jdbc.html}
 * 
 */
public class NamedParameterStatement
{
    /** The statement this object is wrapping. */
    private final PreparedStatement statement;

    /**
     * Maps parameter names to arrays of Integers which are the parameter
     * indices.
     * 
     * @since 07 Sept 2009 - Updated to use generics - Chris McMillan
     */
    private final Map<String, List<Integer>> indexMap;

    /**
     * Creates a NamedParameterStatement. Wraps a call to c.
     * {@link Connection#prepareStatement(java.lang.String) prepareStatement}.
     * 
     * @param connection
     *            the database connection
     * @param query
     *            the parameterized query
     * @throws SQLException
     *             if the statement could not be created
     * @since 07 September 2009 - Chris McMillan - Updated to use generics
     */
    public NamedParameterStatement(Connection connection, String query) throws SQLException
    {
	indexMap = new HashMap<String, List<Integer>>();
	String parsedQuery = parse(query, indexMap);
	statement = connection.prepareStatement(parsedQuery);
    }

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

    /**
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @see PreparedStatement#setObject(int, java.lang.Object)
     */
    public void setObject(String name, Object value) throws SQLException
    {
	Integer[] indexes = getIndexes(name);
	for (int i = 0; i < indexes.length; i++)
	{
	    statement.setObject(indexes[i], value);
	}
    }

    /**
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @see PreparedStatement#setString(int, java.lang.String)
     */
    public void setString(String name, String value) throws SQLException
    {
	Integer[] indexes = getIndexes(name);
	for (int i = 0; i < indexes.length; i++)
	{
	    statement.setString(indexes[i], value);
	}
    }

    /**
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setInt(String name, int value) throws SQLException
    {
	Integer[] indexes = getIndexes(name);
	for (int i = 0; i < indexes.length; i++)
	{
	    statement.setInt(indexes[i], value);
	}
    }

    /**
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @see PreparedStatement#setInt(int, int)
     */
    public void setLong(String name, long value) throws SQLException
    {
	Integer[] indexes = getIndexes(name);
	for (int i = 0; i < indexes.length; i++)
	{
	    statement.setLong(indexes[i], value);
	}
    }

    /**
     * Sets a parameter.
     * 
     * @param name
     *            parameter name
     * @param value
     *            parameter value
     * @throws SQLException
     *             if an error occurred
     * @throws IllegalArgumentException
     *             if the parameter does not exist
     * @see PreparedStatement#setTimestamp(int, java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException
{
	Integer[] indexes = getIndexes(name);
	for (int i = 0; i < indexes.length; i++)
	{
	    statement.setTimestamp(indexes[i], value);
	}
    }

    /**
     * Returns the underlying statement.
     * 
     * @return the statement
     */
    public PreparedStatement getStatement()
    {
	return statement;
    }


    /**
     * Executes the statement.
     * 
     * @return true if the first result is a {@link ResultSet}
     * @throws SQLException
     *             if an error occurred
     * @see PreparedStatement#execute()
     */
    public boolean execute() throws SQLException
    {
	return statement.execute();
    }

    /**
     * Executes the statement, which must be a query.
     * 
     * @return the query results
     * @throws SQLException
     *             if an error occurred
     * @see PreparedStatement#executeQuery()
     */
    public ResultSet executeQuery() throws SQLException
    {
	return statement.executeQuery();
    }

    /**
     * Executes the statement, which must be an SQL INSERT, UPDATE or DELETE
     * statement; or an SQL statement that returns nothing, such as a DDL
     * statement.
     * 
     * @return number of rows affected
     * @throws SQLException
     *             if an error occurred
     * @see PreparedStatement#executeUpdate()
     */
    public int executeUpdate() throws SQLException
    {
	return statement.executeUpdate();
    }

    /**
     * Closes the statement.
     * 
     * @throws SQLException
     *             if an error occurred
     * @see Statement#close()
     */
    public void close() throws SQLException
    {
	statement.close();
    }

    /**
     * Adds the current set of parameters as a batch entry.
     * 
     * @throws SQLException
     *             if something went wrong
     */
    public void addBatch() throws SQLException
    {
	statement.addBatch();
    }

    /**
     * Executes all of the batched statements.
     * 
     * See {@link Statement#executeBatch()} for details.
     * 
     * @return update counts for each statement
     * @throws SQLException
     *             if something went wrong
     */
    public int[] executeBatch() throws SQLException
    {
	return statement.executeBatch();
    }
}

