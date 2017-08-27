package net.lebedko.web.util;

/**
 * alexandr.lebedko : 21.08.2017.
 */
public class Functions {

    public static int defineNumberOfRows(int numberOfElements, int elementsPerRow) {
        if (numberOfElements <= 0 || elementsPerRow <= 0)
            return 0;

        int rows = numberOfElements / elementsPerRow;

        if ((numberOfElements % elementsPerRow) > 0) {
            rows++;
        }

        return rows;
    }


    public static int defineRowLimit(int numberOfElements, int elementsPerRow, int currentRow) {
        if (numberOfElements <= 0 || elementsPerRow <= 0 || currentRow <= 0)
            return 0;

        int limitIndex = elementsPerRow * currentRow;

        if (numberOfElements / limitIndex != 0)
            return limitIndex;

        int lackForCompleteRow = limitIndex % numberOfElements;
        limitIndex = limitIndex - lackForCompleteRow;

        return limitIndex;
    }

}
