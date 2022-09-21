package com.yskc.rs.models.company;

import com.yskc.rs.models.hightech.HighTechIncomeModel;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author: hck
 * @DateTime: 2021/7/15 11:00
 * @Description:公司首页统计数据
 */
public class CountDataModel implements Serializable {

    private CountProjectModel countProjectModel;

    private List<ProjectDataModel> projectDataModel;

    List<HighTechIncomeModel> highModels;

    private List<Map<String, Object>> rdCosts;//年度研发费用

    private List<CountRdFundModel> rdFundModels;//归集费用占比model

    private List<CountBudgetModel> countBudgetModels;//归集费用占预算比models

    public static CountDataModel build(CountProjectModel countProjectModel, List<ProjectDataModel> projectDataModels, List<HighTechIncomeModel> highModels, List<Map<String, Object>> rdCosts, List<CountRdFundModel> rdFundModels, List<CountBudgetModel> countBudgetModels) {
        CountDataModel model = new CountDataModel();
        model.setCountProjectModel(countProjectModel);
        model.setProjectDataModel(projectDataModels);
        model.setCountBudgetModels(countBudgetModels);
        model.setHighModels(highModels);
        model.setRdCosts(rdCosts);
        model.setRdFundModels(rdFundModels);
        return model;
    }

    public CountProjectModel getCountProjectModel() {
        return countProjectModel;
    }

    public void setCountProjectModel(CountProjectModel countProjectModel) {
        this.countProjectModel = countProjectModel;
    }

    public List<ProjectDataModel> getProjectDataModel() {
        return projectDataModel;
    }

    public void setProjectDataModel(List<ProjectDataModel> projectDataModel) {
        this.projectDataModel = projectDataModel;
    }

    public List<HighTechIncomeModel> getHighModels() {
        return highModels;
    }

    public void setHighModels(List<HighTechIncomeModel> highModels) {
        this.highModels = highModels;
    }

    public List<Map<String, Object>> getRdCosts() {
        return rdCosts;
    }

    public void setRdCosts(List<Map<String, Object>> rdCosts) {
        this.rdCosts = rdCosts;
    }

    public List<CountRdFundModel> getRdFundModels() {
        return rdFundModels;
    }

    public void setRdFundModels(List<CountRdFundModel> rdFundModels) {
        this.rdFundModels = rdFundModels;
    }

    public List<CountBudgetModel> getCountBudgetModels() {
        return countBudgetModels;
    }

    public void setCountBudgetModels(List<CountBudgetModel> countBudgetModels) {
        this.countBudgetModels = countBudgetModels;
    }
}
