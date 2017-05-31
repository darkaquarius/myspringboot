package boot.chuangqi.base;

import lombok.Data;

import java.util.List;

/**
 * @author libo.ding
 * @since 2016-08-20
 */
@Data
public class PageList<T> {

    public PageList() {
    }

    public PageList(int page, int perPage) {
        this(null, page, perPage, 0, 0);
    }

    public PageList(List<T> list, int page, int perPage, int pageCount, int rowCount) {
        if (page < 1 || perPage < 1)
            throw new IllegalArgumentException("invalid page or per page");
        this.list = list;
        this.page = page;
        this.perPage = perPage;
        this.pageCount = pageCount;
        this.rowCount = rowCount;
    }

    /**
     * current page, >=1
     */
    private int page;
    /**
     * per page size, >=1
     */
    private int perPage;
    /**
     * row count(records id)
     */
    private int rowCount;
    /**
     * page count
     */
    private int pageCount;
    /**
     * current page records
     */
    private List<T> list;

    /**
     * next page, -1: already at last page
     */
    public int getNext() {
        return page >= pageCount ? -1 : page + 1;
    }

    /**
     * pre page, -1: already at start page(page ==1)
     */
    public int getPre() {
        return page == 1 ? -1 : page + 1;
    }
}
