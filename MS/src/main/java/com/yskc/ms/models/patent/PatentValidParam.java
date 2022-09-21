package com.yskc.ms.models.patent;

import com.gargoylesoftware.htmlunit.util.NameValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patent
 * @Author: zhangdingfu
 * @CreateTime: 2019-10-28 09:03
 * @Description: 坐标, 传参
 */
public class PatentValidParam implements Serializable {
    private String x;
    private String y;
    private String x1;
    private String y1;
    private String x2;
    private String y2;
    private Integer accountId;
    private Integer status;
    private String username;
    /**
     * 专利账号密码
     */
    private String password;
    private List<Integer> ids;
    private String patentNo;
    /**
     * 手动输入密码
     */
    private String ownerPassword;

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getX1() {
        return x1;
    }

    public void setX1(String x1) {
        this.x1 = x1;
    }

    public String getY1() {
        return y1;
    }

    public void setY1(String y1) {
        this.y1 = y1;
    }

    public String getX2() {
        return x2;
    }

    public void setX2(String x2) {
        this.x2 = x2;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public PatentValidParam() {
    }

    public List<NameValuePair> getParams() {
        List<NameValuePair> param = new ArrayList<>();
        param.add(new NameValuePair("x", x));
        param.add(new NameValuePair("x1", x1));
        param.add(new NameValuePair("x2", x2));
        param.add(new NameValuePair("y", y));
        param.add(new NameValuePair("y1", y1));
        param.add(new NameValuePair("y2", y2));
        return param;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getOwnerPassword() {
        return ownerPassword;
    }

    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }
}
