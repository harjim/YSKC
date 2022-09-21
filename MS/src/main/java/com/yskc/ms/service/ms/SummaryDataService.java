package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.projectsummary.ProjectSummaryModel;
import com.yskc.ms.models.projectsummary.QuerySummaryModel;

import java.util.List;

/**
 * Created by hck
 * on 2020/11/10 11:44
 * description:
 */
public interface SummaryDataService {
    PageModel<List<ProjectSummaryModel>> getList(QuerySummaryModel model, DataPermModel dataPerm);
}
