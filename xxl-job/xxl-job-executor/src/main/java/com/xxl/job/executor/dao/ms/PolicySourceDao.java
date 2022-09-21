package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PolicySource;
import com.xxl.job.executor.models.policy.PolicySourceModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:22
 * @Description: 政策来源dao层
 */
@Repository
public interface PolicySourceDao extends BaseMapper<PolicySource> {
    /**
     * 获取爬取到最新数据的网站
     * @param dayBegin
     * @return
     */
    List<PolicySourceModel> getNewDataSource(@Param("dayBegin") Date dayBegin);
}
