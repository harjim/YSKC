package com.yskc.ms.service.rs;

import com.yskc.common.exception.OwnerException;
import com.yskc.ms.entity.rs.DocFileStageEntity;
import com.yskc.ms.models.doc.DocFileBaseModel;
import com.yskc.ms.models.params.DocFileStageParams;
import com.yskc.ms.models.rs.DocFileStageModel;

import java.util.List;

public interface DocFileStageService {

    List<DocFileStageModel> queryDocFileStageList(String stageKey);

    Integer saveDocFileStages(DocFileStageParams docFileStageParams, Integer userId) throws OwnerException;

    Integer updateDocFileStage(DocFileStageEntity docFileStageEntity, Integer userId);

    Boolean updateSeq(Integer[] ids, Integer userId) throws OwnerException;

    Integer delDocFileStage(Integer id) throws OwnerException;

    /**
     * 获取阶段不存在的文档
     *
     * @param stageKey
     * @return
     */
    List<DocFileBaseModel> getStageNoDocFiles(String stageKey);
}
