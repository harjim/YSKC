package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.project.ProjectEquipmentEntity;
import com.yskc.rs.models.projectequipment.ExportProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.ProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.projectequipment.WorkSheetEquipmentModel;
import com.yskc.rs.models.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.rs.models.workSheet.WorkSheetQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao.project
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-17 14:34
 * @Description: 项目设备研发记录Dao层
 */
@Repository
public interface ProjectEquipmentDao extends BaseMapper<ProjectEquipmentEntity> {
    /**
     * // // TODO: 2020-04-14  工单问题
     * 获取设备工单数据
     *
     * @param companyId
     * @param query
     * @param year
     * @return
     */
    List<WorkSheetEquipmentModel> getEquipmentWorkSheetList(@Param("companyId") Integer companyId,
                                                            @Param("query") WorkSheetQuery query, @Param("year") Integer year);

    /**
     * 获取设备研发使用记录 (分页)
     *
     * @param page
     * @param query
     * @return
     */
    List<ProjectEquipmentModel> getList(@Param("page") Pagination page, @Param("query") QueryProjectEquipmentModel query);


    /**
     * 获取研发设备
     *
     * @param projectId
     * @param month
     * @param ids
     * @return
     */
    List<ProjectEquipmentModel> getRdEquipment(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("ids") List<Integer> ids);

    /**
     * 通过ecode获取设备研发使用记录
     *
     * @param projectId
     * @param month
     * @param ecodes
     * @return
     */
    List<ProjectEquipmentModel> getByEcodes(@Param("projectId") Integer projectId, @Param("month") Date month, @Param("ecodes") List<String> ecodes);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertBatch(@Param("list") List<ProjectEquipmentEntity> list);

    /**
     * 通过id获取（只获取部分必须字段）
     *
     * @param ids
     * @return
     */
    List<ProjectEquipmentEntity> getByIds(@Param("ids") List<Integer> ids);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<ProjectEquipmentEntity> list);

    /**
     * 获取导出的数据
     *
     * @param query
     * @param companyId
     * @return
     */
    List<ExportProjectEquipmentModel> getExportData(@Param("query") QueryProjectEquipmentModel query,
                                                    @Param("companyId") Integer companyId);

    /**
     * 获取设备运行工时记录
     *
     * @param list
     * @param projectId
     * @return
     */
    List<ProjectEquipmentEntity> getProjectEquipments(@Param("list") List<ProjectRdEquipmentModel> list,
                                                      @Param("projectId") Integer projectId);

    /**
     * 删除数据（打卡记录及使用记录）
     *
     * @param companyId
     * @param month
     * @return
     */
    int deleteData(@Param("companyId") Integer companyId, @Param("month") Date month);
}
