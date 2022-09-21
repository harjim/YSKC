package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectFinanceInfoEntity;
import com.yskc.ms.models.project.ProjectFinanceInfoModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/10/31 10:31
 * description:
 */
@Repository
public interface ProjectFinanceInfoDao extends BaseMapper<ProjectFinanceInfoEntity> {

    ProjectFinanceInfoModel getInfo(@Param("projectId") Integer projectId, @Param("type") int type);

    /**
     * 根据项目获取信息
     * @param projectId
     * @return
     */
    ProjectFinanceInfoEntity getByProject(@Param("projectId") Integer projectId);
}
