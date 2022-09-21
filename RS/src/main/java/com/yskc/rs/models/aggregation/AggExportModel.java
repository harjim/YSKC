package com.yskc.rs.models.aggregation;

import com.yskc.common.utils.DateUtil;

import java.util.Date;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.aggregation
 * @Author: zhangdingfu
 * @CreateTime: 2020-08-04 14:39
 * @Description: 导出model
 */
public class AggExportModel {

    private Date date;
    private String dateStr;
    private String rdTitle;
    private Integer rdType;

    public String getRdTitle() {
        return rdTitle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateStr() {
        if(dateStr != null){
            return  dateStr;
        }
        return DateUtil.format(date, DateUtil.DEFAULT_DATE_FORMAT);
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public Integer getRdType() {
        return rdType;
    }

    public void setRdType(Integer rdType) {
        this.rdType = rdType;
    }

    public void sum(AggExportModel source) {
    }

    public AggExportModel createSumObj(){
        return new AggExportModel();
    }

}
