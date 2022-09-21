package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.RdDeptEntity;
import com.yskc.docservice.models.rs.CommonOrgModel;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-08-08 15:23:42
 */
@Repository
public interface RdDeptDao extends BaseMapper<RdDeptEntity> {

    /**
     * 批量添加
     *
     * @param list
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(@Param("list") List<RdDeptEntity> list);

    /**
     * 获取公司研发组织
     *
     * @param companyId
     * @param year
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取部门名称
     *
     * @param id
     * @return
     */
    String getDeptName(@Param("id") Integer id);

    /**
     * 获取研发架构根节点
     *
     * @param year
     * @param companyId
     * @return
     */
    RdDeptEntity getParentNode(@Param("year") Integer year, @Param("companyId") Integer companyId);
}
