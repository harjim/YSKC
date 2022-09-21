package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.KeysAndIdsModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.employee.EmployeeModel;
import com.yskc.rs.models.employee.EmployeeSelectTableModel;
import com.yskc.rs.models.employee.EmployeeTermModel;
import com.yskc.rs.models.excel.EmployeeExcel;
import com.yskc.rs.models.init.member.QueryInitMemberModel;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
public interface EmployeeService {
    /**
     * 分页查询花名册
     *
     * @param companyId
     * @param termModel
     * @return
     */
    PageModel<List<EmployeeModel>> queryEmployeeList(Integer companyId, EmployeeTermModel termModel);

    /**
     * 删除花名册
     *
     * @param companyId
     * @param id
     * @return
     * @throws OwnerException
     */
    boolean delEy(int companyId, Integer id) throws OwnerException;

    /**
     * 修改花名册
     *
     * @param info
     * @param eyModel
     * @return
     * @throws OwnerException
     */
    boolean updateEy(UserInfo info, EmployeeModel eyModel) throws OwnerException;

    /**
     * 新增花名册
     *
     * @param info
     * @param eyModel
     * @return
     * @throws OwnerException
     */
    boolean addEy(UserInfo info, EmployeeModel eyModel) throws OwnerException;

    /**
     * 查询工号的唯一性
     *
     * @param companyId
     * @param enumber
     * @return
     * @throws OwnerException
     */
    EmployeeModel getEmployeeByenumber(int companyId, String enumber) throws OwnerException;

    /**
     * 导入数据
     *
     * @param info
     * @param employeeExcels
     * @return
     * @throws OwnerException
     */
    boolean importEmployee(UserInfo info, List<EmployeeExcel> employeeExcels) throws OwnerException;

    /**
     * 获取所有员工信息
     *
     * @param companyId
     * @return
     */
    List<EmployeeModel> getAllList(Integer companyId);

    /**
     * 导出数据
     *
     * @param compantyId
     * @param termModel
     * @return
     */
    List<EmployeeExcel> exportEmployeeModel(Integer compantyId, EmployeeTermModel termModel);

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
    PageModel<List<EmployeeModel>> getEmployeeListByMonth(int companyId, String ename, Date month, int pageNo, int pageSize);


    /**
     * 批量删除
     *
     * @param companyId
     * @param model
     * @return
     * @throws OwnerException
     */
    boolean delEmployeeBatch(Integer companyId, List<EmployeeModel> model) throws OwnerException;

    /**
     * 人员下拉表格
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<EmployeeSelectTableModel>> getSelectEmployeeList(UserInfo userInfo, QueryInitMemberModel query);

    /**
     * @param companyId
     * @param termModel
     * @return
     */
    PageModel<List<EmployeeModel>> selectEmployeeList(Integer companyId, EmployeeTermModel termModel);

    PageModel<List<EmployeeModel>> selectEmployees(Integer companyId, EmployeeTermModel termModel);

    PageModel<List<EmployeeModel>> selectStEmployees(Integer companyId, EmployeeTermModel termModel);

    /**
     * 验证工号是否存在
     *
     * @param companyId
     * @param enumber
     * @param id
     * @return
     */
    Boolean checkEnumber(Integer companyId, String enumber, Integer id);

    /**
     * 解除绑定研发考勤
     *
     * @param ids
     * @return
     * @throws OwnerException
     */
    Boolean unbindAtt(List<Integer> ids) throws OwnerException;

    /**
     * 禁用/启用研发考勤
     *
     * @param ids
     * @param trigger
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean toggleAtt(List<Integer> ids, Boolean trigger, UserInfo info) throws OwnerException;

    /**
     * 获取公司花名册
     */
    List<EmployeeModel> getEmployeeList(Integer companyId);

    /**
     * 设置角色
     *
     * @param model
     * @param userInfo
     * @return
     */
    Boolean setRoleType(KeysAndIdsModel model, UserInfo userInfo);

    /**
     * 保存签名
     *
     * @param id
     * @param autographUrl
     * @param userInfo
     * @return
     */
    Boolean saveAutographUrl(Integer id, String autographUrl, UserInfo userInfo);
}
