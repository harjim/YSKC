package com.yskc.rs.models.company;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/2/15 13:57
 * @Description:
 * @author: hsx
 */
public class CompanyNameModel implements Serializable {

    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 旧公司名
     */
    private String oldName;

    /**
     * 新公司名
     */
    private String newName;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
