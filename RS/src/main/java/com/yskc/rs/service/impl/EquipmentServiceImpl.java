package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.EquipmentDao;
import com.yskc.rs.entity.EquipmentEntity;
import com.yskc.rs.enums.EquipmentEnum;
import com.yskc.rs.enums.TableEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.equipment.EquipmentModel;
import com.yskc.rs.models.equipment.EquipmentModifyModel;
import com.yskc.rs.models.equipment.QueryEquipmentModel;
import com.yskc.rs.models.excel.EquipmentExcel;
import com.yskc.rs.service.EquipmentService;
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
import java.text.MessageFormat;
import java.util.*;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-09 09:22
 * @Description: 设备业务层实现
 */
@Service
public class EquipmentServiceImpl implements EquipmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EquipmentDao equipmentDao;

    /**
     * @param companyId
     * @param query
     * @return
     */
    @Override
    public PageModel<List<EquipmentModel>> queryAll(Integer companyId, QueryEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.ecode");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, equipmentDao.queryAll(page, companyId, query));
    }

    /**
     * @param companyId
     * @param ecode
     * @param id
     * @return
     */
    @Override
    public Boolean checkEcode(Integer companyId, String ecode, Integer id) {
        EquipmentEntity equipment = equipmentDao.getEquipmentByEcode(companyId, ecode);
        if (equipment == null) {
            return true;
        }
        return equipment.getId().equals(id);
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean addEquipment(UserInfo info, EquipmentModel model) throws OwnerException {
        EquipmentEntity entity = new EquipmentEntity();
        Date now = new Date();
        entity.setCompanyId(info.getCompanyId());
        ///添加字段 start
        entity.setMsCreatorId(info.getMsUserId());
        entity.setMsLastUpdatorId(info.getMsUserId());
        entity.setCreatorId(info.getUserId());
        entity.setLastUpdatorId(info.getUserId());
        entity.setCreateTime(now);
        entity.setLastUpdateTime(now);
        ///添加字段 end
        entity.setPurchaseDate(now);
        return equipmentDao.insert(getEquipmentEntity(entity, model, BigDecimal.ZERO)) > 0;
    }

    /**
     * @param currentUserId
     * @param info
     * @param model
     * @return
     */
    @Override
    public Boolean updateEquipment(Integer currentUserId, UserInfo info, EquipmentModel model) {
        EquipmentEntity entity = equipmentDao.selectById(model.getId());
        ///添加字段 start
        entity.setMsLastUpdatorId(info.getMsUserId());
        entity.setLastUpdatorId(info.getUserId());
        entity.setLastUpdateTime(new Date());
        ///添加字段 end
        return equipmentDao.updateEquipmentByEntity(getEquipmentEntity(entity, model, BigDecimal.ZERO));
    }

    /**
     * 根据model获取
     *
     * @param entity
     * @param model
     * @return
     */
    private EquipmentEntity getEquipmentEntity(EquipmentEntity entity, EquipmentModel model, BigDecimal zero) {
        entity.setEcode(model.getEcode());
        entity.setDepreciationDate(model.getDepreciationDate());
        entity.setEname(model.getEname());
        entity.setDepreciationRate(model.getDepreciationRate() == null ? zero : model.getDepreciationRate());
        entity.setUsagePower(model.getUsagePower() == null ? BigDecimal.ZERO : model.getUsagePower());
        entity.setPurchaseDate(model.getPurchaseDate());
        entity.setEmodal(model.getEmodal() == null ? "" : model.getEmodal());
        entity.setQuantity(model.getQuantity() == null || model.getQuantity().compareTo(BigDecimal.ZERO) <= 0 ? BigDecimal.ONE : model.getQuantity());
        entity.setRemark(model.getRemark());
        entity.setUnit(model.getUnit() == null ? "" : model.getUnit());
        entity.setUnitPrice(model.getUnitPrice());
        entity.setUsefullife(model.getUsefullife() == null || model.getUsefullife() <= 0 ? 10 : model.getUsefullife());
        entity.setEtype(model.getEtype() == null ? EquipmentEnum.PROD.getType() : model.getEtype());
        entity.setKinds(model.getKinds());
        entity.setDeptName(model.getDeptName());
        entity.setDeptId(model.getDeptId());
        entity.setWorkshop(model.getWorkshop());
        entity.setProductLine(model.getProductLine());
        entity.setProcessSection(model.getProcessSection());
        entity.setData(model.getData());
        entity.setInvalidated(model.getInvalidated());
        return entity;
    }

    /**
     * @param info
     * @param equipmentExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public String importEquipment(UserInfo info, List<EquipmentExcel> equipmentExcels) throws OwnerException {
        if (CollectionUtils.isEmpty(equipmentExcels)) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        EquipmentExcel equipmentExcel;
        Date now = new Date();
        StringBuffer errorRow = new StringBuffer();
        List<EquipmentEntity> equipmentEntities = new ArrayList<>();
        for (int i = 0; i < equipmentExcels.size(); i++) {
            equipmentExcel = equipmentExcels.get(i);
            boolean checkRate = null != equipmentExcel.getDepreciationRate() && (equipmentExcel.getDepreciationRate().doubleValue() > 1 || equipmentExcel.getDepreciationRate().doubleValue() < 0);
            if (checkRate) {
                errorRow.append("第" + (i + 2) + "行 【月折旧率不能大于1且不能小于0】,");
                continue;
            }
            if (equipmentExcel.getPurchaseDate() != null && equipmentExcel.getDepreciationDate() != null && equipmentExcel.getPurchaseDate().getTime() > equipmentExcel.getDepreciationDate().getTime()) {
                errorRow.append("第" + (i + 2) + "行 【折旧日期不能小于开始计提日期】,");
                continue;
            }
            equipmentEntities.add(EquipmentEntity.build(equipmentExcel, BigDecimal.ZERO, now, info, 0));
        }

        // 分批次插入 // 分批次插入
        List<List<EquipmentEntity>> equipments = ListUtils.subList(equipmentEntities, Constant.MAX_INSERT_OR_UPDATE_COUNT);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            for (List<EquipmentEntity> equipmentList : equipments) {
                equipmentDao.insertOrUpdate(equipmentList);
            }
            logger.info("设备导入失败行：" + errorRow.toString());
            TransactionUtils.commit(transactionStatus);
            if (errorRow.length() > 0) {
                errorRow.delete(errorRow.length() - 1, errorRow.length());
            }
            return errorRow.toString();
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            String error = e.getMessage();
            logger.error(error, e);
            throw new OwnerException(
                    MessageFormat.format("导入数据失败{0}",
                            error.contains("Data too long") ? "，部分列字数过长，请检查" : "")
            );
        }
    }

    /**
     * @param companyId
     * @param query
     * @return
     */
    @Override
    public List<EquipmentExcel> exportEquipment(Integer companyId, QueryEquipmentModel query) {
        List<EquipmentExcel> equipmentExcels = equipmentDao.queryForExport(companyId, query);
        for (EquipmentExcel excel : equipmentExcels) {
            if (excel.getFullname()==null){
                String fullName;
                List<String> fullNames = new ArrayList<>();
                fullNames.add(excel.getDeptName());
                fullNames.add(excel.getWorkshop());
                fullNames.add(excel.getProductLine());
                fullNames.add(excel.getProcessSection());
                fullNames.removeAll(Collections.singleton(null));
                fullNames.removeAll(Collections.singleton(""));
                fullName = String.join("/", fullNames);
                excel.setFullname(fullName);
            }
        }
        return equipmentExcels;
    }

    /**
     * @param companyId
     * @param id
     * @return
     * @throws OwnerException
     */
    @Override
    public Boolean deleteById(Integer companyId, Integer id) throws OwnerException {
        List<Integer> ids = CollUtil.newArrayList(id);
        List<Integer> usedIds = equipmentDao.getUsed(TableEnum.D_EQUIPMENT.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发费用核算-数据录入-设备使用】中，不能删除!");
        }

        usedIds = equipmentDao.getUsed(TableEnum.RD_EQUIPMENT.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发技术中心建设-研发设备】中，不能删除!");
        }

        usedIds = equipmentDao.getUsed(TableEnum.I_EQUIPMENT.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发项目管理-项目管理-设备清单】中，不能删除!");
        }
        return equipmentDao.deleteById(id) > 0;
    }

    /**
     * @param info
     * @param model
     * @return
     */
    @Override
    public boolean updateEquipmentEtype(UserInfo info, EquipmentModifyModel model) {
        return equipmentDao.updateEquipmentEtype(model.getEtype(), info.getUserId(), info.getMsUserId(), new Date(), model.getIds()) > 0;
    }

    /**
     * @param companyId
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delEquipments(Integer companyId, EquipmentModifyModel model) throws OwnerException {
        List<Integer> ids = model.getIds();
        Set<Integer> usedIdSet = new HashSet<>();
        usedIdSet.addAll(equipmentDao.getUsed(TableEnum.D_EQUIPMENT.getName(), companyId, ids));
        usedIdSet.addAll(equipmentDao.getUsed(TableEnum.I_EQUIPMENT.getName(), companyId, ids));
        usedIdSet.addAll(equipmentDao.getUsed(TableEnum.RD_EQUIPMENT.getName(), companyId, ids));
        Collection<Integer> result = CollUtil.disjunction(ids, usedIdSet);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选设备已全部在【创新项目】中使用，不能删除!");
        }
        return equipmentDao.deleteBatchIds(result) > 0;
    }
}
