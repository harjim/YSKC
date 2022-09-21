package com.xxl.job.executor.models.innovationmember;

/**
 * @program: xxl-job
 * @description: innovation_member工具类
 * @author: cyj
 * @create: 2022-05-17 17:04
 **/
public class InnovationMemberModel {
    private Integer mType;
    private Integer year;
    private Integer companyId;
    private String realName;

    public Integer getmType() {
        return mType;
    }

    public void setmType(Integer mType) {
        this.mType = mType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
