package net.lebedko.dao.jdbc.template;

import java.util.Map;

public interface EntityToParamsMapper<T> {
    Map<Integer, Object> map(T t);
}
