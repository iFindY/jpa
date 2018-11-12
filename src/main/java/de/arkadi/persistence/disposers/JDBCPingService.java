package de.arkadi.persistence.disposers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;


@ApplicationScoped
public class JDBCPingService {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private Logger logger;

    @Inject
    @UserDatabase
    private Connection conn;

    // ======================================
    // =          Business methods          =
    // ======================================

    public void ping() throws SQLException {
        conn.createStatement().executeQuery("SELECT 1 FROM SYSIBM.SYSDUMMY1");
        logger.info("Ping....");
    }
}
