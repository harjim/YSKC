package com.yskc.rs.models.reviewCommittee;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/9/1 13:35
 * description:添加评审成员
 */
public class ReviewAddModel implements Serializable {

    private List<String> enumbers;//成员编号列表

    private Integer year;

    public List<String> getEnumbers() {
        return enumbers;
    }

    public void setEnumbers(List<String> enumbers) {
        this.enumbers = enumbers;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
