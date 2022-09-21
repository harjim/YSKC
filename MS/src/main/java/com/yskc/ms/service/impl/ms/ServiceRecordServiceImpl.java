package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.model.PageResult;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.FlowInstanceFormEntity;
import com.yskc.ms.entity.ms.WorkItemEntity;
import com.yskc.ms.entity.ms.WorkRecordEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.customer.CustomerOwnerModel;
import com.yskc.ms.models.serviceApply.QueryMobileApply;
import com.yskc.ms.models.serviceApply.ServiceNoModel;
import com.yskc.ms.models.serviceRecord.QueryRecordModel;
import com.yskc.ms.models.serviceRecord.WorkItemModel;
import com.yskc.ms.models.serviceRecord.WorkRecordModel;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.ms.ServiceRecordService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
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
 * @program: ms
 * @description: 新服务记录service层
 * @author: cyj
 * @create: 2022-08-13 10:00
 **/
@Service
public class ServiceRecordServiceImpl implements ServiceRecordService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Integer moduleId = AuditModeEnum.WORK_RECORD.getModuleId();
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private WorkRecordDao workRecordDao;
    @Autowired
    private WorkItemDao workItemDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private ServiceMemberDao serviceMemberDao;
    @Autowired
    private ServiceCustomerDao serviceCustomerDao;
    @Autowired
    private ServiceApplyDao serviceApplyDao;
    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private FlowCurNodeDao flowCurNodeDao;
    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private FlowInstanceService flowInstanceService;


    @Override
    public PageResult getList(QueryRecordModel query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("wr.lastUpdateTime");
        }
        List<WorkRecordModel> list = workRecordDao.getList(page, query, moduleId,dataPerm);
        String format = "yyyy-MM-dd HH:mm";

        if (!CollectionUtils.isEmpty(list)) {
            Map<Integer, WorkRecordModel> map = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(map.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)) {
                hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a,(o,n)->n));
            }
            List<WorkItemModel> itemList = workItemDao.getItemList(map.keySet());
            Map<Integer, List<WorkItemModel>> itemMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(itemList)) {
                itemMap = itemList.stream().collect(Collectors.groupingBy(a -> {
                    a.setDate(DateUtil.format(a.getBegin(), format) + "-" + DateUtil.format(a.getEnd(), format));
                    return a.getWorkRecordId();
                }));
            }
            for (Integer id : map.keySet()) {
                WorkRecordModel model = map.get(id);
                if (!CollectionUtils.isEmpty(itemMap)) {
                    map.get(id).setList(itemMap.get(id));
                }
                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap) && hasPermMap.containsKey(id));
            }
        }

        return PageUtils.buildPageResult(page, list, workRecordDao.getTotal(query,moduleId,dataPerm));
    }

    @Override
    public PageResult getMobileList(QueryMobileApply query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("wr.lastUpdateTime");
        }
        List<WorkRecordModel> list = workRecordDao.getMobileList(page,query, moduleId,dataPerm);
        String format = "yyyy-MM-dd HH:mm";

        if (!CollectionUtils.isEmpty(list)) {
            Map<Integer, WorkRecordModel> map = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(map.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)) {
                hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a,(o,n)->n));
            }
            List<WorkItemModel> itemList = workItemDao.getItemList(map.keySet());
            Map<Integer, List<WorkItemModel>> itemMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(itemList)) {
                itemMap = itemList.stream().collect(Collectors.groupingBy(a -> {
                    a.setDate(DateUtil.format(a.getBegin(), format) + "-" + DateUtil.format(a.getEnd(), format));
                    return a.getWorkRecordId();
                }));
            }
            for (Integer id : map.keySet()) {
                WorkRecordModel model = map.get(id);
                if (!CollectionUtils.isEmpty(itemMap)) {
                    map.get(id).setList(itemMap.get(id));
                }
                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap) && hasPermMap.containsKey(id));
            }
        }
        QueryRecordModel queryRecordModel = new QueryRecordModel();
        queryRecordModel.setCompanyName(query.getCompanyName());
        return PageUtils.buildPageResult(page, list, workRecordDao.getTotal(queryRecordModel,moduleId,dataPerm));
    }

    @Override
    public WorkRecordModel getRecordInfo(Integer recordId, UserInfo info) {
        String format = "yyyy-MM-dd HH:mm";
        WorkRecordModel recordModel = workRecordDao.getRecordInfo(recordId);
        Set<Integer> ids = new HashSet<>();
        ids.add(recordId);
        List<WorkItemModel> itemList = workItemDao.getItemList(ids);
        List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(ids, moduleId, info.getId());
        recordModel.setHasPermission(!CollectionUtils.isEmpty(hasPermission));
        if (!CollectionUtils.isEmpty(itemList)) {
            for (WorkItemModel model : itemList) {
                model.setDate(DateUtil.format(model.getBegin(), format) + "-" + DateUtil.format(model.getEnd(), format));
            }
            recordModel.setList(itemList);
        }

        return recordModel;
    }

    @Override
    public List<CustomerOwnerModel> getCustomerList(String companyName) {
        if (StringUtils.isEmpty(companyName)) {
            return new ArrayList<>();
        }
        return customerDao.getOwnerDept(companyName);
    }

    @Override
    public PageModel<List<ServiceNoModel>> getServiceNo(QueryRecordModel query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("sa.createTime");
        }
        String format = "yyyy-MM-dd";
        List<ServiceNoModel> serviceNo = serviceCustomerDao.getServiceNo(page, query.getCustomerId(), FlowInstanceStatusEnum.DONE.getStatus());
        for (ServiceNoModel model : serviceNo) {
            model.setDate(DateUtil.format(model.getBegin(), format) + "-" + DateUtil.format(model.getEnd(), format));
        }
        return PageUtils.buildPageResult(page, serviceNo);
    }

    private void addRecordDetail(WorkRecordModel model, UserInfo info) throws OwnerException {
        Date now = new Date();
        List<WorkItemModel> list = model.getList();
        WorkRecordEntity entity = new WorkRecordEntity();
        entity.setServiceNo(model.getServiceNo());
        entity.setCustomerId(model.getCustomerId());
        entity.setOwnerId(model.getOwnerId());
        entity.setItemCnt(list.size());
        entity.setDeptId(model.getDeptId());
        ToolUtil.entityCreate(entity, info.getId(), now);

        List<WorkItemEntity> entities = new ArrayList<>();
        workRecordDao.insert(entity);
        Integer recordId = entity.getId();
        model.setId(recordId);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (WorkItemModel itemModel : list) {
            if (itemModel.getEnd().before(itemModel.getBegin())){
                throw new OwnerException("起止时间错误，请重新输入！");
            }
            WorkItemEntity itemEntity = new WorkItemEntity();
            itemEntity.setAmount(itemModel.getAmount());
            itemEntity.setRemark(itemModel.getRemark());
            itemEntity.setWorkRecordId(recordId);
            itemEntity.setItemType(itemModel.getItemType());
            itemEntity.setBegin(itemModel.getBegin());
            itemEntity.setEnd(itemModel.getEnd());
            totalAmount = totalAmount.add(itemModel.getAmount());
            ToolUtil.entityCreate(itemEntity, info.getId(), now);
            entities.add(itemEntity);
        }
        entity.setTotalAmount(totalAmount);
        if (!CollectionUtils.isEmpty(entities)) {
            workItemDao.addItem(entities);
            workRecordDao.updateById(entity);
        }
    }

    @Override
    public Boolean addRecord(WorkRecordModel model, UserInfo info) throws OwnerException {
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            addRecordDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("添加失败！");
        }

        return true;
    }

    private void editRecordDetail(WorkRecordModel model, UserInfo info) throws OwnerException {
        Date now = new Date();
        List<WorkItemModel> list = model.getList();
        WorkRecordEntity entity = new WorkRecordEntity();
        entity.setServiceNo(model.getServiceNo());
        entity.setCustomerId(model.getCustomerId());
        entity.setOwnerId(model.getOwnerId());
        entity.setItemCnt(list.size());
        entity.setId(model.getId());
        entity.setDeptId(model.getDeptId());
        ToolUtil.entityUpdate(entity, info.getId(), now);

        List<WorkItemEntity> entities = new ArrayList<>();
        List<WorkItemEntity> updateList = new ArrayList<>();
        List<Integer> delIds = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (WorkItemModel itemModel : list) {
            if (itemModel.getEnd().before(itemModel.getBegin())){
                throw new OwnerException("起止时间错误，请重新输入！");
            }
            WorkItemEntity itemEntity = new WorkItemEntity();
            itemEntity.setAmount(itemModel.getAmount());
            itemEntity.setRemark(itemModel.getRemark());
            itemEntity.setWorkRecordId(entity.getId());
            itemEntity.setItemType(itemModel.getItemType());
            itemEntity.setBegin(itemModel.getBegin());
            itemEntity.setEnd(itemModel.getEnd());
            ToolUtil.entityCreate(itemEntity, info.getId(), now);
            if (itemModel.getId() == null || itemModel.getId() == 0) {
                entities.add(itemEntity);
            } else {
                itemEntity.setId(itemModel.getId());
                updateList.add(itemEntity);
                delIds.add(itemModel.getId());
            }
            totalAmount = totalAmount.add(itemModel.getAmount());
        }
        entity.setTotalAmount(totalAmount);
        workRecordDao.updateById(entity);
        workItemDao.delItem(delIds, model.getId());
        if (!CollectionUtils.isEmpty(entities)) {
            workItemDao.addItem(entities);
        }
        if (!CollectionUtils.isEmpty(updateList)) {
            workItemDao.updateItem(updateList);
        }
    }

    @Override
    public Boolean editRecord(WorkRecordModel model, UserInfo info) throws OwnerException {
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            editRecordDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑失败！");
        }

        return true;
    }

    @Override
    public Boolean submit(WorkRecordModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return true;
        }
        Integer id;
        Integer userId = info.getId();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            if (model.getId() == null) {
                addRecordDetail(model, info);
            } else {
                editRecordDetail(model, info);
            }

            id = flowInstanceService.submitForm(model.getId(),userId,moduleId);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("提交失败！");
        }

        kafkaQueueService.submitAudit(Arrays.asList(id), userId, FlowModuleTypeEnum.FORM);
        return true;
    }

    @Override
    public Boolean delRecord(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)) {
            return true;
        }
        List<Integer> delList = new ArrayList<>();
        List<FlowInstanceFormEntity> statusList = flowInstanceFormDao.getStatus(ids, moduleId);
        Map<Integer, Integer> map = statusList.stream().collect(Collectors.toMap(a -> a.getFormId(), a -> a.getStatus()));
        for (Integer id : ids) {
            Boolean del = true;
            if (map.containsKey(id)) {
                del = FlowInstanceStatusEnum.canModify(map.get(id));
            }
            if (del) {
                delList.add(id);
            }
        }
        if (CollectionUtils.isEmpty(delList)) {
            throw new OwnerException("所有数据都已提交审核，删除失败！");
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            workRecordDao.deleteBatchIds(delList);
            workItemDao.delItemByRecord(delList);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("删除失败！");
        }
        return true;
    }
}
