package com.yskc.rs.dao.highTech;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.hightech.HighTechEntity;
import com.yskc.rs.entity.hightech.HighTechProjectEntity;
import com.yskc.rs.models.project.ProjectInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/5/27 16:58
 * @Description:
 */
@Repository
public interface HighTechProjectDao extends BaseMapper<HighTechProjectEntity> {
    /**
     * 获取高品绑定的项目列表
     * @param highTechId
     * @return
     */
    List<HighTechProjectEntity> getBindProjects(@Param("highTechId") Integer highTechId, @Param("companyId") Integer companyId);

    /**
     * 批量绑定项目
     * @param insertList
     */
    Integer inertList(@Param("insertList") List<HighTechProjectEntity> insertList);

    /**
     * 删除绑定项目
     * @param highTechIds
     * @return
     */
    Integer deleteByTech(@Param("highTechIds") List<Integer> highTechIds, @Param("companyId") Integer companyId);

    /**
     * 获取所有关联项目
     * @param highTechId
     * @param companyId
     * @return
     */
    List<ProjectInfoModel> getByTech(@Param("highTechId") Integer highTechId, @Param("companyId") Integer companyId);
}
