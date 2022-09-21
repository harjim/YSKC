package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.DesignEntity;
import com.yskc.rs.models.aggregation.DesignExportModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.design.DesignModel;
import com.yskc.rs.models.design.DesignQuery;
import com.yskc.rs.models.excel.DesginExcel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-11 15:28:04
 */
@Repository
public interface DesignDao extends BaseMapper<DesignEntity> {
    /**
     * 获取研发设计信息
     *
     * @param page
     * @param companyId
     * @param designQuery
     * @return
     */
    List<DesignModel> queryDesign(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("designQuery") DesignQuery designQuery);


    /**
     * 获取研发设计信息(导出用)
     *
     * @param companyId
     * @return
     */
    List<DesginExcel> getDesignData(@Param("companyId") Integer companyId, @Param("designQuery") DesignQuery designQuery);

    /**
     * 根据条件查询研发费用(项目选择用)
     *
     * @param page
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    List<DesignModel> queryDesignByTerm(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                        @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                        @Param("query") QueryProjectDesignModel query);

    /**
     * 获取总费用
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal getFeeCount(@Param("companyId") Integer companyId,
                           @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                           @Param("query") QueryProjectDesignModel query);


    /**
     * 修改研发部门
     *
     * @param companyId
     * @param entity
     * @return
     */
    Integer updateDesignRdDept(int companyId, @Param("entity") DesignEntity entity);

    /**
     * @param companyId
     * @param dname
     * @param beginDate
     * @param endDate
     * @param deptName
     * @return
     */
    List<DesignEntity> getDesignByTerm(@Param("companyId") Integer companyId, @Param("dname") String dname,
                                       @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                       @Param("deptName") String deptName,@Param("projectId")Integer projectId);

    /**
     * 批量删除
     * @param designIds
     * @return
     */
    // Integer delDesignBatch(@Param("designIds") List<Integer> designIds);

    /**
     * 批量添加
     *
     * @param designEntityList
     * @return
     */
    Integer addDesignBatch(@Param("designEntityList") List<DesignEntity> designEntityList);

    /**
     * 获取设计是否存在项目使用
     *
     * @param ids
     * @return
     */
    List<Integer> getProjectUsed(@Param("ids") List<Integer> ids);

    Integer updateList(@Param("designEntities") List<DesignEntity> designEntities);

    /**
     * 数据归集后批量修稿剩余金额为0.00
     * @param now
     * @param userId
     * @param msUserId
     * @param ids
     * @return
     */
    Integer updateRemainFee(@Param("now") Date now, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("ids") List<Integer> ids);

}
