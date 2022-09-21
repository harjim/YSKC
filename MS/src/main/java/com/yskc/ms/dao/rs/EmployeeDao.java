package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.EmployeeEntity;
import com.yskc.ms.entity.rs.RdDeptEntity;
import com.yskc.ms.models.employee.EmployeeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by hck
 * on 2020/9/2 18:23
 * description:
 */
@Repository
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {
    /**
     * 根据员工编码获取员工信息
     *
     * @param companyId
     * @param enumber
     * @return
     */
    EmployeeEntity getByNumber(@Param("companyId") int companyId, @Param("enumber") String enumber);

    /**
     * 获取研发架构跟节点
     *
     * @param year
     * @param companyId
     * @return
     */
    RdDeptEntity getParentNode(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 查询工号的唯一性
     *
     * @param companyId
     * @param enumber
     * @return
     */
    EmployeeModel getEmployeeByenumber(@Param("companyId") int companyId, @Param("enumber") String enumber);

    /**
     * 获取研发部门名称
     * @param id
     * @return
     */
    String getDeptName(@Param("id") Integer id);

    List<HashMap<String,String>> getEmployee(@Param("list") Set<String> list, @Param("companyId")Integer companyId);
}
