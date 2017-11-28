package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.OrderState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

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
        Long id = rs.getLong(ID);
        Invoice invoice = getInvoice(rs);
        OrderState state = getState(rs);
        LocalDateTime creation = rs.getTimestamp(CREATION).toLocalDateTime();

        return new Order(id, invoice, state, creation);
    }

    private OrderState getState(ResultSet rs) throws SQLException {
        return OrderState.valueOf(rs.getString(STATE));
    }

    private Invoice getInvoice(ResultSet rs) throws SQLException {
        return nonNull(invoice) ? invoice : invoiceMapper.map(rs);
    }
}
