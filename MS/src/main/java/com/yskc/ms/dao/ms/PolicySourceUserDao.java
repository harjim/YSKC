package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.PolicySourceUser;
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
     * 删除订阅
     *
     * @param userId
     * @param sourceId
     * @return
     */
    int deleteSubscription(@Param("userId") Integer userId, @Param("sourceId") Integer sourceId);

    /**
     * 查询是否存在订阅
     *
     * @param userId
     * @param sourceId
     * @return
     */
    int checkSubscription(@Param("userId") Integer userId, @Param("sourceId") Integer sourceId);

    /**
     * 通过sourceId删除数据
     *
     * @param sourceIds
     * @return
     */
    int deleteBySourceIds(@Param("sourceIds") List<Integer> sourceIds);

    /**
     * 获取订阅人员id
     *
     * @param sourceId
     * @return
     */
    List<Integer> getSubscription(@Param("sourceId") Integer sourceId);

    /**
     * 批量插入人员
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<PolicySourceUser> list);
}
