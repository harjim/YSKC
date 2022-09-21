package com.yskc.ms.models.params;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class PageParams {
    private int pageNo;
    private int pageSize;
    private String field;
    private String order;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Pagination getPagination() {
        Pagination pagination = new Pagination(this.getPageNo(), this.getPageSize());
        if (!StringUtils.isEmpty(this.getField())) {
            List<String> fields = new ArrayList<>();
            fields.add(this.getField());
            if ("asc".equals(this.getOrder())) {
                pagination.setAscs(fields);
            } else {
                pagination.setDescs(fields);
            }
        }
        return pagination;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

}
