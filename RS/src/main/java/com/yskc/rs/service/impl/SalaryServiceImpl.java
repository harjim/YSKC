package com.yskc.rs.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.AccountTitleDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.FieldConfigDao;
import com.yskc.rs.dao.SalaryDao;
import com.yskc.rs.entity.AccountTitleEntity;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.SalaryEntity;
import com.yskc.rs.enums.FieldConfigTypeEnum;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.excel.SalaryExcel;
import com.yskc.rs.models.fieldconfig.FieldConfigModel;
import com.yskc.rs.models.salary.SalaryInfoModel;
import com.yskc.rs.models.salary.SalaryModel;
import com.yskc.rs.models.salary.SalaryQuery;
import com.yskc.rs.service.SalaryService;
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
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryDao, SalaryEntity> implements SalaryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SalaryDao salaryDao;

    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private AccountTitleDao accountTitleDao;

    @Autowired
    private FieldConfigDao fieldConfigDao;

    @Override
    public PageModel<List<SalaryModel>> querySalary(SalaryQuery salaryQuery) {
        if (salaryQuery.getMonth() != null) {
            salaryQuery.setMonth(DateUtil.getMonthFirstDay(salaryQuery.getMonth()));
        }
        Pagination page = salaryQuery.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("s.month");
            page.setDescs(descs);
        }
        return PageUtils.build(page, salaryDao.querySalary(page, salaryQuery));

    }

    @Override
    public boolean updateSalary(UserInfo info, SalaryModel syModel) throws OwnerException {
        EmployeeModel model = employeeDao.getEmployeeByenumber(info.getCompanyId(), syModel.getEnumber());
        syModel.setEname(model.getEname());
        SalaryEntity salaryEntity = setValue(new Date(), info, syModel);
        salaryDao.updateById(salaryEntity);
        return true;
    }

    @Override
    public boolean addSalary(UserInfo info, SalaryModel syModel) throws OwnerException {
        // 判断是否已存在数据
        if (salaryDao.getSalaryByenumber(info.getCompanyId(), syModel.getEnumber(), DateUtil.getMonthFirstDay(syModel.getMonth())) != null) {
            throw new OwnerException(ErrorEnum.SALARY_EXSIT);
        }
        Date now = new Date();
        EmployeeModel model = employeeDao.getEmployeeByenumber(info.getCompanyId(), syModel.getEnumber());
        syModel.setEname(model.getEname());
        SalaryEntity salaryEntity = setValue(now, info, syModel);
        salaryEntity.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
        salaryEntity.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
        salaryEntity.setCreateTime(now);
        salaryDao.insert(salaryEntity);
        return true;
    }

    public SalaryEntity setValue(Date now, UserInfo info, SalaryModel syModel) throws OwnerException {
        if (null != syModel.getWorkHours()) {
            if (syModel.getWorkDays().multiply(BigDecimal.valueOf(Constant.DAY_HOUR)).abs().compareTo(syModel.getWorkHours().abs()) < 0) {
                throw new OwnerException(MessageFormat.format("月份【{0}】,工号【{1}】,姓名【{2}】的总工时【{3}({4}天)】不能大于工作天数【{5}】",
                        DateUtil.format(syModel.getMonth(), "yyyy-MM"),
                        syModel.getEnumber(), syModel.getEname(),
                        syModel.getWorkHours().toString(),
                        syModel.getWorkHours().divide(BigDecimal.valueOf(Constant.DAY_HOUR), 2, BigDecimal.ROUND_HALF_UP).toString(),
                        syModel.getWorkDays().toString()));
            }
        }
        return SalaryEntity.build(now, info, syModel);
    }


    @Override
    public List<SalaryExcel> exportSalaryModel(Integer companyId, SalaryQuery salaryQuery) {
        return salaryDao.getSalaryData(companyId, salaryQuery);
    }


    @Override
    public boolean importSalary(UserInfo info, List<SalaryExcel> salaryExcels) throws OwnerException {
        if (salaryExcels.size() == 0) {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        List<String> enumberList = salaryExcels.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(SalaryExcel::getEnumber).collect(Collectors.toList());
        HashSet h = new HashSet(enumberList);
        enumberList.clear();
        enumberList.addAll(h);
        List<EmployeeEntity> employeeEntityList = enumberList.size() == 0 ? new ArrayList<>() : employeeDao.getByNumbers(info.getCompanyId(), enumberList);
        Map<String, EmployeeEntity> employeeEntityMap = employeeEntityList.stream().collect(Collectors.toMap(EmployeeEntity::getEnumber, b -> b));
        List<SalaryExcel> querySalaryModels = getTermList(salaryExcels);
        List<SalaryEntity> entities = enumberList.size() == 0 ? new ArrayList<>() : salaryDao.getDataByTerm(info.getCompanyId(), querySalaryModels);
        Map<String, SalaryEntity> entityMap = entities.stream().collect(Collectors.toMap(a -> a.getEnumber() + DateUtil.getMonthFirstDay(a.getMonth()).getTime(), b -> b, (c, d) -> d));
        Map<String, String> employeeModelMap = employeeEntityList.stream().collect(Collectors.toMap(EmployeeEntity::getEnumber, EmployeeEntity::getEname));
        for (SalaryExcel salaryExcel : salaryExcels) {
            if (employeeModelMap.containsKey(salaryExcel.getEnumber())) {
                if (!employeeModelMap.get(salaryExcel.getEnumber()).equals(salaryExcel.getEname())) {
                    throw new OwnerException(MessageFormat.format("导入数据中的工号【{0}】,姓名【{1}】,与员工花名册中的姓名【{2}】不一致，请检查修复后再提交", salaryExcel.getEnumber(), salaryExcel.getEname(), employeeModelMap.get(salaryExcel.getEnumber())));
                }
            }
        }
        List<SalaryEntity> insertSalaryEntitys = new ArrayList<>();
        List<SalaryEntity> updateSalaryEntitys = new ArrayList<>();
        Map<String, List<SalaryExcel>> excelMap = salaryExcels.stream().collect(Collectors.groupingBy(a -> a.getEnumber() + DateUtil.getMonthFirstDay(a.getMonth()).getTime()));
        List<String> fullAccountNameList = new ArrayList<>();
        salaryExcels.stream().forEach(item -> {
            if (!StringUtils.isEmpty(item.getFullAccountName())) {
                if (!item.getFullAccountName().endsWith(Constant.PATH_SEPARATOR)) {
                    item.setFullAccountName(item.getFullAccountName() + Constant.PATH_SEPARATOR);
                }
                fullAccountNameList.add(item.getFullAccountName());
            }
        });
        List<AccountTitleEntity> accountTitleEntityList = fullAccountNameList.size() == 0 ? new ArrayList<>() : accountTitleDao.getByFullAccountName(info.getCompanyId(), fullAccountNameList);
        Map<String, AccountTitleEntity> accountTitleEntityMap = accountTitleEntityList.stream().collect(Collectors.toMap(AccountTitleEntity::getFullAccountName, b -> b));
        Date now = new Date();
        for (String key : excelMap.keySet()) {
            SalaryModel salaryModel = new SalaryModel();
            BeanUtil.copyProperties(excelMap.get(key).get(excelMap.get(key).size() - 1), salaryModel);
            salaryModel.setId(0);
            EmployeeEntity employeeEntity = employeeEntityMap.get(salaryModel.getEnumber());
            if (employeeEntity == null) {
                throw new OwnerException(MessageFormat.format("工号【{0}】，姓名【{1}】不存在花名册中，请先在花名册中添加该人员。"
                        , salaryModel.getEnumber()
                        , salaryModel.getEname()));
            }
            Integer accountTitleId = -1;
            if (accountTitleEntityMap.containsKey(salaryModel.getFullAccountName())) {
                accountTitleId = accountTitleEntityMap.get(salaryModel.getFullAccountName()).getId();
            }
            salaryModel.setPayDetail(salaryModel.getPayDetail());
            salaryModel.setAccountTitleId(accountTitleId);
            SalaryEntity salaryEntity = entityMap.get(salaryModel.getEnumber() + salaryModel.getMonth().getTime());
            if (salaryEntity == null) {
                salaryEntity = setSalaryComValue(now, info, salaryModel);
                salaryEntity.setMsCreatorId(info.getMsUserId());
                salaryEntity.setCreatorId(info.getUserId());
                salaryEntity.setCreateTime(now);
                insertSalaryEntitys.add(salaryEntity);
            } else {
                salaryModel.setId(salaryEntity.getId());
                salaryEntity = setSalaryComValue(now, info, salaryModel);
                updateSalaryEntitys.add(salaryEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            if (insertSalaryEntitys.size() > 0) {
                List<List<SalaryEntity>> insertList = ListUtils.subList(insertSalaryEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<SalaryEntity> items : insertList) {
                    salaryDao.addBatch(items);
                }
            }
            if (updateSalaryEntitys.size() > 0) {
                List<List<SalaryEntity>> updateList = ListUtils.subList(updateSalaryEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<SalaryEntity> items : updateList) {
                    salaryDao.updateBatch(items);
                }
            }
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("importSalary", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    private List<SalaryExcel> getTermList(List<SalaryExcel> salaryExcels) {
        String keyFormat = "{0}_{1}";
        Map<String, SalaryExcel> map = new HashMap<>();
        salaryExcels.forEach(item -> {
            // 设置日期为1号
            item.setMonth(DateUtil.getMonthFirstDay(item.getMonth()));
            String key = MessageFormat.format(keyFormat, item.getEnumber(), DateUtil.format(item.getMonth(), DateUtil.DEFAULT_YYMM_FORMAT));
            if (map.containsKey(key)) {
                return;
            }
            map.put(key, item);
        });
        return new ArrayList<>(map.values());
    }

    public SalaryEntity setSalaryComValue(Date now, UserInfo info, SalaryModel syModel) throws OwnerException {
        if (null != syModel.getWorkHours()) {
            if (syModel.getWorkDays().multiply(BigDecimal.valueOf(Constant.DAY_HOUR)).compareTo(syModel.getWorkHours()) < 0) {
                throw new OwnerException(MessageFormat.format("月份【{0}】,工号【{1}】,姓名【{2}】的总工时【{3}({4}天)】不能大于工作天数【{5}】",
                        DateUtil.format(syModel.getMonth(), "yyyy-MM"),
                        syModel.getEnumber(), syModel.getEname(),
                        syModel.getWorkHours().toString(),
                        syModel.getWorkHours().divide(BigDecimal.valueOf(Constant.DAY_HOUR), 2, BigDecimal.ROUND_HALF_UP).toString(),
                        syModel.getWorkDays().toString()));
            }
        }
        return SalaryEntity.build(now, info, syModel);
    }

    @Override
    public boolean updateSalaryDayHours(UserInfo info, SalaryModel model) {
        List<SalaryEntity> entities = model.getSalarylist();
        int dayHours = model.getDayHours();
        for (int i = 0; i < entities.size(); i++) {
            SalaryEntity entity = entities.get(i);
            entity.setDayHours(dayHours);
        }
        updateBatchById(entities);
        return true;

    }

    @Override
    public boolean delSalary(int companyId, SalaryModel syModel) throws OwnerException {
        Integer id = syModel.getId();

        List<Integer> usedIds = salaryDao.getProjectUsed(companyId, CollUtil.newArrayList(id));
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在[创新项目-研发费用核算-数据归集]中，不能删除!");
        }
        return salaryDao.deleteById(syModel.getId()) > 0;
    }

    @Override
    public boolean delSalaryBatch(Integer companyId, List<SalaryModel> models) throws OwnerException {
        List<Integer> ids = new ArrayList<>();
        for (int i = 0; i < models.size(); i++) {
            ids.add(models.get(i).getId());
        }
        List<Integer> usedIds = salaryDao.getProjectUsed(companyId, ids);
        Collection<Integer> result = CollUtil.disjunction(usedIds, ids);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选人员已全部在【创新项目】中使用，不能删除!");
        }
        return salaryDao.deleteBatchIds(result) > 0;
    }

    @Override
    public Map<String, FieldConfigModel> getSalaryConfig(Integer companyId) {
        Map<String, FieldConfigModel> result = new HashMap<>();
        List<FieldConfigModel> data = fieldConfigDao.getSalaryConfig(companyId);
        for (FieldConfigModel m : data) {
            result.put(FieldConfigTypeEnum.getByType(m.getType()), m);
        }
        String insuranceKey = FieldConfigTypeEnum.INSURANCE.getTitle();
        if (!result.containsKey(insuranceKey)) {
            result.put(insuranceKey, fieldConfigDao.getDefault());
        }
        return result;
    }

    @Override
    public List<SalaryInfoModel> getSalaryInfos(Integer companyId, Date month, Set<String> enumbers) {
        return salaryDao.getSalaryInfos(companyId, month, enumbers);
    }
}