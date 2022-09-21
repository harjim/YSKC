package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.common.model.ProductMtype;
import com.yskc.common.model.RsMenuPermModel;
import com.yskc.docservice.entity.rs.RsAppUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 角色用户关联DAO
 *
 * @author huronghua
 */
@Repository
public interface RsAppUserRoleDao extends BaseMapper<RsAppUserRole> {

    /**
     * 获取用户角色
     *
     * @param userId
     * @param companyId
     * @return
     */
    List<Integer> getUserRoleIds(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

    List<Integer> getUserRoleByMTypes(@Param("list") List<ProductMtype> list);

    /**
     * 获取角色权限
     * @param roleId
     * @return
     */
    List<RsMenuPermModel> getPermData(@Param("roleId") Integer roleId);

}
