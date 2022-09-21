package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.rs.RsStageEntity;
import com.yskc.ms.models.QueryStageModel;
import com.yskc.ms.models.SysDictionaryModel;
import com.yskc.ms.models.product.StageModel;
import com.yskc.ms.models.rs.RsStageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by hck
 * on 2020/9/2 14:44
 * description:
 */
@Repository
public interface RsStageDao extends BaseMapper<RsStageEntity> {

    List<RsStageModel> queryStage(@Param("companyId") Integer companyId, @Param("projectId") Integer projectId);


    List<SysDictionaryModel> searchStage();

    /**
     * 获取阶段信息
     *
     * @param projectId
     * @param pDocFileId
     * @return
     */
    RsStageEntity getStageInfo(@Param("projectId") Integer projectId, @Param("pDocFileId") Integer pDocFileId);


    /**
     * 获取项目阶段信息
     *
     * @param query
     * @return
     */
    List<RsStageModel> queryStageList(@Param("query") QueryStageModel query);
}
