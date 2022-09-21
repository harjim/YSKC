package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.rs.models.SenseModel;
import com.yskc.rs.models.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @DateTime: 2021/10/12 16:40
 * @Description:研发意识指导业务层
 * @author: hsx
 */
public interface SenseService {

    /**
     * 查询该项目下所有的意识管理指导记录
     * @param companyId
     * @return
     */
    Map<Integer, List<SenseModel>> getSenses(Integer companyId);

    /**
     * 删除研发意识管理阶段记录
     * @param ids
     * @return
     */
    boolean delSense(List<Integer> ids);

    /**
     * 编辑研发意识阶段记录
     * @param model
     * @return
     */
    boolean editSense(SenseModel model , UserInfo info) throws OwnerException;

    /**
     * 新增研发意识管理阶段记录
     * @param model
     * @param info
     * @return
     */
    boolean addSense(SenseModel model , UserInfo info) throws OwnerException;
}
