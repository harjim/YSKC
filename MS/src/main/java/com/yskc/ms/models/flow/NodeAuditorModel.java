package com.yskc.ms.models.flow;

/**
 * Created by hck
 * on 2020/12/18 11:15
 * description:
 */
public class NodeAuditorModel {

    private Integer dataId;

    private String userName;

    private Integer level;

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
