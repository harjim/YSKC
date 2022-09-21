package com.yskc.docservice.service.impl.rs;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yskc.common.enums.AuditModeEnum;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.dao.rs.RdEmployeeDao;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.entity.rs.RdEmployeeEntity;
import com.yskc.docservice.enums.EmployeeTypeEnum;
import com.yskc.docservice.enums.OrgEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.ImportEmployeeExcel;
import com.yskc.docservice.service.rs.RdEmployeeService;
import com.yskc.docservice.utils.ListUtils;
import com.yskc.docservice.utils.ToolUtils;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service.impl
 * @Author: wangxing
 * @CreateTime: 2019-11-18 14:38
 * @Description: RdEmployeeServiceImpl
 */
@Service
public class RdEmployeeServiceImpl extends ServiceImpl<RdEmployeeDao, RdEmployeeEntity> implements RdEmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RdEmployeeDao rdEmployeeDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private EmployeeDao employeeDao;



    @Override
    public String importEmployee(RsUserInfo info, List<ImportEmployeeExcel> employeeExcels, Integer year) throws OwnerException {
        commonService.checkAuditModify(info.getCompanyId(), year, AuditModeEnum.RD_EMPLOYEE, info.getUserSource());
        if (CollectionUtils.isEmpty(employeeExcels)) {
            throw new OwnerException("文件为空,请添加数据后再导入");
        }
        Map<String, Integer> rdDeptMap = commonService.initOrgFullPathMap(info.getCompanyId(), OrgEnum.RD_DEPT, year);
        //查询存在的研发人员列表
        List<String> enumberList = employeeExcels.stream().filter(a -> !StringUtils.isEmpty(a.getEnumber())).map(ImportEmployeeExcel::getEnumber).collect(Collectors.toList());
        List<RdEmployeeEntity> existRdEnumers = rdEmployeeDao.getListByEnumbers(info.getCompanyId(), year, enumberList);
        Map<String, RdEmployeeEntity> existOrRepeatMap = new HashMap<>();
        existRdEnumers.forEach(item -> {
            existOrRepeatMap.put(item.getEnumber(), item);
        });
        List<EmployeeEntity> employeeModelList = employeeDao.getByNumbers(info.getCompanyId(), enumberList);
        Map<String, Integer> existEmployeeMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(employeeModelList)) {
            employeeModelList.forEach(item -> existEmployeeMap.put(item.getEnumber(), item.getId()));
        }
        //插入数据
        List<RdEmployeeEntity> addRdEmployeeEntitys = new ArrayList<>();
        List<RdEmployeeEntity> updateRdEmployees = new ArrayList<>();
        List<EmployeeEntity> insertEmployeeList = new ArrayList<>();
        Date date = new Date();
        int max = employeeExcels.size();
        Map<String, Object> repeatMap = new HashMap<>();
        for (int i = 0; i < max; i++) {
            ImportEmployeeExcel excels = employeeExcels.get(i);
            String enumber = excels.getEnumber();
            if (repeatMap.containsKey(enumber)) {
                continue;
            }
            repeatMap.put(enumber, null);
            Integer rdDeptId = ToolUtils.getOrgId(rdDeptMap, excels.getRdDeptName().trim());
            if (rdDeptId == null) {
                throw new OwnerException(MessageFormat.format(Constant.IMPORT_DEPT_ERROR,
                        i + 2, excels.getRdDeptName()));
            }
            if (!existEmployeeMap.containsKey(enumber)) {
                insertEmployeeList.add(EmployeeEntity.build(info, date, 0, excels.getEname(), enumber, excels.getRdDeptName()));
            }
            if (StringUtils.isEmpty(excels.getTypeValue())) {
                throw new OwnerException("工号：" + enumber + "，姓名：" + excels.getEname() + "的人员类型不能为空！");
            }
            EmployeeTypeEnum employeeTypeEnum = EmployeeTypeEnum.getEmployeeTypeEnum(excels.getTypeValue());
            if (employeeTypeEnum == EmployeeTypeEnum.ORDINARY_EMPLOYEES) {
                throw new OwnerException("工号：" + enumber + "，姓名：" + excels.getEname() + "的人员类型应为研究人员，技术人员或辅助人员！");
            }
            if (existOrRepeatMap.containsKey(enumber)) {
                RdEmployeeEntity rdEmployeeEntity = existOrRepeatMap.get(enumber);
                rdEmployeeEntity.setEtype(employeeTypeEnum.getValue());
                rdEmployeeEntity.setRdDeptId(rdDeptId);
                rdEmployeeEntity.setPosition(excels.getPosition());
                rdEmployeeEntity.setLastUpdateTime(date);
                rdEmployeeEntity.setMsLastUpdatorId(info.getMsUserId());
                rdEmployeeEntity.setLastUpdatorId(info.getUserId());
                updateRdEmployees.add(rdEmployeeEntity);
            } else {
                RdEmployeeEntity rdEmployeeEntity = new RdEmployeeEntity();
                rdEmployeeEntity.setCreateTime(date);
                rdEmployeeEntity.setCreatorId(info.getUserId());
                rdEmployeeEntity.setEnumber(enumber);
                rdEmployeeEntity.setPosition(excels.getPosition());
                rdEmployeeEntity.setLastUpdateTime(date);
                rdEmployeeEntity.setLastUpdatorId(info.getUserId());
                rdEmployeeEntity.setYear(year);
                rdEmployeeEntity.setCompanyId(info.getCompanyId());
                rdEmployeeEntity.setEtype(employeeTypeEnum.getValue());
                rdEmployeeEntity.setMsCreatorId(info.getMsUserId());
                rdEmployeeEntity.setMsLastUpdatorId(info.getMsUserId());
                rdEmployeeEntity.setRdDeptId(rdDeptId);
                addRdEmployeeEntitys.add(rdEmployeeEntity);
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (addRdEmployeeEntitys.size() > 0) {
                for (List<RdEmployeeEntity> inserts : ListUtils.subList(addRdEmployeeEntitys, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEmployeeDao.saveRdEmployeeLists(inserts);
                }
            }
            if (!CollectionUtils.isEmpty(updateRdEmployees)) {
                for (List<RdEmployeeEntity> updates : ListUtils.subList(updateRdEmployees, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    rdEmployeeDao.updateList(updates);
                }
            }
            if (!CollectionUtils.isEmpty(insertEmployeeList)) {
                for (List<EmployeeEntity> inserts : ListUtils.subList(insertEmployeeList, Constant.MAX_INSERT_OR_UPDATE_COUNT)) {
                    employeeDao.addBatch(inserts);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            throw new OwnerException("导入研发花名册失败");
        }
        return "";
    }
}
