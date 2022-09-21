package com.yskc.ms.service.impl.es;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.TalentRequirementDao;
import com.yskc.ms.entity.es.TalentRequirementEntity;
import com.yskc.ms.enums.RequirementStatus;
import com.yskc.ms.models.newexpert.talentdelivery.QueryTalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentdelivery.TalentDeliveryModel;
import com.yskc.ms.models.newexpert.talentrequirement.QueryTalentRequirementModel;
import com.yskc.ms.models.newexpert.talentrequirement.TalentRequirementModel;
import com.yskc.ms.service.es.TalentRequirementService;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2021-12-08 10:47
 **/
@Service
public class TalentRequirementServiceImpl implements TalentRequirementService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TalentRequirementDao talentRequirementDao;

    @Override
    public PageModel<List<TalentRequirementModel>> getList(QueryTalentRequirementModel query) {
        Pagination page = query.getPagination();
        Date now = new Date();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(Arrays.asList("t.lastUpdateTime"));
        }
        List<TalentRequirementModel> list = null;
        list = talentRequirementDao.getList(page,query);
        list.forEach(model->{
            Integer status = model.getStatus();
            //如果状态为0：草稿/4：终止
            if(status!=1){
                model.setEditable(true);
            }
            //如果状态为1：发布中
            else{
                model.setEditable(false);
                Date closeDate = model.getCloseDate();
                if (null != closeDate && closeDate.before(now)){
                    model.setStatus(RequirementStatus.PASS_DUE.getCode());
                    model.setEditable(true);
                }
            }
        });
        return PageUtils.build(page,list);
    }

    @Override
    public TalentRequirementModel getData(Integer id) {
        TalentRequirementModel model = talentRequirementDao.getData(id);
        if(model.getKeywords()!=null&&!model.getKeywords().isEmpty()){
            String keywords = model.getKeywords();
            keywords = keywords.substring(1,model.getKeywords().length()-1);
            model.setKeywords(keywords);
        }
        return model;
    }

    @Override
    public Boolean del(Integer[] ids) throws OwnerException {
        List<TalentRequirementModel> models = talentRequirementDao.getStatusList(ids);
        List<Integer> list = Arrays.asList(0);
        for (TalentRequirementModel model : models) {
            if (!list.contains(model.getStatus())) {
                String message = MessageFormat.format("人才需求：【{0}】 属于不可编辑状态，无法操作！", model.getJob());
                throw new OwnerException(message);
            }
        }
        return talentRequirementDao.deleteBatchIds(Arrays.asList(ids))>0;
    }

    @Override
    public Boolean save(TalentRequirementModel model, Integer id) throws OwnerException {
        Date now = new Date();
        Boolean save = true;
        if (null != model.getCloseDate() && model.getCloseDate().before(now) && model.getIssue()) {
            throw new OwnerException("截止日期不能小于当前日期！");
        }
        if (!(model.getKeywords()==null||model.getKeywords().isEmpty())){
            StringBuilder sb = new StringBuilder();
            sb.append(",");
            sb.append(model.getKeywords());
            sb.append(",");
            model.setKeywords(sb.toString());
        }
        if (null != model.getId()){
            TalentRequirementEntity entity = talentRequirementDao.selectById(model.getId());
            if(null!=entity) {
                BeanUtils.copyProperties(model, entity);
                entity.setMsLastUpdatorId(id);
                entity.setLastUpdateTime(now);
                if (model.getIssue() != null && model.getIssue()) {
                    entity.setStatus(RequirementStatus.DOING.getCode());
                } else {
                    if (model.getStatus() == 5) {
                        entity.setStatus(RequirementStatus.DRAFT.getCode());
                    }
                }
            }
                return talentRequirementDao.updateById(entity)>0;
        }else {
            TalentRequirementEntity entity = new TalentRequirementEntity();
            BeanUtils.copyProperties(model,entity);
            entity.setMsCreatorId(id);
            entity.setCreateTime(now);
            entity.setMsLastUpdatorId(id);
            entity.setLastUpdateTime(now);
            entity.setStatus(model.getIssue()?RequirementStatus.DOING.getCode():RequirementStatus.DRAFT.getCode());
            return talentRequirementDao.insert(entity)>0;
        }
    }

    @Override
    public Boolean changeStatus(Integer id, Integer status,Integer userId) {
        if(id==null&&status==null){
            return null;
        }
        return talentRequirementDao.changeStatus(id,status,userId,new Date())>0;
    }

    @Override
    public PageModel<List<TalentDeliveryModel>> getDeliveryList(QueryTalentDeliveryModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(Arrays.asList("t.lastUpdateTime"));
        }
        List<TalentDeliveryModel> list = talentRequirementDao.getDeliveryList(page,query);
        if (!CollectionUtils.isEmpty(list)) {
            for(TalentDeliveryModel model : list){
                Date birthday = model.getBirthday();
                if(birthday!=null){
                    model.setAge(TalentDeliveryModel.getAgeByBirth(birthday));
                }
            }
        }
        return PageUtils.build(page,list);
    }
}
