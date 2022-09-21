package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.DingUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 钉钉用户表
 *
 * @author huronghua
 */
@Repository
public interface DingUserDao extends BaseMapper<DingUserEntity> {

    /**
     * 获取ms用户的钉钉id
     *
     * @param userIds
     * @return
     */
    List<String> getDingUserIds(@Param("userIds") List<Integer> userIds);
}
