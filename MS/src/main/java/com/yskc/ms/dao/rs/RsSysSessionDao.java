package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsSysSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * rs会话
 * @author huronghua
 */
@Repository
public interface RsSysSessionDao  extends BaseMapper<RsSysSession> {
    /**
     * 根据角色id获取用户token集合
     * @param roleId
     * @return
     */
    List<String> getTokenList(@Param("roleId") Integer roleId);

    /**
     * 根据角色ids获取用户token集合
     * @param roleIds
     * @return
     */
    List<String> getTokenListByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
