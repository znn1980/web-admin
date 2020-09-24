package com.ifacebox.web.common.model;

/**
 * @author znn
 */
public class Pagination {
    protected Integer page;
    protected Integer limit;
    protected Integer offset;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        if (page != null && page > 0) {
            offset = page - 1;
            if (limit != null) {
                offset = offset * limit;
            }
        } else {
            offset = 0;
        }
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
