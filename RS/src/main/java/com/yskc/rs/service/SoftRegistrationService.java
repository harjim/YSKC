package com.yskc.rs.service;


import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.*;
import com.yskc.rs.models.excel.SoftRegistrationExcel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:44
 * @Description:计算机软件著作类service层
 * @author: hsx
 */
public interface SoftRegistrationService {

    /**
     * 导入计算机软件著作excel文件
     * @param info
     * @param list
     * @return
     * @throws OwnerException
     */
    Boolean importSoftRegistration(UserInfo info, List<SoftRegistrationExcel> list) throws OwnerException;

    /**
     * 条件查询
     * @param model
     * @return
     */
    PageModel<List<SoftRegistrationModel>> getList(QuerySoftRegistrationModel model,Integer companyId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean del(List<Integer> ids);

    /**
     * 新增
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean add(SoftRegistrationModel model,UserInfo info) throws OwnerException;

    /**
     * 编辑
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean edit(SoftRegistrationModel model,UserInfo info)throws OwnerException;

    /**
     * 获取单个文本信息
     * @param id
     * @return
     */
    SoftRegistrationModel getText(@Param("id") Integer id);
}
