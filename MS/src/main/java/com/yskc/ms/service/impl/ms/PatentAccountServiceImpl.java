package com.yskc.ms.service.impl.ms;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PatentAccountDao;
import com.yskc.ms.entity.ms.PatentAccount;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.patent.PatentAccountModel;
import com.yskc.ms.models.patent.PatentAccountTreeModel;
import com.yskc.ms.models.patent.QueryPatentListModel;
import com.yskc.ms.service.ms.PatentAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: wangxing
 * @CreateTime: 2020-01-03 14:14
 * @Description: 专利账号管理实现类
 */
@Service
public class PatentAccountServiceImpl implements PatentAccountService {

    @Autowired
    private PatentAccountDao patentAccountDao;

    @Override
    public PageModel<List<PatentAccountModel>> queryPatentAccountList(QueryPatentListModel query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, patentAccountDao.queryPatentAccount(page, query,perm));
    }


    @Override
    public boolean addPatentAccount(PatentAccountModel model, UserInfo info) {
        PatentAccount patentAccount = new PatentAccount();
        patentAccount.setPassword(model.getPassword());
        patentAccount.setUsername(model.getUsername());
        patentAccount.setRemark(model.getRemark());
        patentAccount.setCreatorId(info.getId());
        patentAccount.setCreateTime(new Date());
        patentAccount.setStatus(1);
        patentAccount.setLastUpdateTime(new Date());
        patentAccount.setLastUpdatorId(info.getId());
        return patentAccountDao.insert(patentAccount) > 0;
    }

    @Override
    public boolean updatePatentAccount(PatentAccountModel model, UserInfo info) {
        PatentAccount patentAccount = new PatentAccount();
        BeanUtil.copyProperties(model, patentAccount);
        patentAccount.setLastUpdateTime(new Date());
        patentAccount.setLastUpdatorId(info.getId());
        patentAccount.setStatus(1);
        return patentAccountDao.updateById(patentAccount) > 0;
    }

    @Override
    public boolean delPatentAccount(Integer id) {
        return patentAccountDao.deleteById(id) > 0;
    }

    @Override
    public boolean delBatch(List<PatentAccountModel> patentAccountModels) {
        List<Integer> ids = patentAccountModels.stream().map(PatentAccountModel::getId).collect(Collectors.toList());
        return patentAccountDao.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean checkUsername(String userName, Integer id) {
        PatentAccountModel account = patentAccountDao.getPatentAccountByUserName(userName);
        if (account == null) {
            return true;
        }
        if (account.getId().equals(id)) {
            return true;
        }
        return false;
    }

    @Override
    public List<PatentAccountTreeModel> getPatentAccountTree() {
        return patentAccountDao.getPatentAccountTree();
    }

    @Override
    public Boolean updateStatus(Integer id, String password, Integer status) {
        if (id == null || status == null) {
            return false;
        }
        return patentAccountDao.updateStatus(id, password, status);
    }
}
