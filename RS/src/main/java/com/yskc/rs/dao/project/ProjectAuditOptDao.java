package com.yskc.rs.dao.project;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.project.ProjectAuditOpt;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.hightech.HighTechAllotModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @DateTime: 2022/5/19 10:03
 * @Description:
 * @author: hsx
 */
@Repository
public interface ProjectAuditOptDao extends BaseMapper<ProjectAuditOpt> {

    /**
     * 批量更新
     * @param model
     * @param info
     * @param now
     * @return
     */
    int updateBatch(@Param("model") HighTechAllotModel model, @Param("info") UserInfo info, @Param("now") Date now);
}
