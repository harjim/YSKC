package com.yskc.rs.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.rs.entity.DocFileTrialEntity;
import com.yskc.rs.models.projectyieldconfig.ProjectYieldConfigModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/7/3 14:36
 * description:
 */
@Repository
public interface DocFileTrialDao extends BaseMapper<DocFileTrialEntity> {

    Integer insertList(@Param("entities")List<DocFileTrialEntity> entities);

    Integer delDocFileTrial(@Param("companyId") Integer companyId, @Param("trialId")Integer trialId, @Param("docFileId")Integer docFileId);

    /**
     * 根据试制id查询
     * @param companyId
     * @param trialId
     * @return
     */
    DocFileTrialEntity queryByTrialId(@Param("companyId")Integer companyId,@Param("trialId")Integer trialId);

    Integer delByDocFileId(@Param("docFileId") Integer docFileId,@Param("companyId")Integer companyId);

    /**
     * 根据TrialId获取统计
     * @param ids
     * @return
     */
    List<Integer> getCountByTrialId(@Param("ids") List<Integer> ids);
}
