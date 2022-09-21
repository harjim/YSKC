package com.yskc.rs.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.InspectionDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.InspectionEntity;
import com.yskc.rs.enums.CostEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.InspectionExcel;
import com.yskc.rs.models.inspection.InspectionModel;
import com.yskc.rs.models.inspection.QueryInspectionModel;
import com.yskc.rs.models.projectinspection.QueryProjectInspectionModel;
import com.yskc.rs.service.InspectionService;
import com.yskc.rs.utils.ListUtils;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: zhangdingfu
 * @CreateTime: 2019-07-11 15:06
 * @Description: 检测修理业务层实现
 */
@Service
public class InspectionServiceImpl extends ServiceImpl<InspectionDao, InspectionEntity> implements InspectionService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InspectionDao inspectionDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private AccountTitleDao accountTitleDao;

    @Override
    public PageModel<List<InspectionModel>> queryInspectionByTerm(Integer companyId,
                                                                  QueryProjectInspectionModel query) {
        Pagination page = query.getPagination();
        setDesc(page);
        Date beginDate = null;
        Date endDate = null;
        if (query.getProjectMonth() != null) {
            beginDate = DateUtil.beginOfMonth(query.getProjectMonth());
            endDate = DateUtil.endOfMonth(beginDate);
        }
        return PageUtils.buildPageResult(page,
                inspectionDao.queryInspectionByTerm(page, companyId, beginDate, endDate, query),
                null,
                query.getTypes().length == 5 ? inspectionDao.getOtherSum(companyId, beginDate, endDate, query) :
                        query.getTypes().length == 3 ?
                                inspectionDao.getAmortizationSum(companyId, beginDate, endDate, query) :
                                inspectionDao.getInspectionSum(companyId, beginDate, endDate, query));
    }

    /**
     * 设置排序
     *
     * @param page
     */
    private void setDesc(Pagination page) {
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            page.setDescs(CollUtil.newArrayList("accDate"));
        }
    }

    @Override
    public PageModel<List<InspectionModel>> queryAll(Integer companyId, QueryInspectionModel inspectionQuery) {
        if (inspectionQuery.getAccDate() != null) {
            inspectionQuery.setAccDate(DateUtil.beginOfDay(inspectionQuery.getAccDate()));
        }
        Pagination page = inspectionQuery.getPagination();
        setDesc(page);
        if (inspectionQuery.getType().length > 0 && String.valueOf(CostEnum.TRAVEL.getType()).equals(inspectionQuery.getType()[0])) {
            return PageUtils.build(page, inspectionDao.querytravelData(page, companyId, inspectionQuery));
        }
        return PageUtils.build(page, inspectionDao.queryAll(page, companyId, inspectionQuery));
    }

    @Override
    public Boolean modifyInspection(UserInfo info, InspectionModel model) throws OwnerException {
        InspectionEntity iEntity = new InspectionEntity();
        //id不存在 设置初始数据
        if (null == model.getId() || model.getId() <= 0) {
            iEntity.setCompanyId(info.getCompanyId());
            iEntity.setMsCreatorId(info.getMsUserId());
            iEntity.setCreatorId(info.getUserId());
            iEntity.setCreateTime(new Date());
            //存在id，查询当前数据
        } else {
            iEntity = inspectionDao.selectById(model.getId());
        }
        iEntity.setMsLastUpdatorId(info.getMsUserId());
        iEntity.setLastUpdatorId(info.getUserId());
        iEntity.setLastUpdateTime(new Date());
        iEntity.setAccDate(model.getAccDate());
        iEntity.setAccNumber(model.getAccNumber());
        iEntity.setType(model.getType() != null ? model.getType() : -1);
        iEntity.setExpense(model.getExpense());
        iEntity.setSummary(model.getSummary());
        iEntity.setRemark(model.getRemark());
        iEntity.setEnumber(model.getEnumber());
        iEntity.setDeptName(model.getDeptName());
        iEntity.setAccountTitleId(model.getAccountTitleId());
        iEntity.setRemainExpense(model.getExpense());
        if (null != model.getId() && model.getId() > 0) {
            return inspectionDao.updateById(iEntity) > 0;
        } else {
            return inspectionDao.insert(iEntity) > 0;
        }
    }

    @Override
    public String importInspection(UserInfo info, List<InspectionExcel> inspectionExcels, Integer type, Integer year) throws OwnerException {
        String returnMsg = "";
        if (inspectionExcels.size() == 0) {
            return returnMsg;
        }
        List<String> fullAccountNameList = new ArrayList<>();
        inspectionExcels.forEach(item -> {
            if (!StringUtils.isEmpty(item.getFullAccountName())) {
                if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                    item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                }
                fullAccountNameList.add(item.getFullAccountName());
            }
        });
        List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameList);
        Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b));
        List<String> importEnameList = inspectionExcels.stream().filter(a -> !StringUtils.isEmpty(a.getEname())).map(InspectionExcel::getEname).collect(Collectors.toList());
        List<EmployeeEntity> employeeEntityList = new ArrayList<>();
        Map<String, EmployeeEntity> employeeMap = new HashMap<>();

        if (importEnameList.size() > 0) {
            employeeEntityList = employeeDao.getEmployeeByTerm(info.getCompanyId(), importEnameList);
        }
        if (employeeEntityList.size() > 0) {
            employeeMap = employeeEntityList.stream().collect(Collectors.toMap(EmployeeEntity::getEname, Function.identity()));
        }
        List<InspectionEntity> addList = new ArrayList<>();
        for (int i = 0; i < inspectionExcels.size(); i++) {
            InspectionExcel inspectionExcel = inspectionExcels.get(i);
            int accountTitleId = 0;
            if (accountTitleEntityMap.containsKey(inspectionExcel.getFullAccountName())) {
                accountTitleId = accountTitleEntityMap.get(inspectionExcel.getFullAccountName()).getId();
            }
            InspectionEntity entity = new InspectionEntity();
            String typeName = inspectionExcel.getTypeName();
            Integer currentType = type;
            if (!StringUtils.isEmpty(typeName)) {
                currentType = CostEnum.getCostEnum(inspectionExcel.getTypeName().trim()).getType();
                if (currentType == 0) {
                    String error = MessageFormat.format(
                            "第{0}行，费用类型【{1}】不存在，请从中选择：{2}",
                            i + 2, inspectionExcel.getTypeName(),
                            type == null || type > 0 ? "检测、修理、软件摊销、专利摊销、其他摊销、差旅费、资料、研发成果、知识产权、福利、其他、其他试制、样机，例如：检测"
                                    : "软件摊销、专利摊销、其他摊销，例如：软件摊销");
                    throw new OwnerException(error);
                }
            }
            entity.setType(currentType);
            String enumber = null;
            if (employeeMap.containsKey(inspectionExcel.getEname())) {
                enumber = employeeMap.get(inspectionExcel.getEname()).getEnumber();
            }
            entity.setRdDeptId(0);
            entity.setDeptName(inspectionExcel.getDeptName());
            entity.setSummary(inspectionExcel.getSummary());
            entity.setExpense(inspectionExcel.getExpense());
            entity.setAccNumber(inspectionExcel.getAccNumber());
            entity.setAccDate(inspectionExcel.getAccDate());
            entity.setCompanyId(info.getCompanyId());
            entity.setRemark(inspectionExcel.getRemark());
            entity.setEnumber(enumber);
            entity.setAccountTitleId(accountTitleId);
            entity.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
            entity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
            entity.setCreateTime(new Date());
            entity.setLastUpdateTime(new Date());
            entity.setRemainExpense(inspectionExcel.getExpense());
            addList.add(entity);
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (addList.size() > 0) {
                List<List<InspectionEntity>> insertList = ListUtils.subList(addList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InspectionEntity> items : insertList) {
                    inspectionDao.addBatch(items);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("importInspection", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return returnMsg;

    }

    @Override
    public List<InspectionExcel> exportInspection(Integer compantyId, QueryInspectionModel inspectionQuery) {
        return inspectionDao.queryForExport(compantyId, inspectionQuery);
    }

    @Override
    public boolean updateInspectionRdDept(UserInfo info, InspectionModel model) {
        List<InspectionEntity> entities = model.getEntityList();
        int rdDeptId = 0;
        for (int i = 0; i < entities.size(); i++) {
            InspectionEntity entity = entities.get(i);
            entity.setRdDeptId(rdDeptId);
        }
        updateBatchById(entities);
        return true;
    }

    @Override
    public boolean updateInspetioneType(UserInfo info, InspectionModel model) {
        List<InspectionEntity> entities = model.getEntityList();
        int type = model.getType();
        for (int i = 0; i < entities.size(); i++) {
            InspectionEntity entity = entities.get(i);
            entity.setType(type);
        }
        updateBatchById(entities);
        return true;
    }

    @Override
    public Boolean deleteById(Integer id) throws OwnerException {
        List<Integer> usedIds = inspectionDao.getProjectUsed(CollUtil.newArrayList(id));
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目】中，不能删除!");
        }
        return inspectionDao.deleteById(id) > 0;
    }

    @Override
    public boolean delInspetiontBatch(List<InspectionModel> models) throws OwnerException {
        List<Integer> ids = models.stream().map(InspectionModel::getId).distinct().collect(Collectors.toList());
        List<Integer> usedIds = inspectionDao.getProjectUsed(ids);
        Collection<Integer> result = CollUtil.disjunction(ids, usedIds);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选费用已全部在【创新项目】中使用，不能删除!");
        }
        return inspectionDao.deleteBatchIds(result) > 0;
    }

    @Override
    public PageModel<List<InspectionModel>> queryTravelFee(Integer companyId, QueryProjectInspectionModel query) {
        Pagination page = query.getPagination();
        setDesc(page);
        Date beginDate = null;
        Date endDate = null;
        if (query.getProjectMonth() != null) {
            beginDate = DateUtil.beginOfMonth(query.getProjectMonth());
            endDate = DateUtil.endOfMonth(beginDate);
        }
        return PageUtils.buildPageResult(page,
                inspectionDao.queryTravelFee(page, beginDate, endDate, companyId, query, query.getTypes()[0]),
                null,
                inspectionDao.queryTravelFeeSum(beginDate, endDate, companyId, query, query.getTypes()[0]));
    }
}
