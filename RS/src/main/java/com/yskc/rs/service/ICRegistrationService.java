package com.yskc.rs.service;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.rs.models.ICRegistrationModel;
import com.yskc.rs.models.QueryICRegistrationModel;
import com.yskc.rs.models.UserInfo;
import com.yskc.rs.models.excel.ICRegistrationExcel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @DateTime: 2021/11/2 14:44
 * @Description:集成电路设计类service层
 * @author: hsx
 */
public interface ICRegistrationService {

    /**
     * 导入集成电路设计类excel文件
     * @param info
     * @param list
     * @return
     */
    Boolean importICRegistration(UserInfo info, List<ICRegistrationExcel> list) throws OwnerException;

    /**
     * 条件查询
     * @param model
     * @return
     */
    PageModel<List<ICRegistrationModel>> getList(QueryICRegistrationModel model,Integer companyId);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    Boolean del(List<Integer> ids);

    /**
     * 新增
     * @param model
     * @return
     */
    Boolean add(ICRegistrationModel model,UserInfo info) throws OwnerException;

    /**
     * 编辑
     * @param model
     * @param info
     * @return
     * @throws OwnerException
     */
    Boolean edit(ICRegistrationModel model,UserInfo info) throws OwnerException;

    /**
     * 获取单个文本信息
     * @param id
     * @return
     */
    ICRegistrationModel getText(@Param("id") Integer id);

}
