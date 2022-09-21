package com.xxl.job.executor.models.h3;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class H3Filter {
    /**
     * 分页查询，从第几条开始
     */
    @JsonProperty("FromRowNum")
    private Integer fromRowNum;
    /**
     * 查询的总行数
     */
    @JsonProperty("RequireCount")
    private boolean requireCount;
    /**
     * 返回的字段，不填返回所有
     */
    @JsonProperty("ReturnItems")
    private List<String> returnItems;
    /**
     * 排序字段，目前不支持使用，默认置空
     */
    @JsonProperty("SortByCollection")
    private  List<H3SortBy> sortByCollection;
    /**
     * 分页查询，第几条结束
     */
    @JsonProperty("ToRowNum")
    private Integer toRowNum;
    /**
     * 查询条件
     */
    @JsonProperty("Matcher")
    private H3Matcher matcher;

    public Integer getFromRowNum() {
        return fromRowNum;
    }

    public void setFromRowNum(Integer fromRowNum) {
        this.fromRowNum = fromRowNum;
    }

    public boolean isRequireCount() {
        return requireCount;
    }

    public void setRequireCount(boolean requireCount) {
        this.requireCount = requireCount;
    }

    public List<String> getReturnItems() {
        return returnItems;
    }

    public void setReturnItems(List<String> returnItems) {
        this.returnItems = returnItems;
    }

    public List<H3SortBy> getSortByCollection() {
        return sortByCollection;
    }

    public void setSortByCollection(List<H3SortBy> sortByCollection) {
        this.sortByCollection = sortByCollection;
    }

    public Integer getToRowNum() {
        return toRowNum;
    }

    public void setToRowNum(Integer toRowNum) {
        this.toRowNum = toRowNum;
    }

    public H3Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(H3Matcher matcher) {
        this.matcher = matcher;
    }
}
