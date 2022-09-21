package com.xxl.job.executor.models.policy;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.models.ding
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 17:25
 * @Description: 政策来源model
 */
public class PolicySourceModel {
    private Integer id;

    private String typeUrl;

    private String sname;

    private String stype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeUrl() {
        return typeUrl;
    }

    public void setTypeUrl(String typeUrl) {
        this.typeUrl = typeUrl;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getStype() {
        return stype;
    }

    public void setStype(String stype) {
        this.stype = stype;
    }

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", typeUrl='" + typeUrl + '\'' +
                ", sname='" + sname + '\'' +
                ", stype='" + stype + '\'' +
                '}';
    }
}
