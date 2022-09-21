package com.yskc.rs.models.docList;

import com.yskc.rs.models.params.PageParams;
import com.yskc.rs.models.sysDocument.SysDocumentModel;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2021/9/16 9:36
 * @Description:
 * @author: hsx
 */
public class QueryDocListModel extends PageParams implements Serializable{

    private Integer docListId;

    private Integer year;

    private List<Integer> documentIds;

    private List<SysDocumentModel> sysDocumentModels;

    public Integer getDocListId() {
        return docListId;
    }

    public void setDocListId(Integer docLisId) {
        this.docListId = docLisId;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Integer> getDocumentIds() {
        return documentIds;
    }

    public void setDocumentIds(List<Integer> documentIds) {
        this.documentIds = documentIds;
    }

    public List<SysDocumentModel> getSysDocumentModels() {
        return sysDocumentModels;
    }

    public void setSysDocumentModels(List<SysDocumentModel> sysDocumentModels) {
        this.sysDocumentModels = sysDocumentModels;
    }
}
