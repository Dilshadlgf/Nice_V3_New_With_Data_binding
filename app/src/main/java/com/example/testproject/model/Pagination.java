package com.example.testproject.model;

import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("pageNum")
     private int pageNum;

    @SerializedName("limit")
     private int limit;

    @SerializedName("count")
    private int count;

    @SerializedName("nextPage")
    private int nextPage;

    @SerializedName("prevPage")
    private int prevPage;

    @SerializedName("totalPage")
    private int totalPage;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(int prevPage) {
        this.prevPage = prevPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
