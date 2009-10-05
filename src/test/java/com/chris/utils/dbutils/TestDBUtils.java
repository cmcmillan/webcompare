package com.chris.utils.dbutils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.utils.text.TextFileIn;
import com.chris.utils.text.regex.RegexUtils;

public class TestDBUtils
{
    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDBUtils.class);

    /**
     * Common DataSource
     */
    private DataSource ds;

    @Before
    public void setUp() throws Exception
    {
	ds =
		DBUtils.setupPostgreSQLDataSource(DBConstant.PG_USERNAME, DBConstant.PG_PASSWORD,
		    DBConstant.PG_CONNECTION_URL);
    }

    @After
    public void tearDown() throws Exception
    {
	DBUtils.shutdownDataSource(ds);
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

    public void testLoadMVNData() throws MalformedPatternException
    {
	String regex = ".*-(([^:]*):([^:]*):([^:]*):([^:])*:([^:]*)$)";
	String inputString = "";
	Pattern pattern = RegexUtils.initPattern(regex);// Pattern.compile(regex);
	PatternMatcherInput input = new PatternMatcherInput(inputString);// pattern.matcher(input);

	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rset = null;

	try
	{
	    LOGGER.debug("Creating connection");
	    conn = ds.getConnection();
	    conn.setCatalog("public");
	    LOGGER.debug("Creating statement");

	    String sql =
		    "INSERT INTO mvn_data( raw_text, group_id, artifact_id, artifact_type, "
			    + "artifact_version, scope, classifier) "
			    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

	    stmt = conn.prepareStatement(sql);

	    // Get the input text file
	    TextFileIn txtFile = new TextFileIn("deptree.txt");
	    String myLine;
	    String trimmedGroup;
	    while ((myLine = txtFile.readLine()) != null)
	    {
		PatternMatcher matcher = new Perl5Matcher();
		while (matcher.contains(input, pattern))
		{
		    MatchResult result = matcher.getMatch();
		    // Clear out any parameters from last time
		    stmt.clearParameters();
		    // Add the raw input string for in case we need it later
		    stmt.setNString(0, myLine);
		    // is it a dependency? yes, assuming the Regex is correct
		    // and Maven doesn't throw weirdness in
		    stmt.setBoolean(1, true);
		    for (int j = 1; j <= result.groups(); j++)
		    {
			trimmedGroup = result.group(j).trim();
			LOGGER.debug("Group {}: {}", j, trimmedGroup);

			// Populate the remaining parameters
			switch (j)
			{
			    case 1:
				// GroupId which corresponds
				stmt.setNString(j, trimmedGroup);
			    case 2:
				// Trim version of the main group
				stmt.setNString(j, trimmedGroup);
			    case 3:
				// Trim version of the main group
				stmt.setNString(j, trimmedGroup);
			    case 4:
				// Packaging of the dependency
				stmt.setNString(j, trimmedGroup);
			    case 5:
				// Packaging
				stmt.setNString(j, trimmedGroup);
			    case 6:
				// Phase of the main group
				stmt.setNString(j, trimmedGroup);
			    default:
				break;
			}
		    }
		    // Add the parameter set to batch
		    stmt.addBatch();
		}
	    }
	}
	catch (Exception e)
	{
	    fail(e.getLocalizedMessage());
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
    }

    class MavenData
    {
	String rawText;
	String groupID;
	String artifactID;
	String artifactType;
	String artifactVersion;
	String scope;
	String classifier = "";

	/**
	 * 
	 */
	public MavenData()
	{
	    rawText = "";
	    groupID = "";
	    artifactID = "";
	    artifactType = "";
	    artifactVersion = "";
	    scope = "";
	    classifier = "";
	}

	public MavenData(String line) throws IllegalArgumentException, MalformedPatternException
	{
	    String regex = "\\S+";
	    String inputString = "[INFO] +- commons-pool-C:commons-pool-A:jar:1.5.2:compile";

	    // Initialize the pattern
	    Pattern pattern = RegexUtils.initPattern(regex);
	    PatternMatcherInput input = new PatternMatcherInput(inputString);

	    PatternMatcher matcher = new Perl5Matcher();
	    MatchResult result;

	    rawText = line;

	    int match = 0;
	    while (matcher.contains(input, pattern))
	    {
		result = matcher.getMatch();
		String value = result.group(5);
		switch (match)
		{
		    case (0):
			groupID = value;
			break;
		    case (1):
			artifactID = value;
			break;
		    case (2):
			artifactType = value;
			break;
		    case (3):
			artifactVersion = value;
			break;
		    case (4):
			scope = value;
			break;
		    case (5):
			classifier = value;
			break;
		    default:
			break;
		}
		;
		match++;
		LOGGER.debug("Match ({}), Group ({}): \"{}\" ", new Object[] { match, 5, value });
	    }
	    LOGGER.debug("Total Matches: {}", match);
	    if (match < 5 || match > 6)
	    {
		throw new IllegalArgumentException(String.format(
		    "Unable to parse \"%1$s\". Invalid Maven output line.", line));
	    }
	}

	/**
	 * @param rawText
	 * @param groupID
	 * @param artifactID
	 * @param artifactType
	 * @param artifactVersion
	 * @param scope
	 * @param classifier
	 */
	public MavenData(String rawText, String groupID, String artifactID, String artifactType,
		String artifactVersion, String scope, String classifier)
	{
	    this.rawText = rawText;
	    this.groupID = groupID;
	    this.artifactID = artifactID;
	    this.artifactType = artifactType;
	    this.artifactVersion = artifactVersion;
	    this.scope = scope;
	    this.classifier = classifier;
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

	/**
	 * @param rawText
	 *            the rawText to set
	 */
	public void setRawText(String rawText)
	{
	    this.rawText = rawText;
	}

	/**
	 * @param groupID
	 *            the groupID to set
	 */
	public void setGroupID(String groupID)
	{
	    this.groupID = groupID;
	}

	/**
	 * @param artifactID
	 *            the artifactID to set
	 */
	public void setArtifactID(String artifactID)
	{
	    this.artifactID = artifactID;
	}

	/**
	 * @param artifactType
	 *            the artifactType to set
	 */
	public void setArtifactType(String artifactType)
	{
	    this.artifactType = artifactType;
	}

	/**
	 * @param artifactVersion
	 *            the artifactVersion to set
	 */
	public void setArtifactVersion(String artifactVersion)
	{
	    this.artifactVersion = artifactVersion;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(String scope)
	{
	    this.scope = scope;
	}

	/**
	 * @param classifier
	 *            the classifier to set
	 */
	public void setClassifier(String classifier)
	{
	    this.classifier = classifier;
	}
    }
}
