package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.DeptDao;
import com.yskc.docservice.dao.rs.EquipmentDao;
import com.yskc.docservice.dao.rs.RdEquipmentDao;
import com.yskc.docservice.entity.rs.Dept;
import com.yskc.docservice.entity.rs.EquipmentEntity;
import com.yskc.docservice.entity.rs.RdEquipmentEntity;
import com.yskc.docservice.enums.EquipmentEnum;
import com.yskc.docservice.enums.OrgEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EquipmentExcel;
import com.yskc.docservice.models.rs.rdequipment.RdEquipmentModel;
import com.yskc.docservice.service.rs.RdEquipmentService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-12-02 09:15
 * @Description: 研发设备业务层实现
 */
@Service
public class RdEquipmentServiceImpl implements RdEquipmentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RdEquipmentDao rdEquipmentDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private EquipmentDao equipmentDao;

    @Override
    public String importRdEquipment(RsUserInfo info, List<EquipmentExcel> equipmentExcelList, Integer year) throws OwnerException {
        Integer companyId = info.getCompanyId();
        commonService.checkAuditModify(companyId, year, AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        if (CollectionUtils.isEmpty(equipmentExcelList)) {
            throw new OwnerException("导入数据为空,请添加后再导入!");
        }
        Map<String, Integer> rdDeptMap = commonService.initOrgFullPathMap(companyId, OrgEnum.RD_DEPT, year);
        Set<String> ecodeSet = equipmentExcelList.stream().filter(a -> StringUtils.hasLength(a.getEcode())).map(EquipmentExcel::getEcode).collect(Collectors.toSet());
        List<RdEquipmentModel> rdEquipmentModelList = rdEquipmentDao.getByEcodes(companyId, ecodeSet, year);
        Map<String, RdEquipmentModel> existRdEquipmentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdEquipmentModelList)) {
            rdEquipmentModelList.forEach(item -> existRdEquipmentMap.put(item.getEcode(), item));
        }
        List<EquipmentEntity> equipmentModelList = equipmentDao.getEquipmentByEcodes(companyId, ecodeSet);
        Map<String, EquipmentEntity> existEquipmentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(equipmentModelList)) {
            equipmentModelList.forEach(item -> existEquipmentMap.put(item.getEcode(), item));
        }

        Date date = new Date();
        List<RdEquipmentEntity> insertRdEquipmentList = new ArrayList<>();
        Map<String, Object> repeatedMap = new HashMap<>();
        List<RdEquipmentModel> updateList = new ArrayList<>();
        List<EquipmentEntity> insertEquipments = new ArrayList<>();
        int max = equipmentExcelList.size();
        Integer userId = info.getUserId();
        Integer msUserId = info.getMsUserId();
        for (int i = 0; i < max; i++) {
            EquipmentExcel equipmentExcel = equipmentExcelList.get(i);
            String ecode = equipmentExcel.getEcode();
            if (repeatedMap.containsKey(ecode)) {
                continue;
            }
            repeatedMap.put(ecode, null);
            Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, equipmentExcel.getRdDeptName().trim());
            if (rdDeptId == null) {
                throw new OwnerException(MessageFormat.format(Constant.IMPORT_DEPT_ERROR,
                        i + 2, equipmentExcel.getRdDeptName()));
            }
            Integer etype = EquipmentEnum.getByTitle(equipmentExcel.getTypeName()).getType();
            if (etype <= 0) {
                throw new OwnerException("资产代码：" + ecode + "，设备名称" + equipmentExcel.getEname() + "设备类型应为设备、仪器、软件摊销！");
            }

            if (!existEquipmentMap.containsKey(ecode)) {
                // throw new OwnerException("资产代码：" + ecode+ "，设备名称" + equipmentExcel.getEname() + "请在设备列表中添加后再导入！");
                insertEquipments.add(EquipmentEntity.build(ecode, equipmentExcel.getEname(), etype, equipmentExcel.getRdDeptName(), date,
                        companyId,userId,msUserId));
            }
            if (existRdEquipmentMap.containsKey(ecode)) {
                RdEquipmentModel model = existRdEquipmentMap.get(ecode);
                model.setRdDeptId(rdDeptId);
                model.setEtype(etype);
                updateList.add(model);
            } else {
                insertRdEquipmentList.add(RdEquipmentEntity.build(companyId, userId, msUserId, ecode, year, date, etype,rdDeptId));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (!CollectionUtils.isEmpty(insertRdEquipmentList)) {
                for (List<RdEquipmentEntity> insertList : ListUtils.subList(insertRdEquipmentList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEquipmentDao.saveRdEquipmentLists(insertList);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (List<RdEquipmentModel> updates : ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEquipmentDao.updateList(updates, date, info.getUserId(), info.getMsUserId());
                }
            }
//            if (!CollectionUtils.isEmpty(updateEquipments)) {
//                for (List<EquipmentEntity> updates : ListUtils.subList(updateEquipments, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
//                    equipmentDao.updateList(updates);
//                }
//            }
            if (!CollectionUtils.isEmpty(insertEquipments)) {
                for (List<EquipmentEntity> inserts : ListUtils.subList(insertEquipments, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    equipmentDao.insertOrUpdate(inserts);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            throw new OwnerException("导入研发设备失败");
        }
        return "";
    }
}
