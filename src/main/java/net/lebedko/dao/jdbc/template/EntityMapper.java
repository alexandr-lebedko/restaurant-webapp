package net.lebedko.dao.jdbc.template;

import java.util.Map;

/**
 * alexandr.lebedko : 25.09.2017.
 */
public interface EntityMapper<T> {
    Map<Integer, Object> map(T t);
}
