package com.yskc.ms.entity.es;



import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.yskc.ms.entity.es.models.EsBaseEntity;

import java.io.Serializable;

/**
 * @DateTime: 2021/11/12 9:03
 * @Description:
 * @author: hsx
 */
@TableName("achievement_files")
public class AchievementFilesEntity extends EsBaseEntity implements Serializable {

    private static final long serialVersionUID = 2784763152406045201L;

    @TableId
    private Integer id;

    private Integer userId;

    // 成果id
    private Integer achievementId;

    // 成果资料(多文件)
    private String achievementFile;

    // 是否为代理人
    private Integer agent;

    // 成果所属人姓名
    private String ownerName;

    // 代理协议文件
    private String agentFile;

    // 是否已技术评定
    private Integer assess;

    // 评定技术类型:0：节能环保，1：生物医药
    private Integer assessType;

    // 证明文件（评定文件）
    private String assessFile;

    // 小试
    private Integer smallTest;

    // 小试证明文件
    private String smallTestFile;

    // 中试
    private Integer pilotTest;

    // 中试证明文件
    private String pilotTestFile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAchievementId() {
        return achievementId;
    }

    public void setAchievementId(Integer achievementId) {
        this.achievementId = achievementId;
    }

    public String getAchievementFile() {
        return achievementFile;
    }

    public void setAchievementFile(String achievementFile) {
        this.achievementFile = achievementFile;
    }

    public Integer getAgent() {
        return agent;
    }

    public void setAgent(Integer agent) {
        this.agent = agent;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAgentFile() {
        return agentFile;
    }

    public void setAgentFile(String agentFile) {
        this.agentFile = agentFile;
    }

    public Integer getAssess() {
        return assess;
    }

    public void setAssess(Integer assess) {
        this.assess = assess;
    }

    public Integer getAssessType() {
        return assessType;
    }

    public void setAssessType(Integer assessType) {
        this.assessType = assessType;
    }

    public String getAssessFile() {
        return assessFile;
    }

    public void setAssessFile(String assessFile) {
        this.assessFile = assessFile;
    }


    public String getSmallTestFile() {
        return smallTestFile;
    }

    public void setSmallTestFile(String smallTestFile) {
        this.smallTestFile = smallTestFile;
    }

    public Integer getSmallTest() {
        return smallTest;
    }

    public void setSmallTest(Integer smallTest) {
        this.smallTest = smallTest;
    }

    public Integer getPilotTest() {
        return pilotTest;
    }

    public void setPilotTest(Integer pilotTest) {
        this.pilotTest = pilotTest;
    }

    public String getPilotTestFile() {
        return pilotTestFile;
    }

    public void setPilotTestFile(String pilotTestFile) {
        this.pilotTestFile = pilotTestFile;
    }
}
