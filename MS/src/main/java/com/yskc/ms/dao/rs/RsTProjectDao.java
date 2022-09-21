package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.RsProjectEntity;
import com.yskc.ms.models.rs.RelatedProjectModel;
import com.yskc.ms.models.rs.TechProjectResultModel;
import com.yskc.ms.models.techsummary.TechStageFilesModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/10/15 11:33
 * description:
 */
@Repository
public interface RsTProjectDao extends BaseMapper<RsProjectEntity> {
    /**
     * 根据项目id获取技改项目
     *
     * @param projectIds
     * @return
     */
    List<TechProjectResultModel> getBySourceIds(@Param("projectIds") List<Integer> projectIds);

    /**
     * 根据源项目获取技改项目
     *
     * @param souceProjecId
     * @return
     */
    RsProjectEntity getBySourceProject(@Param("souceProjecId") Integer souceProjecId);

    /**
     * 根据申报项目id获取
     * @param ids     * @return
     */
    RelatedProjectModel getByProjectId(@Param("projectId") Integer projectId);

    /**
     * 根据方向获取技改项目
     *
     * @param directionId
     * @return
     */
    RsProjectEntity getByDirectionId(@Param("directionId") Integer directionId);


    /**
     * 获取阶段文件清单
     *
     * @param projectId
     * @return
     */
    List<TechStageFilesModel> getStageFiles(@Param("projectId") Integer projectId);
}
