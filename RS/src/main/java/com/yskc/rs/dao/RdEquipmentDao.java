package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.RdEquipmentEntity;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.models.projectequipment.DocEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.rdequipment.RdEquipmentInfoModel;
import com.yskc.rs.models.rdequipment.RdEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:15
 * @Description: 研发设备dao层
 */
@Repository
public interface RdEquipmentDao extends BaseMapper<RdEquipmentEntity> {

    /**
     * 获取研发设备
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<RdEquipmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryEquipmentModel query);


    /**
     * 获取设备列表
     *
     * @param page
     * @param companyId
     * @param query
     * @param firstDay
     * @return
     */
    List<EquipmentModel> getEquipmentList(@Param("page") Pagination page,
                                          @Param("companyId") Integer companyId,
                                          @Param("query") QueryEquipmentModel query,
                                          @Param("firstDay")Date firstDay);


    /**
     * 批量添加
     *
     * @param rdEquipments
     * @return
     */
    Integer addBatch(@Param("rdEquipments") List<RdEquipmentEntity> rdEquipments);

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
    Integer updateEtype(@Param("companyId") Integer companyId, @Param("userId") Integer userId,
                        @Param("msUserId") Integer msUserId, @Param("now") Date now, @Param("modify") EquipmentModifyModel modify);

    /**
     * 设置研发部门
     *
     * @param modify
     * @param userId
     * @param msUserId
     * @param now
     * @return
     */
    Integer updateRdDept(@Param("modify") EquipmentModifyModel modify,
                         @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("now") Date now);

    /**
     * 通过ecode获取研发设备
     *
     * @param companyId
     * @param ecodes
     * @return
     */
    List<RdEquipmentModel> getByEcodes(@Param("companyId") Integer companyId, @Param("ecodes") Set<String> ecodes, @Param("year") Integer year);

    /**
     * 导出研发设备
     *
     * @param companyId
     * @param query
     * @return
     */
    List<EquipmentExcel> deriveRdEquipment(@Param("companyId") Integer companyId, @Param("query") QueryEquipmentModel query);

    /**
     * 获取项目设备列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<DocEquipmentModel> getDocEquipmentList(@Param("page") Pagination page, @Param("query") QueryProjectEquipmentModel query, @Param("companyId") Integer companyId);


    /**
     * 添加导入研发设备列表
     *
     * @param rdEquipmentEntities
     * @return
     */
    Integer saveRdEquipmentLists(@Param("RdEquipmentEntitys") List<RdEquipmentEntity> rdEquipmentEntities);


    Integer updateList(@Param("updateModels") List<RdEquipmentModel> updateModels, @Param("date") Date date, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId);

    /**
     * 获取使用的id
     *
     * @param companyId
     * @param ids
     * @param year
     * @return
     */
    List<Integer> getUsedIds(@Param("companyId") Integer companyId, @Param("ids") List<Integer> ids
            , @Param("year") Integer year);

    /**
     * 获取项目rdTitles
     *
     * @param ecodes
     * @param companyId
     * @param year
     * @param needTerm
     * @return
     */
    List<FullYearProjectModel> getRdTitles(@Param("ecodes") List<String> ecodes, @Param("companyId") Integer companyId,
                                           @Param("year") Integer year,@Param("needTerm")Boolean needTerm);

    /**
     * 获取研发设备信息
     *
     * @param page
     * @param companyId
     * @param query
     * @return
     */
    List<RdEquipmentInfoModel> getInfoList(@Param("page") Pagination page, @Param("companyId") Integer companyId,
                                           @Param("query") QueryEquipmentModel query);

    /**
     * 获取研发设备数
     * @param companyId
     * @param year
     * @return
     */
    Integer countNum(@Param("companyId") Integer companyId, @Param("year") Integer year);
}
