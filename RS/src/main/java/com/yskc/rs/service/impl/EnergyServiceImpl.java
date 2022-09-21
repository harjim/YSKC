package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.EnergyDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.EnergyEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.energy.EnergyModel;
import com.yskc.rs.models.energy.EnergyModifyModel;
import com.yskc.rs.models.energy.QueryEnergy;
import com.yskc.rs.models.excel.EnergyExcel;
import com.yskc.rs.service.EnergyService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:22
 * @Description: 能源损耗业务层实现
 */
@Service
public class EnergyServiceImpl implements EnergyService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnergyDao energyDao;

    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param companyId
     * @param queryEnergy
     * @return
     */
    @Override
    public PageModel<List<EnergyModel>> queryAll(Integer companyId, QueryEnergy queryEnergy) {
        Pagination page = queryEnergy.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("e.occDate");
            page.setDescs(descs);
        }
        return PageUtils.build(page, energyDao.queryAll(page, companyId, queryEnergy));
    }

    /**
     * @param info
     * @param energyExcels
     * @param type
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importEnergy(UserInfo info, List<EnergyExcel> energyExcels, Integer type, Integer year) throws OwnerException {
        if (energyExcels.size() <= 0) {
            return true;
        }
        List<String> accountNameList = energyExcels.stream().filter(a -> !StringUtils.isEmpty(a.getFullAccountName())).map(EnergyExcel::getFullAccountName).collect(Collectors.toList());
        List<AccountTitleEntity> accountTitleEntities = !CollectionUtils.isEmpty(accountNameList) ? accountTitleDao.getByFullAccountName(info.getCompanyId(), accountNameList) : new ArrayList<>();
        Map<String, Integer> accountMap = CollectionUtils.isEmpty(accountTitleEntities) ? new HashMap<>() : accountTitleEntities.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, AccountTitleEntity::getId));
        List<EnergyEntity> energies = new ArrayList<>();
        int length = energyExcels.size();
        int defaultAccountId;
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < length; i++) {
            EnergyExcel energyExcel = energyExcels.get(i);
            defaultAccountId = -1;
            EnergyEntity energyEntity = new EnergyEntity();
            // 设置默认类型
            energyEntity.setDeptName(energyExcel.getDeptName());
            energyEntity.setType(type);
            energyEntity.setCreateTime(now);
            energyEntity.setCreatorId(info.getUserId());
            energyEntity.setLastUpdatorId(info.getUserId());
            energyEntity.setMsCreatorId(info.getMsUserId());
            energyEntity.setMsLastUpdatorId(info.getMsUserId());
            energyEntity.setLastUpdateTime(now);
            energyEntity.setCompanyId(info.getCompanyId());
            energyEntity.setEname(energyExcel.getEname());
            energyEntity.setUnit(energyExcel.getUnit() == null ? "" : energyExcel.getUnit());
            energyEntity.setRemark(energyExcel.getRemark());
            energyEntity.setRemainAmount(energyExcel.getAmount());
            energyEntity.setOccDate(energyExcel.getOccDate());
            energyEntity.setAmount(energyExcel.getAmount());
            energyEntity.setAccNumber(energyExcel.getAccNumber());
            BigDecimal totalAmount = energyExcel.getTotalAmount();
            if (null == totalAmount) {
                totalAmount = energyExcel.getAmount();
            }
            energyEntity.setTotalAmount(totalAmount);
            energyEntity.setPriceQuantity(energyExcel.getUnitPrice(), energyExcel.getQuantity());
            c.setTime(energyExcel.getOccDate());
            // rdDept默认0
            energyEntity.setRdDeptId(0);
            if (!StringUtils.isEmpty(energyExcel.getFullAccountName()) && !energyExcel.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                energyExcel.setFullAccountName(energyExcel.getFullAccountName() + Constant.PATH_SEPARATOR);
                if (accountMap.containsKey(energyExcel.getFullAccountName().trim())) {
                    defaultAccountId = accountMap.get(energyExcel.getFullAccountName().trim());
                }
            }
            energyEntity.setAccountTitleId(defaultAccountId);
            energies.add(energyEntity);
        }
        List<List<EnergyEntity>> subEnergies = ListUtils.subList(energies, Constant.MAX_INSERT_OR_UPDATE_COUNT);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            for (List<EnergyEntity> energyList : subEnergies) {
                energyDao.addList(energyList);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }

    /**
     * 获取导出报表内容
     *
     * @param companyId
     * @param queryEnergy
     * @return
     */
    @Override
    public List<EnergyExcel> getExportEnergyList(Integer companyId, QueryEnergy queryEnergy) {
        return energyDao.getExportEnergyList(companyId, queryEnergy);
    }

    /**
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public Boolean addEnergy(UserInfo userInfo, EnergyModel model)throws OwnerException {
        EnergyEntity eEntity = new EnergyEntity();
        eEntity.setCreateTime(new Date());
        eEntity.setCreatorId(userInfo.getUserId());
        eEntity.setLastUpdatorId(userInfo.getUserId());
        eEntity.setMsCreatorId(userInfo.getMsUserId());
        eEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        eEntity.setLastUpdateTime(new Date());
        eEntity.setCompanyId(userInfo.getCompanyId());
        return energyDao.insert(getEnergyEntity(model, eEntity)) > 0;
    }

    /**
     * @param userInfo
     * @param model
     * @return
     */
    @Override
    public Boolean updateEnergy(UserInfo userInfo, EnergyModel model) throws OwnerException {
        EnergyEntity eEntity = energyDao.selectById(model.getId());
        eEntity.setLastUpdatorId(userInfo.getUserId());
        eEntity.setMsLastUpdatorId(userInfo.getMsUserId());
        eEntity.setLastUpdateTime(new Date());
        return energyDao.updateAllColumnById(getEnergyEntity(model, eEntity)) > 0;
    }

    /**
     * @param model
     * @param entity
     * @return
     */
    private EnergyEntity getEnergyEntity(EnergyModel model, EnergyEntity entity) throws OwnerException {
        entity.setEname(model.getEname());
        entity.setRemark(model.getRemark());
        entity.setUnit(model.getUnit() == null ? "" : model.getUnit());
        entity.setType(model.getType() == null ? 0 : model.getType());
        entity.setOccDate(model.getOccDate());
        if (null != entity.getId()) {
            BigDecimal sub =model.getAmount().subtract(entity.getAmount());
            BigDecimal remain;
            if (sub.compareTo(BigDecimal.ZERO) != 0) {
                remain = entity.getRemainAmount().add(sub);
                if (remain.compareTo(BigDecimal.ZERO) < 0) {
                    throw new OwnerException("保存失败，修改分配金额不能低于原分配金额。");
                }
            } else {
                remain = entity.getRemainAmount();
            }
            entity.setRemainAmount(remain);
        }else{
            entity.setRemainAmount(model.getAmount());
        }
        entity.setAmount(model.getAmount());
        entity.setDeptName(model.getDeptName());
        entity.setAccountTitleId(model.getAccountTitleId() == null ? -1 : model.getAccountTitleId());
        entity.setAccNumber(model.getAccNumber());
        BigDecimal totalAmount = model.getTotalAmount();
        if (null == totalAmount) {
            totalAmount = model.getAmount();
        }
        entity.setTotalAmount(totalAmount);
        entity.setPriceQuantity(model.getUnitPrice(), model.getQuantity());
        return entity;
    }

    /**
     * @param model
     * @return
     */
    @Override
    public boolean setRdDept(EnergyModifyModel model) {
        return energyDao.updateRdDept(model) > 0;
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean deleteById(Integer id) throws OwnerException {
        List<Integer> usedIds = energyDao.getProjectUsed(CollUtil.newArrayList(id));
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发费用核算-数据归集】中，不能删除!");
        }
        return energyDao.deleteById(id) > 0;
    }

    /**
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean deleteByIds(EnergyModifyModel model) throws OwnerException {
        List<Integer> ids = model.getIds();
        List<Integer> usedIds = energyDao.getProjectUsed(ids);
        Collection<Integer> result = CollUtil.disjunction(ids, usedIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选能源已全部在【创新项目-研发费用核算-数据归集】中使用，不能删除!");
        }
        return energyDao.deleteBatchIds(result) > 0;
    }
}
