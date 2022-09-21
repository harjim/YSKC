package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.ProblemSummaryEntity;
import com.yskc.rs.models.projectSummary.ProblemSummaryModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Author: hck
 * @DateTime: 2021/2/24 13:35
 * @Description: dao
 */
@Repository
public interface ProblemSummaryDao extends BaseMapper<ProblemSummaryEntity> {
    /**
     * 根据项目情况总结id删除
     *
     * @param id
     * @return
     */
    Integer deleteBySituationId(@Param("id") Integer id);

    /**
     * 批量添加问题总结
     *
     * @param problemSummarys
     * @param userId
     * @param msUserId
     * @param date
     * @param situationId
     * @return
     */
    Integer insertList(@Param("problemSummarys") List<ProblemSummaryModel> problemSummarys, @Param("userId") Integer userId, @Param("msUserId") Integer msUserId, @Param("date") Date date, @Param("situationId") Integer situationId);

    /**
     * 根据项目情况id获取问题总结
     *
     * @param situationId
     * @return
     */
    List<ProblemSummaryModel> getBySituationId(@Param("situationId") Integer situationId);
}
