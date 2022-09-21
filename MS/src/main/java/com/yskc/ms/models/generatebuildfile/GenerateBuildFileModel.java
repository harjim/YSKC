package com.yskc.ms.models.generatebuildfile;

/**
 * @program: ms
 * @description: 建设事项model
 * @author: cyj
 * @create: 2022-01-13 11:44
 **/
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
