package com.yskc.rs.service.impl;

import com.yskc.rs.dao.BuildConfigDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.buildconfig.BuildConfigModel;
import com.yskc.rs.service.BuildConfigService;
import com.yskc.rs.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.*;

/**
 * @program: rs
 * @description: 机构建设事项事项配置
 * @author: cyj
 * @create: 2022-01-05 09:04
 **/
@Service
public class BuildConfigServiceImpl implements BuildConfigService {
    @Autowired
    private BuildConfigDao buildConfigDao;

    @Override
    public boolean saveBuildConfig(BuildConfigModel[] models, UserInfo info) {
        boolean save = false;
        Date now = new Date();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        List<BuildConfigModel> list = Arrays.asList(models);
        for(BuildConfigModel model:list){
            if(null!=model.getId()){
                model.setLastUpdatorId(info.getUserId());
                model.setLastUpdateTime(now);
                model.setMsLastUpdatorId(info.getMsUserId());
            } else {
                model.setCompanyId(info.getCompanyId());
                model.setCreateTime(now);
                model.setLastUpdateTime(now);
                model.setCreatorId(info.getUserId());
                model.setLastUpdatorId(info.getUserId());
                model.setMsLastUpdatorId(info.getMsUserId());
                model.setMsCreatorId(info.getMsUserId());
            }
        }
        try{
            save = buildConfigDao.batchSave(list)>0;
            TransactionUtils.commit(transactionStatus);
            return save;
        }catch (Exception e){
            e.printStackTrace();
            TransactionUtils.rollback(transactionStatus);
            return false;
        }
    }

    @Override
    public List<BuildConfigModel> getBuildConfig(Integer companyId, Integer year) {
        return buildConfigDao.getList(companyId,year);
    }
}
