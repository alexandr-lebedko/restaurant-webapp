package net.lebedko.dao.jdbc.template.errortranslator;

import net.lebedko.dao.exception.DataAccessException;

import java.sql.SQLException;

public interface ExceptionTranslator {
    DataAccessException translate(SQLException e);
}
