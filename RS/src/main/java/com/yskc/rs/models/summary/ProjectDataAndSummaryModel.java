package com.yskc.rs.models.summary;

import java.util.List;

import com.yskc.rs.models.project.ProjectListModel;

public class ProjectDataAndSummaryModel {

	private List<ProjectListModel> projectListModels;
	private List<OtherSummaryModel> otherSummaryModels;
	
	
	public List<ProjectListModel> getProjectListModels() {
		return projectListModels;
	}
	public void setProjectListModels(List<ProjectListModel> projectListModels) {
		this.projectListModels = projectListModels;
	}
	public List<OtherSummaryModel> getOtherSummaryModels() {
		return otherSummaryModels;
	}
	public void setOtherSummaryModels(List<OtherSummaryModel> otherSummaryModels) {
		this.otherSummaryModels = otherSummaryModels;
	}
	
}
