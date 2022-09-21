package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-09 10:13:52
 */
@Repository
public interface EmployeeDao extends BaseMapper<EmployeeEntity> {

    /**
     * 根据员工编号列表获取员工信息
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeEntity> getByNumbers(@Param("companyId") int companyId, @Param("enumbers") List<String> enumbers);


    /**
     * 批量插入
     *
     * @param employeeEntitys
     * @return
     */
    Integer addBatch(@Param("employeeEntitys") List<EmployeeEntity> employeeEntitys);

    /**
     * 批量修改
     *
     * @param employeeEntitys
     * @return
     */
    Integer updateBatch(@Param("employeeEntitys") List<EmployeeEntity> employeeEntitys);

    /**
     * 根据条件获得人员信息
     *
     * @param companyId
     * @param ename
     * @return
     */
    List<EmployeeEntity> getEmployeeByTerm(@Param("companyId") Integer companyId, @Param("enames") List<String> ename);

    List<EmployeeEntity> getEmployeeByEnames(@Param("companyId") Integer companyId, @Param("enames") List<String> enames,
                                            @Param("firstDay")Date firstDay);

    /**
     * 根据员工编码获取员工信息
     *
     * @param companyId
     * @param enumber
     * @return
     */
    EmployeeEntity getByNumber(@Param("companyId") int companyId, @Param("enumber") String enumber);
}
