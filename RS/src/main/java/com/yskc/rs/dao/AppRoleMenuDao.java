package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.common.model.RsMenuPermModel;
import com.yskc.rs.entity.AppRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2020-01-17 16:57
 * @Description: 角色权限dao层
 */
@Repository
public interface AppRoleMenuDao extends BaseMapper<AppRoleMenu> {

    /**
     * 获取数据
     * @param roleId
     * @return
     */
    List<RsMenuPermModel> getPermData(@Param("roleId") Integer roleId);
}
