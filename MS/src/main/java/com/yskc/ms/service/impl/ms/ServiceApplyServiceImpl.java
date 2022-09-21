package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.*;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.enums.SnEnum;
import com.yskc.ms.enums.UserRoleEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.serviceApply.*;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.ms.ServiceApplyService;
import com.yskc.ms.service.ms.SnService;
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

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: ms
 * @description:
 * @author: cyj
 * @create: 2022-08-11 13:38
 **/
@Service
public class ServiceApplyServiceImpl implements ServiceApplyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Integer moduleId = AuditModeEnum.SERVICE_APPLY.getModuleId();

    @Autowired
    private ServiceApplyDao serviceApplyDao;
    @Autowired
    private ServiceMemberDao serviceMemberDao;
    @Autowired
    private ServiceCustomerDao serviceCustomerDao;
    @Autowired
    private ServiceAuditorDao serviceAuditorDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private FlowCurNodeDao flowCurNodeDao;
    @Autowired
    private FlowInstanceUserDao flowInstanceUserDao;
    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private SnService snService;


    @Override
    public PageModel<List<ServiceApplyModel>> getList(QueryApplyModel query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("sa.serviceNo");
        }

        Set<Integer> userIds = new HashSet<>();
        Map<Integer,String> userMap = new HashMap<>();
        Map<String,List<ServiceMemberModel>> memberMap = new HashMap<>();
        Map<Integer, List<ServiceCustomerModel>> customerMap = new HashMap<>();
        String format = "yyyy-MM-dd";

        List<ServiceApplyModel> list = serviceApplyDao.getList(page, query,moduleId,dataPerm);
        Map<Integer, ServiceApplyModel> applyModelMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)){
            applyModelMap = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> {
                userIds.add(a.getFinaDirectorId());
                userIds.add(a.getFinaManagerId());
                userIds.add(a.getTechDirectorId());
                userIds.add(a.getTechManagerId());
                a.setDate(DateUtil.format(a.getBegin(),format)+"-"+DateUtil.format(a.getEnd(),format));

                return a;
            }));
            if (!CollectionUtils.isEmpty(userIds)){
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)){
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<ServiceMemberModel> memberList = serviceMemberDao.getMemberList(applyModelMap.keySet());
            if (!CollectionUtils.isEmpty(memberList)){
                memberMap = memberList.stream().collect(Collectors.groupingBy(a->a.getServiceApplyId().toString()+a.getMtype()));
            }
            List<ServiceCustomerModel> customerList = serviceCustomerDao.getCustomerList(applyModelMap.keySet());
            if (!CollectionUtils.isEmpty(customerList)){
                customerMap = customerList.stream().collect(Collectors.groupingBy(ServiceCustomerModel::getServiceApplyId));
            }
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(applyModelMap.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)){
                 hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a,(o,n)->n));
            }

            for (Integer serviceId : applyModelMap.keySet()) {
                ServiceApplyModel model = applyModelMap.get(serviceId);
                List<ServiceMemberModel> finaList = new ArrayList<>();
                List<ServiceMemberModel> techList = new ArrayList<>();
                List<ServiceMemberModel> otherList = new ArrayList<>();

                model.setFinaDirectorName(userMap.get(model.getFinaDirectorId()));
                model.setFinaManagerName(userMap.get(model.getFinaManagerId()));
                model.setTechDirectorName(userMap.get(model.getTechDirectorId()));
                model.setTechManagerName(userMap.get(model.getTechManagerId()));

                model.setCustomerList(CollectionUtils.isEmpty(customerMap)?new ArrayList<>():(customerMap.get(serviceId)));

                if (!CollectionUtils.isEmpty(memberMap)){
                    finaList = memberMap.get(serviceId.toString()+MemberTypeEnum.FINANCE.getId());
                    techList = memberMap.get(serviceId.toString()+MemberTypeEnum.Tech.getId());
                    otherList = memberMap.get(serviceId.toString()+MemberTypeEnum.OTHER.getId());
                }

                model.setFinaList(finaList);
                model.setTechList(techList);
                model.setOtherList(otherList);

                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap)&&hasPermMap.containsKey(serviceId));
            }

        }
        return PageUtils.buildPageResult(page,list);
    }

    @Override
    public PageModel<List<ServiceApplyModel>> getMobileList(QueryMobileApply query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("sa.serviceNo");
        }
        Set<Integer> userIds = new HashSet<>();
        Map<Integer,String> userMap = new HashMap<>();
        Map<String,List<ServiceMemberModel>> memberMap = new HashMap<>();
        Map<Integer, List<ServiceCustomerModel>> customerMap = new HashMap<>();
        String format = "yyyy-MM-dd";

        List<ServiceApplyModel> list = serviceApplyDao.getMobileList(page,query,moduleId,dataPerm);
        Map<Integer, ServiceApplyModel> applyModelMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)){
            applyModelMap = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> {
                userIds.add(a.getFinaDirectorId());
                userIds.add(a.getFinaManagerId());
                userIds.add(a.getTechDirectorId());
                userIds.add(a.getTechManagerId());
                a.setDate(DateUtil.format(a.getBegin(),format)+"-"+DateUtil.format(a.getEnd(),format));

                return a;
            }));
            if (!CollectionUtils.isEmpty(userIds)){
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)){
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<ServiceMemberModel> memberList = serviceMemberDao.getMemberList(applyModelMap.keySet());
            if (!CollectionUtils.isEmpty(memberList)){
                memberMap = memberList.stream().collect(Collectors.groupingBy(a->a.getServiceApplyId().toString()+a.getMtype()));
            }
            List<ServiceCustomerModel> customerList = serviceCustomerDao.getCustomerList(applyModelMap.keySet());
            if (!CollectionUtils.isEmpty(customerList)){
                customerMap = customerList.stream().collect(Collectors.groupingBy(ServiceCustomerModel::getServiceApplyId));
            }
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(applyModelMap.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)){
                hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a,(o,n)->n));
            }

            for (Integer serviceId : applyModelMap.keySet()) {
                ServiceApplyModel model = applyModelMap.get(serviceId);
                List<ServiceMemberModel> finaList = new ArrayList<>();
                List<ServiceMemberModel> techList = new ArrayList<>();
                List<ServiceMemberModel> otherList = new ArrayList<>();

                model.setFinaDirectorName(userMap.get(model.getFinaDirectorId()));
                model.setFinaManagerName(userMap.get(model.getFinaManagerId()));
                model.setTechDirectorName(userMap.get(model.getTechDirectorId()));
                model.setTechManagerName(userMap.get(model.getTechManagerId()));

                model.setCustomerList(CollectionUtils.isEmpty(customerMap)?new ArrayList<>():(customerMap.get(serviceId)));

                if (!CollectionUtils.isEmpty(memberMap)){
                    finaList = memberMap.get(serviceId.toString()+MemberTypeEnum.FINANCE.getId());
                    techList = memberMap.get(serviceId.toString()+MemberTypeEnum.Tech.getId());
                    otherList = memberMap.get(serviceId.toString()+MemberTypeEnum.OTHER.getId());
                }

                model.setFinaList(finaList);
                model.setTechList(techList);
                model.setOtherList(otherList);

                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap)&&hasPermMap.containsKey(serviceId));
            }

        }
        return PageUtils.buildPageResult(page,list);
    }

    @Override
    public SelectMemberModel getMemberList(QueryUserModel model) {
        SelectMemberModel memberModel = new SelectMemberModel();
        String fullPath = null;
        List<String> fullPaths = null;
        List<Integer> list = null;

        if (!CollectionUtils.isEmpty(model.getUserIds())){
            if (!model.getType().equals(MemberTypeEnum.OTHER.getId())){
                fullPath = userDao.getFullPath(model.getUserIds());
                fullPaths = new ArrayList<>(Arrays.asList(fullPath.split(Constant.PATH_SEPARATOR)));
                list = fullPaths.stream().map(Integer::parseInt).collect(Collectors.toList());
                List<ServiceLeaderModel> leaderList = userDao.getDeptByRole(list, UserRoleEnum.FINA_MANAGER.getRoleName(), UserRoleEnum.TECH_MANAGER.getRoleName()
                        ,UserRoleEnum.FINA_DIRECTOR.getRoleName(), UserRoleEnum.TECH_DIRECTOR.getRoleName() );
                if (!CollectionUtils.isEmpty(leaderList)){
                    for (ServiceLeaderModel leader : leaderList) {
                        if (leader.getRoleName().equals(UserRoleEnum.FINA_DIRECTOR.getRoleName())){
                            memberModel.setFinaDirectorId(leader.getUserId());
                            memberModel.setFinaDirectorName(leader.getUserName());
                        }
                        if (leader.getRoleName().equals(UserRoleEnum.FINA_MANAGER.getRoleName())){
                            memberModel.setFinaManagerId(leader.getUserId());
                            memberModel.setFinaManagerName(leader.getUserName());
                        }
                        if (leader.getRoleName().equals(UserRoleEnum.TECH_DIRECTOR.getRoleName())){
                            memberModel.setTechDirectorId(leader.getUserId());
                            memberModel.setTechDirectorName(leader.getUserName());
                        }
                        if (leader.getRoleName().equals(UserRoleEnum.TECH_MANAGER.getRoleName())){
                            memberModel.setTechManagerId(leader.getUserId());
                            memberModel.setTechManagerName(leader.getUserName());
                        }
                    }
                }
            }
        }
        if (StringUtils.hasLength(model.getUserName())){
            List<ServiceMemberModel> memberList = userDao.getMemberList(model.getUserName(), fullPath,list);
            if (!CollectionUtils.isEmpty(memberList)){
                if (model.getType().equals(MemberTypeEnum.OTHER.getId())){
                    memberModel.setOtherList(memberList);
                }
                if (model.getType().equals(MemberTypeEnum.FINANCE.getId())){
                    memberModel.setFinaList(memberList);
                }
                if (model.getType().equals(MemberTypeEnum.Tech.getId())){
                    memberModel.setTechList(memberList);
                }
            }
        }

        return memberModel;
    }

    @Override
    public MobileMemberModel getMobileMemberList(QueryUserModel model) {
        MobileMemberModel memberModel = new MobileMemberModel();
        String fullPath = null;
        List<String> fullPaths = null;
        List<Integer> list = null;

        if (!CollectionUtils.isEmpty(model.getUserIds())){
            if (!model.getType().equals(MemberTypeEnum.OTHER.getId())){
                fullPath = userDao.getFullPath(model.getUserIds());
                fullPaths = new ArrayList<>(Arrays.asList(fullPath.split(Constant.PATH_SEPARATOR)));
                list = fullPaths.stream().map(Integer::parseInt).collect(Collectors.toList());
                List<ServiceLeaderModel> leaderList = userDao.getDeptByRole(list, UserRoleEnum.FINA_MANAGER.getRoleName(), UserRoleEnum.TECH_MANAGER.getRoleName()
                        ,UserRoleEnum.FINA_DIRECTOR.getRoleName(), UserRoleEnum.TECH_DIRECTOR.getRoleName() );
                if (!CollectionUtils.isEmpty(leaderList)){
                    for (ServiceLeaderModel leader : leaderList) {
                        if (leader.getRoleName().equals(UserRoleEnum.FINA_DIRECTOR.getRoleName())
                        ||leader.getRoleName().equals(UserRoleEnum.TECH_DIRECTOR.getRoleName())){
                            memberModel.setDirectorId(leader.getUserId());
                            memberModel.setDirectorName(leader.getUserName());
                        }
                        if (leader.getRoleName().equals(UserRoleEnum.FINA_MANAGER.getRoleName())
                        ||leader.getRoleName().equals(UserRoleEnum.TECH_MANAGER.getRoleName())){
                            memberModel.setManagerId(leader.getUserId());
                            memberModel.setManagerName(leader.getUserName());
                        }
                    }
                }
            }
        }
        if (StringUtils.hasLength(model.getUserName())){
            List<ServiceMemberModel> memberList = userDao.getMemberList(model.getUserName(), fullPath,list);
            if (!CollectionUtils.isEmpty(memberList)){
                memberModel.setList(memberList);
            }
        }
        return memberModel;
    }

    @Override
    public List<ServiceCustomerModel> getCustomerList(QueryCustomerModel model) {
        return customerDao.getServiceCustomerList(model);
    }

    @Override
    public ServiceApplyModel getApplyInfo(String serviceNo,Integer id,UserInfo info) {
        if (!StringUtils.hasLength(serviceNo)&&null==id){
            return null;
        }

        Set<Integer> userIds = new HashSet<>();
        Map<Integer,String> userMap = new HashMap<>();
        Map<String,List<ServiceMemberModel>> memberMap = new HashMap<>();
        Map<Integer, List<ServiceCustomerModel>> customerMap = new HashMap<>();
        String format = "yyyy-MM-dd";
        Set<Integer> modelIds = new HashSet<>();

        ServiceApplyModel model = serviceApplyDao.getApplyInfo(serviceNo,id);
        if (null==model){
            return null;
        }
        userIds.add(model.getFinaDirectorId());
        userIds.add(model.getFinaManagerId());
        userIds.add(model.getTechDirectorId());
        userIds.add(model.getTechManagerId());
        modelIds.add(model.getId());
        Integer serviceId = model.getId();
        model.setDate(DateUtil.format(model.getBegin(),format)+"-"+DateUtil.format(model.getEnd(),format));
        if (!CollectionUtils.isEmpty(userIds)){
            List<MiniUserModel> users = userDao.getUsers(userIds);
            if (!CollectionUtils.isEmpty(users)){
                userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
            }
        }
        List<ServiceMemberModel> memberList = serviceMemberDao.getMemberList(modelIds);
        if (!CollectionUtils.isEmpty(memberList)){
            memberMap = memberList.stream().collect(Collectors.groupingBy(a->a.getServiceApplyId().toString()+a.getMtype()));
        }
        List<ServiceCustomerModel> customerList = serviceCustomerDao.getCustomerList(modelIds);
        if (!CollectionUtils.isEmpty(customerList)){
            customerMap = customerList.stream().collect(Collectors.groupingBy(ServiceCustomerModel::getServiceApplyId));
        }
        List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(modelIds, moduleId, info.getId());

        List<ServiceMemberModel> finaList = new ArrayList<>();
        List<ServiceMemberModel> techList = new ArrayList<>();
        List<ServiceMemberModel> otherList = new ArrayList<>();

        model.setFinaDirectorName(userMap.get(model.getFinaDirectorId()));
        model.setFinaManagerName(userMap.get(model.getFinaManagerId()));
        model.setTechDirectorName(userMap.get(model.getTechDirectorId()));
        model.setTechManagerName(userMap.get(model.getTechManagerId()));
        model.setCustomerList(CollectionUtils.isEmpty(customerMap)?new ArrayList<>():(customerMap.get(serviceId)));
        model.setHasPermission(!CollectionUtils.isEmpty(hasPermission));
        if (!CollectionUtils.isEmpty(memberMap)){
            finaList = memberMap.get(serviceId.toString()+MemberTypeEnum.FINANCE.getId());
            techList = memberMap.get(serviceId.toString()+MemberTypeEnum.Tech.getId());
            otherList = memberMap.get(serviceId.toString()+MemberTypeEnum.OTHER.getId());
        }

        model.setFinaList(finaList);
        model.setTechList(techList);
        model.setOtherList(otherList);
        return model;
    }

    private void editServiceApplyDetail(ServiceApplyModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getTechList()) && CollectionUtils.isEmpty(model.getFinaList())) {
            throw new OwnerException("财务人员列和技术人员列不可同时为空，请重新输入！");
        }
        if (model.getEnd().before(model.getBegin())){
            throw new OwnerException("起止时间错误，请重新输入！");
        }
        Date now = new Date();
        List<Integer> customerIds = new ArrayList<>();
        List<Integer> memberIds = new ArrayList<>();
        ServiceApplyEntity applyEntity = new ServiceApplyEntity();
        applyEntity.setBegin(model.getBegin());
        applyEntity.setEnd(model.getEnd());
        applyEntity.setRemark(model.getRemark());
        Map<Integer, ServiceCustomerModel> customerEntityMap = new HashMap<>();

        if (!CollectionUtils.isEmpty(model.getCustomerList())){
            List<String> companyNames = new ArrayList<>();
            for (ServiceCustomerModel customerModel : model.getCustomerList()) {
                if (!CollectionUtils.isEmpty(customerEntityMap)&&customerEntityMap.containsKey(customerModel.getCustomerId())){
                    continue;
                }
                customerEntityMap.put(customerModel.getCustomerId(),customerModel);
                companyNames.add(customerModel.getCompanyName());
            }
            applyEntity.setCustomers(String.join(",", companyNames));

        } else {
            throw new OwnerException("请先添加服务事项！");
        }
        applyEntity.setId(model.getId());
        ToolUtil.entityUpdate(applyEntity, info.getId(), now);

        Integer serviceApplyId = model.getId();
        if (serviceApplyId != null) {
            List<ServiceMemberEntity> memberEntities = new ArrayList<>();
            Map<Integer, ServiceMemberModel> otherMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(model.getFinaList())) {
                for (ServiceMemberModel memberModel : model.getFinaList()) {
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.FINANCE.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity, info.getId(), now);
                    if (memberModel.getId() != null && memberModel.getId() != 0) {
                        memberEntity.setId(memberModel.getId());
                        memberIds.add(memberModel.getId());
                    } else {
                        memberEntities.add(memberEntity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(model.getOtherList())) {
                for (ServiceMemberModel memberModel : model.getOtherList()) {
                    if (otherMap.containsKey(memberModel.getUserId())){
                        continue;
                    }
                    otherMap.put(memberModel.getUserId(), memberModel);
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.OTHER.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity, info.getId(), now);
                    if (memberModel.getId() != null && memberModel.getId() != 0) {
                        memberEntity.setId(memberModel.getId());
                        memberIds.add(memberModel.getId());
                    } else {
                        memberEntities.add(memberEntity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(model.getTechList())) {
                for (ServiceMemberModel memberModel : model.getTechList()) {
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.Tech.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity, info.getId(), now);
                    if (memberModel.getId() != null && memberModel.getId() != 0) {
                        memberEntity.setId(memberModel.getId());
                        memberIds.add(memberModel.getId());
                    } else {
                        memberEntities.add(memberEntity);
                    }
                }
            }

            List<ServiceCustomerEntity> customerEntities = new ArrayList<>();
            List<ServiceCustomerEntity> updateCustomer = new ArrayList<>();
            Map<Integer, ServiceCustomerModel> customerMap = new HashMap<>();

            for (ServiceCustomerModel customerModel : model.getCustomerList()) {
                if (!CollectionUtils.isEmpty(customerMap)&&customerMap.containsKey(customerModel.getCustomerId())){
                    continue;
                }
                customerMap.put(customerModel.getCustomerId(),customerModel);
                ServiceCustomerEntity customerEntity = new ServiceCustomerEntity();
                customerEntity.setCustomerId(customerModel.getCustomerId());
                customerEntity.setServiceApplyId(serviceApplyId);
                customerEntity.setCauses(customerModel.getCauses());
                ToolUtil.entityCreate(customerEntity, info.getId(), now);
                if (customerModel.getId() != null && customerModel.getId() != 0) {
                    customerIds.add(customerModel.getId());
                    customerEntity.setId(customerModel.getId());
                    updateCustomer.add(customerEntity);
                } else {
                    customerEntities.add(customerEntity);
                }
            }

            ServiceAuditorEntity auditorEntity = new ServiceAuditorEntity();
            auditorEntity.setServiceApplyId(serviceApplyId);
            auditorEntity.setFinaDirectorId(model.getFinaDirectorId());
            auditorEntity.setFinaManagerId(model.getFinaManagerId());
            auditorEntity.setTechDirectorId(model.getTechDirectorId());
            auditorEntity.setTechManagerId(model.getTechManagerId());
            ToolUtil.entityUpdate(auditorEntity, info.getId(), now);
            serviceApplyDao.updateById(applyEntity);
            serviceAuditorDao.updateAuditor(auditorEntity);
            serviceCustomerDao.delByCustomerIds(customerIds, serviceApplyId);
            serviceMemberDao.delByUserIds(memberIds, serviceApplyId,Arrays.asList(MemberTypeEnum.Saler.getId()));
            if (!CollectionUtils.isEmpty(memberEntities)) {
                serviceMemberDao.addMemberList(memberEntities);
            }
            if (!CollectionUtils.isEmpty(customerEntities)) {
                serviceCustomerDao.addCustomerList(customerEntities);
            }
            if (!CollectionUtils.isEmpty(updateCustomer)) {
                serviceCustomerDao.updateCustomer(updateCustomer);
            }
        }
    }

    @Override
    public Boolean editServiceApply(ServiceApplyModel model, UserInfo info) throws OwnerException {
        if (model==null){
            return true;
        }

            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            editServiceApplyDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("编辑失败！");
        }

        return true;
    }

    @Override
    public Boolean editAuditApply(ApplyEditModel model, UserInfo info) throws OwnerException {
        if (model==null || model.getId()==null||model.getId()==0){
            return true;
        }
        List<ServiceMemberEntity> memberEntities = new ArrayList<>();
        Map<Integer,ServiceMemberModel> otherMap = new HashMap<>();
        Integer serviceApplyId = model.getId(),userId = info.getId();
        Date now = new Date();
        ApplyEditModel applyEditModel = new ApplyEditModel();
        List<Integer> memberIds = new ArrayList<>(),mtypes = new ArrayList<>();
        Boolean editAll = true;

        mtypes.add(MemberTypeEnum.FINANCE.getId());
        mtypes.add(MemberTypeEnum.Tech.getId());
        ServiceApplyModel applyModel = serviceApplyDao.getApplyInfo(null, serviceApplyId);
        if (userId.equals(applyModel.getTechDirectorId())||userId.equals(applyModel.getTechManagerId())){
            if (CollectionUtils.isEmpty(model.getTechList())){
                throw new OwnerException("请选择技术人员！");
            }
            editAll = false;
            applyEditModel.setTechList(model.getTechList());
            mtypes.remove(MemberTypeEnum.Tech.getId());
            mtypes.add(MemberTypeEnum.OTHER.getId());
        }
        if (userId.equals(applyModel.getFinaDirectorId())||userId.equals(applyModel.getFinaManagerId())){
            if (CollectionUtils.isEmpty(model.getFinaList())){
                throw new OwnerException("请选择财务人员！");
            }
            editAll = false;
            applyEditModel.setFinaList(model.getFinaList());
            mtypes.remove(MemberTypeEnum.FINANCE.getId());
            mtypes.add(MemberTypeEnum.OTHER.getId());
        }
        mtypes.add(MemberTypeEnum.Saler.getId());
        if (editAll){
            mtypes.remove(MemberTypeEnum.FINANCE.getId());
            mtypes.remove(MemberTypeEnum.Tech.getId());
            applyEditModel.setFinaList(model.getFinaList());
            applyEditModel.setTechList(model.getTechList());
            applyEditModel.setOtherList(model.getOtherList());
        }
        if (!CollectionUtils.isEmpty(applyEditModel.getFinaList())) {
            for (ServiceMemberModel memberModel : applyEditModel.getFinaList()) {
                ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                memberEntity.setServiceApplyId(serviceApplyId);
                memberEntity.setMtype(MemberTypeEnum.FINANCE.getId());
                memberEntity.setUserId(memberModel.getUserId());
                ToolUtil.entityCreate(memberEntity, info.getId(), now);
                if (memberModel.getId() != null && memberModel.getId() != 0) {
                    memberIds.add(memberModel.getId());
                } else {
                    memberEntities.add(memberEntity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(applyEditModel.getOtherList())) {
            for (ServiceMemberModel memberModel : applyEditModel.getOtherList()) {
                if (otherMap.containsKey(memberModel.getUserId())){
                    continue;
                }
                otherMap.put(memberModel.getUserId(), memberModel);
                ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                memberEntity.setServiceApplyId(serviceApplyId);
                memberEntity.setMtype(MemberTypeEnum.OTHER.getId());
                memberEntity.setUserId(memberModel.getUserId());
                ToolUtil.entityCreate(memberEntity, info.getId(), now);
                if (memberModel.getId() != null && memberModel.getId() != 0) {
                    memberIds.add(memberModel.getId());
                } else {
                    memberEntities.add(memberEntity);
                }
            }
        }
        if (!CollectionUtils.isEmpty(applyEditModel.getTechList())) {
            for (ServiceMemberModel memberModel : applyEditModel.getTechList()) {
                ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                memberEntity.setServiceApplyId(serviceApplyId);
                memberEntity.setMtype(MemberTypeEnum.Tech.getId());
                memberEntity.setUserId(memberModel.getUserId());
                ToolUtil.entityCreate(memberEntity, info.getId(), now);
                if (memberModel.getId() != null && memberModel.getId() != 0) {
                    memberIds.add(memberModel.getId());
                } else {
                    memberEntities.add(memberEntity);
                }
            }
        }
        ServiceAuditorEntity auditorEntity = new ServiceAuditorEntity();
        auditorEntity.setServiceApplyId(serviceApplyId);
        auditorEntity.setFinaDirectorId(model.getFinaDirectorId());
        auditorEntity.setFinaManagerId(model.getFinaManagerId());
        auditorEntity.setTechDirectorId(model.getTechDirectorId());
        auditorEntity.setTechManagerId(model.getTechManagerId());
        ToolUtil.entityUpdate(auditorEntity, info.getId(), now);
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            serviceAuditorDao.updateAuditor(auditorEntity);
            serviceMemberDao.delByUserIds(memberIds, serviceApplyId,mtypes);
            if (!CollectionUtils.isEmpty(memberEntities)) {
                serviceMemberDao.addMemberList(memberEntities);
            }
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("编辑失败！");
        }
        return true;
    }

    private void addServiceApplyDetail(ServiceApplyModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getTechList())&&CollectionUtils.isEmpty(model.getFinaList())){
            throw new OwnerException("财务人员列和技术人员列不可同时为空，请重新输入！");
        }
        if (model.getEnd().before(model.getBegin())){
            throw new OwnerException("起止时间错误，请重新输入！");
        }
        Date now = new Date();

        String nextNo = snService.getNextNo(SnEnum.SERVICE_APPLY);

        ServiceApplyEntity applyEntity = new ServiceApplyEntity();
        Map<Integer, ServiceCustomerModel> customerEntityMap = new HashMap<>();
        applyEntity.setBegin(model.getBegin());
        applyEntity.setEnd(model.getEnd());
        applyEntity.setRemark(model.getRemark());
//        applyEntity.setOwnerId(info.getId());
        applyEntity.setServiceNo(nextNo);

        if (!CollectionUtils.isEmpty(model.getCustomerList())){
            List<String> companyNames = new ArrayList<>();
            for (ServiceCustomerModel customerModel : model.getCustomerList()) {
                if (!CollectionUtils.isEmpty(customerEntityMap)){
                    if (customerEntityMap.containsKey(customerModel.getCustomerId())){
                        continue;
                    }
                }
                customerEntityMap.put(customerModel.getCustomerId(),customerModel);
                companyNames.add(customerModel.getCompanyName());
            }
            applyEntity.setCustomers(String.join(",", companyNames));

        }else {
            throw new OwnerException("请先添加服务事项！");
        }
        ToolUtil.entityCreate(applyEntity, info.getId(), now);
        serviceApplyDao.insert(applyEntity);
        Integer serviceApplyId = applyEntity.getId();
        List<ServiceMemberEntity> memberEntities = new ArrayList<>();
        Map<Integer,ServiceMemberModel> otherMap = new HashMap<>();
        List<ServiceCustomerEntity> customerEntities = new ArrayList<>();
        Map<Integer, ServiceCustomerModel> customerMap = new HashMap<>();
        ServiceAuditorEntity auditorEntity = new ServiceAuditorEntity();
        if (serviceApplyId!=null){
            model.setId(serviceApplyId);

            ServiceMemberEntity member = new ServiceMemberEntity();
            member.setServiceApplyId(serviceApplyId);
            member.setMtype(MemberTypeEnum.Saler.getId());
            member.setUserId(info.getId());
            ToolUtil.entityCreate(member,info.getId(),now);
            memberEntities.add(member);

            if (!CollectionUtils.isEmpty(model.getFinaList())){
                for (ServiceMemberModel memberModel : model.getFinaList()) {
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.FINANCE.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity,info.getId(),now);
                    memberEntities.add(memberEntity);
                }
            }
            if (!CollectionUtils.isEmpty(model.getOtherList())){
                for (ServiceMemberModel memberModel : model.getOtherList()) {
                    if (otherMap.containsKey(memberModel.getUserId())){
                        continue;
                    }
                    otherMap.put(memberModel.getUserId(), memberModel);
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.OTHER.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity,info.getId(),now);
                    memberEntities.add(memberEntity);
                }
            }
            if (!CollectionUtils.isEmpty(model.getTechList())){
                for (ServiceMemberModel memberModel : model.getTechList()) {
                    ServiceMemberEntity memberEntity = new ServiceMemberEntity();
                    memberEntity.setServiceApplyId(serviceApplyId);
                    memberEntity.setMtype(MemberTypeEnum.Tech.getId());
                    memberEntity.setUserId(memberModel.getUserId());
                    ToolUtil.entityCreate(memberEntity,info.getId(),now);
                    memberEntities.add(memberEntity);
                }
            }

            for (ServiceCustomerModel customerModel : model.getCustomerList()) {
                if (!CollectionUtils.isEmpty(customerMap)&&customerMap.containsKey(customerModel.getCustomerId())){
                    continue;
                }
                customerMap.put(customerModel.getCustomerId(),customerModel);
                ServiceCustomerEntity customerEntity = new ServiceCustomerEntity();
                customerEntity.setCustomerId(customerModel.getCustomerId());
                customerEntity.setServiceApplyId(serviceApplyId);
                customerEntity.setCauses(customerModel.getCauses());
                ToolUtil.entityCreate(customerEntity,info.getId(),now);

                customerEntities.add(customerEntity);
            }

            auditorEntity.setServiceApplyId(serviceApplyId);
            auditorEntity.setFinaDirectorId(model.getFinaDirectorId());
            auditorEntity.setFinaManagerId(model.getFinaManagerId());
            auditorEntity.setTechDirectorId(model.getTechDirectorId());
            auditorEntity.setTechManagerId(model.getTechManagerId());
            ToolUtil.entityCreate(auditorEntity,info.getId(),now);
        }
        if (!CollectionUtils.isEmpty(memberEntities)){
            serviceMemberDao.addMemberList(memberEntities);
        }
        if (!CollectionUtils.isEmpty(customerEntities)){
            serviceCustomerDao.addCustomerList(customerEntities);
        }
        serviceAuditorDao.addOrUpdate(auditorEntity);
    }

    @Override
    public Boolean addServiceApply(ServiceApplyModel model, UserInfo info) throws OwnerException {
        if (model==null){
            return true;
        }
            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            addServiceApplyDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("添加失败！");
        }

        return true;
    }

    @Override
    public Boolean delServiceApply(List<Integer> ids) throws OwnerException {
        if (CollectionUtils.isEmpty(ids)){
            return true;
        }
        List<Integer> delList = new ArrayList<>();
        List<FlowInstanceFormEntity> statusList = flowInstanceFormDao.getStatus(ids, moduleId);
        Map<Integer, Integer> map = statusList.stream().collect(Collectors.toMap(a -> a.getFormId(), a -> a.getStatus()));
        for (Integer id : ids) {
            Boolean del = true;
            if (map.containsKey(id)){
                del = FlowInstanceStatusEnum.canModify(map.get(id));
            }
            if (del){
                delList.add(id);
            }
        }
        if (CollectionUtils.isEmpty(delList)){
            throw new OwnerException("所有数据都已提交审核，删除失败！");
        }

        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            serviceApplyDao.deleteBatchIds(delList);
            serviceCustomerDao.delByApplyIds(delList);
            serviceMemberDao.delByApplyIds(delList);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("删除失败！");
        }
        return true;
    }

    @Override
    public Boolean submit(ServiceApplyModel applyModel, UserInfo info) throws OwnerException {
        if (applyModel==null){
            return false;
        }
        Integer id;
        Integer userId = info.getId();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            if (applyModel.getId()==null){
                addServiceApplyDetail(applyModel, info);
            }else {
                editServiceApplyDetail(applyModel, info);
            }

            id = flowInstanceService.submitForm(applyModel.getId(),userId,moduleId);
            TransactionUtils.commit(DataSourceEnum.MS,transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            TransactionUtils.rollback(DataSourceEnum.MS,transactionStatus);
            throw new OwnerException("提交失败！");
        }

        kafkaQueueService.submitAudit(Arrays.asList(id),userId, FlowModuleTypeEnum.FORM);
        return true;
    }

}
