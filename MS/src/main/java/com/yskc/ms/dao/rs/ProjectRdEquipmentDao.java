package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.ProjectRdEquipmentEntity;
import com.yskc.ms.models.projectAudit.QueryAuditDataModel;
import com.yskc.ms.models.projectAudit.QueryRdFeeModel;
import com.yskc.ms.models.projectAudit.RdEquipmentListModel;
import com.yskc.ms.models.rdfunds.EquipmentFeesModel;
import com.yskc.ms.models.rdfunds.EquipmentPowerFeesModel;
import com.yskc.ms.models.rs.RdEquipmentResultModel;
import com.yskc.ms.models.rs.summary.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hck
 * on 2020/9/2 16:36
 * description:
 */
@Repository
public interface ProjectRdEquipmentDao extends BaseMapper<ProjectRdEquipmentEntity> {

    /**
     * 项目设备使用情况(月)
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param year
     * @return
     */
    List<RdEquipmentResultModel> queryList(@Param("companyId") Integer companyId,
                                           @Param("projectId") Integer projectId,
                                           @Param("month") Date month,
                                           @Param("year") Integer year);

    /**
     * 项目设备使用情况(年)
     *
     * @param companyId
     * @param projectId
     * @return
     */
    List<RdEquipmentResultModel> queryYearList(@Param("companyId") Integer companyId,
                                               @Param("projectId") Integer projectId,
                                               @Param("year") Integer year);

    /**
     * 获取研发设备列表
     *
     * @param page
     * @param query
     * @return
     */
    List<RdEquipmentListModel> getList(@Param("page") Pagination page, @Param("query") QueryAuditDataModel query);

    /**
     * 统计
     *
     * @param query
     * @return
     */
    Map<String, Object> getTotal(@Param("query") QueryAuditDataModel query);

    /**
     * 删除项目相关费用
     *
     * @param projectId
     * @param yearKeys
     * @return
     */
    int deleteRdEquipment(@Param("projectId") Integer projectId, @Param("yearKeys") List<YearKeysModel> yearKeys);

    /**
     * 获取工时记录
     *
     * @param projectId
     * @param yearKeys
     * @return
     */
    List<ProjectEquipmentModel> getAttList(@Param("projectId") Integer projectId,
                                           @Param("yearKeys")  List<YearKeysModel> yearKeys);

    /**
     * 获取设备使用
     *
     * @param list
     * @param companyId
     * @return
     */
    List<ProjectEquipmentUsedModel> getAttUsedList(@Param("list") List<ProjectEquipmentModel> list, @Param("companyId") Integer companyId);

    /**
     * 删除设备工时记录
     *
     * @param ids
     * @return
     */
    int deleteEquData(@Param("ids") List<Integer> ids);

    /**
     * 删除设备使用记录
     *
     * @param ids
     * @return
     */
    int deleteEquDataUsed(@Param("ids") List<Integer> ids);

    /**
     * 更新设备使用
     *
     * @param list
     * @param now
     * @param msUserId
     * @return
     */
    int updateUsed(@Param("list") List<ProjectEquipmentUsedModel> list,
                   @Param("now") Date now, @Param("msUserId") Integer msUserId);

    /**
     * 获取归集月份
     *
     * @param yearKeys
     * @param projectId
     * @return
     */
    List<Date> getMonths(@Param("yearKeys") List<YearKeysModel> yearKeys, @Param("projectId") Integer projectId);

    /**
     * 获取归集数据
     *
     * @param projectId
     * @param months
     * @return
     */
    List<ProjectRdEquipmentTotalModel> getSummary(@Param("projectId") Integer projectId, @Param("months") List<Date> months);

    /**
     * 获取年份和ecode
     * @param ids
     * @return
     */
    List<InitEquipmentModel> getYearEcodes(@Param("ids") List<Integer> ids);

    /**
     * 获取项目设备折旧列表
     * @param page
     * @param model
     * @param year
     * @param monthLastDay
     * @return
     */
    List<EquipmentFeesModel> getCollectionList(@Param("page") Pagination page, @Param("model") QueryRdFeeModel model,
                                               @Param("year") int year, @Param("monthLastDay") Date monthLastDay);

    /**
     * 设备用电归集费用查询
     * @param page
     * @param model
     * @param year
     * @return
     */
    List<EquipmentPowerFeesModel> getEquipmentPowerFees(@Param("page") Pagination page, @Param("model") QueryRdFeeModel model,@Param("year") int year);

    /**
     * 获取区间月份
     * @param projectId
     * @param begin
     * @param end
     * @return
     */
    List<Date> getRangeMonths(@Param("projectId") Integer projectId,@Param("begin") Date begin,@Param("end") Date end);
}
