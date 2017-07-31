package net.lebedko.dao.jdbc.transaction;

import java.util.Stack;


/**
 * alexandr.lebedko : 04.07.2017.
 */
public class TransactionCounter {


    //value to push in stack when starting or joining transaction
    private static final Object CHIP = new Object();

    private Stack chipStack = new Stack();

    public void addTx() {
        chipStack.push(CHIP);
    }

    public void removeTx() {
        if (chipStack.empty()) {
            throw new IllegalStateException("Counter is empty!");
        }
        chipStack.pop();
    }

    public boolean isNestedTx() {
        if (chipStack.empty()) {
            throw new IllegalStateException("Counter is empty!");
        }

        return chipStack.size() > 1;
    }
}
