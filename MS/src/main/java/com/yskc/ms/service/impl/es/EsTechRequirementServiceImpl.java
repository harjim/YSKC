package com.yskc.ms.service.impl.es;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.es.EsTechRequirementDao;
import com.yskc.ms.entity.es.TechRequirementEntity;
import com.yskc.ms.enums.RequirementStatus;
import com.yskc.ms.models.newexpert.techrequirement.EsQueryTechRequirementModel;
import com.yskc.ms.models.newexpert.techrequirement.EsTechRequirementModel;
import com.yskc.ms.service.es.EsTechRequirementService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author cyj
 */
@Service
public class EsTechRequirementServiceImpl implements EsTechRequirementService {
    @Autowired
    private EsTechRequirementDao esTechRequirementDao;

    @Override
    public PageModel<List<EsTechRequirementModel>> getList(EsQueryTechRequirementModel query) {
        Pagination page = query.getPagination();
        Date now = new Date();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(Arrays.asList("t.lastUpdateTime"));
        }
        List<EsTechRequirementModel> list = null;
        list = esTechRequirementDao.getList(page,query);
        list.forEach(requirementModel->{
            Integer status = requirementModel.getStatus();
            if(status != 2 && status != 3 ){
                requirementModel.setEditalbe(true);
            }
            if(status==1){
                Date closeDate = requirementModel.getCloseDate();
                if (null != closeDate && closeDate.before(now)){
                    requirementModel.setStatus(RequirementStatus.PASS_DUE.getCode());
                }
            }
        });
        return PageUtils.build(page,list);
    }

    @Override
    public EsTechRequirementModel getData(Integer id) {
        EsTechRequirementModel model = esTechRequirementDao.getData(id);
        if(model.getKeywords()!=null&&!model.getKeywords().isEmpty()){
            String keywords = model.getKeywords();
            keywords = keywords.substring(1,model.getKeywords().length()-1);
            model.setKeywords(keywords);
        }
        return model;
    }

    @Override
    public Boolean save(EsTechRequirementModel model,Integer id) throws OwnerException{
        Date now = new Date();
        if (null != model.getCloseDate() && model.getCloseDate().before(now)) {
            throw new OwnerException("截止日期不能小于当前日期！");
        }
        if (!(model.getKeywords()==null||model.getKeywords().isEmpty())){
            StringBuilder sb = new StringBuilder();
            sb.append(",");
            sb.append(model.getKeywords());
            sb.append(",");
            model.setKeywords(sb.toString());
        }
        if (null != model.getId()) {
            TechRequirementEntity entity = esTechRequirementDao.selectById(model.getId());
            if (null != entity) {
                BeanUtils.copyProperties(model, entity);
                entity.update(now, id);
                if (model.getIssue() == 1) {
                    entity.setStatus(RequirementStatus.DOING.getCode());
                } else {
                    if(model.getStatus() == 5){
                        entity.setStatus(RequirementStatus.DRAFT.getCode());
                    }
                }
                return esTechRequirementDao.updateTech(entity) > 0;

            } else {
                return false;
            }
        } else {
            TechRequirementEntity requirementEntity = new TechRequirementEntity();
            BeanUtils.copyProperties(model,requirementEntity);
            requirementEntity.create(now,id);
            if (model.getIssue() == 1) {
                requirementEntity.setStatus(RequirementStatus.DOING.getCode());
            } else {
                requirementEntity.setStatus(RequirementStatus.DRAFT.getCode());
            }
            return esTechRequirementDao.insert(requirementEntity)>0;
        }
    }

    @Override
    public Boolean editStatus(EsTechRequirementModel model,Integer id) {
        TechRequirementEntity entity = new TechRequirementEntity();
        entity.update(new Date(),id);
        entity.setId(model.getId());
        entity.setStatus(model.getStatus());
        return esTechRequirementDao.updateById(entity) > 0;
    }

    @Override
    public Boolean del(Integer[] ids) throws OwnerException{
        List<EsTechRequirementModel> models = esTechRequirementDao.getStatus(ids);
        List<Integer> list = Arrays.asList(2, 3);
        for (EsTechRequirementModel model : models) {
            if (list.contains(model.getStatus())) {
                String message = MessageFormat.format("技术需求：【{0}】 属于不可编辑状态，无法操作！", model.getRequirementName());
                throw new OwnerException(message);
            }
        }
        return esTechRequirementDao.deleteBatchIds(Arrays.asList(ids))>0;
    }

}
