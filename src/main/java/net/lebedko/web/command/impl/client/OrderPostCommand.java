package net.lebedko.web.command.impl.client;

import net.lebedko.service.exception.ServiceException;
import net.lebedko.web.command.IContext;
import net.lebedko.web.command.impl.AbstractCommand;
import net.lebedko.web.response.ForwardAction;
import net.lebedko.web.response.IResponseAction;
import net.lebedko.web.util.constant.PageErrorNames;
import net.lebedko.web.validator.Errors;


import static net.lebedko.web.util.constant.WebConstant.*;

/**
 * alexandr.lebedko : 20.09.2017.
 */
public class OrderPostCommand extends AbstractCommand {
    private static final IResponseAction ORDER_FORM_FORWARD = new ForwardAction(PAGE.CLIENT_ORDER_FORM);


    @Override
    protected IResponseAction doExecute(IContext context) throws ServiceException {
        final Errors errors = new Errors();

        return null;
    }


    private void parseOrderContent(IContext context, Errors errors) {


        final String[] idValues = context.getRequestParameters("itemId");
        final String[] quantityValues = context.getRequestParameters("quantity");

        for (int i = 0; i < idValues.length; i++) {
            long itemId = parseItemId(idValues[i]);
            int quantity = parseItemQuantity(quantityValues[i]);

            try {

            } catch (IllegalArgumentException e) {
                LOG.error(e);
                errors.register("invalidOrderPosition", PageErrorNames.INVALID_ORDER_POSITION);
            }
        }

    }


    private long parseItemId(String value) {
        long id = -1;
        try {
            id = Long.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return id;
    }

    private int parseItemQuantity(String value) {
        int quantity = -1;
        try {
            quantity = Integer.valueOf(value);
        } catch (NumberFormatException nfe) {
            LOG.error(nfe);
        }
        return quantity;
    }
}
