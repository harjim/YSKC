package com.yskc.docservice.models.rs.excel;

import java.util.List;

/**
 * 封装报表解析类
 *
 * @param <T>
 * @author huronghua
 */
public class ExcelResult<T> {
    /**
     * 返回错误类型
     */
    public String msg = "";
    /**
     * 解析实体
     */
    private List<T> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
