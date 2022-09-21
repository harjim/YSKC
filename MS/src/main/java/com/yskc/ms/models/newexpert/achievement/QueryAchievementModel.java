package com.yskc.ms.models.newexpert.achievement;

import com.yskc.ms.models.params.PageParams;

import java.io.Serializable;

/**
 * @DateTime: 2021/11/13 9:45
 * @Description:
 * @author: hsx
 */
public class QueryAchievementModel extends PageParams implements Serializable {

    //成果名称
    private String achievementName;

    private Integer status;

    //持有人姓名
    private Integer userName;

    // 领域分类
    private String researchArea;

    /**
     * 合作方式:
     * 0：技术转让，1：技术服务，2：技术许可
     * 3：技术融资，4：技术授权，5：其他
     */
    private Integer cooperateType;

    /**
     * 技术成熟度: 1：1级-基本原理被发现或报告，2：2级-技术概念或用途被阐明，3：3级-关键功能或特征的概念验证
     * 4：4级-实验室环境下的部件或实验模型验证，5：5级-相关环境下的部件或时间模型验证，6：6级-相关环境下的系统/子系统模型或样机验证
     * 7：7级-模拟使用环境下的系统样机验证，8：8级-实际系统完成实验验证，9：9级-实际系统完成使用验证
     */
    private String trl;
    /**
     * 资助情况：0：863，1：973，2：科技重大项目，3：自然科学基金，
     * 4：国家科技支撑计划，5：科技型中小企业创新基金，6：其他
     */
    private Integer fundingType;

    private Integer type;

    public String getAchievementName() {
        return achievementName;
    }

    public void setAchievementName(String achievementName) {
        this.achievementName = achievementName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserName() {
        return userName;
    }

    public void setUserName(Integer userName) {
        this.userName = userName;
    }

    public String getResearchArea() {
        return researchArea;
    }

    public void setResearchArea(String researchArea) {
        this.researchArea = researchArea;
    }

    public Integer getCooperateType() {
        return cooperateType;
    }

    public void setCooperateType(Integer cooperateType) {
        this.cooperateType = cooperateType;
    }

    public String getTrl() {
        return trl;
    }

    public void setTrl(String trl) {
        this.trl = trl;
    }

    public Integer getFundingType() {
        return fundingType;
    }

    public void setFundingType(Integer fundingType) {
        this.fundingType = fundingType;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
