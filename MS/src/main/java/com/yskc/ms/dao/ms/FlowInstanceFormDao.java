package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.FlowInstanceFormEntity;
import com.yskc.ms.models.flowInstance.FlowInstanceInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * on 2020/12/18 17:06
 * description:
 * @author wjy
 */
@Repository
public interface FlowInstanceFormDao extends BaseMapper<FlowInstanceFormEntity> {

    List<FlowInstanceFormEntity> getStatus(@Param("formIds")List<Integer> formIds, @Param("moduleId")Integer moduleId);

    Integer getStatusByForm(@Param("formId")Integer formId, @Param("moduleId")Integer moduleId);

    Integer addOrUpdate(@Param("item") FlowInstanceFormEntity item);

    FlowInstanceInfoModel getInstanceInfo(@Param("id") Integer id);

    List<FlowInstanceFormEntity> getHasPermission(@Param("serviceIds") Set<Integer> serviceIds, @Param("moduleId")Integer moduleId,
                                                  @Param("userId")Integer userId);
}
