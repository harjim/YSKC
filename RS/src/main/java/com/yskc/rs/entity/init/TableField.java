package com.yskc.rs.entity.init;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 表格头字段配置表
 *
 * @author huronghua
 */
@TableName("sys_tableField")
public class TableField implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId
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
    /**
     * 创建人
     */
    private Integer creatorId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后操作人id
     */
    private Integer lastUpdatorId;
    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    /**
     *
     */
    private Integer msCreatorId;
    /**
     *
     */
    private Integer msLastUpdatorId;

    private Boolean hasMonth;

    /**
     * 是否存在薪资配置
     */
    private Boolean hasCol;

    /**
     * 是否存在五险一金配置
     */
    private Boolean hasICol;

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }

    public Integer getMsLastUpdatorId() {
        return msLastUpdatorId;
    }

    public void setMsLastUpdatorId(Integer msLastUpdatorId) {
        this.msLastUpdatorId = msLastUpdatorId;
    }

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

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLastUpdatorId() {
        return lastUpdatorId;
    }

    public void setLastUpdatorId(Integer lastUpdatorId) {
        this.lastUpdatorId = lastUpdatorId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
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
