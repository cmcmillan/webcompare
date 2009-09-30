package com.chris.utils.dbutils;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DBUtils
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);

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
	LOGGER.debug("Driver Class Name: " + driverClassName);
	ds.setDriverClassName(driverClassName);
	LOGGER.debug("Username: " + userName);
	ds.setUsername(userName);
	LOGGER.debug("Password: " + password);
	ds.setPassword(password);
	LOGGER.debug("Connect URI: " + connectURI);
	ds.setUrl(connectURI);
	return ds;
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
	StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	if (stackTrace.length > 0)
	{
	    LOGGER.debug("%0$s : %1$d : %2$s", new Object[] { stackTrace[0].getFileName(),
		    stackTrace[0].getLineNumber(), stackTrace[0].getMethodName() });
	}
	LOGGER.info("--------------------------------");
	BasicDataSource bds = (BasicDataSource) ds;
	// Output the Driver
	LOGGER.debug("Driver Class Name: %0$s", bds.getDriverClassName());
	// Output the User Name
	LOGGER.debug("Username: %0$s", bds.getUsername());
	// Output the Connection URI
	LOGGER.info("Connection URI: %0$s", bds.getUrl());
	// Output the Number of Active Connections
	LOGGER.info("NumActive: %0$s", Integer.valueOf(bds.getNumActive()));
	// Output the Number of Idle Connections
	LOGGER.info("NumIdle: %0$d", (Object) Integer.valueOf(bds.getNumIdle()));
	LOGGER.info("--------------------------------");
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
