package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PolicySourceUser;
import com.xxl.job.executor.models.policy.SourceUserModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:22
 * @Description: 政策来源用户dao层
 */
@Repository
public interface PolicySourceUserDao extends BaseMapper<PolicySourceUser> {


    /**
     * 获取政策来源订阅者
     *
     * @param sourceIds
     * @param userId
     * @return
     */
    List<SourceUserModel> getSubscription(@Param("sourceIds") List<Integer> sourceIds,@Param("userId") Integer userId);
}
