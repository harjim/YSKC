package com.yskc.rs.models.stEmployee;

/**
 * @program: rs
 * @description: 导出科技管理人员
 * @author: cyj
 * @create: 2022-08-08 17:41
 **/
public class StEmployeeExportModel extends StEmployeeModel{
    private String eduLevelStr;

    public String getEduLevelStr() {
        return eduLevelStr;
    }

    public void setEduLevelStr(String eduLevelStr) {
        this.eduLevelStr = eduLevelStr;
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
