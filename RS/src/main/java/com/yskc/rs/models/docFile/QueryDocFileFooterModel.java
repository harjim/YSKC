package com.yskc.rs.models.docFile;

import com.yskc.rs.models.params.PageParams;

import java.io.Serializable;
import java.util.List;

/**
 * @DateTime: 2022/3/8 8:27
 * @Description:
 * @author: hsx
 */
public class QueryDocFileFooterModel extends PageParams implements Serializable {

    //rd
    private List<String> rdTitles;

    //年份
    private Integer year;

    private Integer companyId;

    public List<String> getRdTitles() {
        return rdTitles;
    }

    public void setRdTitles(List<String> rdTitles) {
        this.rdTitles = rdTitles;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
