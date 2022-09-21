package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.params.PageParams;
import com.yskc.ms.models.projectuncollected.ProjectUnCollectedModel;

import java.util.List;

public interface ProjectUnCollectedService {
    PageModel<List<ProjectUnCollectedModel>> getList(PageParams query);
}
