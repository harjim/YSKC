package com.yskc.rs.models.projectSummary;

import java.io.Serializable;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:54
 * @Description:项目问题总结model
 */
public class ProblemSummaryModel implements Serializable {

    private Integer id;

    private String problem;//问题

    private String solution;//方案

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
