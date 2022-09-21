package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.ms.SolutionShareEntity;
import com.yskc.ms.models.solution.SolutionShareModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:20
 * @Description: 解决方案分享dao层
 */
@Repository
public interface SolutionShareDao extends BaseMapper<SolutionShareEntity> {
    /**
     * 删除分享
     * @param solutionIds
     * @return
     */
    int deleteShare(@Param("solutionIds") List<Integer> solutionIds);

    /**
     * 获取分享详情
     * @param solutionId
     * @return
     */
    List<SolutionShareModel> getShareInfo(@Param("solutionId") Integer solutionId);

    /**
     * 插入或更新
     * @param list
     * @return
     */
    int inertOrUpdate(@Param("list") List<SolutionShareEntity> list);
}
