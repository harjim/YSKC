package com.xxl.job.executor.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xxl.job.executor.entity.ms.PatentDataEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: xxl-job
 * @BelongsPackage: com.xxl.job.executor.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-09-10 13:54
 * @Description: 专利数据dao层
 */
@Repository
public interface PatentDataDao extends BaseMapper<PatentDataEntity> {


    /**
     * 插入或更新
     *
     * @param list
     * @param updateAddress
     * @param updateCaseStatus
     * @return
     */
    int insertOrUpdate(@Param("list") List<PatentDataEntity> list,@Param("updateAddress") Boolean updateAddress,
                       @Param("updateCaseStatus") Boolean updateCaseStatus);

    /**
     * 获取当前查询词条最大公开日期
     *
     * @param queryWord
     * @return
     */
    Date getMaxPublicDate(@Param("queryWord") String queryWord);
}
