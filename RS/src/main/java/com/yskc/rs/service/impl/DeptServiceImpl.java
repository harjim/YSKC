package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.google.common.base.Joiner;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.JsonUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.DeptDao;
import com.yskc.rs.dao.ReportDao;
import com.yskc.rs.dao.UserDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.dept.DeptModel;
import com.yskc.rs.models.dept.DeptTree;
import com.yskc.rs.models.dept.DeptUserInfo;
import com.yskc.rs.models.excel.DeptExcel;
import com.yskc.rs.models.project.PlanInfo;
import com.yskc.rs.service.DeptService;
import com.yskc.rs.utils.TransactionUtils;
import org.apache.kafka.common.protocol.types.Field;
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
public class DeptServiceImpl implements DeptService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ReportDao reportDao;

    /**
     * @param userInfo
     * @return
     */
    @Override
    public List<DeptTree> queryAll(UserInfo userInfo) {
        return deptDao.queryAll(userInfo.getCompanyId());
    }

    /**
     * @param deptId
     * @param realName
     * @param page
     * @param rows
     * @return
     */
    @Override
    public PageModel<List<DeptUserInfo>> getUser(Integer deptId, String realName, Integer page, Integer rows) {
        PageModel<List<DeptUserInfo>> result = new PageModel<>();
        Pagination pagination = new Pagination(1, 10);
        pagination.setCurrent(page);
        pagination.setSize(rows);
        result.setData(deptDao.getUser(pagination, deptId, realName));
        result.setPageNo(pagination.getCurrent());
        result.setPageSize(pagination.getSize());
        result.setTotalCount(pagination.getTotal());
        result.setTotalPage(pagination.getPages());
        return result;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Boolean delUser(Integer id) {
        return userDao.deleteById(id) > 0;
    }

    /**
     * @param deptName
     * @param nodeDept
     * @param deptList
     * @return
     */
    private DeptModel getNodeDept(String deptName, DeptModel nodeDept, List<DeptModel> deptList) {
        List<DeptModel> nodeList = deptList.stream().filter(a -> a.getParentId() == nodeDept.getId().intValue()).collect(Collectors.toList());
        if (nodeList.size() == 0) {
            if (StringUtils.isEmpty(deptName) || nodeDept.getDeptName().contains(deptName)) {
                return nodeDept;
            } else {
                return null;
            }
        }
        List<DeptModel> returnList = new ArrayList<>();
        for (DeptModel deptModel : nodeList) {
            deptModel = getNodeDept(deptName, deptModel, deptList);
            if (deptModel != null) {
                returnList.add(deptModel);
            }
        }
        if (returnList.size() > 0) {
            nodeDept.setChildren(returnList);
            return nodeDept;
        } else {
            if (StringUtils.isEmpty(deptName) || nodeDept.getDeptName().contains(deptName)) {
                return nodeDept;
            } else {

                return null;
            }
        }
    }

    /**
     * @param companyId
     * @param deptName
     * @return
     */
    @Override
    public List<DeptModel> queryDept(int companyId, String deptName) {
        List<DeptModel> deptList = deptDao.queryDept(companyId, deptName);
        List<DeptModel> testList = new ArrayList<DeptModel>();
        for (DeptModel deptModel : deptList) {
            if (deptModel.getParentId() == -1) {
                deptModel = getNodeDept(deptName, deptModel, deptList);
                if (null != deptModel) {
                    testList.add(deptModel);
                }
            }
        }
        return testList;
    }

    /**
     * @param id
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delDept(Integer companyId, Integer id) throws OwnerException {
        if (id.equals(deptDao.getUsed(id))) {
            throw new OwnerException("已关联数据，不能删除!");
        }
        if (0 < deptDao.getByParentId(companyId, id)) {
            throw new OwnerException("当前部门存在子部门，不能删除!");
        }
        List<String> planInfoList = reportDao.getPlanInfoNoYear(companyId);
        if (!CollectionUtils.isEmpty(planInfoList)){
            List<Integer> deptIds = new ArrayList<>();
            for (String planInfo : planInfoList) {
                if (!StringUtils.isEmpty(planInfo)){
                    List<Integer> list = JsonUtils.jsonToList(planInfo, Integer.class);
                    deptIds.addAll(list);
                }
            }
            if (deptIds.contains(id)){
                throw new OwnerException("当前部门已参与项目规划,不能删除!");
            }
        }
        return deptDao.deleteById(id) > 0;
    }

    /**
     * @param userInfo
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean addDept(UserInfo userInfo, DeptModel deptmodel) throws OwnerException {
        if (null != deptDao.getSameLevelDept(userInfo.getCompanyId(), deptmodel.getParentId(), deptmodel.getDeptName())) {
            throw new OwnerException("同级已存在相同部门名称，请输入其他名称");
        }
        String fullPath = "";
        Date now = new Date();
        Dept dept = getDept(userInfo, deptmodel, now);
        dept.setCreatorId(userInfo.getUserSource() == 0 ? userInfo.getId() : -1);
        dept.setMsCreatorId(userInfo.getUserSource() == 0 ? -1 : userInfo.getId());
        dept.setCreateTime(now);
        if (deptmodel.getParentId() > 0) {
            Dept parent = deptDao.selectById(deptmodel.getParentId());
            if (parent != null) {
                dept.setLevel(parent.getLevel() + 1);
                fullPath = StringUtils.isEmpty(parent.getFullPath()) ? parent.getId() + Constant.PATH_SEPARATOR : parent.getFullPath();
                if (parent.getLevel()==0){
                    dept.setFullname(dept.getDeptName());
                }else {
                    dept.setFullname(StringUtils.isEmpty(parent.getFullname()) ? null : parent.getFullname()+Constant.PATH_SEPARATOR+dept.getDeptName());
                }
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
            dept.setFullPath(fullPath);
            deptDao.insert(dept);
            dept.setFullPath(StringUtils.isEmpty(fullPath) ? dept.getId() + Constant.PATH_SEPARATOR : fullPath + dept.getId() + Constant.PATH_SEPARATOR);
            deptDao.updateById(dept);
            TransactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("addDept", e);
            throw new OwnerException("保存失败，请稍后重试");
        }
        return true;
    }


    private Dept getDept(UserInfo userInfo, DeptModel deptmodel, Date now) {
        Dept dept = new Dept();
        dept.setCompanyId(userInfo.getCompanyId());
        dept.setIdentity("");
        dept.setDeptName(deptmodel.getDeptName());
        dept.setParentId(deptmodel.getParentId());
        dept.setRemark(deptmodel.getRemark());
        dept.setLastUpdatorId(userInfo.getUserId());
        dept.setLastUpdateTime(now);
        dept.setMsLastUpdatorId(userInfo.getMsUserId());
        return dept;
    }

    /**
     * @param userInfo
     * @param deptmodel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean updateDept(UserInfo userInfo, DeptModel deptmodel) throws OwnerException {
        if (deptmodel.getId() == deptmodel.getParentId()) {
            throw new OwnerException("编辑时不可选择本身为上级部门!");
        }

        Dept existDept = deptDao.getSameLevelDept(userInfo.getCompanyId(), deptmodel.getParentId(), deptmodel.getDeptName());
        if (existDept != null && !existDept.getId().equals(deptmodel.getId())) {
            throw new OwnerException("同级已存在相同部门名称，请输入其他名称");
        }
        Dept oldEntity = deptDao.selectById(deptmodel.getId());
        Dept parent = deptDao.selectById(deptmodel.getParentId());
        if (parent.getFullPath().contains(deptmodel.getId().toString())) {
            throw new OwnerException("不可编辑当前部门为其子部门的子部门。");
        }
        String fullPath = "";
        int level = 1;
        Dept dept = getDept(userInfo, deptmodel, new Date());
        if (deptmodel.getParentId() > 0) {
            if (parent != null) {
                fullPath = StringUtils.isEmpty(parent.getFullPath()) ? parent.getId() + Constant.PATH_SEPARATOR : parent.getFullPath();
                level = parent.getLevel() + 1;
                if (parent.getLevel()==0){
                    dept.setFullname(deptmodel.getDeptName());
                }else {
                    dept.setFullname(StringUtils.isEmpty(parent.getFullname())?null:parent.getFullname()+ Constant.PATH_SEPARATOR+dept.getDeptName());
                }
            }
        }
        List<Dept> updateList = new ArrayList<>();
        dept.setId(deptmodel.getId());
        dept.setLevel(level);
        dept.setFullPath(StringUtils.isEmpty(fullPath) ? dept.getId() + Constant.PATH_SEPARATOR : fullPath + dept.getId() + Constant.PATH_SEPARATOR);
        updateList.add(dept);
        List<Dept> oldChildren = deptDao.queryChildrenByTerm(userInfo.getCompanyId(), oldEntity.getFullPath(), oldEntity.getId());
        if (!CollectionUtils.isEmpty(oldChildren)) {
            String oldFullPath = oldEntity.getFullPath();
            int levelDiff = level - oldEntity.getLevel();
            for (Dept child : oldChildren) {
                String newFullPath = child.getFullPath();
                child.setFullPath(newFullPath.replace(oldFullPath, dept.getFullPath()));
                if (!StringUtils.isEmpty(child.getFullname())&&
                        !StringUtils.isEmpty(oldEntity.getFullname())&&!StringUtils.isEmpty(dept.getFullname())){
                    child.setFullname(child.getFullname().replace(oldEntity.getFullname(),dept.getFullname()));
                }
                child.setLastUpdateTime(new Date());
                child.setLevel(child.getLevel() + levelDiff);
                child.setLastUpdatorId(userInfo.getUserId());
                child.setMsLastUpdatorId(userInfo.getMsUserId());
                updateList.add(child);
            }
        }

        return deptDao.updateList(updateList) > 0;
    }


    public static String trimBothEndsChars(String srcStr, String splitter) {
        String regex = "^" + splitter + "*|" + splitter + "*$";
        return srcStr.replaceAll(regex, "");
    }

    @Override
    public String importDept(UserInfo info, List<DeptExcel> deptExcels) throws OwnerException {
        if (deptExcels.size() > 0) {
            Dept parentDept = deptDao.selectParentDept(info.getCompanyId());
            // 无根节点，插入一个根节点
            if (null == parentDept) {
                parentDept = Dept.build(info, new Date());
                deptDao.insert(parentDept);
                parentDept.setFullPath(parentDept.getId() + Constant.PATH_SEPARATOR);
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
            Map<String, String> remarkMap = deptExcels.stream().collect(Collectors.toMap(DeptExcel::getDeptName, DeptExcel::getRemark));
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

            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
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
                            } else {
                                Dept oldDept = fullNameMap.get(model.getFullDeptName().replace("/" + model.getDeptName(), ""));
                                if (oldDept != null) {
                                    dept.setParentId(oldDept.getId());
                                    dept.setFullPath(oldDept.getFullPath());
                                } else {
                                    dept.setParentId(parentDept.getId());
                                    dept.setFullPath(parentDept.getFullPath());
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
                        }
                        deptDao.updateList(insertList);
                    }

                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                TransactionUtils.rollback(transactionStatus);
                throw new OwnerException(ErrorEnum.FAIL);
            }
        } else {
            throw new OwnerException("未获取到任何数据，请检查表格是否存在数据，上传模板是否正确。");
        }
        return "";
    }

    private Dept setDept(UserInfo info, String deptName, int level, String remark) {
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
