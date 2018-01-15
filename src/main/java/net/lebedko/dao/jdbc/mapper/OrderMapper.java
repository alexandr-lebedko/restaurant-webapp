package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class OrderMapper implements Mapper<Order> {
    private static final String ID = "o_id";
    private static final String CREATION = "o_creation";
    private static final String STATE = "o_state";

    private InvoiceMapper invoiceMapper;
    private Invoice invoice;

    public OrderMapper() {
        this(new InvoiceMapper());
    }

    public OrderMapper(Invoice invoice) {
        this.invoice = invoice;
    }

    public OrderMapper(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public Order map(ResultSet rs) throws SQLException {
        return new Order(
                rs.getLong(ID),
                getInvoice(rs),
                OrderState.valueOf(rs.getString(STATE)),
                rs.getTimestamp(CREATION).toLocalDateTime()
        );
    }

    private Invoice getInvoice(ResultSet rs) throws SQLException {
        return nonNull(invoice) ? invoice : invoiceMapper.map(rs);
    }
}
