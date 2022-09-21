package com.yskc.rs.models.docFile;

import java.io.Serializable;

/**
 * @program: rs
 * @description: 会议纪要相关数据
 * @author: wjy
 * @create: 2022-06-25 10:58
 **/
public class MeetingSignInModel implements Serializable {
    private Integer id;
    private String stageKey;
    private String docFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStageKey() {
        return stageKey;
    }

    public void setStageKey(String stageKey) {
        this.stageKey = stageKey;
    }

    public String getDocFileName() {
        return docFileName;
    }

    public void setDocFileName(String docFileName) {
        this.docFileName = docFileName;
    }
}
