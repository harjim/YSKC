package com.yskc.docservice.models.rs.docfile;

/**
 * @BelongsProject:
 * @BelogsPackage: src/main/java/com/yskc/docservice/models/rs/docfile
 * @Author: hm
 * @CreateTime: 2022/9/21
 * @Description: 研发项目资料清单 docCheckList
 */
public class PDataListFormModel {
    /**
     * 序号
     */
    private Integer docSeq;

    /**
     * 阶段
     */
    private String stageType;

    /**
     * 文件名称
     */
    private String docFileName;

    /**
     * 文件编号
     */
    private String documentNumber;

    public Integer getDocSeq() {
        return docSeq;
    }

    public void setDocSeq(Integer docSeq) {
        this.docSeq = docSeq;
    }

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
}
