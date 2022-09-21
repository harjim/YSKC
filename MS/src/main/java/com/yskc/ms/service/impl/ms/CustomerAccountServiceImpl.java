package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.CustomerAccountDao;
import com.yskc.ms.entity.ms.CustomerAccount;
import com.yskc.ms.entity.ms.models.CustomerAccountModel;
import com.yskc.ms.entity.ms.models.QueryCustomerAccountModel;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.service.ms.CustomerAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: wangxing
 * @CreateTime: 2019-12-18 09:15
 * @Description: 客户账号实现类
 */
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {
    @Autowired
    private CustomerAccountDao customerAccountDao;

    @Override
    public PageModel<List<CustomerAccountModel>> queryCustomerAccount(QueryCustomerAccountModel query, DataPermModel dataPerm) {
        Pagination page = query.getPagination();
        if(CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getAscs())){
            page.setDescs(new ArrayList<>());
            page.getDescs().add("createTime");
        }
        List<CustomerAccountModel> list = customerAccountDao.queryCustomerAccount(page, query, dataPerm);
        if (!CollectionUtils.isEmpty(list)) {
            for (CustomerAccountModel model : list) {
                model.setPassword("******");
            }
        }
        return PageUtils.build(page, list);
    }

    @Override
    public boolean insertCustomerAccount(UserInfo userInfo, CustomerAccountModel model) {
        CustomerAccount account = new CustomerAccount();
        BeanUtil.copyProperties(model, account);
        account.setCreateTime(new Date());
        account.setCreatorId(userInfo.getId());
        account.setLastUpdateTime(new Date());
        account.setLastUpdatorId(userInfo.getId());
        return customerAccountDao.insert(account) > 0;
    }

    @Override
    public boolean updateCustomerAccount(UserInfo userInfo, CustomerAccountModel model) {
        CustomerAccount account = new CustomerAccount();
        BeanUtil.copyProperties(model, account);
        account.setLastUpdatorId(userInfo.getId());
        account.setLastUpdateTime(new Date());
        return customerAccountDao.updateById(account) > 0;
    }

    @Override
    public boolean delCustomerAccount(Integer id) {
        return customerAccountDao.deleteById(id) > 0;
    }

    @Override
    public boolean delCustomerAccountList(BatchDeleteModel deleteModel) {
        return customerAccountDao.deleteBatchIds(deleteModel.getIds()) > 0;
    }

    @Override
    public CustomerAccount getDataById(Integer id) {
        return customerAccountDao.selectById(id);
    }

    @Override
    public List<QueryCustomerAccountModel> getDataByPlatform(String platform) {
        if (StrUtil.isEmpty(platform)) {
            return new ArrayList<>();
        }
        return customerAccountDao.getDataByPlatform(platform);
    }

    @Override
    public List<QueryCustomerAccountModel> getDataBypUrl(String pUrl) {
        if (StrUtil.isEmpty(pUrl)) {
            return new ArrayList<>();
        }
        return customerAccountDao.getDataBypUrl(pUrl);
    }
}
