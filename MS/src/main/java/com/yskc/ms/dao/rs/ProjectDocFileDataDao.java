package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ProjectDocFileDataEntity;
import com.yskc.ms.models.projectAudit.ReportListModel;
import com.yskc.ms.models.rs.DocFileDataModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDocFileDataDao extends BaseMapper<ProjectDocFileDataEntity> {
    /**
     * 获取项目文档数据
     *
     * @param pDocFileId
     * @return
     */
    DocFileDataModel getData(@Param("pDocFileId") Integer pDocFileId);

    /**
     * 获取项目所有过程文件
     * ids为空，则按项目获取文档
     * @param ids
     * @param projectId
     * @return
     */
    List<DocFileDataModel> getFileDatas(@Param("projectId") Integer projectId, @Param("ids") List<Integer> ids);

    /**
     * 获取公司立项报告/查新报告
     *
     * @param year
     * @param companyId
     * @param docFileId 27立项报告 38 查新报告
     * @return
     */
    List<ReportListModel> getReportList(@Param("year") Integer year, @Param("companyId") Integer companyId, @Param("docFileId") Integer docFileId);

    /**
     * 根据pDocFileId获取模板数据
     *
     * @param pDocFileId
     * @return
     */
    ProjectDocFileDataEntity getByDocId(@Param("pDocFileId") Integer pDocFileId);
}
