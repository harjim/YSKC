package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.CheckInstDao;
import com.yskc.ms.dao.ms.CheckInstPriceDao;
import com.yskc.ms.entity.ms.CheckInstEntity;
import com.yskc.ms.entity.ms.CheckInstPriceEntity;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckInstModel;
import com.yskc.ms.models.checkInst.CheckPriceModel;
import com.yskc.ms.models.checkInst.QueryCheckInstModel;
import com.yskc.ms.service.ms.CheckInstService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: ms
 * @description: 查新机构业务层
 * @author: cyj
 * @create: 2022-08-09 10:25
 **/
@Service
public class CheckInstServiceImpl implements CheckInstService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CheckInstDao checkInstDao;
    @Autowired
    private CheckInstPriceDao checkInstPriceDao;

    @Override
    public PageModel<List<CheckInstModel>> getList(QueryCheckInstModel query, UserInfo info) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("ch.lastUpdateTime");
        }
        List<CheckInstModel> list = checkInstDao.getList(query,page);
        if (!CollectionUtils.isEmpty(list)){
            Map<Integer, CheckInstModel> instModelMap = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
            List<CheckPriceModel> priceList = checkInstPriceDao.getPriceList(instModelMap.keySet());
            if (!CollectionUtils.isEmpty(priceList)){
                Map<Integer, List<CheckPriceModel>> priceMap = priceList.stream().collect(Collectors.groupingBy(a -> a.getCheckInstId()));
                for (Integer instId : instModelMap.keySet()) {
                    if (priceMap.containsKey(instId)){
                        instModelMap.get(instId).setList(priceMap.get(instId));
                    }
                }

            }
            return PageUtils.buildPageResult(page,list);
        }else {
            list = new ArrayList<>();
        }
        return PageUtils.buildPageResult(page,list);
    }

    @Override
    public Boolean addCheckInst(CheckInstModel model, UserInfo info) throws OwnerException {
        if (model==null){
            return true;
        }
        CheckInstModel instName = checkInstDao.getInstName(model);
        if (instName!=null){
            throw new OwnerException("机构名称重复，请重新输入！");
        }
        List<CheckPriceModel> priceModelList = model.getList();
        if (!CollectionUtils.isEmpty(priceModelList)){
            Integer days = null;
            for (CheckPriceModel priceModel : priceModelList) {
                if (days!=null&&days>=priceModel.getDays()){
                    throw new OwnerException("收费标准的天数应逐条递增，请重新输入！");
                }
                days = priceModel.getDays();
            }
        }
        List<CheckInstPriceEntity> priceEntities = new ArrayList<>();
        CheckInstEntity entity = new CheckInstEntity();
        entity.setInstName(model.getInstName());
        entity.setAccountName(model.getAccountName());
        entity.setAccountBank(model.getAccountBank());
        entity.setAccountNo(model.getAccountNo());
        entity.setPayRemark(model.getPayRemark());
        entity.setPostage(model.getPostage());
        entity.setLinkMan(model.getLinkMan());
        entity.setLinkTel(model.getLinkTel());
        entity.setFilePath(model.getFilePath());
        entity.setRemark(model.getRemark());
        ToolUtil.entityCreate(entity, info.getId(), new Date());


        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        Boolean result;
        try {
         result = checkInstDao.addCheckInst(entity)>0;

            if (!CollectionUtils.isEmpty(priceModelList)){
                for (CheckPriceModel priceModel : priceModelList) {
                  CheckInstPriceEntity priceEntity = new CheckInstPriceEntity();
                  priceEntity.setCheckInstId(entity.getId());
                  priceEntity.setCheckType(priceModel.getCheckType());
                  priceEntity.setAmount(priceModel.getAmount());
                  priceEntity.setDays(priceModel.getDays());
                  ToolUtil.entityCreate(priceEntity, info.getId(), new Date());
                  priceEntities.add(priceEntity);
                }
               result = checkInstPriceDao.insertPriceList(priceEntities)>0;
            }
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (Exception e){
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            logger.error(e.getMessage(),e);
            throw new OwnerException("添加失败！");
        }

        return result;
    }

    @Override
    public Boolean updateCheckInst(CheckInstModel model, UserInfo info) throws OwnerException {
        if (model==null){
            return true;
        }
        CheckInstModel instName = checkInstDao.getInstName(model);
        if (instName!=null){
            throw new OwnerException("机构名称重复，请重新输入！");
        }
        List<CheckPriceModel> priceModelList = model.getList();
        if (!CollectionUtils.isEmpty(priceModelList)){
            Integer days = null;
            for (CheckPriceModel priceModel : priceModelList) {
                if (days!=null&&days>=priceModel.getDays()){
                    throw new OwnerException("收费标准的天数应逐条递增，请重新输入！");
                }
                days = priceModel.getDays();
            }
        }

        List<CheckInstPriceEntity> insertPrices = new ArrayList<>();
        List<CheckInstPriceEntity> updatePrices = new ArrayList<>();
        List<Integer> priceIds = new ArrayList<>();
        CheckInstEntity entity = new CheckInstEntity();
        entity.setInstName(model.getInstName());
        entity.setAccountName(model.getAccountName());
        entity.setAccountBank(model.getAccountBank());
        entity.setAccountNo(model.getAccountNo());
        entity.setPayRemark(model.getPayRemark());
        entity.setPostage(model.getPostage());
        entity.setLinkMan(model.getLinkMan());
        entity.setLinkTel(model.getLinkTel());
        entity.setId(model.getId());
        entity.setFilePath(model.getFilePath());
        entity.setRemark(model.getRemark());
        ToolUtil.entityUpdate(entity, info.getId(), new Date());

        if (!CollectionUtils.isEmpty(priceModelList)){
            for (CheckPriceModel priceModel : priceModelList) {
                CheckInstPriceEntity priceEntity = new CheckInstPriceEntity();

                priceEntity.setCheckInstId(entity.getId());
                priceEntity.setCheckType(priceModel.getCheckType());
                priceEntity.setAmount(priceModel.getAmount());
                priceEntity.setDays(priceModel.getDays());
                ToolUtil.entityCreate(priceEntity, info.getId(), new Date());
                if (priceModel.getId()!=null&&priceModel.getId()!=0){
                    priceIds.add(priceModel.getId());
                    priceEntity.setId(priceModel.getId());
                    updatePrices.add(priceEntity);
                }else {
                    insertPrices.add(priceEntity);
                }

            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            checkInstDao.updateById(entity);

            if (!CollectionUtils.isEmpty(priceIds)){
                checkInstPriceDao.delPriceList(priceIds);
            }
            if (!CollectionUtils.isEmpty(updatePrices)){
                checkInstPriceDao.updatePriceList(updatePrices);
            }
            if (!CollectionUtils.isEmpty(insertPrices)){
                checkInstPriceDao.insertPriceList(insertPrices);
            }
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (Exception e){
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            logger.error(e.getMessage(),e);
            throw new OwnerException("编辑失败！");
        }

        return true;
    }

    @Override
    public Boolean delCheckInst(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)){
            return true;
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);

        try {

            checkInstDao.deleteBatchIds(ids);
            checkInstPriceDao.delPriceByInst(ids);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (Exception e){
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            logger.error(e.getMessage(),e);
            throw new OwnerException("删除失败！");
        }

        return true;
    }


}
