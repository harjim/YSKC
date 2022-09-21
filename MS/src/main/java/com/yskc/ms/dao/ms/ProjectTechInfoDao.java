package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.ProjectTechInfoEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by hck
 * on 2020/10/31 10:23
 * description:
 */
@Repository
public interface ProjectTechInfoDao extends BaseMapper<ProjectTechInfoEntity> {
    /**
     * 根据项目id获取技术信息
     * @param projectId
     * @return
     */
    ProjectTechInfoEntity getInfo(@Param("projectId") Integer projectId);


}
