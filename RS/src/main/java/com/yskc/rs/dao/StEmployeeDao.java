package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.StEmployeeEntity;
import com.yskc.rs.models.stEmployee.QueryStEmployeeModel;
import com.yskc.rs.models.stEmployee.StEmployeeExportModel;
import com.yskc.rs.models.stEmployee.StEmployeeModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StEmployeeDao extends BaseMapper<StEmployeeEntity> {
    /**
     * 查询科技管理人员
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<StEmployeeModel> getList(@Param("page")Pagination page, @Param("query")QueryStEmployeeModel query,
                                  @Param("companyId")Integer companyId);

    /**
     * 根据工号查询
     * @param companyId
     * @param year
     * @param enumbers
     * @return
     */
    List<String> getByenumbers(@Param("companyId")Integer companyId,@Param("year")Integer year,
                               @Param("enumbers") List<String> enumbers);

    /**
     * 添加人员
     * @param list
     * @return
     */
    Integer addStEmployees(@Param("list")List<StEmployeeEntity> list);

    /**
     * 导出查询
     * @param companyId
     * @param query
     * @return
     */
    List<StEmployeeExportModel> getExport(@Param("companyId")Integer companyId,@Param("query")QueryStEmployeeModel query);

}
