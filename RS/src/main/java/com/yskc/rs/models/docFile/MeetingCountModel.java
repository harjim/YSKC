package com.yskc.rs.models.docFile;

import java.io.Serializable;
import java.util.Date;

/**
 * @DateTime: 2022/2/17 14:33
 * @Description:
 * @author: hsx
 */
public class MeetingCountModel implements Serializable {

    private Date month;

    private Integer idCount;

    private Integer docFileIdCount;

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public Integer getIdCount() {
        return idCount;
    }

    public void setIdCount(Integer idCount) {
        this.idCount = idCount;
    }

    public Integer getDocFileIdCount() {
        return docFileIdCount;
    }

    public void setDocFileIdCount(Integer docFileIdCount) {
        this.docFileIdCount = docFileIdCount;
    }
}
