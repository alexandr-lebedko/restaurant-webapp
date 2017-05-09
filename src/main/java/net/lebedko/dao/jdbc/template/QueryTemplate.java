package net.lebedko.dao.jdbc.template;

import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.exception.EntityExistsException;
import net.lebedko.dao.jdbc.connection.ConnectionProvider;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * alexandr.lebedko : 21.04.2017.
 */

public class QueryTemplate {

    private ConnectionProvider connectionProvider;

    public QueryTemplate(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    public <T> T queryOne(String sql, Map<Integer, Object> params, Mapper<T> mapper) throws DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getOne(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw new DataAccessException(e);
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
            throw new DataAccessException(e);
        }
    }

    public void update(String sql, Map<Integer, Object> params) throws DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    public void update(String sql) throws DataAccessException {
        update(sql, Collections.emptyMap());
    }


    public int insertAndReturnKey(String sql, Map<Integer, Object> params) throws EntityExistsException, DataAccessException {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            putParamsToPreparedStatement(ps, params);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new EntityExistsException(e);
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

    public void updateProcedure(String sql, Map<Integer, Object> params) throws DataAccessException {
        try (CallableStatement callableStatement = connectionProvider.getConnection().prepareCall(sql)) {
            putParamsToPreparedStatement(callableStatement, params);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }

    private void putParamsToPreparedStatement(PreparedStatement ps, Map<Integer, Object> params) throws SQLException {
        for (Map.Entry<Integer, Object> param : params.entrySet())
            ps.setObject(param.getKey(), param.getValue());
    }
}
