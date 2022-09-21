package com.yskc.ms.models.patent;

import java.io.Serializable;
import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-02 15:34
 * @Description: 专利同步model
 */
public class PatentSyncModel implements Serializable {

    public Integer total = 0;

    public Integer syncCount = 0;

    private String msg = "";

    private Boolean fail = false;

    private Boolean done = true;

    private Date linkTime;

    private String currentPatentNo;

    private Date lastTime;

    private String userName;

    private Boolean stop = false;

    private Boolean suspend = false;

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Boolean stop) {
        this.stop = stop;
    }

    public Boolean getSuspend() {
        return suspend;
    }

    public void setSuspend(Boolean suspend) {
        this.suspend = suspend;
    }

    public Date getLinkTime() {
        return linkTime;
    }

    public void setLinkTime(Date linkTime) {
        this.linkTime = linkTime;
    }

    public String getCurrentPatentNo() {
        return currentPatentNo;
    }

    public void setCurrentPatentNo(String currentPatentNo) {
        this.currentPatentNo = currentPatentNo;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSyncCount() {
        return syncCount;
    }

    public void setSyncCount(Integer syncCount) {
        this.syncCount = syncCount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getFail() {
        return fail;
    }

    public void setFail(Boolean fail) {
        this.fail = fail;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setState(String msg, Boolean fail, Boolean done, String patentNo) {
        this.fail = fail;
        this.done = done;
        this.msg = msg;
        this.currentPatentNo = patentNo;
        this.lastTime = new Date();
    }

    public void setZeroAndState(String msg, Boolean fail, Boolean done, String patentNo) {
        this.fail = fail;
        this.done = done;
        this.msg = msg;
        this.currentPatentNo = patentNo;
        this.lastTime = new Date();
        total = 0;
        syncCount = 0;
    }

    public PatentSyncModel() {
    }

    public PatentSyncModel(Date linkTime, String userName) {
        this.linkTime = linkTime;
        this.userName = userName;
        this.done = false;
    }
}
