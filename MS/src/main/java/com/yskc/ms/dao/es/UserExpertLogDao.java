package com.yskc.ms.dao.es;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.es.UserExpertLogEntity;
import com.yskc.ms.models.newexpert.expert.UserExpertLogModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/10/19 15:14
 * @Description:
 * @author: hsx
 */
@Repository
public interface UserExpertLogDao extends BaseMapper<UserExpertLogEntity> {

    /**
     * 批量新增用户入驻审批记录
     * @param model
     * @return
     */
    Integer addAuditLog(@Param("model") UserExpertLogModel model);

    /**
     * 获取用户审批记录日志
     * @param id
     * @return
     */
    List<UserExpertLogEntity> getUserAuditLog(Integer id);
}
