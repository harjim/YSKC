package com.yskc.ms.service.ms;

import com.yskc.common.model.PageModel;
import com.yskc.ms.models.DataPermModel;
import com.yskc.ms.models.policy.PolicyContentModel;
import com.yskc.ms.models.policy.QueryPolicyContent;

import java.util.List;

/**
 * @BelongsProject: ms
 * @BelongsPackage: com.yskc.ms.service.ms
 * @Author: zhangdingfu
 * @CreateTime: 2020-04-25 09:32
 * @Description: 政策内容业务层接口
 */
public interface PolicyContentService {

    /**
     * 获取政策内容列表
     * @param query
     * @return
     */
    PageModel<List<PolicyContentModel>>  getList(QueryPolicyContent query, DataPermModel dataPerm,Integer userId);

}
