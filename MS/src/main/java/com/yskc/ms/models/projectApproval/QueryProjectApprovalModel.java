package com.yskc.ms.models.projectApproval;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/16 15:24
 * description:
 */
public class QueryProjectApprovalModel extends PageParams {

    private String pname;//项目名称

    private String companyName;//客户名称

    private Integer year;//年份

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
