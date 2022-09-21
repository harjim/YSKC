package com.yskc.rs.models.highscore;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.highscore
 * @Author: zhangdingfu
 * @CreateTime: 2021-11-08 15:04
 * @Description: 高新技术专利model(专利 ， 电路集成 ， 软件著作)
 */
public class HighTechPatentModel {

    /**
     * 类型，1类，2类
     */
    private Integer type;

    /**
     * 授权日期，颁证日期，发布日期
     */
    private Date issueDate;

    private Integer claimNum;

    private Integer usedCnt;

    private Integer source;

    private Integer patentCnt;

    public static HighTechPatentModel buildDefault() {
        HighTechPatentModel patent = new HighTechPatentModel();
        patent.patentCnt = 0;
        return patent;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getClaimNum() {
        return claimNum != null ? claimNum : 0;
    }

    public void setClaimNum(Integer claimNum) {
        this.claimNum = claimNum;
    }

    public Integer getUsedCnt() {
        return usedCnt;
    }

    public void setUsedCnt(Integer usedCnt) {
        this.usedCnt = usedCnt;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getPatentCnt() {
        return patentCnt;
    }

    public void setPatentCnt(Integer patentCnt) {
        this.patentCnt = patentCnt;
    }

    public void sum(HighTechPatentModel patent) {
        // 当前发布日期为空或大于下一个发布日期 且 当前使用次数 大于 下一个使用次数
        if ((this.issueDate != null && this.issueDate.compareTo(patent.getIssueDate()) > 0) && (this.usedCnt != null && this.usedCnt > patent.usedCnt)) {
            this.issueDate = patent.issueDate;
            this.usedCnt = patent.usedCnt;
        }
        patentCnt++;
    }

}
