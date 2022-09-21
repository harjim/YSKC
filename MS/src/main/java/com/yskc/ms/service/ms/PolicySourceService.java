package com.yskc.ms.service.ms;

import com.yskc.common.exception.OwnerException;
import com.yskc.common.model.PageModel;
import com.yskc.ms.entity.ms.PolicySource;
import com.yskc.ms.models.BatchDeleteModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.UserInfo;
import com.yskc.ms.models.dept.DeptUserInfo;
import com.yskc.ms.models.policy.PolicySourceModel;
import com.yskc.ms.models.policy.PolicySubscriptionModel;
import com.yskc.ms.models.policy.QueryPolicyContent;
import com.yskc.ms.models.policy.QuerySourceUser;

import java.util.List;
import java.util.Map;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-05-06 10:25
 * @Description: 政策来源业务接口层
 */
public interface PolicySourceService {
    /**
     * 政策来源列表
     *
     * @param userInfo
     * @param query
     * @return
     */
    PageModel<List<PolicySourceModel>> getList(UserInfo userInfo, QueryPolicyContent query);

    /**
     * 政策订阅人员列表
     *
     * @param query
     * @return
     */
    PageModel<List<DeptUserInfo>> getSourceUserList(QuerySourceUser query, DataPermModel perm);

    /**
     * 订阅政策来源
     *
     * @param userId
     * @param source
     * @return
     */
    Boolean subscription(Integer userId, PolicySource source);

    /**
     * 取消订阅政策来源
     *
     * @param userId
     * @param source
     * @return
     */
    Boolean unsubscription(Integer userId, PolicySource source);

    /**
     * 添加政策来源
     *
     * @param userId
     * @param source
     * @return
     */
    Boolean add(Integer userId, PolicySource source);

    /**
     * 检查政策TypeUrl是否重复
     *
     * @param sourceId
     * @param typeUrl
     * @return
     * @throws OwnerException
     */
    Boolean checkTypeUrl(Integer sourceId, String typeUrl) throws OwnerException;

    /**
     * 更新政策来源
     *
     * @param userId
     * @param source
     * @return
     */
    Boolean update(Integer userId, PolicySource source);

    /**
     * 删除政策来源
     *
     * @param deleteModel
     * @return
     * @throws OwnerException
     */
    Boolean del(BatchDeleteModel deleteModel) throws OwnerException;

    /**
     * 获取政策来源(单条)
     *
     * @param sourceId
     * @return
     */
    Map<String, Object> getSource(Integer sourceId);

    /**
     * 添加订阅人
     *
     * @param subscriptionModel
     * @return
     */
    boolean addSubscription(PolicySubscriptionModel subscriptionModel);
}
