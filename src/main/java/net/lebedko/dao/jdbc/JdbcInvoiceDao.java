package net.lebedko.dao.jdbc;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.jdbc.mapper.InvoiceMapper;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.dao.paging.Page;
import net.lebedko.dao.paging.Pageable;
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
    private static final String GET_PAGED_BY_USER = QUERIES.getProperty("invoice.getPagedByUser");
    private static final String GET_PAGED_BY_STATE = QUERIES.getProperty("invoice.getPagedByState");
    private static final String COUNT_BY_USER = QUERIES.getProperty("invoice.countByUser");
    private static final String COUNT_BY_STATE = QUERIES.getProperty("invoice.countByState");
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
    public Page<Invoice> getByUser(User user, Pageable pageable) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());
        params.put(2, pageable.getPageSize());
        params.put(3, pageable.getOffset());

        final Collection<Invoice> invoices = template.queryAll(GET_PAGED_BY_USER, params, new InvoiceMapper());
        final Integer total = countInvoicesByUser(user);

        return new Page<>(invoices, total, pageable.getPageNumber());
    }

    private Integer countInvoicesByUser(User user) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());

        return template.queryOne(COUNT_BY_USER, params, (rs) -> rs.getInt("total"));
    }

    @Override
    public Page<Invoice> getByState(InvoiceState state, Pageable pageable) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, state.name());
        params.put(2, pageable.getPageSize());
        params.put(3, pageable.getOffset());

        Collection<Invoice> invoices = template.queryAll(GET_PAGED_BY_STATE, params, new InvoiceMapper());
        Integer total = countInvoicesByUser(state);

        return new Page<>(invoices, total, pageable.getPageNumber());
    }

    private Integer countInvoicesByUser(InvoiceState state) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, state.name());
        return template.queryOne(COUNT_BY_STATE, params, (rs) -> rs.getInt("total"));
    }

    @Override
    public void delete(Long id) {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);

        template.update(DELETE, params);
    }
}
