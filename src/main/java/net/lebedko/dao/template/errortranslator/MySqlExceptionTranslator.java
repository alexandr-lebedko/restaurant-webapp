package net.lebedko.dao.template.errortranslator;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.UniqueViolationException;

import java.sql.SQLException;

/**
 * alexandr.lebedko : 10.05.2017.
 */
public class MySqlExceptionTranslator implements ExceptionTranslator {

    private static final int DUPLICATE_ENTRY = 1062;
    private static final int DUPLICATE_KEY = 1022;

    @Override
    public DataAccessException translate(SQLException e) {
        if(e.getErrorCode()==DUPLICATE_ENTRY)
            return new UniqueViolationException(e);
        if(e.getErrorCode()==DUPLICATE_KEY)
            return new UniqueViolationException(e);

        return new DataAccessException(e);
    }
}
