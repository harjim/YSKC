package com.yskc.rs.models.wechat;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.wechat
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-27 10:30
 * @Description: openid model
 */
public class EmployeeOpenidModel {
    private Integer id;
    private String companyName;
    private Integer companyId;
    private String enumber;
    private String ename;
    private String openid;
    private String unionid;
    private Boolean login;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public EmployeeOpenidModel() {
    }

    public EmployeeOpenidModel(String openid, Boolean login) {
        this.openid = openid;
        this.login = login;
    }

    public void trim() {
        if (null != companyName) {
            companyName = companyName.trim();
        }
        if (null != enumber) {
            enumber = enumber.trim();
        }
        if(null != ename){
            ename = ename.trim();
        }
    }
}
