package net.lebedko.dao.paging;

import java.util.Collection;

public class Page<T> {
    private Collection<T> content;
    private int size;
    private int total;
    private int currentPage;
    private int numberOfPages;

    public Page(Collection<T> content, int total, int currentPage) {
        this(content, 5, total, currentPage);
    }

    public Page(Collection<T> content, int size, int total, int currentPage) {
        this.content = content;
        this.size = size;
        this.total = total;
        this.currentPage = currentPage;
        this.numberOfPages = (int) (Math.ceil((double) total / size));
    }

    public Collection<T> getContent() {
        return content;
    }

    public int getSize() {
        return size;
    }

    public int getTotal() {
        return total;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }
}
