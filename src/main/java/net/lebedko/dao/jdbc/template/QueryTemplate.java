package net.lebedko.dao.jdbc.template;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.connection.ConnectionProvider;
import net.lebedko.dao.jdbc.template.errortranslator.ExceptionTranslator;
import net.lebedko.dao.jdbc.template.errortranslator.MySqlExceptionTranslator;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * alexandr.lebedko : 21.04.2017.
 */

public class QueryTemplate {

    private ConnectionProvider connectionProvider;
    private ExceptionTranslator exceptionTranslator;

    public QueryTemplate(ConnectionProvider connectionProvider) {
        this(connectionProvider, new MySqlExceptionTranslator());
    }

    public QueryTemplate(ConnectionProvider connectionProvider, ExceptionTranslator exceptionTranslator) {
        this.connectionProvider = Objects.requireNonNull(connectionProvider, "Connection provider cannot be null!");
        this.exceptionTranslator = Objects.requireNonNull(exceptionTranslator, "Exception Translator cannot be null");
    }

    public <T> T queryOne(String sql, Map<Integer, Object> params, Mapper<T> mapper) throws DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getOne(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }


    public <T> Collection<T> queryAll(String sql, Mapper<T> mapper) throws DataAccessException {
        return queryAll(sql, Collections.emptyMap(), mapper);
    }

    public <T> Collection<T> queryAll(String sql, Map<Integer, Object> params, Mapper<T> mapper) throws DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getAll(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public void update(String sql, Map<Integer, Object> params) throws DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public void update(String sql) throws DataAccessException {
        update(sql, Collections.emptyMap());
    }


    public long insertAndReturnKey(String sql, Map<Integer, Object> params) throws DataAccessException {
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

    public boolean insert(String sql, Map<Integer, Object> params) throws DataAccessException {
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

    public <T> boolean insertBatch(String sql, Collection<T> elements, EntityMapper<T> mapper) throws DataAccessException {
        int butchCount = 0;

        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            prepareBatch(ps, elements, mapper);

            return ps.executeBatch().length == butchCount;
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    private <T> void prepareBatch(PreparedStatement ps, Collection<T> elements, EntityMapper<T> mapper) throws SQLException {
        for (T t : elements) {
            putParamsToPreparedStatement(ps, mapper.map(t));
        }
    }

    public void updateProcedure(String sql, Map<Integer, Object> params) throws DataAccessException {
        try (CallableStatement callableStatement = connectionProvider.getConnection().prepareCall(sql)) {
            putParamsToPreparedStatement(callableStatement, params);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    private void putParamsToPreparedStatement(PreparedStatement ps, Map<Integer, Object> params) throws SQLException {
        for (Map.Entry<Integer, Object> param : params.entrySet())
            ps.setObject(param.getKey(), param.getValue());
    }
}