package com.xxl.job.executor.entity.ms;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.util.Date;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.entity.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-10 10:33
 * @Description: 专利数据
 */
@TableName("patentData")
public class PatentDataEntity {

    @TableId
    private Integer id;
    private String patentNo;
    private String patentName;
    private String inventor;
    private String applyName;
    private Date applyDateTime;
    /**
     * 专利类型
     */
    private Integer mainType;
    /**
     * 主分类号
     */
    private String mainTypeNo;
    /**
     * 法律状态
     */
    private String caseStatus;
    /**
     * 地址
     */
    private String address;
    /**
     * 公告号
     */
    private String publicNo;
    /**
     * 公告日
     */
    private Date publicDate;
    private Date createTime;
    private Date lastUpdateTime;
    private String queryWord;
    private String anx;

    public static PatentDataEntity build(String address, Date applyDateTime, String applyName, String inventor,
                                         Integer mainType, String mainTypeNo, String patentName, Date publicDate,
                                         String publicNo, String patentNo, String caseStatus, String queryWord, String anx) {
        Date now = new Date();
        PatentDataEntity entity = new PatentDataEntity();
        entity.createTime = now;
        entity.lastUpdateTime = now;
        entity.address = subExistStr(address, 500);
        entity.applyDateTime = applyDateTime;
        entity.applyName = subExistStr(applyName, 300);
        entity.inventor = subExistStr(inventor, 500);
        entity.mainType = mainType;
        entity.mainTypeNo = subExistStr(mainTypeNo, 100);
        entity.patentName = subExistStr(patentName, 200);
        entity.publicDate = publicDate;
        entity.publicNo = subExistStr(publicNo, 20);
        entity.patentNo = loadPatentNo(subExistStr(patentNo, 50));
        entity.caseStatus = caseStatus;
        entity.queryWord = queryWord;
        entity.anx = anx;
        return entity;
    }

    private static String loadPatentNo(String patentNo) {
        patentNo = patentNo.replaceAll("CN", "").replaceAll("ZL", "").replaceAll("\\.", "");
        if (patentNo.length() == 8) {
            return patentNo + 1;
        }
        return patentNo;
    }

    public static String subExistStr(String source, int len) {
        if (source == null) {
            return null;
        }
        return source.length() <= len ? source : source.substring(0, len);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }

    public String getPatentName() {
        return patentName;
    }

    public void setPatentName(String patentName) {
        this.patentName = patentName;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public Integer getMainType() {
        return mainType;
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getMainTypeNo() {
        return mainTypeNo;
    }

    public void setMainTypeNo(String mainTypeNo) {
        this.mainTypeNo = mainTypeNo;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublicNo() {
        return publicNo;
    }

    public void setPublicNo(String publicNo) {
        this.publicNo = publicNo;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getQueryWord() {
        return queryWord;
    }

    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }

    public String getAnx() {
        return anx;
    }

    public void setAnx(String anx) {
        this.anx = anx;
    }
}
