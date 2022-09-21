package com.yskc.rs.models.hightechprogress;

import java.io.Serializable;
import java.util.Map;

/**
 * @program: rs
 * @description: 高新统计明细
 * @author: cyj
 * @create: 2022-05-30 17:32
 **/
public class HighTechTotalModel implements Serializable {
    private Integer node;
    private Integer audit;
    private Integer pApplication;
    private Integer pAccept;
    private Map<Integer,Integer> techMap;
    private Map<Integer,Integer> finaMap;

    public Map<Integer, Integer> getTechMap() {
        return techMap;
    }

    public void setTechMap(Map<Integer, Integer> techMap) {
        this.techMap = techMap;
    }

    public Map<Integer, Integer> getFinaMap() {
        return finaMap;
    }

    public void setFinaMap(Map<Integer, Integer> finaMap) {
        this.finaMap = finaMap;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }

    public Integer getpApplication() {
        return pApplication;
    }

    public void setpApplication(Integer pApplication) {
        this.pApplication = pApplication;
    }

    public Integer getpAccept() {
        return pAccept;
    }

    public void setpAccept(Integer pAccept) {
        this.pAccept = pAccept;
    }

}
