package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.DataEquipmentDao;
import com.yskc.docservice.dao.rs.EquipmentDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.DataEquipmentEntity;
import com.yskc.docservice.entity.rs.EquipmentEntity;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DataEquipmentExcel;
import com.yskc.docservice.service.rs.DataEquipmentService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备使用记录
 *
 * @author zhangdingfu
 */
@Service
public class DataEquipmentServiceImpl implements DataEquipmentService {
    @Autowired
    private DataEquipmentDao dataEquipmentDao;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private AccountTitleDao accountTitleDao;

    /**
     * @param userInfo
     * @param dataEquipmentExcel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importInfo(RsUserInfo userInfo, List<DataEquipmentExcel> dataEquipmentExcel) throws OwnerException {
        if (dataEquipmentExcel.isEmpty()) {
            return true;
        }
        Set<String> ecodeSet = dataEquipmentExcel.stream().filter(a -> !StringUtils.isEmpty(a.getEcode())).map(DataEquipmentExcel::getEcode).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(ecodeSet)) {
            throw new OwnerException("未获取到资产代码,资产代码不能为空");
        }
        List<EquipmentEntity> equipmentList = equipmentDao.getEquipmentByEcodes(userInfo.getCompanyId(), ecodeSet);
        Map<String, String> ecodeAndEnameMap = equipmentList.stream().collect(Collectors.toMap(EquipmentEntity::getEcode, EquipmentEntity::getEname, (a, b) -> b));
        for (DataEquipmentExcel dataTemp : dataEquipmentExcel) {
            if (ecodeAndEnameMap.containsKey(dataTemp.getEcode()) && !ecodeAndEnameMap.get(dataTemp.getEcode()).trim().equals(dataTemp.getEname().trim())) {
                throw new OwnerException(
                        MessageFormat.format("导入数据中的资产代码：【{0}】的设备名称:【{1}】跟设备列表中的设备名称：【{2}】不一致，请检查修复后再提交",
                                dataTemp.getEcode(), dataTemp.getEname(), ecodeAndEnameMap.get(dataTemp.getEcode())));
            }
        }
        Set<String> accountNameList = new HashSet<>();
        dataEquipmentExcel.forEach(item -> {
            if (StringUtils.hasLength(item.getFullAccountName())) {
                if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                    item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                }
                accountNameList.add(item.getFullAccountName());
            }
        });
        List<AccountTitleEntity> accountTitleEntities = new ArrayList<>();
        if (!CollectionUtils.isEmpty(accountNameList)) {
            accountTitleEntities = accountTitleDao.getByFullAccountName(userInfo.getCompanyId(), accountNameList);
        }
        Map<String, Integer> accountMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(accountTitleEntities)) {
            accountMap = accountTitleEntities.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, AccountTitleEntity::getId, (a, b) -> b));
        }
        BigDecimal zero = BigDecimal.ZERO;
        Date now = new Date();
        Integer accountTitleId;
        List<DataEquipmentEntity> dataEquipments = new ArrayList<>();
        for (int i = 0; i < dataEquipmentExcel.size(); i++) {
            DataEquipmentExcel excel = dataEquipmentExcel.get(i);
            if (!ecodeAndEnameMap.containsKey(excel.getEcode())) {
                throw new OwnerException(MessageFormat.format("第{2,number,#}行，资产代码【{0}】，设备名称【{1}】不存在设备列表中，请先在设备列表中添加该设备。"
                        , excel.getEcode(), excel.getEname(), i + 2));
            }
            excel.setDepreciation(excel.getDepreciation() != null ? excel.getDepreciation() : zero);
            DataEquipmentEntity dataEquipmentEntity = DataEquipmentEntity.build(userInfo, now, Constant.DEFAULT_HOUR_EQU_DATA, excel);
            if (StringUtils.hasLength(excel.getFullAccountName()) && !excel.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                excel.setFullAccountName(excel.getFullAccountName().trim() + Constant.PATH_SEPARATOR);
            }
            accountTitleId = accountMap.getOrDefault(excel.getFullAccountName(), -1);
            dataEquipmentEntity.setAccountTitleId(accountTitleId);
            dataEquipments.add(dataEquipmentEntity);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            List<List<DataEquipmentEntity>> insertList = ListUtils.subList(dataEquipments, Constant.MAX_INSERT_OR_UPDATE_COUNT);
            for (List<DataEquipmentEntity> currentInsert : insertList) {
                dataEquipmentDao.insertOrUpdate(currentInsert);
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            throw new OwnerException("保存数据失败,请联系管理员");
        }
    }
}
