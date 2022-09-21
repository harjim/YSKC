package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.CustomerAccount;
import com.yskc.ms.entity.ms.models.CustomerAccountModel;
import com.yskc.ms.entity.ms.models.QueryCustomerAccountModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-18 09:08
 * @Description: 客户账号管理接口类
 */

public interface CustomerAccountService {

    /**
     * 获取所有数据
     * @param query
     * @return
     */
    PageModel<List<CustomerAccountModel>> queryCustomerAccount(QueryCustomerAccountModel query, DataPermModel dataPerm);

    /**
     * 新增数据
     * @param userInfo
     * @param model
     * @return
     */
    boolean insertCustomerAccount(UserInfo userInfo,CustomerAccountModel model);

    /**
     * 修改账户管理
     * @param userInfo
     * @param model
     * @return
     */
    boolean updateCustomerAccount(UserInfo userInfo,CustomerAccountModel model);

    /**
     * 删除账户
     * @param id
     * @return
     */
    boolean delCustomerAccount(Integer id);

    /**
     * 批量删除
     * @param deleteModel
     * @return
     */
    boolean delCustomerAccountList(BatchDeleteModel deleteModel);

    /**
     * 查看密码
     * @param id
     * @return
     */
    CustomerAccount getDataById(Integer id);


    /**
     * 根据系统搜索
     * @param platform
     * @return
     */
    List<QueryCustomerAccountModel> getDataByPlatform(String platform);

    /**
     * 根据网址搜索
     * @param pUrl
     * @return
     */
    List<QueryCustomerAccountModel> getDataBypUrl( String pUrl);

}
