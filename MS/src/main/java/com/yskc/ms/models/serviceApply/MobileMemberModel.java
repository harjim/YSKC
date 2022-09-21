package com.yskc.ms.models.serviceApply;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-23 17:01
 **/
public class MobileMemberModel {
    private Integer directorId;
    private String directorName;
    private Integer managerId;
    private String managerName;
    private List<ServiceMemberModel> list;

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public List<ServiceMemberModel> getList() {
        return list;
    }

    public void setList(List<ServiceMemberModel> list) {
        this.list = list;
    }
}
