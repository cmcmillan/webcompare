package com.chris.utils.dbutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chris.utils.text.TextFileIn;
import com.chris.utils.text.regex.RegexUtils;

public class TestDBUtils_copy
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
     * Name of the GIAT Required Tools Excel File
     */
    private static final String GIAT_REQ_TOOLS_XLS = "RequiredTools_20091012_1700.xls";
    /**
     * Common DataSource
     */
    private DataSource ds;
    private final Map<String, Integer> giatCategoryCache = new HashMap<String, Integer>();
    private final Map<String, Integer> statusCodeCache = new HashMap<String, Integer>();

    @Before
    public void setUp() throws Exception
    {
	ds =
		DBUtils.setupPostgreSQLDataSource(DBConstant.PG_USERNAME, DBConstant.PG_PASSWORD,
		    DBConstant.PG_CONNECTION_URL);
	giatCategoryCache.clear();
	statusCodeCache.clear();
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

    @Ignore
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

    @Test
    public void testLoadCategories() throws Exception
    {
	Connection conn = null;
	NamedParameterStatement stmt = null;
	ResultSet results = null;
	String worksheetName;
	// Create a work book reference
	HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(GIAT_REQ_TOOLS_XLS));

	try
	{
	    // Initialize the connection
	    LOGGER.debug("Creating connection");
	    conn = ds.getConnection();
	    conn.setCatalog("public");
	    LOGGER.debug("Creating statement");

	    String categoriesSql = "select giat_init_category(:category);";

	    stmt = new NamedParameterStatement(conn, categoriesSql);

	    for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++)
	    {
		worksheetName = workbook.getSheetName(sheetIndex);
		LOGGER.debug("Worksheet ({}): {}", sheetIndex, worksheetName);
		// Set the Named Parameter
		stmt.setString("category", worksheetName);
		// Add the GIAT Workbook Categories
		// Get the Category ID of the category
		results = stmt.executeQuery();
		if (results != null && results.next())
		{
		    // categoryIDs.put(worksheetName, results.getInt(1));
		    LOGGER.debug("Category ({}): {}", results.getInt(1), worksheetName);
		    assertEquals("Unexpected category id", sheetIndex + 1, results.getInt(1));
		}
	    }
	}
	catch (SQLException e)
	{
	    fail(e.getMessage());
	}
	finally
	{
	    DBUtils.cleanupConnections(conn, stmt, results);
	}
    }

    @Test
    public void testLoadRequiredToolsXLS() throws Exception
    {
	Connection conn = null;
	List<Statement> stmts = new ArrayList<Statement>();
	List<ResultSet> results = new ArrayList<ResultSet>();
	String worksheetName;
	// Create a work book reference
	HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(GIAT_REQ_TOOLS_XLS));

	try
	{
	    // Initialize the connection
	    LOGGER.debug("Creating connection");
	    conn = ds.getConnection();
	    conn.setCatalog("public");
	    LOGGER.debug("Creating statement");

	    String categoriesSql = "select giat_init_category(:category);";
	    String statusSql = "select giat_init_status(:status);";
	    String requiredToolsPrioritySql =
		    "select giat_init_required_tools("
			    + ":priority, :name, :version, :website, :statusCode, :categoryID);";
	    String requiredToolsSql =
		    "select giat_init_required_tools("
			    + ":name, :version, :website, :statusCode, :categoryID);";

	    NamedParameterStatement categoriesStmt =
		    new NamedParameterStatement(conn, categoriesSql);
	    stmts.add(categoriesStmt.getStatement());

	    NamedParameterStatement statusStmt = new NamedParameterStatement(conn, statusSql);
	    stmts.add(statusStmt.getStatement());

	    NamedParameterStatement requiredToolsPriorityStmt =
		    new NamedParameterStatement(conn, requiredToolsPrioritySql);
	    stmts.add(requiredToolsPriorityStmt.getStatement());

	    NamedParameterStatement requiredToolsStmt =
		    new NamedParameterStatement(conn, requiredToolsPrioritySql);
	    stmts.add(requiredToolsStmt.getStatement());

	    HSSFSheet currSheet = null;
	    HSSFRow currRow = null;
	    int priority;
	    String name;
	    String version;
	    String website;
	    String status;

	    NamedParameterStatement reqToolStmt;

	    for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++)
	    {
		currSheet = workbook.getSheetAt(sheetIndex);
		worksheetName = currSheet.getSheetName();
		LOGGER.debug("Worksheet ({}): {}", sheetIndex, worksheetName);

		int categoryID = getCategoryID(worksheetName, categoriesStmt);
		LOGGER.debug("{} CategoryID: {}", worksheetName, categoryID);

		if (worksheetName.equalsIgnoreCase("Libraries"))
		{
		    // Don't include priority from the Libraries page
		    reqToolStmt = requiredToolsStmt;
		}
		else
		{
		    reqToolStmt = requiredToolsPriorityStmt;
		}

		// Get the last row number
		int lastRowNum = currSheet.getLastRowNum();
		// Skip the first row since it is the header row
		for (int rowNum = 1; rowNum < lastRowNum; rowNum++)
		{
		    currRow = currSheet.getRow(rowNum);
		    priority = Integer.parseInt(currRow.getCell(0).getStringCellValue());
		    name = currRow.getCell(1).getStringCellValue();
		    version = currRow.getCell(2).getStringCellValue();
		    website = currRow.getCell(3).getStringCellValue();
		    status = currRow.getCell(4).getStringCellValue();
		    int statusCode = getStatusCode(status, statusStmt);
		    LOGGER.debug("{} Status Code: {}", status, statusCode);
		    // Set the Named Parameters
		    reqToolStmt.setInt("priority", priority);
		    reqToolStmt.setString("name", name);
		    reqToolStmt.setString("version", version);
		    reqToolStmt.setString("website", website);
		    reqToolStmt.setInt("statusCode", statusCode);
		    reqToolStmt.setInt("categoryID", categoryID);
		    // Add the required tool
		    reqToolStmt.executeQuery();
		}
	    }

	}
	catch (SQLException e)
	{
	    fail(e.toString());
	}
	finally
	{
	    DBUtils.cleanupConnections(conn, stmts, results);
	}
    }

    /**
     * @param worksheetName
     * @param categoriesStmt
     * @return
     * @throws SQLException
     */
    private int getCategoryID(String worksheetName, NamedParameterStatement categoriesStmt)
	    throws SQLException
    {
	if (!giatCategoryCache.containsKey(worksheetName))
	{
	    // Set the Named Parameter
	    categoriesStmt.setString("category", worksheetName);
	    // Add the GIAT Workbook Categories, if it does not already exist
	    // Get the Category ID of the category
	    ResultSet categoryIDResults = categoriesStmt.executeQuery();

	    int categoryID = -9999;
	    if (categoryIDResults != null && categoryIDResults.next())
	    {
		// Get the CategoryID
		categoryID = categoryIDResults.getInt(1);
		LOGGER.debug("Category ({}): {}", categoryID, worksheetName);
		giatCategoryCache.put(worksheetName, categoryID);
	    }
	    else
	    {
		throw new SQLException("Unable to retrieve categoryID, " + worksheetName);
	    }
	}
	return giatCategoryCache.get(worksheetName);
    }

    /**
     * @param status
     * @param statusCodeStmt
     * @return
     * @throws SQLException
     */
    private int getStatusCode(String status, NamedParameterStatement statusCodeStmt)
	    throws SQLException
    {
	if (!statusCodeCache.containsKey(status))
	{
	    // Set the Named Parameter
	    statusCodeStmt.setString("status", status);
	    // Add the GIAT Status, if it does not already exist
	    // Get the Status Code of the status
	    ResultSet statusCodeResults = statusCodeStmt.executeQuery();

	    int statusCode = -9999;
	    if (statusCodeResults != null && statusCodeResults.next())
	    {
		// Get the CategoryID
		statusCode = statusCodeResults.getInt(1);
		LOGGER.debug("Category ({}): {}", statusCode, status);
		statusCodeCache.put(status, statusCode);
	    }
	    else
	    {
		throw new SQLException("Unable to retrieve status code, " + status);
	    }
	}
	return statusCodeCache.get(status);
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
