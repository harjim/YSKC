package com.yskc.ms.models.excel;

import com.yskc.common.annotation.Excel;

import java.io.Serializable;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.models.excel
 * @Author: wangxing
 * @CreateTime: 2019-10-25 15:28
 * @Description: 专利导入Serializable
 */
public class PatentExcel implements Serializable {
    private String patentNo;

    public String getPatentNo() {
        return patentNo;
    }

    public void setPatentNo(String patentNo) {
        this.patentNo = patentNo;
    }
}
