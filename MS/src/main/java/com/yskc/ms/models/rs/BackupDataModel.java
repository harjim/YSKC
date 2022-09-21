package com.yskc.ms.models.rs;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/5/14 11:03
 * @Description:备查资料model
 */
public class BackupDataModel implements Serializable {

    private Map<String, EmployeeSelectModel> footMap;//页脚数据

    private Map<String, Object> commonMap;//过程文件共用数据

    private List<DocFileInfoModel> backupDatas;//备查资料数据

    public Map<String, EmployeeSelectModel> getFootMap() {
        return footMap;
    }

    public void setFootMap(Map<String, EmployeeSelectModel> footMap) {
        this.footMap = footMap;
    }

    public Map<String, Object> getCommonMap() {
        return commonMap;
    }

    public void setCommonMap(Map<String, Object> commonMap) {
        this.commonMap = commonMap;
    }

    public List<DocFileInfoModel> getBackupDatas() {
        return backupDatas;
    }

    public void setBackupDatas(List<DocFileInfoModel> backupDatas) {
        this.backupDatas = backupDatas;
    }
}
