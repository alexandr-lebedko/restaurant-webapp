package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.order.Order;
import net.lebedko.entity.order.State;

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
    private State state;


    public OrderMapper(Invoice invoice) {
        this(invoice, null);

    }

    public OrderMapper(State state) {
        this(null, state);
    }

    public OrderMapper() {
        this(null, null);
    }

    public OrderMapper(Invoice invoice, State state) {
        this(new InvoiceMapper(), invoice, state);
    }

    public OrderMapper(InvoiceMapper invoiceMapper, Invoice invoice, State state) {
        this.invoiceMapper = invoiceMapper;
        this.invoice = invoice;
        this.state = state;
    }

    @Override
    public Order map(ResultSet rs) throws SQLException {
        Long id = rs.getLong(ID);
        Invoice invoice = getInvoice(rs);
        State state = getState(rs);
        LocalDateTime creation = rs.getTimestamp(CREATION).toLocalDateTime();

        return new Order(id, invoice, state, creation);
    }

    private State getState(ResultSet rs) throws SQLException {
        return nonNull(state) ? state : State.valueOf(rs.getString(STATE));
    }

    private Invoice getInvoice(ResultSet rs) throws SQLException {
        return nonNull(invoice) ? invoice : invoiceMapper.map(rs);
    }
}
