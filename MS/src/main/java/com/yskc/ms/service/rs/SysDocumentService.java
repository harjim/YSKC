package com.yskc.ms.service.rs;


import com.yskc.common.exception.OwnerException;
import com.yskc.ms.entity.rs.SysDocumentEntity;
import com.yskc.ms.entity.rs.models.DocListModel;
import com.yskc.ms.models.generatebuildfile.GenerateBuildFileModel;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/9/6 16:44
 * @Description:成果管理/机构建设事项
 */
public interface SysDocumentService {
    List<DocListModel> queryAppendixDocList(Integer listType, Integer companyId, String patentNo, Integer year);

    SysDocumentEntity getFileById(Integer id);

    Integer updateFile(SysDocumentEntity entity);

    Map<String, Object> getBuildFileData(Integer year,String companyName,Integer companyId,String path);

    /**
     * 获取项目查新报告
     * @param projectId
     * @return
     */
    List<SysDocumentEntity> getReportByProject(Integer projectId);
}
