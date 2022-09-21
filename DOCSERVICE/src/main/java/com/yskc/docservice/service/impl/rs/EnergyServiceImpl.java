package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.EnergyDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.EnergyEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EnergyExcel;
import com.yskc.docservice.service.rs.EnergyService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
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
     * @param info
     * @param energyExcels
     * @param type
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importEnergy(RsUserInfo info, List<EnergyExcel> energyExcels, Integer type, Integer year) throws OwnerException {
        if (energyExcels.size() <= 0) {
            return true;
        }
        Set<String> accountNameList = energyExcels.stream().filter(a -> !StringUtils.isEmpty(a.getFullAccountName())).map(EnergyExcel::getFullAccountName).collect(Collectors.toSet());
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            for (List<EnergyEntity> energyList : subEnergies) {
                energyDao.addList(energyList);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        }
    }
}
