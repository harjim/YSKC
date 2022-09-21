package com.yskc.rs.dao.init;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.rs.entity.init.InitEquipmentEntity;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.equipment.ExportEquipmentModel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.equipment.InitEquipmentMiniModel;
import com.yskc.rs.models.init.equipment.InitEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author huronghua
 * @email 267267134@qq.com
 * @date 2019-07-23 16:40:58
 */
@Repository
public interface InitEquipmentDao extends BaseMapper<InitEquipmentEntity> {


    /**
     * 获取项目成员列表
     *
     * @param page
     * @param query
     * @param companyId
     * @return
     */
    List<InitEquipmentModel> getList(@Param("page") Pagination page, @Param("companyId") Integer companyId, @Param("query") QueryProjectEquipmentModel query);

    /**
     * 获取人员列表
     *
     * @param page
     * @param companyId
     * @param model
     * @return
     */
    List<InitEquipmentModel> getEquipmentList(Pagination page, @Param("companyId") Integer companyId, @Param("model") QueryProjectEquipmentModel model);

    /**
     * 通过enumber获取
     *
     * @param companyId
     * @param projectId
     * @param ecodes
     * @param year
     * @return
     */
    List<String> getByEcodes(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId, @Param("ecodes") Set<String> ecodes, @Param("year") Integer year);

    /**
     * 批量新增人员
     *
     * @param initEquipmentList
     * @return
     */
    int addBatch(@Param("initEquipmentList") List<InitEquipmentEntity> initEquipmentList);


    /**
     * 获取项目设备
     *
     * @param companyId
     * @param reportId
     * @return
     */
//    List<ProjectInitEquipment> getProjectInitEquipments(@Param("companyId") Integer companyId, @Param("reportId") Integer reportId);

    /**
     * 更新作用
     *
     * @param effect
     * @param ids
     * @return
     */
    Integer updateEffect(@Param("effect") String effect, @Param("ids") List<Integer> ids);

    List<InitEquipmentEntity> queryIEquipmentByProjectIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 更新项目设备加入日期
     *
     * @param now
     * @param userId
     * @param msUserId
     * @param ids
     * @param entryDate
     * @return
     */
    int updateEntryDate(@Param("now") Date now, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId,
                        @Param("ids") List<Integer> ids, @Param("entryDate") Date entryDate);


    /**
     * 获取设备列表数据
     *
     * @param year
     * @param companyId
     * @return
     */
    List<ExportEquipmentModel> getEquipmentsData(@Param("year") Integer year, @Param("companyId") Integer companyId);

    /**
     * 通过id 获取ecode
     *
     * @param ids
     * @return
     */
    List<InitEquipmentEntity> getEcodesByIds(@Param("ids") List<Integer> ids);

    /**
     * 获取存在数据
     *
     * @param companyId
     * @param keysAndIds
     * @param year
     * @return
     */
    List<InitEquipmentEntity> getExist(@Param("companyId") Integer companyId, @Param("keysAndIds") KeysAndIdsModel keysAndIds,
                                       @Param("year") Integer year);

    /**
     * 获取存在列表
     *
     * @param companyId
     * @param projectId
     * @param ecodes
     * @return
     */
    List<InitEquipmentEntity> getExistList(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                                           @Param("ecodes") Set<String> ecodes, Integer year);

    /**
     * 批量更新
     *
     * @param list
     * @return
     */
    int updateBatch(@Param("list") List<InitEquipmentEntity> list);

    /**
     * 获取可引入的资产清单
     *
     * @param model
     * @param companyId
     * @return
     */
    List<InitEquipmentEntity> getAddEquipments(@Param("model") ImportProjectInfoModel model, @Param("companyId") Integer companyId);

    /**
     * 根据年获取项目资产清单
     *
     * @param projectId
     * @param targetYear
     * @return
     */
    List<InitEquipmentEntity> getEuipmentByYear(@Param("projectId") Integer projectId, @Param("targetYear") Integer targetYear);

    /**
     * 获取存在项目设备的年份
     *
     * @param projectId
     * @param year
     * @return
     */
    List<Integer> getYears(@Param("projectId") Integer projectId, @Param("year") Integer year);

    /**
     * 获取项目资产清单
     *
     * @param companyId
     * @param projectIds
     * @param year
     * @return
     */
    List<InitEquipmentEntity> getInitList(@Param("companyId") Integer companyId, @Param("projectIds") List<Integer> projectIds, @Param("year") int year);

    /**
     * 根据年和项目获取设备
     *
     * @param companyId
     * @param year
     * @param projectId
     * @return
     */
    List<InitEquipmentModel> getEquList(@Param("companyId") Integer companyId, @Param("year") Integer year, @Param("projectId") Integer projectId);

    /**
     * 获取项目设备
     *
     * @param companyId
     * @param year
     * @param month
     * @return
     */
    List<InitEquipmentMiniModel> getInitEquipments(@Param("companyId") Integer companyId,@Param("year") Integer year, @Param("month") Date month);
}
