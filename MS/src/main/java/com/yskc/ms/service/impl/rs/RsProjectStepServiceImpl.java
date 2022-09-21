package com.yskc.ms.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.ms.dao.rs.RsProjectStepDao;
import com.yskc.ms.dao.rs.RsStepStatusDao;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.entity.rs.RsProjectStepEntity;
import com.yskc.ms.entity.rs.RsStepStatusEntity;
import com.yskc.ms.entity.rs.models.StepResultModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.ParentStepModel;
import com.yskc.ms.models.project.QueryProjectStepModel;
import com.yskc.ms.models.project.StepListModel;
import com.yskc.ms.models.project.StepModel;
import com.yskc.ms.models.project.StepTreeModel;
import com.yskc.ms.service.rs.RsProjectStepService;
import com.yskc.ms.utils.TransactionUtils;
import org.apache.xerces.impl.xpath.XPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by hck
 * on 2020/5/19 17:18
 */
@Service
public class RsProjectStepServiceImpl implements RsProjectStepService {

    @Autowired
    private RsProjectStepDao rsProjectStepDao;
    @Autowired
    private RsStepStatusDao rsStepStatusDao;

    @Override
    public Boolean saveStep(UserInfo userInfo, ParentStepModel[] parentStepModels) {
        ParentStepModel parentStepModel = parentStepModels[0];
        Map<String, StepTreeModel> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(parentStepModel.getChildren())) {
            List<StepTreeModel> list=new ArrayList<>();
            int num=0;
            for (StepTreeModel stepTreeModel : parentStepModel.getChildren()) {
                num+=1;
                stepTreeModel.setParentId(-1);
                stepTreeModel.setSignId(num);
                stepTreeModel.setSeqId(-1);
                list.add(stepTreeModel);
                StepTreeModel.setTree(list, stepTreeModel);
            }
            TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
            try {
                if(!CollectionUtils.isEmpty(list)){
                    //删除类型下所有步骤(涉及批量增删改同时操作)
                    List<RsProjectStepEntity> rsProjectStepEntities=new ArrayList<>();
                    List<StepTreeModel> list1=rsProjectStepDao.queryStepTree(parentStepModel.getProductId());
                    if(!CollectionUtils.isEmpty(list1)) {
                        rsProjectStepDao.delStepsByProductId(parentStepModel.getProductId());
                    }
                    for (StepTreeModel step:list) {
                        RsProjectStepEntity rsProjectStepEntity=getChange(step,parentStepModel.getProductId(),userInfo);
                        rsProjectStepDao.insert(rsProjectStepEntity);
                        step.setId(rsProjectStepEntity.getId());
                    }
                    Map<Integer,Integer> map1=list.stream().collect(Collectors.toMap(StepTreeModel::getSignId, StepTreeModel::getId));
                    for (StepTreeModel step:list){
                        RsProjectStepEntity rsProjectStepEntity=getChange(step,parentStepModel.getProductId(),userInfo);
                        if(rsProjectStepEntity.getParentId()!=-1) {
                            rsProjectStepEntity.setParentId(map1.get(step.getSeqId()));
                        }
                        rsProjectStepEntity.setId(step.getId());
                        rsProjectStepEntities.add(rsProjectStepEntity);
                    }
                    rsProjectStepDao.updateList(rsProjectStepEntities);

                }
                TransactionUtils.commit(DataSourceEnum.MS, status);
                return true;
            } catch (Exception ex) {
                TransactionUtils.rollback(DataSourceEnum.MS, status);

            }
        }
        return true;
    }

    @Override
    public StepResultModel getStep(Integer productId) {
        StepResultModel stepResultModel = new StepResultModel();
        List<StepTreeModel> list = StepTreeModel.getTree(rsProjectStepDao.queryStepTree(productId));
        stepResultModel.setData(list);
        stepResultModel.setProductId(productId);
        RsStepStatusEntity rsStepStatusEntity = rsStepStatusDao.getStepStatus(productId);
        stepResultModel.setRemark(rsStepStatusEntity != null ? rsStepStatusEntity.getRemark() : "");
        stepResultModel.setStatus(rsStepStatusEntity != null ? rsStepStatusEntity.getStatus() : null);
        return stepResultModel;
    }

    @Override
    public StepModel addStep(UserInfo userInfo, QueryProjectStepModel model) throws OwnerException {
        Date date = new Date();
        RsStepStatusEntity stepStatus = rsStepStatusDao.getStepStatus(model.getProductId());
        if(stepStatus!=null){
            if(stepStatus.getStatus()==2 || stepStatus.getStatus()==1 ){
                throw new OwnerException("该流程已提交或已审核,添加失败");
            }
        }
        RsProjectStepEntity rsProjectStep = rsProjectStepDao.getStepByProductId(model.getProductId(), model.getParentId());
        if (rsProjectStep != null || model.getParentId() == -1) {
            RsProjectStepEntity rsProjectStepEntity = new RsProjectStepEntity();
            rsProjectStepEntity.setParentId(model.getParentId() == model.getProductId() ? -1 : model.getParentId());
            rsProjectStepEntity.setStepName(model.getStepName());
            rsProjectStepEntity.setProductId(model.getProductId());
            rsProjectStepEntity.setSeq(model.getSeq() != null ? model.getSeq() : 1);
            rsProjectStepEntity.setStepType(1);
            rsProjectStepEntity.setCreateTime(date);
            rsProjectStepEntity.setCreatorId(-1);
            rsProjectStepEntity.setLastUpdateTime(date);
            rsProjectStepEntity.setLastUpdatorId(-1);
            rsProjectStepEntity.setMsCreatorId(userInfo.getId());
            rsProjectStepEntity.setMsLastUpdatorId(userInfo.getId());
            if(rsProjectStepDao.insert(rsProjectStepEntity) > 0) {
                StepModel stepModel = new StepModel();
                stepModel.setId(rsProjectStepEntity.getId());
                stepModel.setParentId(rsProjectStepEntity.getParentId());
                stepModel.setProductId(rsProjectStepEntity.getProductId());
                stepModel.setStepName(rsProjectStepEntity.getStepName());
                stepModel.setSeq(rsProjectStepEntity.getSeq());
                return stepModel;
            }
        }
        throw new OwnerException("添加失败");

    }

    @Override
    public Boolean addStepList(UserInfo info, List<StepListModel> models) throws OwnerException {
        Date date=new Date();
        List<RsProjectStepEntity> list=new ArrayList<>();
        if(!CollectionUtils.isEmpty(models)){
            for (StepListModel step:models) {
                if(step.getParentId()==null){
                    throw new OwnerException(step.getStepName()+"添加失败,请确认该节点父节点已添加");
                }
                RsProjectStepEntity rsProjectStepEntity=new RsProjectStepEntity();
                rsProjectStepEntity.setParentId(step.getParentId());
                rsProjectStepEntity.setStepName(step.getStepName());
                rsProjectStepEntity.setProductId(step.getProductId());
                rsProjectStepEntity.setSeq(step.getSeq() != null ? step.getSeq() : 1);
                rsProjectStepEntity.setStepType(1);
                rsProjectStepEntity.setCreateTime(date);
                rsProjectStepEntity.setCreatorId(-1);
                rsProjectStepEntity.setLastUpdateTime(date);
                rsProjectStepEntity.setLastUpdatorId(-1);
                rsProjectStepEntity.setMsCreatorId(info.getId());
                rsProjectStepEntity.setMsLastUpdatorId(info.getId());
                list.add(rsProjectStepEntity);
            }
            rsProjectStepDao.addStepList(list);
        }
        throw new OwnerException("插入数据为空,批量添加失败");
    }

    @Override
    public Boolean delStep(QueryProjectStepModel model) throws OwnerException {
        List<RsProjectStepEntity> list = rsProjectStepDao.getListByParentId(model.getId());
        if (!CollectionUtils.isEmpty(list)) {
            throw new OwnerException("存在后续操作,无法直接删除");
        }
        return rsProjectStepDao.deleteById(model.getId()) > 0;
    }

    @Override
    public Boolean editStep(UserInfo userInfo, QueryProjectStepModel model) throws OwnerException {
        RsStepStatusEntity rsStepStatusEntity = rsStepStatusDao.getStepStatus(model.getProductId());
        if (rsStepStatusEntity != null) {
            if(rsStepStatusEntity.getStatus()==1  && rsStepStatusEntity.getStatus() == 2){
                throw new OwnerException("步骤已提交或已审核,修改失败");
            }
        }
        RsProjectStepEntity rsProjectStepEntity = rsProjectStepDao.selectById(model.getId());
        // BeanUtil.copyProperties(model,rsProjectStepEntity);
        rsProjectStepEntity.setStepName(model.getStepName() != null ? model.getStepName() : rsProjectStepEntity.getStepName());
        rsProjectStepEntity.setMsLastUpdatorId(userInfo.getId());
        rsProjectStepEntity.setLastUpdateTime(new Date());
        rsProjectStepEntity.setLastUpdatorId(-1);
        rsProjectStepEntity.setSeq(model.getSeq()!=null?model.getSeq():1);
        return rsProjectStepDao.updateById(rsProjectStepEntity) > 0;
    }


    private RsProjectStepEntity getChange(StepTreeModel stepTreeModel,Integer productId,UserInfo userInfo){
        Date date=new Date();
        RsProjectStepEntity rsProjectStepEntity1 = new RsProjectStepEntity();
        rsProjectStepEntity1.setParentId(stepTreeModel.getParentId()!=null?stepTreeModel.getParentId():0);
        rsProjectStepEntity1.setStepName(stepTreeModel.getStepName());
        rsProjectStepEntity1.setProductId(productId);
        rsProjectStepEntity1.setSeq(stepTreeModel.getSeq() != null ? stepTreeModel.getSeq() : 1);
        rsProjectStepEntity1.setStepType(1);
        rsProjectStepEntity1.setCreateTime(date);
        rsProjectStepEntity1.setCreatorId(-1);
        rsProjectStepEntity1.setLastUpdateTime(date);
        rsProjectStepEntity1.setLastUpdatorId(-1);
        rsProjectStepEntity1.setMsCreatorId(userInfo.getId());
        rsProjectStepEntity1.setMsLastUpdatorId(userInfo.getId());
        return rsProjectStepEntity1;
    }

}
