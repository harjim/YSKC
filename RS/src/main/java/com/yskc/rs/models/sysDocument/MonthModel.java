package com.yskc.rs.models.sysDocument;

import java.util.List;

public class MonthModel {
    private String month;
    private List<SysDocumentModel> docList;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<SysDocumentModel> getDocList() {
        return docList;
    }

    public void setDocList(List<SysDocumentModel> docList) {
        this.docList = docList;
    }
}
