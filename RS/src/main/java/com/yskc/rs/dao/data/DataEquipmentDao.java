package com.yskc.rs.dao.data;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.data.DataEquipmentEntity;
import com.yskc.rs.models.data.DataEquipmentInfoModel;
import com.yskc.rs.models.data.DataEquipmentModel;
import com.yskc.rs.models.data.DataEquipmentModifyModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.excel.DataEquipmentExcel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * 设备使用
 *
 * @author huronghua
 */
@Repository
public interface DataEquipmentDao extends BaseMapper<DataEquipmentEntity> {
    /**
     * 获取设备使用记录
     *
     * @param page
     * @param companyId
     * @param queryEquipmentModel
     * @return
     */
    List<DataEquipmentModel> getDataEquipmentList(Pagination page, @Param("companyId") Integer companyId, @Param("queryModel") QueryProjectEquipmentModel queryEquipmentModel);

    /**
     * 检查指定月份的设备使用记录
     *
     * @param companyId
     * @param month
     * @param ecode
     * @return
     */
    Integer checkEcodeByMonth(@Param("companyId") Integer companyId, @Param("month") Date month, @Param("ecode") String ecode);

    /**
     * 批量更新（置零）设备使用记录
     *
     * @param ids
     * @return
     */
    int updateFinishByIds(@Param("ids") List<Integer> ids);

    /**
     * 批量更新插入
     *
     * @param dataEquipmentList
     * @return
     */
    int insertOrUpdate(@Param("dataEquipmentList") List<DataEquipmentEntity> dataEquipmentList);

    /**
     * 批量更新
     *
     * @param dataEquipmentList
     * @return
     */
    int updateBatch(@Param("dataEquipmentList") List<DataEquipmentEntity> dataEquipmentList);

    /**
     * 更新运行工时
     *
     * @param de
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    int updateWorkHours(@Param("de") DataEquipmentModel de, @Param("userId") Integer userId,
                        @Param("msUserId") Integer msUserId, @Param("now") Date now);

    /**
     * 通过研发部门更新运行工时
     *
     * @param modifyModels
     * @return
     */
    int updateWorkHoursBatch(@Param("modifyModels") List<DataEquipmentModifyModel> modifyModels);

    /**
     * 是否当前数据是否绑定项目
     *
     * @param month
     * @param ecode
     * @param companyId
     * @return
     */
    List<Integer> checkExistProject(@Param("month") Date month, @Param("ecode") String ecode, @Param("companyId") Integer companyId);

    /**
     * 获取项目使用设备
     *
     * @param companyId
     * @param ids
     * @return
     */
    List<Integer> getProjectUsed(@Param("companyId") Integer companyId, @Param("ids") List<Integer> ids);

    /**
     * 更新设备类型
     *
     * @param companyId
     * @param modify
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    Integer updateEtype(@Param("companyId") Integer companyId,
                        @Param("userId") Integer userId,
                        @Param("msUserId") Integer msUserId, @Param("now") Date now,
                        @Param("modify") EquipmentModifyModel modify);

    /**
     * 导出数据库
     *
     * @param queryEquipmentModel
     * @return
     */
    List<DataEquipmentExcel> exportDataEquipment(@Param("companyId") Integer companyId, @Param("queryModel") QueryProjectEquipmentModel queryEquipmentModel);

    /**
     * 通过ecode获取数据
     * @param companyId
     * @param month
     * @param ecodes
     * @param hasDepreciation
     * @return
     */
    List<DataEquipmentInfoModel> getByEcodes(@Param("companyId") Integer companyId, @Param("month") Date month,
                                             @Param("ecodes") List<String> ecodes, @Param("hasDepreciation") Boolean hasDepreciation);
}
