package com.chris.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestFileIO
{
    /**
     * Logging interface
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(TestFileIO.class);

    @Test
    public void backupTables() throws IOException
    {
	File dir = new File("DB_Scripts");
	final String tableSuffix = ".table.sql";
	int tableSuffixLength = tableSuffix.length();
	File[] files;
	// It is also possible to filter the list of returned files.
	// This example does not return any files that start with `.'.
	FilenameFilter filterTables = new FilenameFilter()
	{
	    public boolean accept(File dir, String name)
	    {
		return name.endsWith(tableSuffix);
	    }
	};
	files = dir.listFiles(filterTables);

	final String host = "localhost";
	final String port = "5432";
	final String username = "geodb";
	final String format = "plain";
	final String schema = "public";
	final String database = "postgis";

	for (File file : files)
	{
	    // Extract the table name from the file name
	    String fileName = file.getName().toLowerCase();
	    String tableName = fileName.substring(0, fileName.length() - tableSuffixLength);
	    // dump the table
	    pgdumpTable(host, port, username, format, file.getAbsolutePath(), schema, tableName,
		database);
	}
    }

    @Test
    public void backupDBs() throws IOException
    {
	File dir = new File("DB_Scripts");
	final String dbSuffix = ".db.sql";
	int dbSuffixLength = dbSuffix.length();
	File[] files;
	// It is also possible to filter the list of returned files.
	// This example does not return any files that start with `.'.
	FilenameFilter filterTables = new FilenameFilter()
	{
	    public boolean accept(File dir, String name)
	    {
		return name.endsWith(dbSuffix);
	    }
	};
	files = dir.listFiles(filterTables);

	final String host = "localhost";
	final String port = "5432";
	final String username = "geodb";
	final String format = "plain";

	for (File file : files)
	{
	    // Extract the database name from the file name
	    String fileName = file.getName().toLowerCase();
	    String database = fileName.substring(0, fileName.length() - dbSuffixLength);
	    // dump the database
	    pgdumpDB(host, port, username, format, file.getAbsolutePath(), database);
	}
    }

    @Test
    public void backupServers() throws IOException
    {
	File dir = new File("DB_Scripts");
	final String serverSuffix = ".server.sql";
	File[] files;
	// It is also possible to filter the list of returned files.
	// This example does not return any files that start with `.'.
	FilenameFilter filterTables = new FilenameFilter()
	{
	    public boolean accept(File dir, String name)
	    {
		return name.endsWith(serverSuffix);
	    }
	};
	files = dir.listFiles(filterTables);

	for (File file : files)
	{
	    // Extract the database name from the file name
	    String fileName = file.getName().toLowerCase();

	    String[] serverArgs = fileName.split("\\.");
	    // dump the database
	    pgdumpServer(serverArgs[0], serverArgs[1], serverArgs[2], file.getAbsolutePath());
	}
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param format
     * @param file
     * @param schema
     * @param tableName
     * @param database
     * @throws IOException
     */
    private void pgdumpTable(String host, String port, String username, String format, String file,
	    String schema, String tableName, String database) throws IOException
    {
	List<String> command = new ArrayList<String>();
	command.add(String.format("pg_dump " +
	// --host host --port port --username username
		"--host %1$s --port %2$s --username %3$s "
		// --format format --verbose --file "file"
		+ "--format %4$s --verbose --file \"%5$s\" "
		// --table \"schema\".\"tableName\" database
		+ "--table \"\\\"%6$s\\\".\\\"%7$s\\\"\" %8$s", host, port, username, format, file,
	    schema, tableName, database));

	String directory = new File(file).getParentFile().getAbsolutePath();

	LOGGER.debug("host : " + host);
	LOGGER.debug("Port : " + port);
	LOGGER.debug("Username : " + username);
	LOGGER.debug("Format : " + format);
	LOGGER.debug("File : " + file);
	LOGGER.debug("Schema : " + schema);
	LOGGER.debug("Table Name : " + tableName);
	LOGGER.debug("Database : " + database);

	LOGGER.debug("Directory : " + directory);
	LOGGER.debug("Command : " + command.get(0));
	LOGGER.debug("------------------------------------------");
	runCommand(command, directory);
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param format
     * @param file
     * @param database
     * @throws IOException
     */
    private void pgdumpDB(String host, String port, String username, String format, String file,
	    String database) throws IOException
    {
	List<String> command = new ArrayList<String>();
	command.add(String.format("pg_dump " +
	// --host host --port port --username username
		"--host %1$s --port %2$s --username %3$s "
		// --format format --verbose --file "file" database
		+ "--format %4$s --verbose --file \"%5$s\" %6$s", host, port, username, format,
	    file, database));

	String directory = new File(file).getParentFile().getAbsolutePath();

	LOGGER.debug("host : " + host);
	LOGGER.debug("Port : " + port);
	LOGGER.debug("Username : " + username);
	LOGGER.debug("Format : " + format);
	LOGGER.debug("File : " + file);
	LOGGER.debug("Database : " + database);

	LOGGER.debug("Directory : " + directory);
	LOGGER.debug("Command : " + command.get(0));
	LOGGER.debug("------------------------------------------");
	runCommand(command, directory);
    }

    /**
     * @param host
     * @param port
     * @param username
     * @param file
     * @throws IOException
     */
    private void pgdumpServer(String host, String port, String username, String file)
	    throws IOException
    {
	List<String> command = new ArrayList<String>();
	command.add(String.format("pg_dumpall " +
	// --host host --port port --username username --verbose --file "file"
		"--host %1$s --port %2$s --username %3$s --verbose --file \"%4$s\"", host, port,
	    username, file));

	String directory = new File(file).getParentFile().getAbsolutePath();

	LOGGER.debug("host : " + host);
	LOGGER.debug("Port : " + port);
	LOGGER.debug("Username : " + username);
	LOGGER.debug("File : " + file);

	LOGGER.debug("Directory : " + directory);
	LOGGER.debug("Command : " + command.get(0));
	LOGGER.debug("------------------------------------------");
	runCommand(command, directory);
    }

    /**
     * @param command
     * @param directory
     * @throws IOException
     */
    private void runCommand(List<String> command, String directory) throws IOException
    {
	ProcessBuilder builder = new ProcessBuilder(command);

	// builder.directory(new File(directory));
	final Process process = builder.start();
	InputStream is = process.getInputStream();
	InputStreamReader isr = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(isr);
	String line;
	while ((line = br.readLine()) != null)
	{
	    System.out.println(line);
	}
	System.out.println("Program terminated!");
    }
}
