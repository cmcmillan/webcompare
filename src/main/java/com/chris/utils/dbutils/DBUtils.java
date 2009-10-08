package com.chris.utils.dbutils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmts
     *            List of SQL {@link Statement}s used in querying the database
     *            on conn
     * @param results
     *            List of {@link ResultSet} objects returned by the SQL
     *            Statement
     */
    public static final void cleanupConnections(Connection conn, List<Statement> stmts,
	    List<ResultSet> results)
    {
	closeResultSets(results);
	closeStatements(stmts);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmts
     *            List of SQL {@link Statement}s used in querying the database
     *            on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, List<Statement> stmts,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmts);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmt
     *            SQL {@link NamedParameterStatement} used in querying the
     *            database on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, NamedParameterStatement stmt,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmts
     *            SQL {@link NamedParameterStatement}s used in querying the
     *            database on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, NamedParameterStatement[] stmts,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmts);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmt
     *            SQL {@link PreparedStatement} used in querying the database on
     *            conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, PreparedStatement stmt,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmts
     *            SQL {@link PreparedStatement}s used in querying the database
     *            on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, PreparedStatement[] stmts,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmts);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmt
     *            SQL {@link Statement} used in querying the database on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, Statement stmt,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
	closeConnections(conn);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param conn
     *            {@link Connection} to the database
     * @param stmts
     *            SQL {@link Statement}s used in querying the database on conn
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void cleanupConnections(Connection conn, Statement[] stmts,
	    ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmts);
	closeConnections(conn);
    }

    /**
     * Close all of the connections as part of cleanup
     * 
     * @param connections
     *            List of {@link Connection} objects that need to be closed
     *            during cleanup
     */
    public static final void closeConnections(Connection... connections)
    {
	try
	{
	    // Close all of the {@link Connection}
	    for (Connection connection : connections)
	    {
		connection.close();
	    }
	}
	catch (Exception e)
	{
	}
    }

    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link NamedParameterStatement} objects that need to
     *            be closed during cleanup
     */
    public static final void closeNamedParameterStatements(List<NamedParameterStatement> statements)
    {
	if (statements != null && statements.size() > 0)
	{
	    NamedParameterStatement[] x = new NamedParameterStatement[statements.size()];
	    int i = 0;
	    for (NamedParameterStatement statement : statements)
	    {
		x[i++] = statement;
	    }
	    closeStatements(x);
	}
    }

    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link PreparedStatement} objects that need to be
     *            closed during cleanup
     */
    public static final void closePreparedStatements(List<PreparedStatement> statements)
    {
	if (statements != null && statements.size() > 0)
	{
	    PreparedStatement[] x = new PreparedStatement[statements.size()];
	    int i = 0;
	    for (PreparedStatement statement : statements)
	    {
		x[i++] = statement;
	    }
	    closeStatements(x);
	}
    }

    /**
     * Close result sets as part of cleanup of {@link Connection} closing
     * 
     * @param resultSets
     *            List of {@link ResultSet} objects that need to be closed
     *            during cleanup
     */
    public static final void closeResultSets(List<ResultSet> resultSets)
    {
	if (resultSets != null && resultSets.size() > 0)
	{
	    ResultSet[] x = new ResultSet[resultSets.size()];
	    int i = 0;
	    for (ResultSet statement : resultSets)
	    {
		x[i++] = statement;
	    }
	    closeResultSets(x);
	}
    }

    /**
     * Close result sets as part of cleanup of {@link Connection} closing
     * 
     * @param resultSets
     *            List of {@link ResultSet} objects that need to be closed
     *            during cleanup
     */
    public static final void closeResultSets(ResultSet... resultSets)
    {
	try
	{
	    // Close all {@link ResultSets}
	    for (ResultSet rSet : resultSets)
	    {
		rSet.close();
	    }
	}
	catch (Exception e)
	{
	}
    }

    /**
     * Close the statement and results for the cleanup in the finally section of
     * a SQL query section
     * 
     * @param stmt
     *            SQL {@link NamedParameterStatement} used in querying the
     *            database
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void closeStatement(NamedParameterStatement stmt, ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param stmt
     *            SQL {@link PreparedStatement} used in querying the database
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void closeStatement(PreparedStatement stmt, ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
    }

    /**
     * Close the connections for the cleanup in the finally section of a SQL
     * query section
     * 
     * @param stmt
     *            SQL {@link Statement} used in querying the database
     * @param results
     *            Optional array of {@link ResultSet} objects returned by the
     *            SQL Statement
     */
    public static final void closeStatement(Statement stmt, ResultSet... results)
    {
	closeResultSets(results);
	closeStatements(stmt);
    }

    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link Statement} objects that need to be closed
     *            during cleanup
     */
    public static final void closeStatements(List<Statement> statements)
    {
	if (statements != null && statements.size() > 0)
	{
	    Statement[] x = new Statement[statements.size()];
	    int i = 0;
	    for (Statement statement : statements)
	    {
		x[i++] = statement;
	    }
	    closeStatements(x);
	}
    }



    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link NamedParameterStatement} objects that need to
     *            be closed during cleanup
     */
    public static final void closeStatements(NamedParameterStatement... statements)
    {
	try
	{
	    // Close all of the {@link NamedParameterStatement}
	    for (NamedParameterStatement statement : statements)
	    {
		statement.close();
	    }
	}
	catch (Exception e)
	{
	}
    }


    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link PreparedStatement} objects that need to be
     *            closed during cleanup
     */
    public static final void closeStatements(PreparedStatement... statements)
    {
	try
	{
	    // Close all of the {@link ParameterStatement}
	    for (PreparedStatement statement : statements)
	    {
		statement.close();
	    }
	}
	catch (Exception e)
	{
	}
    }

    /**
     * Close statements as part of cleanup of {@link Connection} closing
     * 
     * @param statements
     *            List of {@link Statement} objects that need to be closed
     *            during cleanup
     */
    public static final void closeStatements(Statement... statements)
    {
	try
	{
	    // Close all of the {@link ParameterStatement}
	    for (Statement statement : statements)
	    {
		statement.close();
	    }
	}
	catch (Exception e)
	{
	}
    }

    /**
     * Print basic information about the {@link DataSource}
     * 
     * @param ds
     *            {@link DataSource} to use
     * @throws SQLException
     *             if a database error occurs
     */
    public static void printDataSourceStats(DataSource ds) throws SQLException
    {
	// LOGGER formatting does not seem to work
	LOGGER.info("--------------------------------");
	BasicDataSource bds = (BasicDataSource) ds;
	// Output the Driver
	LOGGER.debug(String.format("Driver Class Name: %0$s", bds.getDriverClassName()));
	// Output the User Name
	LOGGER.debug(String.format("Username: %0$s", bds.getUsername()));
	// Output the Connection URI
	LOGGER.info(String.format("Connection URI: %0$s", bds.getUrl()));
	// Output the Number of Active Connections
	LOGGER.info(String.format("NumActive: %0$s", bds.getNumActive()));
	// Output the Number of Idle Connections
	LOGGER.info(String.format("NumIdle: %0$d", bds.getNumIdle()));
	LOGGER.info("--------------------------------");
    }

    /**
     * Setup a {@link DataSource} using the provided parameters
     * 
     * @param userName
     *            The connection user name to be passed to the JDBC driver to
     *            establish a connection.
     * @param password
     *            The connection password to be passed to the JDBC driver to
     *            establish a connection.
     * @param connectURI
     *            The connection URL to be passed to the JDBC driver to
     *            establish a connection.
     * @return {@link DataSource} using the provided parameters
     */
    public static DataSource setupDataSource(String driverClassName, String userName,
	    String password, String connectURI)
    {
	BasicDataSource ds = new BasicDataSource();
	// LOGGER.debug(String.format("Driver Class Name: %0$s",
	// bds.getDriverClassName()));
	ds.setDriverClassName(driverClassName);
	// LOGGER.debug(String.format("Username: %0$s", bds.getUsername()));
	ds.setUsername(userName);
	// LOGGER.debug(String.format("Password: %0$s", bds.getPassword()));
	ds.setPassword(password);
	// LOGGER.debug(String.format("Connection URI: %0$s", bds.getUrl()));
	ds.setUrl(connectURI);
	return ds;
    }

    /**
     * Setup an Oracle {@link DataSource}
     * 
     * @param userName
     *            The connection user name to be passed to the JDBC driver to
     *            establish a connection.
     * @param password
     *            The connection password to be passed to the JDBC driver to
     *            establish a connection.
     * @param connectURI
     *            The connection URL to be passed to the JDBC driver to
     *            establish a connection.
     * @return {@link DataSource} using the Oracle JDBC driver and the provided
     *         inputs
     */
    public static DataSource setupOracleDataSource(String userName, String password,
	    String connectURI)
    {
	return setupDataSource("oracle.jdbc.driver.OracleDriver", userName, password, connectURI);
    }

    /**
     * Setup a PostgreSQL {@link DataSource}
     * 
     * @param userName
     *            The connection user name to be passed to the JDBC driver to
     *            establish a connection.
     * @param password
     *            The connection password to be passed to the JDBC driver to
     *            establish a connection.
     * @param connectURI
     *            The connection URL to be passed to the JDBC driver to
     *            establish a connection.
     * @return {@link DataSource} using the PostgreSQL JDBC driver and the
     *         provided inputs
     */
    public static DataSource setupPostgreSQLDataSource(String userName, String password,
	    String connectURI)
    {
	return setupDataSource("org.postgresql.Driver", userName, password, connectURI);
    }

    /**
     * Close and release all connections that are currently stored in the
     * connection pool associated with the data source.
     * 
     * @param ds
     *            {@link DataSource} to close
     * @throws SQLException
     *             if a database error occurs
     */
    public static void shutdownDataSource(DataSource ds) throws SQLException
    {
	BasicDataSource bds = (BasicDataSource) ds;
	bds.close();
    }
}
