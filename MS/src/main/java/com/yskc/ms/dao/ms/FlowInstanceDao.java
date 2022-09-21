package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FlowInstance;
import com.yskc.ms.entity.ms.FlowInstanceCurNode;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.flow.FlowListModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import com.yskc.ms.models.flowInstance.FeeFlowInstanceLogModel;
import com.yskc.ms.models.flowInstance.FlowInstanceModel;
import com.yskc.ms.models.params.MyAuditParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:08
 * description:
 */
@Repository
public interface FlowInstanceDao extends BaseMapper<FlowInstance> {
    /**
     * 根据流程获取实例
     *
     * @param flowIds
     * @return
     */
    List<FlowInstance> getByFlow(@Param("flowIds") List<Integer> flowIds);

    /**
     * 审核日志查询
     *
     * @param instanceId
     * @return
     */
    List<FeeFlowInstanceLogModel> getAuditFlow(@Param("instanceId") Integer instanceId, @Param("status") Integer status);

    /**
     * 判断用户有无审核权限
     *
     * @param instanceId
     * @param curNodeId
     * @param userId
     * @return
     */
    FlowInstanceUser getUserPerms(@Param("instanceId") Integer instanceId, @Param("curNodeId") Integer curNodeId, @Param("userId") Integer userId);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<FlowInstance> list);

    /**
     * 获取我的审核列表
     *
     * @param page
     * @param params
     * @param userId
     * @return
     */
    List<FlowInstanceModel> getList(@Param("page") Pagination page, @Param("params") MyAuditParams params, @Param("userId") Integer userId);

    /**
     * 获取审核参与人
     *
     * @param list
     * @return
     */
    List<FlowInstanceModel> getRealName(@Param("list") List<Integer> list);

    /**
     * 获取审核用户
     *
     * @param ids
     * @param userId
     * @param getMaster
     * @return
     */
    List<FlowInstanceUser> getInstanceUsers(@Param("ids") List<Integer> ids, @Param("userId") Integer userId, @Param("getMaster") Integer getMaster);

    /**
     * 获取正在使用的节点的流程列表
     *
     * @param nodeIds
     * @return
     */
    List<FlowInstance> getByNode(@Param("nodeIds") List<Integer> nodeIds);

    /**
     * 通过公司id获取流程
     *
     * @param userId
     * @param companyIds
     * @return
     */
    List<FlowInstanceModel> getByCompanyIds(@Param("userId") Integer userId, @Param("companyIds") List<Integer> companyIds);

    /**
     * 查询流程列表
     *
     * @param query
     * @param page
     * @param dataPerm
     * @return
     */
    List<FlowListModel> search(@Param("query") QueryFlowModel query, @Param("dataPerm") DataPermModel dataPerm, @Param("page") Pagination page);

    /**
     * 更新最后更新时间
     *
     * @param date
     * @param instanceIds
     */
    void updateTime(@Param("date") Date date, @Param("ids") List<Integer> instanceIds);

    /**
     * 获取实例信息
     *
     * @param ids
     * @param status
     * @return
     */
    List<FlowInstance> getInstanceInfos(@Param("ids") List<Integer> ids, @Param("status") Integer status);

    /**
     * 更新节点
     *
     * @param list
     * @param now
     * @param userId
     */
    void updateNodes(@Param("now") Date now, @Param("userId") Integer userId, @Param("list") List<FlowInstanceCurNode> list);

    /**
     * 更新所有节点
     *
     * @param status
     * @param instanceIds
     * @param userId
     * @param now
     */
    void updateAllNodes(@Param("status") Integer status, @Param("instanceIds") List<Integer> instanceIds,
                        @Param("userId") Integer userId, @Param("now") Date now);

    /**
     * 获取 最后节点
     *
     * @param instanceId
     * @return
     */
    FlowInstanceCurNode getLastNode(@Param("instanceId") Integer instanceId);

    /**
     * 获取节点个数
     *
     * @param list
     * @return
     */
    List<FlowListModel> getNodeCnt(@Param("list") List<FlowListModel> list);
}
