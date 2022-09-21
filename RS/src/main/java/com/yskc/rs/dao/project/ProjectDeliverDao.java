package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.hightech.ProjectDeliverEntity;
import com.yskc.rs.models.hightech.QueryHighTechAuditModel;
import com.yskc.rs.models.hightechprogress.HighTechAmountModel;
import com.yskc.rs.models.project.DeliverLogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * p_deliver表dao层
 */
@Repository
public interface ProjectDeliverDao extends BaseMapper<ProjectDeliverEntity> {

    /**
     * 查询高新进度节点
     * @param beginDate
     * @param endDate
     * @param projectIds
     * @return
     */
    List<ProjectDeliverEntity> getNodeList(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("projectIds") Set<Integer> projectIds);

    /**
     * 查询高新进度统计明细
     * @param beginDate
     * @param endDate
     * @param companyIds
     * @return
     */
    List<HighTechAmountModel> getTotalList(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("companyIds") List<Integer> companyIds);

    /**
     * 查询是否有此node
     * @param model
     * @return
     */
    ProjectDeliverEntity getNodeByModel(@Param("model") QueryHighTechAuditModel model);

    /**
     * 添加node
     * @param model
     * @return
     */
    Integer addNode(@Param("model")QueryHighTechAuditModel model);

    /**
     * 更新node
     * @param model
     * @return
     */
    Boolean updateNode(@Param("model")QueryHighTechAuditModel model,@Param("lastUpdatorId")Integer lastUpdatorId,
                       @Param("msLastUpdatorId")Integer msLastUpdatorId,@Param("lastUpdateTime")Date lastUpdateTime);

    /**
     * 查询日志
     * @param deliverId
     * @param companyId
     * @return
     */
    List<DeliverLogModel> getLogList(@Param("deliverId")Integer deliverId,@Param("companyId")Integer companyId);

    /**
     * 批量修改数据当前审核状态以及节点
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectDeliverEntity> list);

    /**
     * 根据id获取对应数据
     * @param ids
     * @return
     */
    List<ProjectDeliverEntity> selectByIds(@Param("ids") List<Integer> ids);
}
