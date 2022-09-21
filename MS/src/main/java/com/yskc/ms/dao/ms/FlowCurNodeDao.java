package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.FlowCurNodeEntity;
import com.yskc.ms.entity.ms.FlowEntity;
import com.yskc.ms.entity.ms.FlowInstanceUser;
import com.yskc.ms.models.flow.FlowModel;
import com.yskc.ms.models.flow.QueryFlowModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/12/15 11:00
 * description:
 */
@Repository
public interface FlowCurNodeDao extends BaseMapper<FlowCurNodeEntity> {
    /**
     * 修改节点状态
     * @param list
     * @return
     */
    Integer updateList(@Param("list")List<FlowCurNodeEntity> list, @Param("userId")Integer userId,
                       @Param("now")Date now,@Param("closed")Boolean closed,@Param("auditUsers")String auditUsers);

    /**
     * 获取可取消流程节点
     * @param ids
     * @return
     */
    List<FlowInstanceUser> getCancelNode(@Param("ids")List<Integer> ids);

    /**
     * 获取可撤回流程节点
     * @param ids
     * @return
     */
    List<FlowInstanceUser> getRecallNode(@Param("ids")List<Integer> ids);
}
