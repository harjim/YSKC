package com.yskc.ms.models;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-18 17:38
 * @Description: id和title model
 */
public class MiniModel implements Serializable {

    private Integer id;

    private String title;

    private String linkTel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }
}
