package net.lebedko.dao.jdbc.template.errortranslator;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.UniqueViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class MySqlExceptionTranslator implements ExceptionTranslator {
    private static final Logger LOG = LogManager.getLogger();

    private static final int DUPLICATE_ENTRY = 1062;
    private static final int DUPLICATE_KEY = 1022;

    @Override
    public DataAccessException translate(SQLException e) {
        int errorCode = e.getErrorCode();

        LOG.error("MySQL ERROR CODE: " + errorCode);

        if (errorCode == DUPLICATE_ENTRY) {
            return new UniqueViolationException(e);
        }
        if (errorCode == DUPLICATE_KEY) {
            return new UniqueViolationException(e);
        }

        return new DataAccessException(e);
    }
}
