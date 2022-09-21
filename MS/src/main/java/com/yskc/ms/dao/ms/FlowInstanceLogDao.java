package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.es.UserEntity;
import com.yskc.ms.entity.ms.FlowInstanceLog;
import com.yskc.ms.models.contract.ContractNodeModel;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.params.FlowInstanceLogParams;
import com.yskc.ms.models.qualityscore.EngineerModel;
import com.yskc.ms.models.qualityscore.QualityScoreModel;
import com.yskc.ms.models.qualityscore.QueryQualityScoreModel;
import com.yskc.ms.models.projectAudit.FlowInstanceLogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by hck
 * on 2020/12/26 9:17
 * description:
 */
@Repository
public interface FlowInstanceLogDao extends BaseMapper<FlowInstanceLog> {
    /**
     * 获取项目审核日志
     *
     * @param instanceIds
     * @return
     */
    List<FlowInstanceLogModel> getInstanceLog(@Param("instanceIds") List<Integer> instanceIds);

    /**
     * 审核日志查询
     * @param instanceId
     * @return
     */
    List<FeeFlowInstanceLogModel> getAuditLog(@Param("instanceId")Integer instanceId);

    /**
     * 批量添加
     *
     * @param flowInstanceLogs
     * @return
     */
    int addBatch(@Param("flowInstanceLogs") List<FlowInstanceLog> flowInstanceLogs);

    List<FlowInstanceLogModel> getLogList(@Param("pagination") Pagination pagination, @Param("param") FlowInstanceLogParams param);

    /**
     * 获取最后一条非自动通过的日志
     *
     * @param instanceId
     * @return
     */
    FlowInstanceLogModel getLastLog(@Param("instanceId") Integer instanceId);

    /**
     * 查每个节点最新记录
     * @param instanceId
     * @return
     */
    List<ContractNodeModel> getLastLogs(@Param("instanceId") Integer instanceId,@Param("nodeNumber") Integer nodeNumber,
                                        @Param("status") Integer status);

    /**
     * 批量插入激活日志
     * @param instanceLogs
     * @return
     */
    int insertAll(@Param("instanceLogs") List<FlowInstanceLog> instanceLogs);

    /**
     * 获取评分审核人信息
     * @param model
     * @param fileIds
     * @return
     */
    List<EngineerModel> getSubmitter(@Param("model") QueryQualityScoreModel model, @Param("fileIds") List<Integer> fileIds);

    /**
     * 获取查新报告评分审核人信息
     * @param list
     * @return
     */
    List<EngineerModel> getCheckSubmitter(@Param("list") List<Integer> list);
    /**
     * 根据公司获取评分人审核信息
     * @param model
     * @return
     */
    List<EngineerModel> getSubmitterByCompany(@Param("model") QueryQualityScoreModel model);

    /**
     * 根据文件获取评分人审核信息
     * @param fileId
     * @return
     */
    List<EngineerModel> getSubmitterByFileId(@Param("fileId") Integer fileId);

    /**
     * 质量评分按文件Id获取一提时间
     * @param fileIds
     * @return
     */
    FlowInstanceLog getSubmitTimeByFileIds(@Param("fileIds") List<Integer> fileIds);

    /**
     * 质量评分按项目Id获取一提时间
     * @param model
     * @return
     */
    FlowInstanceLog getSubmitTimeByProjectId(@Param("model")QualityScoreModel model);

    /**
     * 质量评分按公司id获取一提时间
     * @param model
     * @return
     */
    FlowInstanceLog getSubmitTimeByCompany(@Param("model") QualityScoreModel model);

    /**
     * 根据提交时间获取二级主管审核时间
     * @param log
     * @return
     */
    List<Date> getAuditTime(@Param("log") FlowInstanceLog log);
}
