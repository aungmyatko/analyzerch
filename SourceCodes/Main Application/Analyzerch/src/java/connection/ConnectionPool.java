/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author ingokarn.2011
 */
import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;
 
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

 
public class ConnectionPool{
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
    String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
    String URL = "jdbc:mysql://"+host+":"+port+"/analyzerch";    
    public static final String USERNAME = "admin1yBhVyX";
    public static final String PASSWORD = "GX3tf3VKS55-";
 
    private static GenericObjectPool connectionPool = null;
    private static DataSource datasource = null;
 
    public DataSource setUp() throws Exception {
        //
        // Load JDBC Driver class.
        //
        Class.forName(ConnectionPool.DRIVER).newInstance();
 
        //
        // Creates an instance of GenericObjectPool that holds our
        // pool of connections object.
        //
        connectionPool = new GenericObjectPool();
        connectionPool.setMaxActive(10);
 
        //
        // Creates a connection factory object which will be use by
        // the pool to create the connection object. We passes the
        // JDBC url info, username and password.
        //
        ConnectionFactory cf = new DriverManagerConnectionFactory(
                URL,
                ConnectionPool.USERNAME,
                ConnectionPool.PASSWORD);
 
        //
        // Creates a PoolableConnectionFactory that will wraps the
        // connection object created by the ConnectionFactory to add
        // object pooling functionality.
        //
        PoolableConnectionFactory pcf =
                new PoolableConnectionFactory(cf, connectionPool,
                        null, null, false, true);
        datasource =  new PoolingDataSource(connectionPool);
        return datasource;
    }
 
    public static GenericObjectPool getConnectionPool() {
        return connectionPool;
    }
    
    public static Connection getConnection() throws Exception{
        return datasource.getConnection();
    }
public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        ConnectionPool demo = new ConnectionPool();
        datasource = demo.setUp();
        demo.printStatus();
     }
 
    /**
     * Prints connection pool status.
     */
    private void printStatus() {
        String statement = "Max   : " + getConnectionPool().getMaxActive() + "; " +
            "Active: " + getConnectionPool().getNumActive() + "; " +
            "Idle  : " + getConnectionPool().getNumIdle();
        System.out.println(statement);
    }
    
    public static String returnPrintStatus(){
        String statement = "Max   : " + getConnectionPool().getMaxActive() + "; " +
            "Active: " + getConnectionPool().getNumActive() + "; " +
            "Idle  : " + getConnectionPool().getNumIdle();
        return statement;
    }
}