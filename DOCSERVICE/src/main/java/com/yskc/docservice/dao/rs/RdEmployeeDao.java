package com.yskc.docservice.dao.rs;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.RdEmployeeEntity;
import com.yskc.docservice.models.rs.EmployeeSelectModel;
import com.yskc.docservice.models.rs.RdEmployeeModel;
import com.yskc.docservice.models.rs.initmember.InitMemberModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-11-18 14:21:04
 */
@Repository
public interface RdEmployeeDao extends BaseMapper<RdEmployeeEntity> {

    List<RdEmployeeEntity> getListByEnumbers(@Param("companyId") Integer companyId, @Param("year") Integer year,
                                             @Param("numberList") List<String> numberList);

    /**
     * 添加导入研发花名册列表
     *
     * @param rdEmployeeEntities
     * @return
     */
    Integer saveRdEmployeeLists(@Param("RdEmployeeEntitys") List<RdEmployeeEntity> rdEmployeeEntities);

    Boolean updateList(@Param("entities") List<RdEmployeeEntity> entities);

    List<RdEmployeeModel> queryByCompanyIdAndYear(@Param("companyId") Integer companyId, @Param("year") Integer year);

    /**
     * 通过年份 + 工号查询
     *
     * @param companyId
     * @param year
     * @param numberList
     * @return
     */
    List<String> getByEnumbers(@Param("companyId") Integer companyId, @Param("year") Integer year,
                               @Param("numberList") List<String> numberList);

    /**
     * 获取研发人员列表
     *
     * @param companyId
     * @param projectIds
     * @return
     */
    List<InitMemberModel> getEmployeeList(@Param("companyId") Integer companyId,
                                          @Param("projectIds") List<Integer> projectIds);

    /**
     * 获取年份周期内所有的研发人员
     *
     * @param companyId
     * @param minYear
     * @param maxYear
     * @param enumbers
     * @return
     */
    List<InitMemberModel> getTermEmployee(@Param("companyId") Integer companyId, @Param("minYear") Integer minYear,
                                               @Param("maxYear") Integer maxYear, @Param("enumbers") Set<String> enumbers);

    /**
     * 根据工号获取人员列表
     *
     * @param companyId
     * @param enumbers
     * @return
     */
    List<EmployeeSelectModel> getEmployeeByEnumber(@Param("companyId") Integer companyId, @Param("enumbers") List<String> enumbers);
}
