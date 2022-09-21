package com.yskc.ms.models.projectRegister;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hck
 * on 2020/8/6 16:45
 * description:
 */
public class UploadRegisterFileModel implements Serializable {

    private Integer id;

    private List<String> filePaths;

    private Integer sign;//0 备案扫描件  1  备案文件

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(List<String> filePaths) {
        this.filePaths = filePaths;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }
}
