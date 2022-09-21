package com.yskc.ms.models.newexpert.talentrequirement;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @program: ms
 * @description: 人才需求列表查询模型
 * @author: cyj
 * @create: 2021-12-08 10:25
 **/
public class QueryTalentRequirementModel extends PageParams implements Serializable {
    //职位名称
    private String job;

    //学历 0：研究生，1：本科，2：大专，3：学历不限（其他）
    private Integer eduLevel;

    //工作经验(年限) 0：1-3年，1：3-5年、2：5-10年，3：10年以上，4: 经验不限
    private Integer workYear;

    // 职位类型
    // 0：技术，1：产品，2：设计，3：运营，4：市场，5：人事/财务/行政，6：高级管理，7：销售
    // 8：传媒，9：金融，10：教育培训，11：医疗健康，12：采购/贸易，13：供应链/物流，14：房地产/建筑
    // 15：农/林/牧/渔，16：咨询/翻译/法律，17：旅游，18：服务业，19：生产制造，99：不限
    private Integer jobType;


    // 招聘类型：0：社会招聘，1：校园招聘
    private Integer recruitment;

    private Integer msCreatorId;

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getEduLevel() {
        return eduLevel;
    }

    public void setEduLevel(Integer eduLevel) {
        this.eduLevel = eduLevel;
    }

    public Integer getWorkYear() {
        return workYear;
    }

    public void setWorkYear(Integer workYear) {
        this.workYear = workYear;
    }

    public Integer getJobType() {
        return jobType;
    }

    public void setJobType(Integer jobType) {
        this.jobType = jobType;
    }

    public Integer getRecruitment() {
        return recruitment;
    }

    public void setRecruitment(Integer recruitment) {
        this.recruitment = recruitment;
    }

    public Integer getMsCreatorId() {
        return msCreatorId;
    }

    public void setMsCreatorId(Integer msCreatorId) {
        this.msCreatorId = msCreatorId;
    }
}
