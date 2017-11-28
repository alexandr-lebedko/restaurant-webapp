package net.lebedko.dao.jdbc;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.mapper.InvoiceMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JdbcInvoiceDao extends AbstractJdbcDao implements InvoiceDao {
    private static final String INSERT = QUERIES.getProperty("invoice.insert");
    private static final String UPDATE = QUERIES.getProperty("invoice.update");
    private static final String GET_BY_ID = QUERIES.getProperty("invoice.getById");
    private static final String GET_BY_USER_AND_STATE = QUERIES.getProperty("invoice.getByUserAndState");
    private static final String GET_UNPAID_OR_CLOSED_BY_USER = QUERIES.getProperty("invoice.getUnpaidOrClosedByUser");
    private static final String GET_CURRENT_INVOICE = QUERIES.getProperty("invoice.getCurrentInvoice");
    private static final String GET_BY_STATE = QUERIES.getProperty("invoice.getByState");
    private static final String GET_BY_USER = QUERIES.getProperty("invoice.getByUser");
    private static final String DELETE = QUERIES.getProperty("invoice.delete");

    public JdbcInvoiceDao(QueryTemplate template) {
        super(template);
    }


    @Override
    public Invoice insert(Invoice invoice) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getUser().getId());
        params.put(2, invoice.getAmount().getValue());
        params.put(3, invoice.getState().name());
        params.put(4, Timestamp.valueOf(invoice.getCreatedOn()));

        Long id = template.insertAndReturnKey(INSERT, params);

        return new Invoice(id, invoice.getUser(), invoice.getState(), invoice.getAmount(), invoice.getCreatedOn());
    }

    @Override
    public Invoice getByUserAndState(User user, InvoiceState state) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());
        params.put(2, state.name());

        return template.queryOne(GET_BY_USER_AND_STATE, params, new InvoiceMapper(user, state));
    }

    @Override
    public Invoice findById(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        return template.queryOne(GET_BY_ID, params, new InvoiceMapper());
    }

    @Override
    public void update(Invoice invoice) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getAmount().getValue());
        params.put(2, invoice.getState().name());
        params.put(3, invoice.getId());

        template.update(UPDATE, params);
    }

    @Override
    public Invoice getUnpaidOrClosedByUser(User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());

        return template.queryOne(GET_UNPAID_OR_CLOSED_BY_USER, params, new InvoiceMapper(user));
    }

//    @Override
//    public Invoice getCurrentInvoice(User user) throws DataAccessException {
//        Map<Integer, Object> params = new HashMap<>();
//        params.put(1, user.getId());
//
//
//        return template.queryOne(GET_CURRENT_INVOICE, params, new InvoiceMapper(user));
//    }

    @Override
    public Collection<Invoice> getByState(InvoiceState state) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, state.name());

        return template.queryAll(GET_BY_STATE, params, new InvoiceMapper());
    }

    @Override
    public Collection<Invoice> getByUser(User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());

        return template.queryAll(GET_BY_USER, params, new InvoiceMapper(user));
    }

    @Override
    public void delete(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        template.update(DELETE, params);
    }
}
