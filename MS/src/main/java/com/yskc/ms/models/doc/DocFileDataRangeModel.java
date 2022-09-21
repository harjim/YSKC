package com.yskc.ms.models.doc;

import com.yskc.common.utils.DateUtil;
import com.yskc.ms.models.rs.DocFileDataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.doc
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-13 15:14
 * @Description: 过程文档数据区间model
 */
public class DocFileDataRangeModel {

    private Date begin;

    private Date end;

    private List<DocFileDataModel> data;

    public DocFileDataRangeModel() {
    }

    public DocFileDataRangeModel(DocFileDataModel item) {
        this.begin = item.getMonth();
        this.end = DateUtil.getMonthLastDay(this.begin);
        data = new ArrayList<>();
        data.add(item);
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<DocFileDataModel> getData() {
        return data;
    }

    public void setData(List<DocFileDataModel> data) {
        this.data = data;
    }

    public void addDoc(DocFileDataModel item) {
        this.data.add(item);
        Date m = item.getMonth();
        if (begin.compareTo(m) > 0) {
            begin = m;
        } else if (end.compareTo(m) < 0) {
            end = DateUtil.getMonthLastDay(m);
        }
    }

}
