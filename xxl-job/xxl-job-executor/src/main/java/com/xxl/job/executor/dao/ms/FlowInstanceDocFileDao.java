package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.FlowInstanceDocFile;
import com.xxl.job.executor.models.flowInstance.DocFileAuditInfoModel;
import com.xxl.job.executor.models.flowInstance.FlowInstanceDocFileModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-01-25 08:30
 * @Description: 过程文档流程实例dao层
 */
@Repository
public interface FlowInstanceDocFileDao extends BaseMapper<FlowInstanceDocFile> {

    /**
     * 获取存在的实例（更新用）
     *
     * @param docFileIds
     * @return
     */
    List<FlowInstanceDocFileModel> getInstances(@Param("docFileIds") Set<Integer> docFileIds);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<FlowInstanceDocFile> list);

    /**
     * 通过实例id获取docFileId
     *
     * @param instanceIds
     * @return
     */
    List<FlowInstanceDocFile> getDocFileIds(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * 获取suggestion
     *
     * @param instanceIds
     * @return
     */
    List<DocFileAuditInfoModel> getSuggestions(@Param("instanceIds") List<Integer> instanceIds);
}
