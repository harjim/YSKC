package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.ProjectDeliver;
import com.xxl.job.executor.models.hightechprogress.ProjectDeliverModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @DateTime: 2022/5/23 8:35
 * @Description:
 * @author: hsx
 */
@Repository
public interface ProjectDeliverDao extends BaseMapper<ProjectDeliver> {

    /**
     * 批量新增或修改
     * @param countList
     * @return
     */
    int addOrUpdate(@Param("list") List<ProjectDeliverModel> countList, @Param("now") Date now);
}
