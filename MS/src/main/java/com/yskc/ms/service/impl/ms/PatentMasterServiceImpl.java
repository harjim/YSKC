package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PatentMasterDao;
import com.yskc.ms.dao.rs.RsPatentPlanDao;
import com.yskc.ms.entity.ms.PatentMasterEntity;
import com.yskc.ms.entity.rs.RsPatentPlanEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.MiniModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patentMaster.PatentMasterModel;
import com.yskc.ms.models.patentMaster.QueryMasterModel;
import com.yskc.ms.service.ms.PatentMasterService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hck
 * on 2020/7/7 14:59
 * description:
 */
@Service
public class PatentMasterServiceImpl implements PatentMasterService {

    @Autowired
    private PatentMasterDao patentMasterDao;
    @Autowired
    private RsPatentPlanDao rsPatentPlanDao;

    @Override
    public PageModel<List<PatentMasterModel>> queryMasterList(QueryMasterModel model, UserInfo userInfo, DataPermModel perm) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentMasterDao.getList(page, model, perm));
    }

    @Override
    public Boolean addMaster(PatentMasterModel model, UserInfo userInfo) throws OwnerException {
        PatentMasterEntity patentMasterEntity = new PatentMasterEntity();
        limitMasterName(model.getMasterName(), null);
        BeanUtils.copyProperties(model, patentMasterEntity);
        patentMasterEntity.setCreateTime(new Date());
        patentMasterEntity.setLastUpdateTime(new Date());
        patentMasterEntity.setCreatorId(userInfo.getId());
        patentMasterEntity.setLastUpdatorId(userInfo.getId());
        return patentMasterDao.insert(patentMasterEntity) > 0;
    }

    @Override
    public Boolean updateMaster(PatentMasterModel model, UserInfo userInfo) throws OwnerException {
        PatentMasterEntity patentMasterEntity = patentMasterDao.selectById(model.getId());
        limitMasterName(model.getMasterName(), model.getId());
        BeanUtils.copyProperties(model, patentMasterEntity);
        patentMasterEntity.setLastUpdateTime(new Date());
        patentMasterEntity.setLastUpdatorId(userInfo.getId());
        return patentMasterDao.updateById(patentMasterEntity) > 0;
    }

    @Override
    public Boolean delMaster(PatentMasterModel model) throws OwnerException {
        List<RsPatentPlanEntity> rsPatentPlanEntities = rsPatentPlanDao.queryListByMasterId(model.getId());
        if (!CollectionUtils.isEmpty(rsPatentPlanEntities)) {
            throw new OwnerException("该负责人已有负责的专利，不能删除");
        }
        return patentMasterDao.deleteById(model.getId()) > 0;
    }

    @Override
    public List<PatentMasterModel> getMasterList(QueryMasterModel model) {
        List<PatentMasterModel> list = patentMasterDao.getMasterList(model);
        return list;
    }

    @Override
    public boolean checkMasterName(PatentMasterModel model, UserInfo userInfo) throws OwnerException {
        return limitMasterName(model.getMasterName(), model.getId());
    }

    @Override
    public List<MiniModel> getSelect() {
        return patentMasterDao.getSelect();
    }


    private Boolean limitMasterName(String patentName, Integer id) throws OwnerException {
        PatentMasterEntity patentMaster = patentMasterDao.checkName(patentName, id);
        if (patentMaster != null) {
            throw new OwnerException(MessageFormat.format("{0}已存在，不可重复！", patentName));
        }
        return true;
    }
}
