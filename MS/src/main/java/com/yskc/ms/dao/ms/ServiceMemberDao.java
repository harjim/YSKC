package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ServiceMemberEntity;
import com.yskc.ms.models.serviceApply.QueryApplyModel;
import com.yskc.ms.models.serviceApply.ServiceMemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ServiceMemberDao extends BaseMapper<ServiceMemberEntity> {
    /**
     * 查询服务申请人员列
     * @param serviceIds
     * @return
     */
    List<ServiceMemberModel> getMemberList(@Param("serviceIds")Set<Integer> serviceIds);

    /**
     * 添加服务申请人员
     * @param list
     * @return
     */
    Integer addMemberList(@Param("list")List<ServiceMemberEntity> list);

    /**
     * 编辑时删除
     * @param ids
     * @param serviceApplyId
     * @param mtypes
     * @return
     */
    Integer delByUserIds(@Param("ids")List<Integer> ids,@Param("serviceApplyId")Integer serviceApplyId,
                        @Param("mtypes")List<Integer> mtypes);

    /**
     * 根据ApplyId删除
     * @param ids
     * @return
     */
    Integer delByApplyIds(@Param("ids")List<Integer> ids);
}
