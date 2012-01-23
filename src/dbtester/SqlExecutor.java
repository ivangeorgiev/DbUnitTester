/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbtester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ivan
 */
public class SqlExecutor extends Executor {
    private Connection conn;
    private Statement statement;
    private String url;
    private String driver;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    @Override
    protected void doExecute(Validator v) {
        Statement stmt;
        try {
            stmt = getStatement();
            ResultSet rs = stmt.executeQuery(v.getSql());
            while (rs.next()) {
                Logger.getLogger(SqlExecutor.class.getName()).log(Level.SEVERE, String.format("{%s} Failed: %s", v.getName(), rs.getString("message")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SqlExecutor.class.getName()).log(Level.SEVERE, String.format("{%s} Failed with exception: %s", v.getName(), ex.getMessage()));
        }
    }
    
   
    private Connection getConnection() throws SQLException {
        if (driver != null && driver.trim().length() > 0) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException cnfe) {
                throw new SQLException("Driver not found.");
            }
        }
        if (conn == null) {
            conn = DriverManager.getConnection(url);
        }
        return conn;
    }
    
    private Statement getStatement() throws SQLException {
        statement = getConnection().createStatement();
        return statement;
    }
    
}
