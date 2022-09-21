package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.SolutionEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.solution.QuerySolutionModel;
import com.yskc.ms.models.solution.SolutionModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: zhangdingfu
 * @CreateTime: 2021-07-09 10:20
 * @Description: 知识库dao层
 */
@Repository
public interface SolutionDao extends BaseMapper<SolutionEntity> {
    /**
     * 获取解决方案列表
     *
     * @param page
     * @param query
     * @param dataPerm
     * @return
     */
    List<SolutionModel> getList(@Param("page") Pagination page, @Param("query") QuerySolutionModel query, @Param("perm") DataPermModel dataPerm);

    /**
     * 获取解决方案分享列表
     *
     * @param page
     * @param query
     * @param userId
     * @param paths
     * @return
     */
    List<SolutionModel> getShareList(@Param("page") Pagination page, @Param("query") QuerySolutionModel query,
                                     @Param("userId") Integer userId, @Param("paths") List<String> paths);

    /**
     * 更新解决方案
     *
     * @param entity
     * @return
     */
    int updateSolution(@Param("entity") SolutionEntity entity);

    /**
     * 更新星级
     *
     * @param rate
     * @param id
     * @param userId
     * @param now
     * @return
     */
    int updateRate(@Param("rate") Integer rate,@Param("id") Integer id, @Param("userId") Integer userId,
                   @Param("now") Date now);
}
