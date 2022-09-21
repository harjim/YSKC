package com.yskc.ms.dao.ms;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.ms.entity.ms.CustomerAccount;
import com.yskc.ms.entity.ms.models.CustomerAccountModel;
import com.yskc.ms.entity.ms.models.QueryCustomerAccountModel;
import com.yskc.ms.models.DataPermModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.dao.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-18 09:05
 * @Description: 客户账号管理Dao
 */
@Repository
public interface CustomerAccountDao extends BaseMapper<CustomerAccount> {
    /**
     * 获取所有客户账号信息
     *
     * @param page
     * @param query
     * @return
     */
    List<CustomerAccountModel> queryCustomerAccount(@Param("page") Pagination page,
                                                    @Param("query") QueryCustomerAccountModel query,
                                                    @Param("perm") DataPermModel dataPerm);

    /**
     * 根据系统搜索
     *
     * @param platform
     * @return
     */
    List<QueryCustomerAccountModel> getDataByPlatform(@Param("platform") String platform);

    /**
     * 根据网址搜索
     *
     * @param pUrl
     * @return
     */
    List<QueryCustomerAccountModel> getDataBypUrl(@Param("pUrl") String pUrl);
}
