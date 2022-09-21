package com.yskc.rs.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.DateUtil;
import com.yskc.common.utils.PageUtils;
import com.yskc.rs.config.Constant;
import com.yskc.rs.dao.DeptDao;
import com.yskc.rs.dao.EmployeeDao;
import com.yskc.rs.dao.data.DataAttendanceDao;
import com.yskc.rs.entity.Dept;
import com.yskc.rs.entity.EmployeeEntity;
import com.yskc.rs.entity.data.DataAttendanceEntity;
import com.yskc.rs.models.BatchAttendanceModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.attendance.DataAttendanceModel;
import com.yskc.rs.models.excel.AttendanceExcel;
import com.yskc.rs.service.AttendanceService;
import com.yskc.rs.utils.TransactionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 员工考勤
 * @author huronghua
 */
@Service
public class AttendanceServiceImpl implements AttendanceService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DataAttendanceDao attendanceDao;
   
    @Autowired
    private DeptDao deptDao;
    @Autowired
    private EmployeeDao employeeDao;
    /**
     * 获取员工考勤
     *
     * @param pageNo
     * @param pageSize
     * @param companyId
     * @param ename
     * @param month
     * @return
     */
    @Override
    public PageModel<List<DataAttendanceModel>> getAttendanceList(int pageNo, int pageSize, Integer companyId, String ename, Date month) {
        if (month != null) {
            month = DateUtil.getMonthFirstDay(month);
        }
        Pagination page = new Pagination(pageNo, pageSize);
        return PageUtils.build(page, attendanceDao.getAttendanceList(page, companyId, ename, month));
    }

    /**
     * 更新员工考勤
     *
     * @param attendance
     * @return
     */
    @Override
    public boolean update(DataAttendanceEntity attendance) {
        return attendanceDao.updateById(attendance) > 0;
    }

    /**
     * 删除员工信息
     *
     * @param attendance
     * @return
     */
    @Override
    public boolean delete(DataAttendanceEntity attendance) {
        return attendanceDao.deleteById(attendance.getId()) > 0;
    }

    /**
     * 添加员工考勤记录
     *
     * @param attendance
     * @return
     */
    @Override
    public boolean add(DataAttendanceEntity attendance) throws OwnerException {
        if(attendanceDao.checkRecordByMonth(attendance.getCompanyId(),attendance.getMonth(),attendance.getEnumber())>0){
            throw new OwnerException(MessageFormat.format("员工[{0}]，{1}已经存在考勤记录，请不要重复提交",
                    attendance.getEname(),
                    DateUtil.format(attendance.getMonth(),"yyyy-MM")));
        }
        return attendanceDao.insert(attendance) > 0;
    }

    /**
     * 设置部门信息
     * @param userInfo
     * @param attendanceExcel
     * @return
     */
    private Dept setDept(UserInfo userInfo,AttendanceExcel attendanceExcel) {
        Dept parentDept = deptDao.selectParentDept(userInfo.getCompanyId());
        Dept deptModel = new Dept();
        deptModel.setIdentity("");
        deptModel.setCompanyId(userInfo.getCompanyId());
        deptModel.setDeptName(attendanceExcel.getDeptName());
        deptModel.setParentId(parentDept.getId());
        deptModel.setLevel(1);
        deptModel.setFullPath(parentDept.getFullPath());
        deptModel.setRemark("员工考勤报表导入");
        deptModel.setCreateTime(new Date());
        deptModel.setCreatorId(userInfo.getUserSource()==0? userInfo.getId():-1);
        deptModel.setMsLastUpdatorId(userInfo.getMsUserId());
        deptModel.setMsCreatorId(userInfo.getMsUserId());
        deptModel.setLastUpdatorId(userInfo.getUserSource()==0?userInfo.getId():-1);
        deptModel.setLastUpdateTime(new Date());
        return deptModel;
    }

    /**
     * 设置员工信息
     * @param userInfo
     * @param attendanceExcel
     * @param deptModel
     * @return
     */
    private EmployeeEntity setEmployee(EmployeeEntity entity ,UserInfo userInfo,AttendanceExcel attendanceExcel,Dept deptModel) {
        if (entity == null) {
            entity = new EmployeeEntity();
        }
        entity.setEduLevel(0);
        entity.setDeptId(deptModel.getId());
        entity.setEname(attendanceExcel.getEname());
        entity.setIdNumber(attendanceExcel.getIdNumber());
        entity.setRemark("员工考勤报表导入");
        entity.setCompanyId(userInfo.getCompanyId());
        entity.setEdate(new Date());
        entity.setEnumber(attendanceExcel.getEnumber());
        entity.setPosition(attendanceExcel.getPosition());
        entity.setCreateTime(new Date());
        entity.setCreatorId(userInfo.getUserSource()==0? userInfo.getId():-1);
        entity.setMsLastUpdatorId(userInfo.getMsUserId());
        entity.setMsCreatorId(userInfo.getMsUserId());
        entity.setLastUpdatorId(userInfo.getUserSource()==0?userInfo.getId():-1);
        entity.setLastUpdateTime(new Date());
        return entity;
    }

    /**
     * 设置Attendance
     * @param attendance
     * @param userInfo
     * @param attendanceExcel
     * @return
     */
    private DataAttendanceEntity setAttendance(DataAttendanceEntity attendance, UserInfo userInfo, AttendanceExcel attendanceExcel) {
        if (attendance == null) {
            attendance = new DataAttendanceEntity();
        }
        attendance.setEnumber(attendanceExcel.getEnumber());
        attendance.setEname(attendanceExcel.getEname());
        attendance.setCreateTime(new Date());
        attendance.setCreatorId(userInfo.getUserSource()==0? userInfo.getId():-1);
        attendance.setMsLastUpdatorId(userInfo.getMsUserId());
        attendance.setMsCreatorId(userInfo.getMsUserId());
        attendance.setLastUpdatorId(userInfo.getUserSource()==0?userInfo.getId():-1);
        attendance.setLastUpdateTime(new Date());
        attendance.setLastUpdatorId(userInfo.getId());
        attendance.setCompanyId(userInfo.getCompanyId());
        attendance.setAttData(attendanceExcel.getAttData());
        attendance.setMonth(attendanceExcel.getMonth());
        attendance.setRemark("员工考勤报表导入");
        attendance.setRemainAttData(attendance.getAttData());
        return attendance;
    }
    /**
     * 导入员工考勤
     * @param userInfo
     * @param attendanceExcels
     * @return
     * @throws Exception
     */
    @Override
    public boolean importInfo(UserInfo userInfo, List<AttendanceExcel> attendanceExcels) throws OwnerException {
        for (AttendanceExcel attendanceExcel : attendanceExcels) {
            Date month = DateUtil.getMonthFirstDay(attendanceExcel.getMonth());
            TransactionStatus transactionStatus = TransactionUtils.newTransaction();
            Dept deptModel = deptDao.selectDeptByName(userInfo.getCompanyId(), attendanceExcel.getDeptName());
            boolean isInsertDept = false;
            boolean isInsertEmployee = false;
            boolean isInsertAttendance = false;
            if (deptModel == null) {
                deptModel = setDept(userInfo, attendanceExcel);
                isInsertDept = true;
            }else{
                deptModel.setMsLastUpdatorId(userInfo.getUserSource()==0?-1:userInfo.getId());
                deptModel.setMsCreatorId(userInfo.getUserSource()==0?-1:userInfo.getId());
                deptModel.setLastUpdateTime(new Date());
            }
            EmployeeEntity entity = employeeDao.getByNumber(userInfo.getCompanyId(), attendanceExcel.getEnumber());
            try {
                if (isInsertDept) {
                    deptDao.insert(deptModel);
                    deptModel.setFullPath(deptModel.getFullPath() + Constant.PATH_SEPARATOR + deptModel.getId());
                    deptDao.updateById(deptModel);
                }
                if (entity == null) {
                    isInsertEmployee = true;
                }
                entity = setEmployee(entity, userInfo, attendanceExcel, deptModel);
                if (isInsertEmployee) {
                    employeeDao.insert(entity);
                } else {
                    employeeDao.updateById(entity);
                }
                DataAttendanceEntity attendance = attendanceDao.selectAttendanceByMonth(userInfo.getCompanyId(), month, entity.getEnumber());
                if (attendance == null) {
                    isInsertAttendance = true;
                }
                attendance = setAttendance(attendance, userInfo, attendanceExcel);
                if (isInsertAttendance) {
                    attendanceDao.insert(attendance);
                } else {
                    attendanceDao.updateById(attendance);
                }
                TransactionUtils.commit(transactionStatus);
            } catch (Exception ex) {
                TransactionUtils.rollback(transactionStatus);
                logger.error(MessageFormat.format("公司[{2}]员工[{0}]，{1}考勤记录错误:{3}", attendanceExcel.getEname(), DateUtil.format(attendanceExcel.getMonth(), "yyyy-MM"), userInfo.getCompanyId(), ex.getMessage()));
            }
        }
        return true;
    }

    /**
     * 批量插入员工考勤记录
     * @param userInfo
     * @param model
     * @return
     * @throws OwnerException
     */
    @Override
    public boolean addList(UserInfo userInfo, BatchAttendanceModel model) throws OwnerException {
        model.getAttendances().stream().forEach(a -> {
            a.setCompanyId(userInfo.getCompanyId());
            a.setCreatorId(userInfo.getId());
            a.setCreateTime(new Date());
            a.setAttData("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
            a.setRemainAttData("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
            if (model.getMonth() != null) {
                a.setMonth(DateUtil.getMonthFirstDay(model.getMonth()));
            }
        });
        return attendanceDao.addBatch(model.getAttendances()) > 0;
    }
}
