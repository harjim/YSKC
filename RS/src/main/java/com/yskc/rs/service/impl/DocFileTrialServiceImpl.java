package com.yskc.rs.service.impl;

import com.yskc.rs.dao.DocFileTrialDao;
import com.yskc.rs.entity.DocFileTrialEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.trialprod.DocFileTrialModel;
import com.yskc.rs.service.DocFileTrialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/3 14:38
 * description:
 */
@Service
public class DocFileTrialServiceImpl implements DocFileTrialService {

    @Autowired
    private DocFileTrialDao docFileTrialDao;

    @Override
    public Boolean addDocFileTrial(UserInfo userInfo, List<DocFileTrialModel> models) {
        if(CollectionUtils.isEmpty(models)){
            return true;
        }
        List<DocFileTrialEntity> docFileTrialEntities=new ArrayList<>();
        for (DocFileTrialModel model:models) {
            DocFileTrialEntity trialEntity=new DocFileTrialEntity();
            trialEntity.setCompanyId(userInfo.getCompanyId());
            trialEntity.setCreateTime(new Date());
            trialEntity.setCreatorId(userInfo.getUserId());
            trialEntity.setMsCreatorId(-1);
            trialEntity.setPdocFileId(model.getPdocFileId());
            trialEntity.setTrialId(model.getTrialId());
            docFileTrialEntities.add(trialEntity);
        }
        return docFileTrialDao.insertList(docFileTrialEntities)>0;
    }

    @Override
    public Boolean delDocFileTrial(UserInfo userInfo, Integer trialId, Integer docFileId) {
        return docFileTrialDao.delDocFileTrial(userInfo.getCompanyId(),trialId,docFileId)>0;
    }
}
