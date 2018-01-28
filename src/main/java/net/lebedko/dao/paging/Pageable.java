package net.lebedko.dao.paging;

public class Pageable {
    private int pageSize;
    private int pageNumber;
    private int offset;

    public Pageable(int pageNumber) {
        this(pageNumber, 5);
    }

    public Pageable(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.offset = (pageNumber-1) * pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return offset;
    }
}