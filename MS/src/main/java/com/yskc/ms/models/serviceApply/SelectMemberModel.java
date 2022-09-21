package com.yskc.ms.models.serviceApply;

import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: wjy
 * @create: 2022-08-18 08:48
 **/
public class SelectMemberModel {
    private Integer techManagerId;
    private Integer techDirectorId;
    private Integer finaManagerId;
    private Integer finaDirectorId;
    private String techManagerName;
    private String techDirectorName;
    private String finaManagerName;
    private String finaDirectorName;
    private List<ServiceMemberModel> techList;
    private List<ServiceMemberModel> finaList;
    private List<ServiceMemberModel> otherList;

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

    public String getTechManagerName() {
        return techManagerName;
    }

    public void setTechManagerName(String techManagerName) {
        this.techManagerName = techManagerName;
    }

    public String getTechDirectorName() {
        return techDirectorName;
    }

    public void setTechDirectorName(String techDirectorName) {
        this.techDirectorName = techDirectorName;
    }

    public String getFinaManagerName() {
        return finaManagerName;
    }

    public void setFinaManagerName(String finaManagerName) {
        this.finaManagerName = finaManagerName;
    }

    public String getFinaDirectorName() {
        return finaDirectorName;
    }

    public void setFinaDirectorName(String finaDirectorName) {
        this.finaDirectorName = finaDirectorName;
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
