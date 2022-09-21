package com.yskc.ms.service.impl.rs;


import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.DocFileStageDao;
import com.yskc.ms.entity.rs.DocFileStageEntity;
import com.yskc.ms.models.doc.DocFileBaseModel;
import com.yskc.ms.models.params.DocFileStageParams;
import com.yskc.ms.models.rs.DocFileStageModel;
import com.yskc.ms.service.rs.DocFileStageService;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DocFileStageServiceImpl implements DocFileStageService {

    @Autowired
    private DocFileStageDao docFileStageDao;

    @Override
    public List<DocFileStageModel> queryDocFileStageList(String stageKey) {
        return docFileStageDao.queryDocFileStageList(stageKey);
    }

    @Override
    public Integer saveDocFileStages(DocFileStageParams docFileStageParams, Integer userId) throws OwnerException {
        if (docFileStageParams == null) {
            return null;
        }
        Date date = new Date();
        Integer maxSeq = docFileStageDao.getMaxSeq(docFileStageParams.getStageKey());
        if (maxSeq == null) {
            maxSeq = 1;
        } else {
            maxSeq = maxSeq + 1;
        }
        List<DocFileStageEntity> docFileStages = new ArrayList<>();
        for (Integer docFileId : docFileStageParams.getDocFileIds()) {
            DocFileStageEntity docFileStage = new DocFileStageEntity();
            docFileStage.setDocFileId(docFileId);
            docFileStage.setStageKey(docFileStageParams.getStageKey());
            docFileStage.setSeq(maxSeq);
            docFileStage.setCreatorId(-1);
            docFileStage.setLastUpdatorId(-1);
            docFileStage.setCreateTime(date);
            docFileStage.setLastUpdateTime(date);
            docFileStage.setMsCreatorId(userId);
            docFileStage.setMsLastUpdatorId(userId);
            docFileStage.setAutoAdd(false);
            docFileStage.setMonthly(false);
            docFileStage.setmPrefix(false);
            docFileStage.setNeedEdit(false);
            docFileStages.add(docFileStage);
            maxSeq ++;
        }

        return docFileStageDao.saveDocFileStages(docFileStages);
    }

    @Override
    public Integer updateDocFileStage(DocFileStageEntity docFileStageEntity, Integer userId) {
        docFileStageEntity.setMsLastUpdatorId(userId);
        docFileStageEntity.setLastUpdateTime(new Date());
        return docFileStageDao.updateDocFileStage(docFileStageEntity);
    }

    @Override
    public Boolean updateSeq(Integer[] ids, Integer userId) throws OwnerException {
        if (ids == null || ids.length == 0) {
            return null;
        }
        Date date = new Date();
        List<DocFileStageEntity> seqs = docFileStageDao.findByIdSeq(ids);
        DocFileStageEntity stageEntity = seqs.get(0);
        DocFileStageEntity entity = seqs.get(1);
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            Integer i = stageEntity.getSeq();
            stageEntity.setSeq(entity.getSeq());
            stageEntity.setLastUpdateTime(date);
            stageEntity.setMsLastUpdatorId(userId);
            entity.setSeq(i);
            entity.setLastUpdateTime(date);
            entity.setMsLastUpdatorId(userId);
            docFileStageDao.updateDocFileStage(entity);
            docFileStageDao.updateDocFileStage(stageEntity);
            TransactionUtils.commit(DataSourceEnum.RS, status);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.RS, status);
            throw new OwnerException(ErrorEnum.FAIL, e.getMessage());
        }
    }

    @Override
    public Integer delDocFileStage(Integer id) throws OwnerException {
        if (id == null || id == 0) {
            throw new OwnerException("删除失败,请联系管理员");
        }
        return docFileStageDao.deleteById(id);
    }

    @Override
    public List<DocFileBaseModel> getStageNoDocFiles(String stageKey) {
        return docFileStageDao.getStageNoDocFiles(stageKey);
    }
}
