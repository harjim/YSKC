package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.bean.BeanUtil;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.DateUtil;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.dao.rs.FieldConfigDao;
import com.yskc.docservice.dao.rs.SalaryDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.entity.rs.SalaryEntity;
import com.yskc.docservice.enums.SalaryConfigTypeEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.SalaryExcel;
import com.yskc.docservice.models.rs.fieldconfig.FieldConfigModel;
import com.yskc.docservice.models.rs.salary.SalaryModel;
import com.yskc.docservice.service.rs.SalaryService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class SalaryServiceImpl implements SalaryService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FieldConfigDao fieldConfigDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private SalaryDao salaryDao;
    @Autowired
    private AccountTitleDao accountTitleDao;

    @Override
    public Map<String, FieldConfigModel> getSalaryConfig(Integer companyId) {
        Map<String, FieldConfigModel> result = new HashMap<>();
        List<FieldConfigModel> data = fieldConfigDao.getSalaryConfig(companyId);
        for (FieldConfigModel m : data) {
            result.put(SalaryConfigTypeEnum.getByType(m.getType()), m);
        }
        String insuranceKey = SalaryConfigTypeEnum.INSURANCE.getTitle();
        if (!result.containsKey(insuranceKey)) {
            result.put(insuranceKey, fieldConfigDao.getDefault());
        }
        return result;
    }

    @Override
    public boolean importSalary(RsUserInfo info, List<SalaryExcel> salaryExcels) throws OwnerException {
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
        List<SalaryEntity> entities = enumberList.size() == 0 ? new ArrayList<>() : salaryDao.getDataByTerm(info.getCompanyId(),querySalaryModels);
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
        Set<String> fullAccountNameList = new HashSet<>();
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
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
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("importSalary", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    private List<SalaryExcel> getTermList(List<SalaryExcel> salaryExcels) {
        String keyFormat = "{0}_{1}";
        Map<String,SalaryExcel> map = new HashMap<>();
        salaryExcels.forEach(item->{
            // 设置日期为1号
            item.setMonth(DateUtil.getMonthFirstDay(item.getMonth()));
            String key = MessageFormat.format(keyFormat,item.getEnumber(),DateUtil.format(item.getMonth(),DateUtil.DEFAULT_YYMM_FORMAT));
            if(map.containsKey(key)){
                return;
            }
            map.put(key,item);
        });
        return new ArrayList<>(map.values());
    }

    public SalaryEntity setSalaryComValue(Date now, RsUserInfo info, SalaryModel syModel) throws OwnerException {
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

}