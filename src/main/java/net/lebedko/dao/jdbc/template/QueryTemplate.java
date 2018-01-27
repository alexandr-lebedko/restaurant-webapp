package net.lebedko.dao.jdbc.template;

import net.lebedko.dao.jdbc.connection.ConnectionProvider;
import net.lebedko.dao.jdbc.template.errortranslator.ExceptionTranslator;
import net.lebedko.dao.jdbc.template.errortranslator.MySqlExceptionTranslator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class QueryTemplate {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    private ConnectionProvider connectionProvider;
    private ExceptionTranslator exceptionTranslator;

    public QueryTemplate(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
        this.exceptionTranslator = new MySqlExceptionTranslator();

    }

    public <T> T queryOne(String sql, Object[] params, Mapper<T> mapper) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getOne(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public <T> Collection<T> queryAll(String sql, Mapper<T> mapper) {
        return queryAll(sql, EMPTY_ARRAY, mapper);
    }

    public <T> Collection<T> queryAll(String sql, Object[] params, Mapper<T> mapper) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return getAll(ps.executeQuery(), mapper);
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public int update(String sql, Object... params) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            putParamsToPreparedStatement(ps, params);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public int[] updateButch(String sql, List<Object[]> params) {
        try (PreparedStatement ps = connectionProvider.getConnection().prepareStatement(sql)) {
            for (Object[] paramArray : params) {
                putParamsToPreparedStatement(ps, paramArray);
                ps.addBatch();
            }
            return ps.executeBatch();
        } catch (SQLException e) {
            throw exceptionTranslator.translate(e);
        }
    }

    public int insertAndReturnKey(String sql, Object[] params) {
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

    private void putParamsToPreparedStatement(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}