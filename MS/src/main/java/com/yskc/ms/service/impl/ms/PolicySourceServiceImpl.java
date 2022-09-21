package com.yskc.ms.service.impl.ms;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.yskc.common.enums.DataSourceEnum;
import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.common.utils.PageUtils;
import com.yskc.ms.dao.ms.PolicyContentDao;
import com.yskc.ms.dao.ms.PolicySourceDao;
import com.yskc.ms.dao.ms.PolicySourceUserDao;
import com.yskc.ms.entity.ms.PolicySource;
import com.yskc.ms.entity.ms.PolicySourceUser;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.policy.PolicySourceModel;
import com.yskc.ms.models.policy.PolicySubscriptionModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.models.policy.QuerySourceUser;
import com.yskc.ms.service.ms.PolicySourceService;
import com.yskc.ms.utils.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.impl.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:26
 * @Description: 政策来源业务实现层
 */
@Service
public class PolicySourceServiceImpl implements PolicySourceService {

    @Autowired
    private PolicySourceDao policySourceDao;

    @Autowired
    private PolicySourceUserDao policySourceUserDao;

    @Autowired
    private PolicyContentDao policyContentDao;

    @Override
    public PageModel<List<PolicySourceModel>> getList(UserInfo userInfo, QueryPolicyContent query) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> asc = new ArrayList<>();
            asc.add("sname");
            page.setAscs(asc);
        }
        List<PolicySourceModel> data = policySourceDao.getList(page, query,userInfo.getId());
        if (!CollectionUtils.isEmpty(data)) {
            List<Integer> currentIds = new ArrayList();
            data.forEach(item -> {
                currentIds.add(item.getId());
            });
            List<PolicySourceUser> sourceUsers = policySourceDao.getSubscription(userInfo.getId(), currentIds);
            if (!CollectionUtils.isEmpty(sourceUsers)) {
                Map<Integer, Boolean> sourceIdMap = new HashMap<>();
                sourceUsers.forEach(item -> sourceIdMap.put(item.getSourceId(), true));
                data.forEach(item -> {
                    item.setSubscription(sourceIdMap.getOrDefault(item.getId(), false));
                });
            }
        }
        return PageUtils.build(page, data);
    }

    @Override
    public PageModel<List<DeptUserInfo>> getSourceUserList(QuerySourceUser query, DataPermModel perm) {
        Pagination page = query.getPagination();
        if (CollectionUtils.isEmpty(page.getAscs()) && CollectionUtils.isEmpty(page.getDescs())) {
            List<String> desc = new ArrayList<>();
            desc.add("y.createTime");
            page.setDescs(desc);
        }
        return PageUtils.build(page, policySourceDao.getSourceUserList(page, query, perm));
    }

    @Override
    public Boolean subscription(Integer userId, PolicySource source) {
        if (policySourceUserDao.checkSubscription(userId, source.getId()) > 0) {
            return true;
        }
        PolicySourceUser sourceUser = PolicySourceUser.build(new Date(), userId, source.getId());
        return policySourceUserDao.insert(sourceUser) > 0;
    }

    @Override
    public Boolean unsubscription(Integer userId, PolicySource source) {
        policySourceUserDao.deleteSubscription(userId, source.getId());
        return true;
    }

    @Override
    public Boolean add(Integer userId, PolicySource source) {
        Date now = new Date();
        source.setLastUpdatorId(userId);
        source.setLastUpdateTime(now);
        source.setCreatorId(userId);
        source.setCreateTime(now);
        return policySourceDao.insert(source) > 0;
    }

    @Override
    public Boolean update(Integer userId, PolicySource source) {
        Date now = new Date();
        source.setLastUpdatorId(userId);
        source.setLastUpdateTime(now);
        return policySourceDao.updateById(source) > 0;
    }

    @Override
    public Boolean del(BatchDeleteModel deleteModel) throws OwnerException {
        List<Integer> ids = deleteModel.getIds();
        if (CollectionUtils.isEmpty(ids)) {
            throw new OwnerException("删除失败，未选择任何数据。");
        }
        TransactionStatus transactionStatus = null;
        try {
            transactionStatus = TransactionUtils.newTransaction(DataSourceEnum.MS);
            policySourceUserDao.deleteBySourceIds(ids);
            policyContentDao.deleteBySourceIds(ids);
            policySourceDao.deleteBatchIds(ids);
            TransactionUtils.commit(DataSourceEnum.MS, transactionStatus);
        } catch (Exception e) {
            TransactionUtils.rollback(DataSourceEnum.MS, transactionStatus);
        }
        return true;
    }

    @Override
    public Map<String, Object> getSource(Integer sourceId) {
        return policySourceDao.getSource(sourceId);
    }

    @Override
    public boolean addSubscription(PolicySubscriptionModel subscriptionModel) {
        List<PolicySourceUser> sourceUsers = new ArrayList<>();
        Integer sourceId = subscriptionModel.getSourceId();
        List<Integer> subscriptionUserIds = policySourceUserDao.getSubscription(sourceId);
        Map<Integer, Boolean> subscriptionMap = new HashMap<>();
        subscriptionUserIds.forEach(userId -> subscriptionMap.put(userId, true));
        Date now = new Date();
        subscriptionModel.getUserIds().forEach(subscriptionId -> {
            if (subscriptionMap.containsKey(subscriptionId)) {
                return;
            }
            sourceUsers.add(PolicySourceUser.build(now, subscriptionId, sourceId));
        });
        if (CollectionUtils.isEmpty(sourceUsers)) {
            return true;
        }
        return policySourceUserDao.insertBatch(sourceUsers) > 0;
    }

    @Override
    public Boolean checkTypeUrl(Integer sourceId, String typeUrl) throws OwnerException {
        if (StringUtils.isEmpty(typeUrl)) {
            throw new OwnerException("政策类型地址不能为空。");
        }
        Integer originId = policySourceDao.getByTypeUrl(typeUrl);
        if (null != originId && !originId.equals(sourceId)) {
            throw new OwnerException("该政策类型地址已存在，请输入其他地址。");
        }
        return true;
    }
}
