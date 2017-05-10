package net.lebedko.dao.jdbc;

import net.lebedko.dao.InvoiceDao;
import net.lebedko.dao.exception.DataAccessException;
import net.lebedko.dao.template.Mapper;
import net.lebedko.dao.template.QueryTemplate;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceView;
import net.lebedko.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.Objects.requireNonNull;
import static net.lebedko.util.PropertyUtil.loadProperties;
import static net.lebedko.util.Util.checkValidity;

/**
 * alexandr.lebedko : 01.05.2017.
 */

public class JdbcInvoiceDao implements InvoiceDao {
    private static final Properties props = loadProperties("sql-queries.properties");
    private static final String insert = props.getProperty("invoice.insert");
    private static final String delete = props.getProperty("invoice.delete");
    private static final String update = props.getProperty("invoice.update");
    private static final String findByPaid = props.getProperty("invoice.findByPaid");
    private static final String findUnpaidByClient = props.getProperty("invoice.findUnpaidByClient");
    private static final String findById = props.getProperty("invoice.findById");

    private InvoiceMapper mapper = new InvoiceMapper();
    private QueryTemplate template;

    public JdbcInvoiceDao(QueryTemplate template) {
        this.template = requireNonNull(template);
    }

    @Override
    public Invoice insert(Invoice invoice) throws DataAccessException {
        checkValidity(invoice);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getOrderId());
        params.put(2, invoice.isPaid());
        params.put(3, invoice.getPrice().getDoubleValue());
        params.put(4, Timestamp.valueOf(invoice.getCreationDateTime()));
        int pk = template.insertAndReturnKey(insert, params);
        invoice.setId(pk);

        return invoice;
    }

    @Override
    public void delete(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        template.update(delete, params);
    }

    @Override
    public void update(Invoice invoice) throws DataAccessException {
        checkValidity(invoice);
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, invoice.getOrderId());
        params.put(2, invoice.isPaid());
        params.put(3, invoice.getPrice().getDoubleValue());
        params.put(4, Timestamp.valueOf(invoice.getCreationDateTime()));
        params.put(5, invoice.getId());

        template.update(update, params);
    }

    @Override
    public List<InvoiceView> getUnpaidInvoices() throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, false);
        return new ArrayList<>(template.queryAll(findByPaid, params, mapper));
    }

    @Override
    public List<InvoiceView> getUnpaidInvoicesByUser(User client) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, client.getId());
        return new ArrayList<>(template.queryAll(findUnpaidByClient, params, mapper));
    }


    @Override
    public InvoiceView findById(int id) throws DataAccessException {
        Map<Integer, Object> params = new HashMap<>();
        params.put(1, id);
        return template.queryOne(findById, params, mapper);
    }


    public static final class InvoiceMapper implements Mapper<InvoiceView> {

        @Override
        public InvoiceView map(ResultSet rs) throws SQLException {
            int invoiceId = rs.getInt("i_id");
            int orderId = rs.getInt("i_order_id");
            boolean paid = rs.getBoolean("i_paid");
            Price price = new Price(rs.getDouble("i_price"));
            LocalDateTime creationTimestamp = rs.getTimestamp("i_creation_timestamp").toLocalDateTime();

            return new InvoiceView(invoiceId, orderId, price, paid, creationTimestamp);
        }
    }
}
