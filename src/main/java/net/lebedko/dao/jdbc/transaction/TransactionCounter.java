package net.lebedko.dao.jdbc.transaction;

import java.util.Deque;
import java.util.LinkedList;

public class TransactionCounter {
    //value to push in stack when starting or joining transaction
    private static final Object CHIP = new Object();

    private Deque<Object> chipStack = new LinkedList<>();


    public void addTx() {
        chipStack.push(CHIP);
    }

    public void removeTx() {
        if (chipStack.isEmpty()) {
            throw new IllegalStateException("Counter is empty!");
        }
        chipStack.pop();
    }

    public boolean isNestedTx() {
        if (chipStack.isEmpty()) {
            throw new IllegalStateException("Counter is empty!");
        }
        return chipStack.size() > 1;
    }
}
