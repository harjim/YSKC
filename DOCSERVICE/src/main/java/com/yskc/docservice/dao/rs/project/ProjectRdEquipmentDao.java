package com.yskc.docservice.dao.rs.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yskc.docservice.entity.rs.project.ProjectRdEquipmentEntity;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentModel;
import com.yskc.docservice.models.rs.projectrdequipment.ProjectRdEquipmentTotalModel;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentResultModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.dao
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-01 10:58
 * @Description: 项目设备研发折旧
 */
@Repository
public interface ProjectRdEquipmentDao extends BaseMapper<ProjectRdEquipmentEntity> {

    /**
     * 获取项目设备折旧费用月合计
     *
     * @param projectIds
     * @param months
     * @param year
     * @return
     */
    List<ProjectRdEquipmentTotalModel> getSummary(@Param("projectIds") List<Integer> projectIds,
                                                  @Param("months") List<Date> months, @Param("year") Integer year);

    /**
     * 插入或更新
     *
     * @param list
     * @return
     */
    int insertOrUpdate(@Param("list") List<ProjectRdEquipmentEntity> list);

    /**
     * 批量更新
     * @param list
     * @param now
     * @param userId
     * @param msUserId
     * @return
     */
    int updateBatch(@Param("list") List<ProjectRdEquipmentEntity> list,@Param("now") Date now,@Param("userId") Integer userId,
                    @Param("msUserId") Integer msUserId);

    /**
     * 获取使用过的sum
     *
     * @param companyId
     * @param projectId
     * @param months
     * @param ecodes
     * @return
     */
    List<ProjectRdEquipmentModel> getUsedSum(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId,
                                             @Param("months")Set<Date> months, @Param("ecodes") Set<String> ecodes);

    /**
     * 获取ecode 及 id
     *
     * @param projectId
     * @param months
     * @param ecodes
     * @return
     */
    List<ProjectRdEquipmentEntity> getEcodeId(@Param("projectId") Integer projectId,@Param("months")Set<Date> months,
                                              @Param("ecodes") Set<String> ecodes);

    /**
     * 项目设备使用情况(月)
     *
     * @param companyId
     * @param projectId
     * @param month
     * @param year
     * @return
     */
    List<RdEquipmentResultModel> queryEquipmentList(@Param("companyId") Integer companyId,
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
}
