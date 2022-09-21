package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.Dept;
import com.yskc.docservice.models.rs.CommonOrgModel;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author Administrator
 */
@Repository
public interface DeptDao extends BaseMapper<Dept> {

    /**
     * 获取公司的顶级部门
     *
     * @param companyId
     * @return
     */
    Dept selectParentDept(@Param("companyId") int companyId);

    /**
     * 批量插入
     *
     * @param deptList
     * @return
     */
    @Options(useGeneratedKeys = true)
    Integer addBatch(List<Dept> deptList);


    /**
     * 批量更新
     *
     * @param updateList
     * @return
     */
    Integer updateList(@Param("updateList") List<Dept> updateList);


    List<Dept> queryByCompanyId(@Param("companyId")Integer companyId);

    List<Dept> getChildByDeptList(@Param("deptList")List<Dept> deptList,@Param("companyId") Integer companyId);

    /**
     * 获取公司部门组织
     * @param companyId
     * @return
     */
    List<CommonOrgModel> getCompanyOrg(Integer companyId);
}
