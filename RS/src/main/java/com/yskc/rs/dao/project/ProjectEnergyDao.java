package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectEnergyEntity;
import com.yskc.rs.models.aggregation.EnergyExportModel;
import com.yskc.rs.models.aggregation.ExportTermModel;
import com.yskc.rs.models.projectenergy.*;
import com.yskc.rs.models.workSheet.WorkSheetMiniModel;
import com.yskc.rs.models.workSheet.WorkSheetModel;
import com.yskc.rs.models.workSheet.WorkSheetQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-17 15:32:14
 */
@Repository
public interface ProjectEnergyDao extends BaseMapper<ProjectEnergyEntity> {

    /**
     * 获取项目能源列表 分页
     *
     * @param pagination
     * @param companyId
     * @param queryEnergy
     * @param projectIds
     * @return
     */
    List<ProjectEnergyModel> getList(@Param("pagination") Pagination pagination,
                                     @Param("companyId") Integer companyId,
                                     @Param("queryEnergy") QueryProjectEnergy queryEnergy,
                                     @Param("projectIds") List<Integer> projectIds);

    /**
     * 获取项目能源列表 不分页
     *
     * @param companyId
     * @param queryEnergy
     * @param projectIds
     * @return
     */
    List<ProjectEnergyModel> getList(@Param("companyId") Integer companyId,
                                     @Param("queryEnergy") QueryProjectEnergy queryEnergy,
                                     @Param("projectIds") List<Integer> projectIds);

    /**
     * 获取能源损耗列表
     *
     * @param pagination  分页对象
     * @param companyId
     * @param queryEnergy
     * @return
     */
    List<EnergyListModel> getEnergyList(Pagination pagination,
                                        @Param("companyId") Integer companyId,
                                        @Param("queryEnergy") QueryProjectEnergy queryEnergy);

    /**
     * 获取能源损耗列表 不分页
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    List<EnergyListModel> getEnergyList(@Param("companyId") Integer companyId,
                                        @Param("queryEnergy") QueryProjectEnergy queryEnergy);

    /**
     * 添加能源损耗
     *
     * @param projectEnergies
     * @return
     */
    int addList(@Param("energys") List<ProjectEnergyEntity> projectEnergies);

    /**
     * 获取能源费用汇总
     *
     * @param projectId
     * @param begin
     * @param end
     * @param etype
     * @param type
     * @return
     */
    List<EnergySumModel> getEnergySum(@Param("projectId") Integer projectId,
                                      @Param("begin") Date begin,
                                      @Param("end") Date end,
                                      @Param("etype") int etype,
                                      @Param("type") int type);

    /**
     * 获取条件总费用
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    BigDecimal getEnergyTotal(@Param("companyId") Integer companyId,
                              @Param("queryEnergy") QueryProjectEnergy queryEnergy);

    /**
     * 获取项目能源费用
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    BigDecimal getProjectEnergyTotal(@Param("companyId") Integer companyId,
                                     @Param("queryEnergy") QueryProjectEnergy queryEnergy);

    /**
     * 批量更新费用数据
     *
     * @param energys
     * @return
     */
    int updateList(@Param("energys") List<ProjectEnergyEntity> energys);

    /**
     * 获取能源工单
     *
     * @param companyId
     * @param query
     * @param type
     * @return
     */
    List<WorkSheetModel> getEnergyWorkSheetList(@Param("companyId") Integer companyId, @Param("query") WorkSheetQuery query, @Param("type") Integer type);

    /**
     * 获取月能源工单
     *
     * @param companyId
     * @param query
     * @param type
     * @param begin
     * @param end
     * @return
     */
    List<WorkSheetMiniModel> getEnergyWorkSheetMonthList(@Param("companyId") Integer companyId, @Param("query") WorkSheetQuery
            query, @Param("type") Integer type, @Param("begin") Date begin, @Param("end") Date end);

    /**
     * 导出归集动力
     *
     * @param exportTerm
     * @param etype
     * @return
     */
    List<EnergyExportModel> exportFromStimulus(@Param("term") ExportTermModel exportTerm, @Param("etype") Integer etype);

    /**
     * 通过id获取费用
     *
     * @param ids
     * @return
     */
    List<ProjectEnergyModel> getListByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取其他项目使用的费用
     *
     * @param dataIds
     * @param projectId
     * @return
     */
    List<EnergyUsedModel> getUsedList(@Param("dataIds") List<Integer> dataIds, @Param("projectId") Integer projectId);

    /**
     * 获取归集数据
     *
     * @param begin
     * @param end
     * @param projectId
     * @param rdType
     * @return
     */
    List<ProjectEnergyModel> getProjectEnergyList(@Param("begin") Date begin, @Param("end") Date end,
                                                  @Param("projectId") Integer projectId, @Param("rdType") Integer rdType);

    /**
     * 获取区间数据
     *
     * @param companyId
     * @param begin
     * @param end
     * @param rdType
     * @return
     */
    List<ProjectEnergyModel> getTermData(@Param("companyId") Integer companyId, @Param("begin") Date begin,
                                         @Param("end") Date end, @Param("rdType") Integer rdType);

    /**
     * 批量更新
     *
     * @param list
     * @param now
     * @param userId
     * @param msUserId
     * @return
     */
    int updateBatch(@Param("list") List<ProjectEnergyEntity> list, @Param("now") Date now,
                    @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 获取多项目能源汇总
     *
     * @param projectIds
     * @param month
     * @param monthEnd
     * @param rdType
     * @return
     */
    List<EnergySumModel> getEnergySums(@Param("projectIds") Set<Integer> projectIds, @Param("begin") Date month,
                                       @Param("end") Date monthEnd, @Param("rdType") Integer rdType);

    /**
     * 查询研发试制，动力明细表
     *
     * @param companyId
     * @param query
     * @param page
     * @return
     */
    List<HighTechProjectEnergyModel> getEnergies(@Param("companyId") Integer companyId, @Param("query") QueryProjectEnergy query, @Param("page") Pagination page);
}
