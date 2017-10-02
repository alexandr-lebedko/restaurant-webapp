package net.lebedko.dao.jdbc;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.jdbc.template.QueryTemplate;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.user.User;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * alexandr.lebedko : 02.10.2017.
 */
public class JdbcInvoiceDao extends AbstractJdbcDao implements InvoiceDao {
    private static final String INSERT = QUERIES.getProperty("invoice.insert");
    private static final String GET_BY_USER_AND_STATE = QUERIES.getProperty("invoice.getByUserAndState");

    private static final String ID = "inv_id";
    private static final String STATE = "inv_state";
    private static final String PRICE = "inv_price";
    private static final String CREATION = "inv_creation";


    public JdbcInvoiceDao(QueryTemplate template) {
        super(template);
    }

    @Override
    public Invoice insert(Invoice invoice) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getUser().getId());
        params.put(2, invoice.getState().name());
        params.put(3, Timestamp.valueOf(invoice.getCratedOn()));

        Long id = template.insertAndReturnKey(INSERT, params);

        return new Invoice(id, invoice.getUser(), invoice.getState(), invoice.getAmount(), invoice.getCratedOn());
    }

    @Override
    public Invoice get(User user, State state) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, user.getId());
        params.put(2, state.name());

        return template.queryOne(GET_BY_USER_AND_STATE, params, rs ->
                new Invoice(
                        rs.getLong(ID),
                        user,
                        state,
                        new Price(rs.getDouble(PRICE)),
                        rs.getTimestamp(CREATION).toLocalDateTime()));
    }




}
