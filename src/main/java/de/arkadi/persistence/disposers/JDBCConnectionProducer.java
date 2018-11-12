package de.arkadi.persistence.disposers;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;


public class JDBCConnectionProducer {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    // ======================================
    // =          Business methods          =
    // ======================================

    @Produces
    @UserDatabase
    private Connection createConnection() throws Exception {

        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection conn = DriverManager.getConnection("jdbc:derby:memory:module04DB;create=true", "APP", "APP");

        logger.info("Connection created");
        return conn;
    }

    private void closeConnection(@Disposes @UserDatabase Connection conn) throws SQLException {
        conn.close();
        logger.info("Connection closed");
    }
}
