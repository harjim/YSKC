package com.yskc.ms.models.user;

import cn.hutool.core.date.DateUtil;
import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hck
 * on 2020/6/5 8:16
 * description:
 */
public class QueryUserListModel extends PageParams implements Serializable {

    private String fullPath;

    private String realName;

    private Date beginDate;

    private Date endDate;
    /**
     * 是否禁用状态, 0:启用,1:禁用
     */
    private Integer status;

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void loadTerm() {
        this.beginDate = this.beginDate != null ? DateUtil.beginOfDay(this.beginDate) : null;
        this.endDate = this.endDate != null ? DateUtil.endOfDay(this.endDate) : null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
