package com.yskc.rs.models.material;

import com.yskc.rs.models.params.PageParams;

import java.util.Date;

/**
 * Created by hck
 * on 2020/6/23 14:56
 * description:材料跟踪查询参数model
 */
public class QueryMaterialTrackModel extends PageParams {

    private Integer projectId;

    private String mcode;

    private String mname;

    private Date month;

    private Integer docFileId;
    @Deprecated
    private Integer type;//0:原料 1：辅料 2 工艺装备
    @Deprecated
    private Integer rdType;
    @Deprecated
    private boolean trial;
    @Deprecated
    private Boolean repair;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public boolean isTrial() {
        return trial;
    }

    public void setTrial(boolean trial) {
        this.trial = trial;
    }

    public Boolean getRepair() {
        return repair;
    }

    public void setRepair(Boolean repair) {
        this.repair = repair;
    }

    public Integer getDocFileId() {
        return docFileId;
    }

    public void setDocFileId(Integer docFileId) {
        this.docFileId = docFileId;
    }
}
