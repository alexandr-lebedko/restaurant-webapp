package net.lebedko.dao.jdbc.transaction;

import java.util.Stack;

public class TransactionCounter {
    //value to push in stack when starting or joining transaction
    private static final Object CHIP = new Object();

    private Stack<Object> chipStack = new Stack<>();


    public void pushTx() {
        chipStack.push(CHIP);
    }

    public void popTx() {
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
