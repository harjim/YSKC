
package com.yskc.rs.models.excel;

import com.yskc.common.utils.JsonUtils;

import java.util.Date;
import java.util.Map;

public class TableFieldInfo {

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

    /**  */
    private Integer msCreatorId ;
    /**  */
    private Integer msLastUpdatorId ;

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

    private Map<String, Object> fieldTitleObject;

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

    public Map<String, Object> getFieldTitleObject() {
        return JsonUtils.jsonToPojo(fieldTitle, Map.class);
    }

    public void setFieldTitleObject(Map<String, Object> fieldTitleObject) {
        this.fieldTitleObject = fieldTitleObject;
    }
}
