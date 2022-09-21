package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.DingUserEntity;
import com.yskc.ms.models.dingding.DingUser;
import org.apache.ibatis.annotations.Options;
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
    List<DingUserEntity> queryDUserAll();

    void deleteByUserId(@Param("userId") Integer userId);

    @Options(useGeneratedKeys = true)
    int insertBatch(List<DingUserEntity> dingUserEntities);

    DingUser queryDUserByUserId(@Param("userId") Integer userId);

    /**
     * 获取ms用户的钉钉id
     *
     * @param userIds
     * @return
     */
    List<String> getDingUserIds(@Param("userIds") List<Integer> userIds);

    /**
     * 同步钉钉用户
     * @return
     */
    Integer synDDUser();
}
