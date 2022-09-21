package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectAuditDocFile;
import com.xxl.job.executor.models.audit.ProjectAuditCntModel;
import com.xxl.job.executor.models.flowInstance.AuditDocFileModel;
import com.xxl.job.executor.models.flowInstance.DocFileAuditInfoModel;
import com.xxl.job.executor.models.hightechprogress.HighTechSubmitModel;
import com.xxl.job.executor.models.hightechprogress.ProjectDeliverModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-22 15:03
 * @Description: 项目文档审核dao层
 */
@Repository
public interface ProjectAuditDocFileDao extends BaseMapper<ProjectAuditDocFile> {
    /**
     * 获取审核文档
     *
     * @param status
     * @return
     */
    List<DocFileAuditInfoModel> getAuditDocFile(@Param("status") int status);

    /**
     * 批量更新
     *
     * @param docFileIds
     * @param status
     * @return
     */
    int updateStatus(@Param("docFileIds") Set<Integer> docFileIds, @Param("status") int status);

    /**
     * 获取文档信息
     *
     * @param docFileIds
     * @return
     */
    List<AuditDocFileModel> getDocFileInfos(@Param("docFileIds") List<Integer> docFileIds);

    /**
     * 更新过程文档审核
     * @param list
     * @param now
     * @return
     */
    int updateDocFileAudit(@Param("list") List<DocFileAuditInfoModel> list,@Param("now") Date now);

    /**
     * 获取
     * @param lastTime
     * @return
     */
    List<Integer> getLastProjectIds(@Param("lastTime") Date lastTime);

    /**
     * 获取文档审核数
     * @param projectIds
     * @return
     */
    List<ProjectAuditCntModel> getDocAuditCnt(@Param("projectIds") List<Integer> projectIds);

    /**
     * 获取对应时段的数据
     * @param model
     * @return
     */
    List<ProjectDeliverModel> getList(@Param("model") HighTechSubmitModel model);
}
