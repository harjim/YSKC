package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.ManagerUserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.rs
 * @Author: wangxing
 * @CreateTime: 2019-09-12 10:24
 * @Description: 服务人员Dao
 */
@Repository
public interface ManagerUserDao extends BaseMapper<ManagerUserEntity> {
    /**
     * 批量新增
     *
     * @param managerUserEntities
     * @return
     */
    Integer addManagerUserBatch(@Param("managerUserEntities") List<ManagerUserEntity> managerUserEntities);

    /**
     * 获得已经添加人员
     *
     * @param companyId
     * @return
     */
    List<Integer> getManagerUserId(@Param("companyId") Integer companyId);

    /**
     * 根据公司删除
     *
     * @param companyId
     * @return
     */
    Integer delByCompanyId(@Param("companyId") Integer companyId);


    void deleteByUserId(@Param("userId") Integer userId);

    /**
     * 获取存在用户角色的companyId
     *
     * @param companyIds
     * @param currentUserId
     * @param msUserIds
     * @return
     */
    List<Integer> getByCompanyIds(@Param("companyIds") List<Integer> companyIds,
                                  @Param("currentUserId") Integer currentUserId,
                                  @Param("msUserIds") List<Integer> msUserIds);

    /**
     * 通过公司id，ms用户id获取数据
     *
     * @param msUserId
     * @param companyId
     * @return
     */
    ManagerUserEntity getByCompanyIdAndUserId(@Param("msUserId") Integer msUserId, @Param("companyId") Integer companyId);

    /**
     * 获取存在的数据
     *
     * @param userIds
     * @param companyId
     * @return
     */
    List<ManagerUserEntity> getExistManagerUser(@Param("userIds") List<Integer> userIds, @Param("companyId") Integer companyId);

}

