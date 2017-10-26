package net.lebedko.dao.jdbc.mapper;

import net.lebedko.dao.jdbc.JdbcUserDao;
import net.lebedko.dao.jdbc.JdbcUserDao.UserMapper;
import net.lebedko.dao.jdbc.template.Mapper;
import net.lebedko.entity.general.Price;
import net.lebedko.entity.invoice.Invoice;
import net.lebedko.entity.invoice.State;
import net.lebedko.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class InvoiceMapper implements Mapper<Invoice> {
    private static final String ID = "inv_id";
    private static final String STATE = "inv_state";
    private static final String PRICE = "inv_price";
    private static final String CREATION = "inv_creation";


    private UserMapper userMapper;
    private User user;
    private State state;

    public InvoiceMapper(User user, State state) {
        this.user = user;
        this.state = state;
    }

    public InvoiceMapper(User user) {
        this.user = user;
    }

    public InvoiceMapper() {
        this.userMapper = new UserMapper();
    }

    @Override
    public Invoice map(ResultSet rs) throws SQLException {
        Long id = rs.getLong(ID);
        User user = getUser(rs);
        State state = getState(rs);
        Price price = new Price(rs.getDouble(PRICE));
        LocalDateTime creation = rs.getTimestamp(CREATION).toLocalDateTime();

        return new Invoice(id, user, state, price, creation);
    }

    private State getState(ResultSet rs) throws SQLException {
        return nonNull(state) ? state : State.valueOf(rs.getString(STATE));
    }

    private User getUser(ResultSet rs) throws SQLException {
        return nonNull(user) ? user : userMapper.map(rs);
    }
}
