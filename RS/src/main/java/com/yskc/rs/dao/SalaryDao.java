package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.SalaryEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.SalaryExcel;
import com.yskc.rs.models.salary.SalaryInfoModel;
import com.yskc.rs.models.salary.SalaryModel;
import com.yskc.rs.models.salary.SalaryQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-11 09:04:31
 */
@Repository
public interface SalaryDao extends BaseMapper<SalaryEntity> {
    /**
     * 获取薪资表
     *
     * @param page
     * @param salaryQuery
     * @return
     */
    List<SalaryModel> querySalary(@Param("page") Pagination page, @Param("salaryQuery") SalaryQuery salaryQuery);

    /**
     * 查询同一公司同一员工编号同月份是否存在
     *
     * @param companyId
     * @param enumber
     * @param month
     * @return
     */
    SalaryModel getSalaryByenumber(@Param("companyId") Integer companyId, @Param("enumber") String enumber, @Param("month") Date month);

    /**
     * 获取薪资数据(导出用)
     *
     * @param companyId
     * @param salaryQuery
     * @return
     */
    List<SalaryExcel> getSalaryData(@Param("companyId") Integer companyId, @Param("salaryQuery") SalaryQuery salaryQuery);

    /**
     * 批量修改日工时
     *
     * @param info
     * @param entity
     * @return
     */
    Integer updateSalarydayHours(UserInfo info, @Param("entity") SalaryEntity entity);


    /**
     * 查询同一公司同一员工编号同月份是否存在
     *
     * @param companyId
     * @param queryModels
     * @return
     */
    List<SalaryEntity> getDataByTerm(@Param("companyId") Integer companyId, @Param("queryModels") List<SalaryExcel> queryModels);

    /**
     * 批量插入
     *
     * @param salaryEntitielist
     * @return
     */
    Integer addBatch(@Param("salaryEntitielist") List<SalaryEntity> salaryEntitielist);

    /**
     * 批量修改
     *
     * @param salaryEntityList
     * @return
     */
    Integer updateBatch(@Param("salaryEntityList") List<SalaryEntity> salaryEntityList);

    /**
     * 获取薪资是否被使用
     * 返回薪资本身id
     *
     * @param companyId
     * @param ids
     * @return
     */
    List<Integer> getProjectUsed(@Param("companyId") int companyId, @Param("ids") List<Integer> ids);

    /**
     * 获取信息信息
     *
     * @param companyId
     * @param month
     * @param enumbers
     * @return
     */
    List<SalaryInfoModel> getSalaryInfos(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("enumbers") Set<String> enumbers);
}
