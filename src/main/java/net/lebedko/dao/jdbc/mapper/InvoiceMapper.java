package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.InvoiceState;
import net.lebedko.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

public class InvoiceMapper implements Mapper<Invoice> {
    private static final String ID = "inv_id";
    private static final String STATE = "inv_state";
    private static final String PRICE = "inv_price";
    private static final String CREATION = "inv_creation";


    private UserMapper userMapper;
    private User user;
    private InvoiceState invoiceState;

    public InvoiceMapper(User user, InvoiceState invoiceState) {
        this.user = user;
        this.invoiceState = invoiceState;
    }

    public InvoiceMapper(User user) {
        this.user = user;
    }

    public InvoiceMapper() {
        this.userMapper = new UserMapper();
    }

    @Override
    public Invoice map(ResultSet rs) throws SQLException {
        return new Invoice(rs.getLong(ID),
                getUser(rs),
                getState(rs),
                new Price(rs.getDouble(PRICE)),
                rs.getTimestamp(CREATION).toLocalDateTime());
    }

    private InvoiceState getState(ResultSet rs) throws SQLException {
        return nonNull(invoiceState) ? invoiceState : InvoiceState.valueOf(rs.getString(STATE));
    }

    private User getUser(ResultSet rs) throws SQLException {
        return nonNull(user) ? user : userMapper.map(rs);
    }
}
