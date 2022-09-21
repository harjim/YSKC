package com.yskc.ms.models.patent;

import java.io.Serializable;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-02-09 16:26
 **/
public class PatentFileModel implements Serializable {
    private String file;
    private String type;
    private Integer id;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
