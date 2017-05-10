package net.lebedko.dao.template.errortranslator;

import net.lebedko.dao.exception.DataAccessException;

import java.sql.SQLException;

/**
 * alexandr.lebedko : 10.05.2017.
 */
public interface ExceptionTranslator {
    DataAccessException translate(SQLException e);
}
