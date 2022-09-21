package com.yskc.docservice.service.impl.rs;

import com.google.common.base.Joiner;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;

import com.yskc.docservice.config.Constant;
import com.yskc.docservice.dao.rs.DeptDao;
import com.yskc.docservice.entity.rs.Dept;
import com.yskc.docservice.models.rs.DeptModel;
import com.yskc.docservice.models.rs.RsUserInfo;
import com.yskc.docservice.models.rs.excel.DeptExcel;
import com.yskc.docservice.service.rs.DeptService;
import com.yskc.docservice.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class DeptServiceImpl implements DeptService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeptDao deptDao;


    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }

    @Override
    public String importDept(RsUserInfo info, List<DeptExcel> deptExcels) throws OwnerException {
        if (deptExcels.size() > 0) {
            Dept parentDept = deptDao.selectParentDept(info.getCompanyId());
            // 无根节点，插入一个根节点
            if (null == parentDept) {
                parentDept = Dept.build(info, new Date());
                deptDao.insert(parentDept);
                parentDept.setFullPath(parentDept.getId() + Constant.PATH_SEPARATOR);
                parentDept.setFullname(parentDept.getDeptName());
                deptDao.updateById(parentDept);
            }
            List<String> deptNameList = new ArrayList<>();
            List<String> exceldeptName = deptExcels.stream().filter(a -> !StringUtils.isEmpty(a.getDeptName())).map(DeptExcel::getDeptName).collect(Collectors.toList());
            List<String> checkDeptName = new ArrayList<>();
            List<String> repeatDeptName = new ArrayList<>();
            for (String deptName : exceldeptName) {
                deptName = trimBothEndsChars(deptName, "/");
                if (deptName.indexOf(parentDept.getDeptName() + "/") == -1) {
                    deptName = parentDept.getDeptName() + "/" + deptName;
                }
                if (!checkDeptName.contains(deptName)) {
                    checkDeptName.add(deptName);
                } else {
                    repeatDeptName.add(deptName);
                }
                deptNameList.add(deptName);
            }
            if (repeatDeptName.size() > 0) {
                String patentNoStr = Joiner.on(",").join(repeatDeptName);
                return "有重复的部门:" + patentNoStr + ",请修改后重新导入!";
            }

            List<Dept> companyDept = deptDao.queryByCompanyId(info.getCompanyId());
            Map<Integer, String> companyDeptMap = companyDept.stream().collect(Collectors.toMap(Dept::getId, Dept::getDeptName));
            Map<String, Dept> fullNameMap = new HashMap<>();
            for (Dept dept : companyDept) {
                if (dept != null && dept.getFullPath() != null) {
                    String[] deptIdArr = dept.getFullPath().split("/");
                    List<String> fullList = new ArrayList<>();
                    for (String deptId : deptIdArr) {
                        if (!StringUtils.isEmpty(deptId)) {
                            String s = companyDeptMap.get(Integer.parseInt(deptId));
                            fullList.add(s);
                        }
                    }
                    String fullNameStr = String.join("/", fullList);
                    fullNameMap.put(fullNameStr, dept);
                }
            }


//            List<String> deptNameList = deptExcels.stream().filter(a -> !StringUtils.isEmpty(a.getDeptName())).map(DeptExcel::getDeptName).collect(Collectors.toList());
            // Map<String, String> remarkMap = deptExcels.stream().collect(Collectors.toMap(DeptExcel::getDeptName, DeptExcel::getRemark));
            Map<String, String> remarkMap = new HashMap<>();
            deptExcels.forEach(item -> remarkMap.put(item.getDeptName(), item.getRemark()));
            Map<Integer, List<DeptModel>> deptNameMap = new HashMap<>();
            Map<String, Dept> deptMap = new HashMap<>();
            for (String deptName : deptNameList) {
                String[] deptNameArr = deptName.split("/");
                for (int i = 0; i < deptNameArr.length; i++) {
                    List<DeptModel> deptList = deptNameMap.get(i);

                    String fullDeptName = "";
                    if (i != 0) {
                        for (int j = 0; j < i; j++) {
                            fullDeptName += deptNameArr[j] + "/";
                        }
                    }
                    String remark = remarkMap.get(fullDeptName + deptNameArr[i]) != null ? remarkMap.get(fullDeptName + deptNameArr[i]) : "";
                    Dept dept = setDept(info, deptNameArr[i], i, remark);
                    if (fullNameMap.get(fullDeptName + deptNameArr[i]) == null) {
                        deptMap.put(fullDeptName + deptNameArr[i], dept);
                    }

                    if (deptList != null) {
                        List<String> dList = deptList.stream().filter(a -> !StringUtils.isEmpty(a.getFullDeptName())).map(DeptModel::getFullDeptName).collect(Collectors.toList());
                        if (!dList.contains(fullDeptName + deptNameArr[i])) {
                            DeptModel model = new DeptModel();
                            model.setDeptName(deptNameArr[i]);
                            model.setFullDeptName(fullDeptName + deptNameArr[i]);
                            deptList.add(model);
                            deptNameMap.put(i, deptList);
                        }
                    } else {
                        deptList = new ArrayList<>();
                        DeptModel model = new DeptModel();
                        model.setDeptName(deptNameArr[i]);
                        model.setFullDeptName(fullDeptName + deptNameArr[i]);
                        deptList.add(model);
                        deptNameMap.put(i, deptList);
                    }
                }
            }

            Map<String, Dept> insertMap = new HashMap<>();

            TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.RS);
            try {
                for (Integer level : deptNameMap.keySet()) {
                    List<Dept> insertList = new ArrayList<>();
                    List<DeptModel> deptModels = deptNameMap.get(level);
                    for (DeptModel model : deptModels) {
                        Dept dept = deptMap.get(model.getFullDeptName());
                        if (dept != null) {
                            String pFullPath = model.getFullDeptName().replace("/" + model.getDeptName(), "");
                            Dept pDept = insertMap.get(pFullPath);
                            if (pDept != null) {
                                dept.setParentId(pDept.getId());
                                dept.setFullPath(pDept.getFullPath());
                                String fullName = StringUtils.hasLength(pDept.getFullname())?pDept.getFullname().replace(parentDept.getDeptName(),""):null;
                                dept.setFullname(fullName);
                            } else {
                                Dept oldDept = fullNameMap.get(model.getFullDeptName().replace("/" + model.getDeptName(), ""));
                                if (oldDept != null) {
                                    dept.setParentId(oldDept.getId());
                                    dept.setFullPath(oldDept.getFullPath());
                                    String fullName = StringUtils.hasLength(oldDept.getFullname())?oldDept.getFullname().replace(parentDept.getDeptName(),""):null;
                                    dept.setFullname(fullName);
                                } else {
                                    dept.setParentId(parentDept.getId());
                                    dept.setFullPath(parentDept.getFullPath());
//                                    dept.setFullname(parentDept.getFullname().replace(parentDept.getDeptName(),""));
                                }
                            }

                            insertList.add(dept);
                            insertMap.put(model.getFullDeptName(), dept);
                        }
                    }
                    if (insertList.size() > 0) {
                        deptDao.addBatch(insertList);
                        for (Dept dept : insertList) {
                            dept.setFullPath(dept.getFullPath() + dept.getId() + "/");
                            dept.setFullname(!StringUtils.hasLength(dept.getFullname())?dept.getDeptName():dept.getFullname() + "/"+dept.getDeptName());
                        }
                        deptDao.updateList(insertList);
                    }

                }
                TransactionUtils.commit(DataSourceEnum.RS, transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(DataSourceEnum.RS, transactionStatus);
                throw new OwnerException(ErrorEnum.FAIL);
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }

    private Dept setDept(RsUserInfo info, String deptName, int level, String remark) {
        Dept dept = new Dept();
        dept.setDeptName(deptName);
        dept.setLevel(level);
        dept.setCompanyId(info.getCompanyId());
        dept.setParentId(0);
        dept.setIdentity("");
        dept.setCreateTime(new Date());
        dept.setLastUpdateTime(new Date());
        dept.setCreatorId(info.getUserId());
        dept.setMsCreatorId(info.getMsUserId());
        dept.setLastUpdatorId(info.getUserId());
        dept.setMsLastUpdatorId(info.getMsUserId());
        dept.setRemark(remark);
        return dept;
    }

}
