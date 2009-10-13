package com.chris.utils.dbutils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestDBUtils
{
    class MavenData
    {
	String rawText = "";
	String groupID = "";
	String artifactID = "";
	String artifactType = "";
	String artifactVersion = "";
	String scope = "";
	String classifier = "";

	public MavenData(String line) throws IllegalArgumentException, MalformedPatternException
	{
	    String regex = RegexUtils.A_MVN_LINE;

	    // Initialize the pattern
	    Pattern pattern = RegexUtils.initPattern(regex);
	    PatternMatcherInput input = new PatternMatcherInput(line);

	    PatternMatcher matcher = new Perl5Matcher();
	    MatchResult result;

	    if (!matcher.matches(input, pattern))
	    {
		throw new InvalidParameterException(String.format(
		    "Unable to parse \"%1$s\". Invalid Maven output line.", line));
	    }
	    else
	    {
		result = matcher.getMatch();
		rawText = line;
		outputResultGroup(0, result);
		groupID = result.group(1);
		outputResultGroup(1, result);
		if (result.group(3) != null && !result.group(3).isEmpty())
		{
		    artifactID = result.group(3);
		    outputResultGroup(3, result);
		    artifactType = result.group(4);
		    outputResultGroup(4, result);
		    artifactVersion = result.group(12);
		    outputResultGroup(12, result);
		}
		else if (result.group(5) != null && !result.group(5).isEmpty())
		{
		    artifactID = result.group(5);
		    outputResultGroup(5, result);
		    artifactType = result.group(6);
		    outputResultGroup(6, result);
		    artifactVersion = result.group(7);
		    outputResultGroup(7, result);
		    scope = result.group(12);
		    outputResultGroup(12, result);
		}
		else if (result.group(8) != null && !result.group(8).isEmpty())
		{
		    artifactID = result.group(8);
		    outputResultGroup(8, result);
		    artifactType = result.group(9);
		    outputResultGroup(9, result);
		    artifactVersion = result.group(10);
		    outputResultGroup(10, result);
		    scope = result.group(11);
		    outputResultGroup(11, result);
		    classifier = result.group(12);
		    outputResultGroup(12, result);
		}
	    }
	}

	/**
	 * Output the group contents.
	 * 
	 * @param group
	 *            Pattern subgroup, Group 0 always refers to the entire
	 *            match
	 * @param result
	 *            PatternMatch result
	 */
	private void outputResultGroup(int group, MatchResult result)
	{
	    LOGGER.debug("Group ({}): \"{}\"", group, result.group(group));
	}

	/**
	 * @return the rawText
	 */
	public String getRawText()
	{
	    return rawText;
	}

	/**
	 * @return the groupID
	 */
	public String getGroupID()
	{
	    return groupID;
	}

	/**
	 * @return the artifactID
	 */
	public String getArtifactID()
	{
	    return artifactID;
	}

	/**
	 * @return the artifactType
	 */
	public String getArtifactType()
	{
	    return artifactType;
	}

	/**
	 * @return the artifactVersion
	 */
	public String getArtifactVersion()
	{
	    return artifactVersion;
	}

	/**
	 * @return the scope
	 */
	public String getScope()
	{
	    return scope;
	}

	/**
	 * @return the classifier
	 */
	public String getClassifier()
	{
	    return classifier;
	}
    }

    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDBUtils.class);

    /**
     * Test Oracle JDBC Driver
     */
    private static final String PG_DRIVER = "org.postgresql.Driver";

    /**
     * Test PostgreSQL Host
     */
    private static final String PG_HOST = "localhost";

    /**
     * Test PostgreSQL Port
     */
    private static final String PG_PORT = "5433";

    /**
     * Test PostgreSQL Database
     */
    private static final String PG_DATABASE = "postgres";

    /**
     * Test PostgreSQL User Name
     */
    private static final String PG_USERNAME = "postgres";

    /**
     * Test PostgreSQL Password
     */
    private static final String PG_PASSWORD = "GoodDog1";

    /**
     * Test PostgreSQL Connection URL.
     * <p>
     * Possible connection URL forms are:
     * <ul>
     * <li>jdbc:postgresql:database</li>
     * <li>jdbc:postgresql://host[:port]/database</li>
     * </ul>
     */
    private static final String PG_CONNECTION_URL =
	    String.format("jdbc:postgresql://%1$s:%2$s/%3$s", PG_HOST, PG_PORT, PG_DATABASE);

    /**
     * Test Oracle JDBC Driver
     */
    private static final String ORCL_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * Test Oracle Host
     */
    private static final String ORCL_HOST = "daytongis";

    /**
     * Test Oracle Port
     */
    private static final String ORCL_PORT = "1521";

    /**
     * Test Oracle Database
     */
    private static final String ORCL_DATABASE = "devgis";

    /**
     * Test Oracle User Name
     */
    private static final String ORCL_USERNAME = "geodb";

    /**
     * Test Oracle Password
     */
    private static final String ORCL_PASSWORD = "geodb";

    /**
     * Test Oracle Connection URL. Note that both the ":" and "@" characters are
     * necessary.
     * <p>
     * Possible connection URL forms are:
     * <ul>
     * <li>Oracle Thin Client</li>
     * <ul>
     * <li>
     * <code>jdbc:oracle:thin:[user/password]@[host][:port]:SID
     * </code></li>
     * <li>
     * <code>jdbc:oracle:thin:[user/password]@//[host][:port]/SID
     * </code>
     * </li>
     * </ul>
     * <li>
     * Oracle OCI
     * <ul>
     * <li><code>jdbc:oracle:oci:[user/password]@TNSNames_Entry
     * </code></li>
     * </ul>
     * </li>
     * <li>Oracle Net keyword-value pair
     * <ul>
     * <li>
     * <code>
     * jdbc:oracle:(thin|oci|oci8):[user/password]@(DESCRIPTION=
     *                        (ADDRESS_LIST= 
     *                            (ADDRESS=(PROTOCOL=TCP)  
     *                                     (HOST=host)
     *                                     (PORT=port)
     *                            )
     *                        )
     *                        (CONNECT_DATA=
     *                            (SERVICE_NAME=SID)
     *                            (SERVER=DEDICATED)
     *                        )
     *                      )
     * </code></li>
     * </ul>
     * </li>
     * </ul>
     * <ul>
     * <li>user - The login user name defined in the Oracle server.
     * <ul>
     * <li>Optional</li></li>
     * </ul>
     * <li>password - The password for the login user.
     * <ul>
     * <li>Optional</li>
     * </ul>
     * </li>
     * <li>host - The host name where Oracle server is running.
     * <ul>
     * <li>Optional</li>
     * <li>Default - 127.0.0.1 - IP address of localhost</li>
     * </ul>
     * </li>
     * <li>port - The port number where Oracle is listening for connection.
     * <ul>
     * <li>Optional</li>
     * <li>Default - 1521</li>
     * </ul>
     * </li>
     * <li>SID - System ID of the Oracle server database instance.
     * <ul>
     * <li>Required*</li>
     * <li>By default, Oracle Database 10g Express Edition creates one database
     * called XE.</li>
     * </ul>
     * </li>
     * <li>TNSNames_Entry - TNSNAMES entry of the Oracle database instance.
     * <ul>
     * <li>Required*</li>
     * <li>You can find the available TNSNAMES entries listed in the file
     * tnsnames.ora on the client computer from which you are connecting.</li>
     * </ul>
     * </li>
     * <li>* This is less readable than a TNSNAMES entry but does not depend on
     * the accuracy of the TNSNAMES.ORA file. The Oracle Net keyword-value pair
     * works with all of the Oracle JDBC drivers.</li> </ul>
     */
    private static final String ORCL_CONNECTION_URL =
	    String.format("jdbc:oracle:thin:@%1$s:%2$s:%3$s", ORCL_HOST, ORCL_PORT, ORCL_DATABASE);

    /**
     * Common DataSource
     */
    private DataSource ds;

    @Before
    public void setUp() throws Exception
    {
	ds = DBUtils.setupPostgreSQLDataSource(PG_USERNAME, PG_PASSWORD, PG_CONNECTION_URL);
    }

    @After
    public void tearDown() throws Exception
    {
	DBUtils.shutdownDataSource(ds);
    }

    @Ignore
    @Test
    public void testDataSource()
    {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rset = null;
	int numcols = 0;
	try
	{
	    LOGGER.debug("Creating connection");
	    conn = ds.getConnection();
	    conn.setCatalog("public");
	    LOGGER.debug("Creating statement");
	    stmt = conn.createStatement();
	    LOGGER.debug("Executing statement");
	    rset = stmt.executeQuery("SELECT * FROM mvn_data");
	    // rset = conn.getMetaData().getTables(null, null, null, null);
	    LOGGER.debug("Results:");
	    numcols = rset.getMetaData().getColumnCount();
	    while (rset.next())
	    {
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 1; i <= numcols; i++)
		{
		    sBuilder.append("\t" + rset.getString(i));
		}
		LOGGER.info(sBuilder.toString());
	    }
	}
	catch (SQLException e)
	{
	    fail(e.toString());
	}
	finally
	{
	    try
	    {
		rset.close();
	    }
	    catch (Exception e)
	    {
	    }
	    try
	    {
		stmt.close();
	    }
	    catch (Exception e)
	    {
	    }
	    try
	    {
		conn.close();
	    }
	    catch (Exception e)
	    {
	    }
	}
	assertTrue("No columns exist in the table", numcols > 0);
    }

    @Test
    public void testLoadMVNData() throws MalformedPatternException
    {
	Connection conn = null;
	PreparedStatement stmt = null;

	try
	{
	    LOGGER.debug("Creating connection");
	    conn = ds.getConnection();
	    conn.setCatalog("public");
	    LOGGER.debug("Creating statement");

	    String sql =
		    "INSERT INTO mvn_data(raw_text, group_id, artifact_id, artifact_type, "
			    + "artifact_version, scope, classifier) "
			    + "VALUES (?, ?, ?, ?, ?, ?, ?);";

	    stmt = conn.prepareStatement(sql);

	    // Get the input text file
	    TextFileIn txtFile = new TextFileIn("deptree.txt");
	    String myLine;
	    MavenData data;
	    while ((myLine = txtFile.readLine()) != null)
	    {
		try
		{
		    data = new MavenData(myLine);
		    stmt.setString(1, data.getRawText());
		    stmt.setString(2, data.getGroupID());
		    stmt.setString(3, data.getArtifactID());
		    stmt.setString(4, data.getArtifactType());
		    stmt.setString(5, data.getArtifactVersion());
		    stmt.setString(6, data.getScope());
		    stmt.setString(7, data.getClassifier());
		    // Add the Prepared statement to the batch
		    stmt.addBatch();
		}
		catch (SQLException e)
		{
		    LOGGER.debug("SQL Error: " + e.getLocalizedMessage());
		}
		catch (IllegalArgumentException e)
		{
		    LOGGER.debug("Unable to parse line: " + myLine);
		}
		catch (Exception e)
		{
		    LOGGER.debug("Error: " + e.getLocalizedMessage());
		}
		finally
		{
		    // Clear the old parameters
		    stmt.clearParameters();
		}
	    }
	    int[] results = stmt.executeBatch();
	    LOGGER.debug("Batch Statements Executed: " + results.length);
	}
	catch (SQLException e)
	{
	    LOGGER.error("SQL Exception", e);
	    fail(e.getLocalizedMessage());
	}
	catch (Exception e)
	{
	    LOGGER.error("Exception", e);
	    fail(e.getLocalizedMessage());
	}
	finally
	{
	    try
	    {
		stmt.close();
	    }
	    catch (Exception e)
	    {
	    }
	    try
	    {
		conn.close();
	    }
	    catch (Exception e)
	    {
	    }
	}
    }

    @Ignore
    @Test
    public void testPrintDataSourceStats()
    {
	try
	{
	    DBUtils.printDataSourceStats(ds);
	}
	catch (Exception e)
	{
	    LOGGER.error("Unable to print DataSource stats.", e);
	    fail(e.toString());
	}
    }

    @Ignore("Oracle Drivers need to be in local repository")
    public void testSetupOracleDataSource()
    {
	ds =
		DBUtils.setupOracleDataSource(DBConstant.ORCL_USERNAME, DBConstant.ORCL_PASSWORD,
		    DBConstant.ORCL_CONNECTION_URL);
	Connection conn = null;
	try
	{
	    conn = ds.getConnection();
	    assertTrue("Connection is closed", conn != null && !conn.isClosed());
	}
	catch (SQLException e)
	{
	    LOGGER.error("Connection to Oracle DataSource failed.", e);
	    fail(e.toString());
	}
	finally
	{
	    try
	    {
		conn.close();
	    }
	    catch (SQLException e)
	    {
	    }
	    finally
	    {
		conn = null;
	    }
	}
    }

    @Ignore
    @Test
    public void testSetupPostgreSQLDataSource()
    {
	ds =
		DBUtils.setupPostgreSQLDataSource(DBConstant.PG_USERNAME, DBConstant.PG_PASSWORD,
		    DBConstant.PG_CONNECTION_URL);
	Connection conn = null;
	try
	{
	    conn = ds.getConnection();
	    assertTrue("Connection is closed", conn != null && !conn.isClosed());
	}
	catch (SQLException e)
	{
	    LOGGER.error("Connection to PostgreSQL DataSource failed.", e);
	    fail(e.toString());
	}
	finally
	{
	    try
	    {
		conn.close();
	    }
	    catch (SQLException e)
	    {
	    }
	    finally
	    {
		conn = null;
	    }
	}
    }
}
