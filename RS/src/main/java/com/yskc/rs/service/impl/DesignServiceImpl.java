package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.DesignDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.DesignEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.design.DesignModel;
import com.yskc.rs.models.design.DesignQuery;
import com.yskc.rs.models.excel.DesginExcel;
import com.yskc.rs.models.projectdesign.QueryProjectDesignModel;
import com.yskc.rs.service.DesignService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class DesignServiceImpl extends ServiceImpl<DesignDao, DesignEntity> implements DesignService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DesignDao designDao;

    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param companyId
     * @param designQuery
     * @return
     */
    @Override
    public PageModel<List<DesignModel>> queryDesignEntity(int companyId, DesignQuery designQuery) {
        Pagination page = designQuery.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("d.designDate");
            page.setDescs(descs);
        }
        if (designQuery.getDesignDate() != null) {
            designQuery.setDesignDate(com.yskc.common.utils.DateUtil.getMonthFirstDay(designQuery.getDesignDate()));
        }
        return PageUtils.build(page, designDao.queryDesign(page, companyId, designQuery));

    }

    /**
     * @param info
     * @param dModel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean updateDesign(UserInfo info, DesignModel dModel) throws OwnerException {
        DesignEntity entity = setValue(0, info, dModel);
        entity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
        entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        entity.setLastUpdateTime(new Date());
        entity.setRemainDFee(dModel.getdFee());
        return designDao.updateById(entity) > 0;
    }

    /**
     * @param currentUserId
     * @param userInfo
     * @param dModel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean addDesign(int currentUserId, UserInfo userInfo, DesignModel dModel) throws OwnerException {
        DesignEntity designEntity=setValue(currentUserId, userInfo, dModel);
        designEntity.setRemainDFee(dModel.getdFee());
        return designDao.insert(designEntity) > 0;
    }

    /**
     * @param currentUserId
     * @param userInfo
     * @param dModel
     * @return
     */
    public DesignEntity setValue(int currentUserId, UserInfo userInfo, DesignModel dModel) {
        DesignEntity dEntity = new DesignEntity();
        if (dModel.getId() > 0 || null != dModel.getId()) {
            dEntity.setId(dModel.getId());
        }
        dEntity.setCompanyId(userInfo.getCompanyId());
        dEntity.setDname(dModel.getDname());
        dEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        dEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
        dEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        dEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        dEntity.setCreateTime(new Date());
        dEntity.setLastUpdateTime(new Date());
        dEntity.setCreatorId(currentUserId);
        dEntity.setDesignDate(dModel.getDesignDate());
        dEntity.setDfee(dModel.getdFee());
        dEntity.setDeptName(dModel.getDeptName());
        dEntity.setAccountTitleId(dModel.getAccountTitleId());
        dEntity.setRemark(dModel.getRemark());
        return dEntity;
    }

    /**
     * @param companyId
     * @return
     */
    @Override
    public List<DesginExcel> exportDesignModel(Integer companyId, DesignQuery designQuery) {
        return designDao.getDesignData(companyId, designQuery);
    }

    /**
     * @param info
     * @param desginExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importDesign(UserInfo info, List<DesginExcel> desginExcels, Integer year) throws OwnerException {
        if (desginExcels.size() == 0) {
            return true;
        }
        List<String> fullAccountNameList = new ArrayList<>();
        desginExcels.forEach(item -> {
            if (!StringUtils.isEmpty(item.getFullAccountName())) {
                if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                    item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                }
                fullAccountNameList.add(item.getFullAccountName());
            }
        });
        List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameList);
        Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b));
        List<DesignEntity> designEntities = new ArrayList<>();
        for (int i = 0; i < desginExcels.size(); i++) {
            DesginExcel desginExcel = desginExcels.get(i);
            int accountTitleId = 0;
            if (accountTitleEntityMap.containsKey(desginExcel.getFullAccountName())) {
                accountTitleId = accountTitleEntityMap.get(desginExcel.getFullAccountName()).getId();
            }
            DesignModel model = new DesignModel();
            BeanUtil.copyProperties(desginExcel, model);
            // 默认为0
            model.setAccountTitleId(accountTitleId);
            DesignEntity designEntity=setValue(info.getId(), info, model);
            designEntity.setRemainDFee(model.getdFee());
            designEntities.add(designEntity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (designEntities.size() > 0) {
                //Integer num= designDao.addDesignBatch(designEntities);
                List<List<DesignEntity>> insertList = ListUtils.subList(designEntities, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<DesignEntity> items : insertList) {
                    designDao.addDesignBatch(items);
                }

            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("importDesgin", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    @Override
    public PageModel<List<DesignModel>> queryDesignByTerm(Integer companyId, QueryProjectDesignModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("designDate");
            page.setDescs(desc);
        }
        Date beginDate = null;
        Date endDate = null;
        if (query.getProjectMonth() != null) {
            beginDate = com.yskc.common.utils.DateUtil.getMonthFirstDay(query.getProjectMonth());
            endDate = com.yskc.common.utils.DateUtil.getMonthLastDay(beginDate);
        }
        return PageUtils.buildPageResult(page, designDao.queryDesignByTerm(page, companyId, beginDate, endDate, query),
                null, designDao.getFeeCount(companyId, beginDate, endDate, query));
    }

    /**
     * @param info
     * @param model
     * @return
     */
    @Override
    public boolean updateDesignRdDept(UserInfo info, DesignModel model) {
        List<DesignEntity> entities = model.getDesignEntities();
        int rdDeptId = 0;//model.getRdDeptId();
        for (int i = 0; i < entities.size(); i++) {
            DesignEntity entity = entities.get(i);
            entity.setRdDeptId(rdDeptId);
        }
        updateBatchById(entities);
        return true;
    }

    /**
     * @param dModel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delDesign(DesignModel dModel) throws OwnerException {
        Integer id = dModel.getId();
        List<Integer> usedIds = designDao.getProjectUsed(CollUtil.newArrayList(id));
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发费用核算-数据归集】中，不能删除!");
        }
        return designDao.deleteById(id) > 0;
    }

    /**
     * @param models
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delDesignBatch(List<DesignModel> models) throws OwnerException {
        List<Integer> ids = new ArrayList<>();
        models.forEach(item -> ids.add(item.getId()));
        List<Integer> usedIds = designDao.getProjectUsed(ids);
        Collection<Integer> result = CollUtil.disjunction(ids, usedIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选设计费用已全部在【创新项目】中使用，不能删除!");
        }
        return designDao.deleteBatchIds(result) > 0;
    }
}
