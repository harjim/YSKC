package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.ProjectDao;
import com.yskc.rs.dao.RdEquipmentDao;
import com.yskc.rs.dao.init.InitEquipmentDao;
import com.yskc.rs.dao.project.ProjectRdEquipmentDao;
import com.yskc.rs.entity.init.InitEquipmentEntity;
import com.yskc.rs.entity.project.ProjectEntity;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InitEquipmentExcel;
import com.yskc.rs.models.init.ImportProjectInfoModel;
import com.yskc.rs.models.init.InitRdUsedModel;
import com.yskc.rs.models.init.InitUsedRoleModel;
import com.yskc.rs.models.init.equipment.BatchInitEquipmentModel;
import com.yskc.rs.models.init.equipment.InitEquipmentModel;
import com.yskc.rs.models.projectequipment.QueryProjectEquipmentModel;
import com.yskc.rs.models.rdequipment.RdEquipmentModel;
import com.yskc.rs.service.InitEquipmentService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-23 17:20
 * @Description: 初始化项目设备列表业务实现层
 */
@Service
public class InitEquipmentServiceImpl implements InitEquipmentService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InitEquipmentDao initEquipmentDao;
    @Autowired
    private RdEquipmentDao rdEquipmentDao;
    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private ProjectRdEquipmentDao projectRdEquipmentDao;

    @Override
    public PageModel<List<InitEquipmentModel>> getList(Integer companyId, QueryProjectEquipmentModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("ie.ecode");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, initEquipmentDao.getList(page, companyId, query));
    }

    @Override
    public PageModel<List<InitEquipmentModel>> getEquipmentList(Integer companyId, QueryProjectEquipmentModel model) {
        Pagination page = model.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.ecode");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, initEquipmentDao.getEquipmentList(page, companyId, model));
    }

    @Override
    public boolean addList(UserInfo userInfo, BatchInitEquipmentModel model) throws OwnerException {
        List<String> exsitEcodes = initEquipmentDao.getByEcodes(userInfo.getCompanyId(), model.getProjectId(), new HashSet<>(model.getEcodes()), model.getYear());
        List<String> ecodes = (ArrayList<String>) CollUtil.disjunction(model.getEcodes(), exsitEcodes);
        List<InitEquipmentEntity> initEquipmentEntities = new ArrayList<>();
        Date now = new Date();
        String blank = "";
        ecodes.forEach((ecode) -> {
            initEquipmentEntities.add(InitEquipmentEntity.build(userInfo, ecode, blank, model.getProjectId(), now, null, model.getYear()));
        });
        if (CollectionUtils.isEmpty(initEquipmentEntities)) {
            return true;
        }
        return initEquipmentDao.addBatch(initEquipmentEntities) > 0;
    }

    @Override
    public boolean del(InitEquipmentModel model, UserInfo info) throws OwnerException {
        List<InitRdUsedModel> rdUsed = getRdUsed(CollUtil.newArrayList(model.getId()), model.getProjectId(), null, model.getYear());
        if (!CollectionUtils.isEmpty(rdUsed)) {
            throw new OwnerException("删除失败，当前设备已存在归集数据。");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            initEquipmentDao.deleteById(model.getId());
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(),e);
            throw new OwnerException("删除失败");
        }
    }

    @Override
    public boolean updateEffect(InitUsedRoleModel model) {
        if (CollectionUtils.isEmpty(model.getIds())) {
            return true;
        }
        return initEquipmentDao.updateEffect(model.getRole(), model.getIds()) > 0;
    }

    @Override
    public boolean importInitEquipment(UserInfo userInfo, List<InitEquipmentExcel> data, int year, Integer projectId) throws OwnerException {
        if (CollectionUtils.isEmpty(data)) {
            throw new OwnerException("未获取到任何项目设备，请检查是否录入了数据。");
        }
        ProjectEntity projectEntity = projectDao.selectById(projectId);
        if (null == projectEntity) {
            throw new OwnerException("当前所选项目不存在。请稍后重试！");
        }
        Map<String, InitEquipmentExcel> dataMap = new LinkedHashMap<>();
        data.forEach(item -> {
            if (dataMap.containsKey(item.getEcode())) {
                return;
            }
            dataMap.put(item.getEcode(), item);
        });
        data = new ArrayList<>(dataMap.size());
        data.addAll(dataMap.values());
        Set<String> ecodeSet = dataMap.keySet();
        List<RdEquipmentModel> rdEquipmentModels = rdEquipmentDao.getByEcodes(userInfo.getCompanyId(), ecodeSet, year);
        List<InitEquipmentEntity> initExistList = initEquipmentDao.getExistList(userInfo.getCompanyId(), projectId, ecodeSet, year);
        Map<String, Integer> ecodeTypeMap = new HashMap<>();
        Map<String, Boolean> rdExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(rdEquipmentModels)) {
            rdEquipmentModels.forEach(item -> {
                ecodeTypeMap.put(item.getEcode(), item.getEtype());
                rdExistMap.put(item.getEcode(), true);
            });
        }
        Map<String, Integer> initExistMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(initExistList)) {
            initExistList.forEach(item -> initExistMap.put(item.getEcode(), item.getId()));
        }
        Date now = new Date();
        List<InitEquipmentEntity> initList = new ArrayList<>();
        List<InitEquipmentEntity> updateList = new ArrayList<>();
        long projectBegin = projectEntity.getBeginDate().getTime();
        long projectEnd = projectEntity.getEndDate().getTime();
        for (int i = 0; i < data.size(); i++) {
            InitEquipmentExcel excel = data.get(i);
            if (!rdExistMap.containsKey(excel.getEcode())) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，资产代码【{0}】，设备名称【{1}】不存在【{2,number,#}】年研发设备列表中，请先在研发设备中添加。"
                        , excel.getEcode(), excel.getEname(), year, i + 2
                ));
            }
            Integer etype = ecodeTypeMap.get(excel.getEcode());
            if (null == etype || etype <= 0) {
                throw new OwnerException(MessageFormat.format("第{3,number,#}行，资产代码【{0}】，设备名称【{1}】类型必须是设备或者仪器，请先在设备列表中设置。"
                        , excel.getEcode(), excel.getEname(), year, i + 2
                ));
            }
            if (null != excel.getEntryDate()) {
                if (excel.getEntryDate().getTime() < projectBegin || excel.getEntryDate().getTime() > projectEnd) {
                    String error = MessageFormat.format("第{0,number,#}行，进入时间【{1}】，必须在项目起止日期【{2}至{3}】以内。",
                            i + 2,
                            DateUtil.format(excel.getEntryDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getBeginDate(), DateUtil.DEFAULT_DATE_FORMAT),
                            DateUtil.format(projectEntity.getEndDate(), DateUtil.DEFAULT_DATE_FORMAT)
                    );
                    throw new OwnerException(error);
                }
            }
            String ecode = excel.getEcode();
            if (!initExistMap.containsKey(ecode)) {
                initList.add(InitEquipmentEntity.build(userInfo, ecode, excel.getEffect(), projectId, now, excel.getEntryDate(), year));
            } else {
                InitEquipmentEntity equipmentEntity = InitEquipmentEntity.buildUpdate(userInfo, excel.getEffect(), now, excel.getEntryDate());
                equipmentEntity.setId(initExistMap.get(ecode));
                updateList.add(equipmentEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (!CollectionUtils.isEmpty(initList)) {
                List<List<InitEquipmentEntity>> insertInitList = ListUtils.subList(initList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InitEquipmentEntity> items : insertInitList) {
                    initEquipmentDao.addBatch(items);
                }
            }
            if (!CollectionUtils.isEmpty(updateList)) {
                for (List<InitEquipmentEntity> updates : ListUtils.subList(updateList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    initEquipmentDao.updateBatch(updates);
                }
            }
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("导入设备清单失败", e);
            throw new OwnerException("保存数据失败");
        }
    }

    @Override
    public boolean delInitEquipments(InitUsedRoleModel model, UserInfo info) throws OwnerException {
        TransactionStatus transactionStatus = null;
        List<Integer> ids = getModifyIds(model.getIds(), model.getProjectId(), null, model.getYear());
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("所选设备已全部存在归集数据，不能删除!");
        }
        try {
            transactionStatus = TransactionUtils.newTransaction();
            initEquipmentDao.deleteBatchIds(ids);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("删除资产清单失败");
        }

    }

    @Override
    public boolean setEntryDate(UserInfo userInfo, InitUsedRoleModel model) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            Integer msUserId = userInfo.getMsUserId();
            Integer userId = userInfo.getUserId();
            Date now = new Date(),entryDate= cn.hutool.core.date.DateUtil.beginOfDay(model.getEntryDate());
            List<Integer> ids = getModifyIds(model.getIds(), model.getProjectId(), entryDate, model.getYear());
            if (CollectionUtils.isEmpty(ids)) {
                throw new OwnerException("设置加入时间失败，所选设备已全部存在归集数据!");
            }
            initEquipmentDao.updateEntryDate(now, userId, msUserId, ids, entryDate);
            TransactionUtils.commit(transactionStatus);
            return true;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            throw new OwnerException("设置加入时间失败。");
        }

    }

    @Override
    public String checkRdUsed(InitUsedRoleModel model) {
        List<InitRdUsedModel> models = getRdUsed(model.getIds(), model.getProjectId(), model.getEntryDate(), model.getYear());
        StringBuilder builder = new StringBuilder();
        if (!CollectionUtils.isEmpty(models)) {
            Map<String, Boolean> keyMap = new HashMap<>();
            for (InitRdUsedModel m : models) {
                if (!keyMap.containsKey(m.getKey())) {
                    builder.append(m.getName()).append("（").append(m.getKey()).append("）,");
                    keyMap.put(m.getKey(), true);
                }
            }
            builder.append("已被归集费用。继续操作将跳过这些数据。");
        }
        return builder.toString();
    }

    @Override
    public boolean importEquipments(UserInfo userInfo, ImportProjectInfoModel model) throws OwnerException {
        if (model == null) {
            return true;
        }
        if (model.getSourceYear() == model.getTargetYear()) {
            throw new OwnerException("不能引入相同年份的数据！");
        }
        ProjectEntity project = projectDao.selectById(model.getProjectId());
        if (model.getSourceYear() < project.getBeginYear() || model.getSourceYear() > project.getEndYear()) {
            throw new OwnerException("选择引入数据的年份应处于项目周期内！");
        }
        List<InitEquipmentEntity> addEquipments = initEquipmentDao.getAddEquipments(model, userInfo.getCompanyId());
        if (CollectionUtils.isEmpty(addEquipments)) {
            throw new OwnerException("没有可引入的资产清单！");
        }
        List<InitEquipmentEntity> existEquipmets = initEquipmentDao.getEuipmentByYear(model.getProjectId(), model.getTargetYear());
        Map<String, InitEquipmentEntity> equipmentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existEquipmets)) {
            equipmentMap = existEquipmets.stream().collect(Collectors.toMap(e -> e.getEcode(), e -> e));
        }
        List<InitEquipmentEntity> insertList = new ArrayList<>();
        for (InitEquipmentEntity entity : addEquipments) {
            if (equipmentMap.containsKey(entity.getEcode())) {
                continue;
            }
            insertList.add(InitEquipmentEntity.build(userInfo, entity.getEcode(), entity.getEffect(), model.getProjectId(), new Date(), entity.getEntryDate(), model.getTargetYear()));
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            initEquipmentDao.addBatch(insertList);
        }
        return true;
    }

    @Override
    public List<Integer> getEquipmentYear(Integer year, Integer projectId) {
        ProjectEntity project=projectDao.selectById(projectId);
        if(project.getEndYear().equals(project.getBeginYear())){
            return new ArrayList<>();
        }
        return initEquipmentDao.getYears(projectId,year);
    }

    @Override
    public List<InitEquipmentModel> getEquList(Integer companyId, QueryProjectEquipmentModel model) {
        List<InitEquipmentModel> equList = initEquipmentDao.getEquList(companyId, model.getYear(), model.getProjectId());
        equList.forEach(item->{
            item.setTotalPrice(item.getUnitPrice().multiply(item.getQuantity()));
        });
        return equList;
    }

    private List<InitRdUsedModel> getRdUsed(List<Integer> ids, Integer projectId, Date entryDate, Integer year) {
        Date begin = DateUtil.getYearFirstDay(year);
        return projectRdEquipmentDao.getRdUsed(ids, projectId, entryDate != null ? DateUtil.getMonthFirstDay(entryDate) : DateUtil.getYearLastDay(year), year,begin);
    }

    private List<Integer> getModifyIds(List<Integer> dataIds, Integer projectId, Date entryDate, Integer year) {
        List<InitRdUsedModel> rdUsed = getRdUsed(dataIds, projectId, entryDate, year);
        List<Integer> ids;
        if (!CollectionUtils.isEmpty(rdUsed)) {
            Map<String, Boolean> usedMap = new HashMap<>();
            rdUsed.forEach(item -> usedMap.put(item.getKey(), Boolean.TRUE));
            List<InitEquipmentEntity> initEquipment = initEquipmentDao.getEcodesByIds(dataIds);
            List<Integer> finalIds = new ArrayList<>();
            initEquipment.forEach(item -> {
                if (!usedMap.containsKey(item.getEcode())) {
                    finalIds.add(item.getId());
                }
            });
            ids = finalIds;
        } else {
            ids = dataIds;
        }
        return ids;
    }

}
