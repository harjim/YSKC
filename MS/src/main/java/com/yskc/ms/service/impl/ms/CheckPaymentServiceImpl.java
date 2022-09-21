package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.RsProjectDao;
import com.yskc.ms.entity.ms.CheckPaymentEntity;
import com.yskc.ms.entity.ms.CheckPaymentRdEntity;
import com.yskc.ms.entity.ms.FlowInstanceFormEntity;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.checkInst.CheckMiniModel;
import com.yskc.ms.models.checkPayment.AddPaymentModel;
import com.yskc.ms.models.checkPayment.CheckPaymentModel;
import com.yskc.ms.models.checkPayment.PaymentProjectModel;
import com.yskc.ms.models.checkPayment.QueryCheckModel;
import com.yskc.ms.models.contract.ContractNodeModel;
import com.yskc.ms.models.rs.ReportProjectModel;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.ms.CheckPaymentService;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
 * @description:
 * @author: wjy
 * @create: 2022-09-06 11:25
 **/
@Service
public class CheckPaymentServiceImpl implements CheckPaymentService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Integer moduleId = AuditModeEnum.CHECK_PAYMENT.getModuleId();
    @Autowired
    private CheckPaymentDao checkPaymentDao;
    @Autowired
    private CheckPaymentRdDao checkPaymentRdDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;
    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private RsProjectDao rsProjectDao;
    @Autowired
    private CheckInstDao checkInstDao;



    @Override
    public PageModel<List<CheckPaymentModel>> getList(QueryCheckModel query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("cp.lastUpdateTime");
        }
        Set<Integer> userIds = new HashSet<>();
        Map<Integer, String> userMap = new HashMap<>();
        Map<Integer,CheckPaymentModel> map;
        Map<Integer,List<PaymentProjectModel>> projectMap = new HashMap<>();

        List<CheckPaymentModel> list = checkPaymentDao.getList(page, moduleId, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)){
            map = list.stream().collect(Collectors.toMap(a->a.getId(),a->{
                userIds.add(a.getOwnerId());
                userIds.add(a.getFinaManagerId());
                userIds.add(a.getFinaDirectorId());
                userIds.add(a.getTechId());
                userIds.add(a.getOwnerMangerId());
                return a;
            }));
            if (!CollectionUtils.isEmpty(userIds)) {
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)) {
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<PaymentProjectModel> projectList = checkPaymentRdDao.getProjectList(map.keySet());
            if (!CollectionUtils.isEmpty(projectList)) {
                projectMap = projectList.stream().collect(Collectors.groupingBy(PaymentProjectModel::getCheckPaymentId));
            }
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(map.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)) {
                hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a, (o, n) -> n));
            }
            for (Integer id : map.keySet()) {
                CheckPaymentModel model = map.get(id);
                model.setOwnerName(userMap.get(model.getOwnerId()));
                model.setOwnerMangerName(userMap.get(model.getOwnerMangerId()));
                model.setTechName(userMap.get(model.getTechId()));
                model.setFinaManagerName(userMap.get(model.getFinaManagerId()));
                model.setFinaDirectorName(userMap.get(model.getFinaDirectorId()));
                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap) && hasPermMap.containsKey(id));
                model.setList(CollectionUtils.isEmpty(projectMap) ? new ArrayList<>() : (projectMap.get(id)));
            }
        }

        return PageUtils.buildPageResult(page,list);
    }

    @Override
    public CheckPaymentModel getInfo(Integer id, UserInfo info) {
        Set<Integer> userIds = new HashSet<>(), ids = new HashSet<>();
        Map<Integer, String> userMap = new HashMap<>();
        ids.add(id);
        CheckPaymentModel model = checkPaymentDao.getInfo(id, moduleId);
        if (model != null) {
            userIds.add(model.getOwnerId());
            userIds.add(model.getFinaManagerId());
            userIds.add(model.getFinaDirectorId());
            userIds.add(model.getTechId());
            userIds.add(model.getOwnerMangerId());
            if (!CollectionUtils.isEmpty(userIds)) {
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)) {
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<PaymentProjectModel> projectList = checkPaymentRdDao.getProjectList(ids);
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(ids, moduleId, info.getId());
            if (model.getInstanceId()!=null){
                List<ContractNodeModel> lastLogs = flowInstanceLogDao.getLastLogs(model.getInstanceId(),model.getNodeNumber(),model.getStatus());
                Map<Integer, ContractNodeModel> nodeModelMap = lastLogs.stream()
                        .collect(Collectors.toMap(a -> a.getNodeNumber(), a -> a, (o, n) -> o));
                model.setNodeList(new ArrayList<>(nodeModelMap.values()));
            }
            model.setOwnerName(userMap.get(model.getOwnerId()));
            model.setOwnerMangerName(userMap.get(model.getOwnerMangerId()));
            model.setTechName(userMap.get(model.getTechId()));
            model.setFinaManagerName(userMap.get(model.getFinaManagerId()));
            model.setFinaDirectorName(userMap.get(model.getFinaDirectorId()));
            model.setHasPermission(!CollectionUtils.isEmpty(hasPermission));
            model.setList(projectList);
            return model;
        }
        return null;
    }

    @Override
    public List<ReportProjectModel> getProjectList(Integer customerId,Integer year) {
        if (customerId==null||customerId==0||
                year==null||year==0){
            return new ArrayList<>();
        }
        Integer companyId = customerDao.getCompanyId(customerId);
        return rsProjectDao.getProByCustomerId(companyId,year);
    }

    @Override
    public List<CheckMiniModel> getCheckInstList(String checkInstName) {
        if (!StringUtils.hasLength(checkInstName)){
            return new ArrayList<>();
        }
        return checkInstDao.getMiniList(checkInstName);
    }

    private void addCheckPaymentDetail(AddPaymentModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getList())){
            throw new OwnerException("项目列表不可为空！");
        }

        Date now = new Date();
        Integer userId = info.getId();
        CheckPaymentEntity entity = new CheckPaymentEntity();
        BeanUtils.copyProperties(model,entity);
        entity.setRdCnt(model.getList().size());
        entity.setTotalAmount(model.getUnitPrice().multiply(BigDecimal.valueOf(model.getList().size())));
        ToolUtil.entityCreate(entity,userId,now);
        checkPaymentDao.insert(entity);
        Integer id = entity.getId();
        model.setId(id);
        List<CheckPaymentRdEntity> projectEntities = new ArrayList<>();
        Map<String, PaymentProjectModel> pnameMap = new HashMap<>();
        Map<String, PaymentProjectModel> rdTitleMap = new HashMap<>();
        if (id != null) {
            if (!CollectionUtils.isEmpty(model.getList())) {
                for (PaymentProjectModel projectModel : model.getList()) {
                    if (pnameMap.containsKey(projectModel.getPname())){
                        throw new OwnerException("项目列表项目名称重复，请检查！");
                    }
                    if (rdTitleMap.containsKey(projectModel.getRdTitle())){
                        throw new OwnerException("项目列表项目编号重复，请检查！");
                    }
                    pnameMap.put(projectModel.getPname(),projectModel);
                    rdTitleMap.put(projectModel.getRdTitle(),projectModel);
                    CheckPaymentRdEntity rdEntity = new CheckPaymentRdEntity();
                    BeanUtils.copyProperties(projectModel,rdEntity);
                    rdEntity.setCheckPaymentId(id);
                    ToolUtil.entityCreate(rdEntity,userId,now);
                    projectEntities.add(rdEntity);
                }
                checkPaymentRdDao.addProjectList(projectEntities);
            }
        }

    }

    @Override
    public Boolean addCheckPayment(AddPaymentModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return true;
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            addCheckPaymentDetail(model, info);
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

    @Override
    public Boolean addPaymentFile(Integer id, UserInfo info, String payType, String paymentFile) throws OwnerException {
        if (!StringUtils.hasLength(payType)||!StringUtils.hasLength(paymentFile)){
            throw new OwnerException("请填写完整付款信息！");
        }
        CheckPaymentEntity entity = new CheckPaymentEntity();
        entity.setId(id);
        entity.setPayType(payType);
        entity.setPaymentFile(paymentFile);
        ToolUtil.entityUpdate(entity,info.getId(),new Date());
        return checkPaymentDao.updateById(entity)>0;
    }

    private void editCheckPaymentDetail(AddPaymentModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getList())){
            throw new OwnerException("项目列表不可为空！");
        }

        Date now = new Date();
        Integer userId = info.getId();
        CheckPaymentEntity entity = new CheckPaymentEntity();

        BeanUtils.copyProperties(model,entity);
        entity.setRdCnt(model.getList().size());
        entity.setTotalAmount(model.getUnitPrice().multiply(BigDecimal.valueOf(model.getList().size())));
        ToolUtil.entityUpdate(entity,userId,now);
        Integer id = model.getId();
        List<CheckPaymentRdEntity> projectEntities = new ArrayList<>();
        List<CheckPaymentRdEntity> updateProject = new ArrayList<>();
        List<Integer> projectIds = new ArrayList<>();
        Map<String, PaymentProjectModel> pnameMap = new HashMap<>();
        Map<String, PaymentProjectModel> rdTitleMap = new HashMap<>();
        if (id != null) {
            if (!CollectionUtils.isEmpty(model.getList())) {
                for (PaymentProjectModel projectModel : model.getList()) {
                    if (pnameMap.containsKey(projectModel.getPname())){
                        throw new OwnerException("项目列表项目名称重复，请检查！");
                    }
                    if (rdTitleMap.containsKey(projectModel.getRdTitle())){
                        throw new OwnerException("项目列表项目编号重复，请检查！");
                    }
                    pnameMap.put(projectModel.getPname(),projectModel);
                    rdTitleMap.put(projectModel.getRdTitle(),projectModel);
                    CheckPaymentRdEntity rdEntity = new CheckPaymentRdEntity();
                    BeanUtils.copyProperties(projectModel,rdEntity);
                    rdEntity.setCheckPaymentId(id);
                    ToolUtil.entityCreate(rdEntity,userId,now);
                    if (projectModel.getId()!=null && projectModel.getId() != 0){
                        projectIds.add(projectModel.getId());
                        updateProject.add(rdEntity);
                    }else {
                        projectEntities.add(rdEntity);
                    }
                }
                checkPaymentDao.updateById(entity);
                checkPaymentRdDao.delByIds(projectIds,id);
                if (!CollectionUtils.isEmpty(projectEntities)) {
                    checkPaymentRdDao.addProjectList(projectEntities);
                }
                if (!CollectionUtils.isEmpty(updateProject)) {
                    checkPaymentRdDao.editProjectList(updateProject);
                }
            }
        }
    }

    @Override
    public Boolean editCheckPayment(AddPaymentModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return true;
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            editCheckPaymentDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        }catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("编辑失败！");
        }

        return true;
    }

    @Override
    public Boolean delCheckPayment(List<Integer> ids) throws OwnerException {
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
            checkPaymentDao.deleteBatchIds(delList);
            checkPaymentRdDao.delByCheckPaymentIds(delList);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("删除失败！");
        }
        return true;
    }

    @Override
    public Boolean submit(AddPaymentModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return false;
        }
        Integer id;
        Integer userId = info.getId();

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            if (model.getId() == null) {
                addCheckPaymentDetail(model, info);
            } else {
                editCheckPaymentDetail(model, info);
            }

            id = flowInstanceService.submitForm(model.getId(), userId, moduleId);
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
}
