package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.Dept;
import com.yskc.ms.models.project.ProjectProgressModel;
import com.yskc.ms.models.rs.RdDeptTree;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RsDeptDao extends BaseMapper<Dept> {

    /**
     * 获取研发部门
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdDeptTree> getRdDept(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 获取研发部门levels
     * @param list
     * @return
     */
    List<ProjectProgressModel> getRdDeptLevels(@Param("list") List<ProjectProgressModel> list);

    Integer getDeptLevel(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 获取研发部门名称
     *
     * @param id
     * @return
     */
    String getDeptName(@Param("id") Integer id);
}
