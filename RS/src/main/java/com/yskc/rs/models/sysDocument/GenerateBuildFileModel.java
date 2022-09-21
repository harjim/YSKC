package com.yskc.rs.models.sysDocument;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.sysDocument
 * @Author: zhangdingfu
 * @CreateTime: 2021-12-16 15:13
 * @Description: 建设事项model
 */
public class GenerateBuildFileModel {
    private Integer id;
    private Integer year;
    private String docName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
