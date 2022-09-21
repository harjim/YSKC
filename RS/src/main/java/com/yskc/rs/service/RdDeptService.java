package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.rddept.*;

import java.util.List;

/**
 * @BelongsProject: rs
 * @BelongsPackage: com.yskc.rs.service
 * @Author: wangxing
 * @CreateTime: 2019-08-08 15:32
 * @Description: 研发部门接口类
 */
public interface RdDeptService {
    /**
     * 查询所有rd部门
     *
     * @param companyId
     * @param year
     * @return
     */
    List<RdDeptTree> queryRdDeptTree(Integer companyId, Integer year);

    /**
     * 操作研发部门
     *
     * @param rdDeptModel
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean modifyRdDept(RdDeptModel rdDeptModel, UserInfo userInfo) throws OwnerException;

    /**
     * 根据id删除研发部门
     *
     * @param rdDeptModel
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean delete(RdDeptModel rdDeptModel, UserInfo info) throws OwnerException;

    /**
     * 获取最大年份 <= 传入年份
     * 获取研发部门
     *
     * @param companyId
     * @param year
     * @return
     */
    ResultRdDeptModel getList(Integer companyId, Integer year);

    /**
     * 导入年份数据
     *
     * @param userInfo
     * @param rdYearModel
     * @return
     * @throws OwnerException
     */
    Boolean importYear(UserInfo userInfo, RdYearModel rdYearModel) throws OwnerException;

    /**
     * 克隆节点
     *
     * @param cloneRdDept
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean cloneNode(CloneRdDeptModal cloneRdDept, UserInfo userInfo) throws OwnerException;

    /**
     * 排序研发部门
     *
     * @param rdDeptSort
     * @param userInfo
     * @return
     * @throws OwnerException
     */
    Boolean sortNode(RdDeptSortModel rdDeptSort, UserInfo userInfo)throws OwnerException;
}
