package com.yskc.ms.models.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.user
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-01 08:29
 * @Description: 操作日志用户
 */
public class OperationLogUserModel {
    private Integer id;

    private String userName;

    private String realName;

    private String tel;

    private String postion;

    private String deptName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rsLastOperationTime;

    private Integer operationCnt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getRsLastOperationTime() {
        return rsLastOperationTime;
    }

    public void setRsLastOperationTime(Date rsLastOperationTime) {
        this.rsLastOperationTime = rsLastOperationTime;
    }

    public Integer getOperationCnt() {
        return operationCnt == null ? 0 : operationCnt;
    }

    public void setOperationCnt(Integer operationCnt) {
        this.operationCnt = operationCnt;
    }
}
