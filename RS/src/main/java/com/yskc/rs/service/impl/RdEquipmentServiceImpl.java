package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.EquipmentDao;
import com.yskc.rs.dao.RdEquipmentDao;
import com.yskc.rs.dao.init.InitEquipmentDao;
import com.yskc.rs.entity.EquipmentEntity;
import com.yskc.rs.entity.RdEquipmentEntity;
import com.yskc.rs.entity.init.InitEquipmentEntity;
import com.yskc.rs.enums.EduLevelEnum;
import com.yskc.rs.enums.EmployeeTypeEnum;
import com.yskc.rs.enums.EquipmentEnum;
import com.yskc.rs.enums.OrgEnum;
import com.yskc.rs.models.BatchDeleteModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.RdEmployeeModel;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.models.projectequipment.DocEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdequipment.AddRdEquipmentModel;
import com.yskc.rs.models.rdequipment.FullYearProjectModel;
import com.yskc.rs.models.rdequipment.RdEquipmentModel;
import com.yskc.rs.service.RdEquipmentService;
import com.yskc.rs.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private InitEquipmentDao initEquipmentDao;

    @Override
    public PageModel<List<RdEquipmentModel>> getList(UserInfo userInfo, QueryEquipmentModel queryModel) {
        Pagination page = queryModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("re.ecode");
            page.setAscs(ascs);
        }
        List<RdEquipmentModel> result = rdEquipmentDao.getList(page, userInfo.getCompanyId(), queryModel);
        if (!CollectionUtils.isEmpty(result)) {
            List<String> ecodes = result.stream().map(RdEquipmentModel::getEcode).collect(Collectors.toList());
            List<FullYearProjectModel> ecodeRdInfos = rdEquipmentDao.getRdTitles(ecodes, userInfo.getCompanyId(), queryModel.getYear(),true);
            Map<String, FullYearProjectModel> ecodeRdMap = AttDataUtils.getKeyAndRdLackMonthMap(ecodeRdInfos,queryModel.getYear());
            result.forEach(item -> {
                FullYearProjectModel model = ecodeRdMap.get(item.getEcode());
                if (null == model) {
                    item.setLackMonth(Constant.DEFAULT_LACK_MONTH);
                }else{
                    item.setLackMonth(model.getLackMonth());
                    item.setRds(model.getRdTitle());
                }
            });
        }
        return PageUtils.build(page, result);
    }

    @Override
    public PageModel<List<EquipmentModel>> getEquipmentList(UserInfo userInfo, QueryEquipmentModel queryModel) {
        Pagination page = queryModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setAscs(CollUtil.newArrayList("e.ecode"));
        }
        Date firstDay = DateUtil.getYearFirstDay(queryModel.getYear());
        return PageUtils.build(page, rdEquipmentDao.getEquipmentList(page, userInfo.getCompanyId(), queryModel,firstDay));
    }

    @Override
    public Boolean delete(UserInfo info, RdEquipmentEntity rdEquipmentEntity) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), rdEquipmentEntity.getYear(), AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        List<Integer> usedIds = rdEquipmentDao.getUsedIds(info.getCompanyId(), CollUtil.newArrayList(rdEquipmentEntity.getId()), rdEquipmentEntity.getYear());
        if (!CollectionUtils.isEmpty(usedIds)) {
            throw new OwnerException("已存在[加计扣除-项目管理-设备列表]中，不能删除!");
        }
        return rdEquipmentDao.deleteById(rdEquipmentEntity.getId()) > 0;

    }

    @Override
    public Boolean delRdEquipments(UserInfo info, BatchDeleteModel deleteModel) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), deleteModel.getYear(), AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        List<Integer> ids = deleteModel.getIds();
        List<Integer> usedIds = rdEquipmentDao.getUsedIds(info.getCompanyId(), ids, deleteModel.getYear());
        Collection<Integer> result = CollUtil.disjunction(ids, usedIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选研发设备已全部在资产清单中，不能删除!");
        }
        return rdEquipmentDao.deleteBatchIds(result) > 0;
    }

    @Override
    public Boolean addRdEquipments(UserInfo userInfo, AddRdEquipmentModel addRdEquipmentModel) throws OwnerException {
        commonService.checkAuditModify(userInfo.getCompanyId(), addRdEquipmentModel.getYear(), AuditModeEnum.RD_EQUIPMENT, userInfo.getUserSource());
        Date now = new Date();
        Integer companyId = userInfo.getCompanyId();
        Set<String> ecodes = new HashSet<>(addRdEquipmentModel.getEcodes());
        List<RdEquipmentModel> existList = rdEquipmentDao.getByEcodes(companyId, ecodes, addRdEquipmentModel.getYear());
        Map<String, Boolean> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existList)) {
            existList.forEach(item -> existMap.put(item.getEcode(), true));
        }
        List<EquipmentEntity> equipments = equipmentDao.getEquipmentByEcodes(companyId, ecodes);
        Map<String, Integer> ecodeEtypeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(equipments)) {
            equipments.forEach(item -> ecodeEtypeMap.put(item.getEcode(), item.getEtype()));
        }
        List<RdEquipmentEntity> rdEquipmentList = new ArrayList<>();
        Integer userId = userInfo.getUserId();
        Integer msUserId = userInfo.getMsUserId();
        addRdEquipmentModel.getEcodes().forEach(ecode -> {
            if (existMap.containsKey(ecode)) {
                return;
            }
            Integer etype = ecodeEtypeMap.get(ecode);
            if (null == etype) {
                return;
            }
            rdEquipmentList.add(RdEquipmentEntity.build(companyId, userId, msUserId, ecode, addRdEquipmentModel.getYear(), now, etype,-1));
        });
        if (CollectionUtils.isEmpty(rdEquipmentList)) {
            return true;
        }
        return rdEquipmentDao.addBatch(rdEquipmentList) > 0;
    }

    @Override
    public Boolean updateEtype(UserInfo info, EquipmentModifyModel modifyModel) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), modifyModel.getYear(), AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        return rdEquipmentDao.updateEtype(info.getCompanyId(), info.getUserId(), info.getMsUserId(), new Date(), modifyModel) > 0;
    }

    @Override
    public Boolean setRdDept(EquipmentModifyModel modifyModel, UserInfo info) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), modifyModel.getYear(), AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        return rdEquipmentDao.updateRdDept(modifyModel, info.getUserId(), info.getMsUserId(), new Date()) > 0;
    }


    @Override
    public List<EquipmentExcel> deriveRdEquipment(Integer companyId, QueryEquipmentModel query) {
        List<EquipmentExcel> result = rdEquipmentDao.deriveRdEquipment(companyId, query);
        if (!CollectionUtils.isEmpty(result)) {
            List<String> ecodes = result.stream().map(EquipmentExcel::getEcode).collect(Collectors.toList());
            List<FullYearProjectModel> ecodeRdInfos = rdEquipmentDao.getRdTitles(ecodes, companyId, query.getYear(),true);
            Map<String, FullYearProjectModel> ecodeRdMap = AttDataUtils.getKeyAndRdLackMonthMap(ecodeRdInfos,query.getYear());
            result.forEach(item -> {
                FullYearProjectModel model = ecodeRdMap.get(item.getEcode());
                if (null == model) {
                    item.setLackMonth(Constant.DEFAULT_LACK_MONTH);
                }else{
                    item.setLackMonth(model.getLackMonth());
                    item.setRds(model.getRdTitle());
                }
            });
        }
        return result;
    }

    @Override
    public void exportRdEquipment(Integer companyId, QueryEquipmentModel query, OutputStream out, String path) throws OwnerException {
        List<EquipmentExcel> result = rdEquipmentDao.deriveRdEquipment(companyId, query);
        Map<String, Object> dataMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(result)) {
            List<String> ecodes = result.stream().map(EquipmentExcel::getEcode).collect(Collectors.toList());
            List<FullYearProjectModel> ecodeRdInfos = rdEquipmentDao.getRdTitles(ecodes, companyId, query.getYear(),true);
            Map<String, FullYearProjectModel> ecodeRdMap = AttDataUtils.getKeyAndRdLackMonthMap(ecodeRdInfos,query.getYear());
            result.forEach(item -> {
                if (item.getFullname()==null){
                    String fullName;
                    List<String> fullNames = new ArrayList<>();
                    fullNames.add(item.getDeptName());
                    fullNames.add(item.getWorkshop());
                    fullNames.add(item.getProductLine());
                    fullNames.add(item.getProcessSection());
                    fullNames.removeAll(Collections.singleton(null));
                    fullNames.removeAll(Collections.singleton(""));
                    fullName = String.join("/", fullNames);
                    item.setFullname(fullName);
                }
                FullYearProjectModel model = ecodeRdMap.get(item.getEcode());
                if (null == model) {
                    item.setLackMonth(Constant.DEFAULT_LACK_MONTH);
                }else{
                    item.setLackMonth(model.getLackMonth());
                    item.setRds(model.getRdTitle());
                }
            });
        }
        dataMap.put("result", result);
        YsExcelUtils.generalReport(dataMap, path, (workbook) -> {
            if (workbook != null) {
                try {
                    workbook.write(out);
                    workbook.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }


    @Override
    public PageModel<List<DocEquipmentModel>> getDocEquipmentList(UserInfo userInfo, QueryProjectEquipmentModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, rdEquipmentDao.getDocEquipmentList(page, query, userInfo.getCompanyId()));

    }

    @Override
    public String importRdEquipment(UserInfo info, List<EquipmentExcel> equipmentExcelList, Integer year) throws OwnerException {
        Integer companyId = info.getCompanyId();
        commonService.checkAuditModify(companyId, year, AuditModeEnum.RD_EQUIPMENT, info.getUserSource());
        if (CollectionUtils.isEmpty(equipmentExcelList)) {
            throw new OwnerException("导入数据为空,请添加后再导入!");
        }
        Map<String, Integer> rdDeptMap = commonService.initOrgFullPathMap(companyId, OrgEnum.RD_DEPT, year);
        Set<String> ecodeSet = equipmentExcelList.stream().filter(a -> !StringUtils.isEmpty(a.getEcode())).map(EquipmentExcel::getEcode).collect(Collectors.toSet());
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
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
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("导入研发设备失败");
        }
        return "";
    }

    @Override
    public Boolean setProjectEquipment(UserInfo userInfo, KeysAndIdsModel keysAndIds) throws OwnerException {
        Integer companyId = userInfo.getCompanyId();
        Integer year = keysAndIds.getYear();
        commonService.checkAuditModify(companyId, keysAndIds.getYear(), AuditModeEnum.RD_EMPLOYEE, userInfo.getUserSource());
        Date now = new Date();
        List<InitEquipmentEntity> exist = initEquipmentDao.getExist(companyId, keysAndIds, year);
        String keyFormat = "{0}_{1}";
        Map<String, Boolean> existMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(exist)) {
            exist.forEach(item -> existMap.put(MessageFormat.format(keyFormat, item.getProjectId(), item.getEcode()), true));
        }
        List<InitEquipmentEntity> insertList = new ArrayList<>();
        keysAndIds.getIds().forEach(projectId -> {
            keysAndIds.getKeys().forEach(ecode -> {
                if (existMap.containsKey(MessageFormat.format(keyFormat, projectId, ecode))) {
                    return;
                }
                insertList.add(InitEquipmentEntity.build(userInfo, ecode, null, projectId, now, null, year));
            });
        });
        if (!CollectionUtils.isEmpty(insertList)) {
            TransactionStatus transactionStatus = null;
            try {
                transactionStatus = TransactionUtils.newTransaction();
                for (List<InitEquipmentEntity> list : ListUtils.subList(insertList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initEquipmentDao.addBatch(list);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException("保存失败");
            }
        }
        return true;
    }
}
