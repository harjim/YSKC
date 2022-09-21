package com.yskc.ms.models.doctemplate;

import com.yskc.ms.models.params.PageParams;

/**
 * Created by hck
 * on 2020/6/5 14:47
 * description:
 */
public class QueryDocModel  extends PageParams {

    private Integer listType;
    private String docName;

    public Integer getListType() {
        return listType;
    }

    public void setListType(Integer listType) {
        this.listType = listType;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }
}
