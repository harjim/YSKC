package com.yskc.rs.models.employee;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.models.employee
 * @Author: zhangdingfu
 * @CreateTime: 2022-05-26 14:34
 * @Description: 研发人员导出model
 */
public class RdEmployeeExportModel extends RdEmployeeModel {

    private String etypeStr;
    private String edLevelStr;

    public String getEtypeStr() {
        return etypeStr;
    }

    public void setEtypeStr(String etypeStr) {
        this.etypeStr = etypeStr;
    }

    public String getEdLevelStr() {
        return edLevelStr;
    }

    public void setEdLevelStr(String edLevelStr) {
        this.edLevelStr = edLevelStr;
    }

    public String getGenderStr() {
        Integer gender = getGender();
        if (gender != null) {
            if (gender == 1) {
                return "女";
            }
            if (gender == 2) {
                return "男";
            }
        }
        return "无";
    }
}
