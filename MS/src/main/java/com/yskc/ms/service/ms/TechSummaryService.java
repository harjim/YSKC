package com.yskc.ms.service.ms;

import com.yskc.common.model.PageResult;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.techsummary.QueryTechSummaryModel;
import com.yskc.ms.models.techsummary.TechStageFilesModel;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-10-15 09:03
 * @Description: 技改汇总业务接口层
 */
public interface TechSummaryService {

    /**
     * 获取技改汇总列表
     *
     * @param query
     * @param dataPerm
     * @return
     */
    PageResult getList(QueryTechSummaryModel query, DataPermModel dataPerm);

    /**
     * 获取技改资料清单
     *
     * @param projectId
     * @return
     */
    Map<String, Map<String, List<TechStageFilesModel>>> getStageFiles(Integer projectId);
}
