package com.yskc.ms.entity.rs.models;

import com.yskc.ms.entity.ms.User;
import com.yskc.ms.models.user.UserModel;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.entity.rs.models
 * @Author: wangxing
 * @CreateTime: 2019-09-12 11:31
 * @Description: 服务人员model
 */
public class ManagerUserModel {
    private  Integer companyId;
    private List<User> modelList ;
    private  Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<User> getModelList() {
        return modelList;
    }

    public void setModelList(List<User> modelList) {
        this.modelList = modelList;
    }
}
