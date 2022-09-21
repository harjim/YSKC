package com.yskc.docservice.service.impl.rs;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.utils.PageUtils;
import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.AccountTitleDao;
import com.yskc.docservice.dao.rs.EmployeeDao;
import com.yskc.docservice.dao.rs.InspectionDao;
import com.yskc.docservice.entity.rs.AccountTitleEntity;
import com.yskc.docservice.entity.rs.EmployeeEntity;
import com.yskc.docservice.entity.rs.InspectionEntity;
import com.yskc.docservice.enums.CostEnum;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.InspectionExcel;
import com.yskc.docservice.service.rs.InspectionService;
import com.yskc.docservice.utils.ListUtils;
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
    public String importInspection(RsUserInfo info, List<InspectionExcel> inspectionExcels, Integer type, Integer year) throws OwnerException {
        String returnMsg = "";
        if (inspectionExcels.size() == 0) {
            return returnMsg;
        }
        Set<String> fullAccountNameList = new HashSet<>();
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
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
        try {
            if (addList.size() > 0) {
                List<List<InspectionEntity>> insertList = ListUtils.subList(addList, Constant.MAX_INSERT_OR_UPDATE_COUNT);
                for (List<InspectionEntity> items : insertList) {
                    inspectionDao.addBatch(items);
                }
            }
            TransactionUtils.commit(DataSourceEnum.RS,transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.RS,transactionStatus);
            logger.error("importInspection", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }

        return returnMsg;

    }
}
