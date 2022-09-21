package com.yskc.ms.models.expert;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/5 8:55
 * description:
 */
public class QueryExpertModel extends PageParams {

    private String realName;
    private String industryCode;
    private String beginDate;
    private String endDate;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
