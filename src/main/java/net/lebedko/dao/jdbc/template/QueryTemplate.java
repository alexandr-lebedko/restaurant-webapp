package net.lebedko.dao.jdbc.template;

import net.lebedko.dao.jdbc.connection.ConnectionProvider;
import net.lebedko.dao.jdbc.template.errortranslator.ExceptionTranslator;
import net.lebedko.dao.jdbc.template.errortranslator.MySqlExceptionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class QueryTemplate {

    private ConnectionProvider connectionProvider;
    private ExceptionTranslator exceptionTranslator;

    public QueryTemplate(ConnectionProvider connectionProvider) {
        this(connectionProvider, new MySqlExceptionTranslator());
    }

    public QueryTemplate(ConnectionProvider connectionProvider, ExceptionTranslator exceptionTranslator) {
        this.connectionProvider = connectionProvider;
        this.exceptionTranslator = exceptionTranslator;
    }

    public <T> T queryOne(String sql, Map<Integer, Object> params, Mapper<T> mapper) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getOne(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }


    public <T> Collection<T> queryAll(String sql, Mapper<T> mapper) {
        return queryAll(sql, Collections.emptyMap(), mapper);
    }

    public <T> Collection<T> queryAll(String sql, Map<Integer, Object> params, Mapper<T> mapper) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getAll(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public void update(String sql, Map<Integer, Object> params) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public void update(String sql) {
        update(sql, Collections.emptyMap());
    }


    public long insertAndReturnKey(String sql, Map<Integer, Object> params) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            putParamsToPreparedStatement(ps, params);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public boolean insert(String sql, Map<Integer, Object> params) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }

    }

    private <T> T getOne(ResultSet rs, Mapper<T> mapper) throws SQLException {
        if (rs.next()) {
            return mapper.map(rs);
        }
        return null;
    }

    private <T> Collection<T> getAll(ResultSet rs, Mapper<T> mapper) throws SQLException {
        Collection<T> collection = new ArrayList<>();
        while (rs.next()) {
            collection.add(mapper.map(rs));
        }
        return collection;
    }

    public <T> void insertBatch(String sql, Collection<T> elements, EntityToParamsMapper<T> mapper) {
        updateBatch(sql, elements, mapper);
    }

    public <T> void updateBatch(String sql, Collection<T> elements, EntityToParamsMapper<T> mapper) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            prepareBatch(ps, elements, mapper);
            ps.executeBatch();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    private <T> void prepareBatch(PreparedStatement ps, Collection<T> elements, EntityToParamsMapper<T> mapper) throws SQLException {
        for (T t : elements) {
            putParamsToPreparedStatement(ps, mapper.map(t));
            ps.addBatch();
        }
    }

    private void putParamsToPreparedStatement(PreparedStatement ps, Map<Integer, Object> params) throws SQLException {
        for (Map.Entry<Integer, Object> param : params.entrySet()) {
            ps.setObject(param.getKey(), param.getValue());
        }
    }
}