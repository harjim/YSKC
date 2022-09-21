package com.yskc.ms.service.impl.ms;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.FlowInstanceStatusEnum;
import com.yskc.common.enums.FlowModuleTypeEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.config.MsConfig;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.entity.ms.ContractEntity;
import com.yskc.ms.entity.ms.ContractMemberEntity;
import com.yskc.ms.entity.ms.ContractProjectEntity;
import com.yskc.ms.entity.ms.FlowInstanceFormEntity;
import com.yskc.ms.enums.MemberTypeEnum;
import com.yskc.ms.enums.SnEnum;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.contract.*;
import com.yskc.ms.models.user.MiniUserModel;
import com.yskc.ms.service.ms.ContractService;
import com.yskc.ms.service.ms.ContractTraceabilityService;
import com.yskc.ms.service.ms.FlowInstanceService;
import com.yskc.ms.service.ms.SnService;
import com.yskc.ms.service.rs.KafkaQueueService;
import com.yskc.ms.utils.ToolUtil;
import com.yskc.ms.utils.TransactionUtils;
import com.yskc.ms.utils.YsWordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 合同
 *
 * @author wjy
 */
@Service
public class ContractServiceImpl implements ContractService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Integer moduleId = AuditModeEnum.CONTRACT.getModuleId();
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private ContractMemberDao contractMemberDao;
    @Autowired
    private ContractProjectDao contractProjectDao;
    @Autowired
    private FlowInstanceFormDao flowInstanceFormDao;
    @Autowired
    private FlowInstanceService flowInstanceService;
    @Autowired
    private KafkaQueueService kafkaQueueService;
    @Autowired
    private FlowInstanceLogDao flowInstanceLogDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private SnService snService;
    @Autowired
    private ContractTraceabilityService contractTraceabilityService;
    @Autowired
    private MsConfig msConfig;

    @Override
    public ContractModel getInfo(Integer id, UserInfo info) {
        Set<Integer> userIds = new HashSet<>(), ids = new HashSet<>();
        Map<Integer, String> userMap = new HashMap<>();
        ids.add(id);
        ContractModel model = contractDao.getInfo(id, moduleId);
        if (model != null) {
            userIds.add(model.getBusinessId());
            userIds.add(model.getOwnerId());
            userIds.add(model.getTechId());
            userIds.add(model.getFinaId());
            if (!CollectionUtils.isEmpty(userIds)) {
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)) {
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<ContractMemberModel> memberList = contractMemberDao.getMemberList(ids);
            List<ContractProjectModel> projectList = contractProjectDao.getProjectList(ids);
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(ids, moduleId, info.getId());
            model.setBusinessName(userMap.get(model.getBusinessId()));
            model.setOwnerName(userMap.get(model.getOwnerId()));
            model.setTechName(userMap.get(model.getTechId()));
            model.setFinaName(userMap.get(model.getFinaId()));
            model.setMemberList(memberList);
            model.setProjectList(projectList);
            model.setHasPermission(!CollectionUtils.isEmpty(hasPermission));
            if (model.getInstanceId() != null) {
                List<ContractNodeModel> lastLogs = flowInstanceLogDao.getLastLogs(model.getInstanceId(), model.getNodeNumber(), model.getStatus());
                Map<Integer, ContractNodeModel> nodeModelMap = lastLogs.stream()
                        .collect(Collectors.toMap(a -> a.getNodeNumber(), a -> a, (o, n) -> o));
                model.setNodeList(new ArrayList<>(nodeModelMap.values()));
            }
            return model;
        }
        return null;
    }

    @Override
    public PageModel<List<ContractModel>> getList(QueryContractModel query, UserInfo info, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getDescs()) && CollectionUtils.isEmpty(page.getAscs())) {
            page.setDescs(new ArrayList<>());
            page.getDescs().add("co.contractNo");
        }
        Set<Integer> userIds = new HashSet<>();
        Map<Integer, String> userMap = new HashMap<>();
        Map<Integer, ContractModel> map;
        Map<Integer, List<ContractMemberModel>> memberMap = new HashMap<>();
        Map<Integer, List<ContractProjectModel>> projectMap = new HashMap<>();

        List<ContractModel> list = contractDao.getList(page, moduleId, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            map = list.stream().collect(Collectors.toMap(a -> a.getId(), a -> {
                userIds.add(a.getBusinessId());
                userIds.add(a.getOwnerId());
                userIds.add(a.getTechId());
                userIds.add(a.getFinaId());
                return a;
            }));
            if (!CollectionUtils.isEmpty(userIds)) {
                List<MiniUserModel> users = userDao.getUsers(userIds);
                if (!CollectionUtils.isEmpty(users)) {
                    userMap = users.stream().collect(Collectors.toMap(a -> a.getId(), a -> a.getRealName()));
                }
            }
            List<ContractMemberModel> memberList = contractMemberDao.getMemberList(map.keySet());
            if (!CollectionUtils.isEmpty(memberList)) {
                memberMap = memberList.stream().collect(Collectors.groupingBy(ContractMemberModel::getContractId));
            }
            List<ContractProjectModel> projectList = contractProjectDao.getProjectList(map.keySet());
            if (!CollectionUtils.isEmpty(projectList)) {
                projectMap = projectList.stream().collect(Collectors.groupingBy(ContractProjectModel::getContractId));
            }
            List<FlowInstanceFormEntity> hasPermission = flowInstanceFormDao.getHasPermission(map.keySet(), moduleId, info.getId());
            Map<Integer, FlowInstanceFormEntity> hasPermMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(hasPermission)) {
                hasPermMap = hasPermission.stream().collect(Collectors.toMap(a -> a.getId(), a -> a, (o, n) -> n));
            }

            for (Integer id : map.keySet()) {
                ContractModel model = map.get(id);
                model.setBusinessName(userMap.get(model.getBusinessId()));
                model.setOwnerName(userMap.get(model.getOwnerId()));
                model.setTechName(userMap.get(model.getTechId()));
                model.setFinaName(userMap.get(model.getFinaId()));
                model.setMemberList(CollectionUtils.isEmpty(memberMap) ? new ArrayList<>() : (memberMap.get(id)));
                model.setProjectList(CollectionUtils.isEmpty(projectMap) ? new ArrayList<>() : (projectMap.get(id)));
                model.setHasPermission(!CollectionUtils.isEmpty(hasPermMap) && hasPermMap.containsKey(id));
            }
        }
        return PageUtils.buildPageResult(page, list);
    }

    @Override
    public Integer getProduct(QueryProductModel query, UserInfo info) {
        Integer product = contractProjectDao.getProduct(query);
        if (product == null) {
            return 1;
        } else {
            return product + 1;
        }
    }

    @Override
    public String checkProduct(CheckProductModel model) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getProjectList())) {
            throw new OwnerException("项目类型列不可为空！");
        }
        List<String> list = contractProjectDao.checkProduct(model.getProjectList(), model.getCustomerId());
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return "项目类型：【" + String.join("，", list) + "】有重复起止年，是否要继续操作？";
    }

    @Override
    public Boolean addExpress(Integer id, UserInfo info, String expressNo, String expressAddress) throws OwnerException {
        if (!StringUtils.hasLength(expressNo) || !StringUtils.hasLength(expressAddress)) {
            throw new OwnerException("请填写完整快递信息！");
        }
        ContractEntity entity = new ContractEntity();
        entity.setId(id);
        entity.setExpressNo(expressNo);
        entity.setExpressAddress(expressAddress);
        ToolUtil.entityUpdate(entity, info.getId(), new Date());
        return contractDao.updateById(entity) > 0;
    }

    @Override
    public void addPassContract(Integer id, UserInfo info) throws Exception {
        ContractModel model = contractDao.getPassInfo(id);
        passContract(id, info, model.getCustomerId(), model.getContractNo(), model.getFilepath());
    }


    private void addContractDetail(AddContractModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getProjectList())) {
            throw new OwnerException("项目列表不可为空！");
        }
        if (model.getEffectDate().after(model.getCloseDate())) {
            throw new OwnerException("生效日期应小于终止日期！");
        }
        String productName = contractProjectDao.getModel(model.getProjectList(), model.getCustomerId());
        if (StringUtils.hasLength(productName)) {
            throw new OwnerException("项目类型：【" + productName + "】签约次数重复，请重新输入！");
        }

        Date now = new Date();
        BigDecimal decimal = new BigDecimal(100);
        BigDecimal total = BigDecimal.ZERO;
        Integer userId = info.getId();
        String contractNo = snService.getNextNo(SnEnum.CONTRACT, model.getPrefix());

        ContractEntity entity = new ContractEntity();
        entity.setCustomerId(model.getCustomerId());
        entity.setDeptId(model.getDeptId());
        entity.setTechId(model.getTechId());
        entity.setFinaId(model.getFinaId());
        entity.setSignDate(model.getSignDate());
        entity.setEffectDate(model.getEffectDate());
        entity.setCloseDate(model.getCloseDate());
        entity.setFilepath(model.getFilepath());
        entity.setQrcode(model.getQrcode());
        entity.setRemark(model.getRemark());
        entity.setSealType(org.apache.commons.lang3.StringUtils.join(model.getSealType(), ","));
        entity.setProjectCnt(model.getProjectList().size());
        entity.setPartnerCnt(model.getMemberList().size());
        entity.setContractNo(contractNo);
        ToolUtil.entityCreate(entity, userId, now);
        contractDao.insert(entity);
        Integer id = entity.getId();
        model.setId(id);
        List<ContractMemberEntity> memberEntities = new ArrayList<>();
        Map<Integer, ContractMemberModel> memberMap = new HashMap<>();
        List<ContractProjectEntity> projectEntities = new ArrayList<>();
        Map<Integer, ContractProjectModel> projectMap = new HashMap<>();

        if (id != null) {
            ContractMemberEntity owner = new ContractMemberEntity();
            owner.setContractId(id);
            owner.setMtype(MemberTypeEnum.Saler.getId());
            owner.setUserId(model.getOwnerId());
            owner.setRatio(new BigDecimal(-1));
            ToolUtil.entityCreate(owner, userId, now);
            ContractMemberEntity business = new ContractMemberEntity();
            business.setContractId(id);
            business.setMtype(MemberTypeEnum.Businesser.getId());
            business.setUserId(model.getBusinessId());
            business.setRatio(new BigDecimal(-1));
            ToolUtil.entityCreate(business, userId, now);
            memberEntities.add(owner);
            memberEntities.add(business);
            if (!CollectionUtils.isEmpty(model.getMemberList())) {
                for (ContractMemberModel memberModel : model.getMemberList()) {
                    if (memberModel.getUserId().equals(model.getOwnerId())) {
                        continue;
                    }
                    total = total.add(memberModel.getRatio());
                    if (memberMap.containsKey(memberModel.getUserId())) {
                        throw new OwnerException("合作人员不可重复！");
                    }
                    memberMap.put(memberModel.getUserId(), memberModel);
                    ContractMemberEntity member = new ContractMemberEntity();
                    member.setContractId(id);
                    member.setMtype(MemberTypeEnum.OTHER.getId());
                    member.setUserId(memberModel.getUserId());
                    member.setRatio(memberModel.getRatio());
                    member.setRemark(memberModel.getRemark());
                    ToolUtil.entityCreate(member, userId, now);
                    memberEntities.add(member);
                }
            }

            for (ContractProjectModel projectModel : model.getProjectList()) {
                if (projectMap.containsKey(projectModel.getProductId())) {
                    ContractProjectModel check = projectMap.get(projectModel.getProductId());
                    if (check.getSignCnt().equals(projectModel.getSignCnt())) {
                        throw new OwnerException("同一项目类型签约次数不可重复！");
                    }
                    if (check.getBeginYear().equals(projectModel.getBeginYear()) &&
                            check.getEndYear().equals(projectModel.getEndYear())) {
                        throw new OwnerException("同一项目类型起止年份不可重复！");
                    }
                }
                projectMap.put(projectModel.getProductId(), projectModel);
                if (projectModel.getRatio().compareTo(decimal) > 0) {
                    throw new OwnerException("项目收费比例应小于100%，请重新输入！");
                }
                ContractProjectEntity projectEntity = new ContractProjectEntity();
                projectEntity.setContractId(id);
                projectEntity.setCustomerId(model.getCustomerId());
                projectEntity.setProductId(projectModel.getProductId());
                projectEntity.setBeginYear(projectModel.getBeginYear());
                projectEntity.setEndYear(projectModel.getEndYear());
                projectEntity.setRatio(projectModel.getRatio());
                projectEntity.setSignCnt(projectModel.getSignCnt());
                projectEntity.setRemark(projectModel.getRemark());
                ToolUtil.entityCreate(projectEntity, userId, now);
                projectEntities.add(projectEntity);
            }
            if (total.compareTo(decimal) > 0) {
                throw new OwnerException("合作人员列贡献比之和应小于100%，请重新输入！");
            }
        }
        contractMemberDao.addMemberList(memberEntities);
        contractProjectDao.addProjectList(projectEntities);
    }

    @Override
    public Boolean addContract(AddContractModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return true;
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            addContractDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (OwnerException e) {
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

    private void editContractDetail(AddContractModel model, UserInfo info) throws OwnerException {
        if (CollectionUtils.isEmpty(model.getProjectList())) {
            throw new OwnerException("项目列表不可为空！");
        }
        if (model.getEffectDate().after(model.getCloseDate())) {
            throw new OwnerException("生效日期应小于终止日期！");
        }
        String productName = contractProjectDao.getModel(model.getProjectList(), model.getCustomerId());
        if (StringUtils.hasLength(productName)) {
            throw new OwnerException("项目类型：【" + productName + "】签约次数重复，请重新输入！");
        }
        Date now = new Date();
        Integer userId = info.getId();
        BigDecimal decimal = new BigDecimal(100);
        BigDecimal total = BigDecimal.ZERO;
        ContractEntity entity = new ContractEntity();
        entity.setId(model.getId());
        entity.setCustomerId(model.getCustomerId());
        entity.setDeptId(model.getDeptId());
        entity.setTechId(model.getTechId());
        entity.setFinaId(model.getFinaId());
        entity.setSignDate(model.getSignDate());
        entity.setEffectDate(model.getEffectDate());
        entity.setCloseDate(model.getCloseDate());
        entity.setFilepath(model.getFilepath());
        entity.setQrcode(model.getQrcode());
        entity.setRemark(model.getRemark());
        entity.setSealType(org.apache.commons.lang3.StringUtils.join(model.getSealType(), ","));
        entity.setProjectCnt(model.getProjectList().size());
        entity.setPartnerCnt(model.getMemberList().size());
        ToolUtil.entityUpdate(entity, userId, now);
        Integer id = model.getId();

        List<ContractMemberEntity> memberEntities = new ArrayList<>();
        List<ContractMemberEntity> updateMember = new ArrayList<>();
        Map<Integer, ContractMemberModel> memberMap = new HashMap<>();
        List<ContractProjectEntity> projectEntities = new ArrayList<>();
        List<ContractProjectEntity> updateProject = new ArrayList<>();
        Map<Integer, ContractProjectModel> projectMap = new HashMap<>();
        List<Integer> projectIds = new ArrayList<>();
        List<Integer> memberIds = new ArrayList<>();

        if (id != null) {
            ContractMemberEntity owner = new ContractMemberEntity();
            owner.setContractId(id);
            owner.setMtype(MemberTypeEnum.Saler.getId());
            owner.setUserId(model.getOwnerId());
            owner.setRatio(new BigDecimal(-1));
            ToolUtil.entityCreate(owner, userId, now);
            ContractMemberEntity business = new ContractMemberEntity();
            business.setContractId(id);
            business.setMtype(MemberTypeEnum.Businesser.getId());
            business.setUserId(model.getBusinessId());
            business.setRatio(new BigDecimal(-1));
            ToolUtil.entityCreate(business, userId, now);
            memberEntities.add(owner);
            memberEntities.add(business);
            if (!CollectionUtils.isEmpty(model.getMemberList())) {
                for (ContractMemberModel memberModel : model.getMemberList()) {

                    total = total.add(memberModel.getRatio());
                    if (memberMap.containsKey(memberModel.getUserId())) {
                        throw new OwnerException("合作人员不可重复！");
                    }
                    memberMap.put(memberModel.getUserId(), memberModel);
                    ContractMemberEntity member = new ContractMemberEntity();
                    member.setContractId(id);
                    member.setMtype(MemberTypeEnum.OTHER.getId());
                    member.setUserId(memberModel.getUserId());
                    member.setRatio(memberModel.getRatio());
                    member.setRemark(memberModel.getRemark());
                    ToolUtil.entityCreate(member, userId, now);
                    if (memberModel.getId() != null && memberModel.getId() != 0) {
                        member.setId(memberModel.getId());
                        memberIds.add(memberModel.getId());
                        updateMember.add(member);
                    } else {
                        memberEntities.add(member);
                    }
                }
            }

            for (ContractProjectModel projectModel : model.getProjectList()) {
                if (projectMap.containsKey(projectModel.getProductId())) {
                    ContractProjectModel check = projectMap.get(projectModel.getProductId());
                    if (check.getSignCnt().equals(projectModel.getSignCnt())) {
                        throw new OwnerException("同一项目类型签约次数不可重复！");
                    }
                    if (check.getBeginYear().equals(projectModel.getBeginYear()) &&
                            check.getEndYear().equals(projectModel.getEndYear())) {
                        throw new OwnerException("同一项目类型起止年份不可重复！");
                    }
                }
                projectMap.put(projectModel.getProductId(), projectModel);
                if (projectModel.getRatio().compareTo(decimal) > 0) {
                    throw new OwnerException("项目收费比例应小于100%，请重新输入！");
                }
                ContractProjectEntity projectEntity = new ContractProjectEntity();
                projectEntity.setContractId(id);
                projectEntity.setCustomerId(model.getCustomerId());
                projectEntity.setProductId(projectModel.getProductId());
                projectEntity.setBeginYear(projectModel.getBeginYear());
                projectEntity.setEndYear(projectModel.getEndYear());
                projectEntity.setRatio(projectModel.getRatio());
                projectEntity.setSignCnt(projectModel.getSignCnt());
                projectEntity.setRemark(projectModel.getRemark());
                ToolUtil.entityCreate(projectEntity, userId, now);
                if (projectModel.getId() != null && projectModel.getId() != 0) {
                    projectEntity.setId(projectModel.getId());
                    projectIds.add(projectModel.getId());
                    updateProject.add(projectEntity);
                } else {
                    projectEntities.add(projectEntity);
                }
            }
            if (total.compareTo(decimal) > 0) {
                throw new OwnerException("合作人员列贡献比之和应小于100%，请重新输入！");
            }
        }
        contractDao.updateById(entity);
        contractMemberDao.delByIds(memberIds, id);
        contractProjectDao.delByIds(projectIds, id);
        if (!CollectionUtils.isEmpty(memberEntities)) {
            contractMemberDao.addMemberList(memberEntities);
        }
        if (!CollectionUtils.isEmpty(updateMember)) {
            contractMemberDao.editMemberList(updateMember);
        }
        if (!CollectionUtils.isEmpty(projectEntities)) {
            contractProjectDao.addProjectList(projectEntities);
        }
        if (!CollectionUtils.isEmpty(updateProject)) {
            contractProjectDao.editProjectList(updateProject);
        }

    }

    @Override
    public Boolean editContract(AddContractModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return true;
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            editContractDetail(model, info);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (OwnerException e) {
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
    public Boolean delContract(List<Integer> ids) throws OwnerException {
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
            contractDao.deleteBatchIds(delList);
            contractMemberDao.delByContractIds(delList);
            contractProjectDao.delByContractIds(delList);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("删除失败！");
        }
        return true;
    }

    @Override
    public Boolean submit(AddContractModel model, UserInfo info) throws OwnerException {
        if (model == null) {
            return false;
        }
        Integer id;
        Integer userId = info.getId();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            if (model.getId() == null) {
                addContractDetail(model, info);
            } else {
                editContractDetail(model, info);
            }

            id = flowInstanceService.submitForm(model.getId(), userId, moduleId);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        }catch (OwnerException e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("提交失败！");
        }
        kafkaQueueService.submitAudit(Arrays.asList(id), userId, FlowModuleTypeEnum.FORM);
        return true;
    }


    /**
     * 通过合同
     * 当前节点为 合同审批 （nodeNumber=2），且审核通过
     *
     * @param id         合同id
     * @param info
     * @param customerId
     * @param contractNo
     * @param filepath
     * @throws Exception
     */
    private void passContract(Integer id, UserInfo info, Integer customerId, String contractNo, String filepath) throws OwnerException {
        TransactionStatus transactionStatus = null;
        String qrCode;
        Boolean hasQrCode;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            qrCode = contractTraceabilityService.generateTraceability(info, customerId, contractNo);
            if (hasQrCode = qrCode != null) {
                contractDao.updateQrcode(id, qrCode, info.getId(), new Date());
            }
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException("生成二维码失败，请联系管理员！");
        }
        if (!hasQrCode) {
            return;
        }
        File oldFile = new File(msConfig.getUploadConfig().getDocPath() + filepath);
        //文件不存在，不做操作
        if (!oldFile.exists()) {
            return;
        }
        // 重命名为bak+合同号
        String bakName = "bak" + contractNo;
        try {
            String sourceFile = FileUtil.rename(oldFile, "bak" + contractNo, true, true).getAbsolutePath();
            YsWordUtils.generateHeader(sourceFile, "合同编号：" + contractNo, msConfig.getUploadConfig().getImagePath() + qrCode,
                    msConfig.getUploadConfig().getDocPath() + filepath);
        } catch (Exception e) {
            logger.error("生成页眉失败，合同编号：" + contractNo);
            logger.error(e.getMessage(), e);
            int lastIndex = filepath.lastIndexOf(Constant.PATH_SEPARATOR + 1);
            // 生成页眉失败后，把备份文件还原为原文件
            FileUtil.rename(new File(msConfig.getUploadConfig().getDocPath() + filepath.substring(0, lastIndex) + bakName),
                    filepath.substring(lastIndex), true, true);
        }
    }

}
