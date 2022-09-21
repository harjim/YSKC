package com.yskc.docservice.service.impl.rs;

import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.enums.EduLevelEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.EmployeeExcel;
import com.yskc.docservice.service.rs.EmployeeService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.TransactionUtils;
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
 * @author Administrator
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * @param info
     * @param employeeExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importEmployee(RsUserInfo info, List<EmployeeExcel> employeeExcels) throws OwnerException {
        if (employeeExcels.size() == 0) {
            return true;
        }
        Map<String, EmployeeExcel> removeExcels = new LinkedHashMap<>();
        employeeExcels.forEach(item -> {
            removeExcels.put(item.getEnumber(), item);
        });
        if (!CollectionUtils.isEmpty(removeExcels)) {
            employeeExcels = new ArrayList<>();
            for (EmployeeExcel e : removeExcels.values()) {
                employeeExcels.add(e);
            }
        } else {
            return true;
        }
        List<String> enumberList = employeeExcels.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(EmployeeExcel::getEnumber).collect(Collectors.toList());
        List<EmployeeEntity> employeeEntityList = enumberList.size() == 0 ? new ArrayList<>() : employeeDao.getByNumbers(info.getCompanyId(), enumberList);
        Map<String, EmployeeEntity> employeeEntityMap = employeeEntityList.stream().collect(Collectors.toMap(EmployeeEntity::getEnumber, b -> b));
        List<EmployeeEntity> insertEmployeeEntitys = new ArrayList<>();
        List<EmployeeEntity> updateEmployeeEntitys = new ArrayList<>();
        Integer companyId = info.getCompanyId(), msUserId = info.getMsUserId(), userId = info.getUserId();
        Date now = new Date();
        for (int i = 0; i < employeeExcels.size(); i++) {
            EmployeeExcel employeeExcel = employeeExcels.get(i);
            EmployeeEntity employeeEntity = employeeEntityMap.get(employeeExcel.getEnumber());
            if (employeeEntity == null) {
                employeeEntity = buildImportEmployee(companyId, msUserId, userId, now, employeeExcel, 0, null);
                insertEmployeeEntitys.add(employeeEntity);
            } else {
                updateEmployeeEntitys.add(buildImportEmployee(companyId, msUserId, userId, now, employeeExcel, 0, employeeEntity.getId()));
            }
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            if (insertEmployeeEntitys.size() > 0) {
                List<List<EmployeeEntity>> insertList = ListUtils.subList(insertEmployeeEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<EmployeeEntity> items : insertList) {
                    employeeDao.addBatch(items);
                }
            }
            if (updateEmployeeEntitys.size() > 0) {
                List<List<EmployeeEntity>> updateList = ListUtils.subList(updateEmployeeEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<EmployeeEntity> items : updateList) {
                    employeeDao.updateBatch(items);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
            logger.error("importEmployee", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * 导入人员build
     *
     * @param companyId
     * @param msUserId
     * @param userId
     * @param now
     * @param employeeExcel
     * @param deptId
     * @Param id
     * @return
     */
    private EmployeeEntity buildImportEmployee(Integer companyId, Integer msUserId, Integer userId, Date now, EmployeeExcel employeeExcel, Integer deptId,
                                               Integer id) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setDeptName(employeeExcel.getDeptName());
        employee.setCompanyId(companyId);
        employee.setEnumber(employeeExcel.getEnumber());
        employee.setEname(employeeExcel.getEname());
        employee.setDeptId(deptId);
        String edLevel = employeeExcel.getEduLevel();
        employee.setRemark(employeeExcel.getRemark());
        employee.setTitle(employeeExcel.getTitle());
        employee.setSpecialities(employeeExcel.getSpecialities());
        employee.setBirthday(employeeExcel.getBirthday());
        employee.setEduLevel(EduLevelEnum.getEduLevelEnum(edLevel).getValue() != -1 ? EduLevelEnum.getEduLevelEnum(edLevel).getValue() : -1);
        employee.setIdNumber(employeeExcel.getIdNumber() != null ? employeeExcel.getIdNumber() : "");
        employee.setPosition(employeeExcel.getPosition() != null ? employeeExcel.getPosition() : "");
        employee.setEdate(employeeExcel.getEdate());
        employee.setData(employeeExcel.getData());
        employee.setMsLastUpdatorId(msUserId);
        if (id == null) {
            employee.setMsCreatorId(msUserId);
            employee.setCreatorId(userId);
            employee.setCreateTime(now);
        } else {
            employee.setId(id);
        }
        employee.setLastUpdatorId(userId);
        employee.setLastUpdateTime(now);
        Integer gender = 0;
        if (StringUtils.hasLength(employeeExcel.getGender())) {
            if ("女".equals(employeeExcel.getGender())) {
                gender = 1;
            }
            if ("男".equals(employeeExcel.getGender())) {
                gender = 2;
            }
        }
        employee.setGender(gender);
        employee.setLeaveDate(employeeExcel.getLeaveDate());
        return employee;
    }
}
