package com.xxl.job.executor.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.rs.CWorker;
import com.xxl.job.executor.entity.rs.RsProjectEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @DateTime: 2021/10/9 17:19
 * @Description:
 * @author: hsx
 */
@Repository
public interface RsProjectDao extends BaseMapper<RsProjectEntity> {

    /**
     * 获取科研项目总数
     * @return
     */
    Integer getProjectStatNumber();

}
