package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.AppRoleData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-17 10:53
 * @Description: 数据权限Dao
 */
@Repository
public interface AppRoleDataDao extends BaseMapper<AppRoleData> {
    /**
     * 获取最高数据权限
     *
     * @param roleIds
     * @param functionId
     * @return
     */
    Integer getDataTop(@Param("roleIds") List<Integer> roleIds, @Param("functionId") Integer functionId);

    /**
     * 通过角色id删除
     *
     * @param roleIds
     * @return
     */
    int deleteByRoleIds(@Param("roleIds") List<Integer> roleIds);

    /**
     * 批量插入数据权限
     *
     * @param dataPerms
     * @return
     */
    int insertBatch(@Param("dataPerms") List<AppRoleData> dataPerms);
}
