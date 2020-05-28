package pl.bergholc.bazak.jira.service;

import org.apache.log4j.Logger;
import pl.bergholc.bazak.jira.exception.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JDBCConnection {
    private final String user = "root";
    private final String password = "toor";
    private final String url = "jdbc:mysql://localhost/database?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection = null;
    private Logger logger = LoggerManager.getApplicationLogger();

    public JDBCConnection() throws DatabaseConnectionException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new DatabaseConnectionException();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if (connection != null)
            try {
                connection.close();
                connection = null;
            } catch (SQLException sqlE) {
                sqlE.printStackTrace();
                logger.error(sqlE.getMessage());
            }
    }
}
