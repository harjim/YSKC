package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.common.model.RsMenuPermModel;
import com.yskc.ms.entity.rs.RsRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-30 16:29:05
 */
@Repository
public interface RsRoleMenuDao extends BaseMapper<RsRoleMenu> {

    /**
     * 通过角色删除角色菜单
     * @param roleId
     * @return
     */
    int delByRoleId(@Param("roleId") Integer roleId);

    /**
     * 批量添加
     * @param rsRoleMenus
     * @return
     */
    int addBatch(@Param("rsRoleMenus") List<RsRoleMenu> rsRoleMenus);

    /**
     * 获取功能id
     * @param roleId
     * @return
     */
    List<Integer> getRoleMenuIds(@Param("roleId")Integer roleId);

    /**
     * 获取数据
     * @param roleId
     * @return
     */
    List<RsMenuPermModel> getPermData(@Param("roleId") Integer roleId);
}
