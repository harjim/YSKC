package com.yskc.rs.models.docFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: hck
 * @DateTime: 2021/4/15 8:36
 * @Description:
 */
public class TissueFormModel implements Serializable {
    /**
     * 产出数量
     */
    private String actualPO;
    /**
     * 试产地点
     */
    private String addr;
    /**
     * 试制时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date beginDate;
    /**
     * 试制问题
     */
    private String problem;
    /**
     * 解决方案
     */
    private String solution;
    /**
     * 实施情况
     */
    private String solutionState;
    /**
     * 反馈人工号
     */
    private String enumber;
    /**
     * 反馈人
     */
    private String ename;

    public String getActualPO() {
        return actualPO;
    }

    public void setActualPO(String actualPO) {
        this.actualPO = actualPO;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionState() {
        return solutionState;
    }

    public void setSolutionState(String solutionState) {
        this.solutionState = solutionState;
    }

    public String getEnumber() {
        return enumber;
    }

    public void setEnumber(String enumber) {
        this.enumber = enumber;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }
}
