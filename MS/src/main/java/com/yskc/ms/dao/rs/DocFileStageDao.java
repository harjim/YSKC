package com.yskc.ms.dao.rs;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.yskc.ms.entity.rs.DocFileStageEntity;
import com.yskc.ms.models.doc.DocFileBaseModel;
import com.yskc.ms.models.rs.DocFileStageModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocFileStageDao extends BaseMapper<DocFileStageEntity> {

    List<DocFileStageModel> queryDocFileStageList(@Param("stageKey") String stageKey);

    int saveDocFileStages(@Param("docFileStages") List<DocFileStageEntity> docFileStages);

    Integer getMaxSeq(@Param("stageKey") String stageKey);

    Integer updateDocFileStage(@Param("docFileStageEntity") DocFileStageEntity docFileStageEntity);

    List<DocFileStageEntity> findByIdSeq(@Param("ids") Integer[] ids);

    /**
     * 获取阶段不存在的文档
     *
     * @param stageKey
     * @return
     */
    List<DocFileBaseModel> getStageNoDocFiles(@Param("stageKey") String stageKey);

    /**
     * 根据文件类型id获取阶段配置
     *
     * @param docFileId
     * @return
     */
    DocFileStageEntity getByDocFileId(@Param("docFileId") Integer docFileId);
}
