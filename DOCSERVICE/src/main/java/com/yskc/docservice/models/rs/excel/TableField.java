package com.yskc.docservice.models.rs.excel;

import java.io.Serializable;

/**
 * 表格头字段配置表
 *
 * @author huronghua
 */
public class TableField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private Integer id;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 表格标志
     */
    private String tableId;
    /**
     * 表格头部
     */
    private String fieldTitle;

    private Boolean hasMonth;

    /**
     * 是否存在薪资配置
     */
    private Boolean hasCol;

    /**
     * 是否存在五险一金配置
     */
    private Boolean hasICol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getFieldTitle() {
        return fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public Boolean getHasMonth() {
        if (null == hasMonth) {
            return false;
        }
        return hasMonth;
    }

    public void setHasMonth(Boolean hasMonth) {
        this.hasMonth = hasMonth;
    }

    public Boolean getHasCol() {
        if (null == hasCol) {
            return false;
        }
        return hasCol;
    }

    public void setHasCol(Boolean hasCol) {
        this.hasCol = hasCol;
    }

    public Boolean getHasICol() {
        if (null == hasICol) {
            return false;
        }
        return hasICol;
    }

    public void setHasICol(Boolean hasICol) {
        this.hasICol = hasICol;
    }
}
