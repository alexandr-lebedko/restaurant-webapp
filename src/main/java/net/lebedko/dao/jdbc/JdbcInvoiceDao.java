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

public class JdbcInvoiceDao implements InvoiceDao {
    private static final String INSERT = QUERIES.getProperty("invoice.insert");
    private static final String UPDATE = QUERIES.getProperty("invoice.update");
    private static final String GET_BY_ID = QUERIES.getProperty("invoice.getById");
    private static final String GET_BY_USER_AND_STATE = QUERIES.getProperty("invoice.getByUserAndState");
    private static final String GET_PAGED_BY_USER = QUERIES.getProperty("invoice.getPagedByUser");
    private static final String GET_PAGED_BY_STATE = QUERIES.getProperty("invoice.getPagedByState");
    private static final String COUNT_BY_USER = QUERIES.getProperty("invoice.countByUser");
    private static final String COUNT_BY_STATE = QUERIES.getProperty("invoice.countByState");
    private static final String DELETE = QUERIES.getProperty("invoice.delete");

    private final QueryTemplate template;

    public JdbcInvoiceDao(QueryTemplate template) {
        this.template = template;
    }

    @Override
    public Invoice insert(Invoice invoice) {
        Object[] params = {
                invoice.getUser().getId(),
                invoice.getAmount().getValue(),
                invoice.getState().name(),
                Timestamp.valueOf(invoice.getCreatedOn())
        };
        Integer id = template.insertAndReturnKey(INSERT, params);
        return new Invoice(id.longValue(),
                invoice.getUser(),
                invoice.getState(),
                invoice.getAmount(),
                invoice.getCreatedOn());
    }

    @Override
    public Invoice getByUserAndState(User user, InvoiceState state) {
        return template.queryOne(
                GET_BY_USER_AND_STATE,
                new Object[]{user.getId(), state.name()},
                new InvoiceMapper(user, state)
        );
    }

    @Override
    public Invoice findById(Long id) {
        return template.queryOne(GET_BY_ID,
                new Object[]{id},
                new InvoiceMapper());
    }

    @Override
    public void update(Invoice invoice) {
        template.update(UPDATE,
                invoice.getAmount().getValue(),
                invoice.getState().name(),
                invoice.getId());
    }

    @Override
    public Page<Invoice> getByUser(User user, Pageable pageable) {

        final Collection<Invoice> invoices = template.queryAll(
                GET_PAGED_BY_USER,
                new Object[]{user.getId(), pageable.getPageSize(), pageable.getOffset()},
                new InvoiceMapper());

        final Integer total = countInvoicesByUser(user);

        return new Page<>(invoices, total, pageable.getPageNumber());
    }

    private Integer countInvoicesByUser(User user) {
        return template.queryOne(
                COUNT_BY_USER,
                new Object[]{user.getId()},
                rs -> rs.getInt("total"));
    }

    @Override
    public Page<Invoice> getByState(InvoiceState state, Pageable pageable) {
        Collection<Invoice> invoices = template.queryAll(
                GET_PAGED_BY_STATE,
                new Object[]{state.name(), pageable.getPageSize(), pageable.getOffset()},
                new InvoiceMapper());
        Integer total = countInvoicesByUser(state);
        return new Page<>(invoices, total, pageable.getPageNumber());
    }

    private Integer countInvoicesByUser(InvoiceState state) {
        return template.queryOne(
                COUNT_BY_STATE,
                new Object[]{state.name()},
                rs -> rs.getInt("total"));
    }

    @Override
    public void delete(Long id) {
        template.update(DELETE, id);
    }
}