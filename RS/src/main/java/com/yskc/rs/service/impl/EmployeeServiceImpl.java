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
import com.yskc.common.utils.RedisUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.EmployeeOpenidDao;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.enums.EduLevelEnum;
import com.yskc.rs.enums.TableEnum;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectTableModel;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.init.member.QueryInitMemberModel;
import com.yskc.rs.models.wechat.EmployeeOpenidModel;
import com.yskc.rs.service.EmployeeService;
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
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeEntity> implements EmployeeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployeeOpenidDao employeeOpenidDao;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * @param companyId
     * @param termModel
     * @return
     */
    @Override
    public PageModel<List<EmployeeModel>> queryEmployeeList(Integer companyId, EmployeeTermModel termModel) {
        Pagination page = termModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> ascs = new ArrayList<>();
            ascs.add("e.enumber");
            page.setAscs(ascs);
        }
        return PageUtils.build(page, employeeDao.queryEmployeeList(companyId, page, termModel));
    }

    /**
     * @param info
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean updateEy(UserInfo info, EmployeeModel model) throws OwnerException {
        EmployeeEntity entity = new EmployeeEntity();
        BeanUtil.copyProperties(model, entity);
        entity.setMsLastUpdatorId(info.getMsUserId());
        entity.setLastUpdatorId(info.getUserId());
        entity.setLastUpdateTime(new Date());
        boolean result;
        if (model.getOldEnumber().equals(model.getEnumber())) {
            result = employeeDao.updateById(entity) > 0;
        } else {
            // 判断是否已存在数据
            if (employeeDao.getEmployeeByenumber(info.getCompanyId(), model.getEnumber()) != null) {
                throw new OwnerException(ErrorEnum.EMPLOYEE_ENUMBER_EXSIT);
            } else {
                result = employeeDao.updateById(entity) > 0;
            }
        }
        return result;
    }

    /**
     * @param info
     * @param eyModel
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean addEy(UserInfo info, EmployeeModel eyModel) throws OwnerException {
        // 判断是否已存在数据
        if (employeeDao.getEmployeeByenumber(info.getCompanyId(), eyModel.getEnumber()) != null) {
            throw new OwnerException(ErrorEnum.EMPLOYEE_ENUMBER_EXSIT);
        }
        return employeeDao.insert(setEmployee(info, eyModel)) > 0;

    }


    /**
     * @param companyId
     * @param enumber
     * @return
     * @throws OwnerException
     */
    @Override
    public EmployeeModel getEmployeeByenumber(int companyId, String enumber) throws OwnerException {
        return employeeDao.getEmployeeByenumber(companyId, enumber);
    }


    /**
     * 设置花名册信息
     *
     * @param info
     * @param eyModel
     * @return
     */
    private EmployeeEntity setEmployee(UserInfo info, EmployeeModel eyModel) {
        EmployeeEntity employee = new EmployeeEntity();
        employee.setDeptName(eyModel.getDeptName());
        employee.setCompanyId(info.getCompanyId());
        employee.setEnumber(eyModel.getEnumber());
        employee.setEname(eyModel.getEname());
        employee.setEduLevel(eyModel.getEduLevel() != null ? eyModel.getEduLevel() : 0);
        employee.setIdNumber(eyModel.getIdNumber() != null ? eyModel.getIdNumber() : "");
        employee.setRemark(eyModel.getRemark());
        employee.setPosition(eyModel.getPosition() != null ? eyModel.getPosition() : "");
        employee.setEdate(eyModel.getEdate());
        employee.setLeaveDate(eyModel.getLeaveDate());
        employee.setBirthday(eyModel.getBirthday());
        employee.setGender(eyModel.getGender());
        employee.setTitle(eyModel.getTitle());
        employee.setSpecialities(eyModel.getSpecialities());
        employee.setMsCreatorId(info.getUserSource() == 1 ? info.getId() : -1);
        employee.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
        employee.setCreatorId(info.getUserSource() == 0 ? info.getId() : -1);
        employee.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        employee.setCreateTime(new Date());
        employee.setLastUpdateTime(new Date());
        employee.setData(eyModel.getData());
        return employee;
    }

    /**
     * @param info
     * @param employeeExcels
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean importEmployee(UserInfo info, List<EmployeeExcel> employeeExcels) throws OwnerException {
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
                employeeEntity = buildImportEmployee(companyId, msUserId, userId, now, employeeExcel, 0);
                insertEmployeeEntitys.add(employeeEntity);
            } else {
                updateEmployeeEntitys.add(setEmployeeEntity(info, employeeExcel, 0, employeeEntity));
            }
        }
        TransactionStatus transactionStatus = TransactionUtils.newTransaction();
        try {
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
            TransactionUtils.commit(transactionStatus);
        } catch (Exception ex) {
            TransactionUtils.rollback(transactionStatus);
            logger.error("importEmployee", ex);
            throw new OwnerException(ErrorEnum.FAIL);
        }
        return true;
    }

    /**
     * @param info
     * @param employeeExcel
     * @param deptId
     * @param employeeEntity
     * @return
     */
    private EmployeeEntity setEmployeeEntity(UserInfo info, EmployeeExcel employeeExcel, Integer deptId, EmployeeEntity employeeEntity) {
        String edLevel = employeeExcel.getEduLevel();
        employeeEntity.setDeptName(employeeExcel.getDeptName());
        employeeEntity.setDeptId(deptId);
        employeeEntity.setEname(employeeExcel.getEname());
        employeeEntity.setRemark(employeeExcel.getRemark());
        employeeEntity.setTitle(employeeExcel.getTitle());
        employeeEntity.setSpecialities(employeeExcel.getSpecialities());
        employeeEntity.setLeaveDate(employeeExcel.getLeaveDate());
        employeeEntity.setBirthday(employeeExcel.getBirthday());
        employeeEntity.setEduLevel(EduLevelEnum.getEduLevelEnum(edLevel).getValue() != -1 ? EduLevelEnum.getEduLevelEnum(edLevel).getValue() : -1);
        employeeEntity.setIdNumber(employeeExcel.getIdNumber() != null ? employeeExcel.getIdNumber() : "");
        employeeEntity.setPosition(employeeExcel.getPosition() != null ? employeeExcel.getPosition() : "");
        employeeEntity.setEdate(employeeExcel.getEdate());
        employeeEntity.setMsLastUpdatorId(info.getUserSource() == 1 ? info.getId() : -1);
        employeeEntity.setLastUpdatorId(info.getUserSource() == 0 ? info.getId() : -1);
        employeeEntity.setLastUpdateTime(new Date());
        Integer gender = -1;
        if (!StringUtils.isEmpty(employeeExcel.getGender())) {
            if ("女".equals(employeeExcel.getGender())) {
                gender = 1;
            }
            if ("男".equals(employeeExcel.getGender())) {
                gender = 2;
            }
        }
        employeeEntity.setGender(gender);
        return employeeEntity;
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
     * @return
     */
    private EmployeeEntity buildImportEmployee(Integer companyId, Integer msUserId, Integer userId, Date now, EmployeeExcel employeeExcel, Integer deptId) {
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
        employee.setMsCreatorId(msUserId);
        employee.setMsLastUpdatorId(msUserId);
        employee.setCreatorId(userId);
        employee.setLastUpdatorId(userId);
        employee.setCreateTime(now);
        employee.setLastUpdateTime(now);
        Integer gender = 0;
        if (!StringUtils.isEmpty(employeeExcel.getGender())) {
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


    /**
     * 获取所有员工信息
     *
     * @param companyId
     * @return
     */
    @Override
    public List<EmployeeModel> getAllList(Integer companyId) {
        return employeeDao.getAllList(companyId);
    }

    /**
     * @param compantyId
     * @param termModel
     * @return
     */
    @Override
    public List<EmployeeExcel> exportEmployeeModel(Integer compantyId, EmployeeTermModel termModel) {
        return employeeDao.getData(compantyId, termModel);
    }

    /**
     * 根据员工考勤获取员工列表
     *
     * @param companyId
     * @param ename
     * @param month
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageModel<List<EmployeeModel>> getEmployeeListByMonth(int companyId, String ename, Date month, int pageNo, int pageSize) {
        Pagination page = new Pagination(pageNo, pageSize);
        if (month != null) {
            month = DateUtil.getMonthFirstDay(month);
        }
        return PageUtils.build(page, employeeDao.getEmployeeListByMonth(companyId, month, ename, page));
    }

    /**
     * @param companyId
     * @param id
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delEy(int companyId, Integer id) throws OwnerException {
        List<Integer> ids = CollUtil.newArrayList(id);
        List<Integer> usedIds = employeeDao.getUsed(TableEnum.D_SALARY.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创想项目-研发费用核算-数据录入-员工薪资】中，不能删除!");
        }
        usedIds = employeeDao.getUsed(TableEnum.I_MEMBER.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发项目管理-项目管理-项目成员】中，不能删除!");
        }
//        usedIds = employeeDao.getUsed(TableEnum.D_BONUS.getName(), companyId, ids);
//        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
//            throw new OwnerException("已存在【创新项目-研发费用核算-数据录入-员工奖金】中，不能删除!");
//        }
//        usedIds = employeeDao.getUsed(TableEnum.P_PROJECT.getName(), companyId, ids);
//        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
//            throw new OwnerException("已存在【创新项目-研发项目管理-项目管理】中，不能删除!");
//        }
        usedIds = employeeDao.getUsed(TableEnum.RD_EMPLOYEE.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发技术中心建设-研发花名册】中，不能删除!");
        }
        usedIds = employeeDao.getUsed(TableEnum.C_ATTENDANCE.getName(), companyId, ids);
        if (!CollectionUtils.isEmpty(usedIds) && usedIds.contains(id)) {
            throw new OwnerException("已存在【创新项目-研发费用核算-数据录入-员工考勤】中，不能删除!");
        }
        return employeeDao.deleteById(id) > 0;
    }

    /**
     * @param companyId
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean delEmployeeBatch(Integer companyId, List<EmployeeModel> model) throws OwnerException {
        List<Integer> ids = new ArrayList<>();
        model.forEach(item -> ids.add(item.getId()));
        Set<Integer> usedIdSet = new HashSet<>();
        usedIdSet.addAll(employeeDao.getUsed(TableEnum.D_SALARY.getName(), companyId, ids));
        usedIdSet.addAll(employeeDao.getUsed(TableEnum.I_MEMBER.getName(), companyId, ids));
        usedIdSet.addAll(employeeDao.getUsed(TableEnum.RD_EMPLOYEE.getName(), companyId, ids));
        usedIdSet.addAll(employeeDao.getUsed(TableEnum.C_ATTENDANCE.getName(), companyId, ids));
        Collection<Integer> result = CollUtil.disjunction(usedIdSet, ids);
        if (CollectionUtils.isEmpty(result)) {
            throw new OwnerException("所选人员已全部在【创新项目】中使用，不能删除!");
        }
        return employeeDao.deleteBatchIds(result) > 0;
    }

    /**
     * @param userInfo
     * @param query
     * @return
     */
    @Override
    public PageModel<List<EmployeeSelectTableModel>> getSelectEmployeeList(UserInfo userInfo, QueryInitMemberModel query) {
        Pagination page = new Pagination(query.getPageNo(), query.getPageSize());
        return PageUtils.build(page, employeeDao.getSelectEmployeeList(page, userInfo.getCompanyId(), query));
    }


    /**
     * @param companyId
     * @param termModel
     * @return
     */
    @Override
    public PageModel<List<EmployeeModel>> selectEmployeeList(Integer companyId, EmployeeTermModel termModel) {
        Pagination page = termModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("e.enumber");
            descs.add("e.createTime");
            page.setDescs(descs);
        }
        Date firstDay = DateUtil.getYearFirstDay(termModel.getYear());
        if (!StringUtils.isEmpty(termModel.getEdate())) {
            termModel.setEdate(com.yskc.common.utils.DateUtil.getMonthFirstDay(termModel.getEdate()));
        }
        return PageUtils.build(page, employeeDao.selectEmployeeList(companyId, page, termModel, firstDay));

    }

    @Override
    public PageModel<List<EmployeeModel>> selectEmployees(Integer companyId, EmployeeTermModel termModel) {
        Pagination page = termModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("e.enumber");
            descs.add("e.createTime");
            page.setDescs(descs);
        }
        return PageUtils.build(page, employeeDao.getEmployees(companyId, page, termModel));
    }

    @Override
    public PageModel<List<EmployeeModel>> selectStEmployees(Integer companyId, EmployeeTermModel termModel) {
        Pagination page = termModel.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> descs = new ArrayList<>();
            descs.add("e.enumber");
            descs.add("e.createTime");
            page.setDescs(descs);
        }
        Date now = DateUtil.getYearFirstDay(termModel.getYear());
        return PageUtils.build(page, employeeDao.selectStEmployees(companyId, page, termModel,now));
    }

    /**
     * @param companyId
     * @param enumber
     * @param id
     * @return
     */
    @Override
    public Boolean checkEnumber(Integer companyId, String enumber, Integer id) {
        EmployeeModel model = employeeDao.getEmployeeByenumber(companyId, enumber);
        if (model == null) {
            return true;
        }
        if (model.getId().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean unbindAtt(List<Integer> ids) throws OwnerException {
        List<EmployeeOpenidModel> bindList = employeeOpenidDao.getBindList(ids);
        if (CollectionUtils.isEmpty(bindList)) {
            return true;
        }
        List<String> keys = new ArrayList<>();
        List<Integer> delIds = new ArrayList<>();
        bindList.forEach(item -> {
            keys.add(MessageFormat.format(Constant.WE_CHAT_OPENID_KEY, item.getOpenid()));
            delIds.add(item.getId());
        });
        try {
            redisUtils.del(keys);
            employeeOpenidDao.deleteBatchIds(delIds);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new OwnerException("解除绑定失败，请联系管理员。");
        }
        return true;
    }

    @Override
    public Boolean toggleAtt(List<Integer> ids, Boolean toggleAtt, UserInfo info) throws OwnerException {
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction();
            if (toggleAtt) {
                unbindAtt(ids);
            }
            employeeDao.updateDisabledAtt(ids, toggleAtt, info.getUserId(), info.getMsUserId(), new Date());
            TransactionUtils.commit(transactionStatus);
        } catch (OwnerException ow) {
            TransactionUtils.rollback(transactionStatus);
            throw ow;
        } catch (Exception e) {
            TransactionUtils.rollback(transactionStatus);
            logger.error(e.getMessage(), e);
            throw new OwnerException(toggleAtt ? "禁用失败" : "启用失败");
        }
        return true;
    }

    @Override
    public List<EmployeeModel> getEmployeeList(Integer companyId) {
        return employeeDao.getEmployeeList(companyId);
    }

    @Override
    public Boolean setRoleType(KeysAndIdsModel model, UserInfo userInfo) {
        List<Integer> ids = model.getIds();
        List<EmployeeOpenidModel> bindList = employeeOpenidDao.getBindList(ids);
        List<String> keys = new ArrayList<>();
        if (!CollectionUtils.isEmpty(bindList)) {
            bindList.forEach(item -> {
                keys.add(MessageFormat.format(Constant.WE_CHAT_OPENID_KEY, item.getOpenid()));
            });
        }
        try {
            if (!CollectionUtils.isEmpty(keys)) {
                redisUtils.del(keys);
            }
            return employeeDao.updateRoleType(model.getIds(), model.getRoleType(), userInfo.getUserId(), userInfo.getMsUserId(), new Date()) > 0;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public Boolean saveAutographUrl(Integer id, String autographUrl, UserInfo userInfo) {
        return employeeDao.updateAutographUrl(id, autographUrl, userInfo.getUserId(), userInfo.getMsUserId(), new Date()) > 0;
    }
}
