package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.InspectionEntity;
import com.yskc.rs.models.excel.InspectionExcel;
import com.yskc.rs.models.inspection.InspectionModel;
import com.yskc.rs.models.inspection.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.OtherSumModel;
import com.yskc.rs.models.projectinspection.QueryProjectInspectionModel;
import com.yskc.rs.models.projectinspection.SumProjectAmortizationModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:04
 * @Description: 检测修理Dao层
 */
@Repository
public interface InspectionDao extends BaseMapper<InspectionEntity> {
    /**
     * 查询所有信息
     *
     * @param page
     * @param companyId
     * @param inspectionQuery
     * @return
     */
    List<InspectionModel> queryAll(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("inspectionQuery") QueryInspectionModel inspectionQuery);


    /**
     * 查询差旅费
     *
     * @param page
     * @param companyId
     * @param inspectionQuery
     * @return
     */
    List<InspectionModel> querytravelData(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("inspectionQuery") QueryInspectionModel inspectionQuery);


    /**
     * 导出查询
     *
     * @param compantyId
     * @param inspectionQuery
     * @return
     */
    List<InspectionExcel> queryForExport(@Param("companyId") Integer compantyId, @Param("inspectionQuery") QueryInspectionModel inspectionQuery);


    /**
     * 根据条件查询检测修理(项目用)
     *
     * @param page
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    List<InspectionModel> queryInspectionByTerm(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                                @Param("beginDate") Date beginDate,
                                                @Param("endDate") Date endDate,
                                                @Param("query") QueryProjectInspectionModel query
    );

    /**
     * 批量研发部门
     *
     * @param companyId
     * @param entity
     * @return
     */
    Integer updateInspetionRdDept(@Param("companyId") Integer companyId, @Param("entity") InspectionEntity entity);


    /**
     * 批量类型
     *
     * @param companyId
     * @param entity
     * @return
     */
    Integer updateInspetioneType(@Param("companyId") Integer companyId, @Param("entity") InspectionEntity entity);

    /**
     * 查询
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    List<InspectionEntity> queryInspectionDataByTerm(@Param("companyId") Integer companyId,
                                                     @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                     @Param("query") QueryProjectInspectionModel query);

    /**
     * 根据条件查询检测修理(项目用)
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal getInspectionSum(@Param("companyId") Integer companyId, @Param("beginDate") Date beginDate,
                                @Param("endDate") Date endDate,
                                @Param("query") QueryProjectInspectionModel query);


    /**
     * 批量插入
     *
     * @param addList
     * @return
     */
    int addBatch(@Param("addList") List<InspectionEntity> addList);

    /**
     * 批量删除
     *
     * @param inspectionIds
     * @return
     */
    // boolean delInspectionBatch(@Param("inspectionIds") List<Integer> inspectionIds);


    /**
     * 查询费用
     *
     * @param page
     * @param companyId
     * @param query
     * @param type
     * @param beginDate
     * @param endDate
     * @return
     */
    List<InspectionModel> queryTravelFee(@Param("page") Pagination page,
                                         @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                         @Param("companyId") Integer companyId,
                                         @Param("query") QueryProjectInspectionModel query, @Param("type") String type);

    /**
     * 费用总和
     *
     * @param companyId
     * @param type
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    BigDecimal queryTravelFeeSum(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                 @Param("companyId") Integer companyId,
                                 @Param("query") QueryProjectInspectionModel query,
                                 @Param("type") String type);

    /**
     * 获取是否存在项目费用
     *
     * @param ids
     * @return
     */
    List<Integer> getProjectUsed(@Param("ids") List<Integer> ids);


    /**
     * 获取摊销费用
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    SumProjectAmortizationModel getAmortizationSum(@Param("companyId") Integer companyId,
                                                   @Param("beginDate") Date beginDate, @Param("endDate") Date endDate,
                                                   @Param("query") QueryProjectInspectionModel query);

    /**
     * 获取其他费用sum
     *
     * @param companyId
     * @param beginDate
     * @param endDate
     * @param query
     * @return
     */
    OtherSumModel getOtherSum(@Param("companyId") Integer companyId, @Param("beginDate") Date beginDate,
                              @Param("endDate") Date endDate, @Param("query") QueryProjectInspectionModel query);

    /**
     * 数据归集后剩余金额修改为0
     * @param ids
     * @param date
     * @param msUserId
     * @param userId
     * @return
     */
    Integer updateAmount(@Param("ids") List<Integer> ids, @Param("date") Date date, @Param("msUserId") Integer msUserId, @Param("userId") Integer userId);

   Integer updateList(@Param("inspectionEntities") List<InspectionEntity> inspectionEntities);
}