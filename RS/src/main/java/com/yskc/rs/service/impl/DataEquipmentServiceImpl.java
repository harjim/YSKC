package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.EquipmentDao;
import com.yskc.rs.dao.data.DataEquipmentDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.EquipmentEntity;
import com.yskc.rs.entity.data.DataEquipmentEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.data.DataEquipmentModel;
import com.yskc.rs.models.data.DataEquipmentModifyModel;
import com.yskc.rs.models.equipment.EquipmentMinModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.excel.DataEquipmentExcel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.service.DataEquipmentService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.ToolUtils;
import com.yskc.rs.utils.TransactionUtils;
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
     * @param companyId
     * @param queryEquipmentModel
     * @return
     */
    @Override
    public PageModel<List<DataEquipmentModel>> getDataEquitmentList(Integer companyId, QueryProjectEquipmentModel queryEquipmentModel) {
        Pagination page = queryEquipmentModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("de.`month`");
            page.setDescs(descs);
        }
        if (queryEquipmentModel.getMonth() != null) {
            queryEquipmentModel.setMonth(DateUtil.getMonthFirstDay(queryEquipmentModel.getMonth()));
        }
        return PageUtils.build(page, dataEquipmentDao.getDataEquipmentList(page, companyId, queryEquipmentModel));
    }

    /**
     * @param de
     * @param info
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean update(DataEquipmentModel de, UserInfo info) throws OwnerException {
        Date now = new Date();
        if (!StringUtils.isEmpty(de.getEcode())) {
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            try {

                dataEquipmentDao.updateWorkHours(de, info.getId(), info.getMsUserId(), now);
                equipmentDao.updateEtypeByEcode(info.getCompanyId(), de.getEtype(), de.getEcode());
                TransactionUtils.commit(transactionStatus);
                return true;
            } catch (Exception e) {
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException("保存失败，请稍后再试。");
            }
        } else {
            return dataEquipmentDao.updateWorkHours(de, info.getId(), info.getMsUserId(), now) > 0;
        }
    }

    /**
     * @param modifyModels
     * @param info
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean setWorkHour(List<DataEquipmentModifyModel> modifyModels, UserInfo info) throws OwnerException {
        return dataEquipmentDao.updateWorkHoursBatch(modifyModels) > 0;
    }

    /**
     * @param rdIdnexs
     * @return
     * @throws OwnerException
     */
    private boolean checkRd(List<Integer> rdIdnexs) throws OwnerException {
        if (rdIdnexs != null && !rdIdnexs.isEmpty()) {
            StringBuffer buffer = new StringBuffer();
            for (int index : rdIdnexs) {
                buffer.append("RD" + index + ",");
            }
            throw new OwnerException(MessageFormat.format("已在项目[{0}]中使用，不能修改!", buffer.substring(0, buffer.length() - 1)));
        }
        return true;
    }

    /**
     * @param value
     * @param companyId
     * @return
     */
    @Override
    public List<EquipmentMinModel> getEquipment(String value, Integer companyId) {
        return equipmentDao.getEquipment(value, companyId);
    }

    /**
     * @param equipmentEntity
     * @param userInfo
     * @return
     */
    @Override
    public Boolean save(DataEquipmentEntity equipmentEntity, UserInfo userInfo) {
        equipmentEntity.setRemainEquData(Constant.DEFAULT_HOUR_EQU_DATA);
        equipmentEntity.setEquData(Constant.DEFAULT_HOUR_EQU_DATA);
//        equipmentEntity.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//        equipmentEntity.setLastUpdatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
//        equipmentEntity.setMsCreatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
//        equipmentEntity.setMsLastUpdatorId(userInfo.getUserSource() == 1 ? userInfo.getId() : -1);
//
//        equipmentEntity.setCreateTime(new Date());
//        equipmentEntity.setLastUpdateTime(new Date());
        ToolUtils.entityCreate(equipmentEntity,userInfo.getUserId(),userInfo.getMsUserId(),new Date());
        equipmentEntity.setCompanyId(userInfo.getCompanyId());
        if (null != equipmentEntity.getEtype()) {
            equipmentDao.updateEtypeByEcode(userInfo.getCompanyId(), equipmentEntity.getEtype(), equipmentEntity.getEcode());
        }
        return dataEquipmentDao.insertOrUpdate(CollUtil.newArrayList(equipmentEntity)) > 0;
    }

    @Override
    public Boolean updateEtype(UserInfo info, EquipmentModifyModel modifyModel) {
        return dataEquipmentDao.updateEtype(info.getCompanyId(), info.getUserId(), info.getMsUserId(), new Date(), modifyModel) > 0;
    }

    /**
     * @param companyId
     * @param de
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delete(Integer companyId, DataEquipmentEntity de) throws OwnerException {
        int id = de.getId();
        List<Integer> usedIds = dataEquipmentDao.getProjectUsed(companyId, CollUtil.newArrayList(id));
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发费用核算-数据归集】中，不能删除!");
        }
        return dataEquipmentDao.deleteById(id) > 0;
    }

    /**
     * @param companyId
     * @param modifyModels
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean dels(Integer companyId, List<DataEquipmentModifyModel> modifyModels) throws OwnerException {
        List<Integer> ids = new ArrayList<>();
        modifyModels.forEach(item -> {
            ids.add(item.getId());
        });
        List<Integer> usedIds = dataEquipmentDao.getProjectUsed(companyId, ids);
        Collection<Integer> result = CollUtil.disjunction(ids, usedIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选设备已全部在【创新项目-研发费用核算-数据归集】中使用，不能删除!");
        }
        return dataEquipmentDao.deleteBatchIds(result) > 0;
    }

    /**
     * @param userInfo
     * @param dataEquipmentExcel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importInfo(UserInfo userInfo, List<DataEquipmentExcel> dataEquipmentExcel) throws OwnerException {
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
        List<String> accountNameList = new ArrayList<>();
        dataEquipmentExcel.forEach(item -> {
            if (!StringUtils.isEmpty(item.getFullAccountName())) {
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
            if (excel.getDepreciation() == null) {
                excel.setDepreciation(zero);
            }

            excel.setDepreciation(excel.getDepreciation() != null ? excel.getDepreciation() : zero);
            DataEquipmentEntity dataEquipmentEntity = DataEquipmentEntity.build(userInfo, now, Constant.DEFAULT_HOUR_EQU_DATA, excel);
            if (!StringUtils.isEmpty(excel.getFullAccountName()) && !excel.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                excel.setFullAccountName(excel.getFullAccountName().trim() + Constant.PATH_SEPARATOR);
            }
            accountTitleId = accountMap.getOrDefault(excel.getFullAccountName(), -1);
            dataEquipmentEntity.setAccountTitleId(accountTitleId);
            dataEquipments.add(dataEquipmentEntity);
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            List<List<DataEquipmentEntity>> insertList = ListUtils.subList(dataEquipments, Constant.MAX_INSERT_OR_UPDATE_COUNT);
            for (List<DataEquipmentEntity> currentInsert : insertList) {
                dataEquipmentDao.insertOrUpdate(currentInsert);
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("保存数据失败,请联系管理员");
        }
    }

    @Override
    public List<DataEquipmentExcel> exportDataEquipment(UserInfo info, QueryProjectEquipmentModel queryEquipmentModel) {
        return dataEquipmentDao.exportDataEquipment(info.getCompanyId(), queryEquipmentModel);
    }
}
