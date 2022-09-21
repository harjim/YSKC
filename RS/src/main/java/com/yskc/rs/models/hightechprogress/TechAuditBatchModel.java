package com.yskc.rs.models.hightechprogress;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/5/27 9:06
 * @Description: 高新进度技术批量审核model
 * @author: hsx
 */
public class TechAuditBatchModel implements Serializable {

    private List<Integer> ids;

    private String suggestion;

    private Integer status;

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
