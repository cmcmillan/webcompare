package com.chris.utils.dbutils;

public final class DBConstant
{
    /**
     * Default PostgreSQL JDBC Driver
     */
    public static final String PG_DRIVER = "org.postgresql.Driver";

    /**
     * Default PostgreSQL Host
     */
    public static final String PG_HOST = "localhost";

    /**
     * Default PostgreSQL Port
     */
    public static final String PG_PORT = "5433";

    /**
     * Default PostgreSQL Database
     */
    public static final String PG_DATABASE = "postgres";

    /**
     * Default PostgreSQL User Name
     */
    public static final String PG_USERNAME = "postgres";

    /**
     * Default PostgreSQL Password
     */
    public static final String PG_PASSWORD = "GoodDog1";

    /**
     * Default PostgreSQL Connection URL.
     * <p>
     * Possible connection URL forms are:
     * <ul>
     * <li>jdbc:postgresql:database</li>
     * <li>jdbc:postgresql://host[:port]/database</li>
     * </ul>
     */
    public static final String PG_CONNECTION_URL =
	    String.format("jdbc:postgresql://%1$s:%2$s/%3$s", PG_HOST, PG_PORT, PG_DATABASE);

    /**
     * Default Oracle JDBC Driver
     */
    public static final String ORCL_DRIVER = "oracle.jdbc.driver.OracleDriver";

    /**
     * Default Oracle Host
     */
    public static final String ORCL_HOST = "daytongis";

    /**
     * Default Oracle Port
     */
    public static final String ORCL_PORT = "1521";

    /**
     * Default Oracle Database
     */
    public static final String ORCL_DATABASE = "devgis";

    /**
     * Default Oracle User Name
     */
    public static final String ORCL_USERNAME = "geodb";

    /**
     * Default Oracle Password
     */
    public static final String ORCL_PASSWORD = "geodb";

    /**
     * Default Oracle Connection URL. Note that both the ":" and "@" characters
     * are necessary.
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
    public static final String ORCL_CONNECTION_URL =
	    String.format("jdbc:oracle:thin:@%1$s:%2$s:%3$s", ORCL_HOST, ORCL_PORT, ORCL_DATABASE);
}
