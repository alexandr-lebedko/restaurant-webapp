package net.lebedko.dao.template;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * alexandr.lebedko : 21.04.2017.
 */
public interface Mapper<T> {
    T map(ResultSet rs) throws SQLException;

}
