package com.yskc.ms.models.project;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.workshop
 * @Author: wangxing
 * @CreateTime: 2019-11-21 09:52
 * @Description: Workshop
 */
public class WorkshopModel {
    private Integer id;
    private Integer companyId;
    private String workshopName;
    private Integer parentId;
    @Size(max=300,message="备注不能超过300个字")
    private String remark;
    private String fullPath;
    private List<WorkshopModel> children;
    private Integer seq;
    private String fullWorkshopName;

    public String getFullWorkshopName() {
        return fullWorkshopName;
    }

    public void setFullWorkshopName(String fullWorkshopName) {
        this.fullWorkshopName = fullWorkshopName;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<WorkshopModel> getChildren() {
        return children;
    }

    public void setChildren(List<WorkshopModel> children) {
        this.children = children;
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

    public String getWorkshopName() {
        return workshopName;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }


}
