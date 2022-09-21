package com.xxl.job.executor.dao.rs;

import com.xxl.job.executor.models.hightechprogress.HighTechSubmitModel;
import com.xxl.job.executor.models.hightechprogress.ProjectDeliverModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2022/5/21 11:06
 * @Description:
 * @author: hsx
 */
@Repository
public interface ProjectAuditRdFeeDao {

    /**
     * 获取条件时间端里的数据
     * @param model
     * @return
     */
    List<ProjectDeliverModel> getList(@Param("model") HighTechSubmitModel model);
}
