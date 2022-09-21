package com.yskc.ms.models.doc;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.doc
 * @Author: zhangdingfu
 * @CreateTime: 2021-04-21 10:49
 * @Description: 过程文件基础
 */
public class DocFileBaseModel {

    private Integer id;

    private String docName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
