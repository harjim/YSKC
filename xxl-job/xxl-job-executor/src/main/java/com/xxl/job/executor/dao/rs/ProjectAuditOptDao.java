package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.CWorker;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.rs
 * @Author: wenjy
 * @CreateTime: 2022-05-18 13:51
 * @Description: ProjectAuditOpt dao层
 */
@Repository
public interface ProjectAuditOptDao extends BaseMapper<CWorker> {

}
