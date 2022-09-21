package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.enums.ErrorEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.Md5Util;
import com.yskc.common.utils.PageUtils;
import com.yskc.common.utils.RedisUtils;
import com.yskc.ms.config.Constant;
import com.yskc.ms.dao.ms.*;
import com.yskc.ms.dao.rs.ManagerUserDao;
import com.yskc.ms.entity.ms.Dept;
import com.yskc.ms.entity.ms.User;
import com.yskc.ms.entity.ms.UserDept;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptModel;
import com.yskc.ms.models.dept.DeptTree;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.dept.InsertUserDeptModel;
import com.yskc.ms.models.dingding.Department;
import com.yskc.ms.models.dingding.DingUser;
import com.yskc.ms.models.user.QueryUserListModel;
import com.yskc.ms.service.ms.DeptService;
import com.yskc.ms.utils.DingUtils;
import com.yskc.ms.utils.TransactionUtils;
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
 * @author Administrator
 */
@Service
public class DeptServiceImpl implements DeptService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DeptDao deptDao;

    @Autowired
    private UserDao userDao;
    @Autowired
    private DingUtils dingUtils;
    @Autowired
    private UserDeptDao userDeptDao;
    @Autowired
    private DingUserDao dingUserDao;
    @Autowired
    private ManagerUserDao managerUserDao;
    @Autowired
    private AppUserRoleDao appUserRoleDao;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<DeptTree> queryAll() {
        return deptDao.queryAll();
    }

    @Override
    public PageModel<List<DeptUserInfo>> getUser(QueryUserListModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("y.createTime");
            page.setDescs(desc);
        }
        List<DeptUserInfo> userList = deptDao.getUser(page, query, perm);
        for (DeptUserInfo user : userList) {
            if (user.getDeptIdArrStr() != null) {
                user.setDeptIdArr(user.getDeptIdArrStr().split(","));
            }
        }
        return PageUtils.build(page, userList);
    }

    @Override
    public Boolean delUser(Integer currentUserId, Integer id) throws OwnerException {
        if (currentUserId.equals(id)) {
            throw new OwnerException(ErrorEnum.USER_DEL_SELF);
        }
        TransactionStatus status = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            dingUserDao.deleteByUserId(id);
            userDeptDao.deleteByUserId(id);
            managerUserDao.deleteByUserId(id);
            appUserRoleDao.deleteByUserId(id);
            userDao.deleteById(id);
            TransactionUtils.commit(DataSourceEnum.MS, status);
            return true;
        } catch (Exception ex) {
            TransactionUtils.rollback(DataSourceEnum.MS, status);
            throw new OwnerException(ErrorEnum.FAIL, ex.getMessage());
        }
    }

    @Override
    public List<DeptModel> queryDept(String deptName) {
        List<DeptModel> deptList = deptDao.queryDept(deptName);
        List<DeptModel> testList = new ArrayList<DeptModel>();
        for (DeptModel deptModel : deptList) {
            if (deptModel.getParentId() == -1) {
                deptModel = getNodeDept(deptName, deptModel, deptList);
                if (deptModel != null) {
                    testList.add(deptModel);
                }
            }

        }
        return testList;
    }

    private DeptModel getNodeDept(String deptName, DeptModel nodeDept, List<DeptModel> deptList) {
        List<DeptModel> nodeList = deptList.stream().filter(a -> a.getParentId() == nodeDept.getId().intValue())
                .collect(Collectors.toList());
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

    @Override
    public boolean delDept(DeptModel deptmodel) throws OwnerException {
        if (deptDao.getUsed(deptmodel.getId()) > 0) {
            throw new OwnerException("已关联数据，不能删除!");
        }
        if (deptDao.getByParentId(deptmodel.getId()) > 0) {
            throw new OwnerException("该部门有子部门不能进行删除操作!");
        }
        return deptDao.deleteById(deptmodel.getId()) > 0;
    }

    @Override
    public boolean updateDept(DeptModel deptmodel) throws OwnerException {
        if (deptmodel.getId().equals(deptmodel.getParentId())) {
            throw new OwnerException("编辑时不可选择本身为上级部门!");
        }
        Dept existDept = deptDao.getExist(deptmodel.getParentId(), deptmodel.getDeptName());
        if (null != existDept && !existDept.getId().equals(deptmodel.getId())) {
            throw new OwnerException("同级已存在相同部门名称，请输入其他名称");
        }
        Dept parentDept = deptDao.selectById(deptmodel.getParentId());
        if (parentDept.getFullPath().contains(deptmodel.getId().toString())) {
            throw new OwnerException("不可编辑当前部门为其子部门的子部门。");
        }
        Dept rawDept = deptDao.selectById(deptmodel.getId());
        int level = 1;
        String fullPath = "";
        level = parentDept.getLevel() + 1;
        fullPath = parentDept.getFullPath();
        Dept dept = new Dept();
        dept.setId(deptmodel.getId());
        dept.setDeptName(deptmodel.getDeptName());
        dept.setParentId(deptmodel.getParentId());
        if (dept.getParentId()!=1){
            dept.setFullname(parentDept.getFullname()+ Constant.PATH_SEPARATOR+dept.getDeptName());
        }else {
            dept.setFullname(dept.getDeptName());
        }
        dept.setRemark(deptmodel.getRemark());
        dept.setLevel(level);
        dept.setFullPath(fullPath + dept.getId() + Constant.PATH_SEPARATOR);
        Integer branchId = Integer.valueOf(dept.getFullPath().split(Constant.PATH_SEPARATOR)[1]);
        dept.setBranchId(branchId);
        List<Dept> updateList = new ArrayList();
        updateList.add(dept);
        int levelDiff = level - deptmodel.getLevel();
        List<Dept> children = deptDao.getChildren(deptmodel.getFullPath(), dept.getId());
        if (!CollectionUtils.isEmpty(children)) {
            children.forEach(item -> {
                item.setFullPath(item.getFullPath().replace(deptmodel.getFullPath(), dept.getFullPath()));
                item.setFullname(item.getFullname().replace(rawDept.getFullname(), dept.getFullname()));
                item.setLevel(item.getLevel() + levelDiff);
                item.setBranchId(branchId);
                updateList.add(item);
            });
        }
        return deptDao.updateBatch(updateList) > 0;
    }

    @Override
    public boolean addDept(DeptModel deptmodel) throws OwnerException {
        Dept existDept = deptDao.getExist(deptmodel.getParentId(), deptmodel.getDeptName());
        if (null != existDept) {
            throw new OwnerException("同级已存在相同部门名称，请输入其他名称");
        }
        Dept dept = getDept(deptmodel);
        Dept parentDept = deptDao.selectById(deptmodel.getParentId());
        dept.setLevel(parentDept.getLevel() + 1);
        if (parentDept.getLevel() == 0) {
            dept.setFullname(dept.getDeptName());
            dept.setBranchId(0);
        } else {
            dept.setBranchId(Integer.valueOf(parentDept.getFullPath().split(Constant.PATH_SEPARATOR)[1]));
            dept.setFullname(parentDept.getFullname() + Constant.PATH_SEPARATOR + dept.getDeptName());
        }
        deptDao.insert(dept);
        if (dept.getBranchId() == 0) {
            dept.setBranchId(dept.getId());
        }
        dept.setFullPath(dept.getFullPath() + dept.getId() + Constant.PATH_SEPARATOR);
        return deptDao.updateById(dept) > 0;
    }

    private Dept getDept(DeptModel deptmodel) {
        Dept dept = new Dept();
        dept.setCreateTime(new Date());
        dept.setFullPath(deptmodel.getFullPath());
        dept.setIdentity("");
        dept.setDeptName(deptmodel.getDeptName());
        dept.setParentId(deptmodel.getParentId());
        dept.setRemark(deptmodel.getRemark());
        return dept;
    }

    @Override
    public boolean synDingUser(UserInfo info) throws OwnerException {
        List<Department> departments = dingUtils.getAllDepartmentList();
        Map<Integer, Dept> deptMap = new HashMap<>();
        //钉钉用户部门关联数据
        List<UserDept> userDepts = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        // 钉钉用户集合,以钉钉的userId为key
        Map<String, User> dUserIdMap = new HashMap<>();
        for (Department department :
                departments) {
            Dept dept = new Dept();
            dept.setId(department.getId());
            dept.setDeptName(department.getName());
            dept.setCreatorId(-1);
            dept.setCreateTime(new Date());
            dept.setIdentity("");
            if (StringUtils.isEmpty(department.getParentid())) {
                dept.setParentId(-1);
            } else {
                Integer parentId = Integer.parseInt(department.getParentid());
                dept.setParentId(parentId);
            }
            List<DingUser> tmpDingUsers = dingUtils.getDingUserList(department.getId());
            for (DingUser dingUser :
                    tmpDingUsers) {
                UserDept userDept = new UserDept();
                userDept.setUserId(-1);
                userDept.setDingUserId(dingUser.getUserid());
                userDept.setAdmin(dingUser.getIsLeader());
                userDept.setDepId(department.getId());
                userDept.setUnionid(dingUser.getUnionid());
                userDepts.add(userDept);
                if (dUserIdMap.get(dingUser.getUserid()) == null) {
                    User user = new User();
                    user.setUserName(dingUser.getMobile());
                    String newPassword = Md5Util.encrypt(MessageFormat.format("{0}{1}", dingUser.getMobile(), "123456"));
                    user.setPassword(newPassword);
                    user.setRealName(dingUser.getName());
                    user.setTel(dingUser.getMobile());
                    user.setDepId(dingUser.getDepartment().get(0) + "");
                    user.setPostion(dingUser.getPosition());
                    user.setStatus(0);
                    user.setAvatar("");
                    user.setCreatorId(info.getId());
                    user.setCreateTime(new Date());
                    user.setRemark("钉钉同步");
                    user.setReSetPassword(false);
                    user.setUnionid(dingUser.getUnionid());
                    user.setDingUserId(dingUser.getUserid());
                    dUserIdMap.put(dingUser.getUserid(), user);
                    userList.add(user);
                }
            }
            deptMap.put(dept.getId(), dept);
        }
        for (Dept dept :
                deptMap.values()) {
            Dept tmpDept = dept;
            String fullName = tmpDept.getDeptName();
            String fullPath = tmpDept.getId() + Constant.PATH_SEPARATOR;
            Integer branchId = tmpDept.getId();
            int level = 0;
            while (tmpDept.getParentId() != -1) {
                tmpDept = deptMap.get(tmpDept.getParentId());
                fullPath = tmpDept.getId() + Constant.PATH_SEPARATOR + fullPath;
                // 全名称不加根部门名称
                if (tmpDept.getParentId() != -1)
                    fullName = tmpDept.getDeptName() + Constant.PATH_SEPARATOR + fullName;
                // 到第一级部门,设置branchId
                if (tmpDept.getParentId() == 1) {
                    branchId = tmpDept.getId();
                }
                level++;
            }
            dept.setFullname(fullName);
            dept.setFullPath(fullPath);
            dept.setLevel(level);
            dept.setBranchId(branchId);
        }

        // 获取现有的用户部门数据,并转成map方便处理与钉钉数据比较
        List<UserDept> oldUserDepts = userDeptDao.getDDUserDept();
        Map<Integer,Map<Integer,UserDept>> udMap = new HashMap<>();
        for (UserDept ud:
                oldUserDepts) {
            Integer uId = ud.getUserId();
            Integer dId = ud.getDepId();
            if (udMap.get(uId)==null){
                udMap.put(uId,new HashMap<>());
            }
            udMap.get(uId).put(dId,ud);
        }

        List<String> redisRoleKeys = new ArrayList<>();
        List<String> sessionKeys = new ArrayList<>();
        TransactionStatus transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
        try {
            // 同步用户数据
            userDao.insertOrUpdate(userList);
            List<User> users = userDao.queryUserAll();
            List<Integer> offUserIds = new ArrayList<>();
            List<Integer> notSynUserIds = new ArrayList<>();
            for (User user:
                 users) {
                // 用表中所有从钉钉的同步过来的用户
                if (!StringUtils.isEmpty(user.getDingUserId())){
                    User nUser = dUserIdMap.get(user.getDingUserId());
                    // 如果不在现有的用户,则表示当前用户离职了
                    if (nUser==null){
                        offUserIds.add(user.getId());
                    } else {
                        nUser.setId(user.getId());
                        // 设置用户token,当用户更换部门时,需要移除用户session.
                        nUser.setToken(user.getToken());
                    }
                    if (user.getNotSyndd()){
                        notSynUserIds.add(user.getId());
                    }
                }
            }
            // 设置用户系统ID
            List<UserDept> synUserDeptList = new ArrayList<>();
            // 保留的用户部门数据Id
            List<Integer> retainIds = new ArrayList<>();
            // 不需要同步的部门数据不变
            for (Integer notSynUserId:
                 notSynUserIds) {
                if (udMap.get(notSynUserId)!=null){
                    for (UserDept ud:
                    udMap.get(notSynUserId).values()) {
                        retainIds.add(ud.getId());
                    }
                }
            }
            // 离职的用户保留原来的部门
            for (Integer offUserId:
                    offUserIds) {
                if (udMap.get(offUserId)!=null){
                    for (UserDept ud:
                            udMap.get(offUserId).values()) {
                        retainIds.add(ud.getId());
                    }
                }
            }
            List<UserDept> adminUserDeptList = new ArrayList<>();
            for (UserDept userDept:
                 userDepts) {
                User nUser = dUserIdMap.get(userDept.getDingUserId());
                if (!notSynUserIds.contains(nUser.getId())){
                    userDept.setUserId(nUser.getId());
                    if (udMap.get(nUser.getId())==null || udMap.get(nUser.getId()).get(userDept.getDepId())==null) {
                        // 需要新增的数据,表示用户部门有变动或增加
                        synUserDeptList.add(userDept);
                        // 钉钉现有用户,清除角色缓存
                        String roleKey = MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, nUser.getId());
                        if (!redisRoleKeys.contains(roleKey))
                            redisRoleKeys.add(MessageFormat.format(Constant.REDIS_KEY_MS_USER_ROLE, nUser.getId()));
                        // 添加需要移除的session
                        if (nUser.getToken()!=null && nUser.getToken()!=""){
                            String sKey = MessageFormat.format(Constant.REDIS_KEY_SESSION, nUser.getToken());
                            if (!sessionKeys.contains(sKey))
                                sessionKeys.add(sKey);
                        }
                    } else {
                        // 取未改变的数据的userDeptId
                        retainIds.add(udMap.get(nUser.getId()).get(userDept.getDepId()).getId());
                    }
                    // 获取所有主管数据
                    if (userDept.getAdmin()){
                        adminUserDeptList.add(userDept);
                    }
                }
            }
            // 将离职用户的状态改为禁用
            if (offUserIds.size()>0){
                userDao.updateStatus(offUserIds);
            }
            // 同步用户关联部门数据
            if (synUserDeptList.size()>0 || retainIds.size()>0){
                userDeptDao.updateDDUserDept(retainIds,synUserDeptList);
            }
            // 同步部门主管数据
            if (adminUserDeptList.size()>0 ){
                userDeptDao.updateDDUserAdmin(notSynUserIds,adminUserDeptList);
            }

            // 同步部门数据
            if (deptMap.size()>0){
                deptDao.updateDDDept(deptMap.values());
            }
            // 同步钉钉用户表数据
            dingUserDao.synDDUser();

            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
            throw new OwnerException(ErrorEnum.FAIL);
        } finally {
            redisUtils.del(redisRoleKeys);
            redisUtils.del(sessionKeys);
        }

        return true;
    }

    @Override
    public List<Integer> queryDeptIds() {
        return deptDao.queryDeptIds();
    }


    @Override
    public PageModel<List<DeptUserInfo>> getUserDataByDeptId(int pageNo, int pageSize, String realName, Integer deptId) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, deptDao.getUserDataByDeptId(page, realName, deptId));
    }

    @Override
    public PageModel<List<DeptUserInfo>> getSelectUserData(int pageNo, int pageSize, String realName, Integer deptId, String fullPath) {
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, deptDao.getSelectUserData(page, realName, deptId, fullPath));
    }

    @Override
    public boolean deptAddUser(InsertUserDeptModel model) throws OwnerException {
        List<UserDept> insertList = new ArrayList<>();
        model.getUserIds().forEach(a -> {
            UserDept userDept = new UserDept();
            userDept.setUserId(a);
            userDept.setDepId(model.getDeptId());
            userDept.setAdmin(false);
            userDept.setUnionid("");
            userDept.setDingUserId("");
            insertList.add(userDept);
        });
        try {
            if (insertList.size() > 0) {
                userDeptDao.insertBatch(insertList);
            }
        } catch (Exception ex) {
            throw new OwnerException("部门批量添加用户失败" + ex);
        }
        return true;
    }

    @Override
    public boolean deptAddPrincipal(InsertUserDeptModel model,Boolean addTo) throws OwnerException {
        try {
            if (model.getUserIds().size() > 0 && null!=model.getDeptId()) {
                List<Integer> userIds = model.getUserIds();
                if (!addTo){
                    List<Integer> principal = userDeptDao.getPrincipal(model.getDeptId());
                    if (!CollectionUtils.isEmpty(principal)){
                        userDeptDao.updateByUserIds(principal, model.getDeptId(), 0);
                    }
                }
                userDeptDao.updateByUserIds(userIds, model.getDeptId(),1);
            }
        } catch (Exception ex) {
            throw new OwnerException("部门批量设置负责人失败" + ex);
        }
        return true;
    }

    @Override
    public boolean deleteDeptUser(List<DeptUserInfo> deptUserInfos) {
        List<Integer> userIds = deptUserInfos.stream().map(DeptUserInfo::getId).collect(Collectors.toList());
        Integer deptId = deptUserInfos.get(0).getDepId();
        return userDeptDao.deleteDeptUser(userIds, deptId);
    }
}
