package com.yskc.ms.models.serviceApply;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-26 10:35
 **/
public class ApplyEditModel {
    private Integer id;
    private Integer techManagerId;
    private Integer techDirectorId;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private List<ServiceMemberModel> techList;
    private List<ServiceMemberModel> finaList;
    private List<ServiceMemberModel> otherList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechManagerId() {
        return techManagerId;
    }

    public void setTechManagerId(Integer techManagerId) {
        this.techManagerId = techManagerId;
    }

    public Integer getTechDirectorId() {
        return techDirectorId;
    }

    public void setTechDirectorId(Integer techDirectorId) {
        this.techDirectorId = techDirectorId;
    }

    public Integer getFinaManagerId() {
        return finaManagerId;
    }

    public void setFinaManagerId(Integer finaManagerId) {
        this.finaManagerId = finaManagerId;
    }

    public Integer getFinaDirectorId() {
        return finaDirectorId;
    }

    public void setFinaDirectorId(Integer finaDirectorId) {
        this.finaDirectorId = finaDirectorId;
    }

    public List<ServiceMemberModel> getTechList() {
        return techList;
    }

    public void setTechList(List<ServiceMemberModel> techList) {
        this.techList = techList;
    }

    public List<ServiceMemberModel> getFinaList() {
        return finaList;
    }

    public void setFinaList(List<ServiceMemberModel> finaList) {
        this.finaList = finaList;
    }

    public List<ServiceMemberModel> getOtherList() {
        return otherList;
    }

    public void setOtherList(List<ServiceMemberModel> otherList) {
        this.otherList = otherList;
    }
}
