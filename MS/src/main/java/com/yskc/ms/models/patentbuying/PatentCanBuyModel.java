package com.yskc.ms.models.patentbuying;

import cn.hutool.core.date.DateUtil;
import com.yskc.ms.enums.PatentMainTypeEnum;

import java.util.Date;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.patentbuying
 * @Author: zhangdingfu
 * @CreateTime: 2021-09-10 09:21
 * @Description: 专利可够用model
 */
public class PatentCanBuyModel {
    private Integer id;
    private Integer seq;
    private String patentNo;
    private String patentName;
    private Integer mainType;
    private String inventor;
    private String files;
    private String patentSeaFile;
    private Date applyDateTime;

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

    public String getMainType() {
        return PatentMainTypeEnum.getMainName(mainType);
    }

    public void setMainType(Integer mainType) {
        this.mainType = mainType;
    }

    public String getInventor() {
        return inventor;
    }

    public void setInventor(String inventor) {
        this.inventor = inventor;
    }

    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String getPatentSeaFile() {
        return patentSeaFile;
    }

    public void setPatentSeaFile(String patentSeaFile) {
        this.patentSeaFile = patentSeaFile;
    }

    public Date getApplyDateTime() {
        return applyDateTime;
    }

    public void setApplyDateTime(Date applyDateTime) {
        this.applyDateTime = applyDateTime;
    }

    public String getApplyDateTimeStr() {
        if (null == applyDateTime) {
            return "";
        }
        return DateUtil.formatDate(applyDateTime);
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
